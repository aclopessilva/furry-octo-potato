/*
 * iMath project
 * iComb: http://www.matematica.br/icomb
 * 
 * @author AEK, LOB
 * @description The botton panel of 'ConstructionPanel'
 *              it contains: final information about each constructed stage (in text form); stage exclusion; stage composition (last operation in resolution)
 * @see icomb.ui.ConstructionPanel: panel that instantiate 'ListPanel'
 * @see icomb.ui.StagePanel: panel with the Button("addStage") that triggers the inclusion of a "new stage" in 'ListPanel'
 *
 */

package icomb.ui;

import icomb.components.CustomChoice;
import icomb.components.CustomList;
import icomb.events.ObjectManager;
import icomb.objects.Estagio;
import icomb.util.Constants;
import icomb.util.I18n;
import icomb.util.Configuracoes;
import icomb.util.Util;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

public class ListPanel extends Panel implements ItemListener, ActionListener {

  private ConstructionPanel pai;

  //addStage=Adicionar Estágio

  //  private Button btnValidateConstruction;
  private Button btnSupressSelection; // supressSelection=Excluir Seleção
  private Button btnModifySelection; // modifySelection=Modificar Seleção
  private CustomChoice chAction;
  //  private Label lblLabel;
  private CustomList list;

  private Label lblResult; // Label to the registered values of each stage ("how much", "combien"?)
  private Button btnCancel;

  public ListPanel (ConstructionPanel pai) {
    // try{String str=""; System.out.println(str.charAt(3)); }catch(Exception e){e.printStackTrace();}
    this.pai = pai;
    setLayout(new BorderLayout());
    Panel southPanel = new Panel(new BorderLayout());
    southPanel.setBackground(Configuracoes.CINZA);

    Panel panelExcludeOrFinal = new Panel(new BorderLayout()); // "Exclude" or "Add || Multiply"
    panelExcludeOrFinal.setBackground(Configuracoes.CINZA);

    // Choice to "close" a solution (final step): how to compose all the stages (add or multiply)?
    // "Após definir todos os estágios escolha como eles serão agrupados para formar o conjunto final"
    chAction = ObjectManager.criaCombo(southPanel,Util.getListActions(),"choiceAction-dica");
    // chAction.setSize(new Dimension(200,20));
    // AQUI: tentativa de deixar o 'choice', abaixo do botao "Excluir selecao" (que deveria mudar de nome "Excluir estagio"), visivel

    // chAction.setSize(new Dimension(200,20));
    chAction.setEnabled(false);
    chAction.addItemListener(this);

    Panel panelRight = new Panel(new BorderLayout());
    panelRight.setBackground(Configuracoes.CINZA);

    lblResult = new Label(""); // registered values of each stage ("how much", "combien"?)
    lblResult.setForeground(Color.BLACK);

    btnCancel = ObjectManager.criaBotao(panelRight, I18n.getString("cancel"), I18n.getString("cancel-dica"));
    btnCancel.setEnabled(false);
    btnCancel.addActionListener(this);

    panelRight.add(lblResult,BorderLayout.CENTER);

    btnSupressSelection = ObjectManager.criaBotao(southPanel, I18n.getString("supressSelection"), I18n.getString("supressSelection-dica"));
    btnSupressSelection.setEnabled(false);
    btnSupressSelection.addActionListener(this);

    panelExcludeOrFinal.add(btnSupressSelection, BorderLayout.NORTH);
    panelExcludeOrFinal.add(chAction, BorderLayout.SOUTH);
    southPanel.add(panelExcludeOrFinal, BorderLayout.NORTH);
    southPanel.add(panelRight, BorderLayout.SOUTH);

    list = ObjectManager.criaList(new Vector());
    list.setForeground(Color.BLACK);
    list.setEnabled(false);
    list.addItemListener(this);

    add(list,BorderLayout.CENTER);
    add(southPanel,BorderLayout.EAST);

    this.setVisible(true);
    }

  // Panel dimensions
  public Dimension getPreferredSize () {
    return new Dimension(Constants.PANEL_WIDTH-10, 75);
    }

  // public void paint (Graphics g) {
  //   g.setColor(...);
  //   g.drawString(I18n.getString("listOfStages"), 5, 15); // listOfStages=Lista de Estagios
  //   g.setColor(Configuracoes.CINZA); //
  //   g.drawRect(2, 2, getSize().width-4, getSize().height-4);
  //   }

  public Insets getInsets () {
    return new Insets(2,2,2,2);
    }

  public void itemStateChanged (ItemEvent e) {
    if (e.getSource() == list) {
       if (list.getSelectedIndex()>=0) {
          btnSupressSelection.setEnabled(true);
          //btnModifySelection.setEnabled(true);
          }
       }
    else
    if (e.getSource() == chAction) {
      if (chAction.getSelectedIndex()>=0) {
        ObjectManager.setMachine.setOperacao(chAction.getSelectedKey());
        lblResult.setText(ObjectManager.setMachine.getOperacaoTexto() + "   ");
        btnCancel.setEnabled(true);
        //btnValidateConstruction.setEnabled(true);
        pai.habilitaBotaoEuTerminei(true);
        validate();
        }
      }

    } // void itemStateChanged(ItemEvent e)

  public void actionPerformed (ActionEvent e) {
    if (e.getSource() == btnSupressSelection) {
       supressStage(); // ConstructionPanel pai.stageWasDeleted() - NewStageButtonPanel <Reinicia>
       btnSupressSelection.setEnabled(false);
       }
    // else
    //L   if (e.getSource() == btnModifySelection) {
    //   modifySelection();
    //   btnModifySelection.setEnabled(false);
    // }
    else
    if (e.getSource() == btnCancel) {
       btnCancel.setEnabled(false);
       // btnValidateConstruction.setEnabled(false);
       pai.habilitaBotaoEuTerminei(false);
       }
    // else
    //L   if (e.getSource() == btnValidateConstruction) {
    //   try {
    //     ObjectManager.setMachine.validaConstrucao();
    //     pai.validateConstruction();
    //     StringBuffer sb = new StringBuffer();
    //     sb.append(Constants.LOG_VALIDA_CONSTRUCAO + Constants.LOG_DELIMITADOR);
    //     sb.append(I18n.getString(chAction.getSelectedKey()));
    //     ObjectManager.getInstance().registraAcao(sb);
    //   }
    //   catch(RuntimeException ex) {
    //     MessageFrame.showMessage(ex.getMessage(), pai);
    //     StringBuffer sb = new StringBuffer();
    //     sb.append(Constants.LOG_VALIDA_CONSTRUCAO + Constants.LOG_DELIMITADOR);
    //     sb.append(I18n.getString(chAction.getSelectedKey()));
    //     sb.append(Constants.LOG_ERRO + ex.getMessage());
    //     ObjectManager.getInstance().registraAcao(sb);
    //     return;
    //   }
    // }
    }


  public void modifySelection () {
    }

  public void supressStage () {
    String key = list.getSelectedKey();
    for (int i=0; i <ObjectManager.setMachine.getEstagios().size(); i++) {
      Estagio stage = ((Estagio) ObjectManager.setMachine.getEstagios().get(i));
      if ((""+stage.hashCode()).equals(key)) {
         ObjectManager.setMachine.getEstagios().remove(stage);
         StringBuffer sb = new StringBuffer();
         sb.append(Constants.LOG_EXCLUI_ESTAGIO  + "(" + stage.hashCode() + ")" + Constants.LOG_DELIMITADOR);
         ObjectManager.getInstance().registraAcao(sb);
         break;
         }
      }
    redo(); // 
    pai.stageWasDeleted();
    }

  // From: src/icomb/ui/ConstructionPanel.java - addStagePanel()
  public void addStageFromConstructionPanel () {
    String strStage;
    Vector vec = ObjectManager.setMachine.getEstagios(); // Vector estagios
    Estagio stage = null;
    if (vec != null && vec.size()>0)
       stage = (Estagio) vec.lastElement(); // get the last inserted stage
    else
       return;
	
    strStage = stage.getDescricao() + ": " + stage.getTextoFormula();
System.out.println("ListPanel.addStageFromConstructionPanel(): <"+stage.hashCode()+"> :: "+strStage);
    list.add(""+stage.hashCode(), strStage); // add the last stage
    list.setEnabled(true);
    lblResult.setText(ObjectManager.setMachine.getOperacaoTexto() + "   "); // Label
    chAction.setEnabled(true);
    }
   // X From: src/icomb/ui/ConstructionPanel.java - addStagePanel()
   // From: ListPanel.supressStage() above
   public void redo () {
     String strStage;
     list.removeAll(); // icomb.components.CustomList list
     for (int i=0; i<ObjectManager.setMachine.getEstagios().size(); i++) {
       Estagio stage = ((Estagio) ObjectManager.setMachine.getEstagios().get(i));
       strStage = stage.getDescricao() + ": " + stage.getTextoFormula();
       // System.out.println("ListPanel.redo(): <"+stage.hashCode()+"> :: "+strStage);
       list.add(""+stage.hashCode(), strStage);
       }
     list.setEnabled(true);
     lblResult.setText(ObjectManager.setMachine.getOperacaoTexto() + "   ");
     chAction.setEnabled(true);
     }

  }
