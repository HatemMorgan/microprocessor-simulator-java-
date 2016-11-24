package reservationStations;

import registers.RegisterEnum;

public class ReservationStationEntry {

	private boolean isBusy;
	private Operation op;
	private String vj;
	private String vk;
	private Integer qj;
	private Integer qk;
	private Integer destination;
	private Integer address ;
	
	public ReservationStationEntry(boolean isBusy, Operation op, String vj,
			String vk, Integer qj, Integer qk, Integer destination,
			Integer address) {
		this.isBusy = isBusy;
		this.op = op;
		this.vj = vj;
		this.vk = vk;
		this.qj = qj;
		this.qk = qk;
		this.destination = destination;
		this.address = address;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public Operation getOp() {
		return op;
	}

	public void setOp(Operation op) {
		this.op = op;
	}

	public String getVj() {
		return vj;
	}

	public void setVj(String vj) {
		this.vj = vj;
	}

	public String getVk() {
		return vk;
	}

	public void setVk(String vk) {
		this.vk = vk;
	}

	public Integer getQj() {
		return qj;
	}

	public void setQj(Integer qj) {
		this.qj = qj;
	}

	public Integer getQk() {
		return qk;
	}

	public void setQk(Integer qk) {
		this.qk = qk;
	}

	public Integer getDestination() {
		return destination;
	}

	public void setDestination(Integer destination) {
		this.destination = destination;
	}

	public Integer getAddress() {
		return address;
	}

	public void setAddress(Integer address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ReservationStationEntry [isBusy=" + isBusy + ", op=" + op
				+ ", vj=" + vj + ", vk=" + vk + ", qj=" + qj + ", qk=" + qk
				+ ", destination=" + destination + ", address=" + address + "]";
	}
	
	
	
	
	
	
	
	
}
