/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br / http://www.usp.br/line
 *
 * @author AEK, LOB
 * 
 * @description A panel with a single image from ONE element from the universe. This class extends Panel to show the universe from 'icomb.ui.UniversoFrame'
 * 
 * @see icomb/ui/SetMachinePanel.java: void update()
 * @see icomb/ui/UniversoFrame.java: for (int i=0; i<elementos.size(); i++) { ... ImagePanel imgPanel = new ImagePanel(mf); ... }
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.ui;

import icomb.IComb;
import base.MontaFace;
import icomb.events.ObjectManager;
import icomb.objects.Element;
import icomb.objects.Universo;
import icomb.util.GerenciadorRecursos;
import icomb.util.I18n;
import icomb.LocalizacaoImagens; //?

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.Set;


public class ImagePanel extends Panel {

  private Element element;
  private MontaFace mf;
  private boolean mouseOver = false;

  private Image imgItemUniverso = null; // icomb.ui.TrataImagem.pegaImagem(nameOfImage)
  private Image imgItemUniversoToInstances;

  private String nome;
  private Graphics goff;
  private Image imagem;

  // @see icomb/ui/UniversoFrame.java: load(): 'for (int i=0;...) { ... ImagePanel imgPanel = new ImagePanel(mf); ...}'
  // @see icomb/ui/UniversoFrame.java: UniversoFrame(MontaFace mf, Universo universo): 'if (universo!=null) load();'
  // @see icomb/ui/DefinedUniversePanel.java: actionPerformed(ActionEvent e): 'universoFrame = new UniversoFrame(pai.getMf(), universo);'
  public ImagePanel (MontaFace mf) {
    this.mf = mf;
    //D try { String str=""; System.out.println(str.charAt(3)); }catch(Exception e) {e.printStackTrace();}
    }

  public String getNome () {
    return this.nome;
    }

  // @see icomb/ui/UniversoFrame.java: load(): 'img_aux = imgPanel.getElement(universo, element);'
  //      'load()' uses ImagePanel.imagem, defined here, in 'setElement(Universo universo, Element element, String nome, Image img)'
  public Image setElement (Universo universo, Element element, String nome) {

    String nomeArquivo = universo.getArquivo(); // icomb/objects/Universo.java: setArquivo(String directory, String arquivo)
    String dirImagens = universo.getDirectory(); // 2008-2013: it must be { "baralho/imagens/" or "futebol/imagens/" }"
    String nameOfImage = dirImagens + element.getImagem();

    // 28-30/08/2014
    // TODO: eliminar as 3 linhas abaixo eliminando o caso antes dele chegar aqui (?)
    // When application with JAR (java -jar iComb.jar) and inline construction of exercise
    // If image name is 'icomb/futebol/imagens/az1.png' is wrong, must be '/futebol/imagens/az1.png'
    int count = nameOfImage!=null ? nameOfImage.length() : 0;
    if (count>5 && nameOfImage.substring(0,5).equalsIgnoreCase("icomb"))
       nameOfImage = nameOfImage.substring(5,count);

    // icomb.ui.TrataImagem.pegaImagem(nameOfImage);
    if (this.imgItemUniverso==null)
       this.imgItemUniverso = GerenciadorRecursos.getImagem(mf, nameOfImage);

    this.element = element;
    this.imagem = this.imgItemUniverso;
    this.nome = nameOfImage;
    this.imgItemUniversoToInstances = GerenciadorRecursos.getImagem(mf, nameOfImage); // duplicate Image this one used exclusivelly by the 'icomb.ui.DrawingPanel'

    return imgItemUniverso;

    } // public Image setElement(Universo universo, Element element, String nome)



  public int getImageWidth () {
    return imgItemUniverso.getWidth(this);
    }

  public void paint (Graphics g) {
    if (goff==null && this.getSize().getWidth()>0 && this.getSize().getHeight()>0) {
       if (this.getWidth() <= 0 || this.getHeight() <= 0) {
          System.err.println("icomb/ui/ImagePanel: dim negativa: "+imgItemUniverso);
          return;
          }
       imagem = createImage(this.getWidth(), this.getHeight());
       if (imagem != null)
          goff = imagem.getGraphics();
       }
    if (goff==null || imagem==null)
       return;

    if (element == null) return;
    goff.drawImage(imgItemUniverso,0,0,imgItemUniverso.getWidth(this),imgItemUniverso.getHeight(this),this);
    Set set = element.getAttributes();
    Iterator it = set.iterator();
    int y = 10;
    int x = 10;

    if (g!=null && imagem!= null)
       g.drawImage(imagem, 0, 0, this);

    while (it.hasNext())  {
       String attribute = (String) it.next();
       Set pred = element.getPredicates(attribute);
       Iterator it2 = pred.iterator();
       String value = element.getAttribute(attribute);
       StringBuffer sb = new StringBuffer(""+I18n.getInstance().getString(attribute) + " = " + I18n.getInstance().getString(value));
       while (it2.hasNext()) {
          String predicado = (String) it2.next();
          sb.append(", " + I18n.getInstance().getString(predicado));
          }
       g.drawString(sb.toString(),x+imgItemUniverso.getWidth(this),y);
       y += 15;
       } // while (it.hasNext())
    } // void paint (Graphics g)

  private static int count=0; //Debug
  private static int staticID = 0; //Debug
  private int ID = staticID++; //Debug

  public Dimension getSize () {
    //D if (count++==0) System.out.println("icomb/ui/ImagePanel.java: getSize(): nome="+nome+", "+imgItemUniverso);
    if (imgItemUniverso==null) {
       System.err.println("Error: see 'imgItemUniverso', image from current universe is empty "+nome+" (ID="+ID+")");
       return null;
       }
    return new Dimension(imgItemUniverso.getWidth(this)+200, imgItemUniverso.getHeight(this));
    }


  public Dimension getPreferredSize () {
    try {
      return new Dimension(imgItemUniverso.getWidth(this)+200, imgItemUniverso.getHeight(this));
    } catch (Exception e) {
      System.err.println("Erro: painel imagem: getPreferredSize: "+nome+": "+imgItemUniverso+": "+e); // e.printStackTrace();
      return null;
      }
    }

  }
