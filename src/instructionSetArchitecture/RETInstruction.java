package instructionSetArchitecture;

import memory.Clock;
import registers.RegisterEnum;
import reservationStations.Operation;

public class RETInstruction extends InstructionSetArchitecture {

	public RETInstruction(RegisterEnum sourceOneRegister,Integer instructionNumber) {
		super(Operation.RET,instructionNumber, null, sourceOneRegister, null);

	}

	@Override
	public Short execute() {

		Short[] operands = super.loadDataFromRegisters();
		
		int current = Clock.counter.intValue();
		while(Clock.counter.intValue() != current+1);
		// call (call/return method) and pass to it the operand
		
		return operands[0];
	}

}
