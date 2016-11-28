package instructionSetArchitecture;

import main.TomasuloProcessor;
import memory.Clock;
import memory.DataMemory;
import functionalUnits.FunctionalUnitsType;
import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class StoreInstruction extends InstructionSetArchitecture {
	
	Short immediateValue ;
	public StoreInstruction(RegisterEnum sourceOneRegister,Integer instructionNumber,
			RegisterEnum sourceTwoRegister, Short immediateValue) {
		
		super(Operation.SW,instructionNumber, null, sourceOneRegister, sourceTwoRegister,FunctionalUnitsType.STORE);
		this.immediateValue = immediateValue;
	}
	
	

	public Short getImmediateValue() {
		return immediateValue;
	}



	@Override
	public int execute(Short operand1,Short operand2) {
		
		
		short address = (short) (operand2 + immediateValue);

		// Stall for 1 cycle
		int current = Clock.counter.intValue();
		while (Clock.counter.intValue() != current + 1);

		// Store value
		DataMemory.getInstance().store(address, operand1);
		return Clock.counter.intValue() + 1;
	}

}
