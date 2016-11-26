package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class JMPInstruction extends InstructionSetArchitecture {

	private Short immediateValue;

	public JMPInstruction(RegisterEnum sourceOneRegister,Integer instructionNumber,Short immediateValue) {
		super(Operation.JMP,instructionNumber ,null, sourceOneRegister,
				null);
		this.immediateValue = immediateValue;
	}

	@Override
	public Short execute() {
		
		Short[] operands = super.loadDataFromRegisters();
		
		// call ADDI function and pass  operand and immidiate value to it
		Short result = adderFU.add(operands[0],immediateValue);
		
		// call ADDI function and pass result and PC+1 to calculate targat address
		

		// call jmp method that will jmp to the instruction at the resulted address
		
		return null;
	}

}
