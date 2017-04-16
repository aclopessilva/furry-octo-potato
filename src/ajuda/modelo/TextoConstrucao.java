package ajuda.modelo;

import icomb.util.I18n;

public class TextoConstrucao extends TextoAjuda {

	public TextoConstrucao() {
//        super("4   Construção");
        super(I18n.getInstance().getString("help4title"));
		
//        insereParagrafo("Uma \"Construção\" é uma lista de Estágios criados pelo aluno para a resolução do exerício. " +
//        "A secção \"Construção\" agrupa, portanto, todos os Estágios criados.");
        insereParagrafo("help4p1");
		
		
//        insereSubTitulo("4.1   Novo Estágio");
        insereSubTitulo("help4.1");
//        insereParagrafo("Como o próprio nome diz, o botão \"Novo Estágio\" irá criar um Estágio vazio " +
//                "na construção da solução do exerício. A construção deverá envolver 1 ou mais estágios " +
//                "para que o exerício seja resolvido. Uma vez que um novo estágio é criado, o aluno deverá " +
//                "então preencher as caixas de combinação apresentadas. ");
        insereParagrafo("help4.1p1");
		
		
//		insereSubTitulo("4.2   Quantidade de elementos");
        insereSubTitulo("help4.2a");
//        insereParagrafo("Em resposta a pergunta \"Eu quero\" o aluno deverá informar na caixa de combinação " +
//        "a quantidade de elementos desejados para este estágio." );
        insereParagrafo("help4.2ap1");
		
//		insereSubTitulo("4.2   Propriedades");
        insereSubTitulo("help4.2b");
//		insereParagrafo("Em resposta a pergunta \"Eu quero [x] elementos que verifiquem \" o aluno deverá informar " +
//						"quantas propriedades, ou restrições deseja utilizar para a definição deste estágio. As opções " +
//						"são nenhuma, 1 ou 2 propriedades.");
        insereParagrafo("help4.2ap1");

		
//		insereSubTitulo("4.3   Condição");
        insereSubTitulo("help4.3");
//		insereParagrafo("Uma \"Condição\" é representada por três controles consecutivas. Dependendo da resposta " +
//				"do aluno a caixa de combinação \"Propriedades\", nenhuma, uma ou duas \"Condições\" será apresentadas. ");
        insereParagrafo("help4.3p1");

//		insereParagrafo("A caixa de combinação \"Atributos\" apresenta qual atributo se deseja aplicar uma restrição.");
        insereParagrafo("help4.3p2");

//		insereParagrafo("A caixa de combinação \"Pertinência\" define qual é a relação de pertinência desejada. As opções são: " +
//				"\"é\", \"não é\", \"está em\" ou \"não está em\". Dependendo da opção solicitada o próximo controle apresentado será " +
//				"uma caixa de combinação ou um botão que acionará uma lista de múltipla escolha.");
        insereParagrafo("help4.3p3");
		
//		insereParagrafo("O terceiro controle apresentado na condição será uma caixa de combinação simples " +
//				"ou uma lista com seleção múltipla (acionada pelo botão contendo três pontinhos) dependendo da " +
//				"relação de pertinência selecionada no controle anterior. Estas listas conterão os valores do " +
//				"atributo selecionado para que o aluno complete a condição.");
        insereParagrafo("help4.3p4");
		
//		insereParagrafo("Caso duas propriedades tenham sido selecionadas, uma nova trinca de controles terá " +
//				"sido apresentada e o aluno deverá preenchelas da mesma maneira. Neste caso a condição completa possui" +
//				"duas restrições que devem ser respeitadas simultaneamente. Usando outras palavras, as duas condições são " +
//				"concatenadas com o operador lógico \"E\".");
        insereParagrafo("help4.3p5");
		
//		insereSubTitulo("4.4   Validar Estágio");
        insereSubTitulo("help4.4");
//		insereParagrafo("Depois de preenchida a Condição o botão \"Validar Estágio\" será habilitado. Neste ponto " +
//				"o aluno deverá informar quantos subconjuntos do universos poderão ser formado de maneira que a " +
//				"condição informada seja respeitada para este Estágio. O aluno deverá informar uma quantitade, porém " +
//				"utilizando as fórmulas disponíveis na caixa de combinação seguinte");
        insereParagrafo("help4.4p1");
		
//		insereSubTitulo("4.5   Fórmula");
        insereSubTitulo("help4.5");
//		insereParagrafo("A caixa de combinação \"Fórmula\" possui as opção \"valor\", \"C(n,p)\",\"A(n,p)\",\"n!\",\"n^p\". Dependendo " +
//				"da opção do aluno, uma ou duas caixas de texto serão abertas em resposta para a " +
//				"definição dos parâmetros \"n\" e \"p\". A opção \"valor\" pede um valor fixo, \"C(n,p)\" " +
//				"representa a fórmula da combinação n, p a p e o aluno deverá informar os valores de n e p. \"A(n,p)\" " +
//				"repre a fórmula de arranjo n, p a p. A opção \"n!\" equivale a n fatorial enquanto \"n^p\" representa" +
//				"n elevado a p." );
        insereParagrafo("help4.5p1");

//		insereParagrafo("Depois de informada a fórmula com os devidos parâmetros, o aluno deverá acionar o botão " +
//				"\"Ok\" e na sequência o botão \"Adicionar Estágio\"." );
        insereParagrafo("help4.5p2");
	
//		insereSubTitulo("4.6   Adicionar Estágio");
        insereSubTitulo("help4.6");
//		insereParagrafo("O botão \"Adicionar Estágio\" irá inserir na construção o estágio em questão. " +
//				"Em resposta desta operação, uma nova linha aparecerá na lista \"Lista de Estágios\".");
        insereParagrafo("help4.6p1");

//		insereSubTitulo("4.7   Lista de Estágios");
        insereSubTitulo("help4.7");
//		insereParagrafo("Esta lista apresenta todos os estágios que compõem a construção. Para excluir " +
//				"um estágio criado previamente basta selecioná-lo na lista e acionar o botão \"Excluir Estágio\".");
        insereParagrafo("help4.7p1");

//		insereSubTitulo("4.8   Somar ou Multiplicar");
        insereSubTitulo("help4.8");
//		insereParagrafo("Em determinado momento o aluno deverá estar satisfeito com os estágios criados e certo de que " +
//				"eles são suficientes para alcançar a solução desejada. Neste ponto, o aluno deverá definir qual operação " +
//				"realizar, soma ou multiplicação com a quantidade de elementos de cada um dos estágios da construção. Uma vez " +
//				"definido basta acionar o botão \"Validar Construção\" para encerrar a construção.");
        insereParagrafo("help4.8p1");

//		insereSubTitulo("4.9   Quadro de Imagens");
        insereSubTitulo("help4.9");
//		insereParagrafo("O quadro de imagens é uma importante ferramenta para o aluno conferir visualmente " +
//				"o resultado de sua construção enquanto os estágios vêem sendo definidos. A qualquer tempo, " +
//				"o aluno poderá clicar sobre o quadro de imagens que um novo cojunto de elementos será apresentado. " +
//				"Estes elementos respeitarão a construção definida até o momento e é uma ótima maneira do aluno" +
//				"perceber se algo está saindo errado."); 
        insereParagrafo("help4.9p1");

//		insereSubTitulo("4.10   Validar Construção");
        insereSubTitulo("help4.10");
//        insereParagrafo("O botão \"Validar Construção\" apresentará uma nova tela contendo um resumo " +
//				"da construção realizada pelo aluno.");
        insereParagrafo("help4.10p1");
		
//		insereSubTitulo("4.11   Alterar Construção");
        insereSubTitulo("help4.11");
//		insereParagrafo("O botão \"Alterar Construção\" retornará a construção para que o aluno possa" +
//				"realizar qualquer reparo.");
        insereParagrafo("help4.11p1");
		
//		insereSubTitulo("4.12   Eu Terminei!");
        insereSubTitulo("help4.12");
//		insereParagrafo("O botão \"Eu Terminei!\" apresenta a tela final. " +
//				"Neste ponto o aluno a construção está terminada só restando o envio ao servidor.");
        insereParagrafo("help4.12p1");
		
//		insereSubTitulo("4.13   Enviar");
        insereSubTitulo("help4.13");
//		insereParagrafo("O botão \"Enviar\" irá enviar a solução para o servidor.");
        insereParagrafo("help4.13p1");
		
		
		
		
	}
}
