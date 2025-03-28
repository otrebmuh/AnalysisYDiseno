/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uam.ayd.proyecto.presentacion.realizarVenta;

import org.springframework.stereotype.Component;

/**
 *
 * @author Lalo
 */
@Component
public class ControlRealizarVenta {
    private VentanaRealizarVenta ventana;

    public ControlRealizarVenta() {
        ventana = new VentanaRealizarVenta();
    }

    public void inicia() {
        ventana.muestra(this);
    }

    public void confirmarVenta() {
        // Add your venta confirmation logic here
        System.out.println("Venta confirmada");
        ventana.dispose();
    }
}
