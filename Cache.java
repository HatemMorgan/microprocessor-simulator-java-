import java.nio.ByteBuffer;
import java.util.Arrays;

public class Cache {
	int cacheSize;
	int lineSize;
	int associativity;
	int cacheLines;
	int accessTimeInCycles;
	boolean busy = false;

	CacheEntry[][] memory;

	public Cache(int cacheSize, int lineSize, int associativity, int accessTimeInCycles) {
		// cacheSize is assumed to be in bytes
		this.cacheSize = cacheSize;
		this.lineSize = lineSize;
		this.associativity = associativity;
		this.cacheLines = cacheSize / lineSize;
		this.accessTimeInCycles = accessTimeInCycles;
		this.memory = new CacheEntry[cacheLines / associativity][associativity];
	}
	
	public String toString(){
		for (int i = 0; i < memory.length; i++) {
			System.out.println(Arrays.toString(memory[i]));
		}
		return "";
	}

	public void searchCache(int byteAddress){
		
		while(busy);
		int indexBits = log(cacheLines, 2);
		int offsetBits = log(lineSize, 2);
		int tagBits = 16-(indexBits+offsetBits);

		int byteAddressCopy = byteAddress;
		byteAddressCopy >>= offsetBits;
		int index = extractLastNBitsToDecimal(byteAddressCopy, indexBits);
		byteAddressCopy >>= indexBits;
		int tag = extractLastNBitsToDecimal(byteAddressCopy, tagBits);

		CacheEntry[] memorySet = memory[index];
		for (int i = 0; i < memorySet.length; i++) {
			if(memorySet[i] == null){
				break;
			}
			if(memorySet[i].tag == tag){
				System.out.println("Result found");
				return;
			} 
		}
		System.out.println("Result not found, going 1 level deeper");
		
		//fetch data from lower memory in the hierarchy
		insertIntoCache(tag, index, "Fake data");
		
	}
	
	
	void insertIntoCache(int tag, int index, String data){
		while(busy);

		CacheEntry[] memorySet = memory[index];
		for (int i = 0; i < memorySet.length; i++) {
			if(memorySet[i] == null){
				memorySet[i]= new CacheEntry();
			}
			
			if(memorySet[i].valid == false){
				memorySet[i] = new CacheEntry(tag, data, true);
				System.out.println("Inserted new value into cache");
				return;
			}
		}
	}
	
	
	
	public int bitmask(int n){
		return (int) (Math.pow(2, n) -1);
	}
	
	public int extractLastNBitsToDecimal(int integer, int N){
		final ByteBuffer buf = ByteBuffer.allocate(N);
		buf.putInt(integer);
		
		int result = 0;
		for (int i = 0; i < N; i++) {
			int currentBit =  (int) buf.get(i);
			result += currentBit;
		}
		
		result &= bitmask(N);

		return result;
	}

	static int log(int x, int base) {
		return (int) Math.floor(Math.log(x) / Math.log(base));
	}

	public static void main(String[] args) {
		Cache c = new Cache(16, 1, 1, 1);
		c.searchCache(213);
		c.toString();
	}
}
