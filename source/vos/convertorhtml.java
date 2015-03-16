package vos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class convertorhtml 
{
	// -----------------------------------------------------------------
    // Main
    // -----------------------------------------------------------------

    /**
     * Este método ejecuta la aplicación, creando una nueva interfaz
     * @param args

     */
    public static void main( String[] args ) 
    {

        try 
        {
			convertorhtml convertor = new convertorhtml( "./data/html/procesoDeProduccion.html");
		} 
        catch (Exception e) {
		
			e.printStackTrace();
		}
       
    }
	
    public convertorhtml( String rutaArchivoEntrada) throws Exception
    {
        File archivoE = new File( rutaArchivoEntrada );

        if( archivoE.exists( ) )
        {
            try
            {
                BufferedReader lector = new BufferedReader( new FileReader( archivoE ) );
               

                String linea = lector.readLine( );
                
                while( linea != null )
                {
                   String[] l= linea.split("");
                   System.out.print("respuesta.println( \"");
                   for(int i=0; i<l.length;i++)
                   {
                	   if(l[i].equals("\""))
                	   {
                		   System.out.print("\\\"");
                	   }
                	   else
                	   {
                		   System.out.print(l[i]);
                	   }
                   }
                   System.out.println("\");");
                   linea = lector.readLine( );
                }
                lector.close( );
                
            }
            catch( FileNotFoundException e )
            {
                e.printStackTrace();
            }
            catch( IOException e )
            {
            	e.printStackTrace();
            }
        }
    }
    
    

}
