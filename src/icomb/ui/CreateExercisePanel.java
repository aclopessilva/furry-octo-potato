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
 * @see icomb/ui/SetMachinePanel.java: void update()
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.ui;

import icomb.components.CustomChoice;
import icomb.events.ObjectManager;
import icomb.objects.Exercicio;
import icomb.objects.Universo;
import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.GerenciadorRecursos;
import icomb.util.I18n;
import icomb.util.Parse;
import icomb.util.Util;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ajuda.Help;

public class CreateExercisePanel extends Panel implements ActionListener {

 private String title;
 private CustomChoice choiceNumbers;
 private CustomChoice choiceUniverses;
 private TextArea txtDica;
 private TextArea txtExercicio;

 private SetMachinePanel pai;

 private Button btnUniverso; // button to exam universe set
 private Button btnIniciarResolucao; // button to start the exercise resolution
 private Button btnHelp; // button to launch help frame
 private Button btnBack; // button to return to start

 public CreateExercisePanel (SetMachinePanel pai) {
  this.pai = pai;

  // choiceNumbers = ObjectManager.criaCombo(Util.getListNumbers(50));
  // choiceUniverses = ObjectManager.criaCombo(UniversosRecursos.getInstance().getUniversos(),UniversosRecursos.getInstance().getNomeUniversos());

  setLayout(new BorderLayout());

  txtExercicio = new TextArea("",2,5,TextArea.SCROLLBARS_NONE);
  txtExercicio.setBackground(Color.WHITE);
  txtExercicio.setForeground(Color.BLACK);
  txtExercicio.setEditable(true);

  txtDica = new TextArea("",2,5,TextArea.SCROLLBARS_NONE);
  txtDica.setBackground(Color.WHITE);
  txtDica.setForeground(Color.BLACK);
  txtDica.setEditable(true);

  Panel editar = new Panel(new BorderLayout());
  editar.add(txtExercicio,BorderLayout.CENTER);
  editar.add(txtDica,BorderLayout.SOUTH);

  Panel leftPanel = new Panel(new BorderLayout()) {
   public Insets getInsets() { return new Insets(0,0,0,5); }
   };
  leftPanel.add(editar,BorderLayout.CENTER);

  add(leftPanel,BorderLayout.CENTER);

  Panel panelButtons = new Panel(new BorderLayout());

  btnIniciarResolucao = ObjectManager.criaBotao(panelButtons, I18n.getString("createAnswer"), I18n.getString("createAnswer-dica"));
  btnIniciarResolucao.addActionListener(this);
  panelButtons.add(btnIniciarResolucao,BorderLayout.SOUTH);

  Panel panelAux = new Panel(new BorderLayout());

  // new Button("Ajuda");
  btnHelp = ObjectManager.criaBotao(panelButtons, I18n.getString("help"), I18n.getString("help-dica"));
  btnHelp.addActionListener(this);
  panelAux.add(btnHelp,BorderLayout.NORTH);

  // Go back to the begining
  btnBack = ObjectManager.criaBotao(panelButtons, I18n.getString("back2start"), I18n.getString("back2start-dica"));
  btnBack.addActionListener(this);
  panelAux.add(btnBack,BorderLayout.SOUTH);

  panelButtons.add(panelAux,BorderLayout.CENTER);

  btnUniverso = ObjectManager.criaBotao(panelButtons, I18n.getString("seeUniverse"), I18n.getString("seeUniverse-dica"));
  btnUniverso.addActionListener(this);
  panelButtons.add(btnUniverso,BorderLayout.NORTH);

  add(panelButtons,BorderLayout.EAST);
  this.setVisible(true);
  // try{String str=""; System.out.println(str.charAt(3)); }catch(Exception e){e.printStackTrace();} 
  }

 public Dimension getPreferredSize () {
  return new Dimension(Constants.PANEL_WIDTH, 150);
  }

 // Panel: creation of exercise (statement and hint area)
 public void paint (Graphics g) {
  g.setColor(Configuracoes.corBarraSupInf); // label identifying the text areas
  g.drawString(I18n.getString("exerciseAndClue"), 5, 15); // Exercise / Hint
  g.setColor(Color.black);
  g.drawRect(2, 2, getSize().width-4, getSize().height-4);
  // System.out.println("CreateExercisePanel.paint: "+getSize().width()+","+getSize().height);
  }

 public Insets getInsets () {
  return new Insets(20,5,5,5);
  }

 public void actionPerformed (ActionEvent e) {
  if (e.getSource() == btnUniverso) {
   ObjectManager.setMachine.loadUniverso(pai.getMf());
   UniversoFrame universoFrame = new UniversoFrame(pai.getMf());
   universoFrame.setUniverso(ObjectManager.setMachine.getUniverso());
   universoFrame.setVisible(true);
   }
  else if (e.getSource() == btnIniciarResolucao) {
   Exercicio ex = new Exercicio(50,txtExercicio.getText(),txtExercicio.getText());
   // ex.setSolucao(Universo.interpretaCondicao(solucao));
   // ex.setResposta(Long.parseLong(resposta));

   ObjectManager.setMachine.setCorrige(false); // SetMachine: this.corrige = false
   ObjectManager.setMachine.setExercicio(ex); // define 'SetMachine.estado' as 'ESTADO_EXERCICIO_CONFIRMADO'
   ObjectManager.setMachine.defineDica(txtDica.getText());

   // ObjectManager.setMachine.loadUniverso(pai.getMf()); - must be commented, since in a new exercise there is no Universe
   pai.redraw();
   }
  else if (e.getSource() == btnBack) { // go back to the beginning
   // ObjectManager.setMachine.setEstado(icomb.machine.SetMachine.ESTADO_INICIAL);
   // icomb.events.ObjectManager: static icomb.machine.SetMachine setMachine
   ObjectManager.setMachine.reinicia(); // set 'icomb.machine.SetMachine.estado = ESTADO_INICIAL', redefine all
   pai.redraw();
   }
  else if (e.getSource() == btnHelp) {
   Help help = new Help();

   // String nElementos = choiceNumbers.getSelectedKey();
   // String universoFile =  choiceUniverses.getSelectedKey();
   // String universoNome =  choiceUniverses.getSelectedItem();
   // Universo uni = null;
   // try {
   //  uni = Parse-.-parseUniverso(GerenciadorRecursos.getInputStream(pai.getMf(), universoFile  ));
   // } catch(Exception ex) { MessageFrame.showMessage("Universo Inválido!", pai); return; }
   // uni.setNome(universoNome);
   // uni.setArquivo(universoFile);
   // UniversoFrame universoFrame = new UniversoFrame(pai.getMf());
   // universoFrame.setUniverso(uni);
   // universoFrame.setVisible(true);

   }

  }

 }
