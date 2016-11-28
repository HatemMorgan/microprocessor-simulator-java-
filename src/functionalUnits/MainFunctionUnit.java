package functionalUnits;

import reservationStations.ReservationStationEntry;
import reservationStations.ReservationsStationTable;

public class MainFunctionUnit {

	private Adder adder;
	private Mult mult;
	private Nand nand;
	private Object[] loadReservationStationsNames;
	private Object[] storeReservationStationsNames;
	private Object[] callReservationStationsNames;
	int callNumCycles;
	private static MainFunctionUnit instance;

	private MainFunctionUnit(int adderNumCycles, int multNumCycles,
			int nandNumCycles, int callNumCycles,
			Object[] adderReservationStations,
			Object[] multReservationStations, Object[] nandReservationStations,
			Object[] loadReservationStationsNames,
			Object[] storeReservationStationsNames,
			Object[] callReservationStationsNames) {

		this.adder = new Adder(adderNumCycles, adderReservationStations);
		this.mult = new Mult(multNumCycles, multReservationStations);
		this.nand = new Nand(nandNumCycles, nandReservationStations);
		this.loadReservationStationsNames = loadReservationStationsNames;
		this.storeReservationStationsNames = storeReservationStationsNames;
		this.callReservationStationsNames = callReservationStationsNames;
		this.callNumCycles = callNumCycles;
	}

	public synchronized static MainFunctionUnit getInstance() {
		return instance;
	}

	public static void init(int adderNumCycles, int multNumCycles,
			int nandNumCycles, int callNumCycles,
			Object[] adderReservationStations,
			Object[] multReservationStations, Object[] nandReservationStations,
			Object[] loadReservationStationsNames,
			Object[] storeReservationStationsNames,
			Object[] callReservationStationsNames) {
		instance = new MainFunctionUnit(adderNumCycles, multNumCycles,
				nandNumCycles, callNumCycles, adderReservationStations,
				multReservationStations, nandReservationStations,
				loadReservationStationsNames, storeReservationStationsNames,
				callReservationStationsNames);
	}

	public Adder getAdder() {
		return adder;
	}

	public Mult getMult() {
		return mult;
	}

	public Nand getNand() {
		return nand;
	}

	public Object[] getLoadReservationStationsNames() {
		return loadReservationStationsNames;
	}

	public Object[] getStoreReservationStationsNames() {
		return storeReservationStationsNames;
	}

	public Object[] getCallReservationStationsNames() {
		return callReservationStationsNames;
	}

	public int getCallNumCycles() {
		return callNumCycles;
	}

	public String getAvailableReservationStation(
			FunctionalUnitsType functionUnitType,
			ReservationsStationTable reservationsStationTable) {

		Object[] reservationStationsNames;

		switch (functionUnitType) {

		case ADDER:
			reservationStationsNames = adder.getReservationStationsName();
			break;
		case LOAD:
			reservationStationsNames = getLoadReservationStationsNames();
			break;
		case MULT:
			reservationStationsNames = mult.getReservationStationsName();
			break;
		case NAND:
			reservationStationsNames = nand.getReservationStationsName();
			break;
		case STORE:
			reservationStationsNames = getStoreReservationStationsNames();
			break;
		case CALL:
			reservationStationsNames = getCallReservationStationsNames();
			break;
		default:
			reservationStationsNames = null;

		}

		for (int i = 0; i < reservationStationsNames.length; i++) {
			ReservationStationEntry reservationStationEntry = reservationsStationTable
					.getReservationStationEntry(reservationStationsNames[i]
							.toString());
			if (!reservationStationEntry.isBusy()) {
				return reservationStationsNames[i].toString();
			}
		}

		return null;

	}

}
