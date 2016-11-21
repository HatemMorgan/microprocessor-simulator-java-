
public class Memory {
	Cache[] caches;
	MainMemory main;
	Clock clock;
	int cacheLevels;
	
	//back + allocate
	//through + around
	public static writeHitPolicy hitPolicy = writeHitPolicy.writeThrough;
	public static writeMissPolicy missPolicy = writeMissPolicy.writeAround;
	
	public Memory(int cacheLevels, int mainMemoryAccessTimeInCycles,  Clock clock, writeHitPolicy hitPolicy, writeMissPolicy missPolicy) {
		this.cacheLevels = cacheLevels;
		this.caches = new Cache[cacheLevels];
		this.clock = clock;
		if(hitPolicy != null)
			Memory.hitPolicy = hitPolicy;
		if(missPolicy != null)
			Memory.missPolicy = missPolicy;
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
		String res=loadHelper(byteAddress, 0);
		System.out.println(res);
		return res;

	}

	
	String loadHelper(int byteAddress, int i){
		CacheEntry result = null;
		if(i>= caches.length){
			String mainMemoryResult =  main.load(byteAddress);
			return mainMemoryResult;
		}

		//searching the cache
		result = caches[i].searchCache(byteAddress);
		//Read hit
		if(result !=null){
			return result.data;
		}
		
		String returnValue = "nothing";
		
		
//		Read miss
		if(missPolicy == writeMissPolicy.writeAround){
//			no allocate
			
			//get value from lower memory
			returnValue =  readAround(byteAddress, i);
			//write the value to this cache level
			storeHelper(byteAddress, returnValue, i);
			return returnValue;		

		}else{
//			allocate
			returnValue =  readAllocate(byteAddress, i);
			return returnValue;		

		}		
	}
	String readAround(int byteAddress, int i) {
		return loadHelper(byteAddress, i+1);
	}

	String readAllocate(int byteAddress, int i) {
		if(i>= caches.length){
			String mainMemoryResult =  main.load(byteAddress);
			return mainMemoryResult;
		}

		CacheEntry replacement = null;
		replacement = caches[i].locateReplacementBlock(byteAddress);
		
		if(replacement!=null && replacement.dirty){
			storeHelper(byteAddress, replacement.data, i+1);
		}

		String data = loadHelper(byteAddress, i+1);
		caches[i].insertIntoCache(byteAddress, data, false);
		
		return data;
	}

	void writeAllocate(int byteAddress, String value, int i){
		//BLOCK NOT FOUND
		if(i>= caches.length){
			main.store(value, byteAddress);
			return;
		}
		
		//fetch the block from lower memories and place it into this cache level
		loadHelper(byteAddress, i+1);
		
		//insert value into cache and set dirty bit
		caches[i].insertIntoCache(byteAddress, value, true);			
	}
	
	//DONE
	void writeBack(int byteAddress, String value, int i, CacheEntry result){
		
		
		if(i>= caches.length){
			main.store(value, byteAddress);
			return;
		}
     
		//block is dirty, write it back to lower memory
		if(result.dirty){
			storeHelper(byteAddress, result.data, i+1);
		}
		
		//block now isn't dirty, replace it
		caches[i].insertIntoCache(byteAddress, value, true);			
	}

	
	
	//DONE
	void writeAround(int byteAddress, String value, int i){
		//only write to main
		main.store(value, byteAddress);
	}

	
	//DONE
	void writeThrough(int byteAddress, String value, int i){
		//Write to caches and main
		for (int j = 0; j < caches.length; j++) {
			caches[i].insertIntoCache(byteAddress, value, false);			
		}
		main.store(value, byteAddress);

	}

	void store(int byteAddress, String value){
		storeHelper(byteAddress,value, 0);
	}
	
	void storeHelper(int byteAddress, String value, int i){
		if(i>= caches.length){
			return;
		}
		CacheEntry result = null;
		Cache topCache = caches[i];
		//updating the caches
			result = topCache.searchCache(byteAddress);
			//WRITE MISS
			if(result == null){				
				if(missPolicy == writeMissPolicy.writeAround){
					writeAround(byteAddress, value, i);
				}else{
					writeAllocate(byteAddress, value, i);
				}
			}else{
				//WRITE HIT
				if(hitPolicy == writeHitPolicy.writeThrough){
					writeThrough(byteAddress, value, i);
				}else{
					writeBack(byteAddress, value, i, result);
					
				}
			}
		}
		
	

	
	
	public static void main(String[] args) {
		Clock c = new Clock();
		c.start();
		Memory m = new Memory(2, 1, c, writeHitPolicy.writeThrough, writeMissPolicy.writeAllocate);
		m.store(212, "sayegh");
		String res = m.load(212);
		System.out.println(res);
	}
}
