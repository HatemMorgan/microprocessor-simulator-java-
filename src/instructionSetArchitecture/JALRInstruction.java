package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class JALRInstruction extends InstructionSetArchitecture {


	public JALRInstruction(RegisterEnum destinationRegister, RegisterEnum sourceOneRegister) {
		super(Operation.JALR,destinationRegister ,sourceOneRegister , null);

	}

	@Override
	public Short execute() {
		
		Short[] operands = super.loadDataFromRegisters();
		
		
		// store PC+1 in destination 
		
		// call (call/return method) and pass to it the operand
		
		return null;
	}

}
