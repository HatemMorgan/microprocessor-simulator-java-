package instructionSetArchitecture;

import functionalUnits.FunctionalUnitsType;
import functionalUnits.MainFunctionUnit;
import memory.Clock;
import memory.InstructionMemory;
import registers.RegisterEnum;
import reservationStations.Operation;

public class JALRInstruction extends InstructionSetArchitecture {
	
	private Short pcResult ;
		
	public JALRInstruction(RegisterEnum destinationRegister,Integer instructionNumber ,RegisterEnum sourceOneRegister) {
		super(Operation.JALR,instructionNumber,destinationRegister ,sourceOneRegister , null,FunctionalUnitsType.CALL);
		pcResult = null;

	}

	@Override
	public int execute(Short operand1,Short operand2) {
				
		// store PC+1 in destination 
		short pc = InstructionMemory.getInstance().getPC();
		// setting pc variable to be used to write the pc to destination result after committing
		this.pcResult = pc; 
		
		int callNumCycles = MainFunctionUnit.getInstance().getCallNumCycles();
		int current = Clock.counter.intValue();
		while(Clock.counter.intValue() != current+callNumCycles);
		
		// call (call/return method) and pass to it the operand
		super.setResult(operand1);
		
		return current+1;
		

	}

	public Short getPcResult() {
		return pcResult;
	}
	
	

}
