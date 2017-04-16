/*
 * iMath Project
 * iComb: http://www.matematica.br/icomb
 * 
 * Help framework
 * @autho RP, LOB
 */

package ajuda.visao.navegador;

import icomb.util.Configuracoes;

import java.awt.Graphics;
import java.awt.Panel;
import java.util.Vector;

import base.EsquemaVisual;

import ajuda.evento.NavigatorPanelEvent;
import ajuda.evento.NavigatorPanelListener;
import ajuda.modelo.ListaTextoAjuda;
import ajuda.modelo.TextoAjuda;

public abstract class NavigatorPanel extends Panel implements NavigatorPanelListener, EsquemaVisual {

 Vector listaDeOuvintes;
 TextoAjuda ca;
 int textIndex = 0;
 
 public NavigatorPanel () {
  listaDeOuvintes = new Vector();
  setBackground(corAreaDesenho);
  }
 
 protected void setConteudoAjuda (int i) {
  textIndex = i;
  if (textIndex < 0) textIndex = 0;
  if (textIndex > ListaTextoAjuda.numTopicos-1) 
   textIndex = ListaTextoAjuda.numTopicos-1;

  disparaEventoMudouItemSelecionado();
  }
 
 public TextoAjuda getConteudoSelecionado () {
  return ListaTextoAjuda.getTexto(textIndex);
  }
 
 public int getTextIndex () {
  return textIndex;
  }

 
 public synchronized void addNavigatorPanelListener (NavigatorPanelListener hl) {
  listaDeOuvintes.addElement(hl);
  }
 
 public synchronized void removeNavigatorPanelListener (NavigatorPanelListener hl) {
  listaDeOuvintes.removeElement(hl);
  }
 
 void disparaEventoMudouItemSelecionado () {
  Vector v;
  synchronized (this) {
   v = (Vector)listaDeOuvintes.clone();
   }
  
  NavigatorPanelEvent evento = new NavigatorPanelEvent(this);
  
  for (int i = 0; i<v.size(); i++) {
   NavigatorPanelListener item = (NavigatorPanelListener) v.elementAt(i);
   item.mudouItemSelecionado(evento);   
   }
  }
 
 public void paint (Graphics g) {
  g.drawRect(0, 0, getSize().width-1, getSize().height-1);
  }

 }
