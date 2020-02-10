package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladjihStudenataException;

public class FakultetRacunalstva extends ObrazovnaUstanova implements Diplomski {

	public static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	public FakultetRacunalstva(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti,
			Ispit[] ispiti) {
		super(naziv, predmeti, profesori, studenti, ispiti);

	}

	@Override
	public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer ocjenaPismenogDjelaDiplomskogRada,
			Integer ocjenaObraneDiplomskogRada) {

		BigDecimal bd3 = new BigDecimal("3");
		BigDecimal bd5 = new BigDecimal("5");

		BigDecimal konacnaOcjena = new BigDecimal("1");
		try {
			konacnaOcjena = (bd3.multiply(odrediProsjekOcjenaNaIspitima(ispiti))
					.add(new BigDecimal(ocjenaPismenogDjelaDiplomskogRada))
					.add(new BigDecimal(ocjenaObraneDiplomskogRada))).divide(bd5);
		} catch (NemoguceOdreditiProsjekStudentaException greska) {
			logger.info(greska.getMessage(), greska);
			System.out.println(greska.getMessage());
		}

		return konacnaOcjena;

	}

	@Override
	public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmladjihStudenataException {

		Ispit[] sviIspiti = getIspiti();
		Student[] sviStudenti = getStudenti();

		Student najuspjesnijiStudent = sviStudenti[0];

		Ispit[] ispitiNajboljegStudenta = filtrirajIspitePoStudentu(sviIspiti, najuspjesnijiStudent);
		BigDecimal prosjekNajboljegStudenta = new BigDecimal("0");
		try {
			prosjekNajboljegStudenta = odrediProsjekOcjenaNaIspitima(ispitiNajboljegStudenta);
		} catch (NemoguceOdreditiProsjekStudentaException greska) {
			logger.info(greska.getMessage(), greska);
			System.out.println(greska.getMessage());
		}

		for (Student student : sviStudenti) {
			Ispit[] ispitiStudenta = filtrirajIspitePoStudentu(sviIspiti, student);

			BigDecimal prosjecnaOcjenaStudenta = new BigDecimal("0");

			try {
				prosjecnaOcjenaStudenta = odrediProsjekOcjenaNaIspitima(ispitiStudenta);
			} catch (NemoguceOdreditiProsjekStudentaException greska) {
				logger.info(greska.getMessage(), greska);
				System.out.println(greska.getMessage());
			}
			if (prosjecnaOcjenaStudenta.compareTo(prosjekNajboljegStudenta) >= 0) {
				if (student.getDatumRodenja().compareTo(najuspjesnijiStudent.getDatumRodenja()) >= 0) {
					najuspjesnijiStudent = student;
					prosjekNajboljegStudenta = prosjecnaOcjenaStudenta;
				} else if (student.getDatumRodenja().compareTo(najuspjesnijiStudent.getDatumRodenja()) == 0) {
					logger.info("Pronadeno je više najmladih studenata s istim datumom rodenja");
					throw new PostojiViseNajmladjihStudenataException(
							"Pronaðeno je više najmladih studenata s istim datumom rodenja: " + student.getIme() + " "
									+ student.getPrezime() + ", " + najuspjesnijiStudent.getIme() + " "
									+ najuspjesnijiStudent.getPrezime());

				}
			}
		}

		return najuspjesnijiStudent;
	}

	@Override
	public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {

		Ispit[] sviIspiti = getIspiti();
		Ispit[] ispitiSaGodine = new Ispit[sviIspiti.length];

		int brojac = 0;

		for (Ispit x : sviIspiti) {
			if (x.getDatumIVrijeme().getYear() == godina) {
				ispitiSaGodine[brojac++] = x;
			}
		}

		Ispit[] filtriraniIspitiSaGodine = Arrays.copyOf(ispitiSaGodine, brojac);

		Student[] sviStudenti = getStudenti();

		Student najuspjesnijiStudent = sviStudenti[0];

		Ispit[] ispitiNajboljegStudenta = filtrirajIspitePoStudentu(filtriraniIspitiSaGodine, najuspjesnijiStudent);

		int brojacOdlicnihOcjenaNajboljegStudenta = 0;

		for (int i = 0; i < ispitiNajboljegStudenta.length; i++) {
			if (ispitiNajboljegStudenta[i].getOcjena() == 5) {
				brojacOdlicnihOcjenaNajboljegStudenta = brojacOdlicnihOcjenaNajboljegStudenta + 1;
			}
		}

		for (Student student : sviStudenti) {
			Ispit[] ispitiStudenta = filtrirajIspitePoStudentu(filtriraniIspitiSaGodine, student);

			int brojacOdlicnihOcjenaStudenta = 0;
			for (int i = 0; i < ispitiStudenta.length; i++) {
				if (ispitiStudenta[i].getOcjena() == 5) {
					brojacOdlicnihOcjenaStudenta = brojacOdlicnihOcjenaStudenta + 1;
				}
			}
		}

		return najuspjesnijiStudent;

	}

}
