package instructionSetArchitecture;

import registers.Register;
import reservationStations.Operation;

public class JALRInstruction extends InstructionSetArchitecture {


	public JALRInstruction(Operation operation, Register destinationRegister, Register sourceOneRegister) {
		super(operation,destinationRegister ,sourceOneRegister , null);

	}

	@Override
	public String execute() {
		
		String[] operands = super.loadDataFromRegisters();
		
		
		// store PC+1 in destination 
		super.storeResultIntoDest(null);
		
		// call (call/return method) and pass to it the operand
		
		return null;
	}

}
