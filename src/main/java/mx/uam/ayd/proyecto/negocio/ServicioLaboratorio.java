package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.LaboratorioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Laboratorio;

@Service
public class ServicioLaboratorio {
    
    @Autowired
    private LaboratorioRepository laboratorioRepository;
    
    /**
     * Obtiene todos los laboratorios registrados
     * @return Lista de laboratorios
     */
    public List<Laboratorio> obtenerTodos() {
        return laboratorioRepository.findAll();
    }
    
    /**
     * Obtiene un laboratorio por su ID
     * @param id ID del laboratorio
     * @return Laboratorio si existe, null si no
     */
    public Laboratorio obtenerPorId(Long id) {
        Optional<Laboratorio> laboratorio = laboratorioRepository.findById(id);
        return laboratorio.orElse(null);
    }
    
    /**
     * Registra un nuevo laboratorio
     * @param laboratorio Laboratorio a registrar
     * @return Laboratorio registrado
     */
    public Laboratorio registrar(Laboratorio laboratorio) {
        return laboratorioRepository.save(laboratorio);
    }
    
    /**
     * Actualiza un laboratorio existente
     * @param laboratorio Laboratorio a actualizar
     * @return Laboratorio actualizado
     */
    public Laboratorio actualizar(Laboratorio laboratorio) {
        if (!laboratorioRepository.existsById(laboratorio.getIdLaboratorio())) {
            return null;
        }
        return laboratorioRepository.save(laboratorio);
    }
    
    /**
     * Elimina un laboratorio
     * @param id ID del laboratorio a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminar(Long id) {
        if (!laboratorioRepository.existsById(id)) {
            return false;
        }
        laboratorioRepository.deleteById(id);
        return true;
    }
    
    /**
     * Valida que los campos del laboratorio sean correctos
     * @param laboratorio Laboratorio a validar
     * @return true si es válido, false si no
     */
    public boolean validarLaboratorio(Laboratorio laboratorio) {
        if (laboratorio == null) return false;
        if (laboratorio.getNombre() == null || laboratorio.getNombre().trim().isEmpty()) return false;
        return true;
    }
} 