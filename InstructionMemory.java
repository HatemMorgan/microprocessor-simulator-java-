
public class InstructionMemory extends Memory {

	public InstructionMemory(int cacheLevels, int mainMemoryAccessTimeInCycles, Clock clock, writeHitPolicy hitPolicy,
			writeMissPolicy missPolicy) {
		super(cacheLevels, mainMemoryAccessTimeInCycles, clock, hitPolicy, missPolicy);
	}

	//method that ensures instructions are fetched all at once
	private void decreaseClockCycles(int instructionsToFetch) {
		for (int i = 0; i < caches.length; i++) {
			caches[i].accessTimeInCycles /= instructionsToFetch;
		}
	}

	@SuppressWarnings("unused")
	private void resetClockCycles(int instructionsToFetch) {
		for (int i = 0; i < caches.length; i++) {
			caches[i].accessTimeInCycles *= instructionsToFetch;
		}
	}
//gets number of instructions to read and the start address of the program
	//returns an array of strings containing the instructions
	public String[] readInstructions(int instructionsToFetch, int startAddress) {
		String[] result = new String[instructionsToFetch];
		decreaseClockCycles(instructionsToFetch);
		for (int i = 0; i < instructionsToFetch; i++) {
			result[i] = load(startAddress);
			startAddress++;
		}
//		resetClockCycles(instructionsToFetch);
		
		return result;
	}

	//gets an array of instructions to place into memory and the start address of the program
	//places the instructions sequentially in the instruction memory
	public void placeInstructionsInMemory(int startAddress, String[] instructions) {
		decreaseClockCycles(instructions.length);
		for (int i = 0; i < instructions.length; i++) {
			this.store(startAddress, instructions[i]);
			startAddress++;
		}
//		resetClockCycles(instructions.length);
	}

}
