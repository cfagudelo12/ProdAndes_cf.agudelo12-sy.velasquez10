package fachada;

import java.sql.Date;
import java.util.ArrayList;

import vos.ClienteValue;
import vos.EstacionProduccionValue;
import vos.MaterialValue;
import vos.PedidoValue;
import vos.ProductoValue;
import vos.ProveedorValue;
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
	public ArrayList<ProductoValue> darProductosParaRegistrarEntrega() throws Exception 
	{
		return dao.darProductosParaRegistrarEntrega();
	}
	public ArrayList<ProductoValue> darProductos() throws Exception 
	{
		return dao.darProductos();
	}

	public void cancelarPedidoCliente(int idPedido) throws Exception 
	{
		dao.cancelarPedidoCliente(idPedido);
	}

	public ArrayList<ProductoValue> darPedidosPendientesCliente() throws Exception
	{
		return dao.darPedidosPendientesCliente();
	}

	public ArrayList<ProductoValue> darPedidosCliente() throws Exception 
	{
		return dao.darPedidosCliente();
	}

	public ArrayList<PedidoValue> consultarPedidos() throws Exception 
	{
		return dao.consultarPedidos();
	}
	
	public ArrayList<PedidoValue> consultarPedidoPorId(int idPedido) throws Exception 
	{
		ArrayList<PedidoValue> a= new ArrayList<PedidoValue>();
		a.add(dao.consultarPedidosPorId(idPedido));
		return a;
	}

	public ArrayList<ClienteValue> darClientes() throws Exception 
	{
		return dao.darClientes();
	}

	public ArrayList<ClienteValue> consultarClientePorId(int idCliente) throws Exception
	{
		ArrayList<ClienteValue> a= new ArrayList<ClienteValue>();
		a.add(dao.consultarClientesPorId(idCliente));
		return a;
	}
	
	public ArrayList<ProveedorValue> darProveedores() throws Exception
	{
		return dao.darProveedores();
	}

	public ArrayList<ProveedorValue> consultarProveedorPorId(int idProveedor) throws Exception
	{
		ArrayList<ProveedorValue> a= new ArrayList<ProveedorValue>();
		a.add(dao.consultarProveedorPorId(idProveedor));
		return a;

	}

	public ArrayList<EstacionProduccionValue> darEstacionesProduccion() throws Exception
	{
		return dao.darEstacionesProduccion();
	}

	public ArrayList<PedidoValue> consultarPedidosPorMonto(int monto) throws Exception
	{
		return dao.consultarPedidosPorMonto(monto);
	}

	public ArrayList<ClienteValue> consultarClientes() throws Exception
	{
		return dao.consultarClientes();
	}

	public ArrayList<ClienteValue> consultarClientesPorEmail(String email) throws Exception
	{
		ArrayList<ClienteValue> a= new ArrayList<ClienteValue>();
		a.add(dao.consultarClientesPorEmail(email));
		return a;
	}

	public ArrayList<ProveedorValue> consultarProveedores() throws Exception
	{
		return dao.consultarProveedores();
	}

	public ArrayList<ProveedorValue> consultarProveedorPorNombre(String nombre) throws Exception
	{
		return dao.consultarProveedoresPorNombre(nombre);
	}

}
