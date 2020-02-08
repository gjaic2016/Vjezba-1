package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Arrays;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska {

	public VeleucilisteJave(String naziv, Predmet[] predmeti, Profesor[] profesori, Student[] studenti,
			Ispit[] ispiti) {
		super(naziv, predmeti, profesori, studenti, ispiti);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer ocjenaPismenogDjelaZavrsnogRada,
			Integer ocjenaObraneZavrsnogRada) {

		BigDecimal bd2 = new BigDecimal("2");
		BigDecimal bd4 = new BigDecimal("4");

		BigDecimal konacnaOcjena = (bd2.multiply(odrediProsjekOcjenaNaIspitima(ispiti))
				.add(new BigDecimal(ocjenaPismenogDjelaZavrsnogRada)).add(new BigDecimal(ocjenaObraneZavrsnogRada)))
						.divide(bd4);

		return konacnaOcjena;
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
		BigDecimal prosjekNajboljegStudenta = odrediProsjekOcjenaNaIspitima(ispitiNajboljegStudenta);

		for (Student student : sviStudenti) {
			Ispit[] ispitiStudenta = filtrirajIspitePoStudentu(filtriraniIspitiSaGodine, student);
			BigDecimal prosjecnaOcjenaStudenta = odrediProsjekOcjenaNaIspitima(ispitiStudenta);

			if (prosjecnaOcjenaStudenta.compareTo(prosjekNajboljegStudenta) >= 0) {
				najuspjesnijiStudent = student;
				prosjekNajboljegStudenta = prosjecnaOcjenaStudenta;
			}
		}

		return najuspjesnijiStudent;
	}
}
