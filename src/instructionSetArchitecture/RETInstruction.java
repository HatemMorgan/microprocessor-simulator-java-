package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class RETInstruction extends InstructionSetArchitecture {

	public RETInstruction(RegisterEnum sourceOneRegister) {
		super(Operation.RET, null, sourceOneRegister, null);

	}

	@Override
	public Short execute() {

		Short[] operands = super.loadDataFromRegisters();

		// call (call/return method) and pass to it the operand

		return null;
	}

}
