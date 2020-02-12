package hr.java.vjezbe.glavna;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.FakultetRacunalstva;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.ObrazovnaUstanova;
import hr.java.vjezbe.entitet.Ocjena;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.entitet.Sveuciliste;
import hr.java.vjezbe.entitet.VeleucilisteJave;
import hr.java.vjezbe.sortiranje.StudentSorter;

/**
 * @author Fluffy @
 */

public class Glavna {

	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

	final static int GODINA = 1111;
	private static final String FORMAT_DATUM = "dd.MM.yyyy.";
	private static final String FORMAT_DATUM_VRIJEME = "dd.MM.yyyy.HH:mm";

	public static void main(String[] args) {

		Map<Profesor, List<Predmet>> myMap = new HashMap<>();

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

		Sveuciliste<ObrazovnaUstanova> obrazovnaUstanovaLista = new Sveuciliste<>();

		for (int i = 0; i < brojObrazovnihUstanova; i++) {

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

			List<Profesor> profesori = unesiProfesora(skener, brojProfesora);

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

			List<Predmet> predmeti = unesiPredmet(skener, profesori, brojPredmeta);

			for (Predmet predmet : predmeti) {
				// provjeri postoji li kljuc, ako postoji dodaj predmet, ako ne kreiraj kljuc
				if (myMap.containsKey(predmet.getNositelj())) {
					myMap.get(predmet.getNositelj()).add(predmet);
				} else {
					List<Predmet> myList = new ArrayList<Predmet>();
					myList.add(predmet);
					myMap.put(predmet.getNositelj(), myList);
				}
			}

			for (Profesor key : myMap.keySet()) {
				// Profesor xy predaje sljedeće predmete:
				System.out
						.println("Profesor " + key.getIme() + " " + key.getPrezime() + " predaje sljedeće predmete: ");
				int brojac = 0;
				for (Predmet predmet : myMap.get(key)) {
					System.out.println(++brojac + ") " + predmet.getNaziv());
				}
			}

			List<Student> studenti = unesiStudenta(skener, predmeti);

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

			List<Ispit> ispiti = unesiIspit(skener, brojIspitnihRokova, predmeti, studenti);

//			for (Ispit x : ispiti) {
//				if (x.getOcjena().equals(5)) {
//					System.out.println("Student " + x.getStudent().getPrezime() + " " + x.getStudent().getIme()
//							+ " je dobio ocjenu " + Ocjena.IZVRSTAN + "na predmetu: " + x.getPredmet().getNaziv());
//				}
//			}

			// lambda verzija petlje za ispis studenta koji ima ocjenu izvrstan na ispitu
			ispiti.stream().filter(ispit -> ispit.getOcjena().equals(Ocjena.IZVRSTAN.getOcjenaInt()))
					.collect(Collectors.toCollection(LinkedList::new))
					.forEach(filteredIspiti -> System.out.println("Student " + filteredIspiti.getStudent().getPrezime()
							+ " " + filteredIspiti.getStudent().getIme() + "je dobio: "
							+ Ocjena.IZVRSTAN.getOcjenaString() + " na predmetu: "
							+ filteredIspiti.getPredmet().getNaziv()));

//			List<Student> sortedStudent = null;
//			for (int t = 0; t < predmeti.size(); t++) {
//				System.out.println("\nStudenti na predmetu " + predmeti.get(t).getNaziv() + " su: ");
//				sortedStudent = new ArrayList<>(predmeti.get(t).getStudent());
//				Collections.sort(sortedStudent, new StudentSorter());
//				for (Student stud : sortedStudent) {
//					System.out.println(stud.getPrezime() + " " + stud.getIme());
//				}
//			}

			// 1. verzija lambda izraza ispisa studenata po predmetu
			predmeti.forEach(predmet -> predmet.getStudent().stream().sorted(new StudentSorter())
					.forEach(student -> System.out.println("Studenti na predmetu: " + predmet.getNaziv() + " su: "
							+ student.getIme() + " " + student.getPrezime())));

			// 2. verzija lambda izraza ispisa studenata po predmetu
			predmeti.stream().forEach(p -> {
				System.out.println("Studenti na predmetu " + p.getNaziv() + " su: ");
				List<Student> sortedStudentLambda = new ArrayList<>(p.getStudent());
				Collections.sort(sortedStudentLambda, new StudentSorter());
				sortedStudentLambda.forEach(s -> System.out.println(s.getPrezime() + " " + s.getIme()));
			});

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

				veleucilisteJave.setNaziv(nazivObrazovneUstanove);
				obrazovnaUstanovaLista.dodajObrazovnuUstanovu(veleucilisteJave);

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

					fakultetRacunalstva.setNaziv(nazivObrazovneUstanove);
					obrazovnaUstanovaLista.dodajObrazovnuUstanovu(fakultetRacunalstva);

				}

			}

		}

		obrazovnaUstanovaLista.getListaSveuciliste().stream()
				.sorted((o1, o2) -> Integer.compare(o1.getStudenti().size(), o2.getStudenti().size()))
				.forEach(s -> System.out.println("Sortirane obrazovne ustanove prema broju studenata: \n" + s.getNaziv()
						+ ": Broj studenta: " + s.getStudenti().size()));

		skener.close();

	}

	private static List<Ispit> unesiIspit(Scanner skener, int brojIspitnihRokova, List<Predmet> predmeti,
			List<Student> studenti) {

		List<Ispit> ispiti = new ArrayList<>();

		for (int i = 0; i < brojIspitnihRokova; i++) {

			System.out.println("Unesite " + (i + 1) + " ispitni rok:");

			Integer odabirBrojPredmeta;
			do {
				System.out.println("Odaberite predmet: ");

				for (int j = 0; j < predmeti.size(); j++) {
					System.out.println((j + 1) + ". " + predmeti.get(j).getNaziv());

				}
				odabirBrojPredmeta = skener.nextInt();

			} while (odabirBrojPredmeta < 1 || odabirBrojPredmeta > predmeti.size());
			skener.nextLine();
			Predmet odabraniPredmet = predmeti.get(odabirBrojPredmeta - 1);

			List<Student> myList = new ArrayList<>(odabraniPredmet.getStudent());
			Integer odabirBrojStudenta = 0;
			do {
				System.out.println("Odaberite studenta: ");

				for (int k = 0; k < myList.size(); k++) {
					System.out.println((k + 1) + ". " + myList.get(k).getIme() + " " + myList.get(k).getPrezime());
				}
				odabirBrojStudenta = skener.nextInt();

			} while (odabirBrojStudenta < 1 || odabirBrojStudenta > odabraniPredmet.getStudent().size());
			skener.nextLine();

			Student odabraniStudent = myList.get(odabirBrojStudenta - 1);

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

//			ispiti[i] = new Ispit(odabraniPredmet, odabraniStudent, ocjena, datumIVrijemeIspita);
			ispiti.add(new Ispit(odabraniPredmet, odabraniStudent, ocjena, datumIVrijemeIspita));
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

	private static List<Student> unesiStudenta(Scanner skener, List<Predmet> predmeti) {

		List<Student> studentiLista = new ArrayList<Student>();

		Student student = null;

		for (int i = 0; i < predmeti.size(); i++) {

			int brojStudenata = predmeti.get(i).getStudent().size();

			System.out.println("Unesite studente za predmet " + predmeti.get(i).getNaziv());

			Set<Student> noviStudentSet = new HashSet<Student>();

			for (int j = 0; j < brojStudenata; j++) {

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

				student = new Student(ime, prezime, jmbag, datumRodjenja);
				noviStudentSet.add(student);
				studentiLista.add(student);
//				noviStudentSet.add(new Student(ime, prezime, jmbag, datumRodjenja));
//				predmeti.get(i).setStudent(noviStudentSet);
			}
			predmeti.get(i).setStudent(noviStudentSet);
		}

		return studentiLista;

	}

	private static List<Predmet> unesiPredmet(Scanner skener, List<Profesor> profesori, int brojPredmeta) {

		List<Predmet> predmeti = new ArrayList<Predmet>();

		boolean provjeraWhilePetlja = false;

		for (int i = 0; i < brojPredmeta; i++) {

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
					int redBroj = 0;
					for (Profesor profesor : profesori) {
						System.out.println((++redBroj) + ". " + profesor.getIme() + " " + profesor.getPrezime());
					}
//					for (int j = 0; j < profesori.length; j++) {
//						System.out.println((j + 1) + ". " + profesori[j].getIme() + " " + profesori[j].getPrezime());
//					}
					odabirProfesora = skener.nextInt();
					provjeraWhilePetlja = false;
				} catch (InputMismatchException greska) {
					System.out.println("Molimo upisite broj za odabir profesora!");
					skener.nextLine();
					logger.error("Unesen tekst umjesto broja za odabir profesora!" + odabirProfesora, greska);
					provjeraWhilePetlja = true;
				}

			} while (odabirProfesora < 1 || odabirProfesora > profesori.size() && provjeraWhilePetlja);

			Profesor odabraniProfesor = profesori.get(odabirProfesora - 1);

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

			Predmet myPredmet = new Predmet(sifra, naziv, brojEctsBodova, odabraniProfesor);
			for (int j = 0; j < brojStudenata; j++) {
				myPredmet.getStudent().add(new Student(null, null, String.valueOf(j), null));
			}

			predmeti.add(myPredmet);
		}

		return predmeti;
	}

	private static List<Profesor> unesiProfesora(Scanner skener, int brojProfesora) {

		List<Profesor> profesori = new ArrayList<>();

		for (int i = 0; i < brojProfesora; i++) {

			System.out.println("Unesite sifru " + (i + 1) + " profesora:");
			String sifra = skener.nextLine();

			System.out.println("Unesite ime " + (i + 1) + " profesora:");
			String ime = skener.nextLine();

			System.out.println("Unesite prezime " + (i + 1) + " profesora:");
			String prezime = skener.nextLine();

			System.out.println("Unesite titulu " + (i + 1) + " profesora:");
			String titula = skener.nextLine();

//			profesori[i] = new Profesor(sifra, ime, prezime, titula);
			profesori.add(new Profesor(sifra, ime, prezime, titula));
		}

		return profesori;
	}

//	dohvat ocjene i usporedivanje s vrijednositma iz enum clase Ocjena, vraca nazad String vrijednost ocjene
//	public static String ispisOcjena(Integer unosOcjena) {
//		String ispisOcjene = null;
//		Ocjena[] ocjena = Ocjena.values();
//		for (Ocjena ocjene : ocjena) {
//			if (ocjene.getOcjenaInt() == unosOcjena) {
//				ispisOcjene = ocjene.getOcjenaString();
//			}
//		}
//		return ispisOcjene;
//	}
}
