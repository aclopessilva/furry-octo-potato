package ajuda.modelo;

/**
 * Exceção lançada quando se tenta criar um tópico com nível não
 * reconhecido pelo sistema de ajuda do iGraf. São permitidos apenas
 * três níveis: Topico.TITULO, Topico.SUBTITULO e Topico.SESSAO. 
 * @author Reginaldo
 *
 */
public class UnsuportedLevelException extends Exception {

		String mensagem = "Um tópico só pode ser definido " +
				          "com números inteiros de 0 a 2\n" +
				          "Utilize as constantes estáticas " +
				          "da classe Topico: TITULO, SUBTITULO ou SESSAO.\n";
		
		public String toString() {
			return mensagem;
		}
}
