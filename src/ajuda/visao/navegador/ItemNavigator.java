/*
 * iMath Project
 * iComb: http://www.matematica.br/icomb
 * 
 * Help framework
 * @autho RP, LOB
 */

package ajuda.visao.navegador;


import java.awt.Dimension;

import ajuda.evento.NavigatorPanelEvent;
import ajuda.modelo.ListaTextoAjuda;


public class ItemNavigator extends NavigatorPanel {
	NavigatorLabel[] listaLabel;
	
	public ItemNavigator() {
		insereLabel();
		addNavigatorPanelListener(this);
		listaLabel[0].selecionaLabel(0);
	}
		
	
	private void insereLabel() {
		int num = ListaTextoAjuda.numTopicos;
		listaLabel = new 	NavigatorLabel[num];
		NavigatorLabel nl;
		
		for (int i = 0; i < num; i++) {
			nl = new NavigatorLabel(ListaTextoAjuda.getTexto(i).toString(), i, this);
			listaLabel[i] = nl;
			add(nl);
		}		
	}


	public Dimension getPreferredSize() {
		return new Dimension(210, 0);
	}


	public void mudouItemSelecionado(NavigatorPanelEvent npe) {
		textIndex = npe.getSelectedTextIndex();
		
		for (int i = 0; i < listaLabel.length; i++) {
			listaLabel[i].selecionaLabel(textIndex);
		}		
	}

}
