package instructionSetArchitecture;

import memory.InstructionMemory;
import functionalUnits.FunctionalUnitsType;
import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class BEQInstruction extends InstructionSetArchitecture {

		private Short immeditate ;
	public BEQInstruction( RegisterEnum sourceOneRegister, Integer instructionNumber ,
			RegisterEnum sourceTwoRegister, Short immeditate) {
		
		super(Operation.BEQ,instructionNumber ,null, sourceOneRegister, sourceTwoRegister,FunctionalUnitsType.ADDER);
		this.immeditate = immeditate;
	}
	
	
	

	public Short getImmeditate() {
		return immeditate;
	}




	@Override
	public int execute(Short operand1,Short operand2) {

		
		// call subtract method of adder and pass operands to it and it will return result
		int[] results = MainFunctionUnit.getInstance().getAdder().sub(operand1,operand2);
		// check if result==0 so we will branch 
		
		if(results[0] == 0)
			super.setResult((short) (InstructionMemory.getInstance().getPC()+immeditate));
		else
			super.setResult(null);
		
		return results[1];
	}

}
