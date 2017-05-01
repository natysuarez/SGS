package org.openxava.sgs.model;

import java.math.*;

import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.model.*;

@Entity
public class producto extends Identifiable{
	
	@Column(length=15) @Required
	private int codigo;
	
	@Column(length=40) @Required
	private String nombre;
	
	@Column(length=40) 
	private String descripcion;
	
	@Required
	private uniMedida unidadDeMedida;
	public enum uniMedida { unidad, kilogramo, metro }
	
	@Column(length=5) @Required 
	private BigDecimal stockMinimo;
	
	@ManyToOne( // La referencia se almacena como una relación en la base de datos
	fetch=FetchType.LAZY, // La referencia se carga bajo demanda
	optional=true) // La referencia puede estar sin valor
	@DescriptionsList // Así la referencia se visualiza usando un combo
	private Familia familia;
	
	@Stereotype("DINERO") // La propiedad precio se usa para almacenar dinero
	private BigDecimal precio; 
	 
	@Stereotype("FOTO") // El usuario puede ver y cambiar una foto
	@Column(length=16777216) // Este tamaño para poder guardar fotos grandes
	private byte [] foto;
	 
	@Stereotype("GALERIA_IMAGENES") // Una galería de fotos completa está disponible
	@Column(length=32) // La cadena de 32 de longitud es para almacenar la clave de la galería
	private String masFotos;
	 
	@Stereotype("TEXTO_GRANDE") // Esto es para un texto grande, se usará un área de texto o equivalente
	private String observaciones;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public uniMedida getUnidadDeMedida() {
		return unidadDeMedida;
	}

	public void setUnidadDeMedida(uniMedida unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}

	public BigDecimal getStockMinimo() {
		return stockMinimo;
	}

	public void setStockMinimo(BigDecimal stockMinimo) {
		this.stockMinimo = stockMinimo;
	}

	public Familia getFamilia() {
		return familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getMasFotos() {
		return masFotos;
	}

	public void setMasFotos(String masFotos) {
		this.masFotos = masFotos;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	} 

}
