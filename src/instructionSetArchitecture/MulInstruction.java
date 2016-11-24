package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class MulInstruction extends InstructionSetArchitecture {

	public MulInstruction(RegisterEnum destinationRegister,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {

		super(Operation.MUL, destinationRegister, sourceOneRegister,
				sourceTwoRegister);
	}

	@Override
	public Short execute() {
		Short[] operands = super.loadDataFromRegisters();

		// call Mul function and pass operands to it and it will return the
		// result to be store in dest reg
		Short result = multFU.mul(operands[0], operands[1]);

		return result;
	}

}
