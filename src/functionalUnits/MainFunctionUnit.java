package functionalUnits;

public class MainFunctionUnit {

	private Adder adder;
	private Mult mult;
	private Nand nand;
	private static MainFunctionUnit instance;

	private MainFunctionUnit(int adderNumCycles, int multNumCycles,
			int nandNumCycles) {
	
		this.adder = new Adder(adderNumCycles);
		this.mult = new Mult(multNumCycles);
		this.nand = new Nand(nandNumCycles);
		
	}

	public synchronized static MainFunctionUnit getInstance() {
		return instance;
	}

	public static void  init(int adderNumCycles, int multNumCycles, int nandNumCycles) {
		instance = new MainFunctionUnit(adderNumCycles, multNumCycles,
				nandNumCycles);
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

	
	
}
