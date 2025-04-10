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
import mx.uam.ayd.proyecto.datos.SolicitudReabastecimientoRepository;
import mx.uam.ayd.proyecto.datos.DetallesSolicitudRepository;
import mx.uam.ayd.proyecto.datos.InventarioRepository;
import mx.uam.ayd.proyecto.negocio.modelo.CategoriaProducto;
import mx.uam.ayd.proyecto.negocio.modelo.Empleado;
import mx.uam.ayd.proyecto.negocio.modelo.Grupo;
import mx.uam.ayd.proyecto.negocio.modelo.Ingrediente;
import mx.uam.ayd.proyecto.negocio.modelo.Laboratorio;
import mx.uam.ayd.proyecto.negocio.modelo.Producto;
import mx.uam.ayd.proyecto.negocio.modelo.Sucursal;
import mx.uam.ayd.proyecto.negocio.modelo.TipoEmpleado;
import mx.uam.ayd.proyecto.negocio.modelo.Usuario;
import mx.uam.ayd.proyecto.negocio.modelo.SolicitudReabastecimiento;
import mx.uam.ayd.proyecto.negocio.modelo.DetallesSolicitud;
import mx.uam.ayd.proyecto.negocio.modelo.Inventario;
import java.util.Date;
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
	
	@Autowired
	SolicitudReabastecimientoRepository solicitudReabastecimientoRepository;
	
	@Autowired
	DetallesSolicitudRepository detallesSolicitudRepository;
	
	@Autowired
	InventarioRepository inventarioRepository;



	
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

		Sucursal sucursal0 = new Sucursal();
		sucursal0.setNombre("Almacén General");
		sucursal0.setDireccion("Av. Juárez 105, Col. Centro, CDMX");
		sucursal0.setTelefono("5551475567");
		sucursalRepository.save(sucursal0);

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
    	
    	// Creación de solicitudes de reabastecimiento de ejemplo
    	
    	// Solicitud 1: desde Sucursal Centro al Almacén General (no atendida)
    	SolicitudReabastecimiento solicitud1 = new SolicitudReabastecimiento();
    	solicitud1.setSucursal(sucursal1); // Sucursal Centro
    	solicitud1.setFecha(new Date());
    	solicitud1.setAtendida(false);
    	solicitudReabastecimientoRepository.save(solicitud1);
    	
    	// Detalles de la solicitud 1
    	DetallesSolicitud detalle1_1 = new DetallesSolicitud();
    	detalle1_1.setSolicitudReabastecimiento(solicitud1);
    	detalle1_1.setProducto(producto1); // Tempra 500mg
    	detalle1_1.setCantidad(20); // Necesitan 20 unidades
    	detallesSolicitudRepository.save(detalle1_1);
    	
    	DetallesSolicitud detalle1_2 = new DetallesSolicitud();
    	detalle1_2.setSolicitudReabastecimiento(solicitud1);
    	detalle1_2.setProducto(producto4); // Centrum Multivitamínico
    	detalle1_2.setCantidad(10); // Necesitan 10 unidades
    	detallesSolicitudRepository.save(detalle1_2);
    	
    	// Solicitud 2: desde Sucursal Norte al Almacén General (no atendida)
    	SolicitudReabastecimiento solicitud2 = new SolicitudReabastecimiento();
    	solicitud2.setSucursal(sucursal2); // Sucursal Norte
    	solicitud2.setFecha(new Date());
    	solicitud2.setAtendida(false);
    	solicitudReabastecimientoRepository.save(solicitud2);
    	
    	// Detalles de la solicitud 2
    	DetallesSolicitud detalle2_1 = new DetallesSolicitud();
    	detalle2_1.setSolicitudReabastecimiento(solicitud2);
    	detalle2_1.setProducto(producto2); // Amoxicilina 500mg
    	detalle2_1.setCantidad(15); // Necesitan 15 unidades
    	detallesSolicitudRepository.save(detalle2_1);
    	
    	DetallesSolicitud detalle2_2 = new DetallesSolicitud();
    	detalle2_2.setSolicitudReabastecimiento(solicitud2);
    	detalle2_2.setProducto(producto3); // Claritromicina 250mg/5mL
    	detalle2_2.setCantidad(8); // Necesitan 8 unidades
    	detallesSolicitudRepository.save(detalle2_2);
    	
    	DetallesSolicitud detalle2_3 = new DetallesSolicitud();
    	detalle2_3.setSolicitudReabastecimiento(solicitud2);
    	detalle2_3.setProducto(producto5); // Cetirizina 10mg
    	detalle2_3.setCantidad(12); // Necesitan 12 unidades
    	detallesSolicitudRepository.save(detalle2_3);
    	
    	// Solicitud 3: desde Sucursal Sur al Almacén General (ya atendida)
    	SolicitudReabastecimiento solicitud3 = new SolicitudReabastecimiento();
    	solicitud3.setSucursal(sucursal3); // Sucursal Sur
    	// Fecha de hace una semana para la solicitud ya atendida
    	Date fechaAnterior = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
    	solicitud3.setFecha(fechaAnterior);
    	solicitud3.setAtendida(true); // Ya fue atendida
    	solicitudReabastecimientoRepository.save(solicitud3);
    	
    	// Detalles de la solicitud 3 (atendida)
    	DetallesSolicitud detalle3_1 = new DetallesSolicitud();
    	detalle3_1.setSolicitudReabastecimiento(solicitud3);
    	detalle3_1.setProducto(producto1); // Tempra 500mg
    	detalle3_1.setCantidad(25); // Necesitaban 25 unidades
    	detallesSolicitudRepository.save(detalle3_1);
    	
    	DetallesSolicitud detalle3_2 = new DetallesSolicitud();
    	detalle3_2.setSolicitudReabastecimiento(solicitud3);
    	detalle3_2.setProducto(producto5); // Cetirizina 10mg
    	detalle3_2.setCantidad(18); // Necesitaban 18 unidades
    	detallesSolicitudRepository.save(detalle3_2);
    	
    	// Crear registros de inventario para el Almacén General (sucursal0)
    	// Estos registros permiten comparar el stock disponible vs. cantidades solicitadas
    	
    	// Inventario del producto 1 (Tempra 500mg) - Stock suficiente
    	Inventario inv1 = new Inventario();
    	inv1.setSucursal(sucursal0); // Almacén General
    	inv1.setProducto(producto1);
    	inv1.setStock(30); // Hay 30 unidades en stock (más que las 20 solicitadas)
    	inventarioRepository.save(inv1);
    	
    	// Inventario del producto 2 (Amoxicilina 500mg) - Stock insuficiente
    	Inventario inv2 = new Inventario();
    	inv2.setSucursal(sucursal0);
    	inv2.setProducto(producto2);
    	inv2.setStock(10); // Solo hay 10 unidades (menos que las 15 solicitadas)
    	inventarioRepository.save(inv2);
    	
    	// Inventario del producto 3 (Claritromicina) - Stock igual a lo solicitado
    	Inventario inv3 = new Inventario();
    	inv3.setSucursal(sucursal0);
    	inv3.setProducto(producto3);
    	inv3.setStock(8); // Exactamente lo que se solicita
    	inventarioRepository.save(inv3);
    	
    	// Inventario del producto 4 (Centrum Multivitamínico) - Sin stock
    	Inventario inv4 = new Inventario();
    	inv4.setSucursal(sucursal0);
    	inv4.setProducto(producto4);
    	inv4.setStock(0); // No hay stock
    	inventarioRepository.save(inv4);
    	
    	// Inventario del producto 5 (Cetirizina 10mg) - Stock suficiente
    	Inventario inv5 = new Inventario();
    	inv5.setSucursal(sucursal0);
    	inv5.setProducto(producto5);
    	inv5.setStock(35); // Hay 35 unidades (más que las 12 solicitadas)
    	inventarioRepository.save(inv5);
    	
    	// Registros de inventario para la Sucursal Norte (sucursal2)
    	// Estos registros son para la sucursal con ID 2
    	
    	// Inventario del producto 1 (Tempra 500mg) en Sucursal Norte
    	Inventario invNorte1 = new Inventario();
    	invNorte1.setSucursal(sucursal2); // Sucursal Norte
    	invNorte1.setProducto(producto1);
    	invNorte1.setStock(15); // 15 unidades en stock
    	inventarioRepository.save(invNorte1);
    	
    	// Inventario del producto 2 (Amoxicilina 500mg) en Sucursal Norte
    	Inventario invNorte2 = new Inventario();
    	invNorte2.setSucursal(sucursal2);
    	invNorte2.setProducto(producto2);
    	invNorte2.setStock(5); // 5 unidades en stock
    	inventarioRepository.save(invNorte2);
    	
    	// Inventario del producto 3 (Claritromicina) en Sucursal Norte
    	Inventario invNorte3 = new Inventario();
    	invNorte3.setSucursal(sucursal2);
    	invNorte3.setProducto(producto3);
    	invNorte3.setStock(2); // 2 unidades en stock
    	inventarioRepository.save(invNorte3);
    	
    	// Inventario del producto 4 (Centrum Multivitamínico) en Sucursal Norte
    	Inventario invNorte4 = new Inventario();
    	invNorte4.setSucursal(sucursal2);
    	invNorte4.setProducto(producto4);
    	invNorte4.setStock(8); // 8 unidades en stock
    	inventarioRepository.save(invNorte4);
    	
    	// Inventario del producto 5 (Cetirizina 10mg) en Sucursal Norte
    	Inventario invNorte5 = new Inventario();
    	invNorte5.setSucursal(sucursal2);
    	invNorte5.setProducto(producto5);
    	invNorte5.setStock(3); // 3 unidades en stock
    	inventarioRepository.save(invNorte5);
	}
}