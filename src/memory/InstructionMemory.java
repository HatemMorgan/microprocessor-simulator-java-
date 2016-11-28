package memory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

import instructionSetArchitecture.AddInstruction;
import instructionSetArchitecture.InstructionSetArchitecture;
import registers.RegisterEnum;

public class InstructionMemory extends Memory {

	private Short PC;
	private static InstructionMemory instance;

	private InstructionMemory(int cacheLevels,
			int mainMemoryAccessTimeInCycles, Clock clock,
			writeHitPolicy hitPolicy, writeMissPolicy missPolicy) {
		super(cacheLevels, mainMemoryAccessTimeInCycles, clock, hitPolicy,
				missPolicy);

		PC = (short) 0;
	}

	public synchronized static InstructionMemory getInstance() {
		return instance;
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
	public InstructionSetArchitecture[] readInstructions(int instructionsToFetch, int startAddress) {
		InstructionSetArchitecture[] result = new InstructionSetArchitecture[instructionsToFetch];
		decreaseClockCycles(instructionsToFetch);
		for (int i = 0; i < instructionsToFetch; i++) {
			result[i] = decode(load(startAddress));
			startAddress++;
			PC++;
		}
		 resetClockCycles(instructionsToFetch);

		return result;
	}

	public static void main(String[] args) {
		AddInstruction i = new AddInstruction(RegisterEnum.R2, 1, RegisterEnum.R3, RegisterEnum.R4);
//		System.out.println(a.toString());
//		String encoded = encode(a);
//		System.out.println(encoded);
//		InstructionSetArchitecture b = decode(encoded);
//		System.out.println(b);
		Clock c = new Clock();
		c.start();
		InstructionMemory a = new InstructionMemory(1, 4, c, writeHitPolicy.writeThrough, writeMissPolicy.writeAround);
		InstructionSetArchitecture[] ins = new InstructionSetArchitecture[1];
		ins[0] = i;
		a.storeInstructions(ins);
		InstructionSetArchitecture[] aa = a.readInstructions(1, 0);
		System.out.println(aa[0]);
		InstructionSetArchitecture[] ab = a.readInstructions(1, 0);
		System.out.println(ab[0]);
	}
	private static InstructionSetArchitecture decode(String instruction) {
        InstructionSetArchitecture result = null;
		try {
			result = ( InstructionSetArchitecture ) fromString( instruction );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return result;
	}
	private static String encode(InstructionSetArchitecture instruction) {
        try {
			return toString(instruction);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
	
    /** Read the object from Base64 string. */
	private static Object fromString(String s) throws IOException, ClassNotFoundException {
		byte[] data = Base64.getDecoder().decode(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		Object o = ois.readObject();
		ois.close();
		return o;
	}

	/** Write the object to a Base64 string. */
	private static String toString(Serializable o) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(o);
		oos.close();
		return Base64.getEncoder().encodeToString(baos.toByteArray());
	}

	public void storeInstructions(InstructionSetArchitecture []instructions){ 
		for(short i=0; i<instructions.length; ++i){
			String encoded = encode(instructions[i]);
			main.storeInstruction(encoded, i);
			System.out.println("Stored Instruction: " + instructions[i]);
		}
		System.out.println("Stored Instructions Successfully!");
	}


	// gets an array of instructions to place into memory and the start address
	// of the program
	// places the instructions sequentially in the instruction memory
	public void placeInstructionsInMemory(int startAddress, String[] instructions) {
		decreaseClockCycles(instructions.length);
		for (int i = 0; i < instructions.length; i++) {
			this.store(startAddress, instructions[i]);
			startAddress++;
		}
		 resetClockCycles(instructions.length);
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
	public static void init(int cacheLevels, int mainMemoryAccessTimeInCycles,
			Clock clock, writeHitPolicy hitPolicy, writeMissPolicy missPolicy) {

		instance = new InstructionMemory(cacheLevels,
				mainMemoryAccessTimeInCycles, clock, hitPolicy, missPolicy);
	}
	
	


}
