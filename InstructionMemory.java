
public class InstructionMemory extends Memory {

	int instructionsToFetch = 1;
	int startAddress = 0;

	public InstructionMemory(int cacheLevels, int mainMemoryAccessTimeInCycles, Clock clock, writeHitPolicy hitPolicy,
			writeMissPolicy missPolicy) {
		super(cacheLevels, mainMemoryAccessTimeInCycles, clock, hitPolicy, missPolicy);
	}

	
	//gets number of instructions to read and the start address of the program
	//returns an array of strings containing the instructions
	public String[] readInstructions(int instructionsToFetch, int startAddress) {
		String[] result = new String[instructionsToFetch];
		
		for (int i = 0; i < instructionsToFetch; i++) {
			result[i] = load(startAddress);
			startAddress++;
		}
		
		return result;
	}

	//gets an array of instructions to place into memory and the start address of the program
	//places the instructions sequentially in the instruction memory
	public void placeInstructionsInMemory(int startAddress, String[] instructions) {
		for (int i = 0; i < instructions.length; i++) {
			this.store(startAddress, instructions[i]);
			startAddress++;
		}
	}

}
