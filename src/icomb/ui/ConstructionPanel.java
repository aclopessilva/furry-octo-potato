/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author AEK, LOB
 * @description Panel with all components related with one stage (stage decomposition and validation messages)
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */


package icomb.ui;

import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.I18n;

import java.awt.Dimension;
import java.awt.Insets;

public class ConstructionPanel extends VerticalPanel {

  private final NewStageButtonPanel painel_nsbp;
  private final StagePanel painel_sp;
  private final ListPanel painel_lp;
  // private ConstructionFooterPanel cfp;
  private final SetMachinePanel painelSetMachine;

  // icomb.ui.SetMachinePanel.update(SetMachinePanel.java:107)
  public ConstructionPanel (SetMachinePanel pai) {
    super(null);
    // try{String str="";System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
    this.painelSetMachine = pai;
    // System.out.println("icomb/ui/ConstructionPanel: painelSetMachine="+painelSetMachine);

    painel_nsbp = new NewStageButtonPanel(this);
    painel_sp = new StagePanel(this); // painel para montar estágio
    painel_lp = new ListPanel(this); // painel para listar estágios após validá-lo (em formato texto)
    // System.out.println("icomb/ui/ConstructionPanel: #width="+painel_lp.getWidth()+", heigth="+painel_lp.getHeight());

    // cfp = new ConstructionFooterPanel(this);

    //    setTitle("construction");
    setDesenhaBorda(true);
    // setDimension(new Dimension(Configuracoes.LARG_CENTRAL, Configuracoes.ALT_CENTRAL_2)); // 590,383
    // setSize(Configuracoes.LARG_CENTRAL, Configuracoes.ALT_CENTRAL_2); <- nao, e def. para valer em 'StagePanel.getPreferredSize()'
    setBackground(Configuracoes.CINZA); // new Color(230,230,230)

    addComponent(painel_nsbp); //
    addComponent(painel_sp);

    //1 java.awt.Panel painel = new java.awt.Panel();
    //1 painel.add(painel_lp); painel.add(cfp); addComponent(painel);
    //2 painel_lp.addEsq(cfp); addComponent(painel_lp);
    addComponent(painel_lp);
    // addComponent(cfp);
    redraw();
    }

  public Insets getInsets () {
    return new Insets(3,3,3,3); // top padding of GridBagLayout
    }

  // import icomb.events.ObjectManager;
  // src/icomb/ui/SetMachinePanel.java: void habilitarNovoEstagio(): clicar em "iniciar" resolução de exercício
  public void habilitarNovoEstagio () {
    painel_nsbp.open(); // src/icomb/ui/NewStageButtonPanel btnNewStage.setEnabled(true)

    // para carregar atributos em 'StagePanel.chAttributes1' via 'chAttributes1.populate(universo.getAtributos())'
    // If it is an exercise construction: the universe must be defined
    painel_sp.carregaAtributos(icomb.events.ObjectManager.setMachine.getUniverso());

    }

  // From: src/icomb/ui/StagePanel.java - addStage()
  public void addStagePanel () {
    // ListPanel painel_lp
    // painel_lp.redo(); - this remove all 'icomb.components.CustomList' from 'icomb.ui.ListPanel'
    painel_lp.addStageFromConstructionPanel(); // this version just add the last stage ((Estagio) ObjectManager.setMachine.getEstagios().lastElement())
    painel_nsbp.open(); // NewStageButtonPanel painel_nsbp
    painelSetMachine.drawAgain(); // SetMachinePanel painelSetMachine: does 'DrawingPanel.recalcula()'
    }

  public void redrawPanel () {
    painelSetMachine.drawAgain();
    }

  public void stageWasDeleted () {
    painelSetMachine.drawAgain();
    painel_nsbp.enabledResetConstruction(false); // does 'NewStageButtonPanel.btnResetConstruction.setEnabled(false)'
    }

  public void openStagePanel () {
    painel_sp.open();
    }

  public void validateConstruction () {
    painelSetMachine.redraw();
    }

  public void habilitaBotaoEuTerminei (boolean habilita) {
    painel_nsbp.habilitaBotao(habilita);
    }

  // :- LMS -:
  // Prepare and send to the LMS the student response
  // From: src/icomb/ui/NewStageButtonPanel.java; 
  public boolean criarExercicio () {
    if (!icomb.events.ObjectManager.setMachine.isModoCriacao()) { // just in case...
       // Sorry, but it must have occurred an internal error. I could not certify that it was the construction of an exercise ...
       String msgErr = I18n.getString("errSystemCreatNot");
       System.err.println("Error: in ConstructionPanel: "+msgErr);
       MessageFrame.showMessage(msgErr, this.painelSetMachine); // 
       }
    if (!base.MontaFace.staticApplet) { // is exercise creation from desktop version
       String strFileExerc = this.painelSetMachine.getCePanel().storeExercise(); // SetMachinePanel.java - ChooseExercisePanel.java:storeExercise()
       System.out.println("ConstructionPanel.java: storeExercise(): "+strFileExerc);
       if (strFileExerc==null || strFileExerc=="")
          return false;
       // Save the file
       java.awt.FileDialog fd = new java.awt.FileDialog(icomb.IComb.appletFrame, "iMath :: iComb", java.awt.FileDialog.SAVE); //
       fd.setFile("*.cmb");
       fd.setDirectory(".");
       fd.setVisible(true);
       if (fd.getFile() != null) {
         String nameFile = fd.getDirectory() + fd.getFile();
         icomb.util.Util.storeFile(nameFile, strFileExerc, "ConstructionPanel.java");
         // System.out.println("ConstructionPanel.java: storeExercise(): OK");
         return true;
         }
       //System.out.println("ConstructionPanel.java: storeExercise(): NAO");
       return false;
       }
    else // is exercise creation through the Web
       return painelSetMachine.sent2Server(); // SetMachinePanel
    } // boolean criarExercicio()

  public void apagaEstagioAtual () {
    painel_sp.close();
    }

  public Dimension getPreferredSize () { //- usar o próprio 'VerticalPanel' com 'Configuracoes.ALT_CENTRAL_ESTAGIO'
    return new Dimension(Constants.PANEL_WIDTH, Configuracoes.ALT_CENTRAL);
    }

  }
