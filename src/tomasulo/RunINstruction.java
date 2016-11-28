package tomasulo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import functionalUnits.FunctionalUnitsType;
import functionalUnits.MainFunctionUnit;
import memory.Clock;
import memory.InstructionMemory;
import memory.writeHitPolicy;
import memory.writeMissPolicy;
import instructionSetArchitecture.AddImmediateInstruction;
import instructionSetArchitecture.BEQInstruction;
import instructionSetArchitecture.InstructionSetArchitecture;
import instructionSetArchitecture.JALRInstruction;
import instructionSetArchitecture.JMPInstruction;
import instructionSetArchitecture.LoadInstruction;
import instructionSetArchitecture.MulInstruction;
import instructionSetArchitecture.RETInstruction;
import instructionSetArchitecture.StoreInstruction;
import programState.ProgramState;
import programState.ProgramStateEntry;
import registerStatus.RegisterStatus;
import registers.RegisterEnum;
import registers.RegisterFile;
import reorderBuffer.ROB;
import reorderBuffer.ROBEntry;
import reservationStations.ReservationStationEntry;
import reservationStations.ReservationsStationTable;

public class RunINstruction implements Runnable {

	private InstructionSetArchitecture instruction;
	private ProgramState programState;
	private RegisterStatus registerStatus;
	private ReservationsStationTable reservationsStationTable;
	private ROB rob;
	private boolean committed;
	private int pipelineDepth;

	public RunINstruction(InstructionSetArchitecture instruction,
			ProgramState programState, RegisterStatus registerStatus,
			ReservationsStationTable reservationsStationTable, ROB rob,
			int pipelineDepth) {

		this.instruction = instruction;
		this.programState = programState;
		this.registerStatus = registerStatus;
		this.reservationsStationTable = reservationsStationTable;
		this.rob = rob;
		this.committed = false;
		this.pipelineDepth = pipelineDepth;
	}

	@Override
	public void run() {

		issueInstruction();
		System.out.println("-->" + instruction.toString() + "----->"
				+ " finished " + " at cycle : " + Clock.counter.intValue());

	}

	public void issueInstruction() {

		// checking if there are available reservation station and rob entry

		String reservationStationName = MainFunctionUnit.getInstance()
				.getAvailableReservationStation(
						instruction.getFunctionalUnitsType(),
						reservationsStationTable);

		while (rob.isFull()
				|| reservationStationName == null
				|| ! (programState.getInstructionNumberOfInOrderIssuedISA() >= instruction
						.getInstructionNumber())) {
			
//			System.out.println("------------------>"+programState.getInstructionNumberOfInOrderIssuedISA()+"------"+instruction.toString());
			
			reservationStationName = MainFunctionUnit.getInstance()
					.getAvailableReservationStation(
							instruction.getFunctionalUnitsType(),
							reservationsStationTable);
		}

		System.out.println("-->" + instruction.toString() + "----->"
				+ " Start issuing" + " at cycle : " + Clock.counter.intValue());

		ReservationStationEntry reservationStation = reservationsStationTable
				.getReservationStationEntry(reservationStationName);
		instruction.setReservationStationEntryName(reservationStationName);

		// insert into ROB after we made sure that there is an empty ROB
		// entry
		// this line insert instruction and destination into rob entry and
		// return rob entry number
		
		while(rob.getRobLastinstructionNumEntry() != instruction.getInstructionNumber()){
			System.out.print("");
		}
		
		int robEntryNum = rob.insertToROB(instruction);
		rob.setRobLastinstructionNumEntry(rob.getRobLastinstructionNumEntry()+1);

		// insert into reservation station
		reservationStation.setBusy(true);
		reservationStation.setOp(instruction.getOperation());

		// check if the instuciton have destination register
		if (instruction.getDestinationRegister() != null) {
			reservationStation.setDestination(robEntryNum);

			// insert into registerStatus file the ROB entry number of
			// destination register
			registerStatus.insertIntoRegisterStatusTable(
					instruction.getDestinationRegister(), robEntryNum);

		} else {
			reservationStation.setDestination(robEntryNum);
		}

		// All instruction has RS which is the first source operand
		// check if RS is busy (waiting for another instruction to write on

		RegisterEnum rs = instruction.getSourceOneRegister();
		if (registerStatus.isBusy(rs)) {

			// get ROB number of the ROB entry that will write on RS after
			// Committing
			Integer ROBNum = registerStatus.getROBNum(rs);

			// check if entry with ROBNum is ready which means that the
			// value of
			// the result is available
			if (rob.IsReady(ROBNum)) {

				reservationStation.setVj(rob.getValue(ROBNum));
			} else {
				// set Qj to rob entry number
				reservationStation.setQj(ROBNum);
			}

		} else {
			// if Rs is available so we will load the operand from it and
			// add it to vj in reservation station
			short rsValue = RegisterFile.getInstance().loadDataFromRegister(rs);
			reservationStation.setVj(rsValue);
		}

		// instructions with 2 source operands eg: ADD , SUB , MULT , NAND ,SW
		// ,BEQ
		if (instruction.getSourceTwoRegister() != null) {
			RegisterEnum rt = instruction.getSourceTwoRegister();
			if (registerStatus.isBusy(rt)) {
				// get ROB number of the ROB entry that will write on RT after
				// Committing
				Integer ROBNum = registerStatus.getROBNum(rt);

				// check if entry with ROBNum is ready which means that the
				// value of
				// the result is available
				if (rob.IsReady(ROBNum)) {
					reservationStation.setVk(rob.getValue(ROBNum));
				} else {
					// set Qj to rob entry number
					reservationStation.setQk(ROBNum);
				}

			} else {
				// if Rs is available so we will load the operand from it and
				// add it to vj in reservation station
				short rtValue = RegisterFile.getInstance()
						.loadDataFromRegister(rt);
				reservationStation.setVk(rtValue);

			}

		}

		// instruction with immeditate value as the second operand of instructon
		// eg: LW,SW,BEQ and ADDI

		// ADDI
		if (instruction instanceof AddImmediateInstruction)
			reservationStation.setVk(((AddImmediateInstruction) instruction)
					.getImmediateValue());

		if (instruction instanceof BEQInstruction)
			reservationStation.setAddress(((BEQInstruction) instruction)
					.getImmeditate());

		if (instruction instanceof LoadInstruction)
			reservationStation.setAddress(((LoadInstruction) instruction)
					.getImmediateValue());

		if (instruction instanceof StoreInstruction)
			reservationStation.setAddress(((StoreInstruction) instruction)
					.getImmediateValue());

		if (instruction instanceof JMPInstruction)
			reservationStation.setAddress(((JMPInstruction) instruction)
					.getImmediateValue());

		// update program state table after one clock cycle
		int current = Clock.counter.intValue();
		while (Clock.counter.intValue() != current + 1)
			;

		// System.out.println(instruction.getInstructionNumber());
		ProgramStateEntry programStateEntry = programState
				.getProgramStateTableEntry(instruction.getInstructionNumber());
		// System.out.println(programStateEntry.toString());
		programStateEntry.setIssued(current + 1);

		System.out.println("-->" + instruction.toString() + "----->"
				+ " End issuing" + " at cycle : " + Clock.counter.intValue());

		// increment turn of issued instructions
		if (programState.getInstructionNumberOfInOrderIssuedISA() == instruction
				.getInstructionNumber())
			programState.setInstructionNumberOfInOrderIssuedISA(programState
					.getInstructionNumberOfInOrderIssuedISA() + pipelineDepth);

		executeInstruction(robEntryNum);

	}

	public void executeInstruction(int robEntryNum) {
		ProgramStateEntry programStateEntry = programState
				.getProgramStateTableEntry(instruction.getInstructionNumber());

		if (programStateEntry.getIssued() == 0) {
			System.out.println("instruction not issued yet");
			return;
		}

		ReservationStationEntry reservationStation = reservationsStationTable
				.getReservationStationEntry(instruction
						.getReservationStationEntryName());

		while (reservationStation.getQj() != null
				|| reservationStation.getQk() != null) {
			System.out.print("");
		}

		System.out.println("-->" + instruction.toString() + "----->"
				+ " Start Executing" + " at cycle : "
				+ Clock.counter.intValue());

		if (reservationStation.getQj() == null
				&& reservationStation.getQk() == null) {
			int execEndClockCycle = instruction.execute(
					reservationStation.getVj(), reservationStation.getVk());
			if (instruction instanceof LoadInstruction
					|| instruction instanceof JALRInstruction
					|| instruction instanceof RETInstruction
					|| instruction instanceof JMPInstruction
					|| instruction instanceof StoreInstruction
					|| instruction instanceof BEQInstruction) {

				reservationStation.setAddress(instruction.getResult());

			}

			programState.getProgramStateTableEntry(
					instruction.getInstructionNumber()).setExecuted(
					(short) execEndClockCycle);

			System.out.println("-->" + instruction.toString() + "----->"
					+ " End Executing" + " at cycle : "
					+ Clock.counter.intValue());

			writeInstruction(robEntryNum);
		}

	}

	public void writeInstruction(int robEntryNum) {

		ProgramStateEntry programStateEntry = programState
				.getProgramStateTableEntry(instruction.getInstructionNumber());

		if (programStateEntry.getExecuted() == 0) {
			System.out.println("-------------->" + instruction.toString());
			System.out.println("instruction not executed yet");
			return;
		}

		System.out.println("-->" + instruction.toString() + "----->"
				+ " Start Writing" + " at cycle : " + Clock.counter.intValue());

		// stalling for 1 clock cycle

		int current = Clock.counter.intValue();
		while (Clock.counter.intValue() != current + 1)
			;

		ReservationStationEntry reservationStation = reservationsStationTable
				.getReservationStationEntry(instruction
						.getReservationStationEntryName());

		// Getting ROB number
		Integer ROBNum = reservationStation.getDestination();

		// Emptying reservation station entry
		reservationsStationTable.remove(instruction
				.getReservationStationEntryName());
		Short result = instruction.getResult();

		if (ROBNum != null) {
			// ROB entry ready
			rob.setROBEntryReady(ROBNum);
		}
		// ROB adding result
		rob.writeResultTOROB(ROBNum, result);
		// pass results to all waiting reservation stations
		reservationsStationTable.passResultToWaitingReservationStation(result,
				ROBNum);

		// update program state table after one clock cycle
		programState.getProgramStateTableEntry(
				instruction.getInstructionNumber()).setWritten(
				(short) current + 1);

		System.out.println("-->" + instruction.toString() + "----->"
				+ " End Writing" + " at cycle : " + Clock.counter.intValue());

		commitInstruction(robEntryNum);

	}

	public void commitInstruction(int robEntryNum) {

		ProgramStateEntry programStateEntry = programState
				.getProgramStateTableEntry(instruction.getInstructionNumber());

		if (programStateEntry.getWritten() == 0) {
			System.out.println("instruction not Written yet");
			return;
		}
		// stall until its turn come
		while (rob.getRobentryNumberOfInOrderCommittedISA() != robEntryNum) {
			// because compiler is ignoring the loop so I have to print any
			// thing to make it check condition
			System.out.print("");
		}

		System.out.println("------------------> ROBNUM------------->"
				+ robEntryNum);
		RegisterEnum registerDestination = instruction.getDestinationRegister();

		System.out.println("-->" + instruction.toString() + "----->"
				+ " Want to commit " + " at cycle : "
				+ Clock.counter.intValue());

		rob.commit();
		if (instruction.getResult() != null && registerDestination != null) {

			if (instruction instanceof JALRInstruction) {
				RegisterFile.getInstance().storeDataToRegister(
						registerDestination,
						((JALRInstruction) instruction).getPcResult());
			} else {
				RegisterFile.getInstance().storeDataToRegister(
						registerDestination, instruction.getResult());
				registerStatus
						.removeFromRegisterStatusTable(registerDestination);

			}

		}
		// stalling for 1 clock cycle

		int current = Clock.counter.intValue();
		while (Clock.counter.intValue() != current + 1)
			;

		// update program state table after one clock cycle
		programState.getProgramStateTableEntry(
				instruction.getInstructionNumber()).setCommitted(
				(short) current + 1);

		// set RobentryNumberOfInOrderCommittedISA in the ROB to allow
		// committing of next instruction
		// this will allow inorder committing

		if (rob.getRobentryNumberOfInOrderCommittedISA() != rob.getSize()) {
			rob.setRobentryNumberOfInOrderCommittedISA(rob
					.getRobentryNumberOfInOrderCommittedISA() + 1);
		} else {
			rob.setRobentryNumberOfInOrderCommittedISA(1);
		}

		System.out.println("-->" + instruction.toString() + "----->"
				+ " committed" + " at cycle : " + Clock.counter.intValue());

		// set committed flag to true ;
		committed = true;

	}

	// public static void main(String[] args) {
	//
	// InstructionSetArchitecture[] instructions = new
	// InstructionSetArchitecture[9];
	// instructions[0] = new LoadInstruction(RegisterEnum.R2, 1,
	// RegisterEnum.R1, (short) 20);
	//
	// instructions[1] = new MulInstruction(RegisterEnum.R6, 2,
	// RegisterEnum.R3, RegisterEnum.R4);
	//
	// instructions[2] = new StoreInstruction(RegisterEnum.R4, 3,
	// RegisterEnum.R5, (short) 30);
	//
	// instructions[3] = new BEQInstruction(RegisterEnum.R3, 4,
	// RegisterEnum.R7, (short) 50);
	// instructions[4] = new JMPInstruction(RegisterEnum.R3, 5, (short) 70);
	// instructions[5] = new RETInstruction(RegisterEnum.R1, 6);
	// instructions[6] = new JALRInstruction(RegisterEnum.R4, 7,
	// RegisterEnum.R5);
	// instructions[7] = new MulInstruction(RegisterEnum.R7, 8,
	// RegisterEnum.R4, RegisterEnum.R3);
	// instructions[8] = new LoadInstruction(RegisterEnum.R2, 9,
	// RegisterEnum.R6, (short) 20);
	//
	// ArrayList<String> adderReservationStations = new ArrayList<String>();
	// adderReservationStations.add("add1");
	// adderReservationStations.add("add2");
	// adderReservationStations.add("add3");
	// adderReservationStations.add("add4");
	//
	// ArrayList<String> multReservationStations = new ArrayList<String>();
	// multReservationStations.add("mult");
	//
	// ArrayList<String> loadReservationStationsNames = new ArrayList<String>();
	// loadReservationStationsNames.add("load1");
	// loadReservationStationsNames.add("load2");
	//
	// ArrayList<String> storeReservationStationsNames = new
	// ArrayList<String>();
	// storeReservationStationsNames.add("store1");
	//
	// ArrayList<String> nandReservationStations = new ArrayList<String>();
	// nandReservationStations.add("nand");
	//
	// ArrayList<String> callReservationStations = new ArrayList<String>();
	// callReservationStations.add("call1");
	// callReservationStations.add("call2");
	//
	// TomasuloProcessor tomasuloProcessor = new TomasuloProcessor(
	// instructions, 3, 5, 2, 1, 8, adderReservationStations,
	// multReservationStations, nandReservationStations,
	// loadReservationStationsNames, storeReservationStationsNames,
	// callReservationStations);
	//
	// RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R1,
	// (short) 20);
	// RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R2,
	// (short) 200);
	// RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R3,
	// (short) 2);
	// RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R4,
	// (short) 290);
	// RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R5,
	// (short) 522);
	// RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R6,
	// (short) 2022);
	// RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R7,
	// (short) 903);
	//
	// System.out
	// .println("----------------------------------- ISSUE ---------------------------------------");
	//
	// tomasuloProcessor.issueInstruction(instructions[0]);
	// tomasuloProcessor.issueInstruction(instructions[1]);
	// tomasuloProcessor.issueInstruction(instructions[2]);
	// tomasuloProcessor.issueInstruction(instructions[3]);
	// tomasuloProcessor.issueInstruction(instructions[4]);
	// tomasuloProcessor.issueInstruction(instructions[5]);
	// tomasuloProcessor.issueInstruction(instructions[6]);
	// // this will not be issued beacuse no available reservation station
	// tomasuloProcessor.issueInstruction(instructions[7]);
	// // this will update the register status R2 with last ROB entry
	// // (renaming)
	// tomasuloProcessor.issueInstruction(instructions[8]);
	//
	// System.out
	// .println("---------------------------------------- Execute  ----------------------------------------------");
	//
	// tomasuloProcessor.executeInstruction(instructions[0]);
	// tomasuloProcessor.executeInstruction(instructions[1]);
	// tomasuloProcessor.executeInstruction(instructions[2]);
	// tomasuloProcessor.executeInstruction(instructions[3]);
	// tomasuloProcessor.executeInstruction(instructions[4]);
	// tomasuloProcessor.executeInstruction(instructions[5]);
	// tomasuloProcessor.executeInstruction(instructions[6]);
	// tomasuloProcessor.executeInstruction(instructions[7]);
	// tomasuloProcessor.executeInstruction(instructions[8]);
	//
	// System.out
	// .println("------------------------------------------ WriteBack   --------------------------------------------");
	//
	// tomasuloProcessor.writeInstruction(instructions[0]);
	// tomasuloProcessor.writeInstruction(instructions[1]);
	// tomasuloProcessor.writeInstruction(instructions[2]);
	// tomasuloProcessor.writeInstruction(instructions[3]);
	// tomasuloProcessor.writeInstruction(instructions[4]);
	// tomasuloProcessor.writeInstruction(instructions[5]);
	// tomasuloProcessor.writeInstruction(instructions[6]);
	// tomasuloProcessor.writeInstruction(instructions[7]);
	// tomasuloProcessor.writeInstruction(instructions[8]);
	//
	// System.out
	// .println("------------------------------------------ Committing   --------------------------------------------");
	//
	// tomasuloProcessor.commitInstruction(instructions[0]);
	// tomasuloProcessor.commitInstruction(instructions[1]);
	// tomasuloProcessor.commitInstruction(instructions[2]);
	// tomasuloProcessor.commitInstruction(instructions[3]);
	// tomasuloProcessor.commitInstruction(instructions[4]);
	// tomasuloProcessor.commitInstruction(instructions[5]);
	// tomasuloProcessor.commitInstruction(instructions[6]);
	// tomasuloProcessor.commitInstruction(instructions[7]);
	// tomasuloProcessor.commitInstruction(instructions[8]);
	//
	// System.out
	// .println("------------------------- Program State ---------------------------");
	// tomasuloProcessor.getProgramState().printProgramStateTable();
	// System.out
	// .println("------------------------- Reservation Station ---------------------------");
	// tomasuloProcessor.getReservationsStationTable()
	// .printReservationStationsTable();
	// System.out
	// .println("------------------------- Register Status ---------------------------");
	// tomasuloProcessor.getRegisterStatus().printRegisterStatus();
	// System.out
	// .println("------------------------- ROB ---------------------------");
	// tomasuloProcessor.getRob().printROB();
	//
	// System.out
	// .println("------------------------- Register File ---------------------------");
	// RegisterFile.getInstance().printregisterFileTable();
	// }
}
