package mx.uam.ayd.proyecto;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import mx.uam.ayd.proyecto.datos.CategoriaProductoRepository;
import mx.uam.ayd.proyecto.datos.EmpleadoRepository;
import mx.uam.ayd.proyecto.datos.GrupoRepository;
import mx.uam.ayd.proyecto.datos.LaboratorioRepository;
import mx.uam.ayd.proyecto.datos.ProductoRepository;
import mx.uam.ayd.proyecto.datos.IngredienteRepository;
import mx.uam.ayd.proyecto.datos.SucursalRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.CategoriaProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Ingrediente;
import mx.uam.ayd.proyecto.negocio.modelo.Laboratorio;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.presentacion.principal.ControlPrincipal;


/**
 * 
 * Clase principal que arranca la aplicación 
 * construida usando el principio de 
 * inversión de control
 * 
 * 
 * @author Humberto Cervantes (c) 21 Nov 2022
 * Este cambio es en la rama de trabajo
 */
@SpringBootApplication
public class ProyectoApplication {

	@Autowired
	ControlLoggin controlLoggin;


	@Autowired
	ControlPrincipal controlPrincipal;

	@Autowired
	CategoriaProductoRepository categoriaProductoRepository;

	@Autowired
	LaboratorioRepository LaboratorioRepository;

	@Autowired
	IngredienteRepository IngredienteRepository;
	
	@Autowired
	GrupoRepository grupoRepository;

	@Autowired
	EmpleadoRepository empleadoRepository;

	@Autowired
	SucursalRepository sucursalRepository;

	@Autowired
	ProductoRepository productoRepository;

	@Autowired
	UsuarioRepository usuarioRepository;



	
	/**
	 * 
	 * Método principal
	 *
	 * @params args argumentos de la línea de comando
	 * 
	 */
	public static void main(String[] args) {
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder(ProyectoApplication.class);

		builder.headless(false);

		builder.run(args);
	}

	/**
	 * Metodo que arranca la aplicacion
	 * inicializa la bd y arranca el controlador
	 * otro comentario
	 */
	@PostConstruct
	public void inicia() {
		
		inicializaBD();
		
		//controlPrincipal.inicia();
		controlLoggin.inicia();
	}
	
	
	/**
	 * 
	 * Inicializa la BD con datos
	 * 
	 */
	public void inicializaBD() {
		
		// Vamos a crear los dos grupos de usuarios
		
		Grupo grupoAdmin = new Grupo();
		grupoAdmin.setNombre("Administradores");
		grupoRepository.save(grupoAdmin);
		
		Grupo grupoOps = new Grupo();
		grupoOps.setNombre("Operadores");
		grupoRepository.save(grupoOps);

		Empleado admin = new Empleado();
		admin.setNombre("Admin");
		empleadoRepository.save(admin);
		
		/**Empleado empleado1 = new Empleado();
        empleado1.setNombre("Juan");
        empleado1.setApellidoPaterno("Pérez");
        empleado1.setApellidoMaterno("López");
        empleado1.setNumeroEmpleado("EMP001");
        empleado1.setCorreoElectronico("juan.perez@gmail.com");
        empleado1.setTelefono("5524924074");
        empleadoRepository.save(empleado1);
		*/

		Sucursal sucursal1 = new Sucursal();
        sucursal1.setNombre("Sucursal Centro");
        sucursalRepository.save(sucursal1);

		CategoriaProducto categoriaAnalgesicos = new CategoriaProducto();
        categoriaAnalgesicos.setNombre("Analgésicos");
        categoriaAnalgesicos.setDescripcion("Medicamentos para el dolor");
        categoriaProductoRepository.save(categoriaAnalgesicos);

		Laboratorio lab1 = new Laboratorio();
        lab1.setNombre("Bayer");
        LaboratorioRepository.save(lab1);

		Ingrediente ing1 = new Ingrediente();
    	ing1.setNombre("Paracetamol");
    	ing1.setDescripcion("Analgésico y antipirético");
    	IngredienteRepository.save(ing1);

		Producto producto1 = new Producto();
    	producto1.setCodigo("1204101");
    	producto1.setNombre("Tempra 500mg");
    	producto1.setDescripcion("Tabletas de paracetamol");
    	producto1.setPrecio(50.00);
    	producto1.setReceta(false);
    	producto1.setCategoria(categoriaAnalgesicos);
    	producto1.setIngrediente(ing1);
    	producto1.setLaboratorio(lab1);
    	productoRepository.save(producto1);

		Usuario usuario1 = new Usuario();
		usuario1.setNombre("Juan");
		usuario1.setApellido("Pérez");
		usuario1.setEdad(30);
		usuarioRepository.save(usuario1);

	}
}