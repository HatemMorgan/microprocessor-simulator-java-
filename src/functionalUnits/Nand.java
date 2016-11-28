package functionalUnits;
import memory.Clock;

import java.util.*;
import java.math.*;

public class Nand {
	
	private int nandCycles;
	private String[] reservationStationsName ;
	
	public Nand (int numberOfCycles,String[] reservationStationsName){
		this.nandCycles = numberOfCycles;
		this.reservationStationsName = reservationStationsName;
	}
	
	
	public void setMULTCycles(int time){
		nandCycles = time;
	}
	
		
	public short bitwiseAnd(short x,short y)
	{
		short result= (short)(x&y);
		return result;
	}
	
	public int[] nand(Short sourceReg1,Short sourceReg2)
	{
		int currentClock = Clock.counter.intValue()+nandCycles;
		while(true)
		{
			if(currentClock == Clock.counter.intValue()){
				break;
			}
		}
		/*int regA=Integer.parseInt(sourceReg1);
		int regB=Integer.parseInt(sourceReg2);*/
		short result = (short)~(bitwiseAnd(sourceReg1.shortValue(),sourceReg2.shortValue()));
		//String resultString =""+result;
		return new int[] {(int)result,currentClock};
	}
}

