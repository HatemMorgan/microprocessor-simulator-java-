package tomasulo;

import instructionSetArchitecture.InstructionSetArchitecture;
import programState.ProgramState;
import registerStatus.RegisterStatus;
import registers.Register;
import registers.RegisterEnum;
import registers.RegisterFile;
import reorderBuffer.ROB;
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
			int robNum, String reservationStationName) {
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
		reservationStation.setDestination(robEntryNum);

		// All instruction has RS which is the first source operand
		// check if RS is busy (waiting for another instruction to write on
		RegisterEnum rs = instruction.getSourceOneRegister();
		if (registerStatus.isBusy(rs)) {
			// get ROB number of the ROB entry that will write on RS after
			// Committing
			Integer ROBNum = registerStatus.getROBNum(instruction
					.getSourceOneRegister());

			// check if entry with ROBNum is ready which means that the
			// value of
			// the result is available
			if (rob.IsReady(robNum)) {
				reservationStation.setVj(rob.getValue(robNum));
				reservationStation.setQj(null);
			} else {
				// set Qj to rob entry number
				reservationStation.setQj(robEntryNum);
			}

		} else {
			// if Rs is available so we will load the operand from it and
			// add it to vj in reservation station
			short rsValue = RegisterFile.getInstance().loadDataFromRegister(rs);
			reservationStation.setVj(rsValue);

		}

		// instructions with 2 source operands  eg: ADD , SUB , MULT , NAND
		if (instruction.getSourceTwoRegister() != null) {
			RegisterEnum rt = instruction.getSourceOneRegister();
			if (registerStatus.isBusy(rt)) {
				// get ROB number of the ROB entry that will write on RT after
				// Committing
				Integer ROBNum = registerStatus.getROBNum(instruction
						.getSourceTwoRegister());

				// check if entry with ROBNum is ready which means that the
				// value of
				// the result is available
				if (rob.IsReady(robNum)) {
					reservationStation.setVk(rob.getValue(robNum));
					reservationStation.setQk(null);
				} else {
					// set Qj to rob entry number
					reservationStation.setQk(robEntryNum);
				}

			} else {
				// if Rs is available so we will load the operand from it and
				// add it to vj in reservation station
				short rtValue = RegisterFile.getInstance()
						.loadDataFromRegister(rt);
				reservationStation.setVj(rtValue);

			}

		}
		
		// instruction with immeditate  value as the second operand of instructon eg: LW,SW,BEQ and ADDI
		
		
		

	}

}
