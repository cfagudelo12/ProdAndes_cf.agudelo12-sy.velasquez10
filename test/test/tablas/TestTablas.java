package test.tablas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import junit.framework.TestCase;

public class TestTablas extends TestCase
{
	private Connection conexion;

	private final static String usuario="ISIS2304481510";
	
	private final static String clave="fberrapius";
	
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
	
	private void setupEscenario1(){
		try {
			establecerConexion(usuario, clave, cadenaConexion);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testUsuarios(){
//		PreparedStatement s=null;
//		try{
//			s = conexion.prepareStatement(consulta);
//			establecerConexion(cadenaConexion, usuario, clave);
//		}
		
	}
}
