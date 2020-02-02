package hr.java.vjezbe.glavna;

import java.util.Scanner;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;

public class Glavna {

	private static final int BROJ_PROFESORA = 2;
	private static final int BROJ_PREDMETA = 3;
	public static final int BROJ_STUDENATA = 2;
	private static final int BROJ_ISPITA = 1;

	public static void main(String[] args) {

		Scanner skener = new Scanner(System.in);

		Profesor[] profesori = new Profesor[BROJ_PROFESORA];
		Predmet[] predmeti = new Predmet[BROJ_PREDMETA];

		for (int i = 0; i < BROJ_PROFESORA; i++) {
			profesori[i] = unesiProfesora(skener, i, profesori);
		}

		for (int i = 0; i < BROJ_PREDMETA; i++) {
			predmeti[i] = unesiPredmet(skener, i, profesori);
		}

		skener.close();

		for (int j = 0; j < profesori.length; j++) {

			System.out.println(profesori[j].getIme() + " " + profesori[j].getPrezime());
		}

		for (int j = 0; j < predmeti.length; j++) {

			System.out.println(predmeti[j].getNaziv() + " " + predmeti[j].getNositelj());
			System.out.println(predmeti[j].getStudenti().length);
		}

//		Arrays.stream(profesori).sorted().forEach(m -> System.out.println(m.getIme()));

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
