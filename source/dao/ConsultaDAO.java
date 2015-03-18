package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
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
			solicitarPedido(1, 1, "2016-04-01", 3);
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
		try
		{
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
        try 
        {
			ConsultaDAO dao= new ConsultaDAO();
		} 
        catch (Exception e) {
		
			e.printStackTrace();
		}
    }

	/**
	 * Cierra la conexion activa a la base de datos.
	 * @param con Conexion a la base de datos
	 * @throws Exception Si se presentan errores de conexion
	 */
	public void closeConnection(Connection connection) throws Exception 
	{        
		try
		{
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

	/**
	 * Metodo encargado de consultar en la base de datos la existencia de un recurso, segun el tipo de material, un rango de cantidad, una fecha de solicitud y fecha de entrega
	 * @param tipoMaterial El tipo de material. Materia prima o Componente
	 * @param rInferior El limite inferior del rango
	 * @param rSuperior El limite superior del rango
	 * @param idEtapaProduccion El id de la etapa de produccion
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return Una lista con los recursos que cumplen con los parametros.
	 * @throws Exception Si hay algun problema en la conexion o si no y si no hay existencias disponibles.
	 */
	public ArrayList<RecursoValue> consultarExistenciasRecurso(String tipoMaterial, int rInferior, int rSuperior, int idEtapaProduccion, String fechaSolicitud, String fechaEntrega) throws Exception
	{
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
	 * Metodo encargado en generar el query de la consulta de existencias
	 * @param tipoMaterial El tipo de material. Materia prima o Componente
	 * @param rInferior El limite inferior del rango
	 * @param rSuperior El limite superior del rango
	 * @param idEtapaProduccion El id de la etapa de produccion
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return El query respectivo a la solicitud.
	 */
	private String generarConsultaExistenciaRecurso(String tipoMaterial, int rInferior, int rSuperior, int idEtapaProduccion, String fechaPedido, String fechaLlegada)
	{
		String consulta = "SELECT * FROM Recursos NATURAL INNER JOIN (SELECT idRecurso FROM Tienen WHERE idEmpresa="+idEmpresaF+") WHERE tipoRecurso='"+tipoMaterial+"'";
		if(rInferior!=0||rSuperior!=0||idEtapaProduccion!=0||fechaPedido!=null||fechaLlegada!=null){
			if(rInferior>0 && rInferior<rSuperior){
				consulta+=" AND idRecurso IN (SELECT idRecurso FROM Recursos NATURAL INNER JOIN (SELECT idRecurso FROM Tienen WHERE idEmpresa="+idEmpresaF+
						" AND cantidadEnBodega BETWEEN "+rInferior+" AND "+rSuperior+"))";
			}
			if(idEtapaProduccion>0){
				consulta+=" AND idRecurso IN (SELECT idRecurso FROM RECURSOS NATURAL INNER JOIN (SELECT idRecurso FROM Requieren WHERE idEtapaProduccion="+idEtapaProduccion+"))";
			}
			if(fechaPedido!=null){
				consulta+=" AND idRecurso IN(SELECT idRecurso FROM Solicitan NATURAL INNER JOIN Pedidos WHERE fechaPedido=TO_DATE('"+fechaPedido+"', 'YYYY-MM-DD'))";
			}
			if(fechaLlegada!=null){
				consulta+=" AND idRecurso IN(SELECT idRecurso FROM Solicitan NATURAL INNER JOIN Pedidos WHERE fechaPedido=TO_DATE('"+fechaLlegada+"', 'YYYY-MM-DD'))";
			}
		}
		return consulta;
	}
	
	/**
	 * 
	 * Metodo encargado de consultar en la base de datos la existencia de un prodcuto, un rango de cantidad, una fecha de solicitud y fecha de entrega
	 * @param tipoMaterial El tipo de material. Materia prima o Componente
	 * @param rInferior El limite inferior del rango
	 * @param rSuperior El limite superior del rango
	 * @param idEtapaProduccion El id de la etapa de produccion
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return Una lista con los recursos que cumplen con los parametros.
	 * @throws Exception Si hay algun problema en la conexion o si no y si no hay existencias disponibles.
	 */
	public ArrayList<ProductoValue> consultarExistenciasProductos(int rInferior, int rSuperior, int idProcesoProduccion, String fechaSolicitud, String fechaEntrega) throws Exception
	{
		ArrayList<ProductoValue> productos = new ArrayList<ProductoValue>();
		PreparedStatement selStmt = null;
		try
		{
			String consulta	= generarConsultaExistenciasProductos(rInferior, rSuperior, idProcesoProduccion, fechaSolicitud, fechaEntrega);
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();
			while(rs.next())
			{
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
	 * Metodo encargado en generar el query de la consulta de existencias
	 * @param tipoMaterial El tipo de material. Materia prima o Componente
	 * @param rInferior El limite inferior del rango
	 * @param rSuperior El limite superior del rango
	 * @param idEtapaProduccion El id de la etapa de produccion
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return El query respectivo a la solicitud.
	 */
	private String generarConsultaExistenciasProductos(int rInferior, int rSuperior, int idProcesoProduccion, String fechaPedido, String fechaLlegada){
		String consulta = "SELECT * FROM Productos WHERE idEmpresa="+idEmpresaF+" AND cantidadEnBodega>0";
		if(rInferior>0 && rInferior<rSuperior){
			consulta+=" AND cantidadEnBodega BETWEEN "+rInferior+" AND "+rSuperior;
		}
		if(idProcesoProduccion>0){
			consulta+=" AND idProcesoProduccion="+idProcesoProduccion;
		}
		if(fechaPedido!=null){
			consulta+=" AND idProducto IN(SELECT idProducto FROM Compran NATURAL INNER JOIN Pedidos WHERE fechaPedido=TO_DATE('"+fechaPedido+"', 'YYYY-MM-DD'))";
		}
		if(fechaLlegada!=null){
			consulta+=" AND idProducto IN(SELECT idProducto FROM Compran NATURAL INNER JOIN Pedidos WHERE fechaLlegada=TO_DATE('"+fechaLlegada+"', 'YYYY-MM-DD'))";
		}
		return consulta;
	}
	
	/**
	 * Metodo encardo de buscar un objeto segun los parametros
	 * @param volumen EL volumen que se quiere buscar
	 * @param desde Desde cuando se quiere buscar
	 * @param hasta Hasta cuando se quiere buscar
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
			String queryConsulta = "SELECT * FROM Empresas em,Recursos r,Solicitan s,EtapasProduccion e,Requieren req,ProcesosProduccion pro,Pedidos p, Tienen t"+ 
					"WHERE em.idEmpresa="+idEmpresaF+" AND r.idRecurso=s.idRecurso AND s.idPedido=p.idPedido AND r.idRecurso=req.idRecurso AND req.idEtapaProduccion=e.idEtapaProduccion"+
					"AND pro.idProcesoProduccion=e.idProcesoProduccion AND t.idEmpresa=em.idEmpresa AND t.idRecurso=r.idRecurso";
			if(volumen>0)
			{
				queryConsulta+=" AND t.cantidadEnBodega="+volumen;
			}
			if(costo>0)
			{
				queryConsulta+=" AND p.monto="+costo;
			}
			if(desde!=null && hasta!=null)
			{
				queryConsulta+="AND (p.fechaLlegada>TO_DATE('"+desde+"','YYYY-MM-DD') OR p.fechaLlegada<TO_DATE('"+hasta+"','YYYY-MM-DD'))";
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
	 * @param volumen EL volumen que se quiere buscar
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
			String queryConsulta = "SELECT * FROM "+tProductos+" p, "+tRecursos+" r, "+tEtapasProduccion+" e,"+tRequieren+" req,"+tProcesosProduccion+" pro,"+tPedidos+" pe, "+tCompran+" c WHERE p.idProducto=pro.idProducto AND pro.idProcesoProduccion=e.idProcesoProduccion AND req.idEtapaProduccion=e.idEtapaProduccion AND req.idRecurso=r.idRecurso AND c.idProducto=p.idProducto AND pe.idPedido=c.idPedido";
			
			if(cantidad>0)
			{
				queryConsulta+=" AND p.cantidadenbodega="+cantidad;
			}
			if(costo>0)
			{
				queryConsulta+=" AND p.costo="+costo;
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


	//---------------------------------------------------
	// Metodos asociados a los casos de uso: Modificacion
	//---------------------------------------------------
	
	/**
	 * Metodo encargado de registrar la llegada de un recurso a par tir de lo que hemos entregado.
	 * @param idRecurso El id del recurso deseado
	 * @param idPedido El id del pedido al que pertenece el recurso
	 * @param fechaLlegada La fecha de llegada del producto.
	 * @throws Exception Si hay un error en alguna parte del proceso
	 */
	public void registrarLlegadaRecurso(int idRecurso, int idPedido, String fechaLlegada) throws Exception
	{
		PreparedStatement updStmt = null;
		PreparedStatement selStmt = null;
		PreparedStatement stmt = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryPedido = "UPDATE Pedidos p SET p.fechaLlegada=TO_DATE('"+fechaLlegada+"','YYYY-MM-DD'), p.estado='Terminado' WHERE p.idPedido="+idPedido;
			String queryConsulta = "SELECT * FROM Tienen t WHERE t.idEmpresa="+idEmpresaF+" AND t.idRecurso="+idRecurso;
			String queryTienen = null;
			updStmt = conexion.prepareStatement(queryPedido);
			updStmt.executeQuery();
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			if(rs.next())
			{
				queryTienen="UPDATE Tienen t SET t.cantidadEnBodega=t.cantidadEnBodega+(SELECT p.cantidad FROM Pedidos p WHERE p.idPedido="+idPedido+")"
						+ "WHERE t.idEmpresa="+idEmpresaF+" AND t.idRecurso="+idRecurso;
				stmt=conexion.prepareStatement(queryTienen);
				stmt.executeQuery();
			}	
			else
			{
				queryTienen="INSERT INTO Tienen(idEmpresa,idRecurso,cantidadEnBodega) VALUES ("+idEmpresaF+","+idRecurso+", "
						+ "(SELECT p.cantidad FROM Pedidos p WHERE p.idPedido="+idPedido+"))";
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
	 * Metodo encargado de registrar en la base de datos la entrega de un pedido
	 * @param idCliente EL cliente que realiza la entrega
	 * @param idProducto El id del producto que esta en el pedido
	 * @param idPedido El id del pedido 
	 * @param fechaLlegada La fechas de 
	 * @throws Exception
	 */
	public void registrarEntregaPedido(int idProducto,int idPedido, String fechaLlegada) throws Exception
	{
		PreparedStatement updPedStmt = null;
		PreparedStatement updProdStmt = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryPedido = "UPDATE Pedidos p SET p.fechaLlegada=TO_DATE('"+fechaLlegada+"','YYYY-MM-DD'), p.estado='Terminado' WHERE p.idPedido="+idPedido;
			updPedStmt = conexion.prepareStatement(queryPedido);
			updPedStmt.executeQuery();
			String queryProducto = "UPDATE Productos p SET p.cantidadEnBodega=p.cantidadEnBodega-(SELECT p.cantidad FROM Pedidos p WHERE p.idPedido="+idPedido+"),"
					+ "p.unidadesVendidas=p.unidadesVendidas+(SELECT p.cantidad FROM Pedidos p WHERE p.idPedido="+idPedido+") WHERE p.idProducto="+idProducto;
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

	/**
	 * Metodo encargado de solicitar un pedido
	 * @param idCliente El id del cliente que realizo el pedido.
	 * @param idProducto El id del producto.
	 * @param fechaEntrega La fecha de entrega.
	 * @param cantidad La cantidad de elementos compro.
	 * @throws Exception
	 */

	public void solicitarPedido(int idCliente, int idProducto, String fechaEntrega, int cantidad) throws Exception 
	{
		PreparedStatement insStmt = null;
		PreparedStatement selStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT p.costo FROM "+tProductos+" p WHERE p.idProducto="+idProducto;
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			if(rs.next()){
				Float costo=rs.getFloat(ProductoValue.cCosto);
				float monto= (float) costo*cantidad;
				String queryConsulta2 = "SELECT * FROM "+tTienen+" t,(SELECT r.idRecurso,r.cantidad FROM "+tRequieren+" r,etapasProduccion e,procesosProduccion pr WHERE "
						+ "pr.idProducto="+idProducto+" AND e.idProcesoProduccion=pr.idProcesoProduccion AND e.idEtapaProduccion=r.idEtapaProduccion) n "
								+ "WHERE t.idRecurso=n.idRecurso AND n.cantidad*"+cantidad+"<=t.cantidadEnBodega";
				selStmt = conexion.prepareStatement(queryConsulta2);
				ResultSet rs2 = selStmt.executeQuery();

				if(rs2.next())
				{
					establecerConexion(cadenaConexion, usuario, clave);
					String queryInsert ="INSERT INTO "+tPedidos+" (cantidad,monto,fechaPedido,fechaEsperada) VALUES ("+cantidad+","+monto+",TO_DATE('"+darFechaActualFormato()+"','YYYY-MM-DD'), TO_DATE('"+fechaEntrega+"','YYYY-MM-DD')))";
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
			String queryInsert="INSERT INTO Operan(idOperario,idEtapaProduccion,fechaEjecucion, duracion) VALUES("+idEtapaProduccion+","+idOperario+","+fechaEjecucion+","+duracion+")";
			String querySelectE="SELECT * FROM EtapasProduccion e WHERE e.idEtapaProduccion="+idEtapaProduccion+" AND e.idAnteriorEtapa IS NULL";
			String querySelect="SELECT * FROM EtapasProduccion e WHERE e.idEtapaProduccion=(SELECT idAnteriorEtapa FROM EtapasProduccion et WHERE et.idEtapaProduccion="+idEtapaProduccion+") AND e.estado='Terminado'";
			String queryUpdate="UPDATE Tienen t SET t.cantidad=t.cantidad-(SELECT req.cantidad FROM Requieren req WHERE req.idEtapaProduccion="+idEtapaProduccion+")";
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
