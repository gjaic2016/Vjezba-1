package hr.java.vjezbe.glavna;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;

public class Glavna {

	private static final int BROJ_PROFESORA = 2;
	private static final int BROJ_PREDMETA = 2;
	public static final int BROJ_STUDENATA = 2;
	private static final int BROJ_ISPITA = 1;
	private static final String FORMAT_DATUM_VRIJEME = "dd.MM.yyyy.HH:mm";

	public static void main(String[] args) {

		Scanner skener = new Scanner(System.in);

		Profesor[] profesori = new Profesor[BROJ_PROFESORA];
		Predmet[] predmeti = new Predmet[BROJ_PREDMETA];
		Student[] studenti = new Student[BROJ_STUDENATA];
		Ispit[] ispiti = new Ispit[BROJ_ISPITA];

		for (int i = 0; i < BROJ_PROFESORA; i++) {
			profesori[i] = unesiProfesora(skener, i, profesori);
		}

		for (int i = 0; i < BROJ_PREDMETA; i++) {
			predmeti[i] = unesiPredmet(skener, i, profesori);
		}

		for (int i = 0; i < BROJ_STUDENATA; i++) {
			studenti[i] = unesiStudenta(skener, i);
		}

		for (int i = 0; i < BROJ_ISPITA; i++) {
			ispiti[i] = unesiIspit(skener, i, predmeti, studenti);
		}

		skener.close();

		for (Ispit x : ispiti) {

			if (x.getOcjena().equals(5)) {
				System.out.println("Student " + x.getStudent().getPrezime() + " " + x.getStudent().getIme()
						+ " je dobio ocjenu izvrstan na predmetu: " + x.getPredmet().getNaziv());
			}

		}

//		for (int j = 0; j < profesori.length; j++) {
//
//			System.out.println(profesori[j].getIme() + " " + profesori[j].getPrezime());
//		}
//
//		for (int j = 0; j < predmeti.length; j++) {
//
//			System.out.println(predmeti[j].getNaziv());
//			System.out.println(predmeti[j].getStudenti().length);
//		}

//		Arrays.stream(profesori).sorted().forEach(m -> System.out.println(m.getIme()));

	}

	private static Ispit unesiIspit(Scanner skener, int i, Predmet[] predmeti, Student[] studenti) {

		Integer odabirBrojPredmeta;

		do {
			System.out.println("Odaberite predmet: ");

			for (int j = 0; j < BROJ_PREDMETA; j++) {
				System.out.println((j + 1) + ". " + predmeti[j].getNaziv());

			}
			odabirBrojPredmeta = skener.nextInt();

		} while (odabirBrojPredmeta < 1 || odabirBrojPredmeta > BROJ_PREDMETA);
		skener.nextLine();

		Predmet odabraniPredmet = predmeti[odabirBrojPredmeta - 1];

		Integer odabirBrojStudenta;

		do {
			System.out.println("Odaberite studenta: ");

			for (int j = 0; j < BROJ_STUDENATA; j++) {
				System.out.println((j + 1) + ". " + studenti[j].getIme() + " " + studenti[j].getPrezime());
			}
			odabirBrojStudenta = skener.nextInt();

		} while (odabirBrojStudenta < 1 || odabirBrojStudenta > BROJ_STUDENATA);
		skener.nextLine();

		Student odabraniStudent = studenti[odabirBrojStudenta - 1];

		System.out.println("Unesite ocjenu za " + (i + 1) + ". ispitni rok: ");

		while (!skener.hasNextInt()) {
			skener.next();
			System.out.println("Molimo unijeti broj za ocjenu!");
		}
		Integer ocjena = provjeraUnesenogBrojaOcjene(skener);

		System.out.println("Unesite datum i vrijeme " + (i + 1) + ". ispitnog roka: ");

		String datumIVrijemeString = skener.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATUM_VRIJEME);
		LocalDateTime datumIVrijemeIspita = LocalDateTime.parse(datumIVrijemeString, formatter);

		Ispit noviIspit = new Ispit(odabraniPredmet, odabraniStudent, ocjena, datumIVrijemeIspita);

		return noviIspit;
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

	private static Student unesiStudenta(Scanner skener, int i) {

		System.out.println("Unesite ime " + (i + 1) + " studenta:");
		String ime = skener.nextLine();

		System.out.println("Unesite prezime" + (i + 1) + " studenta:");
		String prezime = skener.nextLine();

		System.out.println("Unesite JMBAG " + (i + 1) + " studenta:");
		String jmbag = skener.nextLine();

		System.out.println("Unesite datum rodenja " + (i + 1) + " studenta (DD.MM.YYYY.):");
		String datumRodenja = skener.nextLine();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
		LocalDate datumRodjenja = LocalDate.parse(datumRodenja, formatter);

		Student noviStudent = new Student(ime, prezime, jmbag, datumRodjenja);

		return noviStudent;
	}

	private static Predmet unesiPredmet(Scanner skener, int i, Profesor[] profesori) {

		Integer odabirProfesora = 0;

		System.out.println("Unesite sifru " + (i + 1) + " predmeta:");
		String sifra = skener.nextLine();

		System.out.println("Unesite naziv " + (i + 1) + " predmeta:");
		String naziv = skener.nextLine();

		System.out.println("Unesite broj ECTS bodova " + (i + 1) + " predmeta:");
		Integer brojEctsBodova = skener.nextInt();

		do {
			System.out.println("Odaberite profesora za predmet: ");

			for (int j = 0; j < BROJ_PROFESORA; j++) {
				System.out.println((j + 1) + ". " + profesori[j].getIme() + " " + profesori[j].getPrezime());
			}
			odabirProfesora = skener.nextInt();
			skener.nextLine();

		} while (odabirProfesora < 1 || odabirProfesora > BROJ_PROFESORA);

		Profesor odabraniProfesor = profesori[odabirProfesora - 1];

		System.out.println("Unesite broj studenata na predmetu " + naziv + ": ");
		Integer brojStudenata = skener.nextInt();
		skener.nextLine();

		Predmet noviPredmet = new Predmet(sifra, naziv, brojEctsBodova, odabraniProfesor, brojStudenata);

		return noviPredmet;
	}

	private static Profesor unesiProfesora(Scanner skener, int i, Profesor[] profesori) {

		System.out.println("Unesite sifru " + (i + 1) + " profesora:");
		String sifra = skener.nextLine();

		System.out.println("Unesite ime " + (i + 1) + " profesora:");
		String ime = skener.nextLine();

		System.out.println("Unesite prezime " + (i + 1) + " profesora:");
		String prezime = skener.nextLine();

		System.out.println("Unesite titulu " + (i + 1) + " profesora:");
		String titula = skener.nextLine();

		Profesor noviProfesor = new Profesor(sifra, ime, prezime, titula);

		return noviProfesor;
	}
}
