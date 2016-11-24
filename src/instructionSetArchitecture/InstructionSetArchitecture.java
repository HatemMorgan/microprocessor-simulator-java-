package instructionSetArchitecture;

import registers.Register;
import reservationStations.Operation;

public abstract class InstructionSetArchitecture {
	private Operation operation ;
	private Register destinationRegister;
	private Register sourceOneRegister;
	private Register sourceTwoRegister;
	
	
	public InstructionSetArchitecture(Operation operation,
			Register destinationRegister, Register sourceOneRegister,
			Register sourceTwoRegister) {

		this.operation = operation;
		this.destinationRegister = destinationRegister;
		this.sourceOneRegister = sourceOneRegister;
		this.sourceTwoRegister = sourceTwoRegister;
	}
	
	
	public abstract String execute();
	
	public String[] loadDataFromRegisters (){
		//TODO get data from source operands
	return null;	
	}
	
	public void storeResultIntoDest(String result){
		//TODO store data to destination registers
	}
	
	
	
}
