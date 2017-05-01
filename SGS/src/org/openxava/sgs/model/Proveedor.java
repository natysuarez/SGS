package org.openxava.sgs.model;

import java.util.*;

import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.model.*;

@Entity
@View (members= "ruc;" + "razonSocial;" + "telProveedor;" + "direccion;" + "correo;" + "encargado;" + "telEncargado;" + "fechaCreacion;")

public class Proveedor extends Identifiable{
	
	@Column(length=40) @Required
	private String razonSocial;
	
	@Column(length=12) @Required
	private String ruc;
	
	@Column(length=10) @Required
	private String telProveedor;
	
	@Column(length=60) @Required
	private String direccion;
	
	@Column(length=50) 
	private String correo;

	@Column(length=40) 
	private String encargado;
	
	@Column(length=10) 
	private String telEncargado;

	@ReadOnly
	@DefaultValueCalculator(CurrentDateCalculator.class) //Fecha_actual
	private Date fechaCreacion;

	public String getTelProveedor() {
		return telProveedor;
	}

	public void setTelProveedor(String telProveedor) {
		this.telProveedor = telProveedor;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getEncargado() {
		return encargado;
	}

	public void setEncargado(String encargado) {
		this.encargado = encargado;
	}

	public String getTelEncargado() {
		return telEncargado;
	}

	public void setTelEncargado(String telEncargado) {
		this.telEncargado = telEncargado;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	
	
	
}
