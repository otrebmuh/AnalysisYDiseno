package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.IngredienteRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Ingrediente;

@Service
public class ServicioIngrediente {
    
    @Autowired
    private IngredienteRepository ingredienteRepository;
    
    /**
     * Obtiene todos los ingredientes registrados
     * @return Lista de ingredientes
     */
    public List<Ingrediente> obtenerTodos() {
        return ingredienteRepository.findAll();
    }
    
    /**
     * Obtiene un ingrediente por su ID
     * @param id ID del ingrediente
     * @return Ingrediente si existe, null si no
     */
    public Ingrediente obtenerPorId(Long id) {
        Optional<Ingrediente> ingrediente = ingredienteRepository.findById(id);
        return ingrediente.orElse(null);
    }
    
    /**
     * Registra un nuevo ingrediente
     * @param ingrediente Ingrediente a registrar
     * @return Ingrediente registrado
     */
    public Ingrediente registrar(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }
    
    /**
     * Actualiza un ingrediente existente
     * @param ingrediente Ingrediente a actualizar
     * @return Ingrediente actualizado
     */
    public Ingrediente actualizar(Ingrediente ingrediente) {
        if (!ingredienteRepository.existsById(ingrediente.getId())) {
            return null;
        }
        return ingredienteRepository.save(ingrediente);
    }
    
    /**
     * Elimina un ingrediente
     * @param id ID del ingrediente a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminar(Long id) {
        if (!ingredienteRepository.existsById(id)) {
            return false;
        }
        ingredienteRepository.deleteById(id);
        return true;
    }
    
    /**
     * Valida que los campos del ingrediente sean correctos
     * @param ingrediente Ingrediente a validar
     * @return true si es válido, false si no
     */
    public boolean validarIngrediente(Ingrediente ingrediente) {
        if (ingrediente == null) return false;
        if (ingrediente.getNombre() == null || ingrediente.getNombre().trim().isEmpty()) return false;
        if (ingrediente.getDescripcion() == null || ingrediente.getDescripcion().trim().isEmpty()) return false;
        return true;
    }
} 