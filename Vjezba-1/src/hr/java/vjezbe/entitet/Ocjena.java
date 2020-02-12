package hr.java.vjezbe.entitet;

public enum Ocjena {

	NEDOVOLJAN(1, "nedovoljan"), DOVOLJAN(2, "dovoljan"), DOBAR(3, "dobar"), VRLO_DOBAR(4, "vrlo dobar"),
	IZVRSTAN(5, "izvrstan");

	private String ocjenaString;
	private Integer ocjenaInt;

	private Ocjena(Integer ocjenaInt, String ocjenaString) {
		this.ocjenaInt = ocjenaInt;
		this.ocjenaString = ocjenaString;
	}

	public String getOcjenaString() {
		return ocjenaString;
	}

	public Integer getOcjenaInt() {
		return ocjenaInt;
	}
}
