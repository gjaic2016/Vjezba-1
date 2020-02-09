package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Arrays;

public class FakultetRacunalstva extends ObrazovnaUstanova implements Diplomski {

	public FakultetRacunalstva(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti,
			Ispit[] ispiti) {
		super(naziv, predmeti, profesori, studenti, ispiti);

	}

	@Override
	public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer ocjenaPismenogDjelaDiplomskogRada,
			Integer ocjenaObraneDiplomskogRada) {

		BigDecimal bd3 = new BigDecimal("3");
		BigDecimal bd5 = new BigDecimal("5");

		BigDecimal konacnaOcjena = (bd3.multiply(odrediProsjekOcjenaNaIspitima(ispiti))
				.add(new BigDecimal(ocjenaPismenogDjelaDiplomskogRada)).add(new BigDecimal(ocjenaObraneDiplomskogRada)))
						.divide(bd5);

		return konacnaOcjena;

	}

	@Override
	public Student odrediStudentaZaRektorovuNagradu() {

		Ispit[] sviIspiti = getIspiti();
		Student[] sviStudenti = getStudenti();

		Student najuspjesnijiStudent = sviStudenti[0];

		Ispit[] ispitiNajboljegStudenta = filtrirajIspitePoStudentu(sviIspiti, najuspjesnijiStudent);
		BigDecimal prosjekNajboljegStudenta = odrediProsjekOcjenaNaIspitima(ispitiNajboljegStudenta);

		for (Student student : sviStudenti) {
			Ispit[] ispitiStudenta = filtrirajIspitePoStudentu(sviIspiti, student);
			BigDecimal prosjecnaOcjenaStudenta = odrediProsjekOcjenaNaIspitima(ispitiStudenta);
			if (prosjecnaOcjenaStudenta.compareTo(prosjekNajboljegStudenta) >= 0) {
				if (student.getDatumRodenja().compareTo(najuspjesnijiStudent.getDatumRodenja()) >= 0) {
					najuspjesnijiStudent = student;
					prosjekNajboljegStudenta = prosjecnaOcjenaStudenta;
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
