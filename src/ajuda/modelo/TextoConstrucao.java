package ajuda.modelo;

import icomb.util.I18n;

public class TextoConstrucao extends TextoAjuda {

	public TextoConstrucao() {
//        super("4   Constru��o");
        super(I18n.getInstance().getString("help4title"));
		
//        insereParagrafo("Uma \"Constru��o\" � uma lista de Est�gios criados pelo aluno para a resolu��o do exer�cio. " +
//        "A sec��o \"Constru��o\" agrupa, portanto, todos os Est�gios criados.");
        insereParagrafo("help4p1");
		
		
//        insereSubTitulo("4.1   Novo Est�gio");
        insereSubTitulo("help4.1");
//        insereParagrafo("Como o pr�prio nome diz, o bot�o \"Novo Est�gio\" ir� criar um Est�gio vazio " +
//                "na constru��o da solu��o do exer�cio. A constru��o dever� envolver 1 ou mais est�gios " +
//                "para que o exer�cio seja resolvido. Uma vez que um novo est�gio � criado, o aluno dever� " +
//                "ent�o preencher as caixas de combina��o apresentadas. ");
        insereParagrafo("help4.1p1");
		
		
//		insereSubTitulo("4.2   Quantidade de elementos");
        insereSubTitulo("help4.2a");
//        insereParagrafo("Em resposta a pergunta \"Eu quero\" o aluno dever� informar na caixa de combina��o " +
//        "a quantidade de elementos desejados para este est�gio." );
        insereParagrafo("help4.2ap1");
		
//		insereSubTitulo("4.2   Propriedades");
        insereSubTitulo("help4.2b");
//		insereParagrafo("Em resposta a pergunta \"Eu quero [x] elementos que verifiquem \" o aluno dever� informar " +
//						"quantas propriedades, ou restri��es deseja utilizar para a defini��o deste est�gio. As op��es " +
//						"s�o nenhuma, 1 ou 2 propriedades.");
        insereParagrafo("help4.2ap1");

		
//		insereSubTitulo("4.3   Condi��o");
        insereSubTitulo("help4.3");
//		insereParagrafo("Uma \"Condi��o\" � representada por tr�s controles consecutivas. Dependendo da resposta " +
//				"do aluno a caixa de combina��o \"Propriedades\", nenhuma, uma ou duas \"Condi��es\" ser� apresentadas. ");
        insereParagrafo("help4.3p1");

//		insereParagrafo("A caixa de combina��o \"Atributos\" apresenta qual atributo se deseja aplicar uma restri��o.");
        insereParagrafo("help4.3p2");

//		insereParagrafo("A caixa de combina��o \"Pertin�ncia\" define qual � a rela��o de pertin�ncia desejada. As op��es s�o: " +
//				"\"�\", \"n�o �\", \"est� em\" ou \"n�o est� em\". Dependendo da op��o solicitada o pr�ximo controle apresentado ser� " +
//				"uma caixa de combina��o ou um bot�o que acionar� uma lista de m�ltipla escolha.");
        insereParagrafo("help4.3p3");
		
//		insereParagrafo("O terceiro controle apresentado na condi��o ser� uma caixa de combina��o simples " +
//				"ou uma lista com sele��o m�ltipla (acionada pelo bot�o contendo tr�s pontinhos) dependendo da " +
//				"rela��o de pertin�ncia selecionada no controle anterior. Estas listas conter�o os valores do " +
//				"atributo selecionado para que o aluno complete a condi��o.");
        insereParagrafo("help4.3p4");
		
//		insereParagrafo("Caso duas propriedades tenham sido selecionadas, uma nova trinca de controles ter� " +
//				"sido apresentada e o aluno dever� preenchelas da mesma maneira. Neste caso a condi��o completa possui" +
//				"duas restri��es que devem ser respeitadas simultaneamente. Usando outras palavras, as duas condi��es s�o " +
//				"concatenadas com o operador l�gico \"E\".");
        insereParagrafo("help4.3p5");
		
//		insereSubTitulo("4.4   Validar Est�gio");
        insereSubTitulo("help4.4");
//		insereParagrafo("Depois de preenchida a Condi��o o bot�o \"Validar Est�gio\" ser� habilitado. Neste ponto " +
//				"o aluno dever� informar quantos subconjuntos do universos poder�o ser formado de maneira que a " +
//				"condi��o informada seja respeitada para este Est�gio. O aluno dever� informar uma quantitade, por�m " +
//				"utilizando as f�rmulas dispon�veis na caixa de combina��o seguinte");
        insereParagrafo("help4.4p1");
		
//		insereSubTitulo("4.5   F�rmula");
        insereSubTitulo("help4.5");
//		insereParagrafo("A caixa de combina��o \"F�rmula\" possui as op��o \"valor\", \"C(n,p)\",\"A(n,p)\",\"n!\",\"n^p\". Dependendo " +
//				"da op��o do aluno, uma ou duas caixas de texto ser�o abertas em resposta para a " +
//				"defini��o dos par�metros \"n\" e \"p\". A op��o \"valor\" pede um valor fixo, \"C(n,p)\" " +
//				"representa a f�rmula da combina��o n, p a p e o aluno dever� informar os valores de n e p. \"A(n,p)\" " +
//				"repre a f�rmula de arranjo n, p a p. A op��o \"n!\" equivale a n fatorial enquanto \"n^p\" representa" +
//				"n elevado a p." );
        insereParagrafo("help4.5p1");

//		insereParagrafo("Depois de informada a f�rmula com os devidos par�metros, o aluno dever� acionar o bot�o " +
//				"\"Ok\" e na sequ�ncia o bot�o \"Adicionar Est�gio\"." );
        insereParagrafo("help4.5p2");
	
//		insereSubTitulo("4.6   Adicionar Est�gio");
        insereSubTitulo("help4.6");
//		insereParagrafo("O bot�o \"Adicionar Est�gio\" ir� inserir na constru��o o est�gio em quest�o. " +
//				"Em resposta desta opera��o, uma nova linha aparecer� na lista \"Lista de Est�gios\".");
        insereParagrafo("help4.6p1");

//		insereSubTitulo("4.7   Lista de Est�gios");
        insereSubTitulo("help4.7");
//		insereParagrafo("Esta lista apresenta todos os est�gios que comp�em a constru��o. Para excluir " +
//				"um est�gio criado previamente basta selecion�-lo na lista e acionar o bot�o \"Excluir Est�gio\".");
        insereParagrafo("help4.7p1");

//		insereSubTitulo("4.8   Somar ou Multiplicar");
        insereSubTitulo("help4.8");
//		insereParagrafo("Em determinado momento o aluno dever� estar satisfeito com os est�gios criados e certo de que " +
//				"eles s�o suficientes para alcan�ar a solu��o desejada. Neste ponto, o aluno dever� definir qual opera��o " +
//				"realizar, soma ou multiplica��o com a quantidade de elementos de cada um dos est�gios da constru��o. Uma vez " +
//				"definido basta acionar o bot�o \"Validar Constru��o\" para encerrar a constru��o.");
        insereParagrafo("help4.8p1");

//		insereSubTitulo("4.9   Quadro de Imagens");
        insereSubTitulo("help4.9");
//		insereParagrafo("O quadro de imagens � uma importante ferramenta para o aluno conferir visualmente " +
//				"o resultado de sua constru��o enquanto os est�gios v�em sendo definidos. A qualquer tempo, " +
//				"o aluno poder� clicar sobre o quadro de imagens que um novo cojunto de elementos ser� apresentado. " +
//				"Estes elementos respeitar�o a constru��o definida at� o momento e � uma �tima maneira do aluno" +
//				"perceber se algo est� saindo errado."); 
        insereParagrafo("help4.9p1");

//		insereSubTitulo("4.10   Validar Constru��o");
        insereSubTitulo("help4.10");
//        insereParagrafo("O bot�o \"Validar Constru��o\" apresentar� uma nova tela contendo um resumo " +
//				"da constru��o realizada pelo aluno.");
        insereParagrafo("help4.10p1");
		
//		insereSubTitulo("4.11   Alterar Constru��o");
        insereSubTitulo("help4.11");
//		insereParagrafo("O bot�o \"Alterar Constru��o\" retornar� a constru��o para que o aluno possa" +
//				"realizar qualquer reparo.");
        insereParagrafo("help4.11p1");
		
//		insereSubTitulo("4.12   Eu Terminei!");
        insereSubTitulo("help4.12");
//		insereParagrafo("O bot�o \"Eu Terminei!\" apresenta a tela final. " +
//				"Neste ponto o aluno a constru��o est� terminada s� restando o envio ao servidor.");
        insereParagrafo("help4.12p1");
		
//		insereSubTitulo("4.13   Enviar");
        insereSubTitulo("help4.13");
//		insereParagrafo("O bot�o \"Enviar\" ir� enviar a solu��o para o servidor.");
        insereParagrafo("help4.13p1");
		
		
		
		
	}
}
