package reservationStations;

import java.util.Hashtable;

import registers.Register;

public class ReservationsStationTable {
	Hashtable<String,ReservationStationEntry> reservationsStationTable;
	
	public ReservationsStationTable(String[] reservationStationNames){
		reservationsStationTable = new Hashtable<String, ReservationStationEntry>();
		init(reservationStationNames);
	}
	
	private void init (String [] reservationStationNames){
		for(int i=0;i<reservationStationNames.length;i++){
			ReservationStationEntry newEntry = new ReservationStationEntry(false, null, null, null, null, null, null, null);
			reservationsStationTable.put(reservationStationNames[i],newEntry);
		}
	}
	
	
	private void insertIntoReservationStation(String reservationStationName,Operation op ,Register vj,Register vk, Integer qj,Integer qk,Integer destination , Integer address ){
		// check if there is a reservation station available (not busy) with this reservationStationName
		if(!reservationsStationTable.get(reservationStationName).isBusy()){
			ReservationStationEntry reservationStationEntry =  reservationsStationTable.get(reservationStationName);
			reservationStationEntry.setBusy(true);
			reservationStationEntry.setOp(op);
			reservationStationEntry.setAddress(address);
			reservationStationEntry.setDestination(destination);
			reservationStationEntry.setQj(qj);
			reservationStationEntry.setQk(qk);
			reservationStationEntry.setVj(vj);
			reservationStationEntry.setVk(vk);
		}else{
			System.out.println("No free Reservation station so you have to wait ");
		}
	}
	
	
	// TODO writing result : removing the entry and update the waiting entries 

	private void writeResult(String reservationStationName){
		
	}
	
	
	
	
}
