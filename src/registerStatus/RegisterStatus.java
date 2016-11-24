package registerStatus;

import java.util.Hashtable;

import registers.Register;

public class RegisterStatus {

	Hashtable<Register, Integer> registerStatusTable;

	public RegisterStatus() {
		registerStatusTable = new Hashtable<Register, Integer>();
		init();
	}

	private void init() {

		for (Register key : Register.values()) {
			registerStatusTable.put(key,-1);
		}

	}
	
	
	private void insertIntoRegisterStatusTable(Register registerNumber,Integer ROBNum){
		if(!registerStatusTable.containsKey(registerNumber)){
			System.out.println("Wrong Register Number: #"+registerNumber);
			return ;
		}
		
		registerStatusTable.put(registerNumber, ROBNum);
		System.out.println("Inserted ROBNumber: #"+ROBNum+" into registerNumber: #"+registerNumber);
	}
	
	
	private void removeFromRegisterStatusTable(Register registerNumber){
		if(!registerStatusTable.containsKey(registerNumber)){
			System.out.println("Wrong Register Number");
			return ;
		}
		
		registerStatusTable.put(registerNumber, -1);
		System.out.println("Emptying register status entry of register: #"+registerNumber);
	}
	

	public static void main(String[] args) {
	  RegisterStatus registerStatus = new RegisterStatus();
	  
	  registerStatus.insertIntoRegisterStatusTable(Register.R2,3);
	  registerStatus.insertIntoRegisterStatusTable(Register.R3,4);
	  registerStatus.removeFromRegisterStatusTable(Register.R2);
	  System.out.println(registerStatus.registerStatusTable.toString());
	}
}
