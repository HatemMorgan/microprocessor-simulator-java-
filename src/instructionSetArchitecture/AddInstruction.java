package instructionSetArchitecture;

import functionalUnits.*;
import registers.RegisterEnum;
import reservationStations.Operation;

public class AddInstruction extends InstructionSetArchitecture {

	public AddInstruction(RegisterEnum destinationRegister,Integer instructionNumber,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {
		
		super(Operation.ADD, instructionNumber,destinationRegister, sourceOneRegister, sourceTwoRegister,FunctionalUnitsType.ADDER);
	}

	@Override
	public int execute(Short operand1,Short operand2) {
		
		// call ADD function and pass operands to it and it will return  the result to be store in dest reg
		int[] results = MainFunctionUnit.getInstance().getAdder().add(operand1, operand2);
		System.out.println("------------->"+(short)results[0]);
		super.setResult((short)results[0]);
		System.out.println("---------------->"+super.getResult());
		return results[1];
	}

}
