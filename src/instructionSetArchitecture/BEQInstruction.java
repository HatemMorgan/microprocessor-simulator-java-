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
	public int execute() {
		Short[] operands = super.loadDataFromRegisters();
		
		// call subtract method of adder and pass operands to it and it will return result
		int[] results = MainFunctionUnit.getInstance().getAdder().sub(operands[0],operands[1]);
		// check if result==0 so we will branch 
		
		if(results[0] == 0)
			super.setResult((short) (InstructionMemory.getInstance().getPC()+immeditate));
		else
			super.setResult(null);
		
		return results[1];
	}

}
