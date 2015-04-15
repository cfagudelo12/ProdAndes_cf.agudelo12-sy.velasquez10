package servlets;


import java.io.IOException;
import java.io.PrintWriter;



import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.system.server.ServerConfig;
import org.jboss.system.server.ServerConfigLocator;

import vos.ClienteValue;
import vos.MaterialValue;
import vos.PedidoValue;
import vos.ProductoValue;
import vos.ProveedorValue;
import vos.RecursoValue;
import vos.UsuarioValue;
import fachada.ProdAndes;



public class ServletAdministrador extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<PedidoValue>listaConsultarPedidos;
	private ArrayList<ClienteValue>listaConsultarClientes;
	private ArrayList<ProveedorValue>listaConsultarProveedores;
	
	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Inicialización del Servlet
	 */
	public void init( ) throws ServletException
	{
		listaConsultarPedidos=new ArrayList<PedidoValue>();
		listaConsultarClientes= new ArrayList<ClienteValue>();
		listaConsultarProveedores = new ArrayList<ProveedorValue>();
	}

	public void destroy( )
	{
		System.out.println("Destruyendo instancia");
		
	}


	/**
	 * Maneja un pedido GET de un cliente
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 */
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// Maneja el GET y el POST de la misma manera
		procesarSolicitud( request, response );
	}

	/**
	 * Maneja un pedido POST de un cliente
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
	{
		// Maneja el GET y el POST de la misma manera
		procesarSolicitud( request, response );
	}

	/**
	 * Procesa el pedido de igual manera para todos
	 * @param request Pedido del cliente
	 * @param response Respuesta
	 * @throws IOException Excepción de error al escribir la respuesta
	 */
	private void procesarSolicitud( HttpServletRequest request, HttpServletResponse response ) throws IOException
	{
		// Envía a la respuesta el encabezado del template
		imprimirEncabezado( response );

		String administrador = request.getParameter( "administrador" );
		String consultarPedidosPorId = request.getParameter( "consultarPedidosPorId");
		String consultarPedidos = request.getParameter( "consultarPedidos");
		String consultarPedidosPorMonto = request.getParameter( "consultarPedidosPorMonto");
		String consultarClientes = request.getParameter( "consultarClientes" );
		String consultarClientesPorId = request.getParameter( "consultarClientesPorId" );
		String consultarClientesPorEmail = request.getParameter( "consultarClientesPorEmail" );
		String consultarProveedores = request.getParameter( "consultarProveedores" );
		String consultarProveedoresPorId = request.getParameter( "consultarProveedoresPorId" );
		String consultarProveedoresPorNombre = request.getParameter( "consultarProveedoresPorNombre" );
		
		if(administrador!=null)
		{
			try
			{
				imprimirPaginaAdministrador(response);
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			}
		}
		else if(consultarPedidosPorId!=null)
		{
			try
			{
				String idPedido = request.getParameter( "idPedido" );
			
				listaConsultarPedidos=ProdAndes.darInstancia().consultarPedidoPorId(Integer.parseInt(idPedido));
				imprimirPaginaAdministrador(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(consultarPedidosPorMonto!=null)
		{
			try
			{
				String idPedido = request.getParameter( "idPedido" );
			
				listaConsultarPedidos=ProdAndes.darInstancia().consultarPedidosPorMonto();

				imprimirPaginaAdministrador(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(consultarPedidos!=null)
		{
			try
			{
				String idPedido = request.getParameter( "idPedido" );
			
				listaConsultarPedidos=ProdAndes.darInstancia().consultarPedidos(Integer.parseInt(idPedido));
				imprimirPaginaAdministrador(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(consultarClientes!=null)
		{
			try
			{
				String idCliente = request.getParameter( "idCliente" );
			
				listaConsultarClientes=ProdAndes.darInstancia().consultarClientes(Integer.parseInt(idCliente));
				imprimirPaginaAdministrador(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(consultarClientesPorId!=null)
		{
			try
			{
				String idCliente = request.getParameter( "idCliente" );
			
				listaConsultarClientes=ProdAndes.darInstancia().consultarClientePorId(Integer.parseInt(idCliente));
				imprimirPaginaAdministrador(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(consultarClientesPorEmail!=null)
		{
			try
			{
				String idCliente = request.getParameter( "idCliente" );
			
				listaConsultarClientes=ProdAndes.darInstancia().consultarClientesPorEmail(Integer.parseInt(idCliente));
				imprimirPaginaAdministrador(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(consultarProveedores!=null)
		{
			try
			{
				String idProveedor = request.getParameter( "idProveedor" );
			
				listaConsultarProveedores=ProdAndes.darInstancia().consultarProveedores(Integer.parseInt(idProveedor));
				imprimirPaginaAdministrador(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(consultarProveedoresPorId!=null)
		{
			try
			{
				String idProveedor = request.getParameter( "idProveedor" );
			
				listaConsultarProveedores=ProdAndes.darInstancia().consultarProveedorPorId(Integer.parseInt(idProveedor));
				imprimirPaginaAdministrador(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(consultarProveedoresPorNombre!=null)
		{
			try
			{
				String idProveedor = request.getParameter( "idProveedor" );
			
				listaConsultarProveedores=ProdAndes.darInstancia().consultarProveedorPorNombre(Integer.parseInt(idProveedor));
				imprimirPaginaAdministrador(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		
		
		imprimirfooter(response);
	}
	

	/**
	 * Imprime el encabezado con el diseño de la página
	 * @param response Respuesta
	 * @throws IOException Excepción al imprimir en el resultado
	 */
	private void imprimirEncabezado( HttpServletResponse response) throws IOException
	{
		// Obtiene el flujo de escritura de la respuesta
		PrintWriter respuesta = response.getWriter( );

		// Imprime el encabezado
		respuesta.println( "<html>" );
		respuesta.println( "<head>" );
		respuesta.println( "		<title>ProdAndes Sistemas Transaccionales</title>");
		respuesta.println( "		<meta charset\"UTF-8\">");
		respuesta.println( "		<meta name=\"description\"></meta>");
		respuesta.println( "		<link rel=\"stylesheet\" type=\"text/css\" href=\"css/estilo.css\"/>");
		respuesta.println( "		<meta name\"viewport\" content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">");
		respuesta.println( "	");
		respuesta.println( "		<!-- Bootstrap Core CSS -->");
		respuesta.println( "		    <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">");
		respuesta.println( "");
		respuesta.println( "		    <!-- Custom CSS -->");
		respuesta.println( "		    <link href=\"css/sb-admin.css\" rel=\"stylesheet\">");
		respuesta.println( "		      <!-- Custom Fonts -->");
		respuesta.println( "    		<link href=\"font-awesome/css/font-awesome.min.css\" rel=\"stylesheet\" type=\"text/css\">");
		respuesta.println( "    		 <!-- Morris Charts CSS -->");
		respuesta.println( "    		<link href=\"css/plugins/morris.css\" rel=\"stylesheet\">");
		respuesta.println( "</head>" );
	}
	
	/**
	 * Imprime el cuerpo con el diseño de la página general
	 * @param response Respuesta
	 * @throws IOException Excepción al imprimir en el resultado
	 */
	private void imprimirPaginaAdministrador(HttpServletResponse response) throws IOException
	{
		// Obtiene el flujo de escritura de la respuesta
				PrintWriter respuesta = response.getWriter( );

				// Imprime el encabezado
				respuesta.println( "<body>" );
				respuesta.println( "		 <div id=\"wrapper\">");
				respuesta.println( "");
				respuesta.println( "        <!-- Navbar -->");
				respuesta.println( "        ");
				respuesta.println( "        <nav id=\"fondoAzul\" class=\"navbar navbar-fixed-top navbar-fixed-top\" role=\"navigation\">");
				respuesta.println( "            <!-- Para navegaciÃ³n en celulare	s-->");
				respuesta.println( "            <div  id=\"fondoAzul\" class=\"navbar-header\">");
				respuesta.println( "                <a id=\"fondoAzul\" class=\"navbar-brand\" href=\"index.html\">Empresa</a>");
				respuesta.println( "            </div>");
				respuesta.println( "            <!-- elementos de la parte de arriba del navbar- para pantallas pequeÃ±as -->");
				respuesta.println( "            <div  class=\"collapse navbar-collapse navbar-ex1-collapse\">");
				respuesta.println( "                <ul class=\"nav navbar-nav side-nav\">");
				respuesta.println( "                	<form method=\"GET\" action=\"gerente.htm\">");
				respuesta.println( "                        <li class=\"active\">");
				respuesta.println( "                            <a><i class=\"fa fa-fw fa-dashboard\"></i> <Input id=\"BotonNegro\" type=\"submit\" value=\"Gerente\" id=\"letraBlanca\" name=\"gerente\" > </a>");
				respuesta.println( "                        </li>");
				respuesta.println( "                    </form>");
				respuesta.println( "                     <form method=\"GET\" action=\"operario.htm\">");
				respuesta.println( "                        <li>");
				respuesta.println( "                              <a><i class=\"fa fa-fw fa-bar-chart-o\"></i> <Input id=\"BotonNegro\" type=\"submit\" value=\"Operario\" id=\"letraBlanca\" name=\"operario\" > </a>");
				respuesta.println( "                        </li>");
				respuesta.println( "                    </form>");
				respuesta.println( "                    <form method=\"GET\" action=\"administrador.htm\">");
				respuesta.println( "                        <li>");
				respuesta.println( "                              <a><i class=\"fa fa-fw fa-bar-chart-o\"></i> <Input id=\"BotonNegro\" type=\"submit\" value=\"Administrador\" id=\"letraBlanca\" name=\"administrador\" > </a>");
				respuesta.println( "                        </li>");
				respuesta.println( "                    </form>"); 
				respuesta.println( "                </ul>");
				respuesta.println( "            </div>");
				respuesta.println( "        </nav>");
				respuesta.println( "		");
				respuesta.println( "        <div id=\"page-wrapper\">");
				respuesta.println( "");
				respuesta.println( "            <div class=\"container-fluid\">");
				respuesta.println( "");
				respuesta.println( "                <!-- titulo de la pagina -->");
				respuesta.println( "                <div class=\"row\">");
				respuesta.println( "                    <div class=\"col-lg-12\">");
				respuesta.println( "                        <h1 class=\"page-header\">");
				respuesta.println( "                            Consultas");
				respuesta.println( "                        </h1>");
				respuesta.println( "                        ");
				respuesta.println( "                    </div>");
				respuesta.println( "                </div>");
				respuesta.println( "                 <!--Consultar Pedidos-->");
				respuesta.println( "				<form method=\"GET\" action=\"administrador.htm\">" );
				respuesta.println( "                <div class=\"row\">");
				respuesta.println( "                        <div class=\"panel panel-default\" >");
				respuesta.println( "                            <div class=\"panel-heading\">");
				respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar Pedidos</h3>");
				respuesta.println( "                            </div>");
				respuesta.println( "                            <br>");
				respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
				respuesta.println( "                             	<div class=\"col-lg-2\">");			
				respuesta.println( "                             	<div class=\"col-lg-12\">");
				respuesta.println( "                                    <p><b>Mostrar todos los pedidos </b></p>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-12\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"Consultar\" name=\"consultarPedidos\">");
				respuesta.println( "                                </div>");
				respuesta.println( "                                </div>");
				respuesta.println( "                             	<div class=\"col-lg-6\">");
				respuesta.println( "                             	<div class=\"col-lg-9\">");
				respuesta.println( "                                    <p><b>Buscar por id </b></p>");
				respuesta.println( "                                    <span>Seleccione el pedido: </span>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <br/>");
				respuesta.println( " 									<select name=\"idPedido\"		>");
																		try
																		{
																			ArrayList<ProductoValue> productos=ProdAndes.darInstancia().darPedidosCliente();
																			for(int i=0; i<productos.size();i++)
																			{
																				respuesta.println( "<option value=\""+productos.get(i).getIdPedido()+"\">"+productos.get(i).getNombre()+" - "+productos.get(i).getIdPedido()+"</option>");
																			}
																		}
																		catch (Exception e)
																		{
																			imprimirMensajeError(respuesta,"Error de carga", e.getMessage());
																		}
				respuesta.println( "                                    </select>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-12\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"Consultar\" name=\"consultarPedidosPorId\">");
				respuesta.println( "                                </div>");
				respuesta.println( "                                </div>");
				respuesta.println( "                             	<div class=\"col-lg-4\">");			
				respuesta.println( "                             	<div class=\"col-lg-12\">");
				respuesta.println( "                                    <p><b>Buscar por monto </b></p>");
				respuesta.println( "                                    <span>Escriba el monto: </span>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <br/>");
				respuesta.println( " 									<INPUT type=\"number\" name=\"monto\">");	
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-12\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"Consultar\" name=\"consultarPedidosPorMonto\">");
				respuesta.println( "                                </div>");
				respuesta.println( "                                </div>");
				respuesta.println( "                            </div>");
				respuesta.println( "                       ");
				respuesta.println( "                        </div>");
				respuesta.println( "                </div>");
				respuesta.println( "                <!-- /.row -->");			
				respuesta.println( "				</form>");
				for(int i=0;i<listaConsultarPedidos.size();i++)
				{
					PedidoValue m=listaConsultarPedidos.get(i);
					respuesta.println( "          <div id=\"littleWrap\" class=\"row\">");
					respuesta.println( "                        <div class=\"panel panel-default\" >");
					respuesta.println( "                            <div class=\"panel-heading\">");
					respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i><b> La informaci&#243n del pedido consultado</b></h3>");
					respuesta.println( "                            </div>");
					respuesta.println( "                            <br/>");
					respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <p><b>Productos: </b> ");
																			ArrayList<ProductoValue> listaProductos= m.getProductos();
																			for(int j=0;j<listaProductos.size();j++)
																			{
																				ProductoValue productoActual= listaProductos.get(j);
																				respuesta.println( "<br/>"+productoActual.getNombre());
																			}
					respuesta.println( "									</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Cantidad: </b> "+m.getCantidad()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Monto: </b> "+m.getMonto()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Estado: </b> "+m.getEstado()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Fecha de solicitud: </b> "+m.getFechaPedido()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Fecha de entrega: </b> ");
																			if(m.getFechaPedido()!=null)
																			{
																				respuesta.println(m.getFechaPedido());
																			}
																			else
																			{
																				respuesta.println(" No se ha entregado");
																			}
					respuesta.println( " 									</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <p><b>Materiales: </b> ");
																			ArrayList<RecursoValue> listaRecursos= m.getRecursosRequeridos();
																			for(int j=0;j<listaRecursos.size();j++)
																			{
																				RecursoValue recursoActual= listaRecursos.get(j);
																				respuesta.println( "<br/>"+recursoActual.getNombre());
																			}
					respuesta.println( "									</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                            </div>");
					respuesta.println( "                        </div>");
					respuesta.println( "                </div>");
				}
				respuesta.println( "                 <!--Consultar Clientes-->");
				respuesta.println( "				<form method=\"GET\" action=\"administrador.htm\">" );
				respuesta.println( "                <div class=\"row\">");
				respuesta.println( "                        <div class=\"panel panel-default\" >");
				respuesta.println( "                            <div class=\"panel-heading\">");
				respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar Clientes</h3>");
				respuesta.println( "                            </div>");
				respuesta.println( "                            <br>");
				respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
				respuesta.println( "                             	<div class=\"col-lg-4\">");
				respuesta.println( "                             	<div class=\"col-lg-12\">");
				respuesta.println( "                                    <p><b>Mostrar todos</b></p>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-12\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"Consultar\" name=\"consultarClientes\">");
				respuesta.println( "                                </div>");
				respuesta.println( "                                </div>");
				
				respuesta.println( "                             	<div class=\"col-lg-4\">");
					respuesta.println( "                             	<div class=\"col-lg-12\">");
					respuesta.println( "                                    <p><b>Buscar por id </b></p>");
					respuesta.println( "                                    <span>Seleccione el clientes: </span>");
					respuesta.println( "                                    <br/>");
					respuesta.println( "                                    <br/>");
					respuesta.println( " 									<select name=\"idCliente\"		>");
																			try
																			{
																				ArrayList<ClienteValue> productos=ProdAndes.darInstancia().darClientes();
																				for(int i=0; i<productos.size();i++)
																				{
																					respuesta.println( "<option value=\""+productos.get(i).getId()+"\">"+productos.get(i).getNombre()+" - "+productos.get(i).getId()+"</option>");
																				}
																			}
																			catch (Exception e)
																			{
																				imprimirMensajeError(respuesta,"Error de carga", e.getMessage());
																			}
					respuesta.println( "                                    </select>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-12\">");
					respuesta.println( "                                	<INPUT type=\"submit\" value=\"Consultar\" name=\"consultarClientesPorId\">");
					respuesta.println( "                                </div>");
				respuesta.println( "                                </div>");
				respuesta.println( "                             	<div class=\"col-lg-4\">");
				respuesta.println( "                             	<div class=\"col-lg-12\">");
				respuesta.println( "                                    <p><b>Buscar por email </b></p>");
				respuesta.println( "                                    <span>Escriba el email: </span>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <br/>");
				respuesta.println( " 									<INPUT type=\"email\" name=\"email\">");
				respuesta.println( "                                    </select>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-12\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"Consultar\" name=\"consultarClientePorEmail\">");
				respuesta.println( "                                </div>");
				respuesta.println( "                                </div>");
				
				
				respuesta.println( "                            </div>");
				respuesta.println( "                        </div>");
				respuesta.println( "                </div>");
				respuesta.println( "                <!-- /.row -->");			
				respuesta.println( "				</form>");
				for(int i=0;i<listaConsultarClientes.size();i++)
				{
					ClienteValue m=listaConsultarClientes.get(i);
					respuesta.println( "          <div id=\"littleWrap\" class=\"row\">");
					respuesta.println( "                        <div class=\"panel panel-default\" >");
					respuesta.println( "                            <div class=\"panel-heading\">");
					respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i><b> La informaci&#243n del cliente consultado</b></h3>");
					respuesta.println( "                            </div>");
					respuesta.println( "                            <br/>");
					respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Nombre: </b> "+m.getNombre()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Id: </b> "+m.getId()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Pedidos: </b> ");
																			ArrayList<PedidoValue> listaPedidos= m.getPedidos();
																			for(int j=0;j<listaPedidos.size();j++)
																			{
																				PedidoValue pedidoActual= listaPedidos.get(j);
																				respuesta.println( "<br/><b> id:</b> "+pedidoActual.getIdPedido());
																				respuesta.println( "<br/><b> Monto:</b> "+pedidoActual.getMonto());
																				respuesta.println( "<br/><b> Estado:</b> "+pedidoActual.getEstado());
																				respuesta.println( "<br/><b> Producto:</b> "+pedidoActual.getProductos().get(0));
																			}
					respuesta.println( "									</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                            </div>");
					respuesta.println( "                        </div>");
					respuesta.println( "                </div>");
				}
				respuesta.println( "                 <!--Consultar Proveedores-->");
				respuesta.println( "				<form method=\"GET\" action=\"administrador.htm\">" );
				respuesta.println( "                <div class=\"row\">");
				respuesta.println( "                        <div class=\"panel panel-default\" >");
				respuesta.println( "                            <div class=\"panel-heading\">");
				respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar Proveedores</h3>");
				respuesta.println( "                            </div>");
				respuesta.println( "                            <br>");
				respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
				respuesta.println( "                             	<div class=\"col-lg-2\">");			
				respuesta.println( "                             	<div class=\"col-lg-12\">");
				respuesta.println( "                                    <p><b>Mostrar todos los proveedores </b></p>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-12\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"Consultar\" name=\"consultarProveedores\">");
				respuesta.println( "                                </div>");
				respuesta.println( "                                </div>");
				respuesta.println( "                             	<div class=\"col-lg-4\">");			
				respuesta.println( "                             	<div class=\"col-lg-12\">");
				respuesta.println( "                                    <p><b>Buscar por id </b></p>");
				respuesta.println( "                                    <span>Seleccione el proveedor: </span>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <br/>");
				respuesta.println( " 									<select name=\"idProveedor\"		>");
																		try
																		{
																			ArrayList<ProveedorValue> proveedores=ProdAndes.darInstancia().darProveedores();
																			for(int i=0; i<proveedores.size();i++)
																			{
																				respuesta.println( "<option value=\""+proveedores.get(i).getId()+"\">"+proveedores.get(i).getNombre()+" - "+proveedores.get(i).getId()+"</option>");
																			}
																		}
																		catch (Exception e)
																		{
																			imprimirMensajeError(respuesta,"Error de carga", e.getMessage());
																		}
				respuesta.println( "                                    </select>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-12\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"Consultar\" name=\"consultarProveedoresPorId\">");
				respuesta.println( "                                </div>");
				respuesta.println( "                                </div>");
				respuesta.println( "                             	<div class=\"col-lg-4\">");
				respuesta.println( "                             	<div class=\"col-lg-12\">");
				respuesta.println( "                                    <p><b>Buscar por nombre </b></p>");
				respuesta.println( "                                    <span>Escriba el nombre: </span>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <br/>");
				respuesta.println( " 									<INPUT type=\"text\" name=\"nombre\">");
				respuesta.println( "                                    </select>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-12\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"Consultar\" name=\"consultarClientePorNombre\">");
				respuesta.println( "                                </div>");
				respuesta.println( "                                </div>");
				respuesta.println( "                            </div>");
				respuesta.println( "                       ");
				respuesta.println( "                        </div>");
				respuesta.println( "                </div>");
				respuesta.println( "                <!-- /.row -->");			
				respuesta.println( "				</form>");
				for(int i=0;i<listaConsultarProveedores.size();i++)
				{
					ProveedorValue m=listaConsultarProveedores.get(i);
					respuesta.println( "          <div id=\"littleWrap\" class=\"row\">");
					respuesta.println( "                        <div class=\"panel panel-default\" >");
					respuesta.println( "                            <div class=\"panel-heading\">");
					respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i><b> La informaci&#243n del proveedor consultado</b></h3>");
					respuesta.println( "                            </div>");
					respuesta.println( "                            <br/>");
					respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Nombre Empresa: </b> "+m.getNombre()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Id: </b> "+m.getId()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Dirección Física: </b> "+m.getDireccionFisica()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Telefono: </b> "+m.getTelefono()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Id Representante Legal: </b> "+m.getIdRepresentanteLegal()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Volumen Maximo: </b> "+m.getVolumenMaximo()+"</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                                <div class=\"col-lg-4\">");
					respuesta.println( "                                    <br/><p><b>Pedidos: </b> ");
																			ArrayList<PedidoValue> listaPedidos= m.getPedidos();
																			for(int j=0;j<listaPedidos.size();j++)
																			{
																				PedidoValue pedidoActual= listaPedidos.get(j);
																				respuesta.println( "<br/><b> id: "+pedidoActual.getIdPedido()+"</b>");
																				respuesta.println( "<br/><b> Monto: "+pedidoActual.getMonto()+"</b>");
																				respuesta.println( "<br/><b> Estado: "+pedidoActual.getEstado()+"</b>");
																			}
					respuesta.println( "									</p>");
					respuesta.println( "                                </div>");
					respuesta.println( "                            </div>");
					respuesta.println( "                        </div>");
					respuesta.println( "                </div>");
				}
				respuesta.println( "                    </div>");
				respuesta.println( "                </div>");
				respuesta.println( "    <!-- /#wrapper -->");
				respuesta.println( "</body>" );
	}

	/**
	 * Imprime un mensaje de error
	 * @param respuesta Respuesta al cliente
	 * @param titulo Título del error
	 * @param mensaje Mensaje del error
	 */
	private void imprimirMensajeError( PrintWriter respuesta, String titulo, String mensaje )
	{
		respuesta.println( "                      <p class=\"error\"><b>Ha ocurrido un error!:<br>" );
		respuesta.println( "                      </b>" + titulo + "</p><p>" + mensaje + ". </p>" );
		respuesta.println( "                      <p>Intente la " );
		respuesta.println( "                      operación nuevamente. Si el problema persiste, contacte" );
		respuesta.println( "                      al administrador del sistema.</p>" );
	}
	
	/**
	 * Imprime el footer 
	 * @param respuesta Respuesta al cliente
	 * @param titulo Título del error
	 * @param mensaje Mensaje del error
	 * @throws IOException 
	 */
	private void imprimirfooter(HttpServletResponse response) throws IOException
	{
		// Obtiene el flujo de escritura de la respuesta
		PrintWriter respuesta = response.getWriter( );
		respuesta.println( "</html>" );
	}
}