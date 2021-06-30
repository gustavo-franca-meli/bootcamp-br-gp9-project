package com.mercadolibre.finalProject.dtos;

import java.io.Serializable;

public class SampleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int random;

	public SampleDTO(){
	}

	public SampleDTO(int random){
		this.random = random;
	}

	public int getRandom() {
		return this.random;
	}

	public void setRandom(int random) {
		this.random = random;
	}
}
