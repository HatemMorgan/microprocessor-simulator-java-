package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class RETInstruction extends InstructionSetArchitecture {


	public RETInstruction(Operation operation, RegisterEnum sourceOneRegister) {
		super(operation,null ,sourceOneRegister , null);

	}

	@Override
	public String execute() {
		
		String[] operands = super.loadDataFromRegisters();
		
		// call (call/return method) and pass to it the operand
		
		return null;
	}

}
