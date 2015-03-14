package fachada;

import java.util.ArrayList;

import dao.ConsultaDAO;
import vos.Empresa;

/**
 * Clase ProdAndes, que representa la fachada de comunicación entre
 * la interfaz y la conexión con la base de datos. Atiende todas
 * las solicitudes.
 */
public class ProdAndes 
{
	/**
	 * Conexión con la clase que maneja la base de datos
	 */
	private ConsultaDAO dao;
	

    
    // -----------------------------------------------------------------
    // Singleton
    // -----------------------------------------------------------------


    /**
     * Instancia única de la clase
     */
    private static ProdAndes instancia;
    
    /**
     * Devuelve la instancia única de la clase
     * @return Instancia única de la clase
     */
    public static ProdAndes darInstancia( )
    {
        if( instancia == null )
        {
            instancia = new ProdAndes( );
        }
        return instancia;
    }
	
	/**
	 * contructor de la clase. Inicializa el atributo dao.
	 */
	private ProdAndes()
	{
		dao = new ConsultaDAO();
	}
	
	/**
	 * inicializa el dao, dándole la ruta en donde debe encontrar
	 * el archivo properties.
	 * @param ruta ruta donde se encuentra el archivo properties
	 */
	public void inicializarRuta(String ruta)
	{
		dao.inicializar(ruta);
	}
	
    // ---------------------------------------------------
    // Métodos asociados a los casos de uso: Consulta
    // ---------------------------------------------------
//    
//	/**
//	 * método que retorna los videos en orden alfabético.
//	 * invoca al DAO para obtener los resultados.
//	 * @return ArrayList lista con los videos ordenados alfabeticamente.
//	 * @throws Exception pasa la excepción generada por el DAO
//	 */
//	public ArrayList<Empresa> darVideosDefault() throws Exception
//	{
//	    return dao.darVideosDefault();
//	}
	
}
