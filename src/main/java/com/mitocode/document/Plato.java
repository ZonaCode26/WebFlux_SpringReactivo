package com.mitocode.document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "platos")
public class Plato {

	@Id
	private String id;

	@NotEmpty
	@Size(min = 3, message = "Tamaño no permitido") //javax.validation.constraints
	@Field(name = "nombre")
	private String nombre;

	@NotNull
	@Max(100)
	@Min(1)
	private Double precio;

	@NotNull
	private Boolean estado;
	
	public Plato() {
		
	}

	public Plato(String id) {		
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	
	
	
	

}
