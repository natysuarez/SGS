package org.openxava.sgs.model;

import javax.persistence.*;

@Embeddable
public class DetalleFactura {
	
	private int cantidad;
	 
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    private producto producto;

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public producto getProducto() {
		return producto;
	}

	public void setProducto(producto producto) {
		this.producto = producto;
	}
}
