package vos;

public class Recurso 
{
	public final static String MATERIA_PRIMA = "Materia Prima";
	
	public final static String COMPONENTE = "Componente";
	
	private int idRecurso;
	
	private String nombre;
	
	private int cantidadInicial;
	
	private String tipoRecurso;
	
	public Recurso(){}
	
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
