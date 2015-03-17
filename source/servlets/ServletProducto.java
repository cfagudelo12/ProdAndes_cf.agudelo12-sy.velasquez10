package servlets;


import java.io.IOException;
import java.io.PrintWriter;



import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.system.server.ServerConfig;
import org.jboss.system.server.ServerConfigLocator;

import vos.MaterialValue;
import vos.ProductoValue;
import fachada.ProdAndes;



public class ServletProducto extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/Empresa.data";

	private boolean seLogroSolicitudProducto;
	private ArrayList<MaterialValue> listaMateriales;
	private ArrayList<ProductoValue> listaProductos;

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Inicialización del Servlet
	 */
	public void init( ) throws ServletException
	{

		seLogroSolicitudProducto=false;
		listaProductos= new ArrayList<ProductoValue>();
		listaMateriales= new ArrayList<MaterialValue>();
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

		String producto = request.getParameter( "producto" );
		String solicitarProducto = request.getParameter( "solicitarProducto" );
		String buscarProducto = request.getParameter( "buscarProducto" );
		String consultarExistencias = request.getParameter( "consultarExistencias" );

		if(producto!=null)
		{
			try
			{
				imprimirPaginaProducto(response);

			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			}
		}
		if(consultarExistencias!=null)
		{
			try
			{	
				String desde = request.getParameter( "desde" );
				String hasta = request.getParameter( "hasta" );
				String etapaProduccion = request.getParameter( "etapaProduccion" );
				String fechaS = request.getParameter( "fechaSolicitud" );
				String fechaE = request.getParameter( "fechaEntrega" );
				SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date fechaLl= null;
				fechaLl = formato.parse(fechaS);
				Date fechaSolicitud=(Date) fechaLl;
				fechaLl = formato.parse(fechaE);
				Date fechaEntrega=(Date) fechaLl;

				listaProductos= ProdAndes.darInstancia().consultarExistenciasProductos(Integer.parseInt(desde),Integer.parseInt(hasta),Integer.parseInt(etapaProduccion),fechaEntrega,fechaSolicitud);
				imprimirPaginaProducto(response);
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			} 
			catch (ParseException e) 
			{
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			} 
			catch (Exception e) 
			{
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		if(solicitarProducto!=null)
		{
			try
			{
				String idCliente = request.getParameter( "idCliente" );
				String idProducto = request.getParameter( "idProducto" );
				String cantidad = request.getParameter( "cantidad" );
				String fecha = request.getParameter( "fechaEntrega" );
				SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date fechaLl= null;
				fechaLl = formato.parse(fecha);
				Date fechaEntrega=(Date) fechaLl;

				ProdAndes.darInstancia().solicitarPedido(idCliente, idProducto, fechaEntrega, Integer.parseInt(cantidad));
				imprimirPaginaProducto(response);

			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			} 
			catch (ParseException e) 
			{
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			} 
			catch (Exception e) 
			{
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(buscarProducto!=null)
		{
			try
			{
				String cantidad = request.getParameter( "cantidad" );
				String costo = request.getParameter( "costo" );
				listaMateriales= ProdAndes.darInstancia().consultarProducto(Integer.parseInt(cantidad),Float.parseFloat(costo));
				imprimirPaginaProducto(response);
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			} 
			catch (Exception e) 
			{
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
	 * Imprime el cuerpo con el diseño de la página
	 * @param response Respuesta
	 * @throws IOException Excepción al imprimir en el resultado
	 */
	private void imprimirPaginaProducto( HttpServletResponse response) throws IOException
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
		respuesta.println( "                <a id=\"fondoAzul\" class=\"navbar-brand\" href=\"index.html\">Administrador</a>");
		respuesta.println( "            </div>");
		respuesta.println( "            <!-- elementos de la parte de arriba del navbar-->");
		respuesta.println( "            <ul id=\"fondoAzul\" class=\"nav navbar-right top-nav\">");
		respuesta.println( "                <li id=\"fondoAzul\"  class=\"dropdown\">");
		respuesta.println( "                    <a id=\"fondoAzul\"  href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><i class=\"fa fa-user\"></i> Carlos y Sergio <b class=\"caret\"></b></a>");
		respuesta.println( "                    <ul id=\"fondoAzul\" class=\"dropdown-menu\">");
		respuesta.println( "                        <li>");
		respuesta.println( "                            <a href=\"#\"><i class=\"fa fa-fw fa-user\"></i> Profile</a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                        <li>");
		respuesta.println( "                            <a href=\"#\"><i class=\"fa fa-fw fa-envelope\"></i> Inbox</a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                        <li>");
		respuesta.println( "                            <a href=\"#\"><i class=\"fa fa-fw fa-gear\"></i> Settings</a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                        <li class=\"divider\"></li>");
		respuesta.println( "                        <li>");
		respuesta.println( "                            <a href=\"#\"><i class=\"fa fa-fw fa-power-off\"></i> Log Out</a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                    </ul>");
		respuesta.println( "                </li>");
		respuesta.println( "            </ul>");
		respuesta.println( "            <!-- elementos de la parte de arriba del navbar- para pantallas pequeÃ±as -->");
		respuesta.println( "            <div  class=\"collapse navbar-collapse navbar-ex1-collapse\">");
		respuesta.println( "                <ul class=\"nav navbar-nav side-nav\">");
		respuesta.println( "                	<form method=\"GET\" action=\"producto.htm\">");
		respuesta.println( "                        <li class=\"active\">");
		respuesta.println( "                            <a><i class=\"fa fa-fw fa-dashboard\"></i> <Input id=\"BotonNegro\" type=\"submit\" value=\"Productos\" id=\"letraBlanca\" name=\"producto\" > </a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                    </form>");
		respuesta.println( "                     <form method=\"GET\" action=\"procesoDeProduccion.htm\">");
		respuesta.println( "                        <li>");
		respuesta.println( "                              <a><i class=\"fa fa-fw fa-bar-chart-o\"></i> <Input id=\"BotonNegro\" type=\"submit\" value=\"Proceso de producci&#243n\" id=\"letraBlanca\" name=\"procesoProduccion\" > </a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                    </form>");
		respuesta.println( "                    <form method=\"GET\" action=\"recursos.htm\">");
		respuesta.println( "                        <li>");
		respuesta.println( "                              <a><i class=\"fa fa-fw fa-bar-chart-o\"></i> <Input id=\"BotonNegro\" type=\"submit\" value=\"Materiales\" id=\"letraBlanca\" name=\"recursos\" > </a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                    </form>");
		respuesta.println( "                    <form method=\"GET\" action=\"empleados.htm\">");
		respuesta.println( "                        <li>");
		respuesta.println( "                             <a><i class=\"fa fa-fw fa-bar-chart-o\"></i> <Input id=\"BotonNegro\" type=\"submit\" value=\"Empleados\" id=\"letraBlanca\" name=\"empleados\" > </a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                    </form>");
		respuesta.println( "                </ul>");
		respuesta.println( "            </div>");
		respuesta.println( "        </nav>");
		respuesta.println( "		");
		respuesta.println( "<form method=\"GET\" action=\"producto.htm\">" );
		respuesta.println( "        <div id=\"page-wrapper\">");
		respuesta.println( "");
		respuesta.println( "            <div class=\"container-fluid\">");
		respuesta.println( "");
		respuesta.println( "                <!-- titulo de la pagina -->");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                    <div class=\"col-lg-12\">");
		respuesta.println( "                        <h1 class=\"page-header\">");
		respuesta.println( "                            Productos <small>Informaci&#243n general</small>");
		respuesta.println( "                        </h1>");
		respuesta.println( "                        ");
		respuesta.println( "                    </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                <!--consultar existencias de productos-->");
		respuesta.println( "                 <form method=\"GET\" action=\"producto.htm\">");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar existencias de productos</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br/>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "                                  <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique el rango de existencias: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <span>Desde: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"number\" name=\"desde\"/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <span>Hasta: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"number\" name=\"hasta\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                 <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique el id del proceso de producci&#243n: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"number\" name=\"etapaProduccion\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                 <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique la fecha de solicitud: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"date\" name=\"fechaSolicitud\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique la fecha de entrega: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"date\" name=\"fechaEntrega\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-12\">");
		respuesta.println( "                                    <INPUT type=\"submit\" value=\"Consultar\" name=\"consultarExistencias\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                            </div>");
		respuesta.println( "                        </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                </form>");
		respuesta.println( "                 <!--Registrar pedido de productos-->");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Solicitar Productos</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "                             	<div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique su Id: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"number\" name=\"idCliente\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "								<div class=\"col-lg-4\">");
		respuesta.println( "	                            	<span>Indique el id del prodcuto: </span>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                               	<INPUT type=\"number\" name=\"idProducto\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique el id del pedido: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"number\" name=\"idPedido\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique la cantidad: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"number\" name=\"cantidad\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "	                            	<span>Indique la fecha de entrega deseada: </span>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                               	<INPUT type=\"date\" name=\"fechaEntrega\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-12\">");
		respuesta.println( "                                	<INPUT type=\"submit\" value=\"Solicitar\" name=\"solicitarProducto\">");
		respuesta.println( "                                </div>");
		respuesta.println( "                            </div>");
		respuesta.println( "                       ");
		respuesta.println( "                        </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                <!-- /.row -->");
		respuesta.println( "");
		respuesta.println( "            </div>");

		if(seLogroSolicitudProducto)
		{
			seLogroSolicitudProducto=false;
			respuesta.println( "<script> alert(\"La solicitud fue exitosa\"); </script>");
		}
		respuesta.println( "</form>");
		respuesta.println( "            <!--Buscar un producto-->");
		respuesta.println( "                <form method=\"GET\" action=\"producto.htm\">");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar un Producto</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br/>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique la cantidad en bodega: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"number\" name=\"cantidadBodega\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique el costo: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"number\" name=\"costo\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-12\">");
		respuesta.println( "                                    <INPUT type=\"submit\" value=\"Buscar\" name=\"buscarProducto\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                            </div>");
		respuesta.println( "                        </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                <!-- /.row -->");
		respuesta.println( "            </form>");
		for(int i=0;i<listaMateriales.size();i++)
		{
			MaterialValue m=listaMateriales.get(i);
			respuesta.println( "          <div id=\"littleWrap\" class=\"row\">");
			respuesta.println( "                        <div class=\"panel panel-default\" >");
			respuesta.println( "                            <div class=\"panel-heading\">");
			respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> La informaci&#243n del producto consultado</h3>");
			respuesta.println( "                            </div>");
			respuesta.println( "                            <br/>");
			respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Materiales que lo componen: "+m.getMaterialesQueLoComponen() +"</span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Pedidos de compra en los que esta involucrado: "+m.getMaterialesQueLoComponen()+"</span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                            </div>");
			respuesta.println( "                        </div>");
			respuesta.println( "                </div>");
		}
		for(int i=0;i<listaProductos.size();i++)
		{
			ProductoValue p=listaProductos.get(i);
			respuesta.println( "          <div id=\"littleWrap\" class=\"row\">");
			respuesta.println( "                        <div class=\"panel panel-default\" >");
			respuesta.println( "                            <div class=\"panel-heading\">");
			respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> La informaci&#243n del producto consultado</h3>");
			respuesta.println( "                            </div>");
			respuesta.println( "                            <br/>");
			respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Informacion del producto: "+p.toString() +"</span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                            </div>");
			respuesta.println( "                        </div>");
			respuesta.println( "                </div>");
		}
		respuesta.println( "            	</div>");
		respuesta.println( "            </div>");
		respuesta.println( "        </div>");
		respuesta.println( "        <!-- /#page-wrapper -->");
		respuesta.println( "");
		respuesta.println( "    </div>");
		respuesta.println( "    <!-- /#wrapper -->");
		respuesta.println( "</body>" );
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

}