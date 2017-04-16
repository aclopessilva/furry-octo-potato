/*
 * iMath: http://www.matematica.br
 * iComb: http://www.matematica.br/icomb
 *
 * @author AEK, LOB
 * @description Panel with all components to make "one stage" to solve an exercise
 *
 */

package icomb.ui;

import icomb.events.ObjectManager;
import icomb.machine.SetMachine;
import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.I18n;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class NewStageButtonPanel extends Panel implements ItemListener, ActionListener {

  private final ConstructionPanel pai;
  private final Label lblLabel;
  private final Button btnNewStage;
  private final Button btnIAmDone;
  private final Button btnEraseStage;
  private final Button btnResetConstruction;

  public NewStageButtonPanel (ConstructionPanel pai) {
    this.pai = pai;
    //try{String str="";System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
    setLayout(new BorderLayout());
    setBackground(Configuracoes.CINZA);
    btnNewStage = ObjectManager.criaBotao(this,  I18n.getString("newStage"), I18n.getString("newStage-dica")); // newStage=Novo Estágio
    btnIAmDone = ObjectManager.criaBotao(this, I18n.getString("iAmDone"), I18n.getString("iAmDone-dica"));
    btnEraseStage = ObjectManager.criaBotao(this, I18n.getString("eraseThisStage"), I18n.getString("eraseThisStage-dica"));
    btnResetConstruction = ObjectManager.criaBotao(this, I18n.getString("resetConstruction"), I18n.getString("resetConstruction-dica"));

    lblLabel = new Label(I18n.getString("stage"));
    lblLabel.setForeground(btnEraseStage.getForeground());
    Font font = new Font(btnEraseStage.getFont().getName(), btnEraseStage.getFont().getStyle(), 13);

    lblLabel.setFont(font);

    btnEraseStage.setEnabled(false);
    btnEraseStage.addActionListener(this);
    btnResetConstruction.setEnabled(false);
    btnResetConstruction.addActionListener(this);

    btnIAmDone.setEnabled(false);

    btnNewStage.setEnabled(ObjectManager.setMachine.getEstado() == SetMachine.ESTADO_CONSTRUCAO_INICIADA);

    // L btnNewStage =
    // ObjectManager.criaBotao(I18n.getString("newStage")); //
    // newStage=Novo Estágio
    // Panel para o botao nao ficar mais do que o tamanho esperado
    Panel auxNewStage = new Panel(new FlowLayout());
    auxNewStage.add(lblLabel);
    auxNewStage.add(btnNewStage);
    auxNewStage.add(btnEraseStage);
    auxNewStage.add(btnResetConstruction);
    add(auxNewStage, BorderLayout.WEST);

    Panel auxIAmDone = new Panel(new FlowLayout());
    auxIAmDone.add(btnIAmDone);
    add(auxIAmDone, BorderLayout.EAST);

    btnNewStage.addActionListener(this);
    btnIAmDone.addActionListener(this);
    // add(aux);
    this.setVisible(true);
    }

  public Dimension getPreferredSize () {
    return new Dimension(Constants.PANEL_WIDTH - 10, 30);
    }

  // public void paint(Graphics g) {
  // g.setColor(...);
  // g.drawString(I18n.getString("newStage"), 5, 15);
  // g.setColor(Color.black);
  // g.drawRect(2, 2, getSize().width-4, getSize().height-4);
  // }

  public Insets getInsets () {
    return new Insets(0, 0, 0, 0);
    }

  public void itemStateChanged (ItemEvent e) {
    }

  public void actionPerformed (ActionEvent e) {
    if (e.getSource() == btnResetConstruction) {
       StringBuffer sb = new StringBuffer();
       sb.append(Constants.LOG_REINICIA_CONSTRUCAO + Constants.LOG_DELIMITADOR);
       ObjectManager.getInstance().registraAcao(sb);
       ObjectManager.setMachine.reiniciaConstrucao();
       pai.apagaEstagioAtual(); // ConstructionPanel
       pai.addStagePanel();
       btnResetConstruction.setEnabled(false);
       }
    else
    if (e.getSource() == btnEraseStage) {
       StringBuffer sb = new StringBuffer();
       // 123456789012345678
       sb.append(Constants.LOG_APAGA_ESTAGIO + Constants.LOG_DELIMITADOR);
       ObjectManager.getInstance().registraAcao(sb);
       ObjectManager.setMachine.apagarEstagio();
       pai.apagaEstagioAtual();
       pai.addStagePanel();
       }
    else
    if (e.getSource() == btnNewStage) {
       ObjectManager.setMachine.mudaDic(I18n.getString("msgStgFirst")); // Select the options to build the first stage...
       btnNewStage.setEnabled(false);
       ObjectManager.setMachine.inicialEstagioAtual();
       pai.openStagePanel();
       StringBuffer sb = new StringBuffer();
       // 123456789012345678
       sb.append(Constants.LOG_NOVO_ESTAGIO + Constants.LOG_DELIMITADOR);
       ObjectManager.getInstance().registraAcao(sb);
       // System.out.println("NewStageButtonPanel: pai="+pai); =>
       // pai=icomb.ui.ConstructionPanel
       btnEraseStage.setEnabled(true);
       btnResetConstruction.setEnabled(true);
       pai.redraw();
       repaint();
       }
    else { // Final construction
       // System.out.println("NewStageButtonPanel.actionPerformed(...): "+ObjectManager.setMachine.isModoCriacao());
       if (ObjectManager.setMachine.isModoCriacao()) { // is exercise creation
          try {
            // :- LMS -:
            boolean sent = pai.criarExercicio(); // send the construction (answer) to the LMS server - ConstructionPanel pai
            String strMsg = "";
            if (sent) {
               if (base.MontaFace.staticApplet) // is exercise creation from desktop version
                 strMsg = I18n.getString("msgLMSsuc_sent"); // The construction was successfully sent to the server!
               else
                 strMsg = I18n.getString("msgLMSsuc_store"); // The construction was successfully saved!
               }
            else {
               strMsg = I18n.getString("msgLMSfail_sent"); // The construction could not be sent to the server...
               System.err.println("Error: in NewStageButtonPanel.actionPerformed(...): "+strMsg);
               System.err.println("Error: in SetMachinePanel.criarExercicio(): "+SetMachinePanel.staticErrSent);
               }
            MessageFrame.showMessage(strMsg, pai);
          } catch (Exception ex) {
            System.err.println("Error: in SetMachinePanel.criarExercicio(): "+SetMachinePanel.staticErrSent);
            MessageFrame.showMessage(ex.getMessage(), pai);
            return;
            }
          } // if (ObjectManager.setMachine.isModoCriacao())
       else try {
         ObjectManager.setMachine.validaConstrucao();
         pai.validateConstruction();
         StringBuffer sb = new StringBuffer();
         sb.append(Constants.LOG_VALIDA_CONSTRUCAO + Constants.LOG_DELIMITADOR);
         sb.append(I18n.getString(ObjectManager.setMachine.getOperacao()));
         ObjectManager.getInstance().registraAcao(sb);
         pai.redraw();
         } catch (RuntimeException ex) {
           MessageFrame.showMessage(ex.getMessage(), pai);
           StringBuffer sb = new StringBuffer();
           sb.append(Constants.LOG_VALIDA_CONSTRUCAO + Constants.LOG_DELIMITADOR);
           sb.append(I18n.getString(ObjectManager.setMachine.getOperacao()));
           sb.append(Constants.LOG_ERRO + ex.getMessage());
           ObjectManager.getInstance().registraAcao(sb);
           return;
           }
       } // else de if (e.getSource() == btnNewStage)

    }

  // Auxiliar: teste
  public String btnNewStage () {
    return btnNewStage.getLabel()+", "+btnNewStage.isEnabled();
    }

  public void open () {
    btnNewStage.setEnabled(true);
    btnIAmDone.setEnabled(false);
    btnEraseStage.setEnabled(false);
    //btnResetConstruction.setEnabled(false);
    }

  public void habilitaBotao (boolean habilita) {
    btnIAmDone.setEnabled(habilita);
    }

  public void enabledResetConstruction (boolean bool) {
    btnResetConstruction.setEnabled(bool);
    }

  public void criarExercicio () {
    pai.criarExercicio();
    }

  }
