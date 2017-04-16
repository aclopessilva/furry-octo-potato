package ajuda.modelo;

import icomb.util.I18n;

public class TextoIcomb extends TextoAjuda {

	public TextoIcomb() {
//		super("1   iComb");
        super(I18n.getInstance().getString("help1title"));
		
        insereParagrafo("help1p1");
//		insereParagrafo("O iComb é um módulo acadêmico que auxilia o aluno na resolução de problemas " +
//						"de combinatória. Cada exercício do iComb propõem ao aluno uma meta imediatamente " +
//						"compreensível: Quantos? " +
//						"Para que o aluno atenda o pedido, isto é, fornecer a quantidade solicitada, deverá " +
//						"seguir alguns passos envolvendo ações, modelagem e raciocínio. ");
		
        insereSubTitulo("help1.1");
//        insereSubTitulo("1.1   Origens");
        insereParagrafo("help1.1p1");
//		insereParagrafo("O iComb foi inspirado na experiência do software Combien?, sistema desenvolvido " +
//						"pela Université Pierre et Marie Curie - LIP6. Diferentemente do sistema Combien?, " +
//						"o iComb pode ser utilizado via internet e integrado a sistema gerenciadores de cursos. ");

        insereSubTitulo("help1.2");
//		insereSubTitulo("1.2   Universos");
        insereParagrafo("help1.2p1");
//		insereParagrafo("Cada exercício do iComb refere-se a um Universo. Entende-se como " +
//						"universo um conjunto de elementos que partilham atributos comuns e que serão " +
//						"referenciados nos enunciados dos exercícios. O iComb foi projetado para " +
//						"permitir múltiplos universos de maneira que cada exercício poderá estar " +
//						"relacionado com um universo diferente.");
		
	}
}
