package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.EmpleadoRepository;
import mx.uam.ayd.proyecto.datos.TipoEmpleadoRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.TipoEmpleado;

@Service
public class ServicioEmpleado {
    
    @Autowired
    private EmpleadoRepository empleadoRepository;
    
    @Autowired
    private TipoEmpleadoRepository tipoEmpleadoRepository;
    
    /**
     * Obtiene todos los empleados registrados
     * @return Lista de empleados
     */
    public List<Empleado> getAll() {
        return StreamSupport.stream(empleadoRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene un empleado por su ID
     * @param id ID del empleado
     * @return Empleado si existe, null si no
     */
    public Empleado obtenerPorId(Long id) {
        Optional<Empleado> empleado = empleadoRepository.findById(id);
        return empleado.orElse(null);
    }
    
    /**
     * Obtiene un empleado por su número de empleado
     * @param numero Numero de empleado
     * @return Empleado si existe, null si no
     */
    public Empleado obtenerPorNumeroEmpleado(String numero) {
        return empleadoRepository.findByNumeroEmpleado(numero);
    }
    
    /**
     * Registra un nuevo empleado
     * @param empleado Empleado a registrar
     * @return Empleado registrado
     */
    public Empleado crear(Empleado empleado) {
        if (empleado.getTipo() != null && empleado.getTipo().getIdTipo() != null) {
            TipoEmpleado tipo = tipoEmpleadoRepository.findById(empleado.getTipo().getIdTipo())
                .orElseThrow(() -> new RuntimeException("Tipo de empleado no encontrado"));
            empleado.setTipo(tipo);
        }
        return empleadoRepository.save(empleado);
    }
    
    /**
     * Actualiza un empleado existente
     * @param empleado Empleado a actualizar
     * @return Empleado actualizado
     */
    public Empleado actualizar(Empleado empleado) {
        if (!empleadoRepository.existsById(empleado.getIdEmpleado())) {
            return null;
        }
        
        if (empleado.getTipo() != null && empleado.getTipo().getIdTipo() != null) {
            TipoEmpleado tipo = tipoEmpleadoRepository.findById(empleado.getTipo().getIdTipo())
                .orElseThrow(() -> new RuntimeException("Tipo de empleado no encontrado"));
            empleado.setTipo(tipo);
        }
        
        return empleadoRepository.save(empleado);
    }
    
    /**
     * Elimina un empleado
     * @param id ID del empleado a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminar(Long id) {
        if (!empleadoRepository.existsById(id)) {
            return false;
        }
        empleadoRepository.deleteById(id);
        return true;
    }
    
    /**
     * Valida que los campos del empleado sean correctos
     * @param empleado Empleado a validar
     * @return true si es válido, false si no
     */
    public boolean validarEmpleado(Empleado empleado) {
        if (empleado == null) return false;
        if (empleado.getNombre() == null || empleado.getNombre().trim().isEmpty()) return false;
        if (empleado.getApellidoPaterno() == null || empleado.getApellidoPaterno().trim().isEmpty()) return false;
        if (empleado.getNumeroEmpleado() == null || empleado.getNumeroEmpleado().trim().isEmpty()) return false;
        if (empleado.getCorreoElectronico() == null || empleado.getCorreoElectronico().trim().isEmpty()) return false;
        if (empleado.getTipo() == null || empleado.getTipo().getIdTipo() == null) return false;
        return true;
    }
}