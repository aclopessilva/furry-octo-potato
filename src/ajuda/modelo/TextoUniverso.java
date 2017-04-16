package ajuda.modelo;

import icomb.util.I18n;

public class TextoUniverso extends TextoAjuda {

	public TextoUniverso() {
//		super("3   Secção Ajuda");
        super(I18n.getInstance().getString("help3title"));
		
        insereParagrafo("help3p1");
//		insereParagrafo("A secção ajuda apresentará dicas para o aluno conforme a resolução do exerício" +
//						"é realizada. ");
		
        insereSubTitulo("help3.1");
//        insereSubTitulo("3.1   Resolver Exercício");
        insereParagrafo("help3.1p1");
//		insereParagrafo("O botão \"Resolver Exercício\" irá abrir a secção Construção e apresentará dois novos " +
//						"botões na secção Ajuda \"Mostrar Universo\" e \"Ajuda\"");

        insereSubTitulo("help3.2");
//		insereSubTitulo("3.2   Mostrar Universo");
        insereParagrafo("help3.2p1");
//		insereParagrafo("O botão \"Mostrar Universo\" abrirá uma nova janela apresentando todos os " +
//				"elementos do universo em questão. A imagem de cada elemento será apresentada bem como " +
//				"seus respectivos atributos.");
		
        insereSubTitulo("help3.3");
//		insereSubTitulo("3.3   Ajuda");
        insereParagrafo("help3.3p1");
//		insereParagrafo("O botão \"Ajuda\" apresenta a janela do sistema de Ajuda do iComb.");
		
		
	}
}
