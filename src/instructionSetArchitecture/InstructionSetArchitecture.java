package instructionSetArchitecture;

import functionalUnits.*;
import registers.Register;
import registers.RegisterEnum;
import registers.RegisterFile;
import reservationStations.Operation;

public abstract class InstructionSetArchitecture {
	private Operation operation;
	private RegisterEnum destinationRegister;
	private RegisterEnum sourceOneRegister;
	private RegisterEnum sourceTwoRegister;
	private Integer instructionNumber ;
	// Functional Units
	protected static Adder adderFU = new Adder();
	protected static Mult multFU = new Mult();
	protected static Nand nandFU = new Nand();

	// Register File
	protected static RegisterFile registerFile = RegisterFile.getInstance();

	public InstructionSetArchitecture(Operation operation, Integer instructionNumber,
			RegisterEnum destinationRegister, RegisterEnum sourceOneRegister,
			RegisterEnum sourceTwoRegister) {

		this.instructionNumber = instructionNumber;
		this.operation = operation;
		this.destinationRegister = destinationRegister;
		this.sourceOneRegister = sourceOneRegister;
		this.sourceTwoRegister = sourceTwoRegister;
	}

	public abstract Short execute();

	public Short[] loadDataFromRegisters() {
		// TODO get data from source operands
		
		if(sourceOneRegister != null && sourceTwoRegister == null){
			Short[] operands = new Short[1];
			operands[0] = registerFile.loadDataFromRegister(sourceOneRegister);
			return operands ;
		}
	
		
		if(sourceOneRegister == null && sourceTwoRegister != null ){
			Short[] operands = new Short[1];
			operands[0] = registerFile.loadDataFromRegister(sourceTwoRegister);
			return operands; 
		}
		
		Short[] operands = new Short[2];
		operands[0] = registerFile.loadDataFromRegister(sourceOneRegister);
		operands[1] = registerFile.loadDataFromRegister(sourceTwoRegister);
		return operands;
	}
	


	public Operation getOperation() {
		return operation;
	}

	public RegisterEnum getDestinationRegister() {
		return destinationRegister;
	}

	public RegisterEnum getSourceOneRegister() {
		return sourceOneRegister;
	}

	public RegisterEnum getSourceTwoRegister() {
		return sourceTwoRegister;
	}

	
	
	public Integer getInstructionNumber() {
		return instructionNumber;
	}

	@Override
	public String toString() {
		return "InstructionSetArchitecture [operation=" + operation
				+ ", destinationRegister=" + destinationRegister
				+ ", sourceOneRegister=" + sourceOneRegister
				+ ", sourceTwoRegister=" + sourceTwoRegister
				+ ", instructionNumber=" + instructionNumber + "]";
	}

	
	

	

}
