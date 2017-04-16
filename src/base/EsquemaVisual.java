/*
 * iComb: http://www.matematica.br/icomb
 * 
 * <p>iComb: a free system to teach/learn combinatorics</p>
 * <p>iMath Interactive Learning Module</p>
 * 
 * @author Le�nidas de O. Brand�o
 * 
 */

package base;

import java.awt.Color;
import java.awt.Font;

import icomb.util.Configuracoes;

public interface EsquemaVisual {

  public static final Color corBarraSupInf = Configuracoes.corBarraSupInf; // Color(32, 64, 128) = HTML: 20 40 80
  
  public static Color corAreaDesenho = new Color (247, 247, 247);
  
  /**
   * A cor de fundo do painel MontaFace.
   */
  public static final Color corFundo = new Color( 80, 96, 255);
  
  //__ Incluindo letras em 'CustomChoice' de 'icomb/ui/StagePanel.secondLine()'
  // + Cor de fundo da �rea de digita��o das express�es matem�ticas. Primeira caixa-propriedade do 'CustomChoice'
  public static final Color corFundoEdicao = Configuracoes.COR_AZUL; // Configuracoes.corBarraSupInf

  // + A cor das letras dos pain�is que exibem texto. Segunda caixa-propriedade do 'CustomChoice'
  public static final Color corLetras = Configuracoes.COR_AZUL; // Configuracoes.corBarraSupInf

  // + Padroniza a cor das letras dos bot�es que, por padr�o, � branca. Terceira caixa-propriedade do 'CustomChoice'
  public static final Color corLetrasBotoes = Configuracoes.COR_AZUL; //Color.white;
  //__

  public static final Color corLetrasRotulos = Configuracoes.COR_AZUL;//Color.black,
  
  /**
   * Determina a cor para o fundo de todos os bot�es.
   */
  public static final Color corFundoBotoes  = new Color(63,127,255);
  
  public static final Color corAcesa  = Configuracoes.FACE_CLEAR1; // Color.white
  
  /**
   * Fonte Helv�tica, negrito(bold), tamanho 12.
   */
  public static final Font fontHB12 = new Font("Helvetica", Font.BOLD, 12);
  
  /**
   * Fonte Helv�tica, simples, tamanho 10.
   */
  public static final Font fontHP12 = new Font("Helvetica", Font.PLAIN,12);
  
  /**
   * Fonte Helv�tica, simples, tamanho 10.
   */
  public static final Font fontHP10  = new Font("Helvetica", Font.PLAIN, 10);
  
  /**
   * Fonte Helv�tica, begrito, tamanho 10.
   */
  public static final Font fontHB10  = new Font("Helvetica", Font.BOLD, 10);

  }
