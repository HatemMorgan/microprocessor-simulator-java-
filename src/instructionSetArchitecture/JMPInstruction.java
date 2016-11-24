package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class JMPInstruction extends InstructionSetArchitecture {

	private String immediateValue;

	public JMPInstruction(Operation operation, RegisterEnum sourceOneRegister, String immediateValue) {
		super(operation, null, sourceOneRegister,
				null);
		this.immediateValue = immediateValue;
	}

	@Override
	public String execute() {
		
		String[] operands = super.loadDataFromRegisters();
		
		// call ADDI function and pass  operand and immidiate value to it and it will return the address as the result
		
		// call jmp method that will jmp to the instruction at the resulted address
		
		return null;
	}

}
