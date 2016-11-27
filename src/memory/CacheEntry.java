package memory;
import instructionSetArchitecture.*;

public class CacheEntry {
	public int tag;

	public InstructionSetArchitecture instructionData;

	public Short data;

	public boolean valid = false;
	public boolean dirty = false;
	
	public CacheEntry(int tag, Short data, boolean valid, boolean dirty) {
		this.tag = tag;
		this.data = data;
		this.valid = valid;
		this.dirty = dirty;
	}
	
	public CacheEntry(int tag, InstructionSetArchitecture data, boolean valid, boolean dirty) {
		this.tag = tag;
		this.instructionData = data;
		this.valid = valid;
		this.dirty = dirty;
	}


	public CacheEntry() {
		// TODO Auto-generated constructor stub
	}
	
	public String toString(){
		return data+"";
		
	}
}
