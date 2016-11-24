package programState;

import instructionSetArchitecture.InstructionSetArchitecture;

public class ProgramStateEntry {
	
	private InstructionSetArchitecture instruction ;
	// the int value represents the number of the clock cycle when instruction state finished
	private int issued ;
	private int executed;
	private int written;
	private int committed;

	public ProgramStateEntry(InstructionSetArchitecture instruction){
		this.instruction = instruction;
	}
	
	public InstructionSetArchitecture getInstruction() {
		return instruction;
	}
	public void setInstruction(InstructionSetArchitecture instruction) {
		this.instruction = instruction;
	}
	public int getIssued() {
		return issued;
	}
	public void setIssued(int issued) {
		this.issued = issued;
	}
	public int getExecuted() {
		return executed;
	}
	public void setExecuted(int executed) {
		this.executed = executed;
	}
	public int getWritten() {
		return written;
	}
	public void setWritten(int written) {
		this.written = written;
	}
	public int getCommitted() {
		return committed;
	}
	public void setCommitted(int committed) {
		this.committed = committed;
	}

	@Override
	public String toString() {
		return "ProgramStateEntry [instruction=" + instruction.toString() + ", issued="
				+ issued + ", executed=" + executed + ", written=" + written
				+ ", committed=" + committed + "]";
	}
	
	
	
}
