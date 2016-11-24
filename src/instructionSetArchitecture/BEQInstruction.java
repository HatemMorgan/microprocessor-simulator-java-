package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class BEQInstruction extends InstructionSetArchitecture {

	public BEQInstruction(Operation operation, RegisterEnum destinationRegister,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister) {
		
		super(operation, destinationRegister, sourceOneRegister, sourceTwoRegister);
	}

	@Override
	public String execute() {
		String[] operands = super.loadDataFromRegisters();
		
		// call subtract method of adder and pass operands to it and it will return result
		
		// check if result==0 so we will branch 
		
		return null;
	}

}
