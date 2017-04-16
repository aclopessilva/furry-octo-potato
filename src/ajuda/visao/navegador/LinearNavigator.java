/*
 * iMath Project
 * iComb: http://www.matematica.br/icomb
 * 
 * Help framework
 * @autho RP, LOB
 */

package ajuda.visao.navegador;


import icomb.util.I18n;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ajuda.evento.NavigatorPanelEvent;
import ajuda.modelo.ListaTextoAjuda;
import ajuda.modelo.TextoAjuda;


public class LinearNavigator extends NavigatorPanel implements ActionListener {

 HelpButton voltar,
            avancar,
            inicial,
            imprimir;
 
 public LinearNavigator () {
  setLayout(new FlowLayout(FlowLayout.LEFT));
  
  //  helpButtonBack = Voltar
  //  helpButtonGo = Avançar
  //  helpButtonStart = Início
  //  helpButtonPrint = Imprimir
  voltar = new HelpButton(I18n.getString("helpButtonBack"));
  avancar = new HelpButton(I18n.getString("helpButtonGo"));
  inicial = new HelpButton(I18n.getString("helpButtonStart"));
  imprimir = new HelpButton(I18n.getString("helpButtonPrint"));
  
  voltar.addActionListener(this);  
  avancar.addActionListener(this);
  inicial.addActionListener(this);
  imprimir.addActionListener(this);
  
  voltar.setEnabled(false);
  inicial.setEnabled(false);
  
  add(voltar);
  add(avancar);
  add(inicial);
  add(imprimir);
  
  setConteudoAjuda(0);
  }


 public Dimension getPreferredSize () {
  return new Dimension(0, 60);
  }


 private void disableApropriatedButton (int i) {
  if (i == 0) {
   voltar.setEnabled(false);
   inicial.setEnabled(false);
   avancar.setEnabled(true);
   }
  else
  if (i == ListaTextoAjuda.numTopicos-1) {
   avancar.setEnabled(false);
   voltar.setEnabled(true);
   inicial.setEnabled(true);
   }  
  else {
   voltar.setEnabled(true);
   inicial.setEnabled(true);
   avancar.setEnabled(true);
   }  
  }

 private void imprimir () {
  Frame f = new Frame();
  TextoAjuda ta = getConteudoSelecionado();
  PrintJob pjob = getToolkit().getPrintJob(f, ta.toString(), null);  
  if (pjob != null) {
    Graphics pg = pjob.getGraphics();
    if (pg != null) {
      ta.printAll(pg);
      pg.dispose(); // flush page
      }
    pjob.end();
    }
  }

 public void actionPerformed (ActionEvent e) {
  String s = e.getActionCommand();
  if (s.equals(I18n.getString("helpButtonBack"))) // "Voltar"
   setConteudoAjuda(--textIndex);

  if (s.equals(I18n.getString("helpButtonGo"))) // "Avançar"
   setConteudoAjuda(++textIndex);

  if (s.equals(I18n.getString("helpButtonStart"))) // "Início"
   setConteudoAjuda(textIndex = 0);

  if (s.equals(I18n.getString("helpButtonPrint"))) // "Imprimir"
   imprimir();
  disableApropriatedButton(textIndex);
  }


 public void mudouItemSelecionado (NavigatorPanelEvent npe) {
  textIndex = npe.getSelectedTextIndex();
  disableApropriatedButton(textIndex);
  }

 }
