package dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import vos.*;

/**
* Clase ConsultaDAO, encargada de hacer las consultas a la base de datos
*/
public class ConsultaDAO {

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
	
	private static final String tEmpresas="Empresas";
	
	private static final String tRecursos="Recursos";
	
	private static final String tUsuarios="Usuarios";
	
	private static final String tProveedores="Proveedores";
	
	private static final String tClientes="Clientes";

	private static final String tEmpleados="Empleados";

	private static final String tPedidos="Pedidos";

	private static final String tProductos="Productos";

	private static final String tTrabajan="Trabajan";

	private static final String tSolicitan="Solicitan";

	private static final String tCompran="Compran";

	private static final String tProcesosProduccion="ProcesosProduccion";

	private static final String tEtapasProduccion="EtapasProduccion";

	private static final String tEstacionesProduccion="EstacionesProduccion";

	private static final String tOperan="Operan";
	
	private static final String tTienen="Tienen";

	private static final String tClientela="Clientela";

	private static final String tProveen="Proveen";
	
	private static final String tRequieren="Requieren";
	
	//----------------------------------------------------
	// Constantes de tipo de consultas sobre recursos
	//----------------------------------------------------
	
	public final static int tcrDefault = 0;
	
	public final static int tcrTipoMateriaPrima = 1;
	
	public final static int tcrTipoComponente = 2;
	
	public final static int tcrVolumen = 3;
	
	public final static int tcrEtapaProduccion = 4;
	
	public final static int tcrFechaSolicitud = 5;
	
	public final static int tcrFechaEntrega = 6;
	
	//----------------------------------------------------
	// Constantes de tipo de consultas sobre recursos en inventario
	//----------------------------------------------------
	
	public final static int tcriDefault = 0;
	
	public final static int tcriTipoMateriaPrima = 1;
	
	public final static int tcriTipoComponente = 2;
	
	public final static int tcriRangoExistencias = 3;
	
	public final static int tcriEtapaProduccion = 4;
	
	public final static int tcriFechaSolicitud = 5;
	
	public final static int tcriFechaEntrega = 6;
	
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
	private String usuario;
	
	/**
	 * Clave de conexion a la base de datos.
	 */
	private String clave;
	
	/**
	 * URL al cual se debe conectar para acceder a la base de datos.
	 */
	private String cadenaConexion;
	
	/**
	 * Constructor de la clase. No inicializa ningun atributo.
	 */
	public ConsultaDAO(){}
	
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
		try{
			File arch= new File(path+ARCHIVO_CONEXION);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
	        prop.load( in );
	        in.close( );
			cadenaConexion = prop.getProperty("url");									
			usuario = prop.getProperty("usuario");	
			clave = prop.getProperty("clave");
			final String driver = prop.getProperty("driver");
			Class.forName(driver);
		}
		catch(Exception e){
			e.printStackTrace();
		}	
	}

	/**
	 * Metodo que se encarga de crear la conexion con el Driver Manager a partir de los parametros recibidos.
	 * @param url Direccion url de la base de datos a la cual se desea conectar
	 * @param usuario Nombre del usuario que se va a conectar a la base de datos
	 * @param clave Clave de acceso a la base de datos
	 * @throws SQLException si ocurre un error generando la conexion con la base de datos.
	 */
	private void establecerConexion(String url, String usuario, String clave) throws SQLException
	{
		try{
			conexion = DriverManager.getConnection(url,usuario,clave);
		}
		catch(SQLException exception){
			throw new SQLException("ERROR: ConsultaDAO obteniendo una conexion.");
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

	public ArrayList<RecursoValue> consultarExistenciasRecurso(String tipoMaterial, int rInferior, int rSuperior, int idEtapaProduccion, Date fechaSolicitud, Date fechaEntrega) throws Exception
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
				recurso.setCantidadInicial(rs.getInt(RecursoValue.cCantidadInicial));
				recurso.setTipoRecurso(rs.getString(RecursoValue.cTipoRecurso));
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
	
	private String generarConsultaExistenciaRecurso(String tipoMaterial, int rInferior, int rSuperior, int idEtapaProduccion, Date fechaSolicitud, Date fechaEntrega){
		String consulta = "SELECT * FROM Recusos r NATURAL INNER JOIN (SELECT t.idRecurso FROM Tienen t WHERE t.idEmpresa="+idEmpresaF+")";
		if(tipoMaterial!=null||rInferior!=0||rSuperior!=0||idEtapaProduccion!=0||fechaSolicitud!=null||fechaEntrega!=null){
			consulta+=" WHERE"; 
			if(tipoMaterial!=null){
				consulta+="AND r.tipoMaterial='"+tipoMaterial+"'";
			}
			if(rInferior>0 && rInferior<rSuperior){
				consulta+=" AND IN (SELECT * FROM RECURSOS NATURAL INNER JOIN (SELECT t.idRecurso FROM Tienen t WHERE t.idEmpresa="+idEmpresaF+
						" AND t.idRecurso=r.idRecurso AND t.cantidad BETWEEN "+rInferior+" AND "+rSuperior+"))";
			}
			if(idEtapaProduccion>0){
				consulta+=" AND IN (SELECT * FROM RECURSOS NATURAL INNER JOIN (SELECT req.idRecurso FROM Requieren req WHERE req.idRecurso=r.idRecurso "
						+ "AND req.idEtapaProduccion="+idEtapaProduccion+")";
			}
			if(fechaSolicitud!=null){
				consulta+=" AND IN (SELECT * FROM RECURSOS NATURAL INNER JOIN (SELECT s.idRecurso FROM Solicitan s NATURAL INNER JOIN Pedidos p WHERE"
						+ " p.fechaSolicitud="+fechaSolicitud+"))";
			}
			if(fechaEntrega!=null){
				consulta+=" AND IN (SELECT * FROM RECURSOS NATURAL INNER JOIN (SELECT s.idRecurso FROM Solicitan s NATURAL INNER JOIN Pedidos p WHERE"
						+ " p.fechaEntrega="+fechaEntrega+"))";
			}
			consulta.replace("WHERE AND", "WHERE ");
		}
		return consulta;
	}
	
	public ArrayList<MaterialValue> consultarRecurso(int cantidad, Date desde, Date hasta, Float costo) throws Exception
	{
		ArrayList<MaterialValue> materiales= new ArrayList<MaterialValue>();
		PreparedStatement selStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT * FROM"+tRecursos+" r, "+tSolicitan+" s,"+tEtapasProduccion+" e,"+tRequieren+" req,"+tProcesosProduccion+" pro,"+ tPedidos+" p, WHERE r.idRecurso=s.idRecurso AND s.idPedido=p.idPedido AND r.idRecurso=req.idRecurso AND req.idEtapaProduccion=e.idEtapaProduccion AND pro.idEtapaProduccion=e.idEtapaProduccion";
			
			if(cantidad>0)
			{
				queryConsulta+=" AND r.volumen="+cantidad;
			}
			if(costo>0)
			{
				queryConsulta+=" AND r.costo="+costo;
			}
			if(desde!=null && hasta!=null && desde.before(hasta))
			{
				queryConsulta+="AND (p.fechaLlegada>"+desde+" OR p.fechaLlegada<"+hasta+")";
			}
			
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			
			while(rs.next())
			{
				MaterialValue m= new MaterialValue();
				
				RecursoValue recurso = new RecursoValue();
				recurso.setIdRecurso(rs.getInt(RecursoValue.cIdRecurso));
				recurso.setNombre(rs.getString(RecursoValue.cNombre));
				recurso.setCantidadInicial(rs.getInt(RecursoValue.cCantidadInicial));
				recurso.setTipoRecurso(rs.getString(RecursoValue.cTipoRecurso));
				
				EtapaProduccionValue etapa = new EtapaProduccionValue();
				etapa.setNumeroEtapa(rs.getInt(EtapaProduccionValue.cNumeroEtapa));
				
				PedidoValue pedido = new PedidoValue();
				pedido.setIdPedido(rs.getInt(PedidoValue.cIdPedido));
				
				ProcesoProduccionValue proceso = new ProcesoProduccionValue();
				proceso.setIdProducto(ProcesoProduccionValue.cIdProducto);
				
				m.setRecurso(recurso);
				m.agregarEtapasProduccion(""+etapa.getNumeroEtapa());
				m.agregarPedidos(""+pedido.getIdPedido());
				m.agregarProductos(""+proceso.getIdProducto());
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
	
	public void registrarLlegadaRecurso(int idRecurso, int idPedido, Date fechaLlegada) throws Exception
	{
		PreparedStatement updStmt = null;
		PreparedStatement selStmt = null;
		PreparedStatement stmt = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryPedido = "UPDATE Pedidos p SET p.fechaLlegada="+fechaLlegada+", p.estado='Entregado' WHERE p.idPedido="+idPedido;
			String queryConsulta = "SELECT * FROM Tienen t WHERE t.idEmpresa="+idEmpresaF+" AND t.idRecurso="+idRecurso;
			String queryTienen = null;
			updStmt = conexion.prepareStatement(queryPedido);
			updStmt.executeQuery();
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			if(rs.next())
			{
				queryTienen="UPDATE Tienen t SET t.cantidad=t.cantidad+(SELECT p.cantidad FROM Pedidos p WHERE p.idPedido="+idPedido+")"
						+ "WHERE t.idEmpresa="+idEmpresaF+" AND t.idRecurso="+idRecurso;
				stmt=conexion.prepareStatement(queryTienen);
				stmt.executeQuery();
			}	
			else
			{
				queryTienen="INSERT INTO Tienen(idEmpresa,idRecurso,cantidad) VALUES ("+idEmpresaF+","+idRecurso+", "
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
	
	public void registrarEntregaPedido(int idCliente,int idProducto,int idPedido, Date fechaLlegada) throws Exception{
		PreparedStatement updPedStmt = null;
		PreparedStatement updProdStmt = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryPedido = "UPDATE Pedidos p SET p.fechaLlegada="+fechaLlegada+", p.estado='Entregado' WHERE p.idPedido="+idPedido;
			updPedStmt = conexion.prepareStatement(queryPedido);
			updPedStmt.executeQuery();
			String queryProducto = "UPDATE Productos p SET p.cantidadEnBodega=p.cantidadEnBodega-(SELECT p.cantidad FROM Pedidos p WHERE p.idPedido="+idPedido+")"
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
}
