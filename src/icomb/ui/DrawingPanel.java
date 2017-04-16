/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author AEK, LOB
 * 
 * @description Panel with an instance image for the registered stages until now - this is the last panel before messsge bar of iComb
 * 
 * @see icomb.ui.SetMachinePanel.drawAgain()
 * 
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 * 
 */

package icomb.ui;

import icomb.events.ObjectManager;
import icomb.objects.Element;
import icomb.objects.Universo;
import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.GerenciadorRecursos;
import icomb.util.I18n;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import base.MontaFace;

//public class DrawingPanel extends Panel { // implements // MouseMotionListener ,  MouseListener {
public class DrawingPanel extends Panel implements MouseListener {

  private Universo universo = null; // the current Universo
  private Element [] handElement;
  private Image [] handImage;
  private final MontaFace mf;
  private boolean mouseOver = false;

  private Graphics goff;
  private Image offscreen;

  // at icomb.ui.SetMachinePanel.update(SetMachinePanel.java:166)
  // at icomb.ui.VerticalPanel.redraw(VerticalPanel.java:116)
  // at icomb.ui.VerticalPanel.<init>(VerticalPanel.java:88)
  // at icomb.ui.SetMachinePanel.<init>(SetMachinePanel.java:57)
  // at icomb.IComb.monta(IComb.java:438)
  // at icomb.IComb.init(IComb.java:239)
  public DrawingPanel (MontaFace mf) {
    // super(mf);
//try{String str=""; System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
System.out.println("\n\nDrawingPanel.DrawingPanel");
java.awt.Component comp = this; // ComponentPeer getPeer()
//java.awt.Container comp = this;
while(comp!=null) {
System.out.println(" - "+comp);
comp = comp.getParent();
}//getParent


    if (mf==null) {
      System.err.println("DrawingPanel.DrawingPanel: Error: MontaFace mf empty: " + mf + " ------------- ");
      // System.err.println(mf.getApplet()+"");	
      }

    this.mf = mf;

//    this.addMouseMotionListener(this);
    this.addMouseListener(this);

    recalcula(); //- nao pode ja haver solucao
    // tem que fazer: icomb.machine.SetMachine universo == null; icomb.machine.SetMachine estagioAuxiliar == null
    // icomb.objects.Universo 
    // ObjectManager.setMachine.getEstagiosParaDesenhar() - tem que estar vazia - Vector estagios

    // System.out.println("DrawingPanel: #handElement="+handElement.length);
    }


  // Impartant to change the instance image representing the set
  public void recalcula () {
    if (ObjectManager.setMachine.getUniverso() == null) {
      handElement = new Element[0];
      }
    else { // icomb.events.ObjectManager - icomb.machine.SetMachine
      this.universo = ObjectManager.setMachine.getUniverso();
      handElement = this.universo.getPossivelColecao(ObjectManager.setMachine.getEstagiosParaDesenhar());
      // 'this.handElement.length' = the size of the instance

/*
handImage = new Image[0]; //___
int sizeOfHand = this.handElement.length;
handImage = new Image[sizeOfHand]; //___
for (int i_=0; i_<sizeOfHand; i_++) {
handImage[i_] = GerenciadorRecursos.getImagem(this.mf, this.handElement[i_].getName());
System.err.println("DrawingPanel.recalcula(): "+this.handElement[i_].getName());
} */

      }
    // System.out.println("DrawingPanel.recalcula(): #handElement="+handElement.length);
    }


  private boolean pinta = true;
  public void myPaint () {
pinta = true;
    update(goff);
    }
  public void repaint () {}
  public void paint (Graphics g) {
  }
  public void update (Graphics g) {
//System.out.println(pinta);
//if (!pinta) return;
    if (offscreen == null)
      offscreen = createImage(getWidth(), this.getHeight());
    goff = offscreen.getGraphics();
    if (goff==null || offscreen==null)
      return;

//Graphics goff = g;

    if (handElement == null || handElement.length==0)
      recalcula();
    if (handElement.length>0 && mouseOver)
      goff.setColor(new Color(255,255,128));
    else
      goff.setColor(Color.WHITE);

    goff.fillRect(2, 2, getWidth()-4, getSize().height-19);
    goff.setColor(new Color(180,180,180));
    goff.drawRect(2, 2, getWidth()-4, getSize().height-19);

    int left=3;
    int top=3;
    String nomeArquivo = "";
    String dirImagens = "";
    Universo _universo = this.universo; // the current Universo
    if (_universo==null) { // try to fix this...
       _universo = ObjectManager.setMachine.getUniverso();
       this.universo = _universo;
       }

    if (_universo != null) {
      nomeArquivo = _universo.getArquivo();
      //Q dirImagens = nomeArquivo.substring(0,nomeArquivo.indexOf("/")) + "/imagens/";
      // You must get the complete path of each image, like '/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/baralho/imagens/' with application
      dirImagens = nomeArquivo.substring(0,nomeArquivo.lastIndexOf("/")) + "/imagens/";
      }

    if (handElement.length>0 && mouseOver) {
      goff.setColor(Configuracoes.FACE_DARK1);
      // "Clique na imagem para gerar outra configuração"
      goff.drawString(I18n.getString("drawingClickImage1"),3,120);
      }

    String nameOfImage = "";
    // System.out.println("DrawingPanel.paint(): #handElement="+handElement.length);
    for (int i= 0; i<handElement.length; i++) try {
      nameOfImage = dirImagens + handElement[i].getImagem();

      // Create a new image
      Image cardImage = GerenciadorRecursos.getImagem(this.mf, nameOfImage);
//Image cardImage = handImage[i]; //__

      if (cardImage==null) {
        System.err.println("Error: in 'DrawingPanel.paint': " + nameOfImage);
        continue; // try the next one
        }
      if (left+cardImage.getWidth(this)>Constants.PANEL_WIDTH) {
        left = 3;
        top += cardImage.getHeight(this);
        }

      goff.drawImage(cardImage,left,top,cardImage.getWidth(this),cardImage.getHeight(this),this);
      left += cardImage.getWidth(this); // put the new image at the right side of those already painted

      } catch (Exception e) {
        System.err.println("Error: failed in image drawing "+i+": "+nameOfImage+": "+e);
        e.printStackTrace();
        }

    if (g!=null && offscreen!= null)      g.drawImage(offscreen, 0, 0, this);
//if (count++<2)g.drawImage(offscreen, 0, 0, this);
pinta = false;

    } // public void update(Graphics g)
private static int count=0;



  public Dimension getPreferredSize () {
    return new Dimension(Constants.PANEL_WIDTH, 125);
    }


  public void mouseDragged (MouseEvent e) {
    }

  public void mouseMoved (MouseEvent e) {
    /*
    if (!mouseOver) {
      mouseOver = true;
      recalcula();
      repaint();	
      }*/
    }

  public void mouseClicked (MouseEvent e) {
    if (handElement.length>0) {
      recalcula();
pinta = true;
      myPaint(); // repaint();
pinta = false;
      }
    }

  public void mouseEntered (MouseEvent e) {
    mouseOver = true;
    recalcula();
pinta = true;
    myPaint(); // repaint();	
pinta = false;
    }


  public void mouseExited (MouseEvent e) {
    mouseOver = false;
pinta = true;
    myPaint(); // repaint();
pinta = false;
    }

  public void mousePressed (MouseEvent e) {
    }

  public void mouseReleased (MouseEvent e) {
    }

  }
