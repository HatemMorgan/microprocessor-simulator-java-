package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class LoadInstruction extends InstructionSetArchitecture {
	String immediateValue ;
	public LoadInstruction(Operation operation, RegisterEnum destinationRegister,
			RegisterEnum sourceOneRegister, String immidiateValue) {
		
		super(operation, destinationRegister, sourceOneRegister, null);
		this.immediateValue = immidiateValue;
	}

	@Override
	public String execute() {
		
		String[] operands = super.loadDataFromRegisters();
		
		// call ADDI function and pass  operand and immidiate value to it and it will return the address as the result
		
		//  pass address to load function in the memory that will return the result
		
		// store returned result to destination register
		super.storeResultIntoDest(null);
		
		return null;
	}
	
}
