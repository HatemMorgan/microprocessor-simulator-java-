package instructionSetArchitecture;

import functionalUnits.FunctionalUnitsType;
import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class AddImmediateInstruction extends InstructionSetArchitecture {
	
	Short immediateValue;
	public AddImmediateInstruction( RegisterEnum destinationRegister, Integer instructionNumber,
			RegisterEnum sourceOneRegister, Short immidiateValue) {
		
		super(Operation.ADDI,instructionNumber, destinationRegister, sourceOneRegister, null,FunctionalUnitsType.ADDER);
		this.immediateValue = immidiateValue;
	}
	
	

	public Short getImmediateValue() {
		return immediateValue;
	}



	@Override
	public int execute() {
	
		Short[] operands = super.loadDataFromRegisters();
		// call ADDI function and pass operand and immediateValue to it and it will return the address as the result
		int[] results= MainFunctionUnit.getInstance().getAdder().add(operands[0],immediateValue);
		
		super.setResult((short)results[0]);
		return results[1];
	}

}
