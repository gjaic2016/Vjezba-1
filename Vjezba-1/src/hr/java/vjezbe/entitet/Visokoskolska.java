package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Arrays;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

public interface Visokoskolska {

	BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, Integer ocjenaPismenogDjelaZavrsnogRada,
			Integer ocjenaObraneZavrsnogRada);

	default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti) throws NemoguceOdreditiProsjekStudentaException {

		int brojac = 0;
		BigDecimal sumeOcjena = new BigDecimal(0);

//		Ispit[] filtriraniIspiti = filtrirajPolozeneIspite(ispiti);

		for (Ispit x : ispiti) {
			if (x.getOcjena() == 1) {
				throw new NemoguceOdreditiProsjekStudentaException("Nije mogu�e odrediti prosjek ocjena studenta "
						+ x.getStudent().getIme() + " " + x.getStudent().getPrezime()
						+ "zbog ocjene nedovoljan iz predmeta " + x.getPredmet().getNaziv());

			} else {

				sumeOcjena = sumeOcjena.add(new BigDecimal(x.getOcjena()));
				brojac += 1;
			}

		}

		BigDecimal djelitelj = new BigDecimal(brojac);

		BigDecimal prosjek = sumeOcjena.divide(djelitelj);

		return prosjek;
	}

	private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti) {

		int brojacPozitivnihOcjena = 0;

		for (int i = 0; i < ispiti.length; i++) {
			if (ispiti[i].getOcjena() > 1) {
				brojacPozitivnihOcjena++;

			}
		}

		Ispit[] filtriraniIspiti = new Ispit[brojacPozitivnihOcjena];

		for (int i = 0; i < filtriraniIspiti.length; i++) {
			for (int j = 0; j < ispiti.length; j++) {
				if (ispiti[j].getOcjena() > 1) {
					filtriraniIspiti[i] = ispiti[j];
					i++;
				}
			}
		}

		return filtriraniIspiti;
	}

	default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti, Student student) {

		Ispit[] filtrirajIspitPoStudentu = new Ispit[ispiti.length];

		int brojac = 0;

		for (Ispit x : ispiti) {
			if (x.getStudent().getJmbag().equals(x.getStudent().getJmbag())) {
				filtrirajIspitPoStudentu[brojac++] = x;
			}

		}

		filtrirajIspitPoStudentu = Arrays.copyOf(filtrirajIspitPoStudentu, brojac);
		return filtrirajIspitPoStudentu;
	}

}
