package instructionSetArchitecture;

import functionalUnits.FunctionalUnitsType;
import functionalUnits.MainFunctionUnit;
import memory.Clock;
import registers.RegisterEnum;
import reservationStations.Operation;

public class RETInstruction extends InstructionSetArchitecture {

	public RETInstruction(RegisterEnum sourceOneRegister,Integer instructionNumber) {
		super(Operation.RET,instructionNumber, null, sourceOneRegister, null,FunctionalUnitsType.CALL);

	}

	@Override
	public int execute(Short operand1,Short operand2) {


		int callNumCycles = MainFunctionUnit.getInstance().getCallNumCycles();
		int current = Clock.counter.intValue();
		while(Clock.counter.intValue() != current+callNumCycles);
		// call (call/return method) and pass to it the operand
		
		super.setResult(operand1);
		return current+1;
	}

}
