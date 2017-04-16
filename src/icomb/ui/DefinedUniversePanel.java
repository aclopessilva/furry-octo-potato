/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br / http://www.usp.br/line
 *
 * @author Alexandre Eisenmann, Leônidas de Oliveira Brandão
 * @version
 *
 * @description Painel "Ajuda": substitui primeiro painel 'icomb/ui/DefinedExercisePanel.java'
 *
 * @see icomb/ui/DefinedExercisePanel.java
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


public class DefinedUniversePanel extends Panel implements ActionListener, ItemListener {

  private String title;
  private final TextArea label;
  private Exercicio [] exercises;
  private final SetMachinePanel pai;
  private final Button btnUniverso;
  //R private final Button btnHelp;
  private final Panel leftPanel;

  private CustomChoice choiceUniverses;

  public DefinedUniversePanel (SetMachinePanel pai) {
    // System.out.println("DefineUniversePanel: ");
    //try{String str=""; System.out.println(str.charAt(3)); }catch(Exception e){e.printStackTrace();}

    this.pai = pai;
    this.setBackground(Configuracoes.corBarraSupInf);

    setLayout(new BorderLayout());
    String text ="";
    if (ObjectManager.setMachine.getUniverso() != null) {
       text = I18n.getString("theSetOfSubsetsWith")+" "+ObjectManager.setMachine.getNElementos()+ " "+
       I18n.getString("elementsTakenIn")+" "+I18n.getString(ObjectManager.setMachine.getUniverso().getNome());
       }
    //try{String str=""; System.out.println(str.charAt(3)); }catch(Exception e){e.printStackTrace();}

    //label = new TextArea(text,2,5,TextArea.SCROLLBARS_NONE);
    label = new TextArea(ObjectManager.setMachine.getDica(),2,5,TextArea.SCROLLBARS_NONE);
    label.setBackground(new Color(255,215,221));
    label.setForeground(Color.BLACK);
    label.setEditable(false);

    leftPanel = new Panel(new BorderLayout()) {

    public Insets getInsets() {
        return new Insets(0,0,0,5);
        }
      };
    leftPanel.setBackground(Configuracoes.corBarraSupInf);
    leftPanel.add(label,BorderLayout.CENTER);

    add(leftPanel,BorderLayout.CENTER);

    Panel panelButtons = new Panel(new BorderLayout());
    // panelButtons.setBackground(Configuracoes.corBarraSupInf); - nao deixar fundo cinza padrao

    // seeUniverse=Mostrar Universo
    btnUniverso = ObjectManager.criaBotao(panelButtons, I18n.getString("seeUniverse"), I18n.getString("seeUniverse-dica"));
    btnUniverso.addActionListener(this);
    panelButtons.add(btnUniverso,BorderLayout.NORTH);

    // Choose the universe set: "baralho/baralho.xml" or "futebol/futebol.xml"
    // changeUniverse=Mudar Universo; universe=Universo
    choiceUniverses = ObjectManager.criaCombo(panelButtons, icomb.util.Util.geUniversetList(), I18n.getString("changeUniverse")); // -dica
    choiceUniverses.addItemListener(this);
    panelButtons.add(choiceUniverses,BorderLayout.SOUTH);
    if (!ObjectManager.setMachine.isModoCriacao()) {
      choiceUniverses.setEnabled(false); // it is not an exercise creation => do not allow to alter the Universe set
      }

    //R btnHelp = ObjectManager.criaBotao(panelButtons, I18n.getString("help"), I18n.getString("help-dica")); // help=Ajuda
    //R btnHelp.addActionListener(this);
    //R panelButtons.add(btnHelp,BorderLayout.SOUTH);

    add(panelButtons,BorderLayout.EAST);
    this.setVisible(true);
    }

  public void mudaDica () {
    label.setText(ObjectManager.setMachine.getDica());
    this.repaint();
    }

  public Dimension getPreferredSize () {
    return new Dimension(Constants.PANEL_WIDTH, 80);
    }

  // Redesenha painel com "Ajuda": troca msg de ajuda

  public void paint (Graphics g) {
    label.setText(ObjectManager.setMachine.getDica());
    g.setColor(Color.white);
    g.drawString(I18n.getString("help"), 5, 15);
    g.setColor(Color.black);
    g.drawRect(2, 2, getSize().width-4, getSize().height-4);
    }

  public Insets getInsets () {
    return new Insets(20,5,5,5);
    }

  // Take note of the universe
  public void itemStateChanged (ItemEvent e) {
    if (e.getSource() == choiceUniverses) {
     String setChoosen = choiceUniverses.getSelectedKey();
     System.out.println("DefinedUniversePanel.java: setChoosen="+setChoosen);
     Universo uni = null;
     try {
        pai.getDePanel().setEnabled(); // SetMachinePanel pai: DefinedExercisePanel getDePanel { return dePanel; } - enable Start button

        // Universe 1:  "baralho/baralho.xml" = icomb.util.Util.univSet1 ( universeDeck )
        // Universe 2:  "futebol/futebol.xml" = icomb.util.Util.univSet2 ( universeFoot )
        //TODO 29/05/2014 - e os demais?????
        String strUniversePath = icomb.util.GerenciadorRecursos.getPath(this);
        String strUniverseSet =  null;
        String strUniverse = null;

        strUniverseSet =  icomb.util.Util.univSet1; // default is 'universeDeck'
        if (setChoosen.equals(I18n.getString("universeFoot")))
           strUniverseSet = icomb.util.Util.univSet2;
        //TODO: any new universe must have a new entrance here

        if (strUniversePath!=null && strUniversePath.equals("/"))
          strUniversePath = "icomb/";

        // Must be:
        // strUniversePath="/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb";
	// strUniverseSet="/baralho/baralho.xml";
        //T
System.out.println("DefinedUniversePanel.itemStateChanged:\n * strUniversePath = "+strUniversePath
+"\n * strUniverseSet = "+strUniverseSet+"\n * strUniverse = "+strUniverse);
        strUniverse = strUniversePath + strUniverseSet; // default is 'universeDeck'
	// DefinedUniversePanel.itemStateChanged: strUniversePath=file:/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/, strUniverse=file:/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/baralho/baralho.xml
        //T strUniverse="/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/baralho/baralho.xml";

        uni = icomb.util.Parse.parseUniverso(strUniverse); // here it is necessary a complete path
//        at icomb.util.Parse.parseUniverso(Parse.java:49)

        //_ desligado por introduzir 'strUniverseSet' que fica apenas com 'baralho/baralho.xml' ou 'futebol/futebol.xml'
        //_ int index = strUniverse.lastIndexOf("icomb/"); // position of "icomb/"
        //_ if (index>0) { // it is not at the begning => clear it
        //_    strUniverse = strUniverse.substring(index+6, strUniverse.length()); // get from (excludin) 'icomb/' => "baralho/baralho.xml" or "futebol/futebol.xml"
        //_    // System.out.println("src/icomb/ui/DefinedUniversePanel.java: strUniverse="+strUniverse+", uni=" + uni);
        //_    }
        // System.out.println("DefinedUniversePanel.itemStateChanged: strUniverseSet="+strUniverseSet+", strUniverse="+strUniverse);

        // tem que definir: icomb.machine.SetMachine.arquivoUniverso para icomb.events.ObjectManager.setMachine.getUniverso()
        // - usdo em 'icomb.ui.ConstructionPanel.habilitarNovoEstagio'
        // uni.setArquivo("icomb/", strUniverse);
        uni.setArquivo(strUniversePath, strUniverse);
        // System.out.println("DefinedUniversePanel.itemStateChanged: strUniversePath="+strUniversePath+", strUniverseSet="+strUniverseSet+", strUniverse="+strUniverse);

        ObjectManager.setMachine.setUniverso(uni); // sets 'SetMachine.arquivoUniverso'
	// habilitar botao para "iniciar" construcao de solucao para exercicio: Button btnIniciar('startExercise')
     } catch (Exception ex) {
       String msg = I18n.getString("invalid_universe");
       MessageFrame.showMessage(msg,pai);
       System.err.println("Erro: in 'DefinedUniversePanel.java': "+msg+": "+ex);
       ex.printStackTrace();
       return;
       }
     // System.out.println("DefinedUniversePanel.java: "+setChoosen+",  uni.nome="+uni.getArquivo());
     }
   }


  // Event: open window with icons of all the Universe elements
  public void actionPerformed (ActionEvent e) {
    if (e.getSource() == btnUniverso) {
       UniversoFrame universoFrame = null;
       try { // at icomb.ui.DefinedUniversePanel.actionPerformed(DefinedUniversePanel.java:199)
         // universoFrame.setUniverso(ObjectManager.setMachine.getUniverso()); //  icomb.objects.Universo universo
         Universo universo = ObjectManager.setMachine.getUniverso();
         universoFrame = new UniversoFrame(pai.getMf(), universo);
       } catch (Exception ex) { // icomb.ui.UniversoFrame.load(UniversoFrame.java:76): setVisible(true);
         String msg = I18n.getString("universe_empty");
         MessageFrame.showMessage(msg,pai);
         System.err.println("Erro: "+msg+": "+ex);
	 ex.printStackTrace();
         return;
         }
       universoFrame.setVisible(true);
       }
    //    else { Help help = new Help(); }
    } // void actionPerformed(ActionEvent e)

  }
