package vos;

public class ProductoValue
{
	public final static String cIdProducto = "idProducto";
	
	public final static String cNombre = "nombre";
	
	public final static String cCosto = "costo";
	
	public final static String cUnidadesProducidas = "unidadesProducidas";
	
	public final static String cUnidadesEnProduccion = "unidadesEnProduccion";
	
	public final static String cUnidadesVendidas = "unidadesVendidas";
	
	public final static String cCantidadEnBodega = "cantidadEnBodega";
	
	public final static String cIdEmpresa = "idEmpresa";
	
	public final static String cIdProcesoProduccion = "idProcesoProduccion";
	
	private int idProducto;
	
	private String nombre;
	
	private float costo;
	
	private int unidadesProducidas;
	
	private int unidadesEnProduccion;
	
	private int unidadesVendidas;
	
	private int cantidadEnBodega;
	
	private int idEmpresa;
	
	private int idProcesoProduccion;
	
	public String toString()
	{
		return cNombre+" "+nombre+" "+cCosto+" "+costo+" "+cUnidadesProducidas+" "+unidadesProducidas+" "+cUnidadesEnProduccion+" "+unidadesEnProduccion+" "+cCantidadEnBodega+" "+cantidadEnBodega;
	}

	public ProductoValue(){}
	
	public int getIdProducto(){
		return idProducto;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public float getCosto(){
		return costo;
	}
	
	public int getUnidadesProducidas(){
		return unidadesProducidas;
	}
	
	public int getUnidadesEnProduccion(){
		return unidadesEnProduccion;
	}
	
	public int getUnidadesVendidas(){
		return unidadesVendidas;
	}
	
	public int getCantidadEnBodega(){
		return cantidadEnBodega;
	}
	
	public int getIdEmpresa(){
		return idEmpresa;
	}
	
	public int getIdProcesoProduccion(){
		return idProcesoProduccion;
	}
	
	public void setIdProducto(int idProducto){
		this.idProducto=idProducto;
	}
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	public void setCosto(float costo){
		this.costo=costo;
	}
	
	public void setUnidadesProducidas(int unidadesProducidas){
		this.unidadesProducidas=unidadesProducidas;
	}
	
	public void setUnidadesEnProduccion(int unidadesEnProduccion){
		this.unidadesEnProduccion=unidadesEnProduccion;
	}
	
	public void setUnidadesVendidas(int unidadesVendidas){
		this.unidadesVendidas=unidadesVendidas;
	}
	
	public void setCantidadEnBodega(int cantidadEnBodega){
		this.cantidadEnBodega=cantidadEnBodega;
	}
	
	public void setIdEmpresa(int idEmpresa){
		this.idEmpresa=idEmpresa;
	}
	
	public void setIdProcesoProduccion(int idProcesoProduccion){
		this.idProcesoProduccion=idProcesoProduccion;
	}
}
