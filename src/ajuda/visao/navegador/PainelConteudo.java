/*
 * iMath Project
 * iComb: http://www.matematica.br/icomb
 * 
 * Help framework
 * @autho RP, LOB
 */

package ajuda.visao.navegador;

import java.awt.CardLayout;
import java.awt.Panel;
import java.awt.ScrollPane;

import ajuda.evento.NavigatorPanelEvent;
import ajuda.evento.NavigatorPanelListener;
import ajuda.modelo.ListaTextoAjuda;
import ajuda.modelo.TextoAjuda;


public class PainelConteudo extends ScrollPane implements NavigatorPanelListener {
 Panel aux;
 CardLayout cl;
 
 public PainelConteudo () {
  cl = new CardLayout();
  aux = new Panel(cl);
  
  for (int i = 0; i < ListaTextoAjuda.numTopicos; i++) 
   aux.add(ListaTextoAjuda.getTexto(i).getTopico(), 
     ListaTextoAjuda.getTexto(i));
  add(aux);
  }

 public void mudouItemSelecionado (NavigatorPanelEvent npe) {
  TextoAjuda ta = npe.getSelectedText();
  cl.show(aux, ta.getTopico());
  }

 }
