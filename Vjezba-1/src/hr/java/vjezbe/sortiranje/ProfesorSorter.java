package hr.java.vjezbe.sortiranje;

import java.util.Comparator;

import hr.java.vjezbe.entitet.Profesor;

public class ProfesorSorter implements Comparator<Profesor> {

	@Override
	public int compare(Profesor o1, Profesor o2) {
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
