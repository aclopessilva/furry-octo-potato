/*
 * iMath: http://www.matematica.br
 * iComb: http://www.matematica.br/icomb
 *
 * @author AEK, LOB
 * @description
 *
 */

package icomb.ui;

import icomb.IComb;
import icomb.events.ObjectManager;
import icomb.objects.Exercicio;
import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.I18n;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ValidationPanel extends Panel implements ActionListener {

 private String title;
 private TextArea label;
 private SetMachinePanel pai;
 private Button btnChange;
 private Button btnIAmDone;
 private Button btnSend;


 public ValidationPanel (SetMachinePanel pai) {
  this.pai = pai;
  setBackground(new Color(230,230,230));

  setLayout(new BorderLayout());
  label = new TextArea(ObjectManager.setMachine.montaResposta(),2,5,TextArea.SCROLLBARS_NONE);
  label.setBackground(new Color(255,255,128));
  label.setForeground(Color.BLACK);
  label.setEditable(false);

  Panel leftPanel = new Panel(new BorderLayout()){
   public Insets getInsets() {
    return new Insets(0,0,0,5);
    }
   };
  leftPanel.add(label,BorderLayout.CENTER);

  add(leftPanel,BorderLayout.CENTER);

  Panel panelButtons = new Panel(new BorderLayout());
  panelButtons.setBackground(Configuracoes.CINZA);
  btnChange = ObjectManager.criaBotao(panelButtons, I18n.getString("changeConstruction"), I18n.getString("changeConstruction-dica"));
  btnChange.addActionListener(this);
  btnSend = ObjectManager.criaBotao(panelButtons, I18n.getString("send"), I18n.getString("send-dica"));
  if (ObjectManager.setMachine.isOnline() || IComb.notSend())
   btnSend.setEnabled(false);
  else
   btnSend.setEnabled(true);
  btnSend.addActionListener(this);

  Panel footer = new Panel(new BorderLayout());
  footer.setBackground(Configuracoes.CINZA);
  btnIAmDone = ObjectManager.criaBotao(footer, I18n.getString("iAmDone"), I18n.getString("iAmDone-dica"));
  btnIAmDone.addActionListener(this);

  footer.add(btnChange,BorderLayout.EAST);
  // panelButtons.add(btnIAmDone,BorderLayout.NORTH);

  panelButtons.add(btnSend,BorderLayout.NORTH);
  add(panelButtons,BorderLayout.EAST);
  add(footer,BorderLayout.SOUTH);
  this.setVisible(true);
  }

 public Dimension getPreferredSize () {
  return new Dimension(Constants.PANEL_WIDTH, 443);
  }

 public void paint (Graphics g) {
  g.setColor(Color.BLACK);
  g.drawRect(2, 2, getSize().width-4, getSize().height-4);
  }

 public Insets getInsets () {
  return new Insets(20,5,5,5);
  }

 public void actionPerformed (ActionEvent e) {
  if (e.getSource() == btnChange) {
   ObjectManager.setMachine.setOperacao(null);
   StringBuffer sb = new StringBuffer();
   sb.append(Constants.LOG_ALTERA_CONSTRUCAO + Constants.LOG_DELIMITADOR);
   ObjectManager.getInstance().registraAcao(sb);
   pai.redraw();
   }
  else if (e.getSource() == btnIAmDone) {
   ObjectManager.setMachine.finaliza();
   StringBuffer sb = new StringBuffer();
   sb.append(Constants.LOG_EUTERMINEI_CONSTRUCAO + Constants.LOG_DELIMITADOR);
   ObjectManager.getInstance().registraAcao(sb);
   pai.redraw();
   }
  else if (e.getSource() == btnSend) {
   this.pai.enviar();
   }
  }

 }
