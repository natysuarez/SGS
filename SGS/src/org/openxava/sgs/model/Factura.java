package org.openxava.sgs.model;

import java.util.*;
import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.model.*;
import org.openxava.sgs.calculadores.*;


@Entity
public class Factura extends Identifiable{
	
	@Column(length=4) @ReadOnly
 	@DefaultValueCalculator(CurrentYearCalculator.class) //Año_actual
 	private int año;
  
 	@ReadOnly
 	@DefaultValueCalculator(CurrentDateCalculator.class) //Fecha_actual
 	private Date fecha;

 	@Column(length=6) @ReadOnly
 	@DefaultValueCalculator(value=CalculadorSiguienteNumeroPorAño.class, properties=@PropertyValue(name="año"))
 	private int numero;

 	@ManyToOne(fetch=FetchType.LAZY, optional=false) // Es obligatorio agregar un cliente
 	private Cliente cliente;
 	
 	@ElementCollection
 	@ListProperties("producto.codigo, producto.nombre, cantidad")
 	private Collection<DetalleFactura> detalles;
 	
	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Collection<DetalleFactura> getDetalles() {
		return detalles;
	}

	public void setDetalles(Collection<DetalleFactura> detalles) {
		this.detalles = detalles;
	}	
	

}
