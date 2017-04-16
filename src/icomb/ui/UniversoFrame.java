/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author Alexandre Eisenmann, Leônidas de Oliveira Brandão
 * @version
 *
 * @description
 *
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of S<E3>o Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Le<F4>nidas O. Brand<E3>o.
 *
 */

package icomb.ui;

import icomb.objects.Element;
import icomb.objects.Universo;
import icomb.util.I18n;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Image;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import base.MontaFace;

public class UniversoFrame extends PopUpFrame implements ActionListener, WindowListener {

  public static final int
         WIDTH  = 300,    // altura total da janela
         HEIGHT = 500; // altura da imagem com logo: icomb/cabecalho_icomb.gif 741 x 32 - used in 'base.MontaFace'
   
  private MontaFace mf;
  private Universo universo;


  // From: icomb.ui.DefinedUniversePanel.actionPerformed(DefinedUniversePanel.java:178)
  public UniversoFrame (MontaFace mf) {
    this(mf, null);
    }
   

  // From: icomb.ui.DefinedUniversePanel.actionPerformed(DefinedUniversePanel.java:178)
  public UniversoFrame (MontaFace mf, Universo universo) {
    super(I18n.getString("windowShowUniverse")); // windowShowUniverse=Universo atual
    this.mf = mf;
    this.universo = universo;
    int width = WIDTH;
    int height = HEIGHT;
    setSize(width,height);
    setLocationRelativeTo(mf);
    setVisible(false);
    this.addWindowListener(this);
    if (universo!=null)
      load();
    // System.out.println("\n\nUniversoFrame(): universo="+universo); System.out.println("\n\nUniversoFrame(): universo="+universo.getElementos());
    }

  public void setUniverso (Universo universo) {
    if (this.universo == null) {
       this.universo = universo;
       load();
       }
    }

  // Load universe images: baralho/imagens/*gif; futebol/imagens/*gif
  public void load () {
    Vector elementos = universo.getElementos();
    GridLayout layout = new GridLayout(elementos.size()+2,1);
    Panel panelPrincipal = new Panel(layout);
    Label label = new Label(I18n.getString("universe"));
    Image img_aux = null;
    panelPrincipal.add(label); // universe = Universo

    //T System.out.println("UniversoFrame.java: load(): #elementos="+elementos.size());
    //T System.out.println("UniversoFrame.java: load(): universo.directory="+universo.getDirectory()+", universo.arquivo="+universo.getArquivo());
    for (int i=0; i<elementos.size(); i++) {
       Element element = (Element) elementos.get(i);
       ImagePanel imgPanel = new ImagePanel(mf); // Panel with all images

       // icomb.objects.Universo
       // Get the Image from JAR or system file
       imgPanel.setElement(universo, element, imgPanel.getNome()); // define imagem ('Image') e nome da mesma

       //T System.out.println(i+": "+imgPanel.getNome());//System.out.println(i+": "+img_aux);

       panelPrincipal.add(imgPanel);

       }
    ScrollPane scroll = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
    scroll.add(panelPrincipal);
    add(scroll);
    try {
      setVisible(true);
    } catch (Exception e) {
      System.err.println("UniversoFrame.load(): Error to show window!");
      e.printStackTrace();
      }
    } // public void load()

  public void actionPerformed (ActionEvent e) {
    setVisible(false);
    repaint();
    }

  public void windowActivated (WindowEvent e) {  }

  public void windowClosed (WindowEvent e) {  }

  public void windowClosing(WindowEvent e) {
    setVisible(false);
    repaint();
    }

  public void windowDeactivated (WindowEvent e) {  }

  public void windowDeiconified (WindowEvent e) {  }

  public void windowIconified (WindowEvent e) {  }

  public void windowOpened (WindowEvent e) {  }

  }
