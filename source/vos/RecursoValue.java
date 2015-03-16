package vos;

public class RecursoValue 
{
	public final static String materiaPrima = "Materia Prima";
	
	public final static String componente = "Componente";
	
	public final static String cIdRecurso = "idRecurso";
	
	public final static String cNombre = "nombre";
	
	public final static String cCantidadInicial = "cantidadInicial";
	
	public final static String cTipoRecurso = "tipoRecurso";
	
	private int idRecurso;
	
	private String nombre;
	
	private int cantidadInicial;
	
	private String tipoRecurso;
	
	public RecursoValue(){}
	
	public int getIdRecurso(){
		return idRecurso;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public int getCantidadInicial(){
		return cantidadInicial;
	}
	
	public String getTipoRecurso(){
		return tipoRecurso;
	}
	
	public void setIdRecurso(int idRecurso){
		this.idRecurso=idRecurso;
	}
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	public void setCantidadInicial(int cantidadInicial){
		this.cantidadInicial=cantidadInicial;
	}
	
	public void setTipoRecurso(String tipoRecurso){
		this.tipoRecurso=tipoRecurso;
	}
}
