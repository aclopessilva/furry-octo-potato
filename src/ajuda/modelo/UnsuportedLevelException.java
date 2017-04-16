package ajuda.modelo;

/**
 * Exce��o lan�ada quando se tenta criar um t�pico com n�vel n�o
 * reconhecido pelo sistema de ajuda do iGraf. S�o permitidos apenas
 * tr�s n�veis: Topico.TITULO, Topico.SUBTITULO e Topico.SESSAO. 
 * @author Reginaldo
 *
 */
public class UnsuportedLevelException extends Exception {

		String mensagem = "Um t�pico s� pode ser definido " +
				          "com n�meros inteiros de 0 a 2\n" +
				          "Utilize as constantes est�ticas " +
				          "da classe Topico: TITULO, SUBTITULO ou SESSAO.\n";
		
		public String toString() {
			return mensagem;
		}
}
