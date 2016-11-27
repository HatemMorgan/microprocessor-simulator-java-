package functionalUnits;
import memory.Clock;
import java.util.*;

import javax.lang.model.SourceVersion;

import java.math.*;


public class Adder{

	private int addCycles;
	private int addiCycles;
	private int subCycles;
	
	

	
	
	public Adder(int numberOfCycles)
	{
		this.addCycles = numberOfCycles;
		this.subCycles = numberOfCycles;
		this.addiCycles = numberOfCycles;
	}
	
	public void setADDCycleTime(int time){
		this.addCycles = time;
	}
	
	public void setADDICycleTime(int time){
		this.addiCycles = time;
	}
	
	public void setSUBCycleTime(int time){
		this.subCycles = time;
	}
	
	public int[] add(Short sourceReg1,Short sourceReg2)
	{
		
		int currentClock = Clock.counter.intValue()+addCycles;
		while(true)
		{
		
			if(currentClock == Clock.counter.intValue()){
				break;
			}
		}
		
		/*int regA=Integer.parseInt(sourceReg1);
		int regB=Integer.parseInt(sourceReg2);*/
		short result =(short)(sourceReg1.shortValue()+sourceReg2.shortValue());
		//String resultString =""+result;
		return new int[] {(int)result,currentClock};		
	}
	
	public int[] sub(Short sourceReg1,Short sourceReg2)
	{
		int currentClock = Clock.counter.intValue()+subCycles;
		while(true)
		{
			if(currentClock == Clock.counter.intValue()){
				break;
			}
		}
		/*int regA=Integer.parseInt(sourceReg1);
		int regB=Integer.parseInt(sourceReg2);*/
		short result = (short)(sourceReg1.shortValue()-sourceReg2.shortValue());
		//String resultString =""+result;
		return new int[] {(int)result,currentClock};
	}
	
	
	
}
