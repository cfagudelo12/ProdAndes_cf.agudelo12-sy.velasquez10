package vos;

public class UsuarioValue 
{	
	//----------------------------------------------------
	// Constantes
	//----------------------------------------------------
	
	public final static String cliente="Cliente";
	
	public final static String proveedor="Proveedor";
	
	public final static String empleado="Empleado";
	
	public final static String cId="id";
	
	public final static String cClave="clave";
	
	public final static String cTipoUsuario="tipoUsuario";
	
	public final static String cCedula="cedula";
	
	public final static String cNombre="nombre";
	
	public final static String cNacionalidad="nacionalidad";
	
	public final static String cDireccionFisica="direccionFisica";
	
	public final static String cTelefono="telefono";
	
	public final static String cEmail="email";
	
	public final static String cDepartamento="departamento";
	
	public final static String cCiudad="ciudad";
	
	public final static String cCodigoPostal="codigoPostal";
	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private int id;
	
	private String clave;
	
	private String tipoUsuario;
	
	private String cedula;
	
	private String nombre;
	
	private String nacionalidad;
	
	private String direccionFisica;
	
	private String telefono;
	
	private String email;
	
	private String departamento;
	
	private String ciudad;
	
	private String codigoPostal;
	
	//----------------------------------------------------
	// Métodos
	//----------------------------------------------------
	
	public int getId(){
		return id;
	}
	
	public String getClave(){
		return clave;
	}
	
	public String getTipoUsuario(){
		return tipoUsuario;
	}
	
	public String getCedula(){
		return cedula;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public String getNacionalidad(){
		return nacionalidad;
	}
	
	public String getDireccionFisica(){
		return direccionFisica;
	}
	
	public String getTelefono(){
		return telefono;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getDepartamento(){
		return departamento;
	}
	
	public String getCiudad(){
		return ciudad;
	}
	
	public String getCodigoPostal(){
		return codigoPostal;
	}

	public void setId(int id){
		this.id=id;
	}
	
	public void setClave(String clave){
		this.clave=clave;
	}
	
	public void setTipoUsuario(String tipoUsuario){
		this.tipoUsuario=tipoUsuario;
	}
	
	public void setCedula(String cedula){
		this.cedula=cedula;
	}
	
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	
	public void setNacionalidad(String nacionalidad){
		this.nacionalidad=nacionalidad;
	}
	
	public void setDireccionFisica(String direccionFisica){
		this.direccionFisica=direccionFisica;
	}
	
	public void setTelefono(String telefono){
		this.telefono=telefono;
	}
	
	public void setEmail(String email){
		this.email=email;
	}
	
	public void setDepartamento(String departamento){
		this.departamento=departamento;
	}
	
	public void setCiudad(String ciudad){
		this.ciudad=ciudad;
	}
	
	public void setCodigoPostal(String codigoPostal){
		this.codigoPostal=codigoPostal;
	}
}