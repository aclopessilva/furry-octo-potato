/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br / http://www.usp.br/line
 *
 * @author Leônidas de O. Brandão
 * @version
 * @date 09/04/2006; 22/05/2006; 12/07/2011
 *
 * @description Define os parâmetros de configuração do sistema iComb na forma de variáveis estáticas
 *              principalmente para interface gráfica
 *
 * @see ./base/MontaFace.java; ./icomb/IComb.java
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.util;

import java.awt.Font;
import java.awt.Color;

public class Configuracoes {

  /**
   * A string cujo conteúdo aparece alinhado à direita no 
   * painel superior (versão) do programa.
   * Após alterar 'strVersao' compilar também 'icomb/IComb.java' e ''
   */
  public static final String strVersao = "0.9.4";
      // "0.9.4": // Released 29/08/2014: acertos para abrir imagens de Universo em todas as 4 opcoes (JAR com e sem CMB e '-cp bin' idem)
      // "0.9.3": // Released 02/06/2014: tentativas de acertar carga de imagens (agora carregar e mostra universo, mas "flicker" volter qdo applet)
      // "0.9.2": // Released 31/05/2014: acerto para applet carregar GIF (path estava incompleto)
      // "0.9.1": // Released 30/05/2014: acertos para eliminar "loop" infinito em atualizacao de instaciais de elementos no 'DrawingPanel.paint(Graphics)'
      // "0.9.0": // Released 08/08/2013: acertos para ler arquivos e imagens em applet (principalmente um sem exercicio, definindo-o online)
      // "0.8.8": // Released 13/06/2013: tag 'iLM_PARAM_AssignmentURL' do protocolo iLM 2.0; acerto para via arquivo NAO entrar em branco o painel central
      // "0.8.7": // Released 20/07/2011: (milanesa) acerto aplicativo (habilitar corretamente botao Iniciar; habilitar botão para ler arquivo)
      // "0.8.6": // Released 12/07/2011: (milanesa) qdo applet sem parametro "lang" estava definindo 'ResourceBundle' com 'nu_BR', vindo de "null"
      // "0.8.5": // Released 11/07/2011: (milanesa) qdo applet nao estava habilitando botao Iniciar
      // "0.8.4": // Released 07/07/2011: muitos acertos de interface, de formula apos estagio 2
      // "0.8.3": // Released 04/07/2011: pequeno acerto para aplicativo que tentava pegar imagens via 'getCodeBase' de applet
      // "0.8.2": // Released 03/07/2011: muitos acertos (abrir c/ linha comando; faxina em botoes, agora pode-se voltar; acerto ToolTip)
      // "0.8.1": // Released 30/05/2011: pequeno acerto para aplicativo que tentava pegar imagens via 'getCodeBase' de applet
      // "0.8"; // Released 25/01/2010: tratamento de MA_PARAM_notSEND
      // "0.7"; // Released 13/10/2009: acertos em tratamento de imagem, dicas sob botões, internacionalizado help
      // "0.6"; // Released 24/02/2009: acertos p/ mostrar resposta em aplicativo
      // "0.4"; // Released 23/02/2009 (apenas try/catch em 'icomb/objects/Universo.interpretaCondicao')
      // "0.3"; // Released 22/02/2009 (final da noite)
      // "0.2"; // Released 22/02/2009
      // "0.1";  // 20,21/02/2009
      // "0.0.01 "; // Released 14/03/2008

  /**
   * A string cujo conteúdo aparece centralizado no painel 
   * superior (título) do programa.
   */
  public static final String strTitulo = "iComb - Combinatória Interativa na Internet ";

  public static final String fileICombMark = "# iComb: http://www.matematica.br"; // first line of any iComb file

  public static final String CODIFICACAO = "UTF-8"; // ou "ISO-8859-1": em 'icomb/ui/SetMachinePanel'
    
  /**
   * Constante que representa um deslocamento na direção horizontal.
   * Por padrão seu valor é 1.
   */
  public static final int dx = 1;// para deslocamentos

  /**
   * Constante que representa um deslocamento na direção horizontal.
   * Por padrão seu valor é 1.
   */
  public static final int dy = 1; // para deslocamentos

  /**
   * Valor usado para desconto em 'labelMensagens' e 'edicao'
   */
  public static final int descX = 10*dx;// para desconto em 'labelMensagens' e 'edicao'



  // painel: geral, contém "tudo"
  /*
   * A abscissa do canto esquerdo superior do painel principal
   */
  private static final int xTFP = 1;

  /*
   * A ordenada do canto esquerdo superior do painel principal
   */
  private static final int yTFP = 1;

  /**
   * iComb main dimension
   * 'cabecalho-icomb.gif' <E9> 741 x 32 / 'cabecalho-icomb_peq.gif' <E9> 542 x 32 
   */
  public static final int lTFP = 750; //  Largura do painel principal
  public static final int aTFP = 600; // Altura do painel principal

  /* Determina a altura dos label's título e versão */ 
  private static final int altF = 30;

  public static final int 
			  ALT  = 598,    // altura total da janela
			  ALT_LOGO = 36; // altura da imagem com logo: icomb/cabecalho_icomb.gif 741 x 32 - used in 'base.MontaFace'

  //  public static final int LARG = 500,    // largura total da janela
  //                          ALT  = 678,     // altura total da janela
  //                          ALT_LOGO = 42; // altura da imagem com logo: icomb/cabecalho_icomb.gif 741 x 32
  public static final int LARG_CENTRAL = 550, // largura do painel central (com o exerc.)
                          ALT_CENTRAL = 293; // altura do painel central (com o exerc.) - src/icomb/ui/VerticalPanel.java

  // largura, altura de botões: PainelDeBotoes.java

  /**
   * Largura dos botões dos painéis de botões
   */
  public static final int larBt = 20;

  /**
   * Altura dos botões dos painéis de botões
   */
  public static final int altBt = 34; 



  // titulo: iGraf - gráficos Interativos na Internet
  /**
   * A largura da área retangular ocupada pelo título do programa.
   */
  public static final int lTFNome=265;

  /**
   * A altura da área retangular ocupada pelo título do programa.
   */
  public static final int aTFNome=altF;

  /**
   * A abscissa do canto superior esquerdo da área retangular
   * ocupada pelo título do programa.
   */
  public static final int xTFNome=xTFP+(lTFP-lTFNome)/2;

  /**
   * A ordenada do canto superior esquerdo da área retangular
   * ocupada pelo título do programa.
   */
  public static final int yTFNome=yTFP+dy;



  // versão
  /**
   * A largura da área retangular ocupada pelo (label) versão do programa.
   */
  public static final int lTFVersao=120;

  /**
   * A altura da área retangular ocupada pelo (label) versão do programa.
   */
  public static final int aTFVersao=altF;

  /**
   * A abscissa do canto superior esquerdo da área retangular
   * ocupada pelo (label) versão do programa.
   */
  public static final int xTFVersao=xTFP+lTFP-lTFVersao;

  /**
   * A ordenada do canto superior esquerdo da área retangular
   * ocupada pelo (label) versão do programa.
   */
  public static final int yTFVersao=yTFP+dy;



  // painel de botões
  /**
   * A abscissa do canto superior esquerdo da área retangular
   * ocupada pelo painel de botões.
   */
  public static final int xTFPBt=xTFP+dx;

  /**
   * A ordenada do canto superior esquerdo da área retangular
   * ocupada pelo painel de botões.
   */
  public static final int yTFPBt=yTFP+aTFVersao+dy;

  /**
   * A largura da área retangular ocupada pelo painel de botões do programa.
   */
  public static final int lTFPBt=lTFP-dy;

  /**
   * A altura da área retangular ocupada pelo painel de botões do programa.
   */
  public static final int aTFPBt=altBt;



  // edição: área para digitar expressão aritmética
  /**
   * A abscissa do canto superior esquerdo da área retangular
   * ocupada pela área de edição de função.
   */
  public static final int xTFEdicao = 4;

  /**
   * A ordenada do canto superior esquerdo da área retangular
   * ocupada pela área de edição de função.
   */
  public static final int yTFEdicao=yTFPBt+aTFPBt+dy;

  /**
   * A largura da área retangular ocupada pelo painel de botões do programa.
   */
  public static final int lTFEdicao=lTFP-11;

  /**
   * A altura da área retangular ocupada pelo painel de botões do programa.
   */
  public static final int aTFEdicao=30;



  // área de desenho: na verdade é uma área para desenho, com os eixos
  /**
   * A abscissa do canto superior esquerdo da área retangular
   * ocupada pela área de desenho de função.
   */
  public static final int xTFADD = 4;

  /**
   * A ordenada do canto superior esquerdo da área retangular
   * ocupada pela área de desenho de função.
   */
  public static final int yTFADD=yTFEdicao+aTFEdicao+1;

  /**
   * A altura da área retangular ocupada pela área de desenho do programa.
   */
  public static final int lTFADD=lTFP-11;

  /**
   * A altura da área retangular ocupada pela área de desenho do programa.
   */
  public static final int aTFADD=aTFP-(aTFVersao+aTFPBt+aTFEdicao+55);


  //  barra de mensagem: parte inferior
  /**
   * A abscissa do canto superior esquerdo da área retangular
   * ocupada pela barra de mensagens.
   */
  public static final int xTFMsg = 4;

  /**
   * A abscissa do canto superior esquerdo da área retangular
   * ocupada pela barra de mensagens.
   */
  public static final int yTFMsg = yTFADD+aTFADD+3;

  /**
   * A largura da área retangular ocupada pelo label de mensagens na
   * parte inferior do programa.
   */
  public static final int lTFMsg = lTFP-11;

  /**
   * A altura da área retangular ocupada pelo label de mensagens na
   * parte inferior do programa.
   */
  public static final int aTFMsg = 24; 

  // Posicao para string "Versao: x" na barra superior
  public static final int xVersao = 390, yVersao = 10;

  // Fontes
  // Fonte barra superior iComb: para versao
  public static final Font FONTE_VERSAO = new Font("Arial", Font.PLAIN, 9); // Font("Arial", Font.PLAIN, 7);

  // Fonte botoes
  public static final Font FONTE_BOTAO = new Font("Arial", Font.PLAIN, 10);

  // Cores:  (0,50,100) = HTML: 00,32,64 --- (0,32,64) HTML: 00,20,40 --- (32,64,128) = HTML: 20,40,80
  public static final Color corBarraSupInf = new Color(0, 50, 100); // backgroud of header with iComb logo
         //
         // src/icomb/ui/CreateExercisePanel.java: panel of statement and hint of exercise
  public static final Color COR_AZUL = new Color(0,32,64); // corBarraSupInf; // cor de letra para paineis de estágio: para funco CINZA
         // src/base/MontaFace.java; src/icomb/components/CustomChoice.java; src/icomb/ui/DrawingPanel.java
     // 

  // Logo: barra 0,32,64 - fundo 0,50,100=003264
  public static final Color DARK_BLUE2BG = new Color(0, 32, 64); // color to back ground panels: Util.createDialog(...)

  public static final Color CLEAR_BG1 = Color.white; // src/ajuda/visao/navegador/NavigatorLabel.java

  public static final Color FACE_DARK1 = new Color(0, 50, 100); // src/icomb/ui/DrawingPanel.java;
  public static final Color FACE_CLEAR1 = Color.white; // src/base/EsquemaVisual.java; src/ajuda/visao/navegador/HelpButton.java
  public static final Color FACE_CLEAR2 = new Color(100, 150, 200); // src/ajuda/visao/navegador/NavigatorLabel.java - (100,150,200)-HTML=64,96,c8 / (50,100,150)-HTML=32,64,96

  public static final Color COR_DICA = new Color(255,215,221); // icomb/ui/ChooseUniversePanel.java

  // icomb.ui.ConstructionPanel:
  public static final int ALT_CENTRAL_2 = 440; // 590,383

  // icomb.ui.StagePanel: painel com estagio (definicao)
  public static final int LARG_CENTRAL_ESTAGIO = LARG_CENTRAL - 10; // 570
  public static final int ALT_CENTRAL_ESTAGIO = 178; // 181;              // 590,383

  public static final Color CINZA = new Color(230,230,230);

  public static final int
         corPretoRGB = -Color.black.getRGB(),   // Color.black => getRGB = -16777216 
         corBrancoRGB = -Color.white.getRGB(),  // Color.white => getRGB = -1
         corCinzaRGB = (corPretoRGB+corBrancoRGB)/2;  // Color.white => getRGB = -1

  // icomb.ui.StagePanel
  public static final Color CINZA_ESCURO = new Color(95, 95, 95);

  }
