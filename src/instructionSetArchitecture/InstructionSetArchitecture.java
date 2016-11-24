package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public abstract class InstructionSetArchitecture {
	private Operation operation ;
	private RegisterEnum destinationRegister;
	private RegisterEnum sourceOneRegister;
	private RegisterEnum sourceTwoRegister;
	
	
	public InstructionSetArchitecture(Operation operation,
			RegisterEnum destinationRegister, RegisterEnum sourceOneRegister,
			RegisterEnum sourceTwoRegister) {

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
