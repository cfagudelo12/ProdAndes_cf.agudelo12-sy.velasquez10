package test.tablas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import junit.framework.TestCase;

public class TestTablas extends TestCase
{
	private Connection conexion;
	
	/**
	 * Nombre del usuario para conectarse a la base de datos.
	 */
	private final static String usuario="ISIS2304481510";
	
	/**
	 * Clave de conexion a la base de datos.
	 */
	private final static String clave="fberrapius";
	
	/**
	 * URL al cual se debe conectar para acceder a la base de datos.
	 */
	private final static String cadenaConexion= "jdbc:oracle:thin:@prod.oracle.virtual.uniandes.edu.co:1531:prod";
	
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
	
	private void setupEscenario1(){}
	
	public void testUsuarios(){
//		PreparedStatement s=null;
//		try{
//			s = conexion.prepareStatement(consulta);
//			establecerConexion(cadenaConexion, usuario, clave);
//		}
		
	}
}
