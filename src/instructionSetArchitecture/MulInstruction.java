package instructionSetArchitecture;

import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class MulInstruction extends InstructionSetArchitecture {

	public MulInstruction(RegisterEnum destinationRegister,Integer instructionNumber,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {

		super(Operation.MUL, instructionNumber,destinationRegister, sourceOneRegister,
				sourceTwoRegister);
	}

	@Override
	public Short execute() {
		Short[] operands = super.loadDataFromRegisters();

		// call Mul function and pass operands to it and it will return the
		// result to be store in dest reg
		Short result = MainFunctionUnit.getInstance().getMult().mul(operands[0], operands[1]);

		return result;
	}

}
