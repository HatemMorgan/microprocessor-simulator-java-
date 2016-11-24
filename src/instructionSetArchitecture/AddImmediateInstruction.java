package instructionSetArchitecture;

import registers.Register;
import reservationStations.Operation;

public class AddImmediateInstruction extends InstructionSetArchitecture {
	
	String immediateValue ;
	public AddImmediateInstruction(Operation operation, Register destinationRegister,
			Register sourceOneRegister, String immidiateValue) {
		
		super(operation, destinationRegister, sourceOneRegister, null);
		this.immediateValue = immidiateValue;
	}

	@Override
	public String execute() {
		
		String[] operands = super.loadDataFromRegisters();
		
		// call ADDI function and pass operand and immediateValue to it and it will return the address as the result
		
		// store returned result to destination register
		super.storeResultIntoDest(null);
		return null;
	}

}
