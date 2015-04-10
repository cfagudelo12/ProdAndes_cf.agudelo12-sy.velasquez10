package dao;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;

import vos.*;

/**
* Clase ConsultaDAO, encargada de hacer las consultas a la base de datos
*/
public class ConsultaDAO extends oracle.jdbc.driver.OracleDriver
{

	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	/**
	 * Ruta donde se encuentra el archivo de conexion.
	 */
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";
	
	/**
	 * Constante que representa el id de la empresa de prueba.
	 */
	private static final int idEmpresaF = 1;
	
	//----------------------------------------------------
	// Constantes de tablas
	//----------------------------------------------------
	
	/**
	 * Constante que representa el nombre de la tabla Empresas
	 */
	private static final String tEmpresas="Empresas";
	
	/**
	 * Constante que representa el nombre de la tabla Recursos
	 */
	private static final String tRecursos="Recursos";
	
	/**
	 * Constante que representa el nombre de la tabla Usuarios
	 */
	private static final String tUsuarios="Usuarios";
	
	/**
	 * Constante que representa el nombre de la tabla Proveedores
	 */	
	private static final String tProveedores="Proveedores";
	
	/**
	 * Constante que representa el nombre de la tabla Clientes
	 */
	private static final String tClientes="Clientes";

	/**
	 * Constante que representa el nombre de la tabla Empleados
	 */
	private static final String tEmpleados="Empleados";

	/**
	 * Constante que representa el nombre de la tabla Pedidos
	 */
	private static final String tPedidos="Pedidos";

	/**
	 * Constante que representa el nombre de la tabla Productos
	 */
	private static final String tProductos="Productos";

	/**
	 * Constante que representa el nombre de la tabla Trabajan
	 */
	private static final String tTrabajan="Trabajan";

	/**
	 * Constante que representa el nombre de la tabla Solicitan
	 */
	private static final String tSolicitan="Solicitan";

	/**
	 * Constante que representa el nombre de la tablaCompran
	 */
	private static final String tCompran="Compran";

	/**
	 * Constante que representa el nombre de la tabla ProcesosProduccion
	 */
	private static final String tProcesosProduccion="ProcesosProduccion";

	/**
	 * Constante que representa el nombre de la tabla EtapasProduccion
	 */
	private static final String tEtapasProduccion="EtapasProduccion";

	/**
	 * Constante que representa el nombre de la tabla EtapasProduccion
	 */
	private static final String tEtapasProduccionPedido="EtapasProduccionPedido";
	
	/**
	 * Constante que representa el nombre de la tabla EstacionesProduccion
	 */
	private static final String tEstacionesProduccion="EstacionesProduccion";

	/**
	 * Constante que representa el nombre de la tabla Operan
	 */
	private static final String tOperan="Operan";
	
	/**
	 * Constante que representa el nombre de la tabla Tienen
	 */
	private static final String tTienen="Tienen";

	/**
	 * Constante que representa el nombre de la tablaClientela
	 */
	private static final String tClientela="Clientela";

	/**
	 * Constante que representa el nombre de la tabla Proveen
	 */
	private static final String tProveen="Proveen";
	
	/**
	 * Constante que representa el nombre de la tabla Requieren
	 */
	private static final String tRequieren="Requieren";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	/**
	 * Conexion con la base de datos
	 */
	public Connection conexion;
	
	/**
	 * Nombre del usuario para conectarse a la base de datos.
	 */
	public final static String usuario="ISIS2304481510";
	
	/**
	 * Clave de conexion a la base de datos.
	 */
	public final static String clave="fberrapius";
	
	/**
	 * URL al cual se debe conectar para acceder a la base de datos.
	 */
	public final static String cadenaConexion= "jdbc:oracle:thin:@prod.oracle.virtual.uniandes.edu.co:1531:prod";
	
	/**
	 * Constructor de la clase. No inicializa ningun atributo.
	 */
	public ConsultaDAO()
	{
		try {
			registrarUsuario(31, "a", UsuarioValue.empleado, "a", "a", "a", "a", "a", "a", "a", "a", "a", 1, 1, EmpleadoValue.operario, 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//-------------------------------------------------
	// Metodos
	//-------------------------------------------------

	/**
	 * Obtiene los datos necesarios para establecer una conexion
	 * Los datos se obtienen a partir de un archivo properties.
	 * @param path Ruta donde se encuentra el archivo properties.
	 */
	public void inicializar(String path)
	{
		
	}

	/**
	 * Metodo que se encarga de crear la conexion con el Driver Manager a partir de los parametros recibidos.
	 * @param url Direccion url de la base de datos a la cual se desea conectar
	 * @param usuario Nombre del usuario que se va a conectar a la base de datos
	 * @param clave Clave de acceso a la base de datos
	 * @throws SQLException si ocurre un error generando la conexion con la base de datos.
	 */
	private void establecerConexion(String u,String p,String a) throws SQLException
	{
		try{
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@prod.oracle.virtual.uniandes.edu.co:1531:prod","ISIS2304481510","fberrapius");
		}
		catch(SQLException exception){
			throw new SQLException("ERROR: ConsultaDAO obteniendo una conexion.");
		}
	}
	
	 /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args
     */
    public static void main( String[] args ) 
    {
        try {
			new ConsultaDAO();
		} 
        catch (Exception e) {
			e.printStackTrace();
		}
    }

	/**
	 * Cierra la conexion activa a la base de datos.
	 * @param connection Conexion a la base de datos.
	 * @throws Exception Si se presentan errores de conexion.
	 */
	public void closeConnection(Connection connection) throws Exception 
	{        
		try{
			connection.close();
			connection = null;
		}
		catch (SQLException exception){
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexion.");
		}
	} 
   
   //---------------------------------------------------
   // Metodos asociados a los casos de uso: Consulta
   //---------------------------------------------------

	public ArrayList<PedidoValue> consultarPedidos() throws Exception{
		ArrayList<PedidoValue> pedidos = new ArrayList<PedidoValue>();
		PreparedStatement selStmt = null;
		try{
			String consulta = "SELECT * FROM "+tPedidos+" NATURAL INNER JOIN "+tSolicitan+" s WHERE s."+EmpresaValue.cIdEmpresa+"="+idEmpresaF+"";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next()){
				PedidoValue pedido = new PedidoValue();
				pedido.setIdPedido(rs.getInt(PedidoValue.cIdPedido));
				pedido.setMonto(rs.getFloat(PedidoValue.cMonto));
				pedido.setFechaPedido(rs.getDate(PedidoValue.cFechaPedido));
				pedido.setFechaEsperada(rs.getDate(PedidoValue.cFechaEsperada));
				pedido.setFechaLlegada(rs.getDate(PedidoValue.cFechaLlegada));
				pedido.setEstado(rs.getString(PedidoValue.cEstado));
				pedidos.add(pedido);
				pedido = new PedidoValue();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null) 
			{
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return pedidos;
	}
	
	public ArrayList<ClienteValue> consultarClientes() throws Exception{
		ArrayList<ClienteValue> clientes = new ArrayList<ClienteValue>();
		PreparedStatement selStmt = null;
		try{
			String consulta = "SELECT * FROM "+tUsuarios+" NATURAL INNER JOIN "+tClientes+" NATURAL INNER JOIN "+tClientela+" c WHERE c."+EmpresaValue.cIdEmpresa+"="+idEmpresaF+"";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next()){
				ClienteValue cliente = new ClienteValue();
				cliente.setId(rs.getInt(UsuarioValue.cId));
				cliente.setClave(rs.getString(UsuarioValue.cClave));
				cliente.setTipoUsuario(rs.getString(UsuarioValue.cTipoUsuario));
				cliente.setCedula(rs.getString(UsuarioValue.cCedula));
				cliente.setNombre(rs.getString(UsuarioValue.cNombre));
				cliente.setNacionalidad(rs.getString(UsuarioValue.cNacionalidad));
				cliente.setDireccionFisica(rs.getString(UsuarioValue.cDireccionFisica));
				cliente.setTelefono(rs.getString(UsuarioValue.cTelefono));
				cliente.setEmail(rs.getString(UsuarioValue.cEmail));
				cliente.setDepartamento(rs.getString(UsuarioValue.cDepartamento));
				cliente.setCiudad(rs.getString(UsuarioValue.cCiudad));
				cliente.setCodigoPostal(rs.getString(UsuarioValue.cCodigoPostal));
				cliente.setIdRepresentanteLegal(rs.getInt(ClienteValue.cIdRepresentanteLegal));
				cliente.setRegistroSINV(rs.getInt(ClienteValue.cRegistroSINV));
				clientes.add(cliente);
				cliente = new ClienteValue();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null) 
			{
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return clientes;
	}
	
	public ArrayList<ProveedorValue> consultarProveedores() throws Exception{
		ArrayList<ProveedorValue> proveedores = new ArrayList<ProveedorValue>();
		PreparedStatement selStmt = null;
		try{
			String consulta = "SELECT * FROM "+tUsuarios+" NATURAL INNER JOIN "+tProveedores;
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next()){
				ProveedorValue proveedor = new ProveedorValue();
				proveedor.setId(rs.getInt(UsuarioValue.cId));
				proveedor.setClave(rs.getString(UsuarioValue.cClave));
				proveedor.setTipoUsuario(rs.getString(UsuarioValue.cTipoUsuario));
				proveedor.setCedula(rs.getString(UsuarioValue.cCedula));
				proveedor.setNombre(rs.getString(UsuarioValue.cNombre));
				proveedor.setNacionalidad(rs.getString(UsuarioValue.cNacionalidad));
				proveedor.setDireccionFisica(rs.getString(UsuarioValue.cDireccionFisica));
				proveedor.setTelefono(rs.getString(UsuarioValue.cTelefono));
				proveedor.setEmail(rs.getString(UsuarioValue.cEmail));
				proveedor.setDepartamento(rs.getString(UsuarioValue.cDepartamento));
				proveedor.setCiudad(rs.getString(UsuarioValue.cCiudad));
				proveedor.setCodigoPostal(rs.getString(UsuarioValue.cCodigoPostal));
				proveedor.setIdRepresentanteLegal(rs.getInt(ProveedorValue.cIdRepresentanteLegal));
				proveedor.setVolumenMaximo(rs.getInt(ProveedorValue.cVolumenMaximo));
				proveedores.add(proveedor);
				proveedor = new ProveedorValue();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null) 
			{
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return proveedores;
	}
	
	/**
	 * Metodo encargado de consultar en la base de datos la existencia de un recurso, 
	 * segun el tipo de material, un rango de cantidad, una fecha de solicitud y fecha de entrega
	 * @param tipoMaterial El tipo de material. Materia prima o Componente.
	 * @param rInferior El limite inferior del rango.
	 * @param rSuperior El limite superior del rango.
	 * @param idEtapaProduccion El id de la etapa de produccion.
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return Una lista con los recursos que cumplen con los parametros.
	 * @throws Exception Si hay algun problema en la conexion o si no hay existencias disponibles.
	 */
	public ArrayList<RecursoValue> consultarExistenciasRecurso(String tipoMaterial, int rInferior, int rSuperior, int idEtapaProduccion, String fechaSolicitud, String fechaEntrega) throws Exception{
		ArrayList<RecursoValue> recursos = new ArrayList<RecursoValue>();
		PreparedStatement selStmt = null;
		try{
			String consulta	= generarConsultaExistenciaRecurso(tipoMaterial, rInferior, rSuperior, idEtapaProduccion, fechaSolicitud, fechaEntrega);
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();
			while(rs.next()){
				RecursoValue recurso = new RecursoValue();
				recurso.setIdRecurso(rs.getInt(RecursoValue.cIdRecurso));
				recurso.setNombre(rs.getString(RecursoValue.cNombre));
				recurso.setTipoRecurso(rs.getString(RecursoValue.cTipoRecurso));
				recurso.setUnidadMedida(rs.getString(RecursoValue.cUnidadMedida));
				recurso.setIdProveedor(rs.getInt(RecursoValue.cIdProveedor));
				recurso.setCantidadMDistribucion(rs.getInt(RecursoValue.cCantidadMDistribucion));
				recurso.setTiempoEntrega(rs.getInt(RecursoValue.cTiempoEntrega));
				recursos.add(recurso);
				recurso = new RecursoValue();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null) 
			{
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return recursos;
	}
	
	/**
	 * Metodo encargado en generar el query de la consulta de existencias de un recurso.
	 * @param tipoMaterial El tipo de material. Materia prima o Componente.
	 * @param rInferior El limite inferior del rango.
	 * @param rSuperior El limite superior del rango.
	 * @param idEtapaProduccion El id de la etapa de produccion.
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return El query respectivo a la solicitud.
	 */
	private String generarConsultaExistenciaRecurso(String tipoMaterial, int rInferior, int rSuperior, int idEtapaProduccion, String fechaPedido, String fechaLlegada)
	{
		String consulta = "SELECT * FROM "+tRecursos+" NATURAL INNER JOIN (SELECT "+RecursoValue.cIdRecurso+" FROM "+tTienen+" WHERE "+EmpresaValue.cIdEmpresa+"="+idEmpresaF+") WHERE "+RecursoValue.cTipoRecurso+"='"+tipoMaterial+"'";
		if(rInferior!=0||rSuperior!=0||idEtapaProduccion!=0||!fechaPedido.equals("")||!fechaLlegada.equals("")){
			if(rInferior>0 && rInferior<rSuperior){
				consulta+=" AND "+RecursoValue.cIdRecurso+" IN (SELECT "+RecursoValue.cIdRecurso+" FROM "+tRecursos+" NATURAL INNER JOIN (SELECT "+RecursoValue.cIdRecurso+" FROM "+tTienen+" WHERE "+EmpresaValue.cIdEmpresa+"="+idEmpresaF+
						" AND "+RecursoValue.cfCantidadEnBodega+" BETWEEN "+rInferior+" AND "+rSuperior+"))";
			}
			if(idEtapaProduccion>0){
				consulta+=" AND "+RecursoValue.cIdRecurso+" IN (SELECT "+RecursoValue.cIdRecurso+" FROM "+tRecursos+" NATURAL INNER JOIN (SELECT "+RecursoValue.cIdRecurso+" FROM "+tRequieren+" WHERE "+EtapaProduccionValue.cIdEtapaProduccion+"="+idEtapaProduccion+"))";
			}
			if(fechaPedido!=null){
				consulta+=" AND "+RecursoValue.cIdRecurso+" IN(SELECT "+RecursoValue.cIdRecurso+" FROM "+tSolicitan+" NATURAL INNER JOIN "+tPedidos+" WHERE "+PedidoValue.cFechaPedido+"=TO_DATE('"+fechaPedido+"', 'YYYY-MM-DD'))";
			}
			if(fechaLlegada!=null){
				consulta+=" AND "+RecursoValue.cIdRecurso+" IN(SELECT "+RecursoValue.cIdRecurso+" FROM "+tSolicitan+" NATURAL INNER JOIN "+tPedidos+" WHERE "+PedidoValue.cFechaLlegada+"=TO_DATE('"+fechaLlegada+"', 'YYYY-MM-DD'))";
			}
		}
		return consulta;
	}
	
	/**
	 * Metodo encargado de consultar en la base de datos la existencia de un producto, dado un rango de cantidad,
	 * un proceso de producción, una fecha de solicitud y fecha de entrega.
	 * @param rInferior El limite inferior del rango.
	 * @param rSuperior El limite superior del rango.
	 * @param idProcesoProduccion El id del proceso de producción.
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return Una lista con los productos que cumplen con los parametros.
	 * @throws Exception Si hay algun problema en la conexion o si no hay existencias disponibles.
	 */
	public ArrayList<ProductoValue> consultarExistenciasProductos(int rInferior, int rSuperior, int idProcesoProduccion, String fechaSolicitud, String fechaEntrega) throws Exception
	{
		ArrayList<ProductoValue> productos = new ArrayList<ProductoValue>();
		PreparedStatement selStmt = null;
		try{
			String consulta	= generarConsultaExistenciasProductos(rInferior, rSuperior, idProcesoProduccion, fechaSolicitud, fechaEntrega);
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();
			while(rs.next()){
				ProductoValue producto = new ProductoValue();
				producto.setIdProducto(rs.getInt(ProductoValue.cIdProducto));
				producto.setNombre(rs.getString(ProductoValue.cNombre));
				producto.setCosto(rs.getFloat(ProductoValue.cCosto));
				producto.setUnidadesProducidas(rs.getInt(ProductoValue.cUnidadesVendidas));
				producto.setUnidadesEnProduccion(rs.getInt(ProductoValue.cUnidadesEnProduccion));
				producto.setUnidadesVendidas(rs.getInt(ProductoValue.cUnidadesVendidas));
				producto.setCantidadEnBodega(rs.getInt(ProductoValue.cCantidadEnBodega));
				producto.setIdEmpresa(rs.getInt(ProductoValue.cIdEmpresa));
				producto.setIdProcesoProduccion(rs.getInt(ProductoValue.cIdProcesoProduccion));
				productos.add(producto);
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null) 
			{
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return productos;
	}
	
	/**
	 * Metodo encargado en generar el query de la consulta de existencias.
	 * @param rInferior El limite inferior del rango.
	 * @param rSuperior El limite superior del rango.
	 * @param idProcesoProduccion El id del proceso de produccion.
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return El query respectivo a la solicitud.
	 */
	private String generarConsultaExistenciasProductos(int rInferior, int rSuperior, int idProcesoProduccion, String fechaPedido, String fechaLlegada){
		String consulta = "SELECT * FROM "+tProductos+" WHERE "+EmpresaValue.cIdEmpresa+"="+idEmpresaF+" AND "+ProductoValue.cfCantidadEnBodega+">0";
		if(rInferior>0 && rInferior<rSuperior){
			consulta+=" AND "+ProductoValue.cfCantidadEnBodega+" BETWEEN "+rInferior+" AND "+rSuperior;
		}
		if(idProcesoProduccion>0){
			consulta+=" AND "+ProcesoProduccionValue.cIdProcesoProduccion+"="+idProcesoProduccion;
		}
		if(fechaPedido!=null){
			consulta+=" AND "+ProductoValue.cIdProducto+" IN(SELECT "+ProductoValue.cIdProducto+" FROM "+tCompran+" NATURAL INNER JOIN "+tPedidos+" WHERE "+PedidoValue.cFechaPedido+"=TO_DATE('"+fechaPedido+"', 'YYYY-MM-DD'))";
		}
		if(fechaLlegada!=null){
			consulta+=" AND "+ProductoValue.cIdProducto+" IN(SELECT "+ProductoValue.cIdProducto+" FROM "+tCompran+" NATURAL INNER JOIN "+tPedidos+" WHERE "+PedidoValue.cFechaLlegada+"=TO_DATE('"+fechaLlegada+"', 'YYYY-MM-DD'))";
		}
		return consulta;
	}
	
	/**
	 * Metodo encargado de buscar un material segun los parametros.
	 * @param volumen El volumen que se quiere buscar.
	 * @param desde Desde cuando se quiere buscar.
	 * @param hasta Hasta cuando se quiere buscar.
	 * @param costo El costo de un material.
	 * @return Una lista con materiales.
	 * @throws Exception Si hay un error en alguna parte del proceso
	 */
	public ArrayList<MaterialValue> consultarRecurso(int volumen, String desde, String hasta, Float costo) throws Exception
	{
		ArrayList<MaterialValue> materiales= new ArrayList<MaterialValue>();
		PreparedStatement selStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT * FROM "+tEmpresas+" em,"+tRecursos+" r,"+tSolicitan+" s,"+tEtapasProduccion+" e,"+tRequieren+" req,"+tProcesosProduccion+" pro,"+tPedidos+" p, "+tTienen+" t"+ 
					"WHERE em."+EmpresaValue.cIdEmpresa+"="+idEmpresaF+" AND r."+RecursoValue.cIdRecurso+"=s."+RecursoValue.cIdRecurso+" AND s."+PedidoValue.cIdPedido+"=p."+PedidoValue.cIdPedido+
					" AND r."+RecursoValue.cIdRecurso+"=req."+RecursoValue.cIdRecurso+" AND req."+EtapaProduccionValue.cIdEtapaProduccion+"=e."+EtapaProduccionValue.cIdEtapaProduccion+""+
					"AND pro."+ProcesoProduccionValue.cIdProcesoProduccion+"=e."+ProcesoProduccionValue.cIdProcesoProduccion+" AND t."+EmpresaValue.cIdEmpresa+"=em."+EmpresaValue.cIdEmpresa+" AND t."+
					RecursoValue.cIdRecurso+"=r."+RecursoValue.cIdRecurso+"";
			if(volumen>0){
				queryConsulta+=" AND t."+RecursoValue.cfCantidadEnBodega+"="+volumen;
			}
			if(costo>0){
				queryConsulta+=" AND p."+PedidoValue.cMonto+"="+costo;
			}
			if(!desde.equals("")&&!hasta.equals("")){
				queryConsulta+="AND (p."+PedidoValue.cFechaLlegada+">TO_DATE('"+desde+"','YYYY-MM-DD') OR p."+PedidoValue.cFechaLlegada+"<TO_DATE('"+hasta+"','YYYY-MM-DD'))";
			}
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			while(rs.next())
			{
				MaterialValue m= new MaterialValue();
				RecursoValue recurso = new RecursoValue();
				recurso.setIdRecurso(rs.getInt(RecursoValue.cIdRecurso));
				recurso.setNombre(rs.getString(RecursoValue.cNombre));
				recurso.setTipoRecurso(rs.getString(RecursoValue.cTipoRecurso));
				recurso.setUnidadMedida(rs.getString(RecursoValue.cUnidadMedida));
				recurso.setIdProveedor(rs.getInt(RecursoValue.cIdProveedor));
				recurso.setCantidadMDistribucion(rs.getInt(RecursoValue.cCantidadMDistribucion));
				recurso.setTiempoEntrega(rs.getInt(RecursoValue.cTiempoEntrega));
				m.setRecurso(recurso);
				m.agregarEtapasProduccion(""+rs.getInt(EtapaProduccionValue.cNumeroEtapa));
				m.agregarPedidos(""+rs.getInt(PedidoValue.cIdPedido));
				m.agregarProductos(""+rs.getInt(ProcesoProduccionValue.cIdProducto));
				materiales.add(m);
			}
			return materiales;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally
		{
			if (selStmt != null) 
			{
				try
				{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}
	
	/**
	 * Metodo encardo de buscar un proucto segun los parametros
	 * @param volumen El volumen que se quiere buscar
	 * @param costo El costo de un material.
	 * @return Una lista con materiales.
	 * @throws Exception Si hay un error en alguna parte del proceso
	 */
	public ArrayList<MaterialValue> consultarProducto(int cantidad, float costo) throws Exception 
	{
		ArrayList<MaterialValue> materiales= new ArrayList<MaterialValue>();
		PreparedStatement selStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT * FROM "+tProductos+" p, "+tRecursos+" r, "+tEtapasProduccion+" e,"+tRequieren+" req,"+tProcesosProduccion+" pro,"+
			tPedidos+" pe, "+tCompran+" c WHERE p."+ProductoValue.cIdProducto+"=pro."+ProductoValue.cIdProducto+" AND pro."+ProcesoProduccionValue.cIdProcesoProduccion+
			"=e."+ProcesoProduccionValue.cIdProcesoProduccion+" AND req."+EtapaProduccionValue.cIdEtapaProduccion+"=e."+EtapaProduccionValue.cIdEtapaProduccion+
			" AND req."+RecursoValue.cIdRecurso+"=r."+RecursoValue.cIdRecurso+" AND c."+ProductoValue.cIdProducto+"=p."+ProductoValue.cIdProducto+" AND pe."+PedidoValue.cIdPedido+
			"=c."+PedidoValue.cIdPedido+"";
			if(cantidad>0){
				queryConsulta+=" AND p."+ProductoValue.cCantidadEnBodega+"="+cantidad;
			}
			if(costo>0){
				queryConsulta+=" AND p."+ProductoValue.cCosto+"="+costo;
			}
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			while(rs.next())
			{
				MaterialValue m= new MaterialValue();
				m.agregarRecursoQueLoCompone(rs.getString(RecursoValue.cNombre));
				m.agregarPedidos(""+rs.getInt(PedidoValue.cIdPedido));
				materiales.add(m);
			}
			return materiales;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally
		{
			if (selStmt != null) 
			{
				try
				{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}

	//TODO:
	public EmpleadoValue consultarOperarioMasActivo() throws Exception{
		EmpleadoValue r=null;
		PreparedStatement selStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT FROM "+tEmpleados+"";
			return null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally
		{
			if (selStmt != null) 
			{
				try
				{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}
	
	//---------------------------------------------------
	// Metodos asociados a los casos de uso: Modificacion
	//---------------------------------------------------
	
	//TODO:
	public void registrarProducto(int idProducto, String nombre, float costo){}
	
	public void registrarUsuario(int id, String clave, String tipoUsuario,
			String cedula, String nombre, String nacionalidad, String direccionFisica,
			String telefono, String email, String departamento, String ciudad, String codigoPostal,
			int idRepresentanteLegal, int registro_volumen_idEmpresa, String tipoEmpleado, long sueldo) throws Exception{
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String query="INSERT INTO "+tUsuarios+"("+UsuarioValue.cId+","+UsuarioValue.cTipoUsuario+","+UsuarioValue.cClave+","+UsuarioValue.cCedula+","+UsuarioValue.cNombre+","+
			UsuarioValue.cNacionalidad+","+UsuarioValue.cDireccionFisica+","+UsuarioValue.cTelefono+","+UsuarioValue.cEmail+","+
			UsuarioValue.cDepartamento+","+UsuarioValue.cCiudad+","+UsuarioValue.cCodigoPostal+") VALUES ("+id+",'"+tipoUsuario+"','"+clave+"','"+cedula+"','"+nombre+
			"','"+nacionalidad+"','"+direccionFisica+"','"+telefono+"','"+email+"','"+departamento+"','"+ciudad+"','"+codigoPostal+"')";
			stmt=conexion.prepareStatement(query);
			stmt.executeQuery();
			if(tipoUsuario.equals(UsuarioValue.cliente)){
				String query2="INSERT INTO"+tClientes+"("+ClienteValue.cId+","+ClienteValue.cIdRepresentanteLegal+","+ClienteValue.cRegistroSINV+")"
						+ " VALUES ("+id+","+idRepresentanteLegal+","+registro_volumen_idEmpresa+")";
				stmt2=conexion.prepareStatement(query2);
				stmt2.executeQuery();
			}
			else if(tipoUsuario.equals(UsuarioValue.proveedor)){
				String query2="INSERT INTO"+tProveedores+"("+ProveedorValue.cId+","+ProveedorValue.cIdRepresentanteLegal+","+ProveedorValue.cVolumenMaximo+")"
						+ " VALUES ("+id+","+idRepresentanteLegal+","+registro_volumen_idEmpresa+")";
				stmt2=conexion.prepareStatement(query2);
				stmt2.executeQuery();
			}
			else if(tipoUsuario.equals(UsuarioValue.empleado)){
				String query2="INSERT INTO"+tEmpleados+"("+EmpleadoValue.cId+","+EmpleadoValue.cSueldo+","+EmpleadoValue.cTipoEmpleado+","+EmpleadoValue.cfIdEmpresa+")"
						+ " VALUES ("+id+","+sueldo+","+tipoEmpleado+","+registro_volumen_idEmpresa+")";
				stmt2=conexion.prepareStatement(query2);
				stmt2.executeQuery();
			}	
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (stmt != null) 
			{
				try{
					stmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (stmt2 != null) 
			{
				try{
					stmt2.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}
	
	/**
	 * Metodo encargado de registrar la llegada de un recurso.
	 * @param idRecurso El id del recurso deseado.
	 * @param idPedido El id del pedido al que pertenece el recurso.
	 * @param fechaLlegada La fecha de llegada del producto.
	 * @throws Exception Si hay un error en alguna parte del proceso.
	 */
	public void registrarLlegadaRecurso(int idRecurso, int idPedido, String fechaLlegada) throws Exception
	{
		PreparedStatement updStmt = null;
		PreparedStatement selStmt = null;
		PreparedStatement stmt = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryPedido = "UPDATE "+tPedidos+" p SET p."+PedidoValue.cFechaLlegada+"=TO_DATE('"+fechaLlegada+"','YYYY-MM-DD'), p."+PedidoValue.cEstado+"='Terminado' WHERE p."+PedidoValue.cIdPedido+"="+idPedido;
			String queryConsulta = "SELECT * FROM "+tTienen+" t WHERE t."+EmpresaValue.cIdEmpresa+"="+idEmpresaF+" AND t."+RecursoValue.cIdRecurso+"="+idRecurso;
			String queryTienen = null;
			updStmt = conexion.prepareStatement(queryPedido);
			updStmt.executeQuery();
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			if(rs.next()){
				queryTienen="UPDATE "+tTienen+" t SET t."+RecursoValue.cfCantidadEnBodega+"=t."+RecursoValue.cfCantidadEnBodega+"+(SELECT p.cantidad FROM "+tPedidos+" p WHERE p."+PedidoValue.cIdPedido+"="+idPedido+")"
						+ "WHERE t."+EmpresaValue.cIdEmpresa+"="+idEmpresaF+" AND t."+RecursoValue.cIdRecurso+"="+idRecurso;
				stmt=conexion.prepareStatement(queryTienen);
				stmt.executeQuery();
			}	
			else{
				queryTienen="INSERT INTO "+tTienen+"("+EmpresaValue.cIdEmpresa+","+RecursoValue.cIdRecurso+","+RecursoValue.cfCantidadEnBodega+") VALUES ("+idEmpresaF+","+idRecurso+", "
						+ "(SELECT p."+PedidoValue.cCantidad+" FROM "+tPedidos+" p WHERE p."+PedidoValue.cIdPedido+"="+idPedido+"))";
				stmt=conexion.prepareStatement(queryTienen);
				stmt.executeQuery();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (updStmt != null) 
			{
				try{
					updStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt != null) 
			{
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (stmt != null) 
			{
				try{
					stmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}
	
	/**
	 * Metodo encargado de registrar en la base de datos la entrega de un pedido.
	 * @param idCliente EL cliente al que se le realiza la entrega.
	 * @param idProducto El id del producto que esta en el pedido.
	 * @param idPedido El id del pedido a entregar.
	 * @param fechaLlegada La fecha de llegada del pedido.
	 * @throws Exception Si ocurre algún error durante el proceso.
	 */
	public void registrarEntregaPedido(int idProducto,int idPedido, String fechaLlegada) throws Exception
	{
		PreparedStatement updPedStmt = null;
		PreparedStatement updProdStmt = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryPedido = "UPDATE "+tPedidos+" p SET p."+PedidoValue.cFechaLlegada+"=TO_DATE('"+fechaLlegada+"','YYYY-MM-DD'), p."+PedidoValue.cEstado+"='Terminado' WHERE p."+PedidoValue.cIdPedido+"="+idPedido;
			updPedStmt = conexion.prepareStatement(queryPedido);
			updPedStmt.executeQuery();
			String queryProducto = "UPDATE "+tProductos+" p SET p."+RecursoValue.cfCantidadEnBodega+"=p."+RecursoValue.cfCantidadEnBodega+"-(SELECT p.cantidad FROM "+tPedidos+" p WHERE p."+PedidoValue.cIdPedido+"="+idPedido+"),"
					+ "p."+ProductoValue.cUnidadesVendidas+"=p."+ProductoValue.cUnidadesVendidas+"+(SELECT p."+PedidoValue.cCantidad+" FROM "+tPedidos+" p WHERE p."+PedidoValue.cIdPedido+"="+idPedido+") WHERE p."+
					ProductoValue.cIdProducto+"="+idProducto;
			updProdStmt = conexion.prepareStatement(queryProducto);
			updProdStmt.executeQuery();
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (updPedStmt != null) 
			{
				try{
					updPedStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (updProdStmt != null) 
			{
				try{
					updProdStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}

	//TODO PRECIO DE RECURSO.
	public void registrarPedidoRecurso(int idRecurso, int idPedido, int cantidad, String fechaEsperada) throws Exception{
		PreparedStatement selStmt = null;
		PreparedStatement insStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT * FROM "+tRecursos+" r WHERE r."+RecursoValue.cIdRecurso+"=="+idRecurso+"";
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			if(rs.next()){
				RecursoValue recurso = new RecursoValue();
				recurso.setIdRecurso(rs.getInt(RecursoValue.cIdRecurso));
				recurso.setNombre(rs.getString(RecursoValue.cNombre));
				recurso.setTipoRecurso(rs.getString(RecursoValue.cTipoRecurso));
				recurso.setUnidadMedida(rs.getString(RecursoValue.cUnidadMedida));
				recurso.setIdProveedor(rs.getInt(RecursoValue.cIdProveedor));
				recurso.setCantidadMDistribucion(rs.getInt(RecursoValue.cCantidadMDistribucion));
				recurso.setTiempoEntrega(rs.getInt(RecursoValue.cTiempoEntrega));
				if(recurso.getCantidadMDistribucion()<cantidad){
					throw new Exception("La cantidad exigida supera la cantidad máxima de distribución del recurso");
				}
				String queryInsert = "INSERT INTO "+tPedidos+"("+PedidoValue.cIdPedido+","+PedidoValue.cCantidad+","+
				PedidoValue.cMonto+","+PedidoValue.cFechaPedido+","+PedidoValue.cFechaEsperada+")"
				+ " VALUES ("+idPedido+","+cantidad+","+0+","+darFechaActualFormato()+","+fechaEsperada+")";
				insStmt = conexion.prepareStatement(queryInsert);
				insStmt.executeQuery();
				
			}
			else{
				throw new Exception("No existe ningún recurso con el id especificado");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally
		{
			if (selStmt != null) 
			{
				try
				{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (insStmt != null) 
			{
				try
				{
					insStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}
	
	/**
	 * Metodo encargado de registrar la solicitud de un pedido.
	 * @param idCliente El id del cliente que realiza el pedido.
	 * @param idProducto El id del producto.
	 * @param fechaEntrega La fecha de entrega.
	 * @param cantidad La cantidad de elementos compro.
	 * @throws Exception Si ocurre algún error en el proceso.
	 */
	public void solicitarPedido(int idCliente, int idProducto, String fechaEntrega, int cantidad) throws Exception 
	{
		PreparedStatement insStmt = null;
		PreparedStatement selStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT p."+ProductoValue.cCosto+" FROM "+tProductos+" p WHERE p."+ProductoValue.cIdProducto+"="+idProducto;
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			if(rs.next()){
				Float costo=rs.getFloat(ProductoValue.cCosto);
				float monto= (float) costo*cantidad;
				String queryConsulta2 = "SELECT * FROM "+tTienen+" t,(SELECT r."+RecursoValue.cIdRecurso+",r.cantidad FROM "+tRequieren+" r,etapasProduccion e,procesosProduccion pr WHERE "
						+ "pr."+ProductoValue.cIdProducto+"="+idProducto+" AND e."+ProcesoProduccionValue.cIdProcesoProduccion+"=pr."+ProcesoProduccionValue.cIdProcesoProduccion+" AND e."+EtapaProduccionValue.cIdEtapaProduccion+"=r."+EtapaProduccionValue.cIdEtapaProduccion+") n "
								+ "WHERE t."+RecursoValue.cIdRecurso+"=n."+RecursoValue.cIdRecurso+" AND n.cantidad*"+cantidad+"<=t."+RecursoValue.cfCantidadEnBodega+"";
				selStmt = conexion.prepareStatement(queryConsulta2);
				ResultSet rs2 = selStmt.executeQuery();
				if(rs2.next())
				{
					establecerConexion(cadenaConexion, usuario, clave);
					String queryInsert ="INSERT INTO "+tPedidos+" (cantidad,"+PedidoValue.cMonto+","+PedidoValue.cFechaPedido+",fechaEsperada) VALUES ("+cantidad+","+monto+",TO_DATE('"+darFechaActualFormato()+"','YYYY-MM-DD'), TO_DATE('"+fechaEntrega+"','YYYY-MM-DD')))";
					insStmt = conexion.prepareStatement(queryInsert);
					insStmt.executeQuery();
				}
				else
				{
					throw new Exception("No se pudo realizar el pedido del producto, porque no se cuenta con los recursos necesarios. EL pedido se archivara para luego de tener los recursos informar sobre la posibilidad de realizar la solicitud");
				}
			}
			else{
				throw new Exception("No existe el producto");
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally
		{
			if (insStmt != null) 
			{
				try
				{
					insStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}

	@SuppressWarnings("deprecation")
	private String darFechaActualFormato(){
		Date hoy = new Date();
		return (hoy.getYear()+1900)+"-"+(hoy.getMonth()+1)+"-"+hoy.getDate();
	}
	
	/**
	 * Metodo encargado de registrar la ejecucion en la base de datos.
	 * @param idEtapaProduccion la etapa de produccion.
	 * @param idOperario El ide del operario
	 * @param fechaEjecucion La fecha de ejecucion
	 * @param duracion es la duracion de la etapa
	 * @throws Exception Si hay un error en alguna parte del proceso
	 */
	public void registrarEjecucionEtapaProduccion(int idEtapaProduccion, int idOperario, String fechaEjecucion, int duracion) throws Exception{
		PreparedStatement insStmt=null;
		PreparedStatement selStmt=null;
		PreparedStatement selEStmt=null;
		PreparedStatement updStmt=null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryInsert="INSERT INTO "+tOperan+"(idOperario,"+EtapaProduccionValue.cIdEtapaProduccion+",fechaEjecucion,duracion) VALUES("+idEtapaProduccion+","+idOperario+",TO_DATE('"+fechaEjecucion+"','YYYY-MM-DD'),"+duracion+")";
			String querySelectE="SELECT * FROM "+tEtapasProduccionPedido+" e WHERE e."+EtapaProduccionValue.cIdEtapaProduccion+"="+idEtapaProduccion+" AND e.idAnteriorEtapa IS NULL";
			String querySelect="SELECT * FROM "+tEtapasProduccionPedido+" e WHERE e."+EtapaProduccionValue.cIdEtapaProduccion+"=(SELECT idAnteriorEtapa FROM "+tEtapasProduccionPedido+" et WHERE et."+EtapaProduccionValue.cIdEtapaProduccion+"="+idEtapaProduccion+") AND e."+PedidoValue.cEstado+"='Terminado'";
			String queryUpdate="UPDATE "+tTienen+" t SET t.cantidad=t.cantidad-(SELECT req.cantidad FROM "+tRequieren+" req WHERE req."+EtapaProduccionValue.cIdEtapaProduccion+"="+idEtapaProduccion+")";
			selEStmt = conexion.prepareStatement(querySelectE);
			ResultSet rs = selEStmt.executeQuery();
			if(rs.next()){
				insStmt=conexion.prepareStatement(queryInsert);
				insStmt.executeQuery();
				updStmt=conexion.prepareStatement(queryUpdate);
				updStmt.executeQuery();
			}
			else{
				selStmt=conexion.prepareStatement(querySelect);
				ResultSet rs2=selStmt.executeQuery();
				if(rs2.next()){
					throw new Exception("Aun no se han terminado las etapas anteriores a esta");
				}
				else
				{
					insStmt=conexion.prepareStatement(queryInsert);
					insStmt.executeQuery();
					updStmt=conexion.prepareStatement(queryUpdate);
					updStmt.executeQuery();
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally
		{
			if (insStmt != null) 
			{
				try
				{
					insStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt != null) 
			{
				try
				{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt != null) 
			{
				try
				{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (updStmt != null) 
			{
				try
				{
					updStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}
}