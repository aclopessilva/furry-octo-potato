/*
 * iMath Project
 * iComb: http://www.matematica.br/icomb
 *
 * Help framework
 * @autho RP, LOB
 */

package ajuda.visao.navegador;

import icomb.util.Configuracoes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import base.EsquemaVisual;

public class NavigatorLabel extends Label implements MouseListener, EsquemaVisual {

 ItemNavigator in;
 int index;
 private boolean selecionado;

 public NavigatorLabel (String rotulo, int index, ItemNavigator in) {
  super("   " + rotulo);
  this.index = index;
  this.in = in;

  setFont(fontHB12);
  esquemaPadrao();
  addMouseListener(this);
  }

 public Dimension getPreferredSize () {
  return new Dimension(200, 30);
  }

 public void mouseClicked (MouseEvent e) {
  in.setConteudoAjuda(index);
  selecionaLabel(index);
  }

 // Selected button: change color
 void selecionaLabel (int i) {
  if (i == index) {
   setBackground(Configuracoes.CLEAR_BG1);
   setForeground(Configuracoes.FACE_DARK1);
   selecionado = true;
   }
  else {
   esquemaPadrao();
   selecionado = false;
   }
  }

 private void esquemaPadrao () {
  setForeground(Configuracoes.FACE_CLEAR2); //
  setBackground(Configuracoes.DARK_BLUE2BG);
  }

 public void paint (Graphics g) {
  if (selecionado) {
   Color aux = g.getColor();
   g.setColor(Color.black); // border of the selected button
   g.drawRect(0, 0, getSize().width-1, getSize().height-1);

   g.setColor(Color.gray); // line left-top of selected button
   g.drawLine(1, 1, getSize().width-2, 1);
   g.drawLine(1, 1, 1, getSize().height-2);

   g.setColor(Color.lightGray); // line right-bottom of selected button
   g.drawLine(getSize().width-2, 1, getSize().width-2, getSize().height-2);
   g.drawLine(1, getSize().height-2, getSize().width-2, getSize().height-2);

   g.setColor(aux);
   }
  }

 // Mouse over button not selected
 public void mouseEntered (MouseEvent e) {
  if (!selecionado)
   setForeground(Configuracoes.FACE_CLEAR1);
  }

 public void mouseExited (MouseEvent e) {
  if (!selecionado) {
   setForeground(Configuracoes.FACE_CLEAR2); //corLetrasBotoes
   setBackground(Configuracoes.DARK_BLUE2BG);
   }
  }

 public void mousePressed (MouseEvent e) { }
 public void mouseReleased (MouseEvent e) { }

 }
