package registers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import registerStatus.RegisterStatus;

public class RegisterFile {
	private Hashtable<RegisterEnum,Register> registerFile ;
	
	public RegisterFile (){
		registerFile = new Hashtable<RegisterEnum, Register>(8);
		init();
		
	}
	
	private void init (){
		for (int i = 0; i < 8 ;i++) {
			if( i== 0 ){
				registerFile.put(RegisterEnum.valueOf("R"+i), new Register((short) 0));
				continue;
			}
			registerFile.put(RegisterEnum.valueOf("R"+i), new Register(null));
		}
	}
	
	private void printRegisterFile(){
		for(RegisterEnum registerName : registerFile.keySet()){
			System.out.println("Register "+registerName+" has value : "+registerFile.get(registerName));
		}
	}
	
	
	private Short loadDataFromRegister(RegisterEnum registerName){
		if(! registerFile.containsKey(registerName)){
			System.out.println("Wrong Register");
			return null;
		}
		
		return registerFile.get(registerName).getData();
		
	}
	
	private void storeDataToRegister(RegisterEnum registerName , Short data){
		if(! registerFile.containsKey(registerName)){
			System.out.println("Wrong Register");
			return ;
		}
		
		if(registerName.equals(RegisterEnum.R0)){
			System.out.println("cannot store data to register R0");
			return ;
		}
		
		registerFile.get(registerName).setData(data);
		System.out.println("Storing data : "+data+" to register : "+registerName);
	}
	
	
	
	
	
	public static void main(String[] args) {
		RegisterFile registerFile = new RegisterFile();
		
		registerFile.storeDataToRegister(RegisterEnum.R1, (short)100);
		registerFile.storeDataToRegister(RegisterEnum.R3, (short)2230);
		
		System.out.println(registerFile.loadDataFromRegister(RegisterEnum.R1));
		
		registerFile.printRegisterFile();
		
	}
	
}

