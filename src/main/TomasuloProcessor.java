package main;

import functionalUnits.MainFunctionUnit;
import instructionSetArchitecture.AddImmediateInstruction;
import instructionSetArchitecture.AddInstruction;
import instructionSetArchitecture.InstructionSetArchitecture;
import instructionSetArchitecture.LoadInstruction;
import instructionSetArchitecture.MulInstruction;
import instructionSetArchitecture.StoreInstruction;
import instructionSetArchitecture.SubIntstruction;

import java.util.ArrayList;
import java.util.Hashtable;

import memory.Clock;
import memory.DataMemory;
import memory.InstructionMemory;
import memory.writeHitPolicy;
import memory.writeMissPolicy;
import programState.ProgramState;
import registerStatus.RegisterStatus;
import registers.RegisterEnum;
import registers.RegisterFile;
import reorderBuffer.ROB;
import reservationStations.ReservationsStationTable;
import tomasulo.RunINstruction;

public class TomasuloProcessor {

	private ProgramState programState;
	private RegisterStatus registerStatus;
	private ReservationsStationTable reservationsStationTable;
	private ROB rob;
	
	// instializing the all processor components
	public TomasuloProcessor(InstructionSetArchitecture[] programInstructions,
			int adderNumCycles, int multNumCycles, int nandNumCycles,
			int callNumCycles, int ROBSize,
			ArrayList<String> adderReservationStations,
			ArrayList<String> multReservationStations,
			ArrayList<String> nandReservationStations,
			ArrayList<String> loadReservationStationsNames,
			ArrayList<String> storeReservationStationsNames,
			ArrayList<String> callReservationStationsNames, int cacheLevels,
			int mainMemoryAccessTimeInCycles, writeHitPolicy hitPolicy,
			writeMissPolicy missPolicy, int piplineDepthNum,Hashtable<Short,Short> intialData) {
		// intialize program state table and pass instructions array to it
		programState = new ProgramState();

		// intialize RegisterStatus table
		registerStatus = new RegisterStatus();

		// instialize ROB
		rob = new ROB(ROBSize);

		// intialize reservationStationTable
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(adderReservationStations);
		list.addAll(multReservationStations);
		list.addAll(nandReservationStations);
		list.addAll(loadReservationStationsNames);
		list.addAll(storeReservationStationsNames);
		list.addAll(callReservationStationsNames);
		reservationsStationTable = new ReservationsStationTable(list.toArray());

		Clock clock = new Clock();

		MainFunctionUnit.init(adderNumCycles, multNumCycles, nandNumCycles,
				callNumCycles, adderReservationStations.toArray(),
				multReservationStations.toArray(),
				nandReservationStations.toArray(),
				loadReservationStationsNames.toArray(),
				storeReservationStationsNames.toArray(),
				callReservationStationsNames.toArray());

		// loadiing memory with instructions

		InstructionMemory.init(cacheLevels, mainMemoryAccessTimeInCycles,
				clock, hitPolicy, missPolicy);
		InstructionMemory instructionMemory = InstructionMemory.getInstance();
		
		instructionMemory.storeInstructions(programInstructions);

		instructionMemory.setEndProgram((short) (programInstructions.length));

		programState.setInstructionNumberOfInOrderIssuedISA(piplineDepthNum);
		
		
		//  Data Memory
		DataMemory.init(cacheLevels, mainMemoryAccessTimeInCycles, clock, writeHitPolicy.writeThrough, writeMissPolicy.writeAround);
		for(Short address : intialData.keySet() ){
			DataMemory.getInstance().store(address, intialData.get(address));
		}
		
		boolean finished = false;
		while (true) {

			InstructionSetArchitecture[] instructionsFetched = instructionMemory
					.readInstructions(piplineDepthNum);

			if (instructionsFetched[0] == null) {

				break;
			}

			programState
					.addInstructionsToProgramStateTable(instructionsFetched);
			for (int i = 0; i < instructionsFetched.length; i++) {

				if (instructionsFetched[i] == null) {
					finished = true;
					break;
				}

				Thread thread = new Thread(new RunINstruction(
						instructionsFetched[i], programState, registerStatus,
						reservationsStationTable, rob, piplineDepthNum));
				thread.start();
			}

			if (finished)
				break;

		}
		
	
		while (programState.getProgramStateTableEntry(
				programInstructions.length).getCommitted() == 0){
			System.out.print("");
		}

		clock.setFinish(true);

	}

	public ProgramState getProgramState() {
		return programState;
	}

	public RegisterStatus getRegisterStatus() {
		return registerStatus;
	}

	public ReservationsStationTable getReservationsStationTable() {
		return reservationsStationTable;
	}

	public ROB getRob() {
		return rob;
	}

	public static void main(String[] args) {
		// trivial example
		// InstructionSetArchitecture[] instructions = new
		// InstructionSetArchitecture[4];
		// instructions[0] = new AddImmediateInstruction(RegisterEnum.R2, 1,
		// RegisterEnum.R1, (short) 20);
		//
		// instructions[1] = new AddInstruction(RegisterEnum.R5, 2,
		// RegisterEnum.R1, RegisterEnum.R4);
		//
		// instructions[2] = new SubIntstruction(RegisterEnum.R7, 3,
		// RegisterEnum.R3, RegisterEnum.R4);
		//
		// instructions[3] = new MulInstruction(RegisterEnum.R6, 4,
		// RegisterEnum.R3, RegisterEnum.R4);

		// testing committing in order and stalling when it is not its turn to
		// commit
		// InstructionSetArchitecture[] instructions = new
		// InstructionSetArchitecture[4];
		// instructions[0] = new AddImmediateInstruction(RegisterEnum.R2, 1,
		// RegisterEnum.R1, (short) 20);
		//
		// instructions[1] = new MulInstruction(RegisterEnum.R6, 2,
		// RegisterEnum.R3, RegisterEnum.R4);
		//
		// instructions[2] = new AddInstruction(RegisterEnum.R5, 3,
		// RegisterEnum.R1, RegisterEnum.R4);
		//
		// instructions[3] = new SubIntstruction(RegisterEnum.R7,4,
		// RegisterEnum.R3, RegisterEnum.R4);

		// test with RAW dependencies between instructions and testing when ROB size = 5 < numOfInstructions 
		InstructionSetArchitecture[] instructions = new InstructionSetArchitecture[6];
		instructions[0] = new AddImmediateInstruction(RegisterEnum.R2, 1,
				RegisterEnum.R1, (short) 20);

		instructions[1] = new MulInstruction(RegisterEnum.R6, 2,
				RegisterEnum.R2, RegisterEnum.R4);

		instructions[2] = new AddInstruction(RegisterEnum.R5, 3,
				RegisterEnum.R1, RegisterEnum.R4);

		instructions[3] = new SubIntstruction(RegisterEnum.R7, 4,
				RegisterEnum.R3, RegisterEnum.R6);
		instructions[4] = new LoadInstruction(RegisterEnum.R4, 5, RegisterEnum.R0,(short)2);
		instructions[5] = new StoreInstruction(RegisterEnum.R2, 6, RegisterEnum.R0,(short)3);
		

		// instructions[2] = new StoreInstruction(RegisterEnum.R4, 3,
		// RegisterEnum.R5, (short) 30);
		//
		// instructions[3] = new BEQInstruction(RegisterEnum.R3, 4,
		// RegisterEnum.R7, (short) 50);
		// instructions[4] = new JMPInstruction(RegisterEnum.R3, 5, (short) 70);
		// instructions[5] = new RETInstruction(RegisterEnum.R1, 6);
		// instructions[6] = new JALRInstruction(RegisterEnum.R4, 7,
		// RegisterEnum.R5);
		// instructions[7] = new MulInstruction(RegisterEnum.R7, 8,
		// RegisterEnum.R4, RegisterEnum.R3);
		// instructions[8] = new LoadInstruction(RegisterEnum.R2, 9,
		// RegisterEnum.R6, (short) 20);

		ArrayList<String> adderReservationStations = new ArrayList<String>();
		adderReservationStations.add("add1");
		adderReservationStations.add("add2");
		adderReservationStations.add("add3");
		adderReservationStations.add("add4");

		ArrayList<String> multReservationStations = new ArrayList<String>();
		multReservationStations.add("mult");

		ArrayList<String> loadReservationStationsNames = new ArrayList<String>();
		loadReservationStationsNames.add("load1");
		loadReservationStationsNames.add("load2");

		ArrayList<String> storeReservationStationsNames = new ArrayList<String>();
		storeReservationStationsNames.add("store1");

		ArrayList<String> nandReservationStations = new ArrayList<String>();
		nandReservationStations.add("nand");

		ArrayList<String> callReservationStations = new ArrayList<String>();
		callReservationStations.add("call1");
		callReservationStations.add("call2");

		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R1,
				(short) 20);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R2,
				(short) 200);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R3,
				(short) 2);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R4,
				(short) 290);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R5,
				(short) 522);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R6,
				(short) 2022);
		RegisterFile.getInstance().storeDataToRegister(RegisterEnum.R7,
				(short) 903);

		Hashtable<Short,Short> intialData = new Hashtable<Short, Short>();
		intialData.put((short)0,(short) 200);
		intialData.put((short)1,(short) 30);
		intialData.put((short)2,(short) 920);
		TomasuloProcessor tomasuloProcessor = new TomasuloProcessor(
				instructions, 3, 5, 2, 1, 5, adderReservationStations,
				multReservationStations, nandReservationStations,
				loadReservationStationsNames, storeReservationStationsNames,
				callReservationStations, 1, 2, writeHitPolicy.writeThrough,
				writeMissPolicy.writeAround, 3,intialData);

		System.out
				.println("------------------------- Program State ---------------------------");
		tomasuloProcessor.getProgramState().printProgramStateTable();
		 System.out
		 .println("------------------------- Reservation Station ---------------------------");
		 tomasuloProcessor.getReservationsStationTable()
		 .printReservationStationsTable();
		 System.out
		 .println("------------------------- Register Status ---------------------------");
		 tomasuloProcessor.getRegisterStatus().printRegisterStatus();
		 System.out
		 .println("------------------------- ROB ---------------------------");
		 tomasuloProcessor.getRob().printROB();
		
		 System.out
		 .println("------------------------- Register File ---------------------------");
		 RegisterFile.getInstance().printregisterFileTable();

	}

}
