/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author AEK, LOB
 * @description A panel with the exercise (under construction/creation or under solution)
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
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
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ajuda.Help;

public class DefinedExercisePanel extends Panel implements ActionListener {

  private String title;
  private final TextArea label;
  //  private Exercicio [] vetExercicios;
  private final SetMachinePanel painelSetMachine;
  private final Button btnIniciar;
  private final Button btnAjuda;
  private final Button btnBack; // button to go back to the beginning - btnCriar;

  // src/icomb/ui/SetMachinePanel.java
  public void setEnabled () { btnIniciar.setEnabled(true); } // SetMachinePanel.void update(): if (estadoAtualMachine==SetMachine.ESTADO_EXERCICIO_CONFIRMADO)
  public void setDisabled () { btnIniciar.setEnabled(false);
    // try{String str=""; System.out.println(str.charAt(3)); }catch(Exception e){e.printStackTrace();} 
    } // SetMachinePanel.void update(): at the beggining, false by default

  public DefinedExercisePanel (SetMachinePanel pai) {

    this.painelSetMachine = pai;
    // System.out.println("DefinedExercisePanel: painelSetMachine="+painelSetMachine);

    // -: enunciado do exercício
    // -:
    setLayout(new BorderLayout());
    String strStatement = "";
    if (ObjectManager.setMachine.getExercicioAtual()!=null)
      strStatement = ObjectManager.setMachine.getExercicioAtual().getLongDescription();;
    label = new TextArea(strStatement,2,5,TextArea.SCROLLBARS_NONE);
    label.setBackground(new Color(255,255,128));
    label.setForeground(Color.BLACK);
    label.setEditable(false);

    this.setBackground(Configuracoes.corBarraSupInf);

    Panel leftPanel = new Panel(new BorderLayout()) {

    public Insets getInsets() { return new Insets(0,0,0,5); }
      };
    leftPanel.setBackground(Configuracoes.corBarraSupInf);
    leftPanel.add(label,BorderLayout.CENTER);

    add(leftPanel,BorderLayout.CENTER);

    Panel panelButtons = new Panel(new BorderLayout());
    panelButtons.setBackground(Configuracoes.corBarraSupInf);

    // -: botao para iniciar resoluÃ§Ã£o do exercício
    // startExercise=Iniciar
    btnIniciar = ObjectManager.criaBotao(panelButtons, I18n.getString("startExercise"), I18n.getString("startExercise-dica"));
    if (ObjectManager.setMachine.getEstado() == SetMachine.ESTADO_CONSTRUCAO_FINALIZADA)
       btnIniciar.setEnabled(false);
    else
       btnIniciar.setEnabled(true);
    btnIniciar.addActionListener(this);
    //
System.out.println("DefinedExercisePanel: btnIniciar.isEnabled="+btnIniciar.isEnabled());

    // -: botao para ajuda
    btnAjuda = ObjectManager.criaBotao(panelButtons, I18n.getString("help"), I18n.getString("help-dica")); // help=Ajuda
    btnAjuda.addActionListener(this);

    //- // -: botao para criar novo exercício
    //- // changeExercise=Criar Exercício
    //- btnCriar = ObjectManager.criaBotao(panelButtons, I18n.getString("changeExercise"), I18n.getString("changeExercise-dica"));
    //- btnCriar.setEnabled(true);
    //- btnCriar.addActionListener(this);

    // -: button to go back to the beginning
    // back2start=Back
    btnBack = ObjectManager.criaBotao(panelButtons, I18n.getString("back2start"), I18n.getString("back2start-dica"));
    btnBack.addActionListener(this);

    Panel panelAux = new Panel(new BorderLayout());
    panelAux.setBackground(Configuracoes.corBarraSupInf);
    panelAux.add(btnAjuda,BorderLayout.SOUTH);

    panelButtons.add(btnIniciar,BorderLayout.NORTH);
    panelButtons.add(panelAux,BorderLayout.CENTER);
    panelButtons.add(btnBack,BorderLayout.SOUTH);

    add(panelButtons,BorderLayout.EAST);

    this.setVisible(true);
    }


  public Dimension getPreferredSize () {
    return new Dimension(Constants.PANEL_WIDTH, 100);
    }

  public void paint (Graphics g) {
    // System.out.println("icomb/ui/DefinedExercisePanel.java: "+I18n.getString("exercises"));
    if (ObjectManager.setMachine.getExercicioAtual()!=null)
       label.setText(ObjectManager.setMachine.getExercicioAtual().getLongDescription());
    g.setColor(Color.white); //
    g.drawString(I18n.getString("exercises"), 5, 15); //
    g.setColor(Color.black);
    g.drawRect(2, 2, getSize().width-4, getSize().height-4);
    }

  public Insets getInsets () {
    return new Insets(20,5,5,5);
    }

  public void actionPerformed (ActionEvent e) {
    //- if (e.getSource() == btnCriar) {
    //-    btnIniciar.setEnabled(false);
    //-    btnCriar.setEnabled(false);
    //-    ObjectManager.setMachine.criandoExercicio();
    //-    painelSetMachine.redraw(); // método herdado, em 'icomb/ui/VerticalPanel.java'
    //-    }
    if (e.getSource() == btnBack) { // go back to the beginning
      // ObjectManager.setMachine.setEstado(icomb.machine.SetMachine.ESTADO_INICIAL);
      // icomb.events.ObjectManager: static icomb.machine.SetMachine setMachine
      ObjectManager.setMachine.reinicia(); // set 'icomb.machine.SetMachine.estado = ESTADO_INICIAL', redefine all
      painelSetMachine.redraw(); // método herdado, em 'icomb/ui/VerticalPanel.java'
      }
    else
    if (e.getSource() == btnAjuda) {
      Help help = new Help();
      }
    else {
      // src/icomb/ui/DefinedExercisePanel.java: define 'SetMachinePanel painelSetMachine'
      // src/icomb/ui/SetMachinePanel.java: define 'ConstructionPanel cp'
      // src/icomb/ui/ConstructionPanel.java: define 'StagePanel painel_sp'

      // System.out.println("DefinedExercisePanel: "+e.getSource());
      btnIniciar.setEnabled(false);
      //- btnCriar.setEnabled(false);
      ObjectManager.setMachine.confirmaExercicio(); // em 'icomb/machine/SetMachine.java' faz 'estado=ESTADO_EXERCICIO_CONFIRMADO;'
      String strAjuda = "Acione o botão \"Novo Estágio\" para iniciar construção. " +
                        "Para ver detalhes do Universo acione o botão " +
                        "\"Mostrar Universo\" ao lado. O botão \"Ajuda\" abrirá o sistema de Help do iComb.";
      ObjectManager.setMachine.mudaDic(strAjuda); // muda a dica
      ObjectManager.setMachine.loadUniverso(painelSetMachine.getMf());
      painelSetMachine.habilitarNovoEstagio(); // src/icomb/events/ObjectManager.java: habilitar o botão para "novo estágio"
      //fazer 'carregaAtributos(Universo universo)'
      // painelSetMachine.redraw(); - eliminei "addComponent(*);" em 'src/icomb/ui/SetMachinePanel.java' => eliminar 'redraw' p/ evitar painel branco
      // System.out.println("DefinedExercisePanel: icomb.machine.SetMachine.universo="+ObjectManager.setMachine.getUniverso());

      }

    } // void actionPerformed(ActionEvent e)

  }
