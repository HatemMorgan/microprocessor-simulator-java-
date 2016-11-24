package instructionSetArchitecture;

import registers.Register;
import reservationStations.Operation;

public class MulInstruction extends InstructionSetArchitecture {

	public MulInstruction(Operation operation, Register destinationRegister,
			Register sourceOneRegister, Register sourceTwoRegister) {
		
		super(operation, destinationRegister, sourceOneRegister, sourceTwoRegister);
	}

	@Override
	public String execute() {
		String[] operands = super.loadDataFromRegisters();
		
		// call Mul function and pass operands to it and it will return  the result to be store in dest reg
		
		// store returned result to destination register
		super.storeResultIntoDest(null);
		
		return null;
	}

}
