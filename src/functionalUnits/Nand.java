package functionalUnits;


public class Nand {

	public int bitwiseAnd(int x,int y)
	{
		int result= x&y;
		return result;
	}
	
	public String nand(String sourceReg1,String sourceReg2)
	{
		int regA=Integer.parseInt(sourceReg1);
		int regB=Integer.parseInt(sourceReg2);
		int result = ~(bitwiseAnd(regA,regB));
		String resultString =""+result;
		return resultString;
	}
}
