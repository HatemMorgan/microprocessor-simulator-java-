package reorderBuffer;

public class ROBEntry {
	private int entryNumber ;
	private ROBEntryType type ;
	private ROBEntryDestination destination;
	private String value;
	private boolean ready ;
	
	public ROBEntry(int entryNumber, ROBEntryType type,
			ROBEntryDestination destination, String value, boolean ready) {
		this.entryNumber = entryNumber;
		this.type = type;
		this.destination = destination;
		this.value = value;
		this.ready = ready;
	}

	
	
	public int getEntryNumber() {
		return entryNumber;
	}

	public void setEntryNumber(int entryNumber) {
		this.entryNumber = entryNumber;
	}

	public ROBEntryType getType() {
		return type;
	}

	public void setType(ROBEntryType type) {
		this.type = type;
	}

	public ROBEntryDestination getDestination() {
		return destination;
	}

	public void setDestination(ROBEntryDestination destination) {
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
	
	
	
	
	
}


