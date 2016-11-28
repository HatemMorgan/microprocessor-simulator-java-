package memory;

public class DataMemory{
	
	private static DataMemory dataMemory;
	public Memory memory;

	public static void main(String[] args) throws InterruptedException {
		Clock c = new Clock();
//		c.start();
		DataMemory m = new DataMemory(1, 4, c, writeHitPolicy.writeThrough, writeMissPolicy.writeAround);
		m.store((short) 255, (short)200);
		short result = m.load((short)255);
//		short result = m.load((short)265);
		
		System.out.println(result);
	}
	
	public synchronized static DataMemory getInstance(){
		return dataMemory;
	}
	
	public static void init(int cacheLevels, int mainMemoryAccessTimeInCycles,  Clock clock, writeHitPolicy hitPolicy, writeMissPolicy missPolicy) {
		dataMemory = new DataMemory(cacheLevels, mainMemoryAccessTimeInCycles, clock, hitPolicy, missPolicy);
	}
	
	private DataMemory(int cacheLevels, int mainMemoryAccessTimeInCycles,  Clock clock, writeHitPolicy hitPolicy, writeMissPolicy missPolicy) {

		this.memory = new Memory(cacheLevels, mainMemoryAccessTimeInCycles, clock, hitPolicy, missPolicy);
	}
	
	
	

	public synchronized Short load(Short address) {
		int byteAddress = address;
		String result = this.memory.load(byteAddress);
		Short answer = 0;
		try{
		answer = Short.parseShort(result);
		}catch(NumberFormatException e){
			return 0;
		}
		return answer;
	}

	public synchronized void store(Short address, Short value) {
		String val = value + "";
		int byteAddress = address;
		this.memory.store(byteAddress, val);

	}
}

