package hr.java.vjezbe.sortiranje;

import java.util.Comparator;

import hr.java.vjezbe.entitet.Student;

public class StudentSorter implements Comparator<Student> {

	@Override
	public int compare(Student o1, Student o2) {
		if (o1.getPrezime().compareTo(o2.getPrezime()) > 0) {
			return 1;
		} else if (o1.getPrezime().compareTo(o2.getPrezime()) < 0) {
			return -1;
		} else {
			if (o1.getIme().compareTo(o2.getIme()) > 0) {
				return 1;
			} else if (o1.getIme().compareTo(o2.getIme()) < 0) {
				return -1;
			} else {
				return 0;
			}
		}
	}

}
