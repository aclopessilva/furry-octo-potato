package ajuda.modelo;

import icomb.util.I18n;

public class TextoIcomb extends TextoAjuda {

	public TextoIcomb() {
//		super("1   iComb");
        super(I18n.getInstance().getString("help1title"));
		
        insereParagrafo("help1p1");
//		insereParagrafo("O iComb � um m�dulo acad�mico que auxilia o aluno na resolu��o de problemas " +
//						"de combinat�ria. Cada exerc�cio do iComb prop�em ao aluno uma meta imediatamente " +
//						"compreens�vel: Quantos? " +
//						"Para que o aluno atenda o pedido, isto �, fornecer a quantidade solicitada, dever� " +
//						"seguir alguns passos envolvendo a��es, modelagem e racioc�nio. ");
		
        insereSubTitulo("help1.1");
//        insereSubTitulo("1.1   Origens");
        insereParagrafo("help1.1p1");
//		insereParagrafo("O iComb foi inspirado na experi�ncia do software Combien?, sistema desenvolvido " +
//						"pela Universit� Pierre et Marie Curie - LIP6. Diferentemente do sistema Combien?, " +
//						"o iComb pode ser utilizado via internet e integrado a sistema gerenciadores de cursos. ");

        insereSubTitulo("help1.2");
//		insereSubTitulo("1.2   Universos");
        insereParagrafo("help1.2p1");
//		insereParagrafo("Cada exerc�cio do iComb refere-se a um Universo. Entende-se como " +
//						"universo um conjunto de elementos que partilham atributos comuns e que ser�o " +
//						"referenciados nos enunciados dos exerc�cios. O iComb foi projetado para " +
//						"permitir m�ltiplos universos de maneira que cada exerc�cio poder� estar " +
//						"relacionado com um universo diferente.");
		
	}
}
