package instructionSetArchitecture;

import functionalUnits.FunctionalUnitsType;
import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class NANDInstruction extends InstructionSetArchitecture {

	public NANDInstruction(RegisterEnum destinationRegister, Integer instructionNumber,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {

		super(Operation.NAND, instructionNumber,destinationRegister, sourceOneRegister,
				sourceTwoRegister,FunctionalUnitsType.NAND);
	}

	@Override
	public int execute(Short operand1,Short operand2) {
		// call NAND function and pass operands to it and it will return the
		// result to be store in dest reg
		int result [] = MainFunctionUnit.getInstance().getNand().nand(operand1, operand2);

		super.setResult((short)result[0]);
		return result[1];
	}

}
