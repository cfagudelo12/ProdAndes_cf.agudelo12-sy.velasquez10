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
import vos.RecursoValue;
import fachada.ProdAndes;

public class ServletRecurso extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static String RUTA_ARCHIVO_SERIALIZADO = "/Empresa.data";
	private boolean seRegistro;
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
		seRegistro=false;
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

		String recurso = request.getParameter( "recursos" );
		String registrarMaterial = request.getParameter( "registrarMaterial" );
		String consultarExistencias = request.getParameter( "consultarExistencias" );
		String buscarMaterial = request.getParameter( "buscarMaterial" );

		if(recurso!=null)
		{
			try
			{
				listaMateriales = new ArrayList<MaterialValue>();
				listaRecursos = new ArrayList<RecursoValue>();
				imprimirPaginaMateriales(response);

			}
			catch( NumberFormatException e )
			{
				imprimirMensajeError(response.getWriter(), "Error", "Hubo un error cargando la pagina");
			}
		}
		if(registrarMaterial!=null)
		{
			try
			{
				String fecha = request.getParameter( "fechaLlegada" );
				String idPedido= request.getParameter( "idPedido" );
				String idRecurso = request.getParameter( "idRecurso" );
	
				ProdAndes.darInstancia().registrarLlegadaRecurso(Integer.parseInt(idRecurso), Integer.parseInt(idPedido),fechaLlegada);

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
			catch (Exception e) 
			{
				e.printStackTrace();
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
				String fechaS = request.getParameter( "fechaSolicitud" );
				String fechaE = request.getParameter( "fechaEntrega" );

				listaRecursos= ProdAndes.darInstancia().consultarExistenciasRecurso(tipo,Integer.parseInt(desde),Integer.parseInt(hasta),Integer.parseInt(etapaProduccion),fechaEntrega,fechaSolicitud);
				imprimirPaginaMateriales(response);
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
		if(buscarMaterial!=null)
		{
			try
			{
				String volumen = request.getParameter( "volumen" );
				String costo = request.getParameter( "costo" );
				String desde = request.getParameter( "desde" );
				String hasta = request.getParameter( "hasta" );
				
				listaMateriales= ProdAndes.darInstancia().consultarRecurso(Integer.parseInt(volumen), fechaDesde, fechaHasta, Float.parseFloat(costo));

				imprimirPaginaMateriales(response);
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
		respuesta.println( "            <!-- elementos de la parte de arriba del navbar- para pantallas pequeÃ±as -->");
		respuesta.println( "            <div  class=\"collapse navbar-collapse navbar-ex1-collapse\">");
		respuesta.println( "                <ul class=\"nav navbar-nav side-nav\">");
		respuesta.println( "                	<form method=\"GET\" action=\"producto.htm\">");
		respuesta.println( "                        <li>");
		respuesta.println( "                            <a><i class=\"fa fa-fw fa-dashboard\"></i> <Input id=\"BotonNegro\" type=\"submit\" value=\"Productos\" id=\"letraBlanca\" name=\"producto\" > </a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                    </form>");
		respuesta.println( "                     <form method=\"GET\" action=\"procesoProduccion.htm\">");
		respuesta.println( "                        <li>");
		respuesta.println( "                              <a><i class=\"fa fa-fw fa-bar-chart-o\"></i> <Input id=\"BotonNegro\" type=\"submit\" value=\"Proceso de producci&#243n\" id=\"letraBlanca\" name=\"procesoProduccion\" > </a>");
		respuesta.println( "                        </li>");
		respuesta.println( "                    </form>");
		respuesta.println( "                    <form method=\"GET\" action=\"recursos.htm\">");
		respuesta.println( "                        <li  class=\"active\">");
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
		respuesta.println( "                <form method=\"GET\" action=\"recursos.htm\">");
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
		respuesta.println( "                </form>");
		respuesta.println( "");
		respuesta.println( "                 <!--consultar existencias de materiales-->");
		respuesta.println( "                 <form method=\"GET\" action=\"recursos.htm\">");
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
		respuesta.println( "                                    <INPUT type=\"submit\" value=\"Consultar\" name=\"consultarExistencias\"/>");
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
		respuesta.println( "                 <form method=\"GET\" action=\"recursos.htm\">");
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
