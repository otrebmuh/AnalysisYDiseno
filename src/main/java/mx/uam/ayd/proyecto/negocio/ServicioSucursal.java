package mx.uam.ayd.proyecto.negocio;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uam.ayd.proyecto.datos.SucursalRepository;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;

@Service
public class ServicioSucursal {
    
    @Autowired
    private SucursalRepository sucursalRepository;
    
    /**
     * Obtiene todas las sucursales registradas
     * @return Lista de sucursales
     */
    public List<Sucursal> getAll() {
        return StreamSupport.stream(sucursalRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
    
    /**
     * Obtiene una sucursal por su ID
     * @param id ID de la sucursal
     * @return Sucursal si existe, null si no
     */
    public Sucursal obtenerPorId(Long id) {
        Optional<Sucursal> sucursal = sucursalRepository.findById(id);
        return sucursal.orElse(null);
    }
    
    /**
     * Registra una nueva sucursal
     * @param sucursal Sucursal a registrar
     * @return Sucursal registrada
     */
    public Sucursal crear(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }
    
    /**
     * Actualiza una sucursal existente
     * @param sucursal Sucursal a actualizar
     * @return Sucursal actualizada
     */
    public Sucursal actualizar(Sucursal sucursal) {
        if (!sucursalRepository.existsById(sucursal.getIdSucursal())) {
            return null;
        }
        return sucursalRepository.save(sucursal);
    }
    
    /**
     * Elimina una sucursal
     * @param id ID de la sucursal a eliminar
     * @return true si se eliminó, false si no existía
     */
    public boolean eliminar(Long id) {
        if (!sucursalRepository.existsById(id)) {
            return false;
        }
        sucursalRepository.deleteById(id);
        return true;
    }
    
    /**
     * Valida que los campos de la sucursal sean correctos
     * @param sucursal Sucursal a validar
     * @return true si es válido, false si no
     */
    public boolean validarSucursal(Sucursal sucursal) {
        if (sucursal == null) return false;
        if (sucursal.getNombre() == null || sucursal.getNombre().trim().isEmpty()) return false;
        if (sucursal.getDireccion() == null || sucursal.getDireccion().trim().isEmpty()) return false;
        if (sucursal.getTelefono() == null || sucursal.getTelefono().trim().isEmpty()) return false;
        return true;
    }
}