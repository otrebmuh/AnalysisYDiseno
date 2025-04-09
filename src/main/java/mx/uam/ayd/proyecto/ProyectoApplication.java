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
import mx.uam.ayd.proyecto.datos.TipoEmpleadoRepository;
import mx.uam.ayd.proyecto.datos.UsuarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.CategoriaProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Ingrediente;
import mx.uam.ayd.proyecto.negocio.modelo.Laboratorio;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.negocio.modelo.TipoEmpleado;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.presentacion.loggin.ControlLoggin;
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

	@Autowired
	TipoEmpleadoRepository tipoEmpleadoRepository;



	
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
		admin.setApellidoPaterno("Admin");

		TipoEmpleado gerente = new TipoEmpleado();
    	gerente.setNombre("Gerente");
    	tipoEmpleadoRepository.save(gerente);

    	TipoEmpleado almacenista = new TipoEmpleado();
    	almacenista.setNombre("Almacenista");
    	tipoEmpleadoRepository.save(almacenista);

    	TipoEmpleado cajero = new TipoEmpleado();
    	cajero.setNombre("Cajero");
    	tipoEmpleadoRepository.save(cajero);

		Sucursal sucursal1 = new Sucursal();
		sucursal1.setNombre("Sucursal Centro");
		sucursal1.setDireccion("Av. Juárez 100, Col. Centro, CDMX");
		sucursal1.setTelefono("5551234567");
		sucursalRepository.save(sucursal1);
		
		Sucursal sucursal2 = new Sucursal();
		sucursal2.setNombre("Sucursal Norte");
		sucursal2.setDireccion("Calz. Vallejo 200, Col. Industrial, CDMX");
		sucursal2.setTelefono("5552345678");
		sucursalRepository.save(sucursal2);
		
		Sucursal sucursal3 = new Sucursal();
		sucursal3.setNombre("Sucursal Sur");
		sucursal3.setDireccion("Calz. Taxqueña 300, Col. Campestre, CDMX");
		sucursal3.setTelefono("5553456789");
		sucursalRepository.save(sucursal3);
		
		Sucursal sucursal4 = new Sucursal();
		sucursal4.setNombre("Sucursal Poniente");
		sucursal4.setDireccion("Av. Toluca 400, Col. Las Américas, CDMX");
		sucursal4.setTelefono("5554567890");
		sucursalRepository.save(sucursal4);
		
		Sucursal sucursal5 = new Sucursal();
		sucursal5.setNombre("Sucursal Oriente");
		sucursal5.setDireccion("Av. Zaragoza 500, Col. Balbuena, CDMX");
		sucursal5.setTelefono("5555678901");
		sucursalRepository.save(sucursal5);

		Empleado empleado1 = new Empleado();
        empleado1.setNombre("Juan");
        empleado1.setApellidoPaterno("Pérez");
        empleado1.setApellidoMaterno("López");
        empleado1.setNumeroEmpleado("EMP001");
        empleado1.setCorreoElectronico("juan.perez@gmail.com");
        empleado1.setTelefono("5524924074");
		empleado1.setTipo(gerente); 
		empleado1.setSucursal(sucursal1);
        empleadoRepository.save(empleado1);

		Empleado empleado2 = new Empleado();
		empleado2.setNombre("José");
		empleado2.setApellidoPaterno("Sánchez");
		empleado2.setApellidoMaterno("Martínez");
		empleado2.setNumeroEmpleado("EMP002");
		empleado2.setCorreoElectronico("jose.sanchez@gmail.com");
		empleado2.setTelefono("5523456789");
		empleado2.setTipo(cajero); 
		empleado2.setSucursal(sucursal2);
		empleadoRepository.save(empleado2);

		Empleado empleado3 = new Empleado();
		empleado3.setNombre("Gilberto");
		empleado3.setApellidoPaterno("González");
		empleado3.setApellidoMaterno("López");
		empleado3.setNumeroEmpleado("EMP003");
		empleado3.setCorreoElectronico("gilberto.gonzalez@gmail.com");
		empleado3.setTelefono("5534567890");
		empleado3.setTipo(almacenista); // Asignar el tipo "Gerente"
		empleado3.setSucursal(sucursal3);
		empleadoRepository.save(empleado3);  

		CategoriaProducto categoriaAnalgesicos = new CategoriaProducto();
        categoriaAnalgesicos.setNombre("Analgésicos");
        categoriaAnalgesicos.setDescripcion("Medicamentos para el dolor");
        categoriaProductoRepository.save(categoriaAnalgesicos);

		CategoriaProducto categoriaAntibioticos = new CategoriaProducto();
		categoriaAntibioticos.setNombre("Antibióticos");
		categoriaAntibioticos.setDescripcion("Medicamentos para tratar infecciones bacterianas");
		categoriaProductoRepository.save(categoriaAntibioticos);

		CategoriaProducto categoriaVitaminas = new CategoriaProducto();
		categoriaVitaminas.setNombre("Vitaminas y Suplementos");
		categoriaVitaminas.setDescripcion("Suplementos vitamínicos para la salud y el bienestar");
		categoriaProductoRepository.save(categoriaVitaminas);

		CategoriaProducto categoriaAntialergicos = new CategoriaProducto();
		categoriaAntialergicos.setNombre("Antialérgicos");
		categoriaAntialergicos.setDescripcion("Medicamentos para tratar alergias y reacciones alérgicas");
		categoriaProductoRepository.save(categoriaAntialergicos);

		Laboratorio lab1 = new Laboratorio();
        lab1.setNombre("Bayer");
        LaboratorioRepository.save(lab1);

		Laboratorio lab2 = new Laboratorio();
		lab2.setNombre("Pfizer");
		LaboratorioRepository.save(lab2);
		
		Laboratorio lab3 = new Laboratorio();
		lab3.setNombre("Sanofi");
		LaboratorioRepository.save(lab3);
		
		Laboratorio lab4 = new Laboratorio();
		lab4.setNombre("MK");
		LaboratorioRepository.save(lab4);

		Ingrediente ing1 = new Ingrediente();
    	ing1.setNombre("Paracetamol");
    	ing1.setDescripcion("Analgésico y antipirético");
    	IngredienteRepository.save(ing1);

		Ingrediente ing2 = new Ingrediente();
		ing2.setNombre("Amoxicilina");
		ing2.setDescripcion("Antibiótico de amplio espectro para tratar infecciones bacterianas");
		IngredienteRepository.save(ing2);
		
		Ingrediente ing3 = new Ingrediente();
		ing3.setNombre("Claritromicina");
		ing3.setDescripcion("Antibiótico macrólido para infecciones respiratorias");
		IngredienteRepository.save(ing3);
		
		Ingrediente ing4 = new Ingrediente();
		ing4.setNombre("Multivitamínico");
		ing4.setDescripcion("Combinación de vitaminas y minerales esenciales para la salud");
		IngredienteRepository.save(ing4);
		
		Ingrediente ing5 = new Ingrediente();
		ing5.setNombre("Cetirizina");
		ing5.setDescripcion("Antihistamínico para tratar alergias y rinitis");
		IngredienteRepository.save(ing5);

		Producto producto1 = new Producto();
		producto1.setCodigo("1204101");
		producto1.setNombre("Tempra 500mg");
		producto1.setDescripcion("Tabletas de paracetamol");
		producto1.setContenido("10 tabletas");  // Nuevo campo
		producto1.setPrecio(50.00);
		producto1.setReceta(false);
		producto1.setCategoria(categoriaAnalgesicos);
		producto1.setIngrediente(ing1);
		producto1.setLaboratorio(lab1);
		productoRepository.save(producto1);
		
		Producto producto2 = new Producto();
		producto2.setCodigo("0012345");
		producto2.setNombre("Amoxicilina 500mg");
		producto2.setDescripcion("Cápsulas de amoxicilina para infecciones bacterianas");
		producto2.setContenido("12 cápsulas");  // Nuevo campo
		producto2.setPrecio(120.00);
		producto2.setReceta(true);
		producto2.setCategoria(categoriaAntibioticos);
		producto2.setIngrediente(ing2);
		producto2.setLaboratorio(lab2);
		productoRepository.save(producto2);
		
		Producto producto3 = new Producto();
		producto3.setCodigo("0023456");
		producto3.setNombre("Claritromicina 250mg/5mL");
		producto3.setDescripcion("Suspensión oral de claritromicina para infecciones respiratorias");
		producto3.setContenido("60 mL");  // Nuevo campo
		producto3.setPrecio(180.00);
		producto3.setReceta(true);
		producto3.setCategoria(categoriaAntibioticos);
		producto3.setIngrediente(ing3);
		producto3.setLaboratorio(lab3);
		productoRepository.save(producto3);
		
		Producto producto4 = new Producto();
		producto4.setCodigo("0067890");
		producto4.setNombre("Centrum Multivitamínico 30 tabletas");
		producto4.setDescripcion("Tabletas con vitaminas y minerales esenciales para el bienestar diario");
		producto4.setContenido("30 tabletas");  // Nuevo campo
		producto4.setPrecio(250.00);
		producto4.setReceta(false);
		producto4.setCategoria(categoriaVitaminas);
		producto4.setIngrediente(ing4);
		producto4.setLaboratorio(lab1);
		productoRepository.save(producto4);
		
		Producto producto5 = new Producto();
		producto5.setCodigo("0056789");
		producto5.setNombre("Cetirizina 10mg");
		producto5.setDescripcion("Tabletas antihistamínicas para el alivio de alergias y rinitis");
		producto5.setContenido("20 tabletas");  // Nuevo campo
		producto5.setPrecio(95.00);
		producto5.setReceta(false);
		producto5.setCategoria(categoriaAntialergicos);
		producto5.setIngrediente(ing5);
		producto5.setLaboratorio(lab4);
		productoRepository.save(producto5);
		
		Usuario usuario1 = new Usuario();
    	usuario1.setNombre("Juan");
    	usuario1.setApellido("Pérez");
    	usuario1.setEdad(30);
		usuario1.setPassword("1234");
		usuario1.setEmpleado(empleado1);
    	usuarioRepository.save(usuario1);

		Usuario usuario2 = new Usuario();
    	usuario2.setNombre("José");
    	usuario2.setApellido("Sánchez");
    	usuario2.setEdad(45);
		usuario2.setPassword("1234");
    	usuarioRepository.save(usuario2);

		Usuario usuario3 = new Usuario();
    	usuario3.setNombre("Gilberto");
    	usuario3.setApellido("González");
    	usuario3.setEdad(25);
		usuario3.setPassword("1234");
		usuario3.setEmpleado(empleado3);
    	usuarioRepository.save(usuario3);

	}
}