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



public class ServletGerente extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/calculadoraWeb.data";

	private boolean seLogroEntregaProducto;
	private boolean seRegistroMaterial;
	
	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Inicialización del Servlet
	 */
	public void init( ) throws ServletException
	{
		seRegistroMaterial=false;
		seLogroEntregaProducto=false;
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

		String gerente = request.getParameter( "gerente" );
		String registrarEntregaProducto = request.getParameter( "registrarEntregaProducto");
		String registrarMaterial = request.getParameter( "registrarMaterial" );
		
		
		if(gerente!=null)
		{
			try
			{
				imprimirPaginaGerente(response);
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			}
		}
		else if(registrarEntregaProducto!=null)
		{
			try
			{
				String datos = request.getParameter( "idProducto" );
				String[] datosA= datos.split(" ");
				String idProducto= datosA[0];
				String idPedido= datosA[1];
				String fecha= request.getParameter( "fecha" );
				
				ProdAndes.darInstancia().registrarEntregaProducto(Integer.parseInt(idProducto),Integer.parseInt(idPedido),fecha);
				seLogroEntregaProducto=true;
				imprimirPaginaGerente(response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		if(registrarMaterial!=null)
		{
			try
			{
				String fechaLlegada = request.getParameter( "fechaLlegada" );
				String idPedido= request.getParameter( "idPedido" );
				String idRecurso = request.getParameter( "idRecurso" );
	
				ProdAndes.darInstancia().registrarLlegadaRecurso(Integer.parseInt(idRecurso), Integer.parseInt(idPedido),fechaLlegada);
				seRegistroMaterial=true;
				imprimirPaginaGerente(response);		
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			} 
			catch (ParseException e1) 
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error de formato");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
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
	private void imprimirPaginaGerente( HttpServletResponse response) throws IOException
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
				respuesta.println( "                            Productos <small>Informaci&#243n general</small>");
				respuesta.println( "                        </h1>");
				respuesta.println( "                        ");
				respuesta.println( "                    </div>");
				respuesta.println( "                </div>");
				respuesta.println( "                 <!--Registrar entrega de productos-->");
				respuesta.println( "				<form method=\"GET\" action=\"Gerente.htm\">" );
				respuesta.println( "                <div class=\"row\">");
				respuesta.println( "                        <div class=\"panel panel-default\" >");
				respuesta.println( "                            <div class=\"panel-heading\">");
				respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Registrar entrega de producto</h3>");
				respuesta.println( "                            </div>");
				respuesta.println( "                            <br>");
				respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
				respuesta.println( "                             	<div class=\"col-lg-6\">");
				respuesta.println( "                                    <span>Seleccione el producto: </span>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <br/>");
				respuesta.println( " 									<select name=\"idProducto\"		>");
																		try
																		{
																			ArrayList<ProductoValue> procesos=ProdAndes.darInstancia().darProductosParaRegistrarEntrega();
																			for(int i=0; i<procesos.size();i++)
																			{
																				respuesta.println( "                                    <option value=\""+procesos.get(i).getIdProducto()+" "+procesos.get(i).getIdPedido()+"\">"+procesos.get(i).getNombre()+" - "+procesos.get(i).getIdPedido()+"</option>");
																			}
																		}
																		catch (Exception e)
																		{
																			imprimirMensajeError(respuesta,"Error de carga", e.getMessage());
																		}
				respuesta.println( "                                    </select>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-6\">");
				respuesta.println( "                                    <span>Indique la fecha: </span>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <INPUT type=\"date\" name=\"fecha\"/>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-12\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"Registrar\" name=\"registrarEntregaProducto\">");
				respuesta.println( "                                </div>");
				respuesta.println( "                            </div>");
				respuesta.println( "                       ");
				respuesta.println( "                        </div>");
				respuesta.println( "                </div>");
				respuesta.println( "                <!-- /.row -->");
				if(seLogroEntregaProducto)
				{
					seLogroEntregaProducto=false;
					respuesta.println( "<script> alert(\"El registro fue exitoso\"); </script>");
				}
				
				respuesta.println( "				</form>");
				respuesta.println( "        <div id=\"page-wrapper\">");
				respuesta.println( "");
				respuesta.println( "            <div class=\"container-fluid\">");
				respuesta.println( "");
				respuesta.println( "                <!-- titulo de la pagina -->");
				respuesta.println( "                <div class=\"row\">");
				respuesta.println( "                    <div class=\"col-lg-12\">");
				respuesta.println( "                        <h1 class=\"page-header\">");
				respuesta.println( "                            Materiales <small>Informaci&#243n sobre los materiales con los que cuenta la empresa</small>");
				respuesta.println( "                        </h1>");
				respuesta.println( "                        ");
				respuesta.println( "                    </div>");
				respuesta.println( "                </div>");
				respuesta.println( "");
				respuesta.println( "                 <!--Registrar llegada de un material-->");
				respuesta.println( "                <form method=\"GET\" action=\"gerente.htm\">");
				respuesta.println( "                <div class=\"row\">");
				respuesta.println( "                        <div class=\"panel panel-default\" >");
				respuesta.println( "                            <div class=\"panel-heading\">");
				respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Registrar llegada de un Material</h3>");
				respuesta.println( "                            </div>");
				respuesta.println( "                            <br/>");
				respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
				respuesta.println( "                                <div class=\"col-lg-4\">");
				respuesta.println( "                                    <span>Indique la fecha de llegada: </span>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <INPUT type=\"date\" name=\"fechaLlegada\"/>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                 <div class=\"col-lg-4\">");
				respuesta.println( "                                    <span>Indique el id del pedido: </span>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <INPUT type=\"number\" name=\"idPedido\"/>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-4\">");
				respuesta.println( "                                    <span>Indique el id del recurso: </span>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <br/>");
				respuesta.println( "                                    <INPUT type=\"number\" name=\"idRecurso\"/>");
				respuesta.println( "                                </div>");
				respuesta.println( "                                <div class=\"col-lg-12\">");
				respuesta.println( "                                    <INPUT type=\"submit\" value=\"Registrar\" name=\"registrarMaterial\"/>");
				respuesta.println( "                                </div>");
				respuesta.println( "                            </div>");
				respuesta.println( "                        </div>");
				respuesta.println( "                </div>");
				if(seRegistroMaterial)
				{
					seRegistroMaterial=false;
					respuesta.println( "<srcipt>alert(\"Se registro el material de forma exitosa\")</srcipt>");
				}
				respuesta.println( "                </form>");
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