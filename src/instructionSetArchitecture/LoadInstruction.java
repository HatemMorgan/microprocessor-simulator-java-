package instructionSetArchitecture;

import registers.RegisterEnum;
import reservationStations.Operation;

public class LoadInstruction extends InstructionSetArchitecture {
	Short immediateValue;

	public LoadInstruction(RegisterEnum destinationRegister,Integer instructionNumber,
			RegisterEnum sourceOneRegister, Short immediateValue) {

		super(Operation.LD,instructionNumber ,destinationRegister, sourceOneRegister, null);
		this.immediateValue = immediateValue;
	}

	
	
	public Short getImmediateValue() {
		return immediateValue;
	}



	@Override
	public Short execute() {

		Short[] operands = super.loadDataFromRegisters();

		// call ADDI function and pass operand and immidiate value to it and it
		// will return the address as the result
		Short result = adderFU.add(operands[0], immediateValue);

		// pass address to load function in the memory that will return the
		// result
		// TODO

		return null;
	}

}
