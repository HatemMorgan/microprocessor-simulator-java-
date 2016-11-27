package instructionSetArchitecture;

import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class StoreInstruction extends InstructionSetArchitecture {
	
	Short immediateValue ;
	public StoreInstruction(RegisterEnum sourceOneRegister,Integer instructionNumber,
			RegisterEnum sourceTwoRegister, Short immediateValue) {
		
		super(Operation.SW,instructionNumber, null, sourceOneRegister, sourceTwoRegister);
		this.immediateValue = immediateValue;
	}
	
	

	public Short getImmediateValue() {
		return immediateValue;
	}



	@Override
	public int execute() {
		
		
		Short[] operands = super.loadDataFromRegisters();
			
		// call ADDI function and pass operand and immediateValue to it and it will return the address as the result
		int result [] = MainFunctionUnit.getInstance().getAdder().add(operands[0],immediateValue);
		
		// call store method and pass result[0] to it
		//TODo
				
		return 1;
	}

}
