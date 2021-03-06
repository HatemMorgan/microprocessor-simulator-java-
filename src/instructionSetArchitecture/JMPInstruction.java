package instructionSetArchitecture;

import memory.InstructionMemory;
import functionalUnits.FunctionalUnitsType;
import functionalUnits.MainFunctionUnit;
import registers.RegisterEnum;
import reservationStations.Operation;

public class JMPInstruction extends InstructionSetArchitecture {

	private Short immediateValue;

	public JMPInstruction(RegisterEnum sourceOneRegister,Integer instructionNumber,Short immediateValue) {
		super(Operation.JMP,instructionNumber ,null, sourceOneRegister,
				null,FunctionalUnitsType.ADDER);
		this.immediateValue = immediateValue;
	}

	
	
	public Short getImmediateValue() {
		return immediateValue;
	}



	@Override
	public int  execute(Short operand1,Short operand2) {
		
		
		// call ADDI function and pass  operand and immidiate value to it
		int result[] = MainFunctionUnit.getInstance().getAdder().add(operand1,immediateValue);
		
		// call ADDI function and pass result and PC+1 to calculate targat address
		short pc = InstructionMemory.getInstance().getPC();
		result = MainFunctionUnit.getInstance().getAdder().add((short)result[0], pc);
		
		super.setResult((short)result[0]);
		return result[1];
		
	}

}
