package hr.java.vjezbe.glavna;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.FakultetRacunalstva;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.ObrazovnaUstanova;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.entitet.VeleucilisteJave;

/**
 * @author Fluffy @
 */

public class Glavna {

	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	final static int GODINA = 1111;
	public static final int BROJ_STUDENATA = 2;
	private static final String FORMAT_DATUM = "dd.MM.yyyy.";
	private static final String FORMAT_DATUM_VRIJEME = "dd.MM.yyyy.HH:mm";

	public static void main(String[] args) {

		logger.info("Program je pokrenut...");

		Scanner skener = new Scanner(System.in);

		boolean provjeraWhilePetlja = false;
		int brojObrazovnihUstanova = 0;

		do {
			try {
				System.out.print("Unesite broj obrazovnih ustanova za unos: ");
				brojObrazovnihUstanova = skener.nextInt();
				logger.info("Unesen broj obrazovnih ustanova: " + brojObrazovnihUstanova);
				provjeraWhilePetlja = false;
			} catch (InputMismatchException greska) {
				System.out.println("Molimo upisite broj!");
				skener.nextLine();
				logger.error("Unesen tekst umjesto broja za broj ustanova!" + brojObrazovnihUstanova, greska);
				provjeraWhilePetlja = true;
			}
		} while (provjeraWhilePetlja);

		ObrazovnaUstanova[] obrazovnaUstanova = new ObrazovnaUstanova[brojObrazovnihUstanova];

		for (int i = 0; i < obrazovnaUstanova.length; i++) {

			System.out.println("Unesite podatke za " + (i + 1) + " obrazovnu ustanovu: ");

			provjeraWhilePetlja = false;
			int brojProfesora = 0;
			do {
				try {
					System.out.println("Unesite broj profesora: ");
					brojProfesora = skener.nextInt();
					logger.info("Unesen broj profesora: " + brojProfesora);
					skener.nextLine();
					provjeraWhilePetlja = false;

				} catch (InputMismatchException greska) {
					System.out.println("Molimo upisite broj profesora!");
					skener.nextLine();
					logger.error("Unesen tekst umjesto broja za broj profesora!" + brojProfesora, greska);
					provjeraWhilePetlja = true;
				}

			} while (provjeraWhilePetlja);
			Profesor[] profesori = unesiProfesora(skener, brojProfesora);

			provjeraWhilePetlja = false;
			int brojPredmeta = 0;
			do {
				try {
					System.out.println("Unesite broj predmeta: ");
					brojPredmeta = skener.nextInt();
					logger.info("Unesen broj predmeta: " + brojPredmeta);
					skener.nextLine();
					provjeraWhilePetlja = false;
				} catch (InputMismatchException greska) {
					System.out.println("Molimo upisite broj predmeta!");
					skener.nextLine();
					logger.error("Unesen tekst umjesto broja za broj predmeta!" + brojPredmeta, greska);
					provjeraWhilePetlja = true;
				}
			} while (provjeraWhilePetlja);
			Predmet[] predmeti = unesiPredmet(skener, profesori, brojPredmeta);

			Student[] studenti = unesiStudenta(skener, predmeti);

			provjeraWhilePetlja = false;
			int brojIspitnihRokova = 0;
			do {
				try {
					System.out.println("Unesite broj ispitnih rokova: ");
					brojIspitnihRokova = skener.nextInt();
					logger.info("Unesen broj ispitnih rokova: " + brojIspitnihRokova);
					provjeraWhilePetlja = false;
				} catch (InputMismatchException greska) {
					System.out.println("Molimo upisite broj predmeta!");
					skener.nextLine();
					logger.error("Unesen tekst umjesto broja za broj ispitnih rokova!" + brojIspitnihRokova, greska);
					provjeraWhilePetlja = true;
				}
			} while (provjeraWhilePetlja);

			Ispit[] ispiti = unesiIspit(skener, brojIspitnihRokova, predmeti, studenti);

			// TEST SORTIRANJA
			Arrays.sort(ispiti, (a, b) -> b.getOcjena().compareTo(a.getOcjena()));
			Arrays.sort(ispiti, (a, b) -> a.getStudent().getIme().compareTo(b.getStudent().getIme()));
			Arrays.sort(ispiti, (a, b) -> a.getStudent().getPrezime().compareTo(b.getStudent().getPrezime()));

			for (Ispit x : ispiti) {
				if (x.getOcjena().equals(5)) {
					System.out.println("Student " + x.getStudent().getPrezime() + " " + x.getStudent().getIme()
							+ " je dobio ocjenu izvrstan na predmetu: " + x.getPredmet().getNaziv());
				}
			}

			for (int t = 0; t < predmeti.length; t++) {
				System.out.println("\nStudenti na predmetu " + predmeti[t].getNaziv() + " su: ");
				for (int p = 0; p < predmeti[t].getStudenti().length; p++) {
					System.out.println(predmeti[t].getStudenti()[p].getIme());
				}
			}

			// ODABIR USTANOVE
			int odabirUstanove = 0;
			provjeraWhilePetlja = false;
			do {

				try {
					System.out.println("Odaberite obrazovnu istanovu za navedene podatke:\n"
							+ "1 - Veleuciliste Jave, 2 - Fakultet racunalstva ");
					odabirUstanove = skener.nextInt();
					logger.error("Unesen broj obrazovne ustanove: " + odabirUstanove);
				} catch (InputMismatchException greska) {
					System.out.println("Molimo upisite broj odabira obrazovne ustanove!");
					skener.nextLine();
					logger.error("Unesen tekst umjesto broja za broj obrazovnih ustanova!" + odabirUstanove, greska);
					provjeraWhilePetlja = true;
				}

			} while (odabirUstanove > 2 || odabirUstanove < 1);
			skener.nextLine();

			if (odabirUstanove == 1) {

				System.out.println("Unesite naziv obrazovne ustanove: ");
				String nazivObrazovneUstanove = skener.nextLine();

				VeleucilisteJave veleucilisteJave = new VeleucilisteJave(nazivObrazovneUstanove, predmeti, profesori,
						studenti, ispiti);

				for (Student x : veleucilisteJave.getStudenti()) {

					provjeraWhilePetlja = false;
					Integer ocjenaPismenogDjelaZavrsnogRada = 0;
					do {
						try {
							System.out.println(
									"Unesite ocjenu završnog rada za studenta " + x.getIme() + " " + x.getPrezime());
							ocjenaPismenogDjelaZavrsnogRada = skener.nextInt();
							logger.info("Unesena ocjena zavrsnog rada za studenta " + x.getIme() + " " + x.getPrezime()
									+ " je: " + ocjenaPismenogDjelaZavrsnogRada);
							provjeraWhilePetlja = false;
						} catch (InputMismatchException greska) {
							System.out.println("Molimo upisati ocjenu od 1 do 5!");
							skener.nextLine();
							logger.error("Unesen tekst umjesto broj za ocjenu pismenog zavrsnog rada!"
									+ ocjenaPismenogDjelaZavrsnogRada, greska);
							provjeraWhilePetlja = true;
						}
					} while (provjeraWhilePetlja);

					provjeraWhilePetlja = false;
					Integer ocjenaObraneZavrsnogRada = 0;
					do {
						try {
							System.out.println("Unesite ocjenu obrane završnog rada za studenta " + x.getIme() + " "
									+ x.getPrezime());
							ocjenaObraneZavrsnogRada = skener.nextInt();
							logger.info("Unesena ocjena obrane zavrsnog rada za studenta " + x.getIme() + " "
									+ x.getPrezime() + " je: " + ocjenaObraneZavrsnogRada);
							provjeraWhilePetlja = false;
						} catch (InputMismatchException greska) {
							System.out.println("Molimo upisati ocjenu od 1 do 5!");
							skener.nextLine();
							logger.error("Unesen tekst umjesto broj za ocjenu obrane zavrsnog rada!"
									+ ocjenaObraneZavrsnogRada, greska);
							provjeraWhilePetlja = true;
						}
					} while (provjeraWhilePetlja);

					System.out.println("Konacna ocjena studija studenta " + x.getIme() + " " + x.getPrezime() + " je: "
							+ veleucilisteJave.izracunajKonacnuOcjenuStudijaZaStudenta(
									veleucilisteJave.filtrirajIspitePoStudentu(ispiti, x),
									ocjenaPismenogDjelaZavrsnogRada, ocjenaObraneZavrsnogRada));

				}

				System.out.println("Najbolji student " + GODINA + ". godine je: "
						+ veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(GODINA).getIme() + " "
						+ veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(GODINA).getPrezime() + ", JMBAG: "
						+ veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(GODINA).getJmbag());

				obrazovnaUstanova[i] = veleucilisteJave;
				obrazovnaUstanova[i].setNaziv(nazivObrazovneUstanove);

			} else if (odabirUstanove == 2) {

				System.out.println("Unesite naziv obrazovne ustanove: ");
				String nazivObrazovneUstanove = skener.nextLine();

				FakultetRacunalstva fakultetRacunalstva = new FakultetRacunalstva(nazivObrazovneUstanove, predmeti,
						profesori, studenti, ispiti);

				for (Student x : fakultetRacunalstva.getStudenti()) {

					provjeraWhilePetlja = false;
					Integer ocjenaPismenogDjelaDiplomskogRada = 0;
					do {
						try {
							System.out.println(
									"Unesite ocjenu diplomskog rada za studenta " + x.getIme() + " " + x.getPrezime());
							ocjenaPismenogDjelaDiplomskogRada = skener.nextInt();
							logger.info("Unesena ocjena diplomskog rada za studenta " + x.getIme() + " "
									+ x.getPrezime() + " je: " + ocjenaPismenogDjelaDiplomskogRada);
							provjeraWhilePetlja = false;
						} catch (InputMismatchException greska) {
							System.out.println("Molimo upisati ocjenu od 1 do 5!");
							skener.nextLine();
							logger.error("Unesen tekst umjesto broj za ocjenu diplomskog rada!"
									+ ocjenaPismenogDjelaDiplomskogRada, greska);
							provjeraWhilePetlja = true;
						}

					} while (provjeraWhilePetlja);

					provjeraWhilePetlja = false;
					Integer ocjenaObraneDiplomskogRada = 0;
					do {
						try {
							System.out.println("Unesite ocjenu obrane diplomskog rada za studenta " + x.getIme() + " "
									+ x.getPrezime());
							ocjenaObraneDiplomskogRada = skener.nextInt();
							logger.info("Unesena ocjena diplomskog rada za studenta " + x.getIme() + " "
									+ x.getPrezime() + " je: " + ocjenaObraneDiplomskogRada);
							provjeraWhilePetlja = false;
						} catch (InputMismatchException greska) {
							System.out.println("Molimo upisati ocjenu od 1 do 5!");
							skener.nextLine();
							logger.error("Unesen tekst umjesto broj za ocjenu obrane diplomskog rada!"
									+ ocjenaObraneDiplomskogRada, greska);
							provjeraWhilePetlja = true;
						}

					} while (provjeraWhilePetlja);

					System.out.println("Konacna ocjena studija studenta " + x.getIme() + " " + x.getPrezime() + " je:"
							+ fakultetRacunalstva.izracunajKonacnuOcjenuStudijaZaStudenta(
									fakultetRacunalstva.filtrirajIspitePoStudentu(ispiti, x),
									ocjenaPismenogDjelaDiplomskogRada, ocjenaObraneDiplomskogRada));

					System.out.println("Najbolji student" + GODINA + ". godine je: "
							+ fakultetRacunalstva.odrediNajuspjesnijegStudentaNaGodini(GODINA).getIme()
							+ fakultetRacunalstva.odrediNajuspjesnijegStudentaNaGodini(GODINA).getPrezime()
							+ fakultetRacunalstva.odrediNajuspjesnijegStudentaNaGodini(GODINA).getJmbag());

					System.out.println("Student koji je osvojio rektorovu nagradu je: "
							+ fakultetRacunalstva.odrediStudentaZaRektorovuNagradu().getIme() + " "
							+ fakultetRacunalstva.odrediStudentaZaRektorovuNagradu().getPrezime() + ", JMBAG: "
							+ fakultetRacunalstva.odrediStudentaZaRektorovuNagradu().getJmbag());

					obrazovnaUstanova[i] = fakultetRacunalstva;
					obrazovnaUstanova[i].setNaziv(nazivObrazovneUstanove);

				}

			}

		}
		skener.close();

	}

	private static Ispit[] unesiIspit(Scanner skener, int brojIspitnihRokova, Predmet[] predmeti, Student[] studenti) {

		Ispit[] ispiti = new Ispit[brojIspitnihRokova];

		for (int i = 0; i < ispiti.length; i++) {

			System.out.println("Unesite " + (i + 1) + " ispitni rok:");

			Integer odabirBrojPredmeta;
			do {
				System.out.println("Odaberite predmet: ");

				for (int j = 0; j < predmeti.length; j++) {
					System.out.println((j + 1) + ". " + predmeti[j].getNaziv());

				}
				odabirBrojPredmeta = skener.nextInt();

			} while (odabirBrojPredmeta < 1 || odabirBrojPredmeta > predmeti.length);
			skener.nextLine();
			Predmet odabraniPredmet = predmeti[odabirBrojPredmeta - 1];

			Integer odabirBrojStudenta = 0;
			do {
				System.out.println("Odaberite studenta: ");

				for (int k = 0; k < odabraniPredmet.getStudenti().length; k++) {
					System.out.println((k + 1) + ". " + odabraniPredmet.getStudenti()[k].getIme() + " "
							+ odabraniPredmet.getStudenti()[k].getPrezime());
				}
				odabirBrojStudenta = skener.nextInt();

			} while (odabirBrojStudenta < 1 || odabirBrojStudenta > studenti.length);
			skener.nextLine();

			Student odabraniStudent = odabraniPredmet.getStudenti()[odabirBrojStudenta - 1];

			System.out.println("Unesite ocjenu (1-5) za " + (i + 1) + ". ispitni rok: ");

			while (!skener.hasNextInt()) {
				skener.next();
				System.out.println("Molimo unijeti broj za ocjenu!");
			}
			Integer ocjena = provjeraUnesenogBrojaOcjene(skener);

			System.out.println("Unesite datum i vrijeme " + (i + 1) + ". ispitnog roka: ");

			String datumIVrijemeString = skener.nextLine();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATUM_VRIJEME);
			LocalDateTime datumIVrijemeIspita = LocalDateTime.parse(datumIVrijemeString, formatter);

			ispiti[i] = new Ispit(odabraniPredmet, odabraniStudent, ocjena, datumIVrijemeIspita);
		}

		return ispiti;
	}

	private static Integer provjeraUnesenogBrojaOcjene(Scanner skener) {
		boolean uvjet = false;
		String unos = null;
		while (!uvjet) {
			unos = skener.nextLine();
			if (unos.matches("^[1-5]")) {
				uvjet = true;
			} else {
				System.out.println("Broj mora biti između 1 i 5.");
			}
		}

		return Integer.parseInt(unos);
	}

	private static Student[] unesiStudenta(Scanner skener, Predmet[] predmeti) {

		for (int i = 0; i < predmeti.length; i++) {

			int brojStudenata = predmeti[i].getStudenti().length;

			System.out.println("Unesite studente za predmet " + predmeti[i].getNaziv());

			Student[] noviStudent = new Student[brojStudenata];

			for (int j = 0; j < noviStudent.length; j++) {

				System.out.println("Unesite ime " + (j + 1) + " studenta:");
				String ime = skener.nextLine();

				System.out.println("Unesite prezime " + (j + 1) + " studenta:");
				String prezime = skener.nextLine();

				System.out.println("Unesite JMBAG " + (j + 1) + " studenta:");
				String jmbag = skener.nextLine();

				boolean provjeraWhilePetlja = false;
				String datumRodenja = "";
				LocalDate datumRodjenja = null;
				do {
					try {
						System.out.println("Unesite datum rodenja " + (j + 1) + " studenta (DD.MM.YYYY.):");
						datumRodenja = skener.nextLine();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATUM);
						datumRodjenja = LocalDate.parse(datumRodenja, formatter);
						provjeraWhilePetlja = false;

					} catch (DateTimeParseException greska) {
						System.out.println("  Potrebno unijeti datum u formatu dd.mm.yyyy.! ");
						logger.info("Pogrešno unesen datum!", greska);
						provjeraWhilePetlja = true;
					}

				} while (provjeraWhilePetlja);

				noviStudent[j] = new Student(ime, prezime, jmbag, datumRodjenja);
			}
			predmeti[i].setStudenti(noviStudent);
		}

		int ukupanBrojStudenta = 0;

		for (Predmet pred : predmeti) {
			int velicinaPoljaStudenta = pred.getStudenti().length;
			// ukupanBrojStudenta += velicinaPoljaStudenta;
			ukupanBrojStudenta = ukupanBrojStudenta + velicinaPoljaStudenta;
		}

		Student[] student = new Student[ukupanBrojStudenta];

		int k = 0;
		for (Predmet pred : predmeti) {
			for (Student stud : pred.getStudenti()) {
				// for (int k = 0; k < student.length; k++) {
				student[k++] = stud;
				// }
			}

		}

		return student;

	}

	private static Predmet[] unesiPredmet(Scanner skener, Profesor[] profesori, int brojPredmeta) {

		Predmet[] predmeti = new Predmet[brojPredmeta];

		boolean provjeraWhilePetlja = false;

		for (int i = 0; i < predmeti.length; i++) {

			System.out.println("Unesite sifru " + (i + 1) + " predmeta:");
			String sifra = skener.nextLine();

			System.out.println("Unesite naziv " + (i + 1) + " predmeta:");
			String naziv = skener.nextLine();

			provjeraWhilePetlja = false;
			Integer brojEctsBodova = 0;
			do {
				try {
					System.out.println("Unesite broj ECTS bodova " + (i + 1) + " predmeta:");
					while (!skener.hasNextInt()) {
						System.out.println("Unesite broj!");
						skener.next();
					}
					brojEctsBodova = skener.nextInt();
					logger.info("Upisan broj ECTS bodova: " + brojEctsBodova);
					provjeraWhilePetlja = false;
				} catch (InputMismatchException greska) {
					System.out.println("Molimo upisite broj za ECTS bodove!");
					skener.nextLine();
					logger.error("Unesen tekst umjesto broja ECTS bodova!" + brojEctsBodova, greska);
					provjeraWhilePetlja = true;
				}
			} while (provjeraWhilePetlja);

			Integer odabirProfesora = 0;
			provjeraWhilePetlja = false;
			do {
				try {
					System.out.println("Odaberite profesora za predmet: ");
					for (int j = 0; j < profesori.length; j++) {
						System.out.println((j + 1) + ". " + profesori[j].getIme() + " " + profesori[j].getPrezime());
					}
					odabirProfesora = skener.nextInt();
					provjeraWhilePetlja = false;
				} catch (InputMismatchException greska) {
					System.out.println("Molimo upisite broj za odabir profesora!");
					skener.nextLine();
					logger.error("Unesen tekst umjesto broja za odabir profesora!" + odabirProfesora, greska);
					provjeraWhilePetlja = true;
				}

			} while (odabirProfesora < 1 || odabirProfesora > profesori.length && provjeraWhilePetlja);

			Profesor odabraniProfesor = profesori[odabirProfesora - 1];

			provjeraWhilePetlja = false;
			Integer brojStudenata = 0;
			do {
				try {
					System.out.println("Unesite broj studenata na predmetu " + naziv + ": ");
					brojStudenata = skener.nextInt();
					logger.info("Unesen broj studenata na predmetu: " + brojStudenata);
					skener.nextLine();
					provjeraWhilePetlja = false;
				} catch (InputMismatchException greska) {
					System.out.println("Molimo upisite broj studenata!");
					skener.nextLine();
					logger.error("Unesen tekst umjesto broja studenata na predmetu!" + brojStudenata, greska);
					provjeraWhilePetlja = true;
				}
			} while (provjeraWhilePetlja);

			predmeti[i] = new Predmet(sifra, naziv, brojEctsBodova, odabraniProfesor, brojStudenata);
		}

		return predmeti;
	}

	private static Profesor[] unesiProfesora(Scanner skener, int brojProfesora) {

		Profesor[] profesori = new Profesor[brojProfesora];

		for (int i = 0; i < profesori.length; i++) {

			System.out.println("Unesite sifru " + (i + 1) + " profesora:");
			String sifra = skener.nextLine();

			System.out.println("Unesite ime " + (i + 1) + " profesora:");
			String ime = skener.nextLine();

			System.out.println("Unesite prezime " + (i + 1) + " profesora:");
			String prezime = skener.nextLine();

			System.out.println("Unesite titulu " + (i + 1) + " profesora:");
			String titula = skener.nextLine();

			profesori[i] = new Profesor(sifra, ime, prezime, titula);
		}

		return profesori;
	}
}
