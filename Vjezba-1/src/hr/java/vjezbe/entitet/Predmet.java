package hr.java.vjezbe.entitet;

import java.util.HashSet;
import java.util.Set;

public class Predmet {

	private String sifra;
	private String naziv;
	private Integer brojEctsBodova;
	private Profesor nositelj;
//	private Student[] studenti;
	private Set<Student> studenti = new HashSet<Student>();

	public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj) {
		super();
		this.sifra = sifra;
		this.naziv = naziv;
		this.brojEctsBodova = brojEctsBodova;
		this.nositelj = nositelj;
//		this.studenti = new Student[brojStudenata];
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Integer getBrojEctsBodova() {
		return brojEctsBodova;
	}

	public void setBrojEctsBodova(Integer brojEctsBodova) {
		this.brojEctsBodova = brojEctsBodova;
	}

	public Profesor getNositelj() {
		return nositelj;
	}

	public void setNositelj(Profesor nositelj) {
		this.nositelj = nositelj;
	}

//	public Student[] getStudenti() {
//		return studenti;
//	}
//
//	public void setStudenti(Student[] studenti) {
//		this.studenti = studenti;
//	}

	public Set<Student> getStudent() {
		return studenti;
	}

	public void setStudent(Set<Student> studenti) {
		this.studenti = studenti;
	}
}
