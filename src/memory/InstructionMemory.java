package memory;

import registers.Register;

public class InstructionMemory extends Memory {

	private Short PC;

	public InstructionMemory(int cacheLevels, int mainMemoryAccessTimeInCycles,
			Clock clock, writeHitPolicy hitPolicy, writeMissPolicy missPolicy) {
		super(cacheLevels, mainMemoryAccessTimeInCycles, clock, hitPolicy,
				missPolicy);
	}

	// method that ensures instructions are fetched all at once
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

	// gets number of instructions to read and the start address of the program
	// returns an array of strings containing the instructions
	public String[] readInstructions(int instructionsToFetch) {
		String[] result = new String[instructionsToFetch];
		decreaseClockCycles(instructionsToFetch);
		for (int i = 0; i < instructionsToFetch; i++) {
			result[i] = load(getPC());
			incrementPC();
		}
		// resetClockCycles(instructionsToFetch);

		return result;
	}

	// gets an array of instructions to place into memory and the start address
	// of the program
	// places the instructions sequentially in the instruction memory
	public void placeInstructionsInMemory(
			String[] instructions) {
		/*decreaseClockCycles(instructions.length); //Why is this here? Placing instructions in memory is independent of the clock
		for (int i = 0; i < instructions.length; i++) {
			this.store(getPC(), instructions[i]);
			setPC((short) (getPC()+1));
		}*/
		this.storeInstructions(instructions);
		// resetClockCycles(instructions.length);
	}

	public Short getPC() {
		return PC;
	}

	public void setPC(Short pC) {
		PC = pC;
	}

	public void incrementPC() {
		PC++;
	}
	
	public static void main(String []args){
		Clock c = new Clock();
		InstructionMemory im = new InstructionMemory(2, 10, c, writeHitPolicy.writeBack, writeMissPolicy.writeAllocate);
		im.setPC((short)0);
		String[] instructions = {"LW 1, 2, 0", "ADD 5, 2, 1", "SUB 4, 2, 3", "MUL 2, 4, 5"};
		im.placeInstructionsInMemory(instructions); // storing instructions in memory is independent of the 
		//System.out.println(im.getPC());
		c.start();
		System.out.println(im.getPC());
		String test = im.load(0);
		String test2 = im.load(1);
		String test3 = im.load(2);
		String test4 = im.load(3);
		String test5 = im.load(4);
		System.out.println(test + " " + test2 + " " + test3 + " " + test4);
	}
	
	
	
}
