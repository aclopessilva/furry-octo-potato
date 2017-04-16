package ajuda.modelo;


public final class ListaTextoAjuda {	
	
	private static final TextoIcomb ti = new TextoIcomb();
	private static final TextoUniverso tu = new TextoUniverso();
	private static final TextoConstrucao  tc = new TextoConstrucao();
	private static final TextoExercicio te = new TextoExercicio();
	
	private static TextoAjuda[] lista = {ti,te,tu,tc};
	
	public static final int numTopicos = lista.length;
	
	public static TextoAjuda getTexto(int i){
		if(i < 0 || i > numTopicos)
			throw new IllegalArgumentException("Índice inválido: " + i + "\nO índice para acesso a um " +
					  "texto da ajuda varia entre 0 e " + numTopicos);
		return lista[i];
	}
}
