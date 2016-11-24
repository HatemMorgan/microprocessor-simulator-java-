package functionalUnits;

public class Mult {
	
	public String mul(String sourceReg1,String sourceReg2)
	{
		int regA=Integer.parseInt(sourceReg1);
		int regB=Integer.parseInt(sourceReg2);
		int result = regB*regA;
		String resultString =""+result;
		return resultString;
	}
	
	
}