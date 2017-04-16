/*
 * iMath Project
 * iComb: http://www.matematica.br/icomb
 * 
 * Help framework
 * @autho RP, LOB
 */

package ajuda.visao.navegador;

import icomb.util.Configuracoes;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import base.EsquemaVisual;

public class HelpButton extends Button implements MouseListener,EsquemaVisual {

 public HelpButton (String rotulo) {
  super(rotulo);
  setForeground(corLetrasBotoes);
  setBackground(corFundoBotoes);
  setFont(fontHB12);
  addMouseListener(this);
  }

 
 public Dimension getPreferredSize () {
  return new Dimension(70, 50);
  }

 // When mouse "enter" the topic button
 public void mouseEntered (MouseEvent e) {
  setForeground(Configuracoes.FACE_CLEAR1);
  }


 public void mouseExited (MouseEvent e) {
  setForeground(corLetrasBotoes);  
  }

 public void mouseClicked (MouseEvent e) { }
 public void mousePressed (MouseEvent e) { }
 public void mouseReleased (MouseEvent e) { }

 }
