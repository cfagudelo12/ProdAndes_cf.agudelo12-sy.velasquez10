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

public class ServletMateriales
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

			String registrarMaterial = request.getParameter( "registrarMaterial" );
			
			if(registrarMaterial!=null)
			{
				try
				{
					String nombre = request.getParameter( "nombre" );
					String cantidad = request.getParameter( "dantidade" );
					String tipo = request.getParameter( "tipo" );
					seRegistro = ProdAndes.darInstancia().registrarMaterial(nombre,cantidad,tipo);
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
			respuesta.println( "	<body>");
			
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
