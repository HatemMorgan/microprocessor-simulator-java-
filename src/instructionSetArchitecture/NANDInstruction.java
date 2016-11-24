package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class NANDInstruction extends InstructionSetArchitecture {

	public NANDInstruction(Operation operation, RegisterEnum destinationRegister,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {
		
		super(operation, destinationRegister, sourceOneRegister, sourceTwoRegister);
	}

	@Override
	public String execute() {
		String[] operands = super.loadDataFromRegisters();
		
		// call NAND function and pass operands to it and it will return  the result to be store in dest reg
		
		// store returned result to destination register
		super.storeResultIntoDest(null);
		
		return null;
	}

}
