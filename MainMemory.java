
public class MainMemory {
	//64KB memory
	String[] memory = new String[65535];
	int accessTimeInCycles = 0;
	Clock clock;
	boolean busy = false;
	
	public MainMemory(int accessTimeInCycles, Clock clock) {
		this.accessTimeInCycles = accessTimeInCycles;
		this.clock = clock;
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
		memory[address] = value;
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
		
		return memory[address];
		
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		Clock c = new Clock();
		c.start();
		MainMemory m = new MainMemory(4, c);
		m.store("sayegh", 0xff);
		String value = m.load(255);
		System.out.println(value);
	}
	
	
}
