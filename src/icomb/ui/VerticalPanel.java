/*
 * iComb: http://www.matematica.br/icomb
 *
 * AKE, LOB
 * Painel central: segundo bloco abaixo do enunciado do exercicio
 * Contem: container "abaixo" do painel de "Ajuda" e o painel "Construcao"
 */

package icomb.ui;

import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.I18n;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import base.MontaFace;


public class VerticalPanel extends Panel implements ComponentListener {

  private Panel aux;
  // private Dimension d = new Dimension(590, 60);

  // private Dimension dimension;
  private Vector panels = new Vector();

  protected final MontaFace mf; // accessed by 'icomb.ui.SetMachinePanel'
  private final GridBagConstraints cons = new GridBagConstraints();

  private String title;

  private boolean desenhaBorda;

  public boolean isDesenhaBorda () {
    return desenhaBorda;
    }

  public void setDesenhaBorda (boolean desenhaBorda) {
    this.desenhaBorda = desenhaBorda;
    }

  public MontaFace getMf () {
    return mf;
    }

  public String getTitle () {
    return title;
    }

  public void setTitle (String title) {
    this.title = title;
    }

  public void addComponent (Component component) {
    panels.add(component);
    }

  public void removeAllComponents () {
    panels = new Vector();
    }

  // Painel geral com: "Construcao"; "Estagio" e "Lista de estagios"
  public VerticalPanel (MontaFace mf) {
    this.mf = mf;
    // System.out.println("icomb/ui/VerticalPanel: "+Configuracoes.LARG_CENTRAL+","+Configuracoes.ALT_CENTRAL);
    this.setLayout(new BorderLayout());

    //dimension = new Dimension(Configuracoes.LARG_CENTRAL,Configuracoes.ALT_CENTRAL); // 590,368
    //dimension = new Dimension(Constants.PANEL_WIDTH,Configuracoes.ALT_CENTRAL); // 741,368

    this.setBackground(Configuracoes.CINZA); // 

    desenhaBorda = false;
    title = "";
    this.addComponentListener(this);
    redraw();
    }

  public void update () { 
    }

  // Panel: ??? Desenha bordas em preto (painel Construçao)
  public void paint (Graphics g) {
    if (desenhaBorda) {
       // System.out.println("VerticalPanel.java: paine: getSize().height="+getSize().height);
       String str = I18n.getString(title);
       // System.out.println("VerticalPanel: "+str);
       g.setColor(Configuracoes.FACE_DARK1); // (Color.white);
       g.drawString(str, 5, 15); // escreve título: "Construção"
       g.setColor(Color.black);
       g.drawRect(2, 2, getSize().width-4, getSize().height-4);
       }
    }


  public Insets getInsets () {
    if (desenhaBorda)
      return new Insets(20,5,5,5);
    else 
      return super.getInsets();
    }

  // Chamado em: icomb/ui/DefinedExercisePanel via método herdado 'pai.redraw();' sendo 'SetMachinePanel pai'
  public void redraw () {
    update(); // para desenhar painel
    if (aux != null) {
       this.remove(aux);
       aux = null;
       }

    GridBagLayout layout = new GridBagLayout();
    aux = new Panel (layout);
    aux.setBackground(Configuracoes.CINZA);
    int heightPos = 0;
    for (int i=0; i<panels.size(); i++) {
       // 0: icomb.ui.DefinedExercisePanel
       // 0: icomb.ui.DefinedExercisePanel; 1: icomb.ui.ChooseUniversePanel;
       // 0: icomb.ui.NewStageButtonPanel; 1: icomb.ui.StagePanel; 2: icomb.ui.ListPanel; 3: icomb.ui.ConstructionFooterPanel
       // 0: icomb.ui.DefinedExercisePanel; 1: icomb.ui.DefinedUniversePanel; 2: icomb.ui.ConstructionPanel; 3: icomb.ui.DrawingPanel
       cons.gridx = 0;
       cons.gridy = heightPos++;
       cons.gridwidth = 1;
       cons.gridheight = 1;
       aux.add((Component)panels.get(i),cons);
       // System.out.println(i+": "+((Component)panels.get(i))+" -> "+((Component)panels.get(i)).getBackground());
       }

    add(aux,BorderLayout.NORTH);
    validate();
    }

  // X Chamada em: 'icomb.ui.ChooseUniversePanel.actionPerformed(ChooseUniversePanel)' ao clicar em "resolver exercício"
  // desativado?
  // public void setDimension (Dimension dimension) {
  //  // System.out.println("VerticalPanel.java: dimension="+dimension+" this.dimension="+this.dimension);
  //  // this.dimension = dimension;
  //  }

  // ajusta o tamanho do painel principal
  // @Override
  // public Dimension getPreferredSize () { return dimension; }

  public void keyReleased (KeyEvent e) {}
  public void keyTyped (KeyEvent e) {}

  public void componentResized (final ComponentEvent e) {
    int w = e.getComponent().getWidth();
    if (w < Constants.PANEL_WIDTH) {
       Constants.PANEL_WIDTH = w;
       redraw();
       }
    else {
       Constants.PANEL_WIDTH = w;
       repaint();
       }
    }

  public void componentHidden (ComponentEvent e) {
    }

  public void componentMoved (ComponentEvent e) {
    }

  public void componentShown (ComponentEvent e) {
    }

  }

