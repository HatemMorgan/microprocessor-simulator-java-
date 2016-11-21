
public class CacheEntry {
	public int tag;
	public String data;
	public boolean valid = false;
	public boolean dirty = false;
	
	public CacheEntry(int tag, String data, boolean valid, boolean dirty) {
		this.tag = tag;
		this.data = data;
		this.valid = valid;
		this.dirty = dirty;
	}


	public CacheEntry() {
		// TODO Auto-generated constructor stub
	}
	
	public String toString(){
		return data;
		
	}
}
