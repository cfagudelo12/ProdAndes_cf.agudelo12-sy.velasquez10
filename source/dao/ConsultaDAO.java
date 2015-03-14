package dao;


import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import vos.Recurso;

/**
* Clase ConsultaDAO, encargada de hacer las consultas a la base de datos
*/
public class ConsultaDAO {

	//----------------------------------------------------
	//Constantes
	//----------------------------------------------------
	
	/**
	 * Ruta donde se encuentra el archivo de conexión.
	 */
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";
	
	private static final String empresas="Empresas";
	
	private static final String recursos="Recursos";
	
	//----------------------------------------------------
	//Atributos
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
	
	// -------------------------------------------------
   // Metodos
   // -------------------------------------------------

	/**
	 * Obtiene los datos necesarios para establecer una conexion
	 * Los datos se obtienen a partir de un archivo properties.
	 * @param path Ruta donde se encuentra el archivo properties.
	 */
	public void inicializar(String path)
	{
		try
		{
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
	public void closeConnection(Connection connection) throws Exception {        
		try {
			connection.close();
			connection = null;
		} catch (SQLException exception) {
			throw new Exception("ERROR: ConsultaDAO: closeConnection() = cerrando una conexion.");
		}
	} 
   
   // ---------------------------------------------------
   // Metodos asociados a los casos de uso: Consulta
   // ---------------------------------------------------
   
   public ArrayList<Recurso> consultarRecursos() throws Exception{
		
	}
	
	public ArrayList<Recurso> consultarRecursosMateriaPrima() throws Exception{
		
	}
	
	public ArrayList<Recurso> consultarRecursosComponente() throws Exception{
		
	}
	
	public ArrayList<Recurso> consultarRecursosEtapasProduccion(int[] idEtapaProduccion) throws Exception{
		
	}
	
	public ArrayList<Recurso> consultarRecursosFechaSolicitud(Date fechaSolicitud) throws Exception{
		
	}
	
	public ArrayList<Recurso> consultarRecursosFechaEntrega(Date fechaEntrega) throws Exception{
		
	}
	
	public ArrayList<Recurso> consultarRecursosRangoExistencias(int limiteInferior, int limiteSuperior) throws Exception{
		
	}
//   
//   /**
//    * Método que se encarga de realizar la consulta en la base de datos
//    * y retorna un ArrayList de elementos tipo VideosValue.
//    * @return ArrayList lista que contiene elementos tipo VideosValue.
//    * La lista contiene los videos ordenados alfabeticamente
//    * @throws Exception se lanza una excepción si ocurre un error en
//    * la conexión o en la consulta. 
//    */
//   public ArrayList<VideosValue> darVideosDefault() throws Exception
//   {
//   	PreparedStatement prepStmt = null;
//   	
//   	ArrayList<VideosValue> videos = new ArrayList<VideosValue>();
//		VideosValue vidValue = new VideosValue();
//   	
//		try {
//			establecerConexion(cadenaConexion, usuario, clave);
//			prepStmt = conexion.prepareStatement(consultaVideosDefault);
//			
//			ResultSet rs = prepStmt.executeQuery();
//			
//			while(rs.next()){
//				String titVid = rs.getString(tituloVideo);
//				int anyoVid = rs.getInt(anyoVideo);
//				
//				vidValue.setTituloOriginal(titVid);
//				vidValue.setAnyo(anyoVid);	
//			
//				videos.add(vidValue);
//				vidValue = new VideosValue();
//							
//			}
//		
//		} catch (SQLException e) {
//			e.printStackTrace();
//			System.out.println(consultaVideosDefault);
//			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement!!!");
//		}finally 
//		{
//			if (prepStmt != null) 
//			{
//				try {
//					prepStmt.close();
//				} catch (SQLException exception) {
//					
//					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexión.");
//				}
//			}
//			closeConnection(conexion);
//		}		
//		return videos;
//   }
   
}
