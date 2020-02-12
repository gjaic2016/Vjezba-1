package hr.java.vjezbe.entitet;

import java.time.LocalDate;

public class Student extends Osoba {

	private String jmbag;
	private LocalDate datumRodenja;

	public Student(String ime, String prezime, String jmbag, LocalDate datumRodenja) {
		super(ime, prezime);
		this.jmbag = jmbag;
		this.datumRodenja = datumRodenja;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((datumRodenja == null) ? 0 : datumRodenja.hashCode());
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (datumRodenja == null) {
			if (other.datumRodenja != null)
				return false;
		} else if (!datumRodenja.equals(other.datumRodenja))
			return false;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}

}
