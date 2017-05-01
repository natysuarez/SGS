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
 	@DefaultValueCalculator(CurrentYearCalculator.class) //A�o_actual
 	private int a�o;
  
 	@ReadOnly
 	@DefaultValueCalculator(CurrentDateCalculator.class) //Fecha_actual
 	private Date fecha;

 	@Column(length=6) @ReadOnly
 	@DefaultValueCalculator(value=CalculadorSiguienteNumeroPorA�o.class, properties=@PropertyValue(name="a�o"))
 	private int numero;

 	@ManyToOne(fetch=FetchType.LAZY, optional=false) // Es obligatorio agregar un cliente
 	private Cliente cliente;
 	
 	@ElementCollection
 	@ListProperties("producto.codigo, producto.nombre, cantidad")
 	private Collection<DetalleFactura> detalles;
 	
	public int getA�o() {
		return a�o;
	}

	public void setA�o(int a�o) {
		this.a�o = a�o;
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
