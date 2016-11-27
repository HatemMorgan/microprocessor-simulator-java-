package tomasulo;

import javax.swing.text.AbstractDocument.BranchElement;

import instructionSetArchitecture.AddImmediateInstruction;
import instructionSetArchitecture.AddInstruction;
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
import registers.Register;
import registers.RegisterEnum;
import registers.RegisterFile;
import reorderBuffer.ROB;
import reservationStations.Operation;
import reservationStations.ReservationStationEntry;
import reservationStations.ReservationsStationTable;

public class TomasuloProcessor {

	private ProgramState programState;
	private RegisterStatus registerStatus;
	private ReservationsStationTable reservationsStationTable;
	private ROB rob;

	public TomasuloProcessor(InstructionSetArchitecture[] programInstructions,
			int ROBSize, String[] reservationStationsNames) {
		// intialize program state table and pass instructions array to it
		programState = new ProgramState();
		programState.addInstructionsToProgramStateTable(programInstructions);

		// intialize RegisterStatus table
		registerStatus = new RegisterStatus();

		// instialize ROB
		rob = new ROB(ROBSize);

		// intialize reservationStationTable
		reservationsStationTable = new ReservationsStationTable(
				reservationStationsNames);

	}

	public void runInstruction(InstructionSetArchitecture instruction,
			int robNum, String reservationStationName) {
		// issuing
		// check if the instruction is issued
		if (programState.getProgramStateTableEntry(
				instruction.getInstructionNumber()).getIssued() != 0) {

		} else {
			// issuing

		}

	}

	public void issueInstruction(InstructionSetArchitecture instruction,
			String reservationStationName) {

		// checking if there are available reservation station and rob entry
		ReservationStationEntry reservationStation = reservationsStationTable
				.getReservationStationEntry(reservationStationName);
		if (rob.isFull() || reservationStation.isBusy()) {
			System.out
					.println("No availabe an rob free entry  and/or availabe reservation station");
			return;
		}

		// insert into ROB after we made sure that there is an empty ROB
		// entry
		// this line insert instruction and destination into rob entry and
		// return rob entry number
		int robEntryNum = rob.insertToROB(instruction);

		// insert into reservation station
		reservationStation.setBusy(true);
		reservationStation.setOp(instruction.getOperation());
		


		// check if the instuciton have destination register
		if (instruction.getDestinationRegister() != null) {
			reservationStation.setDestination(robEntryNum);
			
			//insert into registerStatus file the ROB entry number of destination register
			registerStatus.insertIntoRegisterStatusTable(instruction.getDestinationRegister(), robEntryNum);
			
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
		// TODO
//		ProgramStateEntry programStateEntry  = programState.getProgramStateTableEntry(instruction.getInstructionNumber());
//		programStateEntry.setIssued();
		
	}
	
	
	
	public void executeInstruction(InstructionSetArchitecture instruction){
		
	}
	
	

	public ProgramState getProgramState() {
		return programState;
	}

	public RegisterStatus getRegisterStatus() {
		return registerStatus;
	}
	
	public ReservationsStationTable getReservationsStationTable() {
		return reservationsStationTable;
	}

	public ROB getRob() {
		return rob;
	}

	public static void main(String[] args) {
			
		String [] reservationStationNames = {"load1","load2","store1","add1","add2","add3","add4","mult"};
		
		InstructionSetArchitecture[] instructions = new InstructionSetArchitecture[9];
		instructions[0] = new LoadInstruction(RegisterEnum.R2, 1, RegisterEnum.R1,(short) 20);

		instructions[1] = new MulInstruction(RegisterEnum.R6,2, RegisterEnum.R2,
				RegisterEnum.R4);

		instructions[2] = new StoreInstruction(RegisterEnum.R4, 3, RegisterEnum.R5, (short)30);

		instructions[3] = new BEQInstruction(RegisterEnum.R3, 4, RegisterEnum.R7, (short)50);
		instructions[4] = new JMPInstruction(RegisterEnum.R3, 5, (short)70);
		instructions[5] = new RETInstruction(RegisterEnum.R1, 6);
		instructions[6] = new JALRInstruction(RegisterEnum.R4, 7, RegisterEnum.R5);
		instructions[7] = new MulInstruction(RegisterEnum.R7,2, RegisterEnum.R4,
				RegisterEnum.R3);
		instructions[8] = new LoadInstruction(RegisterEnum.R2, 1, RegisterEnum.R6,(short) 20);
		
		TomasuloProcessor tomasuloProcessor = new TomasuloProcessor(instructions, 8, reservationStationNames);
		
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R1,(short)20);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R2,(short)200);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R3,(short)240);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R4,(short)290);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R5,(short)522);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R6,(short)2022);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R7,(short)903);
		
		
		tomasuloProcessor.issueInstruction(instructions[0],"load1");
		tomasuloProcessor.issueInstruction(instructions[1],"mult");
		tomasuloProcessor.issueInstruction(instructions[2],"store1");
		tomasuloProcessor.issueInstruction(instructions[3],"add1");
		tomasuloProcessor.issueInstruction(instructions[4],"add2");
		tomasuloProcessor.issueInstruction(instructions[5],"add3");
		tomasuloProcessor.issueInstruction(instructions[6],"add4");
		tomasuloProcessor.issueInstruction(instructions[7],"mult"); // this will not be issued because no available reservation station
		tomasuloProcessor.issueInstruction(instructions[8],"load2"); // this will update the register status R2 with last ROB entry (Renaming)
		
		System.out.println("------------------------- Program State ---------------------------");
		tomasuloProcessor.getProgramState().printProgramStateTable();
		System.out.println("------------------------- Reservation Station ---------------------------");
		tomasuloProcessor.getReservationsStationTable().printReservationStationsTable();
		System.out.println("------------------------- Register Status ---------------------------");
		tomasuloProcessor.getRegisterStatus().printRegisterStatus();
		System.out.println("------------------------- ROB ---------------------------");
		tomasuloProcessor.getRob().printROB();
	}
}
