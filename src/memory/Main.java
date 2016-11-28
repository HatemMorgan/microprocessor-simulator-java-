package memory;

import java.io.PrintStream;

public class Main {
	Clock clock;
	Memory memory;

	public static void main(String[] args) throws InterruptedException {
		


	}

	public Main(int cacheLevels, int mainMemoryAccessTimeInCycles, Clock clock, writeHitPolicy hitPolicy,
			writeMissPolicy missPolicy) {

		this.memory = new Memory(cacheLevels, mainMemoryAccessTimeInCycles, clock, hitPolicy, missPolicy);
	}

	public void load(Short address) {

	}

	public void store(Short address, Short value) {

	}
}
