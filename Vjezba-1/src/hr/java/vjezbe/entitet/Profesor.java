package hr.java.vjezbe.entitet;

public class Profesor extends Osoba {

	private String sifra;
	private String titula;

	public Profesor(Long id, String sifra, String ime, String prezime, String titula) {
		super(id, ime, prezime);
		this.sifra = sifra;
		this.titula = titula;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getIme() {
		return super.getIme();
	}

	public void setIme(String ime) {
		super.setIme(ime);
	}

	public String getPrezime() {
		return super.getPrezime();
	}

	public void setPrezime(String prezime) {
		super.setPrezime(prezime);
	}

	public String getTitula() {
		return titula;
	}

	public void setTitula(String titula) {
		this.titula = titula;
	}

}
