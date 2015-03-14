package servlets;


import java.io.IOException;
import java.io.PrintWriter;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.system.server.ServerConfig;
import org.jboss.system.server.ServerConfigLocator;



public class ServletEmpresa extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/Empresa.data";


	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Inicialización del Servlet
	 */
	public void init( ) throws ServletException
	{

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

		String iteracion2 = request.getParameter( "empresa" );


		if(iteracion2!=null)
		{
			try
			{
				imprimirPaginaIteracion2(response);
				
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			}
		}

	}
	

	/**
	 * Imprime el encabezado con el diseño de la página
	 * @param response Respuesta
	 * @throws IOException Excepción al imprimir en el resultado
	 */
	private void imprimirEncabezado( HttpServletResponse response ) throws IOException
	{
		// Obtiene el flujo de escritura de la respuesta
		PrintWriter respuesta = response.getWriter( );

		// Imprime el encabezado
		respuesta.println( "<html>" );
		respuesta.println( "<head>" );
		respuesta.println( "<title>ProdAndes Sistemas Transaccionales</title>" );
		respuesta.println( "<meta charset\"UTF-8\">" );
		respuesta.println( "<meta name=\"description\"></meta>" );
		respuesta.println( "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/estilo.css\"/>" );
		respuesta.println( "<meta name\"viewport\" content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">" );
		respuesta.println( "<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">" ); 
		respuesta.println( "<link href=\"css/sb-admin.css\" rel=\"stylesheet\">" );
		respuesta.println( "</head>" );
	}
	
	/**
	 * Imprime el encabezado con el diseño de la página
	 * @param response Respuesta
	 * @throws IOException Excepción al imprimir en el resultado
	 */
	private void imprimirPaginaIteracion2( HttpServletResponse response ) throws IOException
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
		respuesta.println( "                    <li class=\"active\">");
		respuesta.println( "                        <a href=\"productos\"><i class=\"fa fa-fw fa-dashboard\"></i> <div id=\"letraBlanca\" > Productos</div></a>");
		respuesta.println( "                    </li>");
		respuesta.println( "                    <li>");
		respuesta.println( "                        <a href=\"charts.html\"><i class=\"fa fa-fw fa-bar-chart-o\"></i> <div id=\"letraBlanca\" >Proceso de producciÃ³n</div></a>");
		respuesta.println( "                    </li>");
		respuesta.println( "                    <li>");
		respuesta.println( "                        <a href=\"tables.html\"><i class=\"fa fa-fw fa-table\"></i> <div id=\"letraBlanca\" >materiales</div></a>");
		respuesta.println( "                    </li>");
		respuesta.println( "                    <li>");
		respuesta.println( "                        <a href=\"forms.html\"><i class=\"fa fa-fw fa-edit\"></i> <div id=\"letraBlanca\" >Empleados</div></a>");
		respuesta.println( "                    </li>");
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
		respuesta.println( "                            Productos <small>InformaciÃ³n general</small>");
		respuesta.println( "                        </h1>");
		respuesta.println( "                        ");
		respuesta.println( "                    </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                 <!--Registrar pedido de productos-->");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Solicitar Productos</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "                            	<div class=\"col-lg-4\">");
		respuesta.println( "	                            	<span>Eliga el producto: </span>");
		respuesta.println( "	                            	<br>");
		respuesta.println( "	                            	<br>");
		respuesta.println( "	                                <Select>");
		respuesta.println( "	                                    <option>");
		respuesta.println( "	                                        <i>producto 1</i> ");
		respuesta.println( "	                                    </option>");
		respuesta.println( "	                                    <option>");
		respuesta.println( "	                                        <i>producto 2</i> ");
		respuesta.println( "	                                    </option>");
		respuesta.println( "	                                    <option>");
		respuesta.println( "	                                        <i>producto 3</i> ");
		respuesta.println( "	                                    </option>");
		respuesta.println( "	                                </Select>");
		respuesta.println( "								</div>");
		respuesta.println( "								<div class=\"col-lg-4\">");
		respuesta.println( "	                            	<span>Indique la cantidad deseada: </span>");
		respuesta.println( "	                            	<br>");
		respuesta.println( "	                            	<br>");
		respuesta.println( "	                               	<INPUT type=\"number\" name=\"cantidad\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "	                            	<span>Indique la fecha de entrega deseada: </span>");
		respuesta.println( "	                            	<br>");
		respuesta.println( "	                            	<br>");
		respuesta.println( "	                               	<INPUT type=\"date\" name=\"fecha\"/>");
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
		respuesta.println( "            <!-- /.container-fluid -->");
		respuesta.println( "");
		respuesta.println( "        </div>");
		respuesta.println( "        <!-- /#page-wrapper -->");
		respuesta.println( "");
		respuesta.println( "    </div>");
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

}