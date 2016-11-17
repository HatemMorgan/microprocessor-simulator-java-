
public class CacheEntry {
	public int tag;
	public String data;
	public boolean valid = false;
	
	
	public CacheEntry(int tag, String data, boolean valid) {
		this.tag = tag;
		this.data = data;
		this.valid = valid;
	}


	public CacheEntry() {
		// TODO Auto-generated constructor stub
	}
	
	public String toString(){
		return data;
		
	}
}
