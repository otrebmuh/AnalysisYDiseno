package mx.uam.ayd.proyecto.datos;

import mx.uam.ayd.proyecto.negocio.modelo.Producto;

import java.util.List;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ProductoRepository extends CrudRepository <Producto, Long> {
	
	public Producto findById(long id);
	@SuppressWarnings("null")
	public List<Producto> findAll();
	Optional<Producto> findByCodigo(String codigo);

}