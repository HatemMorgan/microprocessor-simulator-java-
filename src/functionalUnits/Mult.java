package functionalUnits;
import memory.Clock;


public class Mult {
	
	private int multCycles;
	private Object[] reservationStationsName ;
	
	
	public Mult (int numberOfCycles,Object[] reservationStationsName){
		this.multCycles = numberOfCycles;
		this.reservationStationsName = reservationStationsName;
	}
	
	public void setMULTCycles(int time){
		multCycles = time;
	}
	
	
	
	public Object[] getReservationStationsName() {
		return reservationStationsName;
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
