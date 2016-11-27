package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class BEQInstruction extends InstructionSetArchitecture {

		private Short immeditate ;
	public BEQInstruction( RegisterEnum sourceOneRegister, Integer instructionNumber ,
			RegisterEnum sourceTwoRegister, Short immeditate) {
		
		super(Operation.BEQ,instructionNumber ,null, sourceOneRegister, sourceTwoRegister);
		this.immeditate = immeditate;
	}
	
	
	

	public Short getImmeditate() {
		return immeditate;
	}




	@Override
	public Short execute() {
		Short[] operands = super.loadDataFromRegisters();
		
		// call subtract method of adder and pass operands to it and it will return result
		Short result = adderFU.sub(operands[0],operands[1]);
		// check if result==0 so we will branch 
		
		if(result == 0){
			return 1 ;
		}else{
			return 0 ;
		}
	
	}

}
