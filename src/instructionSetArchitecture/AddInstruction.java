package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class AddInstruction extends InstructionSetArchitecture {

	public AddInstruction(Operation operation, RegisterEnum destinationRegister,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {
		
		super(operation, destinationRegister, sourceOneRegister, sourceTwoRegister);
	}

	@Override
	public String execute() {
		String[] operands = super.loadDataFromRegisters();
		
		// call ADD function and pass operands to it and it will return  the result to be store in dest reg
		
		// store returned result to destination register
		super.storeResultIntoDest(null);
		
		return null;
	}

}
