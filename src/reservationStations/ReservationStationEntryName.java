package reservationStations;

import functionalUnits.FunctionalUnitType;

public class ReservationStationEntryName {

	private String name ;
	private FunctionalUnitType functionalUnitType ;
	
	
	public ReservationStationEntryName(String name,
			FunctionalUnitType functionalUnitType) {
		this.name = name;
		this.functionalUnitType = functionalUnitType;
	}


	public String getName() {
		return name;
	}


	public FunctionalUnitType getFunctionalUnitType() {
		return functionalUnitType;
	}
	
	
	
}
