package hr.java.vjezbe.glavna;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.sortiranje.StudentSorter;

public class GlavnaDatoteke {

	private static final int BROJ_PROFESORA = 5;
	private static final int BROJ_LINIJA_PROFESORA = 5;
	public static final int BROJ_STUDENATA = 2;
	public static final int BROJ_LINIJA_STUDENATA = 5;

	private static final String FILE_PROFESORI = "dat\\profesori.txt";
	private static final String FILE_STUDENTI = "dat\\studenti.txt";

	private static final File SERIALIZIRANI_PROFESORI = new File("dat\\profesori.dat");

	public static void main(String[] args) {

		System.out.println("Dohvat profesora...");

//		List<Profesor> profesori = dohvatiProfesore();
//		serijalizirajProfesore(profesori);

		List<Student> studenti = dohvatiStudente();

//		profesori.forEach(p -> System.out
//				.println("Dohvaceni profesori: " + p.getIme() + " " + p.getPrezime() + " " + p.getTitula()));

//		profesori.stream().sorted().collect(Collectors.toCollection(LinkedList::new)).forEach(p -> System.out
//				.println("Dohvaceni profesori: " + p.getIme() + " " + p.getPrezime() + " " + p.getTitula()));

		// ispis profesora prema ProfesorSorteru
//		profesori.stream().sorted(new ProfesorSorter()).forEach(
//				p -> System.out.println("Profesori: " + p.getIme() + " " + p.getPrezime() + " " + p.getTitula()));

		studenti.forEach(p -> System.out
				.println("Dohvaceni studenti: " + p.getIme() + " " + p.getPrezime() + " " + p.getDatumRodenja()));

		studenti.stream().sorted(new StudentSorter()).forEach(p -> System.out.println(
				"Dohvaceni StudentSorter studenti: " + p.getIme() + " " + p.getPrezime() + " " + p.getDatumRodenja()));

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

		for (int i = 0; i < listaStringova.size() / BROJ_PROFESORA; i++) {

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

}
