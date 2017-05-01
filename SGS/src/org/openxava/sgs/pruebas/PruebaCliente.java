package org.openxava.sgs.pruebas;

import org.openxava.tests.*;

public class PruebaCliente extends ModuleTestBase { // Ha de extender de ModuleTestBase
 
    public PruebaCliente(String nombrePrueba) {
        super(nombrePrueba, "SGS", // Indicamos el nombre de aplicación (Facturacion)
                "Cliente"); // y nombre de módulo (Cliente)
    }
 
    // Los métodos de prueba han de empezar por 'test'
    public void testCrearLeerActualizarBorrar() throws Exception {
        login("admin", "admin"); // Identificación de usuario para acceder al módulo
 
        // Crear
        execute("CRUD.new"); // Pulsa el botón 'Nuevo'
        setValue("id", "77"); // Teclea 77 como valor para el campo 'numero'
        setValue("nombre", "Cliente JUNIT"); // Pone valor en el campo 'nombre'
        setValue("apellido", "apellido JUNIT"); // Pone valor en el campo 'apellido'
        setValue("razon", "razon JUNIT"); // Pone valor en el campo 'razon'
        setValue("ruc", "19203-1 JUNIT"); // Pone valor en el campo 'ruc'
        execute("CRUD.save"); // Pulsa el botón 'Grabar'
        assertNoErrors(); // Verifica que la aplicación no muestra errores
        assertValue("id", ""); // Verifica que el campo 'numero' está vacío
        assertValue("nombre", ""); // Verifica que el campo 'nombre' está vacío
        assertValue("apellido", ""); // Etc
        assertValue("razon", ""); // Etc
        assertValue("ruc", ""); // Etc
        
 
        // Leer
        setValue("id", "77"); // Pone 77 como valor para el campo 'numero'
        execute("CRUD.refresh"); // Pulsa el botón 'Refrescar'
        assertValue("id", "77"); // Verifica que el campo 'numero' tiene un 77
        assertValue("nombre", "Cliente JUNIT"); // y 'nombre' tiene 'Cliente JUNIT'
        assertValue("apellido", "apellido JUNIT"); // Etc
        assertValue("razon", "razon JUNIT"); // Etc
        assertValue("ruc", "19203-1 JUNIT"); // Etc
        
 
        // Actualizar
        setValue("nombre", "Cliente JUNIT MODIFICADO"); // Cambia el valor del campo 'nombre'
        execute("CRUD.save"); // Pulsa el botón 'Grabar'
        assertNoErrors(); // Verifica que la aplicación no muestra errores
        assertValue("id", ""); // Verifica que el campo 'numero' está vacío
        assertValue("nombre", ""); // Verifica que el campo 'nombre' está vacío
 
        // Verifica si se ha modificado
        setValue("id", "77"); // Pone 77 como valor para el campo 'numero'
        execute("CRUD.refresh"); // Pulsa en el botón 'Refrescar'
        assertValue("id", "77"); // Verifica que el campo 'numero' tiene un 77
        assertValue("nombre", "Cliente JUNIT MODIFICADO"); // y 'nombre' tiene
                                                        // 'Cliente JUNIT MODIFICADO'
        // Borrar
        execute("CRUD.delete"); // Pulsa en el botón 'Borrar'
        assertMessage("Cliente borrado satisfactoriamente"); // Verifica que el mensaje
                                // 'Cliente borrado satisfactoriamente' se muestra al usuario
    }
 

}
