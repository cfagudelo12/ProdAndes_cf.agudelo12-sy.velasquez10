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








import vos.RecursoValue;
import fachada.ProdAndes;

public class ServletRecursos
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/Empresa.data";
	private boolean seRegistro;
	private ArrayList<RecursoValue> listaRecursos;
	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Inicialización del Servlet
	 */
	public void init( ) throws ServletException
	{
		seRegistro=false;
		listaRecursos= new ArrayList<RecursoValue>();
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

		String registrarMaterial = request.getParameter( "registrarMaterial" );
		String consultarExistencias = request.getParameter( "consultarExistencias" );
		String buscarMaterial = request.getParameter( "buscarMaterial" );

		if(registrarMaterial!=null)
		{
			try
			{
				String fecha = request.getParameter( "fechaLlegada" );
				String idPedido= request.getParameter( "idPedido" );
				String idRecurso = request.getParameter( "idRecurso" );
				SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date fechaLl= null;
				fechaLl = formato.parse(fecha);
				Date fechaLlegada=(Date) fechaLl;
				try 
				{
					ProdAndes.darInstancia().registrarLlegadaRecurso(Integer.parseInt(idRecurso), Integer.parseInt(idPedido),fechaLlegada);
				} 
				catch (Exception e) 
				{
					imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
				}
				imprimirPaginaMateriales(response);				
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			} 
			catch (ParseException e1) 
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error de formato");
			}
		}
		if(consultarExistencias!=null)
		{
			try
			{
				String tipo = request.getParameter( "tipo" );
				String desde = request.getParameter( "desde" );
				String hasta = request.getParameter( "hasta" );
				String etapaProduccion = request.getParameter( "etapaProduccion" );
				String fechaSoE = request.getParameter( "fechaSoE" );

				//					listaMateriales= ProdAndes.darInstancia().consultarExistenciasMaterial(tipo,desde,hasta,etapaProduccion,fechaSoE);
				imprimirPaginaMateriales(response);
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			}
		}
		if(buscarMaterial!=null)
		{
			try
			{
				String cantidad = request.getParameter( "cantidad" );
				String costo = request.getParameter( "costo" );
				String desde = request.getParameter( "desde" );
				String hasta = request.getParameter( "hasta" );
				SimpleDateFormat formato = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date fechaLl= null;
				try 
				{
					fechaLl = formato.parse(desde);
					Date fechaDesde=(Date) fechaLl;
					fechaLl = formato.parse(hasta);
					Date fechaHasta=(Date) fechaLl;

					listaRecursos= ProdAndes.darInstancia().consultarRecurso(Integer.parseInt(cantidad), fechaDesde, fechaHasta, Float.parseFloat(costo));
				} 
				catch (ParseException e) 
				{
					
					e.printStackTrace();
				} 
				catch (Exception e) 
				{
					imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
				}

				imprimirPaginaMateriales(response);
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
	private void imprimirPaginaMateriales( HttpServletResponse response) throws IOException
	{
		// Obtiene el flujo de escritura de la respuesta
		PrintWriter respuesta = response.getWriter( );

		// Imprime el cuerpo
		respuesta.println( "<body>");
		if(seRegistro)
		{
			seRegistro=false;
			respuesta.println( "<srcipt>alert(\"Se registro el material de forma exitosa\")</srcipt>");
		}
		respuesta.println( "	<div id=\"wrapper\">");
		respuesta.println( "        <!-- Navbar -->");
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
		respuesta.println( "                            <a href=\"productos.html\"><i class=\"fa fa-fw fa-dashboard\"></i> <div id=\"letraBlanca\" > Productos</div></a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                        <li>");
		respuesta.println( "                            <a href=\"procesoDeProduccion.html\"><i class=\"fa fa-fw fa-bar-chart-o\"></i> <div id=\"letraBlanca\" >Proceso de producci&#243n</div></a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                        <li class=\"active\">");
		respuesta.println( "                            <a href=\"materiales.html\"><i class=\"fa fa-fw fa-table\"></i> <div id=\"letraBlanca\" >materiales</div></a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                        <li>");
		respuesta.println( "                            <a href=\"empleados.html\"><i class=\"fa fa-fw fa-edit\"></i> <div id=\"letraBlanca\" >Empleados</div></a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                    </ul>");
		respuesta.println( "                </li>");
		respuesta.println( "            </ul>");
		respuesta.println( "            <!-- elementos de la parte de arriba del navbar- para pantallas pequeÃ±as -->");
		respuesta.println( "            <div  class=\"collapse navbar-collapse navbar-ex1-collapse\">");
		respuesta.println( "                <ul class=\"nav navbar-nav side-nav\">");
		respuesta.println( "                    <li>");
		respuesta.println( "                        <a href=\"productos\"><i class=\"fa fa-fw fa-dashboard\"></i> <div id=\"letraBlanca\" > Productos</div></a>");
		respuesta.println( "                    </li>");
		respuesta.println( "                    <li class=\"active\">");
		respuesta.println( "                        <a href=\"procesoDeProduccion.html\"><i class=\"fa fa-fw fa-bar-chart-o\"></i> <div id=\"letraBlanca\" >Proceso de producci&#243n</div></a>");
		respuesta.println( "                    </li>");
		respuesta.println( "                    <li>");
		respuesta.println( "                        <a href=\"materiales.html\"><i class=\"fa fa-fw fa-table\"></i> <div id=\"letraBlanca\" >materiales</div></a>");
		respuesta.println( "                    </li>");
		respuesta.println( "                    <li>");
		respuesta.println( "                        <a href=\"empleados.html\"><i class=\"fa fa-fw fa-edit\"></i> <div id=\"letraBlanca\" >Empleados</div></a>");
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
		respuesta.println( "                            Materiales <small>Informaci&#243n sobre los materiales con los que cuenta la empresa</small>");
		respuesta.println( "                        </h1>");
		respuesta.println( "                        ");
		respuesta.println( "                    </div>");
		respuesta.println( "                </div>");
		respuesta.println( "");
		respuesta.println( "                 <!--Registrar llegada de un material-->");
		respuesta.println( "                <form method=\"GET\" action=\"materiales.htm\">");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Registrar llegada de un Material</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br/>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "                                <div class=\"col-lg-4\">");
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
		respuesta.println( "                </form>");
		respuesta.println( "");
		respuesta.println( "                 <!--consultar existencias de materiales-->");
		respuesta.println( "                 <form method=\"GET\" action=\"materiales.htm\">");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar existencias de un Material</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br/>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Eliga el tipo: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <Select name=\"tipo\">");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <i>materia prima</i>");
		respuesta.println( "                                        </option>");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <iu>componente</i> ");
		respuesta.println( "                                        </option>");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <i>etapa de producto</i> ");
		respuesta.println( "                                        </option>");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <i>producto</i> ");
		respuesta.println( "                                        </option>");
		respuesta.println( "                                    </Select>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
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
		respuesta.println( "                                    <span>Indique la etapa de producci&#243n: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"text\" name=\"etapaProduccion\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                 <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique la fecha de solicitud y/o entrega: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"date\" name=\"fechaSoE\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-12\">");
		respuesta.println( "                                    <INPUT type=\"submit\" value=\"Consultar\" name=\"consultarExistencias\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                            </div>");
		respuesta.println( "                        </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                </form>");
		respuesta.println( "");
		respuesta.println( "                 <!--Buscar un material-->");
		respuesta.println( "                 <form method=\"GET\" action=\"materiales.htm\">");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar un Material</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br/>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "                            	<div class=\"col-lg-4\">");
		respuesta.println( "	                            	<span>Eliga el tipo: </span>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                                <Select name=\"tipo\">");
		respuesta.println( "	                                    <option>");
		respuesta.println( "	                                        <i>materia prima</i>");
		respuesta.println( "	                                    </option>");
		respuesta.println( "	                                    <option>");
		respuesta.println( "	                                        <iu>componente</i> ");
		respuesta.println( "	                                    </option>");
		respuesta.println( "	                                    <option>");
		respuesta.println( "	                                        <i>etapa de producto</i> ");
		respuesta.println( "	                                    </option>");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <i>producto</i> ");
		respuesta.println( "                                        </option>");
		respuesta.println( "	                                </Select>");
		respuesta.println( "								</div>");
		respuesta.println( "								<div class=\"col-lg-4\">");
		respuesta.println( "	                            	<span>Indique el nombre: </span>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                               	<INPUT type=\"text\" name=\"nombre\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-12\">");
		respuesta.println( "                                	<INPUT type=\"submit\" value=\"Buscar\" name=\"buscarMaterial\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                            </div>");
		respuesta.println( "                        </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                <!-- /.row -->");
		respuesta.println( "            </form>");
		for(int i=0;i<listaRecursos.size();i++)
		{
			//						Recurso r= (Recurso) listaMateriales.get(i);
			//						respuesta.println( "             <div class=\"row\">");
			//						respuesta.println( "                        <div class=\"panel panel-default\" >");
			//						respuesta.println( "                            <div class=\"panel-heading\">");
			//						respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> La informaci&#243n del material consultado</h3>");
			//						respuesta.println( "                            </div>");
			//						respuesta.println( "                            <br/>");
			//						respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
			//						respuesta.println( "                                <div class=\"col-lg-4\">");
			//						respuesta.println( "                                    <span>Tipo: "+ r.darTipo() +"</span>");
			//						respuesta.println( "                                </div>");
			//						respuesta.println( "                                <div class=\"col-lg-4\">");
			//						respuesta.println( "                                    <span>Materiales que lo componen: "+ r.darMaterialesQueComponen() +" </span>");
			//						respuesta.println( "                                </div>");
			//						respuesta.println( "                                <div class=\"col-lg-4\">");
			//						respuesta.println( "                                    <span>Materiales que compone: "+ r.darMaterialesQueCompone() +"</span>");
			//						respuesta.println( "                                </div>");
			//						respuesta.println( "                                <div class=\"col-lg-4\">");
			//						respuesta.println( "                                    <span>Etapas de producci&#243n en las que participa: "+ r.darEtapasDeproduccion() +" </span>");
			//						respuesta.println( "                                </div>");
			//						respuesta.println( "                                <div class=\"col-lg-4\">");
			//						respuesta.println( "                                    <span>Pedidos de clientes en los que esta involucrado: "+ r.darPedidosCliente() +"</span>");
			//						respuesta.println( "                                </div>");
			//						respuesta.println( "                                <div class=\"col-lg-4\">");
			//						respuesta.println( "                                    <span>Pedidos de compra en los que esta involucrado: "+ r.darPedidosCompra() +" </span>");
			//						respuesta.println( "                                </div>");
			//						respuesta.println( "                            </div>");
			//						respuesta.println( "                        </div>");
			//						respuesta.println( "                </div>");
		}
		respuesta.println( "                <!-- /.row -->");
		respuesta.println( "            </div>");
		respuesta.println( "");
		respuesta.println( "              ");
		respuesta.println( "            </div>   <!-- /.row -->");
		respuesta.println( "            </div>");
		respuesta.println( "            <!-- /.container-fluid -->");
		respuesta.println( "              ");
		respuesta.println( "    </div>");
		respuesta.println( "        </div>");
		respuesta.println( "        <!-- /#page-wrapper -->");
		respuesta.println( "    </div>");
		respuesta.println( "    </form>");
		respuesta.println( "    <!-- /#wrapper -->");
		respuesta.println( "	</body>");
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
