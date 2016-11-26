package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class StoreInstruction extends InstructionSetArchitecture {
	
	Short immediateValue ;
	public StoreInstruction(RegisterEnum destinationRegister,Integer instructionNumber,
			RegisterEnum sourceOneRegister, Short immediateValue) {
		
		super(Operation.SW,instructionNumber, destinationRegister, sourceOneRegister, null);
		this.immediateValue = immediateValue;
	}

	@Override
	public Short execute() {
		
		
		Short[] operands = super.loadDataFromRegisters();
			
		// call ADDI function and pass operand and immediateValue to it and it will return the address as the result
		Short result = adderFU.add(operands[0],immediateValue);
		
		// call store method and pass address to it
		//TODo
				
		return null;
	}

}
