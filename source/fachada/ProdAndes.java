package fachada;

import java.sql.Date;
import java.util.ArrayList;

import vos.Recurso;
import dao.ConsultaDAO;

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
	 * Contructor de la clase. Inicializa el atributo dao.
	 */
	private ProdAndes()
	{
		dao = new ConsultaDAO();
	}
	
	/**
	 * Inicializa el dao dándole la ruta en donde debe encontrar el archivo properties.
	 * @param ruta La ruta donde se encuentra el archivo properties
	 */
	public void inicializarRuta(String ruta)
	{
		dao.inicializar(ruta);
	}
	
    // ---------------------------------------------------
    // Métodos asociados a los casos de uso: Consulta
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
}
