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
			registerStatusTable.put(key,-1);
		}

	}
	
	
	private void insertIntoRegisterStatusTable(RegisterEnum registerNumber,Integer ROBNum){
		if(!registerStatusTable.containsKey(registerNumber)){
			System.out.println("Wrong Register Number: #"+registerNumber);
			return ;
		}
		
		registerStatusTable.put(registerNumber, ROBNum);
		System.out.println("Inserted ROBNumber: #"+ROBNum+" into registerNumber: #"+registerNumber);
	}
	
	
	private void removeFromRegisterStatusTable(RegisterEnum registerNumber){
		if(!registerStatusTable.containsKey(registerNumber)){
			System.out.println("Wrong Register Number");
			return ;
		}
		
		registerStatusTable.put(registerNumber, -1);
		System.out.println("Emptying register status entry of register: #"+registerNumber);
	}
	

	public static void main(String[] args) {
	  RegisterStatus registerStatus = new RegisterStatus();
	  
	  registerStatus.insertIntoRegisterStatusTable(RegisterEnum.R2,3);
	  registerStatus.insertIntoRegisterStatusTable(RegisterEnum.R3,4);
	  registerStatus.removeFromRegisterStatusTable(RegisterEnum.R2);
	  System.out.println(registerStatus.registerStatusTable.toString());
	}
}
