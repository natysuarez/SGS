package org.openxava.sgs.model;

import javax.persistence.*;
import org.openxava.annotations.*;
import org.openxava.model.*;


@Entity
public class Cliente {
	
	//@Column(length=40) @Required
	//private String nombre;
	
	//@Column(length=40) @Required
	//private String apellido;
	
	private tipodoc documento;
	public enum tipodoc { Cédula, Ruc }
	
	@Id @Column(length=40) @Required
	private String nroDocumento;
	
	@Column(length=40) @Required
	private String razonSocial;
	
	
	public tipodoc getDocumento() {
		return documento;
	}

	public void setDocumento(tipodoc documento) {
		this.documento = documento;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	
	
}
