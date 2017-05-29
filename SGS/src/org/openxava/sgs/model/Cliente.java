package org.openxava.sgs.model;

import java.math.*;

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
	private BigDecimal nroDocumento;
	
	@Column(length=40) @Required
	private String razonSocial;
	
	
	public tipodoc getDocumento() {
		return documento;
	}

	public void setDocumento(tipodoc documento) {
		this.documento = documento;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public BigDecimal getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(BigDecimal nroDocumento) {
		this.nroDocumento = nroDocumento;
	}
	
	
}
