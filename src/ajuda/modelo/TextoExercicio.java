package ajuda.modelo;

import icomb.util.I18n;

public class TextoExercicio extends TextoAjuda {
	public TextoExercicio() {
//        super("2   Exercício");
        super(I18n.getInstance().getString("help2title"));
		
        insereParagrafo("help2p1");
//        insereParagrafo("Um exercício do iComb deverá solicitar ao aluno que descubra qual é a quantidade de " +
//                "elementos de determinado universo que respeitem um série de restrições. O universo " +
//                "em questão deverá ser indicado no enunciado do exercício.");

        insereParagrafo("help2p2");
//		insereParagrafo("O iComb oferece ao professor a possibilidade de criar e registrar exercícios " +
//						"em um servidor para que seus alunos possam acessar e responder as questões pela " +
//						"Internet.");

        insereSubTitulo("help2.1");
//        insereSubTitulo("2.1   Exemplo de Exercício");
        insereParagrafo("help2.1p1");
//		insereParagrafo("Com um baralho de 32 cartas, quantas mãos de 5 cartas é possível formar com exatamente " +
//						"2 cartas de Copas e exatamente duas cartas de Espadas?");

        insereParagrafo("help2.1p2");
//		insereParagrafo("No exercício acima o universo em questão é \"o baralho de 32 cartas\" e as restrições " +
//						"impostas são: quantas mãos de 5 cartas é possível formar com exatamente " +
//						"2 cartas de Copas e exatamente duas cartas de Espadas?");
		
        insereSubTitulo("help2.2");
//		insereSubTitulo("2.2   Botão Iniciar");
        insereParagrafo("help2.2p1");
//		insereParagrafo("O botão Iniciar, ao acionado, abrirá a sessão Ajuda que conterá uma breve explicação " +
//						"do Universo em questão.");
	}
}
