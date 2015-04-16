package dao;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;

import vos.*;

/**
* Clase ConsultaDAO, encargada de hacer las consultas a la base de datos
*/
public class ConsultaDAO extends oracle.jdbc.driver.OracleDriver
{

	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	/**
	 * Ruta donde se encuentra el archivo de conexion.
	 */
	private static final String ARCHIVO_CONEXION = "/../conexion.properties";
	
	/**
	 * Constante que representa el id de la empresa de prueba.
	 */
	private static final int idEmpresaF = 1;
	
	//----------------------------------------------------
	// Constantes de tablas
	//----------------------------------------------------
	
	/**
	 * Constante que representa el nombre de la tabla Empresas
	 */
	private static final String tEmpresas="Empresas";
	
	/**
	 * Constante que representa el nombre de la tabla Recursos
	 */
	private static final String tRecursos="Recursos";
	
	/**
	 * Constante que representa el nombre de la tabla Usuarios
	 */
	private static final String tUsuarios="Usuarios";
	
	/**
	 * Constante que representa el nombre de la tabla Proveedores
	 */	
	private static final String tProveedores="Proveedores";
	
	/**
	 * Constante que representa el nombre de la tabla Clientes
	 */
	private static final String tClientes="Clientes";

	/**
	 * Constante que representa el nombre de la tabla Empleados
	 */
	private static final String tEmpleados="Empleados";

	/**
	 * Constante que representa el nombre de la tabla Pedidos
	 */
	private static final String tPedidos="Pedidos";

	/**
	 * Constante que representa el nombre de la tabla Productos
	 */
	private static final String tProductos="Productos";

	/**
	 * Constante que representa el nombre de la tabla Trabajan
	 */
	private static final String tTrabajan="Trabajan";

	/**
	 * Constante que representa el nombre de la tabla Solicitan
	 */
	private static final String tSolicitan="Solicitan";

	/**
	 * Constante que representa el nombre de la tablaCompran
	 */
	private static final String tCompran="Compran";

	/**
	 * Constante que representa el nombre de la tabla ProcesosProduccion
	 */
	private static final String tProcesosProduccion="ProcesosProduccion";
	
	private static final String tEjecutan="Ejecutan";
	
	/**
	 * Constante que representa el nombre de la tabla EtapasProduccion
	 */
	private static final String tEtapasProduccion="EtapasProduccion";

	/**
	 * Constante que representa el nombre de la tabla EtapasProduccion
	 */
	private static final String tEtapasProduccionPedido="EtapasProduccionPedido";
	
	/**
	 * Constante que representa el nombre de la tabla EstacionesProduccion
	 */
	private static final String tEstacionesProduccion="EstacionesProduccion";

	/**
	 * Constante que representa el nombre de la tabla Operan
	 */
	private static final String tOperan="Operan";
	
	/**
	 * Constante que representa el nombre de la tabla Tienen
	 */
	private static final String tTienen="Tienen";

	/**
	 * Constante que representa el nombre de la tablaClientela
	 */
	private static final String tClientela="Clientela";

	/**
	 * Constante que representa el nombre de la tabla Proveen
	 */
	private static final String tProveen="Proveen";
	
	/**
	 * Constante que representa el nombre de la tabla Requieren
	 */
	private static final String tRequieren="Requieren";
	
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
	public final static String usuario="ISIS2304481510";
	
	/**
	 * Clave de conexion a la base de datos.
	 */
	public final static String clave="fberrapius";
	
	/**
	 * URL al cual se debe conectar para acceder a la base de datos.
	 */
	public final static String cadenaConexion= "jdbc:oracle:thin:@prod.oracle.virtual.uniandes.edu.co:1531:prod";
	
	/**
	 * Constructor de la clase. No inicializa ningun atributo.
	 */
	public ConsultaDAO()
	{
		try 
		{
			reportarCambioEstadoEstacionProduccion(1, "Activa");
//			reportarCambioEstadoEstacionProduccion(1, "Inactiva");
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
		
	}

	/**
	 * Metodo que se encarga de crear la conexion con el Driver Manager a partir de los parametros recibidos.
	 * @param url Direccion url de la base de datos a la cual se desea conectar
	 * @param usuario Nombre del usuario que se va a conectar a la base de datos
	 * @param clave Clave de acceso a la base de datos
	 * @throws SQLException si ocurre un error generando la conexion con la base de datos.
	 */
	private void establecerConexion(String u,String p,String a) throws SQLException
	{
		try{
			conexion = DriverManager.getConnection("jdbc:oracle:thin:@prod.oracle.virtual.uniandes.edu.co:1531:prod","ISIS2304481510","fberrapius");
		}
		catch(SQLException exception){
			throw new SQLException("ERROR: ConsultaDAO obteniendo una conexion.");
		}
	}
	
	 /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args
     */
    public static void main( String[] args ) 
    {
        try {
			new ConsultaDAO();
		} 
        catch (Exception e) {
			e.printStackTrace();
		}
    }

	/**
	 * Cierra la conexion activa a la base de datos.
	 * @param connection Conexion a la base de datos.
	 * @throws Exception Si se presentan errores de conexion.
	 */
	public void closeConnection(Connection connection) throws Exception 
	{        
		try{
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
	
	public ArrayList<EstacionProduccionValue> darEstacionesProduccion() throws Exception 
	{
		ArrayList<EstacionProduccionValue> estaciones = new ArrayList<EstacionProduccionValue>();
		PreparedStatement selStmt = null;
		try
		{
			String consulta = "SELECT * FROM "+tEstacionesProduccion;
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				EstacionProduccionValue estacion= new EstacionProduccionValue();
				estacion.setIdEstacionProduccion(rs.getInt(EstacionProduccionValue.cIdEstacionProduccion));
				estacion.setCapacidadProduccion(rs.getInt(EstacionProduccionValue.cCapacidadProduccion));
				estaciones.add(estacion);
			}
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
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return estaciones;
	}
	
	public ArrayList<ProveedorValue> darProveedores() throws Exception 
	{
		ArrayList<ProveedorValue> proveedores = new ArrayList<ProveedorValue>();
		PreparedStatement selStmt = null;
		try
		{
			String consulta = "SELECT * FROM "+tProveedores;
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				ProveedorValue proveedor= new ProveedorValue();
				proveedor.setId(rs.getInt(ProveedorValue.cId));
				proveedor.setNombre(rs.getString(ProveedorValue.cNombreEmpresa));
				proveedores.add(proveedor);
			}
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
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return proveedores;
	}
	
	public ArrayList<RecursoValue> darRecursos() throws Exception 
	{
		ArrayList<RecursoValue> recursos = new ArrayList<RecursoValue>();
		PreparedStatement selStmt = null;
		try
		{
			String consulta = "SELECT * FROM "+tRecursos;
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				RecursoValue recurso= new RecursoValue();
				recurso.setIdRecurso(rs.getInt(RecursoValue.cIdRecurso));
				recurso.setNombre(rs.getString(RecursoValue.cNombre));
				recursos.add(recurso);
			}
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
	
	public ArrayList<ClienteValue> darClientes() throws Exception 
	{
		ArrayList<ClienteValue> clientes = new ArrayList<ClienteValue>();
		PreparedStatement selStmt = null;
		try
		{
			String consulta = "SELECT * FROM "+tUsuarios+" NATURAL INNER JOIN "+tClientes+" INNER JOIN "+tClientela+" ON id=idCliente WHERE "+EmpresaValue.cIdEmpresa+"="+idEmpresaF+"";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				ClienteValue cliente= new ClienteValue();
				cliente.setId(rs.getInt(UsuarioValue.cId));
				cliente.setClave(rs.getString(UsuarioValue.cClave));
				cliente.setTipoUsuario(rs.getString(UsuarioValue.cTipoUsuario));
				cliente.setCedula(rs.getString(UsuarioValue.cCedula));
				cliente.setNombre(rs.getString(UsuarioValue.cNombre));
				cliente.setNacionalidad(rs.getString(UsuarioValue.cNacionalidad));
				cliente.setDireccionFisica(rs.getString(UsuarioValue.cDireccionFisica));
				cliente.setTelefono(rs.getString(UsuarioValue.cTelefono));
				cliente.setEmail(rs.getString(UsuarioValue.cEmail));
				cliente.setDepartamento(rs.getString(UsuarioValue.cDepartamento));
				cliente.setCiudad(rs.getString(UsuarioValue.cCiudad));
				cliente.setCodigoPostal(rs.getString(UsuarioValue.cCodigoPostal));
				cliente.setIdRepresentanteLegal(rs.getInt(ClienteValue.cIdRepresentanteLegal));
				cliente.setRegistroSINV(rs.getString(ClienteValue.cRegistroSINV));
				clientes.add(cliente);
			}
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
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return clientes;
	}
	
	public ArrayList<ProductoValue> darPedidosCliente() throws Exception 
	{
		ArrayList<ProductoValue> productos = new ArrayList<ProductoValue>();
		PreparedStatement selStmt = null;
		try
		{
			String consulta = "SELECT * FROM "+tProductos+" NATURAL JOIN "+tCompran+" NATURAL JOIN "+tPedidos+" WHERE idEmpresa=1 order by idPedido";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				ProductoValue producto= new ProductoValue();
				producto.setCantidadEnBodega(rs.getInt(ProductoValue.cCantidadEnBodega));
				producto.setCosto(rs.getFloat(ProductoValue.cCosto));
				producto.setIdEmpresa(rs.getInt(ProductoValue.cIdEmpresa));
				producto.setIdProcesoProduccion(rs.getInt(ProductoValue.cIdProcesoProduccion));
				producto.setIdProducto(rs.getInt(ProductoValue.cIdProducto));
				producto.setNombre(rs.getString(ProductoValue.cNombre));
				producto.setUnidadesEnProduccion(rs.getInt(ProductoValue.cUnidadesEnProduccion));
				producto.setUnidadesProducidas(rs.getInt(ProductoValue.cUnidadesProducidas));
				producto.setUnidadesVendidas(rs.getInt(ProductoValue.cUnidadesVendidas));
				producto.setIdPedido(rs.getInt(PedidoValue.cIdPedido));
				productos.add(producto);
			}
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
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return productos;
	}

	public ArrayList<ProductoValue> darPedidosPendientesCliente() throws Exception 
	{
		ArrayList<ProductoValue> productos = new ArrayList<ProductoValue>();
		PreparedStatement selStmt = null;
		try
		{
			String consulta = "SELECT * FROM "+tProductos+" NATURAL JOIN "+tCompran+" NATURAL JOIN "+tPedidos+" WHERE idEmpresa=1 AND estado='Pendiente' order by idPedido";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				ProductoValue producto= new ProductoValue();
				producto.setCantidadEnBodega(rs.getInt(ProductoValue.cCantidadEnBodega));
				producto.setCosto(rs.getFloat(ProductoValue.cCosto));
				producto.setIdEmpresa(rs.getInt(ProductoValue.cIdEmpresa));
				producto.setIdProcesoProduccion(rs.getInt(ProductoValue.cIdProcesoProduccion));
				producto.setIdProducto(rs.getInt(ProductoValue.cIdProducto));
				producto.setNombre(rs.getString(ProductoValue.cNombre));
				producto.setUnidadesEnProduccion(rs.getInt(ProductoValue.cUnidadesEnProduccion));
				producto.setUnidadesProducidas(rs.getInt(ProductoValue.cUnidadesProducidas));
				producto.setUnidadesVendidas(rs.getInt(ProductoValue.cUnidadesVendidas));
				producto.setIdPedido(rs.getInt(PedidoValue.cIdPedido));
				productos.add(producto);
			}
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
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return productos;
	}

	public ArrayList<Integer> darProcesosProduccion() throws Exception
	{
		ArrayList<Integer> procesos = new ArrayList<Integer>();
		PreparedStatement selStmt = null;
		try{
			String consulta = "SELECT * FROM "+tProcesosProduccion+" WHERE idEmpresa=1";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				procesos.add(rs.getInt(ProcesoProduccionValue.cIdProcesoProduccion));
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
		return procesos;
	}
	
	public ArrayList<ProductoValue> darProductosParaRegistrarEntrega() throws Exception
	{
		ArrayList<ProductoValue> productos = new ArrayList<ProductoValue>();
		PreparedStatement selStmt = null;
		try
		{
			String consulta = "SELECT * FROM "+tProductos+" NATURAL JOIN "+tCompran+" NATURAL JOIN "+tPedidos+" WHERE idEmpresa=1 order by idPedido";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				ProductoValue producto= new ProductoValue();
				producto.setCantidadEnBodega(rs.getInt(ProductoValue.cCantidadEnBodega));
				producto.setCosto(rs.getFloat(ProductoValue.cCosto));
				producto.setIdEmpresa(rs.getInt(ProductoValue.cIdEmpresa));
				producto.setIdProcesoProduccion(rs.getInt(ProductoValue.cIdProcesoProduccion));
				producto.setIdProducto(rs.getInt(ProductoValue.cIdProducto));
				producto.setNombre(rs.getString(ProductoValue.cNombre));
				producto.setUnidadesEnProduccion(rs.getInt(ProductoValue.cUnidadesEnProduccion));
				producto.setUnidadesProducidas(rs.getInt(ProductoValue.cUnidadesProducidas));
				producto.setUnidadesVendidas(rs.getInt(ProductoValue.cUnidadesVendidas));
				producto.setIdPedido(rs.getInt(PedidoValue.cIdPedido));
				productos.add(producto);
			}
		}
		catch (SQLException e)
		{
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
		return productos;
	}
	
	public ArrayList<ProductoValue> darProductos() throws Exception
	{
		ArrayList<ProductoValue> productos = new ArrayList<ProductoValue>();
		PreparedStatement selStmt = null;
		try
		{
			String consulta = "SELECT DISTINCT * FROM "+tProductos+" WHERE idEmpresa=1";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				ProductoValue producto= new ProductoValue();
				producto.setCantidadEnBodega(rs.getInt(ProductoValue.cCantidadEnBodega));
				producto.setCosto(rs.getFloat(ProductoValue.cCosto));
				producto.setIdEmpresa(rs.getInt(ProductoValue.cIdEmpresa));
				producto.setIdProcesoProduccion(rs.getInt(ProductoValue.cIdProcesoProduccion));
				producto.setIdProducto(rs.getInt(ProductoValue.cIdProducto));
				producto.setNombre(rs.getString(ProductoValue.cNombre));
				producto.setUnidadesEnProduccion(rs.getInt(ProductoValue.cUnidadesEnProduccion));
				producto.setUnidadesProducidas(rs.getInt(ProductoValue.cUnidadesProducidas));
				producto.setUnidadesVendidas(rs.getInt(ProductoValue.cUnidadesVendidas));
				productos.add(producto);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null){
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return productos;
	}
	
	public PedidoValue consultarPedidosPorId(int idPedido) throws Exception
	{
		PedidoValue pedido = new PedidoValue();
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		try
		{
			String consulta = "SELECT * FROM "+tPedidos+" NATURAL INNER JOIN "+tCompran+" NATURAL INNER JOIN "+tProductos+" WHERE "+EmpresaValue.cIdEmpresa+"="+idEmpresaF+"AND "+PedidoValue.cIdPedido+"="+idPedido;
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{		
				pedido.setIdPedido(rs.getInt(PedidoValue.cIdPedido));
				pedido.setMonto(rs.getFloat(PedidoValue.cMonto));
				pedido.setFechaPedido(rs.getDate(PedidoValue.cFechaPedido));
				pedido.setFechaEsperada(rs.getDate(PedidoValue.cFechaEsperada));
				pedido.setFechaLlegada(rs.getDate(PedidoValue.cFechaLlegada));
				pedido.setCantidad(rs.getInt(PedidoValue.cCantidad));
				pedido.setEstado(rs.getString(PedidoValue.cEstado));
				
				ProductoValue producto = new ProductoValue();
				producto.setCantidadEnBodega(rs.getInt(ProductoValue.cCantidadEnBodega));
				producto.setCosto(rs.getFloat(ProductoValue.cCosto));
				producto.setIdEmpresa(rs.getInt(ProductoValue.cIdEmpresa));
				producto.setIdProcesoProduccion(rs.getInt(ProductoValue.cIdProcesoProduccion));
				producto.setIdProducto(rs.getInt(ProductoValue.cIdProducto));
				producto.setNombre(rs.getString(ProductoValue.cNombre));
				producto.setUnidadesEnProduccion(rs.getInt(ProductoValue.cUnidadesEnProduccion));
				producto.setUnidadesProducidas(rs.getInt(ProductoValue.cUnidadesProducidas));
				producto.setUnidadesVendidas(rs.getInt(ProductoValue.cUnidadesVendidas));
				pedido.agregarProducto(producto);
				
				String consulta2 = "SELECT * FROM "+tProcesosProduccion+" p, "+tEtapasProduccion+" s NATURAL INNER JOIN "+tRequieren+" NATURAL INNER JOIN "+tRecursos+" WHERE p."+ProcesoProduccionValue.cIdProcesoProduccion+"= s."+ProcesoProduccionValue.cIdProcesoProduccion+" AND "+ProductoValue.cIdProducto+"="+producto.getIdProducto()+"";
				selStmt2 = conexion.prepareStatement(consulta2);
				ResultSet rs2 = selStmt2.executeQuery();	
				while(rs2.next())
				{
					RecursoValue recurso = new RecursoValue();
					recurso.setIdRecurso(rs2.getInt(RecursoValue.cIdRecurso));
					recurso.setNombre(rs2.getString(RecursoValue.cNombre));
					recurso.setTipoRecurso(rs2.getString(RecursoValue.cTipoRecurso));
					recurso.setUnidadMedida(rs2.getString(RecursoValue.cUnidadMedida));
					recurso.setIdProveedor(rs2.getInt(RecursoValue.cIdProveedor));
					recurso.setCantidadMDistribucion(rs2.getInt(RecursoValue.cCantidadMDistribucion));
					recurso.setTiempoEntrega(rs2.getInt(RecursoValue.cTiempoEntrega));
					pedido.agregarRecursoRequerido(recurso);
				}
			}
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
			if (selStmt2 != null)
			{
				try
				{
					selStmt2.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return pedido;
	}
	
	public ArrayList<PedidoValue> consultarPedidos() throws Exception{
		ArrayList<PedidoValue> pedidos = new ArrayList<PedidoValue>();
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		PreparedStatement selStmt3 = null;
		try{
			String consulta = "SELECT * FROM "+tCompran+" NATURAL INNER JOIN "+tPedidos;
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next()){
				PedidoValue pedido = new PedidoValue();
				pedido.setIdPedido(rs.getInt(PedidoValue.cIdPedido));
				pedido.setMonto(rs.getFloat(PedidoValue.cMonto));
				pedido.setFechaPedido(rs.getDate(PedidoValue.cFechaPedido));
				pedido.setFechaEsperada(rs.getDate(PedidoValue.cFechaEsperada));
				pedido.setFechaLlegada(rs.getDate(PedidoValue.cFechaLlegada));
				pedido.setCantidad(rs.getInt(PedidoValue.cCantidad));
				pedido.setEstado(rs.getString(PedidoValue.cEstado));
				String consulta2 = "SELECT * FROM "+tCompran+" NATURAL INNER JOIN "+tProductos+" WHERE "+PedidoValue.cIdPedido+"="+pedido.getIdPedido();
				selStmt2 = conexion.prepareStatement(consulta2);
				ResultSet rs2 = selStmt2.executeQuery();	
				while(rs2.next()){
					ProductoValue producto = new ProductoValue();
					producto.setCantidadEnBodega(rs2.getInt(ProductoValue.cCantidadEnBodega));
					producto.setCosto(rs2.getFloat(ProductoValue.cCosto));
					producto.setIdEmpresa(rs2.getInt(ProductoValue.cIdEmpresa));
					producto.setIdProcesoProduccion(rs2.getInt(ProductoValue.cIdProcesoProduccion));
					producto.setIdProducto(rs2.getInt(ProductoValue.cIdProducto));
					producto.setNombre(rs2.getString(ProductoValue.cNombre));
					producto.setUnidadesEnProduccion(rs2.getInt(ProductoValue.cUnidadesEnProduccion));
					producto.setUnidadesProducidas(rs2.getInt(ProductoValue.cUnidadesProducidas));
					producto.setUnidadesVendidas(rs2.getInt(ProductoValue.cUnidadesVendidas));
					pedido.agregarProducto(producto);
					String consulta3 = "SELECT * FROM "+tRequieren+" NATURAL INNER JOIN "+tRecursos+" NATURAL INNER JOIN ETAPASPRODUCCION NATURAL INNER JOIN PROCESOSPRODUCCION WHERE "+ProductoValue.cIdProducto+"="+producto.getIdProducto()+"";
					selStmt3 = conexion.prepareStatement(consulta3);
					ResultSet rs3 = selStmt3.executeQuery();	
					while(rs3.next()){
						RecursoValue recurso = new RecursoValue();
						recurso.setIdRecurso(rs3.getInt(RecursoValue.cIdRecurso));
						recurso.setNombre(rs3.getString(RecursoValue.cNombre));
						recurso.setTipoRecurso(rs3.getString(RecursoValue.cTipoRecurso));
						recurso.setUnidadMedida(rs3.getString(RecursoValue.cUnidadMedida));
						recurso.setIdProveedor(rs3.getInt(RecursoValue.cIdProveedor));
						recurso.setCantidadMDistribucion(rs3.getInt(RecursoValue.cCantidadMDistribucion));
						recurso.setTiempoEntrega(rs3.getInt(RecursoValue.cTiempoEntrega));
						pedido.agregarRecursoRequerido(recurso);
					}
					producto = new ProductoValue();
				}
				pedidos.add(pedido);
				pedido = new PedidoValue();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null){
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt2 != null){
				try{
					selStmt2.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt3 != null){
				try{
					selStmt3.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return pedidos;
	}
	
	public ArrayList<PedidoValue> consultarPedidosPorMonto(int monto) throws Exception{
		ArrayList<PedidoValue> pedidos = new ArrayList<PedidoValue>();
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		PreparedStatement selStmt3 = null;
		try{
			String consulta = "SELECT * FROM "+tCompran+" c NATURAL INNER JOIN "+tPedidos+" p WHERE p.monto="+monto;
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next()){
				PedidoValue pedido = new PedidoValue();
				pedido.setIdPedido(rs.getInt(PedidoValue.cIdPedido));
				pedido.setMonto(rs.getFloat(PedidoValue.cMonto));
				pedido.setFechaPedido(rs.getDate(PedidoValue.cFechaPedido));
				pedido.setFechaEsperada(rs.getDate(PedidoValue.cFechaEsperada));
				pedido.setFechaLlegada(rs.getDate(PedidoValue.cFechaLlegada));
				pedido.setCantidad(rs.getInt(PedidoValue.cCantidad));
				pedido.setEstado(rs.getString(PedidoValue.cEstado));
				String consulta2 = "SELECT * FROM "+tCompran+" NATURAL INNER JOIN "+tProductos+" WHERE "+PedidoValue.cIdPedido+"="+pedido.getIdPedido();
				selStmt2 = conexion.prepareStatement(consulta2);
				ResultSet rs2 = selStmt2.executeQuery();	
				while(rs2.next()){
					ProductoValue producto = new ProductoValue();
					producto.setCantidadEnBodega(rs2.getInt(ProductoValue.cCantidadEnBodega));
					producto.setCosto(rs2.getFloat(ProductoValue.cCosto));
					producto.setIdEmpresa(rs2.getInt(ProductoValue.cIdEmpresa));
					producto.setIdProcesoProduccion(rs2.getInt(ProductoValue.cIdProcesoProduccion));
					producto.setIdProducto(rs2.getInt(ProductoValue.cIdProducto));
					producto.setNombre(rs2.getString(ProductoValue.cNombre));
					producto.setUnidadesEnProduccion(rs2.getInt(ProductoValue.cUnidadesEnProduccion));
					producto.setUnidadesProducidas(rs2.getInt(ProductoValue.cUnidadesProducidas));
					producto.setUnidadesVendidas(rs2.getInt(ProductoValue.cUnidadesVendidas));
					pedido.agregarProducto(producto);
					String consulta3 = "SELECT * FROM "+tRequieren+" NATURAL INNER JOIN "+tRecursos+" NATURAL INNER JOIN ETAPASPRODUCCION NATURAL INNER JOIN PROCESOSPRODUCCION WHERE "+ProductoValue.cIdProducto+"="+producto.getIdProducto()+"";
					selStmt3 = conexion.prepareStatement(consulta3);
					ResultSet rs3 = selStmt3.executeQuery();	
					while(rs3.next()){
						RecursoValue recurso = new RecursoValue();
						recurso.setIdRecurso(rs3.getInt(RecursoValue.cIdRecurso));
						recurso.setNombre(rs3.getString(RecursoValue.cNombre));
						recurso.setTipoRecurso(rs3.getString(RecursoValue.cTipoRecurso));
						recurso.setUnidadMedida(rs3.getString(RecursoValue.cUnidadMedida));
						recurso.setIdProveedor(rs3.getInt(RecursoValue.cIdProveedor));
						recurso.setCantidadMDistribucion(rs3.getInt(RecursoValue.cCantidadMDistribucion));
						recurso.setTiempoEntrega(rs3.getInt(RecursoValue.cTiempoEntrega));
						pedido.agregarRecursoRequerido(recurso);
					}
					producto = new ProductoValue();
				}
				pedidos.add(pedido);
				pedido = new PedidoValue();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null){
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt2 != null){
				try{
					selStmt2.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt3 != null){
				try{
					selStmt3.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return pedidos;
	}
	
	public ClienteValue consultarClientesPorId(int idCliente) throws Exception
	{
		ClienteValue cliente = new ClienteValue();
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		try
		{
			String consulta = "SELECT * FROM "+tUsuarios+" NATURAL INNER JOIN "+tClientes+" NATURAL INNER JOIN "+tClientela+" c WHERE c."+EmpresaValue.cIdEmpresa+"="+idEmpresaF+" AND "+ClienteValue.cId+"="+idCliente;
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				cliente.setId(rs.getInt(UsuarioValue.cId));
				cliente.setClave(rs.getString(UsuarioValue.cClave));
				cliente.setTipoUsuario(rs.getString(UsuarioValue.cTipoUsuario));
				cliente.setCedula(rs.getString(UsuarioValue.cCedula));
				cliente.setNombre(rs.getString(UsuarioValue.cNombre));
				cliente.setNacionalidad(rs.getString(UsuarioValue.cNacionalidad));
				cliente.setDireccionFisica(rs.getString(UsuarioValue.cDireccionFisica));
				cliente.setTelefono(rs.getString(UsuarioValue.cTelefono));
				cliente.setEmail(rs.getString(UsuarioValue.cEmail));
				cliente.setDepartamento(rs.getString(UsuarioValue.cDepartamento));
				cliente.setCiudad(rs.getString(UsuarioValue.cCiudad));
				cliente.setCodigoPostal(rs.getString(UsuarioValue.cCodigoPostal));
				cliente.setIdRepresentanteLegal(rs.getInt(ClienteValue.cIdRepresentanteLegal));
				cliente.setRegistroSINV(rs.getString(ClienteValue.cRegistroSINV));
				String consulta2 = "SELECT * FROM "+tPedidos+" NATURAL INNER JOIN "+tCompran+" s NATURAL INNER JOIN "+tProductos+" WHERE s."+ClienteValue.cfIdCliente+"="+cliente.getId();
				selStmt2 = conexion.prepareStatement(consulta2);
				ResultSet rs2 = selStmt2.executeQuery();	
				while(rs2.next())
				{
					ProductoValue producto = new ProductoValue();
					producto.setNombre(rs2.getString(ProductoValue.cNombre));
					PedidoValue pedido = new PedidoValue();
					pedido.setIdPedido(rs2.getInt(PedidoValue.cIdPedido));
					pedido.setMonto(rs2.getFloat(PedidoValue.cMonto));
					pedido.setFechaPedido(rs2.getDate(PedidoValue.cFechaPedido));
					pedido.setFechaEsperada(rs2.getDate(PedidoValue.cFechaEsperada));
					pedido.setFechaLlegada(rs2.getDate(PedidoValue.cFechaLlegada));
					pedido.setEstado(rs2.getString(PedidoValue.cEstado));
					pedido.agregarProducto(producto);
					cliente.agregarPedido(pedido);
				}
			}
		}
		catch (SQLException e){
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
			if (selStmt2 != null)
			{
				try
				{
					selStmt2.close();
				} 
				catch (SQLException exception)
				{
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return cliente;
	}
	
	public ClienteValue consultarClientesPorEmail(String email) throws Exception
	{
		ClienteValue cliente = new ClienteValue();
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		try
		{
			String consulta = "SELECT * FROM "+tUsuarios+" NATURAL INNER JOIN "+tClientes+" NATURAL INNER JOIN "+tClientela+" c WHERE c."+EmpresaValue.cIdEmpresa+"="+idEmpresaF+" AND "+ClienteValue.cEmail+"='"+email+"'";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				cliente.setId(rs.getInt(UsuarioValue.cId));
				cliente.setClave(rs.getString(UsuarioValue.cClave));
				cliente.setTipoUsuario(rs.getString(UsuarioValue.cTipoUsuario));
				cliente.setCedula(rs.getString(UsuarioValue.cCedula));
				cliente.setNombre(rs.getString(UsuarioValue.cNombre));
				cliente.setNacionalidad(rs.getString(UsuarioValue.cNacionalidad));
				cliente.setDireccionFisica(rs.getString(UsuarioValue.cDireccionFisica));
				cliente.setTelefono(rs.getString(UsuarioValue.cTelefono));
				cliente.setEmail(rs.getString(UsuarioValue.cEmail));
				cliente.setDepartamento(rs.getString(UsuarioValue.cDepartamento));
				cliente.setCiudad(rs.getString(UsuarioValue.cCiudad));
				cliente.setCodigoPostal(rs.getString(UsuarioValue.cCodigoPostal));
				cliente.setIdRepresentanteLegal(rs.getInt(ClienteValue.cIdRepresentanteLegal));
				cliente.setRegistroSINV(rs.getString(ClienteValue.cRegistroSINV));
				String consulta2 = "SELECT * FROM "+tPedidos+" NATURAL INNER JOIN "+tCompran+" s NATURAL INNER JOIN "+tProductos+" WHERE s."+ClienteValue.cfIdCliente+"="+cliente.getId();
				selStmt2 = conexion.prepareStatement(consulta2);
				ResultSet rs2 = selStmt2.executeQuery();	
				while(rs2.next())
				{
					ProductoValue producto = new ProductoValue();
					producto.setNombre(rs2.getString(ProductoValue.cNombre));
					PedidoValue pedido = new PedidoValue();
					pedido.setIdPedido(rs2.getInt(PedidoValue.cIdPedido));
					pedido.setMonto(rs2.getFloat(PedidoValue.cMonto));
					pedido.setFechaPedido(rs2.getDate(PedidoValue.cFechaPedido));
					pedido.setFechaEsperada(rs2.getDate(PedidoValue.cFechaEsperada));
					pedido.setFechaLlegada(rs2.getDate(PedidoValue.cFechaLlegada));
					pedido.setEstado(rs2.getString(PedidoValue.cEstado));
					pedido.agregarProducto(producto);
					cliente.agregarPedido(pedido);
				}
			}
		}
		catch (SQLException e){
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
			if (selStmt2 != null)
			{
				try
				{
					selStmt2.close();
				} 
				catch (SQLException exception)
				{
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return cliente;
	}
	
	public ArrayList<ClienteValue> consultarClientes() throws Exception
	{
		ArrayList<ClienteValue> clientes = new ArrayList<ClienteValue>();
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String consulta = "SELECT * FROM "+tUsuarios+" NATURAL INNER JOIN "+tClientes+" INNER JOIN "+tClientela+" ON id=idCliente WHERE "+EmpresaValue.cIdEmpresa+"="+idEmpresaF+"";
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next()){
				ClienteValue cliente = new ClienteValue();
				cliente.setId(rs.getInt(UsuarioValue.cId));
				cliente.setClave(rs.getString(UsuarioValue.cClave));
				cliente.setTipoUsuario(rs.getString(UsuarioValue.cTipoUsuario));
				cliente.setCedula(rs.getString(UsuarioValue.cCedula));
				cliente.setNombre(rs.getString(UsuarioValue.cNombre));
				cliente.setNacionalidad(rs.getString(UsuarioValue.cNacionalidad));
				cliente.setDireccionFisica(rs.getString(UsuarioValue.cDireccionFisica));
				cliente.setTelefono(rs.getString(UsuarioValue.cTelefono));
				cliente.setEmail(rs.getString(UsuarioValue.cEmail));
				cliente.setDepartamento(rs.getString(UsuarioValue.cDepartamento));
				cliente.setCiudad(rs.getString(UsuarioValue.cCiudad));
				cliente.setCodigoPostal(rs.getString(UsuarioValue.cCodigoPostal));
				cliente.setIdRepresentanteLegal(rs.getInt(ClienteValue.cIdRepresentanteLegal));
				cliente.setRegistroSINV(rs.getString(ClienteValue.cRegistroSINV));
				String consulta2 = "SELECT * FROM "+tPedidos+" NATURAL INNER JOIN "+tCompran+" c WHERE c."+ClienteValue.cfIdCliente+"="+cliente.getId();
				selStmt2 = conexion.prepareStatement(consulta2);
				ResultSet rs2 = selStmt2.executeQuery();	
				while(rs2.next()){
					PedidoValue pedido = new PedidoValue();
					pedido.setIdPedido(rs2.getInt(PedidoValue.cIdPedido));
					pedido.setMonto(rs2.getFloat(PedidoValue.cMonto));
					pedido.setFechaPedido(rs2.getDate(PedidoValue.cFechaPedido));
					pedido.setFechaEsperada(rs2.getDate(PedidoValue.cFechaEsperada));
					pedido.setFechaLlegada(rs2.getDate(PedidoValue.cFechaLlegada));
					pedido.setEstado(rs2.getString(PedidoValue.cEstado));
					cliente.agregarPedido(pedido);
				}
				clientes.add(cliente);
				cliente = new ClienteValue();
			}
		}
		catch (SQLException e){
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null){
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt2 != null){
				try{
					selStmt2.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return clientes;
	}
	
	public ProveedorValue consultarProveedorPorId(int idProveedor) throws Exception{
		ProveedorValue proveedor = new ProveedorValue();
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		try
		{
			String consulta = "SELECT * FROM "+tUsuarios+" NATURAL INNER JOIN "+tProveedores+" WHERE id="+idProveedor;
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next())
			{
				proveedor.setId(rs.getInt(UsuarioValue.cId));
				proveedor.setClave(rs.getString(UsuarioValue.cClave));
				proveedor.setTipoUsuario(rs.getString(UsuarioValue.cTipoUsuario));
				proveedor.setCedula(rs.getString(UsuarioValue.cCedula));
				proveedor.setNombre(rs.getString(UsuarioValue.cNombre));
				proveedor.setNombreEmpresa(rs.getString(ProveedorValue.cNombre));
				proveedor.setNacionalidad(rs.getString(UsuarioValue.cNacionalidad));
				proveedor.setDireccionFisica(rs.getString(UsuarioValue.cDireccionFisica));
				proveedor.setTelefono(rs.getString(UsuarioValue.cTelefono));
				proveedor.setEmail(rs.getString(UsuarioValue.cEmail));
				proveedor.setDepartamento(rs.getString(UsuarioValue.cDepartamento));
				proveedor.setCiudad(rs.getString(UsuarioValue.cCiudad));
				proveedor.setCodigoPostal(rs.getString(UsuarioValue.cCodigoPostal));
				proveedor.setIdRepresentanteLegal(rs.getInt(ProveedorValue.cIdRepresentanteLegal));
				proveedor.setVolumenMaximo(rs.getInt(ProveedorValue.cVolumenMaximo));
			}
			String consulta2 = "SELECT * FROM "+tPedidos+" NATURAL JOIN "+tSolicitan+" NATURAL INNER JOIN "+tProveedores+" WHERE id="+idProveedor+" order By idPedido";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt2 = conexion.prepareStatement(consulta2);
			ResultSet rs2 = selStmt2.executeQuery();		
			while(rs2.next())
			{
				PedidoValue pedido= new PedidoValue();
				pedido.setIdPedido(rs2.getInt(PedidoValue.cIdPedido));
				pedido.setMonto(rs2.getInt(PedidoValue.cMonto));
				pedido.setEstado(rs2.getString(PedidoValue.cEstado));
				proveedor.agregarPedido(pedido);
			}
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
				catch (SQLException exception)
				{
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
		return proveedor;
	}
	
	public ArrayList<ProveedorValue> consultarProveedores() throws Exception{
		ArrayList<ProveedorValue> proveedores = new ArrayList<ProveedorValue>();
		PreparedStatement selStmt = null;
		try{
			String consulta = "SELECT * FROM "+tUsuarios+" NATURAL INNER JOIN "+tProveedores;
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next()){
				ProveedorValue proveedor = new ProveedorValue();
				proveedor.setId(rs.getInt(UsuarioValue.cId));
				proveedor.setClave(rs.getString(UsuarioValue.cClave));
				proveedor.setTipoUsuario(rs.getString(UsuarioValue.cTipoUsuario));
				proveedor.setCedula(rs.getString(UsuarioValue.cCedula));
				proveedor.setNombre(rs.getString(UsuarioValue.cNombre));
				proveedor.setNacionalidad(rs.getString(UsuarioValue.cNacionalidad));
				proveedor.setDireccionFisica(rs.getString(UsuarioValue.cDireccionFisica));
				proveedor.setTelefono(rs.getString(UsuarioValue.cTelefono));
				proveedor.setEmail(rs.getString(UsuarioValue.cEmail));
				proveedor.setDepartamento(rs.getString(UsuarioValue.cDepartamento));
				proveedor.setCiudad(rs.getString(UsuarioValue.cCiudad));
				proveedor.setCodigoPostal(rs.getString(UsuarioValue.cCodigoPostal));
				proveedor.setIdRepresentanteLegal(rs.getInt(ProveedorValue.cIdRepresentanteLegal));
				proveedor.setVolumenMaximo(rs.getInt(ProveedorValue.cVolumenMaximo));
				proveedor.setNombreEmpresa(rs.getString(ProveedorValue.cNombreEmpresa));
				proveedores.add(proveedor);
				proveedor = new ProveedorValue();
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
		return proveedores;
	}
	
	public ArrayList<ProveedorValue> consultarProveedoresPorNombre(String nombre) throws Exception{
		ArrayList<ProveedorValue> proveedores = new ArrayList<ProveedorValue>();
		PreparedStatement selStmt = null;
		try{
			String consulta = "SELECT * FROM "+tUsuarios+" u NATURAL INNER JOIN "+tProveedores+" p WHERE p.NOMBREEMPRESA='"+nombre+"'";
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();		
			while(rs.next()){
				ProveedorValue proveedor = new ProveedorValue();
				proveedor.setId(rs.getInt(UsuarioValue.cId));
				proveedor.setClave(rs.getString(UsuarioValue.cClave));
				proveedor.setTipoUsuario(rs.getString(UsuarioValue.cTipoUsuario));
				proveedor.setCedula(rs.getString(UsuarioValue.cCedula));
				proveedor.setNombre(rs.getString(UsuarioValue.cNombre));
				proveedor.setNacionalidad(rs.getString(UsuarioValue.cNacionalidad));
				proveedor.setDireccionFisica(rs.getString(UsuarioValue.cDireccionFisica));
				proveedor.setTelefono(rs.getString(UsuarioValue.cTelefono));
				proveedor.setEmail(rs.getString(UsuarioValue.cEmail));
				proveedor.setDepartamento(rs.getString(UsuarioValue.cDepartamento));
				proveedor.setCiudad(rs.getString(UsuarioValue.cCiudad));
				proveedor.setCodigoPostal(rs.getString(UsuarioValue.cCodigoPostal));
				proveedor.setIdRepresentanteLegal(rs.getInt(ProveedorValue.cIdRepresentanteLegal));
				proveedor.setVolumenMaximo(rs.getInt(ProveedorValue.cVolumenMaximo));
				proveedor.setNombreEmpresa(rs.getString(ProveedorValue.cNombreEmpresa));
				proveedores.add(proveedor);
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
		return proveedores;
	}
	
	/**
	 * Metodo encargado de consultar en la base de datos la existencia de un recurso, 
	 * segun el tipo de material, un rango de cantidad, una fecha de solicitud y fecha de entrega
	 * @param tipoMaterial El tipo de material. Materia prima o Componente.
	 * @param rInferior El limite inferior del rango.
	 * @param rSuperior El limite superior del rango.
	 * @param idEtapaProduccion El id de la etapa de produccion.
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return Una lista con los recursos que cumplen con los parametros.
	 * @throws Exception Si hay algun problema en la conexion o si no hay existencias disponibles.
	 */
	public ArrayList<RecursoValue> consultarExistenciasRecurso(String tipoMaterial, int rInferior, int rSuperior, int idEtapaProduccion, String fechaSolicitud, String fechaEntrega) throws Exception{
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
				recurso.setTipoRecurso(rs.getString(RecursoValue.cTipoRecurso));
				recurso.setUnidadMedida(rs.getString(RecursoValue.cUnidadMedida));
				recurso.setIdProveedor(rs.getInt(RecursoValue.cIdProveedor));
				recurso.setCantidadMDistribucion(rs.getInt(RecursoValue.cCantidadMDistribucion));
				recurso.setTiempoEntrega(rs.getInt(RecursoValue.cTiempoEntrega));
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
	
	/**
	 * Metodo encargado en generar el query de la consulta de existencias de un recurso.
	 * @param tipoMaterial El tipo de material. Materia prima o Componente.
	 * @param rInferior El limite inferior del rango.
	 * @param rSuperior El limite superior del rango.
	 * @param idEtapaProduccion El id de la etapa de produccion.
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return El query respectivo a la solicitud.
	 */
	private String generarConsultaExistenciaRecurso(String tipoMaterial, int rInferior, int rSuperior, int idEtapaProduccion, String fechaPedido, String fechaLlegada)
	{
		String consulta = "SELECT * FROM "+tRecursos+" NATURAL INNER JOIN (SELECT "+RecursoValue.cIdRecurso+" FROM "+tTienen+" WHERE "+EmpresaValue.cIdEmpresa+"="+idEmpresaF+") WHERE "+RecursoValue.cTipoRecurso+"='"+tipoMaterial+"'";
		if(rInferior!=0||rSuperior!=0||idEtapaProduccion!=0||!fechaPedido.equals("")||!fechaLlegada.equals("")){
			if(rInferior>0 && rInferior<rSuperior){
				consulta+=" AND "+RecursoValue.cIdRecurso+" IN (SELECT "+RecursoValue.cIdRecurso+" FROM "+tRecursos+" NATURAL INNER JOIN (SELECT "+RecursoValue.cIdRecurso+" FROM "+tTienen+" WHERE "+EmpresaValue.cIdEmpresa+"="+idEmpresaF+
						" AND "+RecursoValue.cfCantidadEnBodega+" BETWEEN "+rInferior+" AND "+rSuperior+"))";
			}
			if(idEtapaProduccion>0){
				consulta+=" AND "+RecursoValue.cIdRecurso+" IN (SELECT "+RecursoValue.cIdRecurso+" FROM "+tRecursos+" NATURAL INNER JOIN (SELECT "+RecursoValue.cIdRecurso+" FROM "+tRequieren+" WHERE "+EtapaProduccionValue.cIdEtapaProduccion+"="+idEtapaProduccion+"))";
			}
			if(fechaPedido!=null){
				consulta+=" AND "+RecursoValue.cIdRecurso+" IN(SELECT "+RecursoValue.cIdRecurso+" FROM "+tSolicitan+" NATURAL INNER JOIN "+tPedidos+" WHERE "+PedidoValue.cFechaPedido+"=TO_DATE('"+fechaPedido+"', 'YYYY-MM-DD'))";
			}
			if(fechaLlegada!=null){
				consulta+=" AND "+RecursoValue.cIdRecurso+" IN(SELECT "+RecursoValue.cIdRecurso+" FROM "+tSolicitan+" NATURAL INNER JOIN "+tPedidos+" WHERE "+PedidoValue.cFechaLlegada+"=TO_DATE('"+fechaLlegada+"', 'YYYY-MM-DD'))";
			}
		}
		return consulta;
	}
	
	/**
	 * Metodo encargado de consultar en la base de datos la existencia de un producto, dado un rango de cantidad,
	 * un proceso de producción, una fecha de solicitud y fecha de entrega.
	 * @param rInferior El limite inferior del rango.
	 * @param rSuperior El limite superior del rango.
	 * @param idProcesoProduccion El id del proceso de producción.
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return Una lista con los productos que cumplen con los parametros.
	 * @throws Exception Si hay algun problema en la conexion o si no hay existencias disponibles.
	 */
	public ArrayList<ProductoValue> consultarExistenciasProductos(int rInferior, int rSuperior, int idProcesoProduccion, String fechaSolicitud, String fechaEntrega) throws Exception
	{
		ArrayList<ProductoValue> productos = new ArrayList<ProductoValue>();
		PreparedStatement selStmt = null;
		try{
			String consulta	= generarConsultaExistenciasProductos(rInferior, rSuperior, idProcesoProduccion, fechaSolicitud, fechaEntrega);
			establecerConexion(cadenaConexion, usuario, clave);
			selStmt = conexion.prepareStatement(consulta);
			ResultSet rs = selStmt.executeQuery();
			
			while(rs.next())
			{
				ProductoValue producto = new ProductoValue();
				producto.setIdProducto(rs.getInt(ProductoValue.cIdProducto));
				producto.setNombre(rs.getString(ProductoValue.cNombre));
				producto.setCosto(rs.getFloat(ProductoValue.cCosto));
				producto.setUnidadesProducidas(rs.getInt(ProductoValue.cUnidadesVendidas));
				producto.setUnidadesEnProduccion(rs.getInt(ProductoValue.cUnidadesEnProduccion));
				producto.setUnidadesVendidas(rs.getInt(ProductoValue.cUnidadesVendidas));
				producto.setCantidadEnBodega(rs.getInt(ProductoValue.cCantidadEnBodega));
				producto.setIdEmpresa(rs.getInt(ProductoValue.cIdEmpresa));
				producto.setIdProcesoProduccion(rs.getInt(ProductoValue.cIdProcesoProduccion));
				productos.add(producto);
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
		return productos;
	}
	
	/**
	 * Metodo encargado en generar el query de la consulta de existencias.
	 * @param rInferior El limite inferior del rango.
	 * @param rSuperior El limite superior del rango.
	 * @param idProcesoProduccion El id del proceso de produccion.
	 * @param fechaSolicitud La fecha de solicitud.
	 * @param fechaEntrega La fecha de entrega.
	 * @return El query respectivo a la solicitud.
	 */
	private String generarConsultaExistenciasProductos(int rInferior, int rSuperior, int idProcesoProduccion, String fechaPedido, String fechaLlegada){
		String consulta = "SELECT * FROM "+tProductos+" WHERE "+EmpresaValue.cIdEmpresa+"="+idEmpresaF+" AND "+ProductoValue.cfCantidadEnBodega+">0";
		if(rInferior>0 && rInferior<rSuperior){
			consulta+=" AND "+ProductoValue.cfCantidadEnBodega+" BETWEEN "+rInferior+" AND "+rSuperior;
		}
		if(idProcesoProduccion>0){
			consulta+=" AND "+ProcesoProduccionValue.cIdProcesoProduccion+"="+idProcesoProduccion;
		}
		if(fechaPedido!=null){
			consulta+=" AND "+ProductoValue.cIdProducto+" IN(SELECT "+ProductoValue.cIdProducto+" FROM "+tCompran+" NATURAL INNER JOIN "+tPedidos+" WHERE "+PedidoValue.cFechaPedido+"=TO_DATE('"+fechaPedido+"', 'YYYY-MM-DD'))";
		}
		if(fechaLlegada!=null){
			consulta+=" AND "+ProductoValue.cIdProducto+" IN(SELECT "+ProductoValue.cIdProducto+" FROM "+tCompran+" NATURAL INNER JOIN "+tPedidos+" WHERE "+PedidoValue.cFechaLlegada+"=TO_DATE('"+fechaLlegada+"', 'YYYY-MM-DD'))";
		}
		return consulta;
	}
	
	/**
	 * Metodo encargado de buscar un material segun los parametros.
	 * @param volumen El volumen que se quiere buscar.
	 * @param desde Desde cuando se quiere buscar.
	 * @param hasta Hasta cuando se quiere buscar.
	 * @param costo El costo de un material.
	 * @return Una lista con materiales.
	 * @throws Exception Si hay un error en alguna parte del proceso
	 */
	public ArrayList<MaterialValue> consultarRecurso(int volumen, String desde, String hasta, Float costo) throws Exception
	{
		ArrayList<MaterialValue> materiales= new ArrayList<MaterialValue>();
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		PreparedStatement selStmt3 = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT distinct * FROM "+tTienen+" t NATURAL JOIN "+tRecursos+" r NATURAL JOIN "+tPedidos+" p WHERE t.IDEMPRESA="+idEmpresaF;
			if(volumen>0)
			{
				queryConsulta+=" AND t."+RecursoValue.cfCantidadEnBodega+"="+volumen;
			}
			if(costo>0)
			{
				queryConsulta+=" AND p."+PedidoValue.cMonto+"="+costo;
			}
			if(!desde.equals("")&&!hasta.equals(""))
			{
				queryConsulta+=" AND (p."+PedidoValue.cFechaLlegada+">TO_DATE('"+desde+"','YYYY-MM-DD') OR p."+PedidoValue.cFechaLlegada+"<TO_DATE('"+hasta+"','YYYY-MM-DD'))";
			}
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			
			while(rs.next())
			{
				MaterialValue m= new MaterialValue();
				RecursoValue recurso = new RecursoValue();
				recurso.setIdRecurso(rs.getInt(RecursoValue.cIdRecurso));
				recurso.setNombre(rs.getString(RecursoValue.cNombre));
				recurso.setTipoRecurso(rs.getString(RecursoValue.cTipoRecurso));
				recurso.setUnidadMedida(rs.getString(RecursoValue.cUnidadMedida));
				recurso.setIdProveedor(rs.getInt(RecursoValue.cIdProveedor));
				recurso.setCantidadMDistribucion(rs.getInt(RecursoValue.cCantidadMDistribucion));
				recurso.setTiempoEntrega(rs.getInt(RecursoValue.cTiempoEntrega));
				m.setRecurso(recurso);
				
				String queryConsulta2 = "SELECT * FROM "+tProductos+" p NATURAL JOIN "+tEtapasProduccion+" e NATURAL JOIN "+tRecursos;	 
				selStmt2 = conexion.prepareStatement(queryConsulta2);
				ResultSet rs2 = selStmt2.executeQuery();
			
				while(rs2.next())
				{
					m.agregarEtapasProduccion(""+rs2.getInt(EtapaProduccionValue.cIdEtapaProduccion));
					m.agregarProductos(rs2.getString(ProductoValue.cNombre));
				}
				
				String queryConsulta3 = "SELECT * FROM "+tRecursos+" r NATURAL JOIN "+tPedidos+" p";
				selStmt3 = conexion.prepareStatement(queryConsulta3);
				ResultSet rs3 = selStmt3.executeQuery();
				while(rs3.next())
				{
					m.agregarPedidos(rs3.getString(PedidoValue.cIdPedido));
				}
				
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
			if (selStmt2 != null) 
			{
				try
				{
					selStmt2.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt3 != null) 
			{
				try
				{
					selStmt3.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}
	
	/**
	 * Metodo encardo de buscar un proucto segun los parametros
	 * @param volumen El volumen que se quiere buscar
	 * @param costo El costo de un material.
	 * @return Una lista con materiales.
	 * @throws Exception Si hay un error en alguna parte del proceso
	 */
	public ArrayList<MaterialValue> consultarProducto(int cantidad, float costo) throws Exception 
	{
		ArrayList<MaterialValue> materiales= new ArrayList<MaterialValue>();
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		PreparedStatement selStmt3 = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT "+ProductoValue.cIdProcesoProduccion +", "+ProductoValue.cNombre+", "+ProductoValue.cIdProducto+" FROM "+tProductos+" WHERE ";
			if(cantidad>0)
			{
				queryConsulta+= ProductoValue.cCantidadEnBodega+"="+cantidad;
			}
			if(costo>0)
			{
				queryConsulta+=" AND "+ProductoValue.cCosto+"="+costo;
			}
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			while(rs.next())
			{
				String queryConsulta2 = "SELECT p."+PedidoValue.cIdPedido +" FROM "+tCompran+" c, "+tPedidos+" p WHERE p."+PedidoValue.cIdPedido+"=c."+PedidoValue.cIdPedido+" AND c."+ProductoValue.cIdProducto+"="+rs.getInt(ProductoValue.cIdProducto);
				selStmt2 = conexion.prepareStatement(queryConsulta2);
				ResultSet rs2 = selStmt2.executeQuery();
				MaterialValue m= new MaterialValue();
			
				while(rs2.next())
				{
					m.agregarPedidos(""+rs2.getInt(PedidoValue.cIdPedido));
				}
				String queryConsulta3 = "SELECT r."+RecursoValue.cNombre +" FROM "+tEtapasProduccion+" e NATURAL JOIN "+tRequieren+" re NATURAL JOIN "+tRecursos+" r WHERE e."+ProductoValue.cIdProcesoProduccion+"="+rs.getInt(ProductoValue.cIdProcesoProduccion);
				selStmt3 = conexion.prepareStatement(queryConsulta3);
				ResultSet rs3 = selStmt3.executeQuery();
				while(rs3.next())
				{
					m.agregarRecursoQueLoCompone(rs3.getString(RecursoValue.cNombre));
				}
				
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
			if (selStmt2 != null) 
			{
				try
				{
					selStmt2.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt3 != null) 
			{
				try
				{
					selStmt3.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}

	//TODO:
	public EmpleadoValue consultarOperarioMasActivo() throws Exception{
		EmpleadoValue r=null;
		PreparedStatement selStmt = null;
		try
		{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT FROM "+tEmpleados+"";
			return null;
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
	
	//TODO:
	public void registrarProducto(int idProducto, String nombre, float costo){}
	
	public void registrarUsuario(int id, String clave, String tipoUsuario,
			String cedula, String nombre, String nacionalidad, String direccionFisica,
			String telefono, String email, String departamento, String ciudad, String codigoPostal,
			int idRepresentanteLegal, int registro_volumen_idEmpresa, String tipoEmpleado, long sueldo) throws Exception{
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String query="INSERT INTO "+tUsuarios+"("+UsuarioValue.cId+","+UsuarioValue.cTipoUsuario+","+UsuarioValue.cClave+","+UsuarioValue.cCedula+","+UsuarioValue.cNombre+","+
			UsuarioValue.cNacionalidad+","+UsuarioValue.cDireccionFisica+","+UsuarioValue.cTelefono+","+UsuarioValue.cEmail+","+
			UsuarioValue.cDepartamento+","+UsuarioValue.cCiudad+","+UsuarioValue.cCodigoPostal+") VALUES ("+id+",'"+tipoUsuario+"','"+clave+"','"+cedula+"','"+nombre+
			"','"+nacionalidad+"','"+direccionFisica+"','"+telefono+"','"+email+"','"+departamento+"','"+ciudad+"','"+codigoPostal+"')";
			stmt=conexion.prepareStatement(query);
			stmt.executeQuery();
			if(tipoUsuario.equals(UsuarioValue.cliente)){
				String query2="INSERT INTO"+tClientes+"("+ClienteValue.cId+","+ClienteValue.cIdRepresentanteLegal+","+ClienteValue.cRegistroSINV+")"
						+ " VALUES ("+id+","+idRepresentanteLegal+","+registro_volumen_idEmpresa+")";
				stmt2=conexion.prepareStatement(query2);
				stmt2.executeQuery();
			}
			else if(tipoUsuario.equals(UsuarioValue.proveedor)){
				String query2="INSERT INTO"+tProveedores+"("+ProveedorValue.cId+","+ProveedorValue.cIdRepresentanteLegal+","+ProveedorValue.cVolumenMaximo+")"
						+ " VALUES ("+id+","+idRepresentanteLegal+","+registro_volumen_idEmpresa+")";
				stmt2=conexion.prepareStatement(query2);
				stmt2.executeQuery();
			}
			else if(tipoUsuario.equals(UsuarioValue.empleado)){
				String query2="INSERT INTO"+tEmpleados+"("+EmpleadoValue.cId+","+EmpleadoValue.cSueldo+","+EmpleadoValue.cTipoEmpleado+","+EmpleadoValue.cfIdEmpresa+")"
						+ " VALUES ("+id+","+sueldo+","+tipoEmpleado+","+registro_volumen_idEmpresa+")";
				stmt2=conexion.prepareStatement(query2);
				stmt2.executeQuery();
			}
			conexion.commit();
		}
		catch (SQLException e){
			conexion.rollback();
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (stmt != null){
				try{
					stmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (stmt2 != null){
				try{
					stmt2.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}
	
	/**
	 * Metodo encargado de registrar la llegada de un recurso.
	 * @param idRecurso El id del recurso deseado.
	 * @param idPedido El id del pedido al que pertenece el recurso.
	 * @param fechaLlegada La fecha de llegada del producto.
	 * @throws Exception Si hay un error en alguna parte del proceso.
	 */
	public void registrarLlegadaRecurso(int idRecurso, int idPedido, String fechaLlegada) throws Exception
	{
		PreparedStatement updStmt = null;
		PreparedStatement selStmt = null;
		PreparedStatement stmt = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryPedido = "UPDATE "+tPedidos+" p SET p."+PedidoValue.cFechaLlegada+"=TO_DATE('"+fechaLlegada+"','YYYY-MM-DD'), p."+PedidoValue.cEstado+"='Terminado' WHERE p."+PedidoValue.cIdPedido+"="+idPedido+"";
			String queryConsulta = "SELECT * FROM "+tTienen+" t WHERE t."+EmpresaValue.cIdEmpresa+"="+idEmpresaF+" AND t."+RecursoValue.cIdRecurso+"="+idRecurso;
			String queryTienen = null;
			updStmt = conexion.prepareStatement(queryPedido);
			updStmt.executeQuery();
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			if(rs.next()){
				queryTienen="UPDATE "+tTienen+" t SET t."+RecursoValue.cfCantidadEnBodega+"=t."+RecursoValue.cfCantidadEnBodega+"+(SELECT p.cantidad FROM "+tPedidos+" p WHERE p."+PedidoValue.cIdPedido+"="+idPedido+")"
						+ "WHERE t."+EmpresaValue.cIdEmpresa+"="+idEmpresaF+" AND t."+RecursoValue.cIdRecurso+"="+idRecurso+"";
				stmt=conexion.prepareStatement(queryTienen);
				stmt.executeQuery();
			}	
			else{
				queryTienen="INSERT INTO "+tTienen+"("+EmpresaValue.cIdEmpresa+","+RecursoValue.cIdRecurso+","+RecursoValue.cfCantidadEnBodega+") VALUES ("+idEmpresaF+","+idRecurso+", "
						+ "(SELECT p."+PedidoValue.cCantidad+" FROM "+tPedidos+" p WHERE p."+PedidoValue.cIdPedido+"="+idPedido+"))";
				stmt=conexion.prepareStatement(queryTienen);
				stmt.executeQuery();
			}
			conexion.commit();
		}
		catch (SQLException e){
			conexion.rollback();
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (updStmt != null){
				try{
					updStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt != null){
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (stmt != null){
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
	
	/**
	 * Metodo encargado de registrar en la base de datos la entrega de un pedido.
	 * @param idCliente EL cliente al que se le realiza la entrega.
	 * @param idProducto El id del producto que esta en el pedido.
	 * @param idPedido El id del pedido a entregar.
	 * @param fechaLlegada La fecha de llegada del pedido.
	 * @throws Exception Si ocurre algún error durante el proceso.
	 */
	public void registrarEntregaPedido(int idProducto,int idPedido, String fechaLlegada) throws Exception
	{
		PreparedStatement updPedStmt = null;
		PreparedStatement updProdStmt = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryPedido = "UPDATE "+tPedidos+" p SET p."+PedidoValue.cFechaLlegada+"=TO_DATE('"+fechaLlegada+"','YYYY-MM-DD'), p."+PedidoValue.cEstado+"='Terminado' WHERE p."+PedidoValue.cIdPedido+"="+idPedido;
			updPedStmt = conexion.prepareStatement(queryPedido);
			updPedStmt.executeQuery();
			String queryProducto = "UPDATE "+tProductos+" p SET p."+RecursoValue.cfCantidadEnBodega+"=p."+RecursoValue.cfCantidadEnBodega+"-(SELECT p.cantidad FROM "+tPedidos+" p WHERE p."+PedidoValue.cIdPedido+"="+idPedido+"),"
					+ "p."+ProductoValue.cUnidadesVendidas+"=p."+ProductoValue.cUnidadesVendidas+"+(SELECT p."+PedidoValue.cCantidad+" FROM "+tPedidos+" p WHERE p."+PedidoValue.cIdPedido+"="+idPedido+") WHERE p."+
					ProductoValue.cIdProducto+"="+idProducto;
			updProdStmt = conexion.prepareStatement(queryProducto);
			updProdStmt.executeQuery();
			conexion.commit();
		}
		catch (SQLException e){
			conexion.rollback();
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (updPedStmt != null){
				try{
					updPedStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (updProdStmt != null){
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

	//TODO PRECIO DE RECURSO.
	public void registrarPedidoRecurso(int idRecurso, int idPedido, int cantidad, String fechaEsperada) throws Exception{
		PreparedStatement selStmt = null;
		PreparedStatement insStmt = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT * FROM "+tRecursos+" r WHERE r."+RecursoValue.cIdRecurso+"=="+idRecurso+"";
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			if(rs.next()){
				RecursoValue recurso = new RecursoValue();
				recurso.setIdRecurso(rs.getInt(RecursoValue.cIdRecurso));
				recurso.setNombre(rs.getString(RecursoValue.cNombre));
				recurso.setTipoRecurso(rs.getString(RecursoValue.cTipoRecurso));
				recurso.setUnidadMedida(rs.getString(RecursoValue.cUnidadMedida));
				recurso.setIdProveedor(rs.getInt(RecursoValue.cIdProveedor));
				recurso.setCantidadMDistribucion(rs.getInt(RecursoValue.cCantidadMDistribucion));
				recurso.setTiempoEntrega(rs.getInt(RecursoValue.cTiempoEntrega));
				if(recurso.getCantidadMDistribucion()<cantidad){
					throw new Exception("La cantidad exigida supera la cantidad máxima de distribución del recurso");
				}
				String queryInsert = "INSERT INTO "+tPedidos+"("+PedidoValue.cIdPedido+","+PedidoValue.cCantidad+","+
				PedidoValue.cMonto+","+PedidoValue.cFechaPedido+","+PedidoValue.cFechaEsperada+")"
				+ " VALUES ("+idPedido+","+cantidad+","+0+","+darFechaActualFormato()+","+fechaEsperada+")";
				insStmt = conexion.prepareStatement(queryInsert);
				insStmt.executeQuery();
				conexion.commit();
			}
			else{
				throw new Exception("No existe ningún recurso con el id especificado");
			}
		}
		catch (SQLException e){
			conexion.rollback();
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null){
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (insStmt != null){
				try{
					insStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}
	
	/**
	 * Metodo encargado de registrar la solicitud de un pedido.
	 * @param idCliente El id del cliente que realiza el pedido.
	 * @param idProducto El id del producto.
	 * @param fechaEntrega La fecha de entrega.
	 * @param cantidad La cantidad de elementos compro.
	 * @throws Exception Si ocurre algún error en el proceso.
	 */
	public void solicitarPedido(int idCliente, int idProducto, String fechaEntrega, int cantidad) throws Exception 
	{
		PreparedStatement insStmt = null;
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryConsulta = "SELECT p."+ProductoValue.cCosto+" FROM "+tProductos+" p WHERE p."+ProductoValue.cIdProducto+"="+idProducto;
			selStmt = conexion.prepareStatement(queryConsulta);
			ResultSet rs = selStmt.executeQuery();
			if(rs.next()){
				Float costo=rs.getFloat(ProductoValue.cCosto);
				float monto= (float) costo*cantidad;
				String queryConsulta2 = "SELECT * FROM "+tTienen+" t,(SELECT r."+RecursoValue.cIdRecurso+",r.cantidad FROM "+tRequieren+" r,etapasProduccion e,procesosProduccion pr WHERE "
						+ "pr."+ProductoValue.cIdProducto+"="+idProducto+" AND e."+ProcesoProduccionValue.cIdProcesoProduccion+"=pr."+ProcesoProduccionValue.cIdProcesoProduccion+" AND e."+EtapaProduccionValue.cIdEtapaProduccion+"=r."+EtapaProduccionValue.cIdEtapaProduccion+") n "
								+ "WHERE t."+RecursoValue.cIdRecurso+"=n."+RecursoValue.cIdRecurso+" AND n.cantidad*"+cantidad+"<=t."+RecursoValue.cfCantidadEnBodega+"";
				selStmt2 = conexion.prepareStatement(queryConsulta2);
				ResultSet rs2 = selStmt2.executeQuery();
				if(rs2.next()){
					establecerConexion(cadenaConexion, usuario, clave);
					String queryInsert ="INSERT INTO "+tPedidos+" (cantidad,"+PedidoValue.cMonto+","+PedidoValue.cFechaPedido+",fechaEsperada) VALUES ("+cantidad+","+monto+",TO_DATE('"+darFechaActualFormato()+"','YYYY-MM-DD'), TO_DATE('"+fechaEntrega+"','YYYY-MM-DD')))";
					insStmt = conexion.prepareStatement(queryInsert);
					insStmt.executeQuery();
				}
				else{
					throw new Exception("No se pudo realizar el pedido del producto, porque no se cuenta con los recursos necesarios. EL pedido se archivara para luego de tener los recursos informar sobre la posibilidad de realizar la solicitud");
				}
			}
			else{
				throw new Exception("No existe el producto");
			}
		}
		catch (SQLException e){
			conexion.rollback();
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (selStmt != null){
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt2 != null){
				try{
					selStmt2.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (insStmt != null) {
				try{
					insStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}
	
	public void cancelarPedidoCliente(int idPedido) throws Exception
	{
		//Se declaran los statement a utilizar en el método
		PreparedStatement selStmt = null;
		PreparedStatement delStmt = null;
		PreparedStatement delStmt2 = null;
		try{
			//Se establece la conexion a la base de datos
			establecerConexion(cadenaConexion, usuario, clave);
			//Se selecciona el pedido con el id específicado por parámetro si este se encuentra pendiente
			String querySelect = "SELECT * FROM COMPRAN NATURAL INNER JOIN PEDIDOS WHERE ESTADO='Pendiente' AND IDPEDIDO="+idPedido;
			//Se prepara el query para eliminar el pedido de la tabla Compran
			String queryDelete = "DELETE FROM "+tCompran+" c WHERE c."+PedidoValue.cIdPedido+"="+idPedido+"";
			//Se prepara el query para eliminar el pedido de la tabla Pedidos
			String queryDelete2 = "DELETE FROM "+tPedidos+" p WHERE p."+PedidoValue.cIdPedido+"="+idPedido+"";
			selStmt=conexion.prepareStatement(querySelect);
			ResultSet rs = selStmt.executeQuery();
			//Si el pedido que se desea cancelar no está pendiente se notifica error
			if(!rs.next()){
				throw new Exception("No se puede cancelar el pedido, bien porque no existe o porque el pedido ya fue entregado");
			}
			//Se ejecutan los cambios a las tablas
			delStmt = conexion.prepareStatement(queryDelete);
			delStmt.executeQuery();
			delStmt2 = conexion.prepareStatement(queryDelete2);
			delStmt2.executeQuery();
			//Se consolidan los cambios
			conexion.commit();
		}
		//Si ocurre algún problema se hace rollback de la transacción
		catch (SQLException e){
			conexion.rollback();
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		//Se cierran los statement y finalmente la conexión
		finally{
			if (delStmt != null){
				try{
					delStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (delStmt2 != null){
				try{
					delStmt2.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}


	@SuppressWarnings("deprecation")
	private String darFechaActualFormato(){
		Date hoy = new Date();
		return (hoy.getYear()+1900)+"-"+(hoy.getMonth()+1)+"-"+hoy.getDate();
	}

	public void reportarCambioEstadoEstacionProduccion(int idEstacionProduccion, String estado) throws Exception{
		//Se crean los statement que serán utilizados en el método
		PreparedStatement updStmt=null;
		PreparedStatement selStmt=null;
		try
		{
			//Se establece la conexión a la base de datos
			establecerConexion(cadenaConexion, usuario, clave);
			//Se selecciona la estación de producción a la que se le cambiará el estado, si esta tiene el mismo estado se lanza una excepción dado que no tiene sentido
			//cambiar el estado de la estación al mismo que tiene al momento de la ejecución del presente método.
			String querySelect="SELECT * FROM ESTACIONESPRODUCCION WHERE idEstacionProduccion="+idEstacionProduccion+" AND ESTADO='"+estado+"'";
			selStmt = conexion.prepareStatement(querySelect);
			ResultSet rs = selStmt.executeQuery();
			if(rs.next()){
				throw new Exception("La estación de producción ya se encuentra en ese estado");
			}
			//Si la estación no tenía el estado se puede cambiar este atributo. El query a continuación se encarga de ello.
			String queryUpdate="UPDATE "+tEstacionesProduccion+" e SET e."+EstacionProduccionValue.cEstado+"= '"+estado+"' WHERE idEstacionProduccion="+idEstacionProduccion;
			updStmt = conexion.prepareStatement(queryUpdate);
			updStmt.executeQuery();
			//Una vez se cambia el estado de la estación de producción se realiza la operación de balanceo de carga.
			balancearCarga(idEstacionProduccion, estado);
			//Se consolidan los cambios
			conexion.commit();
		}
		catch (SQLException e){
			//Si ocurre algún error durante la ejecución de los querys del presente método, o algún error en el balanceo de carga se realiza rollback
			conexion.rollback();
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			//Se cierran los statement y finalmente se cierra la conexión
			if (selStmt != null){
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (updStmt != null){
				try{
					updStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}

	/**
	 * Metodo encargado de registrar la ejecucion en la base de datos.
	 * @param idEtapaProduccion la etapa de produccion.
	 * @param idOperario El ide del operario
	 * @param fechaEjecucion La fecha de ejecucion
	 * @param duracion es la duracion de la etapa
	 * @throws Exception Si hay un error en alguna parte del proceso
	 */
	public void registrarEjecucionEtapaProduccion(int idEtapaProduccion, int idOperario, String fechaEjecucion, int duracion) throws Exception
	{
		PreparedStatement insStmt=null;
		PreparedStatement selStmt=null;
		PreparedStatement selEStmt=null;
		PreparedStatement updStmt=null;
		try{
			establecerConexion(cadenaConexion, usuario, clave);
			String queryInsert="INSERT INTO "+tOperan+"(idOperario,"+EtapaProduccionValue.cIdEtapaProduccion+",fechaEjecucion,duracion) VALUES("+idEtapaProduccion+","+idOperario+",TO_DATE('"+fechaEjecucion+"','YYYY-MM-DD'),"+duracion+")";
			String querySelectE="SELECT * FROM "+tEtapasProduccionPedido+" e WHERE e."+EtapaProduccionValue.cIdEtapaProduccion+"="+idEtapaProduccion+" AND e.idAnteriorEtapa IS NULL";
			String querySelect="SELECT * FROM "+tEtapasProduccionPedido+" e WHERE e."+EtapaProduccionValue.cIdEtapaProduccion+"=(SELECT idAnteriorEtapa FROM "+tEtapasProduccionPedido+" et WHERE et."+EtapaProduccionValue.cIdEtapaProduccion+"="+idEtapaProduccion+") AND e."+PedidoValue.cEstado+"='Terminado'";
			String queryUpdate="UPDATE "+tTienen+" t SET t.cantidad=t.cantidad-(SELECT req.cantidad FROM "+tRequieren+" req WHERE req."+EtapaProduccionValue.cIdEtapaProduccion+"="+idEtapaProduccion+")";
			selEStmt = conexion.prepareStatement(querySelectE);
			ResultSet rs = selEStmt.executeQuery();
			if(rs.next()){
				insStmt=conexion.prepareStatement(queryInsert);
				insStmt.executeQuery();
				updStmt=conexion.prepareStatement(queryUpdate);
				updStmt.executeQuery();
				conexion.commit();
			}
			else{
				selStmt=conexion.prepareStatement(querySelect);
				ResultSet rs2=selStmt.executeQuery();
				if(rs2.next()){
					throw new Exception("Aun no se han terminado las etapas anteriores a esta");
				}
				else{
					insStmt=conexion.prepareStatement(queryInsert);
					insStmt.executeQuery();
					updStmt=conexion.prepareStatement(queryUpdate);
					updStmt.executeQuery();
					conexion.commit();
				}
			}
		}
		catch (SQLException e){
			conexion.rollback();
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
		finally{
			if (insStmt != null){
				try{
					insStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (selStmt != null){
				try{
					selStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			if (updStmt != null) {
				try{
					updStmt.close();
				} 
				catch (SQLException exception){
					throw new Exception("ERROR: ConsultaDAO: loadRow() =  cerrando una conexion.");
				}
			}
			closeConnection(conexion);
		}
	}

	private void balancearCarga(int idEstacionProduccion, String estado) throws Exception
	{
		//Se declaran los statement utilizados en el método
		PreparedStatement delStmt = null;
		PreparedStatement selStmt = null;
		PreparedStatement selStmt2 = null;
		PreparedStatement insStmt = null;
		try
		{
			//Si la estación fue activada se distribuye la carga para asignarle etapas para ejecutar
			if(estado.equals(EstacionProduccionValue.activa))
			{
				//Se seleccionan todas las etapas de producción a ejecutar y todas las estaciones de producción disponibles
				int j=0;
				ArrayList<Integer> etapasProduccion = new ArrayList<Integer>();
				ArrayList<Integer> estacionesProduccion = new ArrayList<Integer>();
				String querySelect = "Select idEtapaProduccion FROM "+tEjecutan+" order by idEtapaProduccion";
				selStmt = conexion.prepareStatement(querySelect);
				ResultSet rs = selStmt.executeQuery();
				//Se agregan todas las etapas de producción pendientes y asignadas a una estación de producción a un arreglo
				while(rs.next())
				{
					etapasProduccion.add(rs.getInt("idEtapaProduccion"));
				}	
				//Se eliminan todas las ejecuciones pendientes
				String queryDelete = "TRUNCATE TABLE "+tEjecutan;
				delStmt = conexion.prepareStatement(queryDelete);
				delStmt.executeQuery();
				//Se seleccionan todas las estaciones de producción disponibles 
				querySelect = "Select IDESTACIONPRODUCCION FROM "+tEstacionesProduccion+" WHERE estado='"+EstacionProduccionValue.activa+"'";
				selStmt2 = conexion.prepareStatement(querySelect);
				ResultSet rs2 = selStmt2.executeQuery();
				while(rs2.next())
				{
					estacionesProduccion.add(rs2.getInt("idEstacionProduccion"));
				}
				//En el siguiente ciclo se le asigna a cada estación una etapa de producción hasta que todas las etapas de producción sean asignadas equitativamente
				for(int i=0;i<estacionesProduccion.size() && j<etapasProduccion.size();i++)
				{
					String queryInsert = "INSERT INTO "+tEjecutan+"(idEstacionProduccion, idEtapaProduccion) VALUES ("+estacionesProduccion.get(i)+","+etapasProduccion.get(j)+")";
					insStmt = conexion.prepareStatement(queryInsert);
					insStmt.executeQuery();
					j++;
					if(i==estacionesProduccion.size()-1 && j<etapasProduccion.size())
					{
						i=-1;
					}
				}
			}
			//Si la estación fue desactivada se distribuyen todas las etapas de producción que le estaban asignadas a las otras estaciones disponibles
			else if(estado.equals(EstacionProduccionValue.inactiva))
			{
				ArrayList<Integer> estacionesProduccion = new ArrayList<Integer>();
				int i = 0;
				//Se seleccionan todas las etapas de producción que estaban asignadas a la estación de producción 
				String querySelect = "SELECT idEtapaProduccion FROM "+tEjecutan+" e WHERE e.idEstacionProduccion="+idEstacionProduccion;
				//Se seleccionan todas las estaciones de producción que se encuentran disponibles
				String querySelect2 = "SELECT idEstacionProduccion FROM "+tEstacionesProduccion+" e WHERE e."+EstacionProduccionValue.cEstado+"='"+EstacionProduccionValue.activa+"'";
				//Se eliminan todas las ejecuciones pendientes a la estación de producción que se va a desactivar
				String queryDelete = "DELETE FROM "+tEjecutan+" e WHERE e.idEstacionProduccion="+idEstacionProduccion;
				selStmt = conexion.prepareStatement(querySelect);
				ResultSet rs = selStmt.executeQuery();
				delStmt = conexion.prepareStatement(queryDelete);
				delStmt.executeQuery();
				selStmt2 = conexion.prepareStatement(querySelect2);
				ResultSet rs2 = selStmt2.executeQuery();
				//Se agregan las estaciones de producciones disponibles a un arreglo
				while(rs2.next()){
					estacionesProduccion.add(rs2.getInt("idEstacionProduccion"));
				}
				//A todas las estaciones disponibles se les asignan las etapas de producción que le estaban asignada a estación de producción desactivada en un ciclo
				while(rs.next()){
					String queryInsert = "INSERT INTO "+tEjecutan+"(idEstacionProduccion, idEtapaProduccion) VALUES ("+estacionesProduccion.get(i)+","+rs.getInt("idEtapaProduccion")+")";
					insStmt = conexion.prepareStatement(queryInsert);
					insStmt.executeQuery();
					i++;
					if(i==estacionesProduccion.size()){
						i=0;
					}
				}
			}
		}
		//Si ocurre algún error se lanza la excepción, que es atrapada por el método registrar cambio de estado y éste último realiza el rollback de la transacción
		catch (SQLException e)
		{
			e.printStackTrace();
			throw new Exception("ERROR = ConsultaDAO: loadRowsBy(..) Agregando parametros y executando el statement");
		}
	}
}