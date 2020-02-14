package hr.java.vjezbe.glavna;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hr.java.vjezbe.entitet.FakultetRacunalstva;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.ObrazovnaUstanova;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.entitet.Sveuciliste;
import hr.java.vjezbe.entitet.VeleucilisteJave;

public class GlavnaDatoteke {

	final static int GODINA = 2018;
	public static final Integer IZVRSTAN = 5;
	public static final Integer VRLO_DOBAR = 4;
	public static final Integer DOBAR = 3;
	public static final Integer DOVOLJAN = 2;

	private static final int BROJ_LINIJA_PROFESORA = 5;
	private static final int BROJ_LINIJA_PREDMETA = 7;
	public static final int BROJ_LINIJA_STUDENATA = 5;
	public static final int BROJ_LINIJA_ISPITA = 5;
	private static final int BROJ_LINIJA_OBRAZOVNIH_USTANOVA = 6;

	private static final String FILE_PROFESORI = "dat\\profesori.txt";
	private static final String FILE_PREDMETI = "dat\\predmeti.txt";
	private static final String FILE_STUDENTI = "dat\\studenti.txt";
	private static final String FILE_ISPITI = "dat\\ispiti.txt";
	private static final String FILE_OBRAZOVNE_USTANOVE = "dat\\obrazovneUstanove.txt";

	private static final File SERIALIZIRANI_PROFESORI = new File("dat\\profesori.dat");
	private static final File SERIALIZIRANI_STUDENTI = new File("dat\\studenti.dat");
	private static final File SERIALIZIRANE_USTANOVE = new File("dat\\obrazovneUstanove2.dat");

	public static void main(String[] args) {

		System.out.println("Pokretanje aplikacije...");

		Sveuciliste<ObrazovnaUstanova> listObrazovnaUstanova = new Sveuciliste<>();

		List<Profesor> profesori = dohvatiProfesore();
//		serijalizirajProfesore(profesori);
		List<Student> studenti = dohvatiStudente();
//		serijalizirajStudente(studenti);
		List<Predmet> predmeti = dohvatiPredmete(profesori, studenti);
		List<Ispit> ispiti = dohvatiIspite(predmeti, studenti);

		Sveuciliste<ObrazovnaUstanova> sveuciliste = dohvatiSveuciliste(profesori, predmeti, studenti, ispiti);

//		serijalizirajUstanove(sveuciliste);

//		deserijalizirajUstanove();

		List<ObrazovnaUstanova> lou = sveuciliste.getListaSveuciliste();

		for (ObrazovnaUstanova ou : lou) {
			if (ou instanceof VeleucilisteJave) {
				VeleucilisteJave vj = (VeleucilisteJave) ou;

				for (Profesor prof : vj.getProfesori()) {
					System.out.println(
							"Profesor " + prof.getIme() + " " + prof.getPrezime() + " predaje sljedece predmente:");

					for (Predmet predmet : vj.getPredmeti()) {
						if (predmet.getNositelj().equals(prof)) {
							System.out.println(vj.getPredmeti().indexOf(predmet) + 1 + ")" + predmet.getNaziv());
						}
					}
				}

				for (Predmet predmet : vj.getPredmeti()) {

					System.out.println("Studenti upisani na predmet " + predmet.getNaziv() + " su: ");

					// System.out.printf("Studenti upisani na predmet %s su:%n",
					// predmet.getNaziv());
					// predmet.getStudent().stream().sorted(new
					// StudentSorter()).forEach(System.out::println);
					for (Student stud : predmet.getStudent()) {
//						if (stud.getId().equals(predmet.getStudent())) {
						System.out.println(stud.getIme() + " " + stud.getPrezime());

//						}
					}
				}

				for (Student stud : vj.getStudenti()) {
					System.out.println("Konacna ocjena studija studenta " + stud.getIme() + " " + stud.getPrezime()
							+ "je " + vj.izracunajKonacnuOcjenuStudijaZaStudenta(ispiti, IZVRSTAN, IZVRSTAN));
				}

				vj.odrediNajuspjesnijegStudentaNaGodini(GODINA);

			} else if (ou instanceof FakultetRacunalstva) {
				FakultetRacunalstva fr = (FakultetRacunalstva) ou;

				fr.odrediNajuspjesnijegStudentaNaGodini(GODINA);
				fr.odrediStudentaZaRektorovuNagradu();

			}
		}

//		listObrazovnaUstanova.getListaSveuciliste().stream()
//				.sorted((o1, o2) -> Integer.compare(o1.getStudenti().size(), o2.getStudenti().size()))
//				.forEach(s -> System.out.println("Sortirane obrazovne ustanove prema broju studenata: \n" + s.getNaziv()
//						+ ": Broj studenta: " + s.getStudenti().size()));

//		ispis profesora
//		profesori.forEach(p -> System.out
//				.println("Dohvaceni profesori: " + p.getIme() + " " + p.getPrezime() + " " + p.getTitula()));

//		ipis profesora stream
//		profesori.stream().sorted().collect(Collectors.toCollection(LinkedList::new)).forEach(p -> System.out
//				.println("Dohvaceni profesori: " + p.getIme() + " " + p.getPrezime() + " " + p.getTitula()));

//		ispis profesora prema ProfesorSorteru
//		profesori.stream().sorted(new ProfesorSorter()).forEach(
//				p -> System.out.println("Profesori: " + p.getIme() + " " + p.getPrezime() + " " + p.getTitula()));

//		ispis studenata
//		studenti.forEach(p -> System.out
//				.println("Dohvaceni studenti: " + p.getIme() + " " + p.getPrezime() + " " + p.getDatumRodenja()));

//		ispis studenata prema StudentSorteru
//		studenti.stream().sorted(new StudentSorter()).forEach(p -> System.out.println(
//				"Dohvaceni StudentSorter studenti: " + p.getIme() + " " + p.getPrezime() + " " + p.getDatumRodenja()));

//		ispis predmeta
//		predmeti.forEach(pr -> System.out.println("Predmeti: " + pr.getNaziv()));

		// ispis profesora prema predmetima koje pradaju
//		predmeti.stream().collect(Collectors.toCollection(LinkedList::new))
//				.forEach(p -> System.out.println("Profesori na predmetu " + p.getNaziv() + " su : "
//						+ p.getNositelj().getIme() + " " + p.getNositelj().getPrezime()));

//		ispis naziv predmeta ispita, studenta i njegove ocjene i datuma kada je polagan
//		ispiti.forEach(is -> System.out
//				.println(" Ispit: " + is.getPredmet().getNaziv() + ", student: " + is.getStudent().getPrezime()
//						+ " je dobio ocjenu: " + is.getOcjena() + ", na datum:  " + is.getDatumIVrijeme()));

		System.out.println("Program zavrsava s izvodenjem.");
	}

	public static List<Profesor> dohvatiProfesore() {

		System.out.println("Ucitavanje profesora...");

		List<String> listaStringova = Collections.<String>emptyList();

		try (Stream<String> stream = Files.lines(new File(FILE_PROFESORI).toPath(), Charset.forName("UTF-8"))) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Profesor> listaProfesora = new ArrayList<>();

		for (int i = 0; i < listaStringova.size() / BROJ_LINIJA_PROFESORA; i++) {

			String idProfesora = listaStringova.get(i * (BROJ_LINIJA_PROFESORA)); // 0 6 12
			String sifraProfesora = listaStringova.get(i * BROJ_LINIJA_PROFESORA + 1); // 1 7 13
			String imeProfesora = listaStringova.get(i * BROJ_LINIJA_PROFESORA + 2); // 2 8 14
			String prezimeProfesora = listaStringova.get(i * BROJ_LINIJA_PROFESORA + 3); // 3 9 15
			String titulaProfesora = listaStringova.get(i * BROJ_LINIJA_PROFESORA + 4); // 4 10 16

			Profesor profesor = new Profesor(Long.parseLong(idProfesora), sifraProfesora, imeProfesora,
					prezimeProfesora, titulaProfesora);
			listaProfesora.add(profesor);
		}

		return listaProfesora;

	}

	public static List<Predmet> dohvatiPredmete(List<Profesor> profesori, List<Student> studenti) {

		System.out.println("Ucitavanje predmeta...");

		List<String> listaStringova = Collections.<String>emptyList();

		try (Stream<String> stream = Files.lines(new File(FILE_PREDMETI).toPath(), Charset.forName("UTF-8"))) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Predmet> listaPredmeta = new ArrayList<>();

		for (int i = 0; i < listaStringova.size() / BROJ_LINIJA_PREDMETA; i++) {

			String idPredmeta = listaStringova.get(i * BROJ_LINIJA_PREDMETA);
			String sifraPredmeta = listaStringova.get((i * BROJ_LINIJA_PREDMETA) + 1);
			String nazivPredmeta = listaStringova.get((i * BROJ_LINIJA_PREDMETA) + 2);

			Integer brojEctsBodova = Integer.parseInt(listaStringova.get((i * BROJ_LINIJA_PREDMETA) + 3));
			Long nositeljPredmetaInt = Long.parseLong(listaStringova.get((i * BROJ_LINIJA_PREDMETA) + 4));
			Integer brojStudenata = Integer.parseInt(listaStringova.get((i * BROJ_LINIJA_PREDMETA) + 5));

			String FKStudenata = listaStringova.get((i * (BROJ_LINIJA_PREDMETA)) + 6);

			Set<Student> setStudenata = new HashSet<Student>();
			String[] str = FKStudenata.split(" ");
			for (String id : str) {
				// myListPredmet.add(predmeti.get(Integer.parseInt(id)));
				setStudenata.add(studenti.stream().filter(p -> p.getId().equals(Long.parseLong(id))).findFirst().get());
			}

			Profesor nositeljPredmeta = profesori.stream().filter(p -> p.getId().equals(nositeljPredmetaInt))
					.findFirst().get();

			Predmet predmet = new Predmet(Long.parseLong(idPredmeta), sifraPredmeta, nazivPredmeta, brojEctsBodova,
					nositeljPredmeta, brojStudenata);
			predmet.setStudent(setStudenata);

			listaPredmeta.add(predmet);
		}

		return listaPredmeta;

	}

	public static List<Student> dohvatiStudente() {

		System.out.println("Ucitavanje studenata...");

		List<String> listaStringova = Collections.<String>emptyList();

		try (Stream<String> stream = Files.lines(new File(FILE_STUDENTI).toPath(), Charset.forName("UTF-8"))) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Student> listaStudenata = new ArrayList<>();

		for (int i = 0; i < listaStringova.size() / BROJ_LINIJA_STUDENATA; i++) {
			String idStudenta = listaStringova.get(i * BROJ_LINIJA_STUDENATA);
			String imeStudenta = listaStringova.get((i * BROJ_LINIJA_STUDENATA) + 1);
			String prezimeStudenta = listaStringova.get((i * BROJ_LINIJA_STUDENATA) + 2);
			String jmbagStudenta = listaStringova.get((i * BROJ_LINIJA_STUDENATA) + 3);
			String datumRodenjaStudentaString = listaStringova.get((i * BROJ_LINIJA_STUDENATA) + 4);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
			LocalDate datumRodenjaStudenta = LocalDate.parse(datumRodenjaStudentaString, formatter);

			Student student = new Student(Long.parseLong(idStudenta), imeStudenta, prezimeStudenta, jmbagStudenta,
					datumRodenjaStudenta);

			listaStudenata.add(student);
		}

		return listaStudenata;

	}

	public static List<Ispit> dohvatiIspite(List<Predmet> predmeti, List<Student> studenti) {

		System.out.println("Ucitavanje ispita i ocjena...");

		List<String> listaStringova = Collections.<String>emptyList();

		try (Stream<String> stream = Files.lines(new File(FILE_ISPITI).toPath(), Charset.forName("UTF-8"))) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Ispit> listaIspita = new ArrayList<>();

		for (int i = 0; i < listaStringova.size() / BROJ_LINIJA_ISPITA; i++) {
			String idIspita = listaStringova.get(i * BROJ_LINIJA_ISPITA);
			Long odabirPredmetaIspita = Long.parseLong(listaStringova.get((i * BROJ_LINIJA_ISPITA) + 1));
			Long odabirStudentaIspita = Long.parseLong(listaStringova.get((i * BROJ_LINIJA_ISPITA) + 2));
			String ocjenaIspita = listaStringova.get((i * BROJ_LINIJA_ISPITA) + 3);
			String datumIVrijemeIspitaString = listaStringova.get((i * BROJ_LINIJA_ISPITA) + 4);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.HH:mm");
			LocalDateTime datumIVrijemeIspita = LocalDateTime.parse(datumIVrijemeIspitaString, formatter);

			// potra�iti u profesorima, profesora pot tim ID-jem, dohvatitit ga i upucati
			// u
			// konstruktor od Predmeta
			Predmet predmet = predmeti.stream().filter(p -> p.getId().equals(odabirPredmetaIspita)).findFirst().get();

			// potra�iti u studentima , studenta pot tim ID-jem, dohvatitit ga i upucati u
			// konstruktor od Predmeta
			Student student = studenti.stream().filter(p -> p.getId().equals(odabirStudentaIspita)).findFirst().get();

			// public Ispit(Long id, Predmet predmet, Student student, Integer ocjena,
			// LocalDateTime datumIVrijeme) {
			Ispit ispit = new Ispit(Long.parseLong(idIspita), predmet, student, Integer.parseInt(ocjenaIspita),
					datumIVrijemeIspita);
			listaIspita.add(ispit);
		}

		return listaIspita;

	}

	public static Sveuciliste<ObrazovnaUstanova> dohvatiSveuciliste(List<Profesor> profesori, List<Predmet> predmeti,
			List<Student> studenti, List<Ispit> ispiti) {

		System.out.println("Ucitavanje obrazovnih ustanova...");

		List<String> listaStringova = Collections.<String>emptyList();

		try (Stream<String> stream = Files.lines(new File(FILE_OBRAZOVNE_USTANOVE).toPath(),
				Charset.forName("UTF-8"))) {
			listaStringova = stream.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Sveuciliste<ObrazovnaUstanova> sveuciliste = new Sveuciliste<ObrazovnaUstanova>();

		for (int i = 0; i < listaStringova.size() / BROJ_LINIJA_OBRAZOVNIH_USTANOVA; i++) {

			String idOU = listaStringova.get(i * BROJ_LINIJA_OBRAZOVNIH_USTANOVA);
			String nazivOU = listaStringova.get((i * BROJ_LINIJA_OBRAZOVNIH_USTANOVA) + 1);
			String listaPredmetaOU = listaStringova.get((i * BROJ_LINIJA_OBRAZOVNIH_USTANOVA) + 2);

			List<Predmet> myListPredmet = new ArrayList<>();

			String[] str = listaPredmetaOU.split(" ");
			for (String id : str) {
				// myListPredmet.add(predmeti.get(Integer.parseInt(id)));
				myListPredmet.add(
						predmeti.stream().filter(pred -> pred.getId().equals(Long.parseLong(id))).findFirst().get());
			}
			String listaProfesoraOU = listaStringova.get((i * BROJ_LINIJA_OBRAZOVNIH_USTANOVA) + 3);

			List<Profesor> myListProfesor = new ArrayList<>();
			String[] strProf = listaProfesoraOU.split(" ");
			for (String id : strProf) {
				// myListProfesor.add(profesori.get(Integer.parseInt(id)));
				myListProfesor.add(
						profesori.stream().filter(prof -> prof.getId().equals(Long.parseLong(id))).findFirst().get());
			}

			String listaStudenataOU = listaStringova.get((i * BROJ_LINIJA_OBRAZOVNIH_USTANOVA) + 4);

			List<Student> myListStudent = new ArrayList<>();

			String[] strStud = listaStudenataOU.split(" ");
			for (String id : strStud) {
				// myListStudent.add(studenti.get(Integer.parseInt(id)));
				myListStudent.add(
						studenti.stream().filter(stud -> stud.getId().equals(Long.parseLong(id))).findFirst().get());
			}

			String listaIspitaOU = listaStringova.get((i * BROJ_LINIJA_OBRAZOVNIH_USTANOVA) + 5);

			List<Ispit> myListIspit = new ArrayList<>();

			String[] strIspit = listaIspitaOU.split(" ");
			for (String id : strIspit) {
				// myListIspit.add(ispiti.get(Integer.parseInt(id)));
				myListIspit
						.add(ispiti.stream().filter(isp -> isp.getId().equals(Long.parseLong(id))).findFirst().get());
			}

			if (idOU.equals("1")) {

				VeleucilisteJave velucilisteJave = new VeleucilisteJave(Long.parseLong(idOU), nazivOU, myListPredmet,
						myListProfesor, myListStudent, myListIspit);

				sveuciliste.dodajObrazovnuUstanovu(velucilisteJave);

			} else if (idOU.equals("2")) {
				FakultetRacunalstva fakultetRacunalstva = new FakultetRacunalstva(Long.parseLong(idOU), nazivOU,
						myListPredmet, myListProfesor, myListStudent, myListIspit);

				sveuciliste.dodajObrazovnuUstanovu(fakultetRacunalstva);

			}

		}

		return sveuciliste;

	}

	private static void serijalizirajProfesore(List<Profesor> profesori) {

		System.out.println("..ulazak u metodu serijalizacije profesora...");

		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZIRANI_PROFESORI));
			for (Profesor profesor : profesori) {
				out.writeObject(profesor);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void serijalizirajStudente(List<Student> studenti) {

		System.out.println("..ulazak u metodu serijalizacije studenata...");

		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZIRANI_STUDENTI));
			for (Student stud : studenti) {
				out.writeObject(stud);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void serijalizirajUstanove(Sveuciliste<ObrazovnaUstanova> sveuciliste) {
		System.out.println("Serijalizacija obrazovne-ustanove.dat.");

		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZIRANE_USTANOVE));
			out.writeObject(sveuciliste);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void deserijalizirajUstanove() {
		System.out.println("Deserijalizacija obrazovne-ustanove.dat:");
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SERIALIZIRANE_USTANOVE))) {
			Sveuciliste sveuciliste = (Sveuciliste) in.readObject();

			System.out.printf("Profesori na %s su %s%n", sveuciliste.dohvatiObrazovnuUstanovu(0).getNaziv(),
					sveuciliste.dohvatiObrazovnuUstanovu(0).getProfesori());

			System.out.printf("Profesori na %s su %s%n", sveuciliste.dohvatiObrazovnuUstanovu(1).getNaziv(),
					sveuciliste.dohvatiObrazovnuUstanovu(1).getProfesori());

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
