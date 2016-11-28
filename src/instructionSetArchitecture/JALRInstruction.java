package instructionSetArchitecture;

import memory.Clock;
import memory.InstructionMemory;
import registers.RegisterEnum;
import reservationStations.Operation;

public class JALRInstruction extends InstructionSetArchitecture {
	
	private Short pcResult ;
		
	public JALRInstruction(RegisterEnum destinationRegister,Integer instructionNumber ,RegisterEnum sourceOneRegister) {
		super(Operation.JALR,instructionNumber,destinationRegister ,sourceOneRegister , null,null);
		pcResult = null;

	}

	@Override
	public int execute() {
		
		Short[] operands = super.loadDataFromRegisters();
		
		
		// store PC+1 in destination 
		short pc = InstructionMemory.getInstance().getPC();
		// setting pc variable to be used to write the pc to destination result after committing
		this.pcResult = pc; 
		
		int current = Clock.counter.intValue();
		while(Clock.counter.intValue() != current+1);
		
		// call (call/return method) and pass to it the operand
		super.setResult(operands[0]);
		
		return current+1;
		

	}

	public Short getPcResult() {
		return pcResult;
	}
	
	

}
