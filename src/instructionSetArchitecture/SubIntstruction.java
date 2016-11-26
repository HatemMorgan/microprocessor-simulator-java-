package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class SubIntstruction extends InstructionSetArchitecture {

	public SubIntstruction(RegisterEnum destinationRegister, Integer instructionNumber,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {

		super(Operation.SUB,instructionNumber, destinationRegister, sourceOneRegister,
				sourceTwoRegister);
	}

	@Override
	public Short execute() {
		Short[] operands = super.loadDataFromRegisters();

		// call SUB function and pass operands to it and it will return the
		// result to be store in dest reg
		Short result = adderFU.sub(operands[0], operands[1]);

		return result;
	}

}
