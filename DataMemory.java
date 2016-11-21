
public class DataMemory extends Memory{

	public DataMemory(int cacheLevels, int mainMemoryAccessTimeInCycles, Clock clock, writeHitPolicy hitPolicy,
			writeMissPolicy missPolicy) {
		super(cacheLevels, mainMemoryAccessTimeInCycles, clock, hitPolicy, missPolicy);
	}

}
