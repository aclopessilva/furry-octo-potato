package ajuda.modelo;

import icomb.util.I18n;

public class TextoUniverso extends TextoAjuda {

	public TextoUniverso() {
//		super("3   Sec��o Ajuda");
        super(I18n.getInstance().getString("help3title"));
		
        insereParagrafo("help3p1");
//		insereParagrafo("A sec��o ajuda apresentar� dicas para o aluno conforme a resolu��o do exer�cio" +
//						"� realizada. ");
		
        insereSubTitulo("help3.1");
//        insereSubTitulo("3.1   Resolver Exerc�cio");
        insereParagrafo("help3.1p1");
//		insereParagrafo("O bot�o \"Resolver Exerc�cio\" ir� abrir a sec��o Constru��o e apresentar� dois novos " +
//						"bot�es na sec��o Ajuda \"Mostrar Universo\" e \"Ajuda\"");

        insereSubTitulo("help3.2");
//		insereSubTitulo("3.2   Mostrar Universo");
        insereParagrafo("help3.2p1");
//		insereParagrafo("O bot�o \"Mostrar Universo\" abrir� uma nova janela apresentando todos os " +
//				"elementos do universo em quest�o. A imagem de cada elemento ser� apresentada bem como " +
//				"seus respectivos atributos.");
		
        insereSubTitulo("help3.3");
//		insereSubTitulo("3.3   Ajuda");
        insereParagrafo("help3.3p1");
//		insereParagrafo("O bot�o \"Ajuda\" apresenta a janela do sistema de Ajuda do iComb.");
		
		
	}
}
