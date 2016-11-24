package functionalUnits;

public class Nand {

	public Short nand(Short num1, Short num2) {
		return (short) ~(num1 & num2);
	}
}
