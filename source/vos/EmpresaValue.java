package vos;

public class EmpresaValue
{
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String cIdEmpresa="idEmpresa";
	
	public final static String cNombre="nombre";
	
	public final static String cDescripcion="descripcion";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private int idEmpresa;
	
	private String nombre;
	
	private String descripcion;
	
	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------
	
	public EmpresaValue(){}
	
	public int getIdEmpresa(){
		return idEmpresa;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public String getDescripcion(){
		return descripcion;
	}
	
	public void setIdEmpresa(int idEmpresa){
		this.idEmpresa=idEmpresa;
	}
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	public void setDescripcion(String descripcion){
		this.descripcion=descripcion;
	}
}