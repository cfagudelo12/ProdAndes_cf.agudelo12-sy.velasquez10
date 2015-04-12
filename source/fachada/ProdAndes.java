package fachada;

import java.sql.Date;
import java.util.ArrayList;

import vos.MaterialValue;
import vos.ProductoValue;
import vos.RecursoValue;
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
	
	public ArrayList<RecursoValue> consultarExistenciasRecurso(String tipoMaterial, int rInferior, int rSuperior, int idEtapaProduccion, String fechaSolicitud, String fechaEntrega) throws Exception
	{
		return dao.consultarExistenciasRecurso(tipoMaterial, rInferior, rSuperior, idEtapaProduccion, fechaSolicitud, fechaEntrega);
	}
	
	public ArrayList<ProductoValue> consultarExistenciasProductos(int rInferior, int rSuperior, int idProcesoProduccion, String fechaSolicitud, String fechaEntrega) throws Exception
	{
		return dao.consultarExistenciasProductos(rInferior, rSuperior, idProcesoProduccion, fechaSolicitud, fechaEntrega);
	}
		
	public void registrarEjecucionEtapaProduccion(int idEtapaProduccion, int idOperario, String fechaEjecucion, int duracion) throws Exception
	{
		dao.registrarEjecucionEtapaProduccion(idEtapaProduccion, idOperario, fechaEjecucion, duracion);
	}
	
	public void registrarLlegadaRecurso(int idRecurso, int idPedido, String fechaLlegada) throws Exception
	{
		dao.registrarLlegadaRecurso(idRecurso,idPedido,fechaLlegada);
	}
	
	public void registrarEntregaProducto(int idProducto, int idPedido, String fechaLlegada) throws Exception
	{
		dao.registrarEntregaPedido(idProducto, idPedido, fechaLlegada);
	}
	
	public ArrayList<MaterialValue> consultarRecurso(int volumen, String desde, String hasta, Float costo) throws Exception
	{
		return dao.consultarRecurso(volumen, desde, hasta, costo);
	}
	public ArrayList<MaterialValue> consultarProducto(int cantidad, float costo) throws Exception 
	{
		return dao.consultarProducto(cantidad,costo);
	}

	public void solicitarPedido(int idCliente, int idProducto, String fechaEntrega, int cantidad) throws Exception 
	{
		dao.solicitarPedido(idCliente,idProducto,fechaEntrega, cantidad);
	}
	public ArrayList<Integer> darProcesosProduccion() throws Exception 
	{
		return dao.darProcesosProduccion();
	}
}
