package vos;

public class EtapaProduccionProductoValue
{
	//----------------------------------------------------
		// Constantes
		//----------------------------------------------------
		
		public final static String cIdEtapaProduccion = "idEtapaProduccion";
		
		public final static String cIdProducto = "idProducto";
		
		public final static String cNumeroEtapa = "numeroEtapa";

		//----------------------------------------------------
		// Atributos
		//----------------------------------------------------
		
		private int idEtapaProduccion;
		
		private String idProducto;
		
		private String numeroEtapa;
		
		//----------------------------------------------------
		// Métodos
		//----------------------------------------------------
		
		public EtapaProduccionProductoValue(){}

		public int getIdEtapaProduccion() {
			return idEtapaProduccion;
		}

		public void setIdEtapaProduccion(int idEtapaProduccion) {
			this.idEtapaProduccion = idEtapaProduccion;
		}

		public String getIdProducto() {
			return idProducto;
		}

		public void setIdProducto(String idProducto) {
			this.idProducto = idProducto;
		}

		public String getNumeroEtapa() {
			return numeroEtapa;
		}

		public void setNumeroEtapa(String numeroEtapa) {
			this.numeroEtapa = numeroEtapa;
		}
}
