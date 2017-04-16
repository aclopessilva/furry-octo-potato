package icomb.util;

public class Acao
{
	private Object origemDaAcao;
	private long milisegundos;
	
	public Acao(Object origemDaAcao,long milisegundos)
	{
		this.origemDaAcao = origemDaAcao;
		this.milisegundos = milisegundos;
	}
	
	public String toString()
	{
		String aux = "          " + milisegundos;
		aux = aux.substring(aux.length()-8);
		return "[" + aux + "s: " + origemDaAcao.toString() + "]";
	}
	
	
	
}