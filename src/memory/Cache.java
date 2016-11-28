package memory;

import java.util.Arrays;
import java.util.Random;

public class Cache {
	int cacheSize;
	int lineSize;
	int associativity;
	int cacheLines;
	int accessTimeInCycles;
	boolean busy = false;
	Clock clock;

	CacheEntry[][] memory;

	public Cache(int cacheSize, int lineSize, int associativity, int accessTimeInCycles, Clock clock) {
		// cacheSize is assumed to be in bytes
		this.cacheSize = cacheSize;
		this.lineSize = lineSize;
		this.associativity = associativity;
		this.cacheLines = cacheSize / lineSize;
		this.accessTimeInCycles = accessTimeInCycles;
		this.memory = new CacheEntry[cacheLines / associativity][associativity];
		this.clock = clock;
	}

	public String toString() {
		for (int i = 0; i < memory.length; i++) {
			System.out.println(Arrays.toString(memory[i]));
		}
		return "";
	}

	public CacheEntry locateReplacementBlock(int byteAddress){
		int[] addressSegments = decryptAddress(byteAddress);
//		int tag = addressSegments[0];
		int index = addressSegments[1];
//		int offset = addressSegments[2];
			
		CacheEntry[] memorySet = memory[index];
		//TODO: LRU Replacement
		Random r = new Random();
		int rand = r.nextInt(memorySet.length);
		return memorySet[rand];
	}

	public CacheEntry searchCache(int byteAddress) {

		while (busy)
			;
		busy = true;
		// determine at which clock cycle the operation will end
		int clockCycleToReturnAt = clock.counter.get() + accessTimeInCycles;

		System.out.println("Cache access will finish in clock cycle " + clockCycleToReturnAt);

		// wait until memory access time is over
		while (clock.counter.get() < clockCycleToReturnAt)
			;

		int[] addressSegments = decryptAddress(byteAddress);
		int tag = addressSegments[0];
		int index = addressSegments[1];
		// int offset = addressSegments[2];

		CacheEntry[] memorySet = memory[index];
		for (int i = 0; i < memorySet.length; i++) {
			if (memorySet[i] == null) {
				break;
			}
			if (memorySet[i].tag == tag && memorySet[i].valid == true) {
				System.out.println("Result found");
				busy = false;
				return memorySet[i];
			}
		}

		// not found
		System.out.println("Result not found");
		busy = false;
		return null;
	}

	// returns [Tag, Index, Offset]
	int[] decryptAddress(int byteAddress) {
		int indexBits = log(cacheLines, 2);
		int offsetBits = log(lineSize, 2);
		int tagBits = 16 - (indexBits + offsetBits);

		int byteAddressCopy = byteAddress;
		int offset = extractLastNBitsToDecimal(byteAddressCopy, offsetBits);
		byteAddressCopy >>= offsetBits;
		int index = extractLastNBitsToDecimal(byteAddressCopy, indexBits);
		byteAddressCopy >>= indexBits;
		int tag = extractLastNBitsToDecimal(byteAddressCopy, tagBits);

		int[] result = new int[3];
		result[0] = tag;
		result[1] = index;
		result[2] = offset;

		return result;

	}

	void insertIntoCache(int byteAddress, String data, boolean dirty) {
		while (busy)
			;
		busy = true;

		// determine at which clock cycle the operation will end
		int clockCycleToReturnAt = clock.counter.get() + accessTimeInCycles;

		System.out.println("Cache access will finish in clock cycle " + clockCycleToReturnAt);

		// wait until memory access time is over
		while (clock.counter.get() < clockCycleToReturnAt)
			;

		int[] addressSegments = decryptAddress(byteAddress);
		int tag = addressSegments[0];
		int index = addressSegments[1];
		// int offset = addressSegments[2];

		// looks for first invalid entry
		boolean foundInvalidCell = false;
		CacheEntry[] memorySet = memory[index];
		for (int i = 0; i < memorySet.length; i++) {
			if (memorySet[i] == null || memorySet[i].valid == false) {
				memorySet[i] = new CacheEntry(tag, data, true, dirty);
				System.out.println("Inserted new value into cache");
				foundInvalidCell = true;
				break;
			}
		}
		if (!foundInvalidCell) {
			// replace first entry in set
			memorySet[0] = new CacheEntry(tag, data, true, dirty);
		}
		// TODO: LRU Replacement
		busy = false;

	}

	public int bitmask(int n) {
		return (int) (Math.pow(2, n) - 1);
	}

	public int extractLastNBitsToDecimal(int integer, int N) {
		int masked = bitmask(N);
		int res = integer & masked;

		return res;
	}

	static int log(int x, int base) {
		return (int) Math.floor(Math.log(x) / Math.log(base));
	}

	public static void main(String[] args) {
		Clock clock = new Clock();
		clock.start();
		Cache c = new Cache(16, 4, 1, 1, clock);
		c.insertIntoCache(213, "sasa", false);
		c.searchCache(213);
		c.toString();
	}
}
