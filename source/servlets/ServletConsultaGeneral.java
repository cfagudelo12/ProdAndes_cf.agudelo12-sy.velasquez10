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



public class ServletConsultaGeneral extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/calculadoraWeb.data";

	private ArrayList<ProductoValue> listaProductos;
	private ArrayList<MaterialValue> listaBuscarProductos;
	private ArrayList<MaterialValue> listaMateriales;
	private ArrayList<RecursoValue> listaRecursos;
	
	// -----------------------------------------------------------------
	// Métodos
	// -----------------------------------------------------------------

	/**
	 * Inicialización del Servlet
	 */
	public void init( ) throws ServletException
	{
		listaProductos= new ArrayList<ProductoValue>();
		listaBuscarProductos= new ArrayList<MaterialValue>();
		listaMateriales= new ArrayList<MaterialValue>();
		listaRecursos=new ArrayList<RecursoValue>();
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

		String consultarExistencias = request.getParameter( "consultarExistencias" );
		String buscarProducto = request.getParameter( "buscarProducto" );
		String consultarExistenciasMaterial = request.getParameter( "consultarExistenciasMaterial" );
		String buscarMaterial = request.getParameter( "buscarMaterial" );
		
		if(consultarExistencias!=null)
		{
			try
			{	
				String desde = request.getParameter( "desde" );
				String hasta = request.getParameter( "hasta" );
				String procesoProduccion = request.getParameter( "procesoProduccion" );
				String fechaSolicitud = request.getParameter( "fechaSolicitud" );
				String fechaEntrega = request.getParameter( "fechaEntrega" );

				listaProductos= ProdAndes.darInstancia().consultarExistenciasProductos(Integer.parseInt(desde),Integer.parseInt(hasta),Integer.parseInt(procesoProduccion),fechaSolicitud,fechaEntrega);
				System.out.println(listaProductos.size());
				imprimirPaginaGeneral(response);
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
				listaBuscarProductos= ProdAndes.darInstancia().consultarProducto(Integer.parseInt(cantidad),Float.parseFloat(costo));
				imprimirPaginaGeneral(response);
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
		else if(consultarExistenciasMaterial!=null)
		{
			try
			{
				String tipo = request.getParameter( "tipo" );
				String desde = request.getParameter( "desde" );
				String hasta = request.getParameter( "hasta" );
				String etapaProduccion = request.getParameter( "etapaProduccion" );
				String fechaSolicitud = request.getParameter( "fechaSolicitud" );
				String fechaEntrega = request.getParameter( "fechaEntrega" );

				listaRecursos= ProdAndes.darInstancia().consultarExistenciasRecurso(tipo,Integer.parseInt(desde),Integer.parseInt(hasta),Integer.parseInt(etapaProduccion),fechaEntrega,fechaSolicitud);
				imprimirPaginaGeneral(response);
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
		else if(buscarMaterial!=null)
		{
			try
			{
				String volumen = request.getParameter( "volumen" );
				String costo = request.getParameter( "costo" );
				String fechaDesde = request.getParameter( "desde" );
				String fechaHasta = request.getParameter( "hasta" );
				
				listaMateriales= ProdAndes.darInstancia().consultarRecurso(Integer.parseInt(volumen), fechaDesde, fechaHasta, Float.parseFloat(costo));

				imprimirPaginaGeneral(response);
			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
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
	private void imprimirPaginaGeneral( HttpServletResponse response) throws IOException
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
		respuesta.println( "                            Consultas <small>Informaci&#243n general</small>");
		respuesta.println( "                        </h1>");
		respuesta.println( "                        ");
		respuesta.println( "                    </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                <!--consultar existencias de productos-->");
		respuesta.println( "                 <form method=\"GET\" action=\"consultaGeneral.htm\">");
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
		respuesta.println( "                                    <span>Selecciones el proceso de producci&#243n: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <select name=\"procesoProduccion\"		>");
																try
																{
																	ArrayList<Integer> procesos=ProdAndes.darInstancia().darProcesosProduccion();
																	for(int i=0; i<procesos.size();i++)
																	{
																		respuesta.println( "                                    <option value=\""+procesos.get(i)+"\">"+procesos.get(i)+"</option>");
																	}
																}
																catch (Exception e)
																{
																	imprimirMensajeError(respuesta,"Error de carga", e.getMessage());
																}
		respuesta.println( "                                    </select>");
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
		for(int i=0;i<listaProductos.size();i++)
		{
			ProductoValue p=listaProductos.get(i);
			respuesta.println( "			<div id=\"littleWrap\" class=\"row\">");
			respuesta.println( "            	<div class=\"panel panel-default\" >");
			respuesta.println( "                	<div class=\"panel-heading\">");
			respuesta.println( "                    	<h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> La informaci&#243n del producto consultado</h3>");
			respuesta.println( "                    </div>");
			respuesta.println( "                    <br/>");
			respuesta.println( "                    <div class=\"panel-body\" id=\"wrap\">");
			respuesta.println( "                    	<div class=\"col-lg-4\">");
			respuesta.println( "                    		<p><b>Informacion del producto:</b>"+p.toString() +"</p>");
			respuesta.println( "                    	</div>");
			respuesta.println( "                	</div>");
			respuesta.println( "           		</div>");
			respuesta.println( "			</div>");
		}
		respuesta.println( "            <!--Buscar un producto-->");
		respuesta.println( "                <form method=\"GET\" action=\"consultaGeneral.htm\">");
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
		for(int i=0;i<listaBuscarProductos.size();i++)
		{
			MaterialValue m=listaBuscarProductos.get(i);
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
		respuesta.println( "                 <!--consultar existencias de materiales-->");
		respuesta.println( "                 <form method=\"GET\" action=\"consultaGeneral.htm\">");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar existencias de un Material</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br/>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Elija el tipo: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <Select name=\"tipo\">");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <i>Materia prima</i>");
		respuesta.println( "                                        </option>");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <iu>Componente</i> ");
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
		respuesta.println( "                                    <span>Indique el id de la etapa de producci&#243n: </span>");
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
		respuesta.println( "                                    <INPUT type=\"submit\" value=\"Consultar\" name=\"consultarExistenciasMaterial\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                            </div>");
		respuesta.println( "                        </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                </form>");
		respuesta.println( "");
		for(int i=0;i<listaRecursos.size();i++)
		{
			RecursoValue r= (RecursoValue) listaRecursos.get(i);
			respuesta.println( "             <div class=\"row\">");
			respuesta.println( "                        <div class=\"panel panel-default\" >");
			respuesta.println( "                            <div class=\"panel-heading\">");
			respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> La informaci&#243n del material consultado</h3>");
			respuesta.println( "                            </div>");
			respuesta.println( "                            <br/>");
			respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Tipo: "+ r.getTipoRecurso() +"</span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Nombre: "+ r.getNombre() +"</span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Cantidad Inicial: "+ r.getUnidadMedida() +"</span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                            </div>");
			respuesta.println( "                        </div>");
			respuesta.println( "                </div>");
		}
		respuesta.println( "                 <!--Buscar un material-->");
		respuesta.println( "                 <form method=\"GET\" action=\"consultaGeneral.htm\">");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar un Material</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br/>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "								<div class=\"col-lg-4\">");
		respuesta.println( "	                            	<span>Indique el volumen: </span>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                               	<INPUT type=\"number\" name=\"volumen\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique el costo: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"number\" name=\"costo\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique el rango de fechas de llegada: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                     <span>Desde: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"date\" name=\"desde\"/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <span>Hasta: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"date\" name=\"hasta\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-12\">");
		respuesta.println( "                                	<INPUT type=\"submit\" value=\"Buscar\" name=\"buscarMaterial\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                            </div>");
		respuesta.println( "                        </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                <!-- /.row -->");
		respuesta.println( "            </form>");
		for(int i=0;i<listaMateriales.size();i++)
		{
			MaterialValue r= (MaterialValue) listaMateriales.get(i);
			respuesta.println( "             <div class=\"row\">");
			respuesta.println( "                        <div class=\"panel panel-default\" >");
			respuesta.println( "                            <div class=\"panel-heading\">");
			respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> La informaci&#243n del material consultado</h3>");
			respuesta.println( "                            </div>");
			respuesta.println( "                            <br/>");
			respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Tipo: "+ r.getRecurso().getTipoRecurso() +"</span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Productos que compone: "+ r.getProductosQueCompone() +"</span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Etapas de producci&#243n en las que participa: "+ r.getEtapasProduccion() +" </span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Pedidos de compra en los que esta involucrado: "+ r.getPedidos() +" </span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                            </div>");
			respuesta.println( "                        </div>");
			respuesta.println( "                </div>");
		}
		
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