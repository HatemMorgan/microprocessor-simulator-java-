package instructionSetArchitecture;

import functionalUnits.FunctionalUnitsType;
import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class LoadInstruction extends InstructionSetArchitecture {
	Short immediateValue;

	public LoadInstruction(RegisterEnum destinationRegister,Integer instructionNumber,
			RegisterEnum sourceOneRegister, Short immediateValue) {

		super(Operation.LD,instructionNumber ,destinationRegister, sourceOneRegister, null,FunctionalUnitsType.LOAD);
		this.immediateValue = immediateValue;
	}

	
	
	public Short getImmediateValue() {
		return immediateValue;
	}



	@Override
	public int execute(Short operand1,Short operand2) {


		// call ADDI function and pass operand and immidiate value to it and it
		// will return the address as the result
		int results[] = MainFunctionUnit.getInstance().getAdder().add(operand1, immediateValue);

		// pass result[0] to load function in the memory that will return the
		// result and the clock cycle
		// TODO

		return 1;
	}

}
