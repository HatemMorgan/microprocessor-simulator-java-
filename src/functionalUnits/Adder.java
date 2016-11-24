package functionalUnits;

import java.util.*;
import java.math.*;
public class Adder{

	public Adder()
	{
		
	}
	public String add(String sourceReg1,String sourceReg2 )
	{
		int regA=Integer.parseInt(sourceReg1);
		int regB=Integer.parseInt(sourceReg2);
		int result = regB+regA;
		String resultString =""+result;
		return resultString;		
	}
	public String addi(String sourceReg1,int number )
	{
		int regA=Integer.parseInt(sourceReg1);
		int result = number+regA;
		String resultString =""+result;
		return resultString;
				
	}
	public String sub(String sourceReg1,String sourceReg2)
	{
		int regA=Integer.parseInt(sourceReg1);
		int regB=Integer.parseInt(sourceReg2);
		int result = regA-regB;
		String resultString =""+result;
		return resultString;
	}
	
	
	
}
