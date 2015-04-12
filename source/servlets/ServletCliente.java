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

import vos.MaterialValue;
import vos.ProductoValue;
import vos.RecursoValue;
import fachada.ProdAndes;



public class ServletCliente extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/calculadoraWeb.data";

	private boolean seLogroSolicitudProducto;
	
	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Inicialización del Servlet
	 */
	public void init( ) throws ServletException
	{
		seLogroSolicitudProducto=false;
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

		String solicitarProducto = request.getParameter( "solicitarProducto" );
		
		if(solicitarProducto!=null)
		{
			try
			{
				String idCliente = request.getParameter( "idCliente" );
				String idProducto = request.getParameter( "idProducto" );
				String cantidad = request.getParameter( "cantidad" );
				String fechaEntrega = request.getParameter( "fechaEntrega" );

				ProdAndes.darInstancia().solicitarPedido(Integer.parseInt(idCliente), Integer.parseInt(idProducto), fechaEntrega, Integer.parseInt(cantidad));
				imprimirPaginaCliente(response);
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
	private void imprimirPaginaCliente( HttpServletResponse response) throws IOException
	{
		// Obtiene el flujo de escritura de la respuesta
		PrintWriter respuesta = response.getWriter( );
		
		respuesta.println( "	<body>");
		respuesta.println( "	");
		respuesta.println( "	<div id=\"wrapper\">");
		respuesta.println( "        <!-- Navbar -->");
		respuesta.println( "        <nav id=\"fondoAzul\" class=\"navbar navbar-fixed-top navbar-fixed-top\" role=\"navigation\">");
		respuesta.println( "            <!-- Para navegaciÃ³n en celulares-->");
		respuesta.println( "            <div  id=\"fondoAzul\" class=\"navbar-header\">");
		respuesta.println( "                <a id=\"fondoAzul\" class=\"navbar-brand\" href=\"index.html\">Consultas generales</a>");
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
		respuesta.println( "        </nav>");
		respuesta.println( "	</div>");
		respuesta.println( "        <div id=\"page-wrapper\">");
		respuesta.println( "");
		respuesta.println( "            <div class=\"container-fluid\">");
		respuesta.println( "");
		respuesta.println( "                <!-- titulo de la pagina -->");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                    <div class=\"col-lg-12\">");
		respuesta.println( "                        <h1 class=\"page-header\">");
		respuesta.println( "                            Cliente <small>Acciones Generales</small>");
		respuesta.println( "                        </h1>");
		respuesta.println( "                        ");
		respuesta.println( "                    </div>");
		respuesta.println( "                </div>");
		
		respuesta.println( "                 <!--Solicitud de productos-->");
		respuesta.println( "<form method=\"GET\" action=\"cliente.htm\">" );
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
		respuesta.println( "									<span>Seleccione el producto: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( " 									<select name=\"idProducto\"		>");
																try
																{
																	ArrayList<ProductoValue> procesos=ProdAndes.darInstancia().darProductosParaRegistrarEntrega();
																	for(int i=0; i<procesos.size();i++)
																	{
																		respuesta.println( "                                    <option value=\""+procesos.get(i).getIdProducto()+"\">"+procesos.get(i).getNombre()+"</option>");
																	}
																}
																catch (Exception e)
																{
																	imprimirMensajeError(respuesta,"Error de carga", e.getMessage());
																}
		respuesta.println( "                                    </select>");
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
		respuesta.println( "</form>" );
        respuesta.println( "    		</div>");
 		respuesta.println( "		</div>");
		respuesta.println( "    </body>");
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