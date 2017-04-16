/*
 * iMath Project
 * iComb: http://www.matematica.br/icomb
 *
 * Help framework
 * @autho RP, LOB
 */

package ajuda.visao.componentesDoTexto;

import java.awt.Canvas;
import java.awt.Dimension;

import base.EsquemaVisual;

/**
 * Um ComponenteDeTexto � um objeto imaginado para ser desenhado sobre um painel do tipo
 * ConteudoAjuda.   Um componente de texto pode ser um t�tulo, um subtitulo, uma sess�o,
 * um par�grafo ou uma figura. 
 * @author Reginaldo
 *
 */
public abstract class ComponenteDoTexto extends Canvas 
implements EsquemaVisual{

	/** A altura m�nima de um componente de texto */
	int altura = 40;
	
	/** A largura do painel sobre o qual esse componente de texto ser� desenhado*/
	int largura;

	/** A dist�ncia m�nima que um componente de texto mant�m da borda esquerda 
	 * do painel no qual ser� desenhado 
	 */
	int margem = 20;
	
	/** Recebe a largura do painel sobre o qual esse componente de texto ser� desenhado
	 * e cria um objeto componente de texto em fun��o dessa largura.   A largura L do
	 * componente de texto � calculada como L = largura - 2*margem.
	 * @param largura
	 */
	public ComponenteDoTexto(int largura) {
		this.largura = largura;
	}

	/** Retorna a altura do componente de texto */
	 public int getAltura() {
		return altura;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(largura - 2*margem, altura);
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	int getLargura(){
		return largura;
	}
	
	/** Determina a altura do componente de texto */
	abstract void setAltura(int altura);	
	
}
