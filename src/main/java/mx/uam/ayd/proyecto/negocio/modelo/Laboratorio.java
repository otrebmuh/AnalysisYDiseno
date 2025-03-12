package mx.uam.ayd.proyecto.negocio.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;


@Entity
@Data
public class Laboratorio {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLaboratorio;

    private String nombre;

    @OneToMany(targetEntity = Producto.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "idLaboratorio")
    private List<Producto> productos = new ArrayList<Producto>();
    
}