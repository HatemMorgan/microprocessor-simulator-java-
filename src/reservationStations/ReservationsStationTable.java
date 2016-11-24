package reservationStations;

import java.util.Hashtable;
import registers.Register;
import reorderBuffer.ROB;

public class ReservationsStationTable {
	Hashtable<String, ReservationStationEntry> reservationsStationTable;

	public ReservationsStationTable(String[] reservationStationNames) {
		reservationsStationTable = new Hashtable<String, ReservationStationEntry>();
		init(reservationStationNames);
	}

	private void init(String[] reservationStationNames) {
		for (int i = 0; i < reservationStationNames.length; i++) {
			ReservationStationEntry newEntry = new ReservationStationEntry(
					false, null, null, null, null, null, null, null);
			reservationsStationTable.put(reservationStationNames[i], newEntry);
		}
	}

	private void insertIntoReservationStation(String reservationStationName,
			Operation op, String vj, String vk, Integer qj, Integer qk,
			Integer destination, Integer address) {
		// check if there is a reservation station available (not busy) with
		// this reservationStationName
		if (!reservationsStationTable.get(reservationStationName).isBusy()) {
			ReservationStationEntry reservationStationEntry = reservationsStationTable
					.get(reservationStationName);
			reservationStationEntry.setBusy(true);
			reservationStationEntry.setOp(op);
			reservationStationEntry.setAddress(address);
			reservationStationEntry.setDestination(destination);
			reservationStationEntry.setQj(qj);
			reservationStationEntry.setQk(qk);
			reservationStationEntry.setVj(vj);
			reservationStationEntry.setVk(vk);
		} else {
			System.out
					.println("No free Reservation station so you have to wait ");
		}
	}

	// emptying reservation station entry with specified reservationStationName
	// when writing results

	private void writeResult(String reservationStationName) {
		if (!reservationsStationTable.containsKey(reservationStationName)) {
			System.out.println("wrong reservation station name");
			return;
		}
		// emptying reservation station entry
		if (reservationsStationTable.get(reservationStationName).isBusy()) {
			ReservationStationEntry reservationStationEntry = reservationsStationTable
					.get(reservationStationName);
			reservationStationEntry.setBusy(false);
			reservationStationEntry.setOp(null);
			reservationStationEntry.setAddress(null);
			reservationStationEntry.setDestination(null);
			reservationStationEntry.setQj(null);
			reservationStationEntry.setQk(null);
			reservationStationEntry.setVj(null);
			reservationStationEntry.setVk(null);
			return;
		} else {
			System.out.println("Reservation station: " + reservationStationName
					+ " is already empty");
		}

	}

	private void passResultToWaitingReservationStation(String value,
			Integer ROBNum) {

		for (ReservationStationEntry reservationEntry : reservationsStationTable
				.values()) {
			if (!reservationEntry.isBusy()) {
				continue;
			}

			if (reservationEntry.getQj() != null && reservationEntry.getQj().equals(ROBNum)) {
				System.out.println("updated Vj with value :" + value
						+ " from ROB: #" + ROBNum);
				reservationEntry.setVj(value);
			}
			if (reservationEntry.getQk() != null && reservationEntry.getQk().equals(ROBNum)) {
				System.out.println("updated Vk with value :" + value
						+ " from ROB: #" + ROBNum);
				reservationEntry.setVk(value);
			}
		}
	}

	private void printReservationStationsTable(){
		for (String key : reservationsStationTable.keySet()) {
			System.out.println(key+" : "+ reservationsStationTable.get(key).toString());
		}
	}
	
	public static void main(String[] args) {
		String [] reservationStationNames = {"load1","load2","store1","add","mult"};
		ReservationsStationTable reservationsStationTable = new ReservationsStationTable(reservationStationNames);
		
		reservationsStationTable.insertIntoReservationStation("load1", Operation.LD,"2","3",null,null,1,null);
		reservationsStationTable.insertIntoReservationStation("mult", Operation.MUL,null,"6",1,null,1,null);
		
		reservationsStationTable.printReservationStationsTable();
		
		reservationsStationTable.writeResult("load1");
		reservationsStationTable.passResultToWaitingReservationStation("200", 1);
		
		System.out.println("------------------------------------------------------");
		reservationsStationTable.printReservationStationsTable();
		
		
		
	}
}
