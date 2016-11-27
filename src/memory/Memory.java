package memory;
import  java.util.*;
import java.io.*;

public class Memory {
	Cache[] caches;
	MainMemory main;
	Clock clock;
	int cacheLevels;
	
	//back + allocate
	//through + around
	
	//?? why there is default values for writeHitPolicy and writeMissPolicy
	public static writeHitPolicy hitPolicy = writeHitPolicy.writeThrough;
	public static writeMissPolicy missPolicy = writeMissPolicy.writeAround;

	
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
	
	// ??? caches doesnot have the same size or associativity . it must be customized 
	void init(){
		inputFromUser();
	}
	
	public void inputFromUser(){
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter number of caches");
		int cacheLevel=sc.nextInt();
		int[][] cachestats=new int[cacheLevel][4];
		int cacheSize=0;
		int lineSize=0;
		int associativity=0;
		int accessTime=0;
		for(int i=0;i<cacheLevel;i++){
			System.out.println("Enter cache size for cache level"+ i+1);
			cacheSize=sc.nextInt();
			System.out.println("Enter cache line size for cache level"+ i+1);
			lineSize=sc.nextInt();
			System.out.println("Enter associativity for cache level"+ i+1);
			associativity=sc.nextInt();
			System.out.println("Enter accessTime for cache level"+ i+1);
			accessTime=sc.nextInt();
			caches[i]=new Cache(cacheSize,lineSize,associativity,accessTime,this.clock);
		}
	}
	public String toString(){
		for (int i = 0; i < cacheLevels; i++) {
			System.out.println("Cache" + i);
			caches[i].toString();
		}		
		return "";
	}
		
	
	public Short dataLoad(short byteAddress){

		Short res=dataloadHelper(byteAddress, 0);
		System.out.println(res);
		return res;

	}
	Short dataloadHelper(short byteAddress,int level){
		CacheEntry result = null;
		System.out.println(level);
		if(level>= caches.length){
			Short mainMemoryResult =  main.dataLoad(byteAddress);//fix MainMemory
			return mainMemoryResult;
		}

		//searching the cache
		result = caches[level].searchCache(byteAddress);
		System.out.println("here------>"+result);
		//Read hit
		if(result !=null){
			System.out.println("Found in cache level " + level);
			return result.data;//fix cache memory
		}
		
		Short returnValue =null;
		
		//Read miss
		if(missPolicy == writeMissPolicy.writeAround){
//			no allocate
			
			//get value from lower memory
			returnValue = datareadAround(byteAddress, level);
			//write the value to this cache level
			datastoreHelper(byteAddress, returnValue, level);
			return returnValue;		

		}else{
//			allocate
			returnValue =  datareadAllocate(byteAddress, level);
			return returnValue;		

		}		
	}
	short datareadAround(short byteAddress, int level) {
		return dataloadHelper(byteAddress, level+1);
	}

	Short datareadAllocate(short byteAddress, int level) {
		if(level>= caches.length){
			Short mainMemoryResult =  main.dataLoad(byteAddress);
			return mainMemoryResult;
		}

		CacheEntry replacement = null;
		replacement = caches[level].locateReplacementBlock(byteAddress);
		
		if(replacement!=null && replacement.dirty){
			datastoreHelper(byteAddress, replacement.data, level+1);
		}

		Short data = dataloadHelper(byteAddress, level+1);
		caches[level].insertIntoCache(byteAddress, data, false);//fix cache
		
		return data;
	}

	void datawriteAllocate(short byteAddress, Short value, int level){
		//BLOCK NOT FOUND
		if(level>= caches.length){
			main.dataStore(value, byteAddress);
			return;
		}
		
		//fetch the block from lower memories and place it into this cache level
		dataloadHelper(byteAddress, level+1);
		
		//insert value into cache and set dirty bit
		caches[level].insertIntoCache(byteAddress, value, true);			
	}
	
	//DONE
	void datawriteBack(short byteAddress, Short value, int level, CacheEntry result){
		
		
		if(level>= caches.length){
			main.dataStore(value, byteAddress);
			return;
		}
     
		//block is dirty, write it back to lower memory
		if(result.dirty){
			datastoreHelper(byteAddress, result.data, level+1);
		}
		
		//block now isn't dirty, replace it
		caches[level].insertIntoCache(byteAddress, value, true);			
	}

	
	
	//DONE
	void datawriteAround(short byteAddress, Short value, int level){
		//only write to main
		main.dataStore(value, byteAddress);
	}

	
	//DONE
	void datawriteThrough(Short byteAddress, Short value, int level){
		//Write to caches and main
		for (int j = 0; j < caches.length; j++) {
			caches[level].insertIntoCache(byteAddress,value, false);			
		}
		main.dataStore(value, byteAddress);

	}
	
	
	public void storeInstructions(String []instructions){
		for(int i=0; i<instructions.length; ++i){
			main.storeInstruction(instructions[i], i);
			System.out.println("Stored Instruction: " + instructions[i]);
		}
		System.out.println("Stored Instructions Successfully!");
	}

	public void datastore(short byteAddress, Short value){
		datastoreHelper(byteAddress,value, 0);
	}
	
	void datastoreHelper(short byteAddress, Short value, int level){
		if(level>= caches.length){
			return;
		}
		CacheEntry result = null;
		Cache topCache = caches[level];
		//updating the caches
			result = topCache.searchCache(byteAddress);
			//WRITE MISS
			if(result == null){				
				if(missPolicy == writeMissPolicy.writeAround){
					datawriteAround(byteAddress, value, level);
				}else{
					datawriteAllocate(byteAddress, value, level);
				}
			}else{
				//WRITE HIT
				if(hitPolicy == writeHitPolicy.writeThrough){
					datawriteThrough(byteAddress, value, level);
				}else{
					datawriteBack(byteAddress, value, level, result);
					
				}
			}
		}
		
	

	
	
	public static void main(String[] args) {
		Clock c = new Clock();
		c.start();
		/*Memory m = new Memory(2, 1, c, writeHitPolicy.writeThrough, writeMissPolicy.writeAllocate);
		m.store(212, "sayegh");
		String res = m.load(212);
		System.out.println(res);

		System.out.println("------------------------------------------------");
		String res2 = m.load(212);
		System.out.println("heree");
		
		System.out.println(res2);*/
	}
}
