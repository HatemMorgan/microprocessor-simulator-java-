package functionalUnits;
import memory.Clock;
import java.util.*;

import javax.lang.model.SourceVersion;

import java.math.*;


public class Adder{

	private int addCycles;
	private int addiCycles;
	private int subCycles;
	
	
	public void setADDCycleTime(int time){
		this.addCycles = time;
	}
	
	public void setADDICycleTime(int time){
		this.addiCycles = time;
	}
	
	public void setSUBCycleTime(int time){
		this.subCycles = time;
	}
	
	
	public Adder()
	{
		
	}
	public Short add(Short sourceReg1,Short sourceReg2)
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
		return result;		
	}
	public Short addi(Short sourceReg1,short number )
	{
		int currentClock = Clock.counter.intValue()+addiCycles;
		while(true)
		{
			if(currentClock == Clock.counter.intValue()){
				break;
			}
		}
		//int regA=Integer.parseInt(sourceReg1);
		short result = (short)(number+sourceReg1.shortValue());
		//String resultString =""+result;
		return result;
				
	}
	public Short sub(Short sourceReg1,Short sourceReg2)
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
		return result;
	}
	
	
	
}
