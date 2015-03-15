package vos;

import java.util.ArrayList;

public class MaterialValue 
{	
	private RecursoValue recurso;
	
	private String productosQueCompone;
	
	private String etapasProduccion;
	
	private String pedidos;
	
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
		this.productosQueCompone = productosQueCompone;
	}

	public String getEtapasProduccion() {
		return etapasProduccion;
	}
	public void setEtapasProduccion(String etapasProduccion) {
		this.etapasProduccion = etapasProduccion;
	}
	public void agregarEtapasProduccion(String etapasProduccion) {
		this.etapasProduccion += etapasProduccion +", ";
	}

	public String getPedidos() {
		return pedidos;
	}

	public void setPedidos(String pedidos) {
		this.pedidos = pedidos;
	}

	public void agregarPedidos(String pedidos) {
		this.pedidos+= pedidos + ", ";
		
	}
}
