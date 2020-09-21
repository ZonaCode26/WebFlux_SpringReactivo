package com.mitocode.document;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class FacturaItem {

	private Integer cantidad;
	//@DBRef
	private Plato plato;
	
	public Double calcularMonto() {
		return cantidad.doubleValue() * plato.getPrecio();
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Plato getPlato() {
		return plato;
	}

	public void setPlato(Plato plato) {
		this.plato = plato;
	}

	@Override
	public String toString() {
		return "FacturaItem [cantidad=" + cantidad + ", plato=" + plato + "]";
	}
	
	

}
