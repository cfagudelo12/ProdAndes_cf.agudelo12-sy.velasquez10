package dao;


import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import vos.RecursoValue;

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
	
	private static final int idEmpresaF = 0;
	
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

	public ArrayList<RecursoValue> consultarRecursosInventario(int tipoConsulta) throws Exception
	{
		String consulta=null;
		switch(tipoConsulta)
		{
			case tcriDefault:
				consulta="";
				break;
			case tcriTipoMateriaPrima:
				consulta="";
				break;
			case tcriTipoComponente:
				consulta="";
				break;
			case tcriRangoExistencias:
				consulta="";
				break;
			case tcriEtapaProduccion:
				consulta="";
				break;
			case tcriFechaSolicitud:
				consulta="";
				break;
			case tcriFechaEntrega:
				consulta="";
				break;
		}
		PreparedStatement prepStmt = null;
		ArrayList<RecursoValue> recursos = new ArrayList<RecursoValue>();
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			prepStmt = conexion.prepareStatement(consulta);
			ResultSet rs = prepStmt.executeQuery();
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
			if (prepStmt != null) 
			{
				try{
					prepStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return recursos;
	}
	
	public ArrayList<RecursoValue> consultarRecursos(int tipoConsulta) throws Exception{
		String consulta=null;
		switch(tipoConsulta){
			case tcrDefault:
				consulta="SELECT * FROM "+tRecursos;
				break;
			case tcrTipoMateriaPrima:
				consulta="SELECT * FROM "+tRecursos+" WHERE "+RecursoValue.cTipoRecurso+"=\'"+RecursoValue.materiaPrima+"\'";
				break;
			case tcrTipoComponente:
				consulta="SELECT * FROM "+tRecursos+" WHERE "+RecursoValue.cTipoRecurso+"=\'"+RecursoValue.componente+"\'";
				break;
			case tcrVolumen:
				consulta="SELECT * FROM "+tRecursos+" WHERE "+RecursoValue.cTipoRecurso+"=\""+RecursoValue.componente+"\"";
				break;
			case tcrEtapaProduccion:
				consulta="SELECT * FROM "+tRecursos+" WHERE "+RecursoValue.cTipoRecurso+"=\""+RecursoValue.componente+"\"";
				break;
			case tcrFechaSolicitud:
				consulta="SELECT * FROM "+tRecursos+" WHERE "+RecursoValue.cTipoRecurso+"=\""+RecursoValue.componente+"\"";
				break;
			case tcrFechaEntrega:
				consulta="SELECT * FROM "+tRecursos+" WHERE "+RecursoValue.cTipoRecurso+"=\""+RecursoValue.componente+"\"";
				break;
		}
		PreparedStatement prepStmt = null;
		ArrayList<RecursoValue> recursos = new ArrayList<RecursoValue>();
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			prepStmt = conexion.prepareStatement(consulta);
			ResultSet rs = prepStmt.executeQuery();
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
			if (prepStmt != null) 
			{
				try{
					prepStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return recursos;
	}

	//---------------------------------------------------
	// Metodos asociados a los casos de uso: Modificacion
	//---------------------------------------------------
}
