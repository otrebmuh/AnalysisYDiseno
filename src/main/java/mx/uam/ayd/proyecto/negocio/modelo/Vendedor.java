package mx.uam.ayd.proyecto.negocio.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vendedor {

    @Id // Esto le dice a Spring que este es el identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Le dice a Spring que genere el id
    private long idUsuario;

    private String nombre;

    private String apellido;

    private int edad;

}
