package mx.uam.ayd.proyecto.negocio.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;


@Entity
@Data
public class Producto {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idProducto;
    private String nombre;
    private String descripcion;
    private String contenido;
    private boolean receta;

    @ManyToOne
    @JoinColumn(name = "idCategoria")
    private CategoriaProducto categoria;
    
    @ManyToOne
    @JoinColumn(name = "idIngrediente")
    private Ingrediente ingrediente;

    @ManyToOne
    @JoinColumn(name = "idLaboratorio")
    private Laboratorio laboratorio;
}
