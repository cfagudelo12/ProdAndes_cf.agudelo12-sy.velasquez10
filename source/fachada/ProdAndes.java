package fachada;

import java.sql.Date;
import java.util.ArrayList;

import vos.MaterialValue;
import vos.RecursoValue;
import dao.ConsultaDAO;

/**
 * Clase ProdAndes, que representa la fachada de comunicaci�n entre
 * la interfaz y la conexi�n con la base de datos. Atiende todas
 * las solicitudes.
 */
public class ProdAndes 
{
	/**
	 * Conexi�n con la clase que maneja la base de datos
	 */
	private ConsultaDAO dao;
	
    // -----------------------------------------------------------------
    // Singleton
    // -----------------------------------------------------------------

    /**
     * Instancia �nica de la clase
     */
    private static ProdAndes instancia;
    
    /**
     * Devuelve la instancia �nica de la clase
     * @return Instancia �nica de la clase
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
	 * Inicializa el dao d�ndole la ruta en donde debe encontrar el archivo properties.
	 * @param ruta La ruta donde se encuentra el archivo properties
	 */
	public void inicializarRuta(String ruta)
	{
		dao.inicializar(ruta);
	}
	
    // ---------------------------------------------------
    // M�todos asociados a los casos de uso: Consulta
    // ---------------------------------------------------
	
	public void registrarLlegadaRecurso(int idRecurso, int idPedido, Date fechaLlegada) throws Exception
	{
		dao.registrarLlegadaRecurso(idRecurso,idPedido,fechaLlegada);
	}
	public ArrayList<MaterialValue> consultarRecurso(int volumen, Date desde, Date hasta, Float costo) throws Exception
	{
		return dao.consultarRecurso(volumen, desde, hasta, costo);
	}
	public ArrayList<MaterialValue> consultarProducto(int cantidad, float costo) throws Exception 
	{
		return dao.consultarProducto(cantidad,costo);
	}

	public void solicitarPedido(String idCliente, String idProducto, Date fechaEntrega, int cantidad) throws Exception 
	{
		dao.solicitarPedido(idCliente,idProducto,fechaEntrega, cantidad);
	}
}
