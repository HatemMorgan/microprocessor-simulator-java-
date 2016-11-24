package reorderBuffer;

import registers.RegisterEnum;



public class ROBEntry {
	private int entryNumber ;
	private ROBEntryType type ;
	private RegisterEnum destination;
	private String value;
	private boolean ready ;
	
	public ROBEntry next ;
	
	public ROBEntry(int entryNumber, ROBEntryType type,
			RegisterEnum destination, String value, boolean ready) {
		this.entryNumber = entryNumber;
		this.type = type;
		this.destination = destination;
		this.value = value;
		this.ready = ready;
		this.next =null ;
	}

	
	
	public int getEntryNumber() {
		return entryNumber;
	}



	public ROBEntryType getType() {
		return type;
	}

	public void setType(ROBEntryType type) {
		this.type = type;
	}

	public RegisterEnum getDestination() {
		return destination;
	}

	public void setDestination(RegisterEnum destination) {
		this.destination = destination;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}



	@Override
	public String toString() {
		return "ROBEntry [entryNumber=" + entryNumber + ", type=" + type
				+ ", destination=" + destination + ", value=" + value
				+ ", ready=" + ready + "]";
	}
	
	
	
	
	
}


