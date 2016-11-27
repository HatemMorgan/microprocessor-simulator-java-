package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class AddImmediateInstruction extends InstructionSetArchitecture {
	
	Short immediateValue;
	public AddImmediateInstruction( RegisterEnum destinationRegister, Integer instructionNumber,
			RegisterEnum sourceOneRegister, Short immidiateValue) {
		
		super(Operation.ADDI,instructionNumber, destinationRegister, sourceOneRegister, null);
		this.immediateValue = immidiateValue;
	}
	
	

	public Short getImmediateValue() {
		return immediateValue;
	}



	@Override
	public Short execute() {
		
		Short[] operands = super.loadDataFromRegisters();
		
		// call ADDI function and pass operand and immediateValue to it and it will return the address as the result
		Short result = adderFU.add(operands[0],immediateValue);
		
		

		return result ;
	}

}
