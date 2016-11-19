import java.util.Arrays;

public class Memory {
	Cache[] caches;
	MainMemory main;
	Clock clock;
	int cacheLevels;
	
	//back + allocate
	//through + around
	writeHitPolicy hitPolicy = writeHitPolicy.writeThrough;
	writeMissPolicy missPolicy = writeMissPolicy.writeAround;
	
	public Memory(int cacheLevels, int mainMemoryAccessTimeInCycles,  Clock clock, writeHitPolicy hitPolicy, writeMissPolicy missPolicy) {
		this.cacheLevels = cacheLevels;
		this.caches = new Cache[cacheLevels];
		this.clock = clock;
		if(hitPolicy != null)
			this.hitPolicy = hitPolicy;
		if(missPolicy != null)
			this.missPolicy = missPolicy;
		this.main = new MainMemory(mainMemoryAccessTimeInCycles, clock, 3);
		init();
	}
	
	void init(){
		for (int i = 0; i < cacheLevels; i++) {
			caches[i] = new Cache(16, 1, 1, 1, this.clock);
		}
	}
	
	
	public String toString(){
		for (int i = 0; i < cacheLevels; i++) {
			System.out.println("Cache" + i);
			caches[i].toString();
		}		
		return "";
	}
	
	
	
	String load(int byteAddress){
		CacheEntry result = null;
		
		//searching the caches
		for (int i = 0; i < caches.length; i++) {
			result = caches[i].searchCache(byteAddress);
			if(result != null){
				return result.data;
			}
		}
		
		
		//if caches don't find it, search main memory
		String mainMemoryResult =  main.load(byteAddress);
		
		//store into the caches
		for (int i = 0; i < caches.length; i++) {
			caches[i].insertIntoCache(byteAddress, mainMemoryResult);
		}
		
		return mainMemoryResult;
		
	}
	
	
	void store(int byteAddress, String value){
		main.store(value, byteAddress);

		//updating the caches
		for (int i = 0; i < caches.length; i++) {
			caches[i].insertIntoCache(byteAddress, value);
		}
		
	}

	
	
	public static void main(String[] args) {
		Clock c = new Clock();
		c.start();
		Memory m = new Memory(2, 2, c, writeHitPolicy.writeThrough, writeMissPolicy.writeAround);
		m.store(212, "sayegh");
		String res = m.load(212);
		System.out.println(res);
	}
}
