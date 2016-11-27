package instructionSetArchitecture;

import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class AddInstruction extends InstructionSetArchitecture {

	public AddInstruction(RegisterEnum destinationRegister,Integer instructionNumber,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {
		
		super(Operation.ADD, instructionNumber,destinationRegister, sourceOneRegister, sourceTwoRegister);
	}

	@Override
	public Short execute() {
		Short[] operands = super.loadDataFromRegisters();
		
		// call ADD function and pass operands to it and it will return  the result to be store in dest reg
		Short result = MainFunctionUnit.getInstance().getAdder().add(operands[0], operands[1]);
		
		return result;
	}

}
