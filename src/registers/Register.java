package registers;

public class Register {

	private Short data;

	public Register(Short data) {
		this.data = data;
	}

	public Short getData() {
		return data;
	}

	public void setData(Short data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Register [data=" + data + "]";
	}

}
