package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska {

	public static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	public VeleucilisteJave(Long id, String naziv, List<Predmet> predmeti, List<Profesor> profesori,
			List<Student> studenti, List<Ispit> ispiti) {
		super(id, naziv, predmeti, profesori, studenti, ispiti);

	}

	@Override
	public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti,
			Integer ocjenaPismenogDjelaZavrsnogRada, Integer ocjenaObraneZavrsnogRada) {

		BigDecimal bd2 = new BigDecimal("2");
		BigDecimal bd4 = new BigDecimal("4");
		BigDecimal konacnaOcjena = new BigDecimal("1");

		try {
			konacnaOcjena = (bd2.multiply(odrediProsjekOcjenaNaIspitima(ispiti))
					.add(new BigDecimal(ocjenaPismenogDjelaZavrsnogRada)).add(new BigDecimal(ocjenaObraneZavrsnogRada)))
							.divide(bd4);
		} catch (NemoguceOdreditiProsjekStudentaException greska) {
			logger.info(greska.getMessage(), greska);
			System.out.println(greska.getMessage());
		}

		return konacnaOcjena;
	}

	@Override
	public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {

		List<Ispit> sviIspiti = getIspiti();
		List<Ispit> ispitiSaGodine = new ArrayList<Ispit>();

//		int brojac = 0;

		for (Ispit x : sviIspiti) {
			if (x.getDatumIVrijeme().getYear() == godina) {
				ispitiSaGodine.add(x);
			}
		}

		List<Ispit> filtriraniIspitiSaGodine = ispitiSaGodine;

//		int brojacPolja = 0;
//		Student[] studentiKojiSuIzasliNaIspit = new Student[getIspiti().size()];
//		for (Ispit ispit : getIspiti()) {
//			studentiKojiSuIzasliNaIspit[brojacPolja++] = ispit.getStudent();
//		}
//		Student[] sviStudenti = studentiKojiSuIzasliNaIspit;
////		List<Student> sviStudenti = getStudenti();
//		Student najuspjesnijiStudent = sviStudenti[0];

		List<Student> studentiKojiSuIzasliNaIspit = new ArrayList<Student>();
		for (Ispit ispit : getIspiti()) {
			studentiKojiSuIzasliNaIspit.add(ispit.getStudent());
		}
		List<Student> sviStudenti = new ArrayList<Student>(studentiKojiSuIzasliNaIspit);
		Student najuspjesnijiStudent = studentiKojiSuIzasliNaIspit.get(0);

		List<Ispit> ispitiNajboljegStudenta = filtrirajIspitePoStudentu(filtriraniIspitiSaGodine, najuspjesnijiStudent);

		BigDecimal prosjekNajboljegStudenta = new BigDecimal("0");
		try {
			prosjekNajboljegStudenta = odrediProsjekOcjenaNaIspitima(ispitiNajboljegStudenta);
		} catch (NemoguceOdreditiProsjekStudentaException greska) {
			logger.info(greska.getMessage(), greska);
			System.out.println(greska.getMessage());
		}

		for (Student student : sviStudenti) {
			List<Ispit> ispitiStudenta = filtrirajIspitePoStudentu(filtriraniIspitiSaGodine, student);
			BigDecimal prosjecnaOcjenaStudenta = new BigDecimal("0");
			try {
				prosjecnaOcjenaStudenta = odrediProsjekOcjenaNaIspitima(ispitiStudenta);
				if (prosjecnaOcjenaStudenta.compareTo(prosjekNajboljegStudenta) >= 0) {
					najuspjesnijiStudent = student;
					prosjekNajboljegStudenta = prosjecnaOcjenaStudenta;
				}
			} catch (NemoguceOdreditiProsjekStudentaException greska) {
				greska.printStackTrace();
				logger.info("Nije moguce odrediti prosjek studenta " + student.getIme() + " " + student.getPrezime()
						+ " zbog negativne ocjene na jednom od ispita. ");
			}

		}

		return najuspjesnijiStudent;
	}
}
