package servlets;


import java.io.IOException;
import java.io.PrintWriter;



import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.system.server.ServerConfig;
import org.jboss.system.server.ServerConfigLocator;

import fachada.ProdAndes;



public class ServletProducto extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/Empresa.data";
	private boolean seRealizoPedido;
	private boolean seLogroSolicitudProducto;
	private ArrayList listaProductos;

	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Inicialización del Servlet
	 */
	public void init( ) throws ServletException
	{
		 seRealizoPedido=false;
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

		String empresa = request.getParameter( "empresa" );
		String solicitudProducto = request.getParameter( "solicitudProducto" );
		String aceptarSolicitudProducto = request.getParameter( "aceptarsolicitudProducto" );
		String denegarSolicitudProducto = request.getParameter( "denegarsolicitudProducto" );
		
		
		if(empresa!=null)
		{
			try
			{
				listaProductos = ProdAndes.darInstancia().darListaDeProductos();
				imprimirPaginaProducto(response,null);
				
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			}
		}
		if(solicitudProducto!=null)
		{
			try
			{
				seRealizoPedido=true;
				Date fechaEntrega= ProdAndes.darInstancia().realizarSolicitudDepedido();
				imprimirPaginaProducto(response, fechaEntrega);
				
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			}
		}
		else if(aceptarSolicitudProducto!=null && seRealizoPedido)
		{
			try
			{
				seRealizoPedido=false;
				seLogroSolicitudProducto= ProdAndes.darInstancia().aceptarSolicitudDepedido();
				imprimirPaginaProducto(response, null);
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			}
		}
		else if(denegarSolicitudProducto!=null && seRealizoPedido)
		{
			try
			{
				seRealizoPedido=false;
				seLogroSolicitudProducto= ProdAndes.darInstancia().denegarSolicitudDepedido();
				imprimirPaginaProducto(response, null);
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
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
	 * Imprime el cuerpo con el diseño de la página
	 * @param response Respuesta
	 * @throws IOException Excepción al imprimir en el resultado
	 */
	private void imprimirPaginaProducto( HttpServletResponse response , Date fecha ) throws IOException
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
		respuesta.println( "                    <li class=\"active\">");
		respuesta.println( "                        <a href=\"productos.html\"><i class=\"fa fa-fw fa-dashboard\"></i> <div id=\"letraBlanca\" > Productos</div></a>");
		respuesta.println( "                    </li>");
		respuesta.println( "                	</form>");
		respuesta.println( "                	<form method=\"GET\" action=\"procesoDeProduccion.htm\">");
		respuesta.println( "                    <li>");
		respuesta.println( "                        <a href=\"procesoDeProduccion.html\"><i class=\"fa fa-fw fa-bar-chart-o\"></i> <div id=\"letraBlanca\" >Proceso de producci&#243n</div></a>");
		respuesta.println( "                    </li>");
		respuesta.println( "                    </form>");
		respuesta.println( "                    <form method=\"GET\" action=\"materiales.htm\">");
		respuesta.println( "                    <li>");
		respuesta.println( "                        <a href=\"materiales.html\"><i class=\"fa fa-fw fa-table\"></i> <div id=\"letraBlanca\" >materiales</div></a>");
		respuesta.println( "                    </li>");
		respuesta.println( "                     </form>");
		respuesta.println( "                    <form method=\"GET\" action=\"empleados.htm\">");
		respuesta.println( "                    <li>");
		respuesta.println( "                        <a href=\"empleados.html\"><i class=\"fa fa-fw fa-edit\"></i> <div id=\"letraBlanca\" >Empleados</div></a>");
		respuesta.println( "                    </li>");
		respuesta.println( "                     </form>");
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
		respuesta.println( "	                                <Select name=\"tipo\">");
						for(int i=0;i<listaProductos.size();i++)
						{
							Producto p= (Producto) listaProductos.get(i);
							respuesta.println( "	                                    <option>");
							respuesta.println( "	                                        <i>"+p.darNombre()+"</i>");
							respuesta.println( "	                                    </option>");
						}
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
		if(seRealizoPedido)
		{
			 respuesta.println( "              <div id=\"littleWrap\">");
			 respuesta.println( "				<div class=\"row\">");
			 respuesta.println( "                        <div class=\"panel panel-default\" >");
			 respuesta.println( "                            <div class=\"panel-heading\">");
			 respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i>Productos solicitados</h3>");
			 respuesta.println( "                            </div>");
			 respuesta.println( "                            <br/>");
			 respuesta.println( "                            <div id=\"margen\"> Los productos solicitados fueron: </div>");
			 respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");											
			 respuesta.println( "                            	<div class=\"col-lg-12\">");
			 if(fecha!=null)
			 {
			 respuesta.println( " La fecha de entrega es " + fecha.toString() );
			 respuesta.println( "								</div>		");
			 respuesta.println( "                                <div class=\"col-lg-6\">");
			 respuesta.println( "                                	<INPUT type=\"submit\" value=\"Aceptar\" name=\"aceptarSolicitudProducto\">");
			 respuesta.println( "                                </div>");
			 respuesta.println( "                                <div class=\"col-lg-6\">");
			 respuesta.println( "                                	<INPUT type=\"submit\" value=\"Cancelar\" name=\"cancelarSolicitudProducto\">");
			 respuesta.println( "                                </div>");
			 }
			 else
			 {
				 respuesta.println( " El pedido no puede ser satisfecho porque la empresa no cuenta con los insumos necesarios, se colocara en pendiente para luego programar la compra de los insumos faltantes." ); 
				 respuesta.println( "								</div>		");
			 }
			 respuesta.println( "                            </div>");
			 respuesta.println( "                        </div>");
			 respuesta.println( "                </div>");
			 respuesta.println( "            </div>");
			
		}
		respuesta.println( "        </div>");
		respuesta.println( "        <!-- /#page-wrapper -->");
		respuesta.println( "");
		respuesta.println( "    </div>");
		respuesta.println( "    <!-- /#wrapper -->");
		if(seLogroSolicitudProducto)
		{
			seLogroSolicitudProducto=false;
			respuesta.println( "<script> alert(\"La solicitud fue exitosa\"); </script>");
		}
		respuesta.println( "</form>");
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