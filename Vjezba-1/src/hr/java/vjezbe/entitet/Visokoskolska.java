package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

public interface Visokoskolska {

	BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, Integer ocjenaPismenogDjelaZavrsnogRada,
			Integer ocjenaObraneZavrsnogRada);

	default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti)
			throws NemoguceOdreditiProsjekStudentaException {

		int brojac = 0;
		BigDecimal sumeOcjena = new BigDecimal(0);

//		Ispit[] filtriraniIspiti = filtrirajPolozeneIspite(ispiti);
		List<Ispit> filtriraniIspiti = filtrirajPolozeneIspite(ispiti);

		// FAILSAFE
		if (filtriraniIspiti.size() == 0) {
			return new BigDecimal(0);
		}

		for (Ispit x : ispiti) {
			if (x.getOcjena() == 1) {
				throw new NemoguceOdreditiProsjekStudentaException("Nije moguæe odrediti prosjek ocjena studenta "
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

	private List<Ispit> filtrirajPolozeneIspite(List<Ispit> ispiti) {

		int brojacPozitivnihOcjena = 0;

		for (int i = 0; i < ispiti.size(); i++) {
			if (ispiti.get(i).getOcjena() > 1) {
				brojacPozitivnihOcjena++;

			}
		}

		List<Ispit> filtriraniIspiti = new ArrayList<Ispit>();

		for (int i = 0; i < filtriraniIspiti.size(); i++) {
			for (int j = 0; j < ispiti.size(); j++) {
				if (ispiti.get(j).getOcjena() > 1) {
//					filtriraniIspiti[i] = ispiti[j];
					filtriraniIspiti.add(ispiti.get(j));
					i++;
				}
			}
		}

		return filtriraniIspiti;
	}

	default List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student student) {

		List<Ispit> filtrirajIspitPoStudentu = new ArrayList<Ispit>();

		for (Ispit x : ispiti) {
			if (x.getStudent().getJmbag().equals(x.getStudent().getJmbag())) {
				filtrirajIspitPoStudentu.add(x);
			}

		}

//		filtrirajIspitPoStudentu = Arrays.copyOf(filtrirajIspitPoStudentu, brojac);
		return filtrirajIspitPoStudentu;
	}

}
