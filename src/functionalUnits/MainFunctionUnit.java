package functionalUnits;

public class MainFunctionUnit {

	private Adder adder;
	private Mult mult;
	private Nand nand;
	private String[] loadReservationStationsNames;
	private String[] storeReservationStationsNames;
	private static MainFunctionUnit instance;

	private MainFunctionUnit(int adderNumCycles, int multNumCycles,
			int nandNumCycles, String[] adderReservationStations,
			String[] multReservationStations, String[] nandReservationStations,
			String[] loadReservationStationsNames,
			String[] storeReservationStationsNames) {

		this.adder = new Adder(adderNumCycles, adderReservationStations);
		this.mult = new Mult(multNumCycles, multReservationStations);
		this.nand = new Nand(nandNumCycles, nandReservationStations);
		this.loadReservationStationsNames = loadReservationStationsNames;
		this.storeReservationStationsNames = storeReservationStationsNames;

	}

	public synchronized static MainFunctionUnit getInstance() {
		return instance;
	}

	public static void init(int adderNumCycles, int multNumCycles,
			int nandNumCycles, String[] adderReservationStations,
			String[] multReservationStations, String[] nandReservationStations,
			String[] loadReservationStationsNames,
			String[] storeReservationStationsNames) {
		
		instance = new MainFunctionUnit(adderNumCycles, multNumCycles,
				nandNumCycles, adderReservationStations,
				multReservationStations, nandReservationStations,
				loadReservationStationsNames, storeReservationStationsNames);
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

	public String[] getLoadReservationStationsNames() {
		return loadReservationStationsNames;
	}

	public String[] getStoreReservationStationsNames() {
		return storeReservationStationsNames;
	}
	
	

}
