package functionalUnits;
import memory.Clock;
import java.util.*;
import java.math.*;

public class Mult {
	
	private int multCycles;
	
	
	public Mult (int numberOfCycles){
		this.multCycles = numberOfCycles;
	}
	
	public void setMULTCycles(int time){
		multCycles = time;
	}
	
	public int[] mul(Short sourceReg1,Short sourceReg2)
	{
		int currentClock = Clock.counter.intValue()+multCycles;
		while(true)
		{
			if(currentClock == Clock.counter.intValue()){
				break;
			}
		}
		
		
		/*int regA=Integer.parseInt(sourceReg1);
		int regB=Integer.parseInt(sourceReg2);*/
		short result = (short)(sourceReg1.shortValue()*sourceReg2.shortValue());
		//String resultString =""+result;
		return new int[] {(int)result,currentClock};
	}
}
