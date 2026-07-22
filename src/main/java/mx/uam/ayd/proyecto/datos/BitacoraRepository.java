package mx.uam.ayd.proyecto.datos;

import org.springframework.data.repository.CrudRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Bitacora;

/**
 * Repositorio de datos para la entidad Bitacora
 *
 * @author Yamelin Larios Nepomuseno
 *
 */
public interface BitacoraRepository extends CrudRepository<Bitacora, Long> {
    // Al heredar de CrudRepository<Bitacora, Long>:
    // 1. Bitacora es la entidad a gestionar.
    // 2. Long es el tipo de dato de su Llave Primaria (@Id -> idBitacora).
    //
    // Métodos que Spring te regala automáticamente:
    // - .save(bitacora)     -> Guarda o actualiza un registro.
    // - .findById(id)       -> Busca una bitácora por su idBitacora.
    // - .findAll()          -> Trae la lista completa de la bitácora.
}