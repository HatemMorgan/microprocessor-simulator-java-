package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class AddInstruction extends InstructionSetArchitecture {

	public AddInstruction(RegisterEnum destinationRegister,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {
		
		super(Operation.ADD, destinationRegister, sourceOneRegister, sourceTwoRegister);
	}

	@Override
	public Short execute() {
		Short[] operands = super.loadDataFromRegisters();
		
		// call ADD function and pass operands to it and it will return  the result to be store in dest reg
		Short result = adderFU.add(operands[0], operands[1]);
		
		return result;
	}

}
