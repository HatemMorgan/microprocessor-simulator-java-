package programState;

import instructionSetArchitecture.*;

import java.util.ArrayList;

import registers.RegisterEnum;

public class ProgramState {
	private ArrayList<ProgramStateEntry> programStateTable;

	public ProgramState() {
		programStateTable = new ArrayList<>();
	}

	public void addInstructionsToProgramStateTable(
			InstructionSetArchitecture[] instructions) {
		for (int i = 0; i < instructions.length; i++) {
			ProgramStateEntry newEntry = new ProgramStateEntry(instructions[i]);
			programStateTable.add(newEntry);
		}
	}

	public void printProgramStateTable() {
		for (int i = 0; i < programStateTable.size(); i++) {
			System.out.println(programStateTable.get(i).toString());
		}
	}

	public static void main(String[] args) {

		InstructionSetArchitecture[] instructions = new InstructionSetArchitecture[2];
		instructions[0] = new AddInstruction(RegisterEnum.R2, RegisterEnum.R3,
				RegisterEnum.R4);

		instructions[1] = new MulInstruction(RegisterEnum.R6, RegisterEnum.R1,
				RegisterEnum.R4);

		ProgramState programState = new ProgramState();

		programState.addInstructionsToProgramStateTable(instructions);
		programState.printProgramStateTable();
	}

}
