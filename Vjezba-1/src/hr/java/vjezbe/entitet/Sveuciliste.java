package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public class Sveuciliste<T extends ObrazovnaUstanova> {

	private List<T> listaSveuciliste = new ArrayList<T>();

	public void dodajObrazovnuUstanovu(T vrijednost) {
		listaSveuciliste.add(vrijednost);
	}

	public T dohvatiObrazovnuUstanovu(Integer index) {
		return listaSveuciliste.get(index);
	}

	public List<T> getListaSveuciliste() {
		return listaSveuciliste;
	}
}
