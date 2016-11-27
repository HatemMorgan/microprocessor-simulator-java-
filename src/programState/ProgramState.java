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

	public InstructionSetArchitecture getInstruction(int position){
		if (programStateTable.size() >= position){
			System.out.println("No instruction with this position");
			return null;
		}
		
		return programStateTable.get(position).getInstruction();
		
	}
	
	public ProgramStateEntry getProgramStateTableEntry(int position){
		if (programStateTable.size() <= position-1){
			System.out.println("No instruction with this position");
			return null;
		}
		
		return programStateTable.get(position-1);
		
	}
	
	
	public static void main(String[] args) {

		InstructionSetArchitecture[] instructions = new InstructionSetArchitecture[2];
		instructions[0] = new AddInstruction(RegisterEnum.R2,1, RegisterEnum.R3,
				RegisterEnum.R4);

		instructions[1] = new MulInstruction(RegisterEnum.R6,2, RegisterEnum.R1,
				RegisterEnum.R4);

		ProgramState programState = new ProgramState();

		programState.addInstructionsToProgramStateTable(instructions);
		programState.printProgramStateTable();
	}

}
