package instructionSetArchitecture;

import registers.Register;
import reservationStations.Operation;

public class SubIntstruction extends InstructionSetArchitecture {

	public SubIntstruction(Operation operation, Register destinationRegister,
			Register sourceOneRegister, Register sourceTwoRegister) {
		
		super(operation, destinationRegister, sourceOneRegister, sourceTwoRegister);
	}

	@Override
	public String execute() {
		String[] operands = super.loadDataFromRegisters();
		
		// call SUB function and pass operands to it and it will return  the result to be store in dest reg
		
		// store returned result to destination register
		super.storeResultIntoDest(null);
		
		return null;
	}

}
