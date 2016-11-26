package reorderBuffer;

import instructionSetArchitecture.InstructionSetArchitecture;
import registers.RegisterEnum;
import reservationStations.Operation;



public class ROBEntry {
	private int entryNumber ;
	private InstructionSetArchitecture type ;
	private RegisterEnum destination;
	private Short value;
	private boolean ready ;
	
	public ROBEntry next ;
	
	public ROBEntry(int entryNumber, InstructionSetArchitecture type,
			RegisterEnum destination, Short value, boolean ready) {
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



	public InstructionSetArchitecture getType() {
		return type;
	}

	public void setType(InstructionSetArchitecture type) {
		this.type = type;
	}

	public RegisterEnum getDestination() {
		return destination;
	}

	public void setDestination(RegisterEnum destination) {
		this.destination = destination;
	}

	public Short getValue() {
		return value;
	}

	public void setValue(Short value) {
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


