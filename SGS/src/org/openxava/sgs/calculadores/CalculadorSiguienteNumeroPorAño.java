package org.openxava.sgs.calculadores;

import javax.persistence.*;
import org.openxava.calculators.*;
import org.openxava.jpa.*;

public class CalculadorSiguienteNumeroPorA�o implements ICalculator{
		
	private int a�o; //This value will be add with his setter before calculated
	 
    public Object calculate() throws Exception { 
        Query query = XPersistence.getManager()  
        .createQuery("select max(f.numero) from Factura f where f.a�o = :a�o"); // La consulta devuelve el n�mero de factura m�ximo en el a�o indicado
        query.setParameter("a�o", a�o); 
        Integer ultimoNumero = (Integer) query.getSingleResult();
        return ultimoNumero == null ? 1 : ultimoNumero + 1; // Devuelve el �ltimo n�mero de factura del a�o + 1 o 1 si no hay n�mero
    }
 
    public int getA�o() {
        return a�o;
    }
 
    public void setA�o(int a�o) {
        this.a�o = a�o;
    }
 

}
