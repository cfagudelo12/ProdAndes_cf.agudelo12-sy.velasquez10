package vos;

public class ProveedorValue extends UsuarioValue
{
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String cIdRepresentanteLegal="idRepresentanteLegal";
	
	public final static String cVolumenMaximo="volumenMaximo";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private int idRepresentanteLegal;
	
	private int volumenMaximo;
	
	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------
	
	public ProveedorValue(){}
	
	public int getIdRepresentanteLegal(){
		return idRepresentanteLegal;
	}
	
	public int getVolumenMaximo(){
		return volumenMaximo;
	}
	
	public void setIdRepresentanteLegal(int idRepresentanteLegal){
		this.idRepresentanteLegal=idRepresentanteLegal;
	}
	
	public void setVolumenMaximo(int volumenMaximo){
		this.volumenMaximo=volumenMaximo;
	}
}