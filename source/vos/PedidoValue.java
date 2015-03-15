package vos;

import java.sql.Date;

public class PedidoValue {

	public final static String cIdPedido = "idPedido";
	
	public final static String cCantidad= "cantidad";
	
	public final static String cMonto = "monto";
	
	public final static String cFechaEsperada = "fechaEsperada";
	
	public final static String cFechaLLegada = "fechaLlegada";
	
	public final static String cEstado = "estado";
	
	private int idPedido;
	
	private float monto;
	
	private Date fechaEsperada;
	
	private Date fechaLlegada;
	
	private String estado;
	
	public PedidoValue(){}

	public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public float getMonto() {
		return monto;
	}

	public void setMonto(float monto) {
		this.monto = monto;
	}

	public Date getFechaEsperada() {
		return fechaEsperada;
	}

	public void setFechaEsperada(Date fechaEsperada) {
		this.fechaEsperada = fechaEsperada;
	}

	public Date getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}
