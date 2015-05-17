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

import vos.EjecucionValue;
import vos.EtapaProduccionValue;
import vos.MaterialValue;
import vos.PedidoValue;
import vos.ProductoValue;
import vos.RecursoValue;
import fachada.ProdAndes;



public class ServletConsultaGeneral extends HttpServlet
{


	private ArrayList<ProductoValue> listaProductos;
	private ArrayList<MaterialValue> listaBuscarProductos;
	private ArrayList<MaterialValue> listaMateriales;
	private ArrayList<RecursoValue> listaRecursos;
	private boolean listaVacia;
	private boolean escribioNada;
	private ArrayList<EjecucionValue> listaEjecucionEtapasProduccion;
	private ArrayList<PedidoValue> listaPedidos;
	private MaterialValue material;
	private int rowNum1;
	private int rowNum2;
	private String datosConsultarEjecucionEtapaProduccion;
	private String datosConsultarPedidos;
	private String datosConsultarRecursosId;

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
		listaEjecucionEtapasProduccion= new ArrayList<EjecucionValue>();
		listaPedidos= new ArrayList<PedidoValue>();
		material= null;
		listaVacia=false;
		escribioNada=false;
		rowNum1=1;
		rowNum2=5;
		datosConsultarEjecucionEtapaProduccion="";
		datosConsultarPedidos="";
		datosConsultarRecursosId="";
	}

	public void destroy( )
	{
		System.out.println("Destruyendo instancia");

	}
	public void inicializarValores()
	{
		listaProductos= new ArrayList<ProductoValue>();
		listaBuscarProductos= new ArrayList<MaterialValue>();
		listaMateriales= new ArrayList<MaterialValue>();
		listaRecursos=new ArrayList<RecursoValue>();
		listaEjecucionEtapasProduccion= new ArrayList<EjecucionValue>();
		listaPedidos= new ArrayList<PedidoValue>();
		material= null;
		listaVacia=false;
		escribioNada=false;
		rowNum1=1;
		rowNum2=5;
		datosConsultarEjecucionEtapaProduccion="";
		datosConsultarPedidos="";
		datosConsultarRecursosId="";
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
		String consultarEjecucionEtapaProduccion = request.getParameter( "consultarEjecucionEtapaProduccion" );
		String consultarPedidos = request.getParameter( "consultarPedidos" );
		String consultarRecursosId = request.getParameter( "consultarRecursosId" );
	
		if(consultarExistencias!=null)
		{
			try
			{	
				String desde = request.getParameter( "desde" );
				String hasta = request.getParameter( "hasta" );
				String procesoProduccion = request.getParameter( "procesoProduccion" );
				String fechaSolicitud = request.getParameter( "fechaSolicitud" );
				String fechaEntrega = request.getParameter( "fechaEntrega" );
				
				inicializarValores();
				listaProductos= ProdAndes.darInstancia().consultarExistenciasProductos(Integer.parseInt(desde),Integer.parseInt(hasta),Integer.parseInt(procesoProduccion),fechaSolicitud,fechaEntrega);
				if(listaProductos.size()==0)
				{
					listaVacia=true;
				}
				imprimirPaginaGeneral(response);
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
				String cantidad = request.getParameter( "cantidadBodega" );
				String costo = request.getParameter( "costo" );
				if(cantidad.equals("")&&costo.equals(""))
				{
					escribioNada=true;
					imprimirPaginaGeneral(response);
				}
				else
				{
					inicializarValores();
					listaBuscarProductos= ProdAndes.darInstancia().consultarProducto(Integer.parseInt(cantidad),Float.parseFloat(costo));
					if(listaBuscarProductos.size()==0)
					{
						listaVacia=true;
					}
					imprimirPaginaGeneral(response);
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
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
				if(desde.equals("")&&hasta.equals("")&&etapaProduccion.equals("")&&fechaSolicitud.equals(""))
				{
					escribioNada=true;
					imprimirPaginaGeneral(response);
				}
				inicializarValores();
				listaRecursos= ProdAndes.darInstancia().consultarExistenciasRecurso(tipo,Integer.parseInt(desde),Integer.parseInt(hasta),Integer.parseInt(etapaProduccion),fechaEntrega,fechaSolicitud);
				if(listaRecursos.size()==0)
				{
					listaVacia=true;
				}
				imprimirPaginaGeneral(response);
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
				String cantidadEnBodega = request.getParameter( "cantidadEnBodega" );
				String costo = request.getParameter( "costo" );
				String fechaDesde = request.getParameter( "desde" );
				String fechaHasta = request.getParameter( "hasta" );
				if(cantidadEnBodega.equals("")&&costo.equals("")&&fechaDesde.equals("")&&fechaHasta.equals(""))
				{
					escribioNada=true;
					imprimirPaginaGeneral(response);
				}
				else
				{
					listaMateriales= ProdAndes.darInstancia().consultarRecurso(Integer.parseInt(cantidadEnBodega), fechaDesde, fechaHasta, Float.parseFloat(costo));
					if(listaMateriales.size()==0)
					{
						listaVacia=true;
					}
					imprimirPaginaGeneral(response);
				}
			}
			catch (Exception e) 
			{
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(consultarEjecucionEtapaProduccion!=null)
		{
			try
			{
				String fechaDesde = request.getParameter( "desde" );
				String fechaHasta = request.getParameter( "hasta" );
				String correspondencia = request.getParameter( "correspondencia" );
				String material1 = request.getParameter( "material" );
				String tipoMaterial = request.getParameter( "tipoMaterial" );
				String pedido = request.getParameter( "pedido" );
				
				if(consultarEjecucionEtapaProduccion.equals("siguiente"))
				{
					rowNum1+=5;
					rowNum2+=5;
				}
				else if(consultarEjecucionEtapaProduccion.equals("anterior"))
				{
					rowNum1-=5;
					rowNum2-=5;
				}
				if(!consultarEjecucionEtapaProduccion.equals("Buscar"))
				{
					String[]datos=datosConsultarEjecucionEtapaProduccion.split("/");
					fechaDesde=datos[0];
					fechaHasta=datos[1];
					pedido=datos[2];
					pedido = pedido.replaceAll(" ", "");
					material1=datos[3];
					material1 = material1.replaceAll(" ", "");
					tipoMaterial=datos[4];
				}
				else
				{
					inicializarValores();
				}
				

				if(fechaDesde.equals("")&&fechaHasta.equals("")&& (material.equals("")||tipoMaterial.equals("")||pedido.equals("")))
				{
					escribioNada=true;
					imprimirPaginaGeneral(response);
				}
				else
				{
					if(correspondencia==null)
					{
						if(pedido.equals(""))
						{
							listaEjecucionEtapasProduccion= ProdAndes.darInstancia().consultarEjecucionEtapasProduccion(fechaDesde, fechaHasta,-1, material1, tipoMaterial,rowNum1,rowNum2);
						}
						else
						{
							listaEjecucionEtapasProduccion= ProdAndes.darInstancia().consultarEjecucionEtapasProduccion(fechaDesde, fechaHasta,Integer.parseInt(pedido), material1, tipoMaterial,rowNum1,rowNum2);
						}
					}	
					else
					{
						if(pedido.equals(""))
						{
							listaEjecucionEtapasProduccion= ProdAndes.darInstancia().consultarEjecucionEtapasProduccionNegado(fechaDesde, fechaHasta,-1, material1, tipoMaterial,rowNum1,rowNum2);
						}
						else
						{
							listaEjecucionEtapasProduccion= ProdAndes.darInstancia().consultarEjecucionEtapasProduccionNegado(fechaDesde, fechaHasta, Integer.parseInt(pedido), material1, tipoMaterial,rowNum1,rowNum2);
						}
					}
					if(listaEjecucionEtapasProduccion.size()==0)
					{
						listaVacia=true;
					}
					else
					{
						datosConsultarEjecucionEtapaProduccion=fechaDesde+"/"+fechaHasta+"/"+pedido+" /"+material1+" /"+tipoMaterial;
					}
					imprimirPaginaGeneral(response);
				}
			}
			catch (Exception e) 
			{
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		else if(consultarPedidos!=null)
		{
			try
			{
				String tipoRecurso = request.getParameter( "tipoRecurso" );
				String valor = request.getParameter( "valor" );
				if(consultarPedidos.equals("siguiente"))
				{
					rowNum1+=5;
					rowNum2+=5;
				}
				else if(consultarPedidos.equals("anterior"))
				{
					rowNum1-=5;
					rowNum2-=5;
				}
				if(!consultarPedidos.equals("Buscar"))
				{
					String[]datos=datosConsultarPedidos.split("/");
					tipoRecurso=datos[0];
					valor=datos[1];
				}
				else
				{
					inicializarValores();
				}
				if(tipoRecurso.equals("")&&valor.equals(""))
				{
					escribioNada=true;
					imprimirPaginaGeneral(response);
				}
				else
				{
					listaPedidos= ProdAndes.darInstancia().consultarPedidosPorCostoRecursoX(tipoRecurso, Integer.parseInt(valor),rowNum1,rowNum2);
					if(listaPedidos.size()==0)
					{
						listaVacia=true;
					}
					else
					{
						datosConsultarPedidos=tipoRecurso+"/"+valor;
					}
					imprimirPaginaGeneral(response);
				}
			}
			catch (Exception e) 
			{
				imprimirMensajeError(response.getWriter(), "Error", e.getMessage());
			}
		}
		
		else if(consultarRecursosId!=null)
		{
			try
			{
				String idRecurso = request.getParameter( "idRecurso" );
				if(consultarRecursosId.equals("siguiente"))
				{
					rowNum1+=5;
					rowNum2+=5;
				}
				else if(consultarRecursosId.equals("anterior"))
				{
					rowNum1-=5;
					rowNum2-=5;
				}
				if(!consultarRecursosId.equals("Buscar"))
				{
					idRecurso=datosConsultarRecursosId;
				}
				else
				{
					inicializarValores();
				}
				if(idRecurso.equals(""))
				{
					escribioNada=true;
					imprimirPaginaGeneral(response);
				}
				else
				{
					
					material= ProdAndes.darInstancia().consultaRecursoPorId(Integer.parseInt(idRecurso),rowNum1,rowNum2);
					if(material==null)
					{
						listaVacia=true;
					}
					else
					{
						datosConsultarRecursosId=idRecurso;
					}
					imprimirPaginaGeneral(response);
				}
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
			ArrayList<Integer> procesos=ProdAndes.darInstancia().darProcesosProduccion(1,20);
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
			respuesta.println( "                                    <p><b>Materiales que lo componen:</b> "+m.getRecursoQueLoCompone()+"</p>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <p><b>Pedidos de compra en los que esta involucrado:</b> "+m.getPedidos()+"</p>");
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
		respuesta.println( "	                            	<span>Indique la cantidad en bodega: </span>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                            	<br/>");
		respuesta.println( "	                               	<INPUT type=\"number\" name=\"cantidadEnBodega\"/>");
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
			respuesta.println( "                                    <p><b>Nombre:</b> "+ r.getRecurso().getNombre() +"</p>");
			respuesta.println( "                                </div>");
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

		
		respuesta.println( "                 <!--Consultar ejecucion etapa de produccion-->");
		respuesta.println( "                 <form method=\"GET\" action=\"consultaGeneral.htm\">");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar ejecucion etapa de produccion</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br/>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "								<div class=\"row\">");
		respuesta.println( "								<div class=\"col-lg-8\">");
		respuesta.println( "                                    <span>Indique el rango de fechas de ejecucion: </span>");
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
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Buscar NO correspondiendo al criterio: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"checkbox\" name=\"correspondencia\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique el material asociado: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"text\" name=\"material\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Elija el tipo de material asociado: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <Select name=\"tipoMaterial\">");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <i>Materia prima</i>");
		respuesta.println( "                                        </option>");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <iu>Componente</i> ");
		respuesta.println( "                                        </option>");
		respuesta.println( "                                    </Select>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Indique el pedido asociado: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"text\" name=\"pedido\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-12\">");
		respuesta.println( "                                	<INPUT type=\"submit\" value=\"Buscar\" name=\"consultarEjecucionEtapaProduccion\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                            </div>");
		respuesta.println( "                        </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                <!-- /.row -->");
		
		respuesta.println( "             <div class=\"panel-body\" id=\"wrap\">");
		for(int i=0;i<listaEjecucionEtapasProduccion.size();i++)
		{
			EjecucionValue e= (EjecucionValue) listaEjecucionEtapasProduccion.get(i);
			respuesta.println( "             <div class=\"row\">");
			respuesta.println( "                        <div class=\"panel panel-default\" >");
			respuesta.println( "                            <div class=\"panel-heading\">");
			respuesta.println( "                                <h3 class=\"panel-title\">"+(i+rowNum1)+". La informaci&#243n de la etapa de producci&#243n consultada</h3>");
			respuesta.println( "                            </div>");
			respuesta.println( "                            <br/>");
			respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
			respuesta.println( "                            <div class=\"row\">");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <p><b>Id:</b> "+ e.getEtapaProduccion().getIdEtapaProduccion() +"</p>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <p><b>Descripci&#243n: </b> "+ e.getEtapaProduccion().getDescripcion() +"</p>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                </div>");
			respuesta.println( "                            <div class=\"row\">");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <p><b>Fecha ejecucion: </b> "+ e.getFechaEjecucion()+"</p>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <p><b>Operario: </b>"+ e.getOperario().getId()+"</p>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                </div>");
			respuesta.println( "                            </div>");
			respuesta.println( "                        </div>");
			respuesta.println( "                </div>");
		}
		if(listaEjecucionEtapasProduccion.size()>0)
		{
			if(rowNum1>5)
			{
				respuesta.println( "                                <div class=\"col-lg-3\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"anterior\" name=\"consultarEjecucionEtapaProduccion\">");
				respuesta.println( "                                </div>");
			}
			respuesta.println( "                                <div class=\"col-lg-3\">");
			respuesta.println( "                                	<INPUT type=\"submit\" value=\"siguiente\" name=\"consultarEjecucionEtapaProduccion\">");
			respuesta.println( "                                </div>");
		}
		respuesta.println( "                </div>");
		respuesta.println( "            </form>");
		respuesta.println( "                 <!--Consultar pedidos-->");
		respuesta.println( "                 <form method=\"GET\" action=\"consultaGeneral.htm\">");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar pedidos</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br/>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "								<div class=\"row\">");
		respuesta.println( "								<div class=\"col-lg-6\">");
		respuesta.println( "                                <div class=\"col-lg-4\">");
		respuesta.println( "                                    <span>Elija el tipo: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <Select name=\"tipoRecurso\">");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <i>Materia prima</i>");
		respuesta.println( "                                        </option>");
		respuesta.println( "                                        <option>");
		respuesta.println( "                                            <iu>Componente</i> ");
		respuesta.println( "                                        </option>");
		respuesta.println( "                                    </Select>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-6\">");
		respuesta.println( "                                    <span>Ingrese un valor menor al minimo deseado: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"number\" name=\"valor\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-12\">");
		respuesta.println( "                                	<INPUT type=\"submit\" value=\"Buscar\" name=\"consultarPedidos\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                            </div>");
		respuesta.println( "                        </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                <!-- /.row -->");
		
		respuesta.println( "             <div class=\"panel-body\" id=\"wrap\">");
		for(int i=0;i<listaPedidos.size();i++)
		{
			PedidoValue p= (PedidoValue) listaPedidos.get(i);
			respuesta.println( "             <div class=\"row\">");
			respuesta.println( "                        <div class=\"panel panel-default\" >");
			respuesta.println( "                            <div class=\"panel-heading\">");
			respuesta.println( "                                <h3 class=\"panel-title\">"+(i+rowNum1)+" La informaci&#243n de la etapa de producci&#243n consultada</h3>");
			respuesta.println( "                            </div>");
			respuesta.println( "                            <br/>");
			respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <p><b>Id:</b> "+ p.getIdPedido()+"</p>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Monto: "+ p.getCosto() +"</span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                                <div class=\"col-lg-4\">");
			respuesta.println( "                                    <span>Estado: "+ p.getEstado()+"</span>");
			respuesta.println( "                                </div>");
			respuesta.println( "                            </div>");
			respuesta.println( "                        </div>");
			respuesta.println( "                </div>");
		}
		if(listaPedidos.size()>0)
		{
			if(rowNum1>5)
			{
				respuesta.println( "                                <div class=\"col-lg-3\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"anterior\" name=\"consultarPedidos\">");
				respuesta.println( "                                </div>");
			}
			respuesta.println( "                                <div class=\"col-lg-3\">");
			respuesta.println( "                                	<INPUT type=\"submit\" value=\"siguiente\" name=\"consultarPedidos\">");
			respuesta.println( "                                </div>");
		}
		respuesta.println( "                </div>");
		respuesta.println( "            </form>");
		respuesta.println( "                 <!--Consultar Recursos por Id-->");
		respuesta.println( "                 <form method=\"GET\" action=\"consultaGeneral.htm\">");
		respuesta.println( "                <div class=\"row\">");
		respuesta.println( "                        <div class=\"panel panel-default\" >");
		respuesta.println( "                            <div class=\"panel-heading\">");
		respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> Consultar recursos por Id</h3>");
		respuesta.println( "                            </div>");
		respuesta.println( "                            <br/>");
		respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
		respuesta.println( "								<div class=\"row\">");
		respuesta.println( "								<div class=\"col-lg-6\">");
		respuesta.println( "                                    <span>Indique el Id del recurso: </span>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <br/>");
		respuesta.println( "                                    <INPUT type=\"text\" name=\"idRecurso\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                </div>");
		respuesta.println( "                                <div class=\"col-lg-12\">");
		respuesta.println( "                                	<INPUT type=\"submit\" value=\"Buscar\" name=\"consultarRecursosId\"/>");
		respuesta.println( "                                </div>");
		respuesta.println( "                            </div>");
		respuesta.println( "                        </div>");
		respuesta.println( "                </div>");
		respuesta.println( "                <!-- /.row -->");

		if(material!=null)
		{
			
			respuesta.println( "             <div class=\"row\">");
			respuesta.println( "                        <div class=\"panel panel-default\" >");
			respuesta.println( "                            <div class=\"panel-heading\">");
			respuesta.println( "                                <h3 class=\"panel-title\"><i class=\"fa fa-check-square-o fa-fw\"></i> La informaci&#243n del material consultado</h3>");
			respuesta.println( "                            </div>");
			respuesta.println( "                            <br/>");
			respuesta.println( "                            <div class=\"panel-body\" id=\"wrap\">");
			respuesta.println( "                                <div class=\"col-lg-12\">");
			respuesta.println( "                                    <p><b>Pedidos de compra en los que esta involucrado:</b> </br> "+ material.getPedidos() +" </p>");
			respuesta.println( "                                </div>");
			respuesta.println( "                            </div>");
			respuesta.println( "                        </div>");
			respuesta.println( "                </div>");
			if(rowNum1>5)
			{
				respuesta.println( "                                <div class=\"col-lg-3\">");
				respuesta.println( "                                	<INPUT type=\"submit\" value=\"anterior\" name=\"consultarRecursosId\">");
				respuesta.println( "                                </div>");
			}
			respuesta.println( "                                <div class=\"col-lg-3\">");
			respuesta.println( "                                	<INPUT type=\"submit\" value=\"siguiente\" name=\"consultarRecursosId\">");
			respuesta.println( "                                </div>");
		}
		respuesta.println( "            </form>");
		respuesta.println( "    		</div>");
		respuesta.println( "		</div>");
		respuesta.println( "    </body>");
		
		if(listaVacia)
		{
			listaVacia=false;
			respuesta.println( "<script> alert(\"La solicitud no retorno ningún resultado\"); </script>");
		}
		else if(escribioNada)
		{
			escribioNada=false;
			respuesta.println( "<script> alert(\"Por favor llene los campos paran realizar la busqueda\"); </script>");
		}
		
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