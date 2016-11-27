package instructionSetArchitecture;

import functionalUnits.*;
import memory.Clock;
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
			System.out.println(sourceOneRegister);
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


	public static void main(String[] args) {
		
		Clock clock = new Clock();
		
		MainFunctionUnit.init(3, 5, 2);
		
		
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R1,(short)20);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R2,(short)200);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R3,(short)240);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R4,(short)290);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R5,(short)522);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R6,(short)2022);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R7,(short)903);
		
		AddImmediateInstruction ADDI = new AddImmediateInstruction(RegisterEnum.R1, 1, RegisterEnum.R2,(short) 200);
		System.out.println(ADDI.execute());
	}

}
