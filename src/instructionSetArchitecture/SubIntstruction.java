package instructionSetArchitecture;

import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class SubIntstruction extends InstructionSetArchitecture {

	public SubIntstruction(RegisterEnum destinationRegister, Integer instructionNumber,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {

		super(Operation.SUB,instructionNumber, destinationRegister, sourceOneRegister,
				sourceTwoRegister);
	}

	@Override
	public int execute() {
		Short[] operands = super.loadDataFromRegisters();

		// call SUB function and pass operands to it and it will return the
		// result to be store in dest reg
		int result[] = MainFunctionUnit.getInstance().getAdder().sub(operands[0], operands[1]);

		super.setResult((short)result[0]);
		return result[1];
	}

}
