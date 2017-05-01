package org.openxava.sgs.calculadores;

import javax.persistence.*;
import org.openxava.calculators.*;
import org.openxava.jpa.*;

public class CalculadorSiguienteNumeroPorAño implements ICalculator{
		
	private int año; //This value will be add with his setter before calculated
	 
    public Object calculate() throws Exception { 
        Query query = XPersistence.getManager()  
        .createQuery("select max(f.numero) from Factura f where f.año = :año"); // La consulta devuelve el número de factura máximo en el año indicado
        query.setParameter("año", año); 
        Integer ultimoNumero = (Integer) query.getSingleResult();
        return ultimoNumero == null ? 1 : ultimoNumero + 1; // Devuelve el último número de factura del año + 1 o 1 si no hay número
    }
 
    public int getAño() {
        return año;
    }
 
    public void setAño(int año) {
        this.año = año;
    }
 

}
