package registerStatus;

import java.util.Hashtable;

import registers.RegisterEnum;

public class RegisterStatus {

	Hashtable<RegisterEnum, Integer> registerStatusTable;

	public RegisterStatus() {
		registerStatusTable = new Hashtable<RegisterEnum, Integer>();
		init();
	}

	private void init() {

		for (RegisterEnum key : RegisterEnum.values()) {
			registerStatusTable.put(key, -1);
		}

	}

	public synchronized void insertIntoRegisterStatusTable(RegisterEnum registerNumber,
			Integer ROBNum) {
		if (!registerStatusTable.containsKey(registerNumber)) {
			System.out.println("Wrong Register Number: #" + registerNumber);
			return;
		}
		
		if(registerNumber == RegisterEnum.R0){
			System.out.println("Cannot write on register R0");
			return;
		}

		registerStatusTable.put(registerNumber, ROBNum);
		System.out.println("Inserted ROBNumber: #" + ROBNum
				+ " into registerNumber: #" + registerNumber);
	}

	public synchronized void removeFromRegisterStatusTable(RegisterEnum registerNumber) {
		if (!registerStatusTable.containsKey(registerNumber)) {
			System.out.println("Wrong Register Number");
			return;
		}

		registerStatusTable.put(registerNumber, -1);
		System.out.println("Emptying register status entry of register: #"
				+ registerNumber);
	}

	public synchronized boolean isBusy(RegisterEnum registerNum) {
		if (!registerStatusTable.containsKey(registerNum)) {
			System.out.println("Wrong Register Number");
			return false;
		}

		return registerStatusTable.get(registerNum) != -1 ? true : false;

	}

	public synchronized Integer getROBNum(RegisterEnum registerNum){
		if (!registerStatusTable.containsKey(registerNum)) {
			System.out.println("Wrong Register Number");
			return 0;
		}
		
		return registerStatusTable.get(registerNum);
	}
	
	public void printRegisterStatus(){
		System.out.println(registerStatusTable.toString());
	}
	
	public static void main(String[] args) {
		RegisterStatus registerStatus = new RegisterStatus();

		registerStatus.insertIntoRegisterStatusTable(RegisterEnum.R2, 3);
		registerStatus.insertIntoRegisterStatusTable(RegisterEnum.R3, 4);
		registerStatus.removeFromRegisterStatusTable(RegisterEnum.R2);
		System.out.println(registerStatus.isBusy(RegisterEnum.R3));
		System.out.println(registerStatus.isBusy(RegisterEnum.R1));
		System.out.println(registerStatus.registerStatusTable.toString());
	}
}
