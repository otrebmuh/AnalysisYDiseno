package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.TipoEmpleadoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.TipoEmpleado;

@Service
public class ServicioTipoEmpleado {
    
    @Autowired
    private TipoEmpleadoRepository tipoEmpleadoRepository;
    
    /**
     * Obtiene todos los tipos de empleados registrados
     * @return Lista de tipos de empleados
     */
    public List<TipoEmpleado> getAll() {
        return StreamSupport.stream(tipoEmpleadoRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un tipo de empleado por su ID
     * @param id ID del tipo de empleado
     * @return TipoEmpleado si existe, null si no
     */
    public TipoEmpleado obtenerPorId(Long id) {
        Optional<TipoEmpleado> tipo = tipoEmpleadoRepository.findById(id);
        return tipo.orElse(null);
    }
    
    /**
     * Registra un nuevo tipo de empleado
     * @param tipo Tipo de empleado a registrar
     * @return Tipo de empleado registrado
     */
    public TipoEmpleado crear(TipoEmpleado tipo) {
        return tipoEmpleadoRepository.save(tipo);
    }
    
    /**
     * Actualiza un tipo de empleado existente
     * @param tipo Tipo de empleado a actualizar
     * @return Tipo de empleado actualizado
     */
    public TipoEmpleado actualizar(TipoEmpleado tipo) {
        if (!tipoEmpleadoRepository.existsById(tipo.getIdTipo())) {
            return null;
        }
        return tipoEmpleadoRepository.save(tipo);
    }
    
    /**
     * Elimina un tipo de empleado
     * @param id ID del tipo de empleado a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminar(Long id) {
        if (!tipoEmpleadoRepository.existsById(id)) {
            return false;
        }
        tipoEmpleadoRepository.deleteById(id);
        return true;
    }
    
    /**
     * Valida que los campos del tipo de empleado sean correctos
     * @param tipo Tipo de empleado a validar
     * @return true si es válido, false si no
     */
    public boolean validarTipoEmpleado(TipoEmpleado tipo) {
        if (tipo == null) return false;
        if (tipo.getNombre() == null || tipo.getNombre().trim().isEmpty()) return false;
        return true;
    }
}