package memory;
import java.util.Arrays;

public class MainMemory {
	//64KB memory
	String[][] memory;
	int accessTimeInCycles = 0;
	Clock clock;
	boolean busy = false;
	int blockSize = 1;
	
	public MainMemory(int accessTimeInCycles, Clock clock, int blockSize) {
		//blockSize represents bytes per memory line
		this.accessTimeInCycles = accessTimeInCycles;
		this.clock = clock;
		this.blockSize = blockSize;
		this.memory = new String[65535/blockSize][blockSize];
	}
	
	
	public String toString(){
		for (int i = 0; i < memory.length; i++) {
			System.out.println("Line " + i + ": " + Arrays.toString(memory[i]));
		}
		return "";
	}
	
	public void storeInstruction(String instruction, int address){
		memory[address/blockSize][address%blockSize] = instruction;
		System.out.println("Inserted " + instruction + " in main successfully!");
	}
	
	
	public void store(String value, int address){
		//value is the binary representation of the value in string format
		//address can be hexadecimal or decimal or even binary
		
		//wait till previous operation is finished
		while(busy);
		busy = true;

		//determine at which clock cycle the store will end
		int clockCycleToReturnAt = clock.counter.get() + accessTimeInCycles;
		
		System.out.println("Store will finish in clock cycle " + clockCycleToReturnAt);

		//wait until memory access time is over
		while(clock.counter.get() < clockCycleToReturnAt);
		
		//update memory
		memory[address/blockSize][address%blockSize] = value;
		System.out.println("Store finished, clock cycle: "+clock.counter.get());
		busy = false;
	}
	
	public String load(int address){
		//address can be hexadecimal or decimal or even binary
		
		//wait till previous operation is finished
		while(busy);
		busy = true;
		//determine at which clock cycle the load will end
		int clockCycleToReturnAt = clock.counter.get() + accessTimeInCycles;
		
		System.out.println("Load will finish in clock cycle " + clockCycleToReturnAt);

		//wait until memory access time is over
		while(clock.counter.get() < clockCycleToReturnAt);
		
		//fetch from memory
		System.out.println("Load finished, clock cycle: "+clock.counter.get());
		busy = false;
		
		return memory[address/blockSize][address%blockSize];
		
	}
	
	
	
	public static void main(String[] args) {
		Clock c = new Clock();
		c.start();
		MainMemory m = new MainMemory(4, c, 1);
		m.store("sayegh", 0xff);
		String value = m.load(255);
		System.out.println(value);
	}
	
	
}
