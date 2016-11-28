package instructionSetArchitecture;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

import functionalUnits.*;
import memory.Clock;
import memory.InstructionMemory;
import memory.writeHitPolicy;
import memory.writeMissPolicy;
import registers.Register;
import registers.RegisterEnum;
import registers.RegisterFile;
import reservationStations.Operation;
import reservationStations.ReservationStationEntry;

public abstract class InstructionSetArchitecture implements Serializable{
	private Operation operation;
	private RegisterEnum destinationRegister;
	private RegisterEnum sourceOneRegister;
	private RegisterEnum sourceTwoRegister;
	private Integer instructionNumber;
	private Short result ;
	private FunctionalUnitsType functionalUnitsType;
	private String reservationStationEntryName;

	// Register File
	protected static RegisterFile registerFile = RegisterFile.getInstance();

	public InstructionSetArchitecture(Operation operation,
			Integer instructionNumber, RegisterEnum destinationRegister,
			RegisterEnum sourceOneRegister, RegisterEnum sourceTwoRegister,FunctionalUnitsType functionalUnitsType) {

		this.instructionNumber = instructionNumber;
		this.operation = operation;
		this.destinationRegister = destinationRegister;
		this.sourceOneRegister = sourceOneRegister;
		this.sourceTwoRegister = sourceTwoRegister;
		this.functionalUnitsType = functionalUnitsType;
	}

	public abstract int execute();

	public Short[] loadDataFromRegisters() {
		// TODO get data from source operands

		if (sourceOneRegister != null && sourceTwoRegister == null) {
			Short[] operands = new Short[1];
			System.out.println(sourceOneRegister);
			operands[0] = registerFile.loadDataFromRegister(sourceOneRegister);
			return operands;
		}

		if (sourceOneRegister == null && sourceTwoRegister != null) {
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
	
	

	public Short getResult() {
		return result;
	}

	public void setResult(Short result) {
		this.result = result;
	}

	
	
	public FunctionalUnitsType getFunctionalUnitsType() {
		return functionalUnitsType;
	}

	
	


	public String getReservationStationEntryName() {
		return reservationStationEntryName;
	}

	public void setReservationStationEntryName(String reservationStationEntryName) {
		this.reservationStationEntryName = reservationStationEntryName;
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
		
//		MainFunctionUnit.init(3, 5, 2,1,new String[]{"add1", "add2", "add3", "add4"},new String[]{ "mult"},null,new String[]{"load1", "load2"},new String[]{"store1"});

		
		InstructionMemory.init(2, 10, clock, writeHitPolicy.writeBack, writeMissPolicy.writeAllocate);		
		
		
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R1,
				(short) 20);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R2,
				(short) 200);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R3,
				(short) 240);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R4,
				(short) 1);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R5,
				(short) 2);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R6,
				(short) 2022);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R7,
				(short) 903);

//		AddImmediateInstruction ADDI = new AddImmediateInstruction(
//				RegisterEnum.R1, 1, RegisterEnum.R2, (short) 200);
//		System.out.println("Finishing at clock cycle:"+ADDI.execute()+" and  Add Immediate result = " + ADDI.getResult() );

//		AddInstruction ADD = new AddInstruction(RegisterEnum.R3, 2,
//				RegisterEnum.R4, RegisterEnum.R5);
//		System.out.println("Finishing at clock cycle:"+ADD.execute()+" and  Add result = " + ADD.getResult() );

//		BEQInstruction BEQ = new BEQInstruction(RegisterEnum.R2, 3,
//				RegisterEnum.R2, (short) 290);
//		System.out.println("Finishing at clock cycle:"+BEQ.execute()+" and  BEQ result = " + BEQ.getResult() );

//		JALRInstruction JALR = new JALRInstruction(RegisterEnum.R3, 4,
//				RegisterEnum.R4);
//		System.out.println("Finishing at clock cycle:"+JALR.execute()+" and  JALR result = " + JALR.getResult() );
//		System.out.println("---> " +RegisterFile.getInstance().loadDataFromRegister(RegisterEnum.R3));

//		JMPInstruction JMP = new JMPInstruction(RegisterEnum.R1, 5, (short) 522);
//		System.out.println("Finishing at clock cycle:"+JMP.execute()+" and  JMP result = " + JMP.getResult() );

		
//		MulInstruction MUL = new MulInstruction(RegisterEnum.R3, 2,
//				RegisterEnum.R4, RegisterEnum.R5);
//		System.out.println("Finishing at clock cycle:"+MUL.execute()+" and  MUL result = " + MUL.getResult() );
		
//		SubIntstruction SUB = new SubIntstruction(RegisterEnum.R3, 2,
//				RegisterEnum.R4, RegisterEnum.R5);
//		System.out.println("Finishing at clock cycle:"+SUB.execute()+" and  SUB result = " + SUB.getResult() );
		
		
//		NANDInstruction NAND = new NANDInstruction(RegisterEnum.R3, 2,
//				RegisterEnum.R4, RegisterEnum.R5);
//		System.out.println("Finishing at clock cycle:"+NAND.execute()+" and  SUB result = " + NAND.getResult() );
		
		
		RETInstruction RET = new RETInstruction(RegisterEnum.R3, 2);
		System.out.println("Finishing at clock cycle:"+ RET.execute()+" and   RET result = " +  RET.getResult() );
		
//		LoadInstruction LD = new LoadInstruction(RegisterEnum.R3, 2,
//				RegisterEnum.R4, (short) 903);
//		System.out.println("Returning Load :" + LD.execute());
		
		
	}

}
