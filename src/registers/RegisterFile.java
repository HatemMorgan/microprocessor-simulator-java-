package registers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import registerStatus.RegisterStatus;

public class RegisterFile {
	private static RegisterFile registerFile;
	private Hashtable<RegisterEnum, Register> registerFileTable;

	private RegisterFile() {
		registerFileTable = new Hashtable<RegisterEnum, Register>(8);
		init();

	}

	public static synchronized RegisterFile getInstance() {

		if (registerFile == null)
			registerFile = new RegisterFile();

		return registerFile;

	}

	private void init() {
		for (int i = 0; i < 8; i++) {
			if (i == 0) {
				registerFileTable.put(RegisterEnum.valueOf("R" + i),
						new Register((short) 0));
				continue;
			}
			registerFileTable.put(RegisterEnum.valueOf("R" + i), new Register(
					null));
		}
	}

	public void printregisterFileTable() {
		for (RegisterEnum registerName : registerFileTable.keySet()) {
			System.out.println("Register " + registerName + " has value : "
					+ registerFileTable.get(registerName));
		}
	}

	public Short loadDataFromRegister(RegisterEnum registerName) {
		if (!registerFileTable.containsKey(registerName)) {
			System.out.println("Wrong Register");
			return null;
		}

		return registerFileTable.get(registerName).getData();

	}

	public void storeDataToRegister(RegisterEnum registerName, Short data) {
		if (!registerFileTable.containsKey(registerName)) {
			System.out.println("Wrong Register");
			return;
		}

		if (registerName.equals(RegisterEnum.R0)) {
			System.out.println("cannot store data to register R0");
			return;
		}

		registerFileTable.get(registerName).setData(data);
		System.out.println("Storing data : " + data + " to register : "
				+ registerName);
	}

	public static void main(String[] args) {
		RegisterFile registerFileTable = new RegisterFile();

		registerFileTable.storeDataToRegister(RegisterEnum.R1, (short) 100);
		registerFileTable.storeDataToRegister(RegisterEnum.R3, (short) 2230);

		System.out.println(registerFileTable
				.loadDataFromRegister(RegisterEnum.R1));

		registerFileTable.printregisterFileTable();

	}

}
