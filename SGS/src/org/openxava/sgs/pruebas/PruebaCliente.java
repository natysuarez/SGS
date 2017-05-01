package org.openxava.sgs.pruebas;

import org.openxava.tests.*;

public class PruebaCliente extends ModuleTestBase { // Ha de extender de ModuleTestBase
 
    public PruebaCliente(String nombrePrueba) {
        super(nombrePrueba, "SGS", // Indicamos el nombre de aplicaci�n (Facturacion)
                "Cliente"); // y nombre de m�dulo (Cliente)
    }
 
    // Los m�todos de prueba han de empezar por 'test'
    public void testCrearLeerActualizarBorrar() throws Exception {
        login("admin", "admin"); // Identificaci�n de usuario para acceder al m�dulo
 
        // Crear
        execute("CRUD.new"); // Pulsa el bot�n 'Nuevo'
        setValue("id", "77"); // Teclea 77 como valor para el campo 'numero'
        setValue("nombre", "Cliente JUNIT"); // Pone valor en el campo 'nombre'
        setValue("apellido", "apellido JUNIT"); // Pone valor en el campo 'apellido'
        setValue("razon", "razon JUNIT"); // Pone valor en el campo 'razon'
        setValue("ruc", "19203-1 JUNIT"); // Pone valor en el campo 'ruc'
        execute("CRUD.save"); // Pulsa el bot�n 'Grabar'
        assertNoErrors(); // Verifica que la aplicaci�n no muestra errores
        assertValue("id", ""); // Verifica que el campo 'numero' est� vac�o
        assertValue("nombre", ""); // Verifica que el campo 'nombre' est� vac�o
        assertValue("apellido", ""); // Etc
        assertValue("razon", ""); // Etc
        assertValue("ruc", ""); // Etc
        
 
        // Leer
        setValue("id", "77"); // Pone 77 como valor para el campo 'numero'
        execute("CRUD.refresh"); // Pulsa el bot�n 'Refrescar'
        assertValue("id", "77"); // Verifica que el campo 'numero' tiene un 77
        assertValue("nombre", "Cliente JUNIT"); // y 'nombre' tiene 'Cliente JUNIT'
        assertValue("apellido", "apellido JUNIT"); // Etc
        assertValue("razon", "razon JUNIT"); // Etc
        assertValue("ruc", "19203-1 JUNIT"); // Etc
        
 
        // Actualizar
        setValue("nombre", "Cliente JUNIT MODIFICADO"); // Cambia el valor del campo 'nombre'
        execute("CRUD.save"); // Pulsa el bot�n 'Grabar'
        assertNoErrors(); // Verifica que la aplicaci�n no muestra errores
        assertValue("id", ""); // Verifica que el campo 'numero' est� vac�o
        assertValue("nombre", ""); // Verifica que el campo 'nombre' est� vac�o
 
        // Verifica si se ha modificado
        setValue("id", "77"); // Pone 77 como valor para el campo 'numero'
        execute("CRUD.refresh"); // Pulsa en el bot�n 'Refrescar'
        assertValue("id", "77"); // Verifica que el campo 'numero' tiene un 77
        assertValue("nombre", "Cliente JUNIT MODIFICADO"); // y 'nombre' tiene
                                                        // 'Cliente JUNIT MODIFICADO'
        // Borrar
        execute("CRUD.delete"); // Pulsa en el bot�n 'Borrar'
        assertMessage("Cliente borrado satisfactoriamente"); // Verifica que el mensaje
                                // 'Cliente borrado satisfactoriamente' se muestra al usuario
    }
 

}
