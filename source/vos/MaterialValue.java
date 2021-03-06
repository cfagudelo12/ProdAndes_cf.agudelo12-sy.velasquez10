package vos;

public class MaterialValue 
{	
	//----------------------------------------------------
	// Atributos
	//----------------------------------------------------
	
	private RecursoValue recurso;
	
	private ProductoValue produto;
	
	private String materialesQueLoComponen;
	
	private String productosQueCompone;
	
	private String etapasProduccion;
	
	private String pedidos;
	
	private String recursoQueLoCompone;
	
	//----------------------------------------------------
	// M�todos
	//----------------------------------------------------
	
	public MaterialValue(){}

	public RecursoValue getRecurso() {
		return recurso;
	}

	public void setRecurso(RecursoValue recurso) {
		this.recurso = recurso;
	}

	public String getProductosQueCompone() {
		return productosQueCompone;
	}
	

	public void agregarProductos(String productos) {	
		this.productosQueCompone += productos + ", ";
	}

	public void setProductosQueCompone(String productosQueCompone) {
		if(this.productosQueCompone==null)
			setProductosQueCompone(productosQueCompone);
		else
		this.productosQueCompone+= ", "+productosQueCompone;
	}

	public String getEtapasProduccion() {
		return etapasProduccion;
	}
	public void setEtapasProduccion(String etapasProduccion) {
		this.etapasProduccion = etapasProduccion;
	}
	public void agregarEtapasProduccion(String etapasProduccion) {
		if(this.etapasProduccion==null)
			setEtapasProduccion(etapasProduccion);
		else
		this.etapasProduccion+= ", "+etapasProduccion;
	}

	public String getPedidos() {
		return pedidos;
	}

	public void setPedidos(String pedidos) {
		this.pedidos = pedidos;
	}

	public void agregarPedidos(String pedidos) {
		if(this.pedidos==null)
			setPedidos(pedidos);
		else
		this.pedidos+= "</br> "+pedidos;
	}

	public ProductoValue getProduto() {
		return produto;
	}

	public void setProduto(ProductoValue produto) {
		this.produto = produto;
	}

	public String getMaterialesQueLoComponen() {
		return materialesQueLoComponen;
	}

	public void setMaterialesQueLoComponen(String materialesQueLoComponen) {
		this.materialesQueLoComponen = materialesQueLoComponen;
	}

	public void agregarRecursoQueLoCompone(String recurso) 
	{
		if(this.recursoQueLoCompone==null)
		{
			setRecursoQueLoCompone(recurso);
		}
		else
		{
			this.recursoQueLoCompone+= ", "+recurso ;
		}
	}
	public String getRecursoQueLoCompone() {
		return recursoQueLoCompone;
	}

	public void setRecursoQueLoCompone(String recursoQueLoCompone) {
		this.recursoQueLoCompone = recursoQueLoCompone;
	}
}