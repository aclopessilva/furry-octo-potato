/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author Alexandre Eisenmann, Leônidas de Oliveira Brandão
 * @version Version 0.8.9 in 08/08/2013 - all files in a single JAR (iComb.jar includes all universes)
 *          First stable version in 30/04/2008
 *
 * @description Panel with components to choose the Universe set
 *
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.ui;

import icomb.components.CustomChoice;
import icomb.events.ObjectManager;
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
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseUniversePanel extends Panel implements  ActionListener {

  private String title;
  private final CustomChoice choiceNumbers;
  private CustomChoice choiceUniverses;
  private final TextArea txtArea;

  private final SetMachinePanel pai;

  private final Button btnMudarUniverso;
  private final Button btnHelp;

  public ChooseUniversePanel (SetMachinePanel pai) {
    this.pai = pai;

    //try { System.out.println("ChooseUniversePanel:"); String str = null; System.out.println(str.charAt(3));
    //}catch(Exception e) {e.printStackTrace();}

    Panel flowPanel = new Panel(new FlowLayout(FlowLayout.LEFT));
    flowPanel.setBackground(Configuracoes.corBarraSupInf);

    //L choiceNumbers = ObjectManager.criaCombo(Util.getListNumbers(50));

    // para valores (em resposta):
    // choiceNumbers-dica="Escolha o número de elementos do conjunto deste estágio" ou "Escolha o número de propriedades que definirão o conjunto formado por este estágio"
    choiceNumbers = ObjectManager.criaCombo(flowPanel, Util.getListNumbers(50),I18n.getString("choiceNumbers-dica"));
    // try{String str="";System.out.println(str.charAt(3));}catch(Exception  e){e.printStackTrace();}

    // choiceUniverses = ObjectManager.criaCombo(UniversosRecursos.getInstance().getUniversos(),UniversosRecursos.getInstance().getNomeUniversos());
    setLayout(new BorderLayout());
    txtArea = new TextArea(ObjectManager.setMachine.getDica(),2,5,TextArea.SCROLLBARS_NONE);
    txtArea.setBackground(Configuracoes.COR_DICA); // new Color(255,215,221)
    txtArea.setForeground(Color.BLACK);
    txtArea.setEditable(false);
    flowPanel.add(txtArea);

    Panel leftPanel = new Panel(new BorderLayout()) {


    public Insets getInsets() {
        return new Insets(0,0,0,5);
        }
      };
    leftPanel.add(txtArea,BorderLayout.CENTER);
    leftPanel.setBackground(Configuracoes.corBarraSupInf);

    add(leftPanel,BorderLayout.CENTER);
    setBackground(Configuracoes.corBarraSupInf);

    Panel panelButtons = new Panel(new BorderLayout());
    panelButtons.setBackground(Configuracoes.corBarraSupInf);
    btnMudarUniverso = ObjectManager.criaBotao(panelButtons, I18n.getString("solve"), I18n.getString("solve-dica")); // solve=Resolver Exercício
    btnMudarUniverso.addActionListener(this);
    panelButtons.add(btnMudarUniverso,BorderLayout.NORTH);

    // btnHelp = new Button(I18n.getString("help")); // "Ajuda"
    btnHelp = ObjectManager.criaBotao(panelButtons, I18n.getString("help"), I18n.getString("help-dica"));
    btnHelp.addActionListener(this);
    // panelButtons.add(btnHelp,BorderLayout.SOUTH);

    add(panelButtons,BorderLayout.EAST);
    this.setVisible(true);

    }


  public Dimension getPreferredSize () {
    return new Dimension(Constants.PANEL_WIDTH, 80);
    }

  public void paint (Graphics g) {
    txtArea.setText(ObjectManager.setMachine.getDica());
    g.setColor(Color.white);
    g.drawString(I18n.getString("help"), 5, 15); // texto no topo da janela de dica
    g.setColor(Color.black);
    g.drawRect(2, 2, getSize().width-4, getSize().height-4);
    }


  public Insets getInsets () {
    return new Insets(20,5,5,5);
    }

  public void actionPerformed (ActionEvent e) {
    System.out.println("\n---------------------\nsrc/icomb/ui/ChooseUniversePanel: " + e.getSource() + ", " + btnMudarUniverso + " ******************* ");

    if (e.getSource() == btnMudarUniverso) {
       String strAjuda = "Acione o botão \"Novo Estágio\" para iniciar construção. Para ver detalhes do Universo acione o botão " +
                         "\"Mostrar Universo\" ao lado. O botão \"Ajuda\" abrirá o sistema de Help do iComb.";
       ObjectManager.setMachine.mudaDic(strAjuda);
       //ObjectManager.setMachine.loadUniverso(pai.getMf());
       pai.redraw();
       }
    else
    if (e.getSource() == btnHelp) {
       String nElementos = choiceNumbers.getSelectedKey();
       String universoFile = choiceUniverses.getSelectedKey();
       String universoNome = choiceUniverses.getSelectedItem();
       Universo uni = null;
       try {
          uni = Parse.parseUniverso(GerenciadorRecursos.getInputStream(pai.getMf(), universoFile), universoFile);
        } catch(Exception ex) {
          //L MessageFrame.showMessage("Universo Inválido!", pai);
          //L invalid_universe={Universo inválido, Univers invalide}
          String msg = I18n.getString("invalid_universe");
          MessageFrame.showMessage(msg,pai);
          System.err.println("Erro: "+msg+": "+ex);
          return;
          }
       uni.setNome(universoNome);
       uni.setArquivo("icomb/", universoFile);

       UniversoFrame universoFrame = new UniversoFrame(pai.getMf());
       universoFrame.setUniverso(uni);
       universoFrame.setVisible(true);
       } // if (e.getSource() == btnHelp)

    } // public void actionPerformed(ActionEvent e)

  }