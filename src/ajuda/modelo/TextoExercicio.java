package ajuda.modelo;

import icomb.util.I18n;

public class TextoExercicio extends TextoAjuda {
	public TextoExercicio() {
//        super("2   Exerc�cio");
        super(I18n.getInstance().getString("help2title"));
		
        insereParagrafo("help2p1");
//        insereParagrafo("Um exerc�cio do iComb dever� solicitar ao aluno que descubra qual � a quantidade de " +
//                "elementos de determinado universo que respeitem um s�rie de restri��es. O universo " +
//                "em quest�o dever� ser indicado no enunciado do exerc�cio.");

        insereParagrafo("help2p2");
//		insereParagrafo("O iComb oferece ao professor a possibilidade de criar e registrar exerc�cios " +
//						"em um servidor para que seus alunos possam acessar e responder as quest�es pela " +
//						"Internet.");

        insereSubTitulo("help2.1");
//        insereSubTitulo("2.1   Exemplo de Exerc�cio");
        insereParagrafo("help2.1p1");
//		insereParagrafo("Com um baralho de 32 cartas, quantas m�os de 5 cartas � poss�vel formar com exatamente " +
//						"2 cartas de Copas e exatamente duas cartas de Espadas?");

        insereParagrafo("help2.1p2");
//		insereParagrafo("No exerc�cio acima o universo em quest�o � \"o baralho de 32 cartas\" e as restri��es " +
//						"impostas s�o: quantas m�os de 5 cartas � poss�vel formar com exatamente " +
//						"2 cartas de Copas e exatamente duas cartas de Espadas?");
		
        insereSubTitulo("help2.2");
//		insereSubTitulo("2.2   Bot�o Iniciar");
        insereParagrafo("help2.2p1");
//		insereParagrafo("O bot�o Iniciar, ao acionado, abrir� a sess�o Ajuda que conter� uma breve explica��o " +
//						"do Universo em quest�o.");
	}
}
