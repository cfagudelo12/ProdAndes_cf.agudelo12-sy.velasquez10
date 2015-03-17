package vos;

public class RecursoValue 
{
	public final static String materiaPrima = "Materia Prima";
	
	public final static String componente = "Componente";
	
	public final static String cIdRecurso = "idRecurso";
	
	public final static String cNombre = "nombre";
	
	public final static String cTipoRecurso = "tipoRecurso";
	
	public final static String cUnidadMedida = "unidadMedida";
	
	public final static String cIdProveedor = "idProveedor";
	
	public final static String cCantidadMDistribucion = "cantidadMDistribucion";
	
	public final static String cTiempoEntrega = "tiempoEntrega";
	
	private int idRecurso;
	
	private String nombre;
	
	private String tipoRecurso;
	
	private String unidadMedida;
	
	private int idProveedor;
	
	private int cantidadMDistribucion;
	
	private int tiempoEntrega;
	
	public RecursoValue(){}
	
	public int getIdRecurso(){
		return idRecurso;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public String getTipoRecurso(){
		return tipoRecurso;
	}
	
	public String getUnidadMedida(){
		return unidadMedida;
	}
	
	public int getIdProveedor(){
		return idProveedor;
	}
	
	public int getCantidadMDistribucion(){
		return cantidadMDistribucion;
	}
	
	public int getTiempoEntrega(){
		return tiempoEntrega;
	}
	
	public void setIdRecurso(int idRecurso){
		this.idRecurso=idRecurso;
	}
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	public void setTipoRecurso(String tipoRecurso){
		this.tipoRecurso=tipoRecurso;
	}
	
	public void setUnidadMedida(String unidadMedida){
		this.unidadMedida=unidadMedida;
	}
	
	public void setIdProveedor(int idProveedor){
		this.idProveedor=idProveedor;
	}
	
	public void setCantidadMDistribucion(int cantidadMDistribucion){
		this.cantidadMDistribucion=cantidadMDistribucion;
	}
	
	public void setTiempoEntrega(int tiempoEntrega){
		this.tiempoEntrega=tiempoEntrega;
	}
}
