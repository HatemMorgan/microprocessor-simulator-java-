package instructionSetArchitecture;

import memory.InstructionMemory;
import registers.RegisterEnum;
import reservationStations.Operation;

public class JALRInstruction extends InstructionSetArchitecture {


	public JALRInstruction(RegisterEnum destinationRegister,Integer instructionNumber ,RegisterEnum sourceOneRegister) {
		super(Operation.JALR,instructionNumber,destinationRegister ,sourceOneRegister , null);

	}

	@Override
	public Short execute() {
		
		Short[] operands = super.loadDataFromRegisters();
		
		
		// store PC+1 in destination 
		short pc = InstructionMemory.getInstance().getPC();
		registerFile.storeDataToRegister(super.getDestinationRegister(),pc);
		
		// call (call/return method) and pass to it the operand
		return operands[0];
		

	}

}
