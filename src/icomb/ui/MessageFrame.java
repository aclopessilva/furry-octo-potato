/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author Leônidas de O. Brandão
 * @version
 *
 * @description Frame to show the Universe
 *
 * @see ConstructionFooterPanel.actionPerformed(ActionEvent e): MessageFrame.showMessage("Exercício enviado com sucesso!", pai); <--------------
 * @see CreateExercisePanel.
 * @see DefinedUniversePanel.itemStateChanged(ItemEvent e): MessageFrame.showMessage(msg,pai);
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.ui;

import icomb.util.I18n;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class MessageFrame extends PopUpFrame implements ActionListener, WindowListener {

 private static MessageFrame mensagemFrame;


 public static void showMessage (String mensagem, Panel panel) {
System.out.println("\n\n-------------------------\nMessageFrame.showMessage(): mensagem="+mensagem+", "+I18n.getString("universe")); // title: windowShowUniverse
  if (mensagemFrame == null)
   mensagemFrame = new MessageFrame(panel);
  mensagemFrame.setMessage(mensagem);
  mensagemFrame.setLocationRelativeTo(panel);
  mensagemFrame.setVisible(true); 
  }


 private Button btnExit;
 private Panel father;
 private TextArea txtMessage;


 public void setMessage (String message) {
  txtMessage.setText(message);
  repaint();
  }


 private MessageFrame (Panel father) {
  super(I18n.getString("windowShowUniverse")); // windowShowUniverse=Universo atual
  // System.out.println("\n\n-------------------------\nMessageFrame: "+I18n.getString("windowShowUniverse"));

  this.father = father;
  setSize(300,200);
  setLocationRelativeTo(father);
  setVisible(false);
  this.addWindowListener(this);
  Panel panel= new Panel(new FlowLayout());
  txtMessage = new TextArea("",2,5,TextArea.SCROLLBARS_NONE);

  txtMessage.setEditable(false);
  btnExit = new Button("OK");
  panel.add(btnExit);
  btnExit.addActionListener(this);
  add(txtMessage,BorderLayout.CENTER);
  add(panel,BorderLayout.SOUTH);
  }

 public void actionPerformed (ActionEvent e) {
  setVisible(false);
  repaint();
  }

 public void windowActivated (WindowEvent e) {
  }

 public void windowClosed (WindowEvent e) {
  }

 public void windowClosing (WindowEvent e) {
  setVisible(false);
  repaint();
  }

 public void windowDeactivated (WindowEvent e) {
  }

 public void windowDeiconified (WindowEvent e) {
  }

 public void windowIconified (WindowEvent e) {
  }

 public void windowOpened (WindowEvent e) {
  }

 }

