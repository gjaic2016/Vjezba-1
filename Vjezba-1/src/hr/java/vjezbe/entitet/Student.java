package hr.java.vjezbe.entitet;

import java.time.LocalDate;

public class Student extends Osoba {

	private String ime;
	private String prezime;
	private String jmbag;
	private LocalDate datumRodenja;

	public Student(String ime, String prezime, String jmbag, LocalDate datumRodenja) {
		super(ime, prezime);
		this.jmbag = jmbag;
		this.datumRodenja = datumRodenja;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getJmbag() {
		return jmbag;
	}

	public void setJmbag(String jmbag) {
		this.jmbag = jmbag;
	}

	public LocalDate getDatumRodenja() {
		return datumRodenja;
	}

	public void setDatumRodenja(LocalDate datumRodenja) {
		this.datumRodenja = datumRodenja;
	}

}
