package instructionSetArchitecture;

import registers.Register;
import reservationStations.Operation;

public class BEQInstruction extends InstructionSetArchitecture {

	public BEQInstruction(Operation operation, Register destinationRegister,
			Register sourceOneRegister, Register sourceTwoRegister) {
		
		super(operation, destinationRegister, sourceOneRegister, sourceTwoRegister);
	}

	@Override
	public String execute() {
		String[] operands = super.loadDataFromRegisters();
		
		// call subtract method of adder and pass operands to it and it will return result
		
		// check if result==0 so we will branch 
		
		return null;
	}

}
