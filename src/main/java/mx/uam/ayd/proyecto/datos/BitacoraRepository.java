package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;

import mx.uam.ayd.proyecto.negocio.modelo.Bitacora;

/**
 * Repositorio para Bitacora
 * 
 * @author Yamelin Larios Nepomuseno
 *
 */
public interface BitacoraRepository extends CrudRepository <Bitacora, Long> {

}