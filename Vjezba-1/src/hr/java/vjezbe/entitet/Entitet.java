package hr.java.vjezbe.entitet;

import java.io.Serializable;

public class Entitet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1924310826302382800L;
	private Long id;

	public Entitet(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
