package instructionSetArchitecture;

import functionalUnits.FunctionalUnitsType;
import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class AddInstruction extends InstructionSetArchitecture {

	public AddInstruction(RegisterEnum destinationRegister,Integer instructionNumber,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {
		
		super(Operation.ADD, instructionNumber,destinationRegister, sourceOneRegister, sourceTwoRegister,FunctionalUnitsType.ADDER);
	}

	@Override
	public int execute() {
		Short[] operands = super.loadDataFromRegisters();
		
		// call ADD function and pass operands to it and it will return  the result to be store in dest reg
		int[] results = MainFunctionUnit.getInstance().getAdder().add(operands[0], operands[1]);
		System.out.println("------------->"+(short)results[0]);
		super.setResult((short)results[0]);
		System.out.println("---------------->"+super.getResult());
		return results[1];
	}

}
