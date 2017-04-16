/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author AEK, LOB
 * @description
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.ui;

import icomb.components.CustomChoice;
import icomb.events.ObjectManager;
import icomb.objects.Exercicio;
import icomb.objects.Estagio;
import icomb.util.Constants;
import icomb.util.I18n;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;


public class ConstructionFooterPanel extends Panel implements ItemListener, ActionListener {

  private static final int ALT = 24; // altura de botao "Eu terminei"

  private ConstructionPanel pai;
  private Button btnIAmDone;

  public ConstructionFooterPanel (ConstructionPanel pai) {
    this.pai = pai;
    setLayout(new FlowLayout(FlowLayout.RIGHT));
    // try{String str="";System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
    //L btnIAmDone = ObjectManager.criaBotao(I18n.getString("iAmDone"));
    btnIAmDone = ObjectManager.criaBotao(this, I18n.getString("iAmDone"), I18n.getString("iAmDone-dica")); // iAmDone=Terminei
    //    btnIAmDone.setSize();
    btnIAmDone.addActionListener(this);
    btnIAmDone.setEnabled(false);
    add(btnIAmDone);

    this.setVisible(true);
    }

  public Dimension getPreferredSize () {
    return new Dimension(Constants.PANEL_WIDTH-10, ALT);
    }

  //  public void paint(Graphics g) {
  //    g.setColor(...);
  //    g.drawString(I18n.getString("newStage"), 5, 15);
  //    g.setColor(Color.black);
  //    g.drawRect(2, 2, getSize().width-4, getSize().height-4);
  //  }

  public Insets getInsets () {
    return new Insets(0,0,0,0);
    }

  public void itemStateChanged (ItemEvent e) {
    }

  public void actionPerformed (ActionEvent e) {
    if (ObjectManager.setMachine.isModoCriacao()) {
       try {
         // System.out.println("ConstructionFooterPanel.actionPerformed: "+e);
         pai.criarExercicio();
         // falta tratar: e se for criacao de exercicio? e se for aplicativo, gravar?
         MessageFrame.showMessage("Exercício enviado com sucesso!", pai);
       } catch (Exception ex) {
         MessageFrame.showMessage(ex.getMessage(), pai);
         return;
         }
       }
    else {
      try {
         ObjectManager.setMachine.validaConstrucao();
         pai.validateConstruction();
         StringBuffer sb = new StringBuffer();
         sb.append(Constants.LOG_VALIDA_CONSTRUCAO + Constants.LOG_DELIMITADOR);
         sb.append(I18n.getString(ObjectManager.setMachine.getOperacao()));
         ObjectManager.getInstance().registraAcao(sb);
         pai.redraw();
      } catch(RuntimeException ex) {
         MessageFrame.showMessage(ex.getMessage(), pai);
         StringBuffer sb = new StringBuffer();
         sb.append(Constants.LOG_VALIDA_CONSTRUCAO + Constants.LOG_DELIMITADOR);
         sb.append(I18n.getString(ObjectManager.setMachine.getOperacao()));
         sb.append(Constants.LOG_ERRO + ex.getMessage());
         ObjectManager.getInstance().registraAcao(sb);
         return;
         }
      }

    }

  public void habilitaBotao (boolean habilita) {
    btnIAmDone.setEnabled(habilita);
    }

  // :- LMS -:
  // Prepare and send to the LMS the student response
  public boolean criarExercicio () {
    return pai.criarExercicio();
    }

  }
