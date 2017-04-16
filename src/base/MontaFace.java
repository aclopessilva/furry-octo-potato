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
 * @description Monta interface inicial (tela inicial)</p>
 * Utiliza a classe Configuracoes para estabelecer coordenadas e
 * dimensões dos painéis
 * 
 * Construct iComb structure: put its header
 * The central panel is build in 'icomb/IComb' under the name 'Panel centralPanel'
 * 
 * O Objeto MontaFace realiza todas as operações de "layout"
 * preparando a apresentação visual do programa. Instancia todos
 * os componentes gráficos visíveis na tela de apresentação e
 * arranja as respectivas posições e dimensões.<br>

 * @see icomb/ui/SetMachinePanel.java: void update()
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 * 
 */

package base;

import icomb.IComb;
import icomb.ui.ComponentImage;
import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.I18n;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MontaFace extends Panel implements KeyListener, EsquemaVisual { //, ComponentListener 

  // Imagem do topo do iComb
  public static final String
         // em "icomb/", mas diretório encontrado por 'LocalizacaoImagem'
         IMG_LOGO = "img/icomb_header.gif",  // "cabecalho-icomb.gif"
         IMG_LOGO_SMALL = "img/cabecalho-icomb_small.gif";
  public static String str_versao = "";

  /**
   * A barra de mensagens posicionada na parte inferior da tela de apresentação.
   */
  private final Label labelMensagens = new Label(); 

  private Panel northPanel, centerPanel;
  private Image imagem_logo = null; // imagem com logo
  Dimension d;

  Applet applet;
  public  Applet getApplet () { return applet; } //usado igraf.C.Sessao.refazSessao(String s)
  public boolean isApplet = false;
  public static boolean staticApplet = false;

  /**
   * Construtor padrão que instancia e configura todos os objetos da parte
   * gráfica do programa além de estabelecer os seus relacionamentos.
   * <li>Método chamado no construtor de IGraf </li><br>
   */
  public MontaFace (Applet applet, boolean isApplet) { //L
    //L GridLayout gl = new GridLayout(3, 1);
    //L this.setLayout(gl);
    this.isApplet = staticApplet = isApplet;
    this.applet   = applet;
    // this.addComponentListener(this);
    } // public MontaFace(Applet applet, boolean isApplet)

  // Vindo de IComb.Monta(): para permitir SetMachinePanel
  public void completaMontaFace (Applet applet, boolean isApplet) { //L
    //L d = new Dimension(Configuracoes.lTFP,Configuracoes.aTFP);

    setLayout(new BorderLayout());
    //L setSize(d);
    setBackground(corFundo);
    setForeground(corLetras); // = Configuracoes.COR_AZUL
    setFont(fontHP12);

    criaPainelSuperior(); // painel superior: com logo iComb
    add(northPanel, BorderLayout.NORTH);

    // Status bar or message bar
    labelMensagens.setBackground(corBarraSupInf); // src/base/EsquemaVisual.java: Color(32, 64, 128) = HTML: 20 40 80
    labelMensagens.setForeground(Color.white);
    labelMensagens.setFont(fontHP10);
    labelMensagens.setText(I18n.getString("iMath")); // "iMática / IME - USP"; "iMath / IME - USP"
    add(labelMensagens, BorderLayout.SOUTH);
  
    // pdp = paramPanel.pdp;
    setVisible(true);
    } // public MontaFace(Applet applet, boolean isApplet)


  public void exibeSlider () {
    // centerPanel.add(as, BorderLayout.SOUTH);
    centerPanel.validate();
    }
  
  public void removeSlider () {
    // centerPanel.remove(as);
    centerPanel.validate();
    }

  // icomb/IComb: adiciona "SetMachinePanel"
  public void addCentral (Panel painel) {
    add(painel, BorderLayout.CENTER);
    }

 
  private void criaPainelSuperior () {
    ComponentImage cmpImg_logo = null;
    str_versao = I18n.getString("versao")+": "+IComb.versao;
    try {
     // 'cabecalho-icomb.gif' é 741 x 32 / 'cabecalho-icomb_peq.gif' é 542 x 32

     if (icomb.IComb.getAppletWidth()<741) {
        imagem_logo = icomb.ui.TrataImagem.pegaImagem(IMG_LOGO_SMALL); // with "img/cabecalho-icomb_small.gif"
        // imagem_logo = icomb.util.GerenciadorRecursos.getImagem(this, "icomb/"+IMG_LOGO_SMALL); // with "icomb/img/cabecalho-icomb_small.gif"
        }
     else {
        imagem_logo = icomb.ui.TrataImagem.pegaImagem(IMG_LOGO); // with "img/icomb_header.gif"
        // imagem_logo = icomb.util.GerenciadorRecursos.getImagem(this, "icomb/"+IMG_LOGO); // with "icomb/img/icomb_header.gif"
        }
     // Configuracoes.lTFP = 828;
    } catch (Exception e) {
      imagem_logo = null;
      System.err.println("Erro: monta face, falta "+IMG_LOGO+"/"+IMG_LOGO_SMALL+": "+e);
      }

    cmpImg_logo = new ComponentImage(imagem_logo,IMG_LOGO); //
    try {
      int width_aux = imagem_logo.getWidth(null);
      if (width_aux>0) // evita erro quando imagem resultar vazia
         Constants.PANEL_WIDTH = imagem_logo.getWidth(null);
    } catch (Exception e) {
      imagem_logo = null;
      System.err.println("Erro: monta face, componente vazio? "+IMG_LOGO+": "+e);
      }
   
    northPanel = new Panel () {
      private Graphics goff;
      private Image ii;
     
      public void paint (Graphics g) {
        if (imagem_logo==null) return; // permite rodar mesmo faltando imagem do Logo 'cabecalho-icomb.gif'
        if (goff==null && this.getSize().getWidth()>0 && this.getSize().getHeight()>0) {
           ii = createImage(this.getWidth(), this.getHeight());
           if (ii != null)
              goff = ii.getGraphics();
           }
        goff.setFont(Configuracoes.FONTE_VERSAO);
        goff.setColor(Color.white);
        goff.drawRect(0, 0, getSize().width-1, getSize().height-1);
        goff.drawImage(imagem_logo, 2, 2, this);
        goff.drawString(str_versao, Configuracoes.xVersao, Configuracoes.yVersao); //
        g.drawImage(ii, 0, 0, this);
        }

      public Dimension getPreferredSize () {
        //return new Dimension(Constants.PANEL_WIDTH,Configuracoes.ALT_LOGO);
        return new Dimension(Configuracoes.lTFP, Configuracoes.ALT_LOGO);
        }
     
      public Insets insets () { // Rever: depreciado
        return new Insets(1,3,1,3);
        }
      }; // northPanel = new Panel()

    northPanel.setBackground(corBarraSupInf); 
    northPanel.setSize(Configuracoes.lTFP, Configuracoes.ALT_LOGO); // default size: 750 x 42

    //- ComponentImage cmpImg = new ComponentImage(TrataImagem.trataImagem(IMG_LOGO),IMG_LOGO); //
    //- java.awt.Image imgAux = null;
    //- try {
    //-   imgAux = TrataImagem.trataImagem(IMG_LOGO);
    //-   //if (imgAux==null || imgAux.getSize()==null)
    //-   cmpImg = new ComponentImage(imgAux,IMG_LOGO); //
    //- // catch (java.lang.NullPointerException npe)
    //- } catch (java.lang.Exception npe) {
    //-   System.out.println("[MF] erro ao tentar monta imagem "+IMG_LOGO+" - "+cmpImg+": "+npe);
    //-   //throw new Exception();
    //-   }
    //- Frame f_ = new Frame("teste");
    //- GridLayout gl = new GridLayout(2, 1);
    //- Dimension dim = northPanel.getSize();//getDimension();
    //- System.out.println("MontaFace: "+str_versao);
    //- System.out.println("MontaFace: dim(northPanel)=("+dim.width+","+dim.height+")");
    //- f_.setLayout(gl);
    //- f_.add(new Label(I18n.getString("versao")+": "+IComb.versao));
    //- f_.add(cmpImg);
    //- //f_.add(northPanel);
    //- f_.setSize(760,300);
    //- f_.setVisible(true);

    } //  void criaPainelSuperior()


  /**
   * Define a fonte para o texto que será desenhado no componente 'comp'
   * @param comp o componente onde se visualizará o texto
   * @param font a fonte desse texto
   * @param corLetras a cor da fonte
   * @param corFundo a cor do fundo do componente
   * <li>Método chamado no construtor</li><br>
   */
  static void defFonte (Component comp, Font font, Color corLetras, Color corFundo) {
    comp.setFont(font);
    comp.setForeground(corLetras);
    comp.setBackground(corFundo);
    }

  // Define as dimensões (alt. x larg.) da janela principal do iComb
  public void setSize () {
    this.setSize(Configuracoes.lTFP,Configuracoes.aTFP); // 750 x 600
    // System.out.println("MontaFace: lTFP="+Configuracoes.lTFP+", aTFP="+Configuracoes.aTFP);
    // this.setSize(Constants.PANEL_WIDTH, Configuracoes.ALT);
    //try {String str=""; System.out.println(str.charAt(3)); }catch(Exception e) {e.printStackTrace();}
    }

  // Define as dimensões (alt. x larg.) da janela principal do iComb
  public static void setSize (Frame fr) {
    fr.setSize(Configuracoes.lTFP, Configuracoes.aTFP); // 828 x 600
    // fr.setSize(Constants.PANEL_WIDTH,Configuracoes.ALT);
    // System.out.println("MontaFace: "+Configuracoes.LARG+", "+Configuracoes.ALT);
    }

  /**
   * Define as dimensões (altura x largura) do Frame fr que conterá a parte principal da inteface gráfica com o usuário do programa.
   * @param fr o frame principal do programa.
   * <li>Método chamado em IGraf.main()</li><br>
   * Chamado de: igraf/IGraf.main(...)
  public static void frameSize (Frame fr) {
    fr.setSize(Configuracoes.lTFP,Configuracoes.aTFP);
    }
   */

  /**
   * Fornece uma referência para o objeto AreaDeDesenho que
   * é usado pelo programa para exibição dos gráficos e animações.
   * <li>Método chamado nos construtores de IGraf, DesenhoDinamico,
   *     DesenhoEstatico e PainelBotoesAnimacao.</li><br>
   * @return <b>igCanvas </b> o Canvas onde são desenhados os gráficos
   */
  // public AreaDeDesenho getIgCanvas () {
  //   return igCanvas;
  //   }

  // Desenha bordas
 
  public void paint (Graphics gr) {
    Dimension tamanho;
    try {
      tamanho = this.getSize(); // size
     
      gr.setColor(Color.black);
      gr.drawRect(Configuracoes.xTFADD-1, Configuracoes.yTFADD-1, Configuracoes.lTFADD+2, Configuracoes.aTFADD+2);

      // desenha borda em barra de mensagem: this.labelMensagens
      gr.setColor(Color.gray); // (Color.black);
      gr.drawRect(Configuracoes.xTFMsg-1, Configuracoes.yTFMsg-1, Configuracoes.lTFMsg+1, Configuracoes.aTFMsg+1);
      }
    catch (Exception ex) {
      System.out.println("[MF] Erro");
      }
    } // paint()


  /**
   * Determina a string informativa que será exibida na barra de mensagens
   * do programa.
   * <li>Método chamado em DesenhoDinamico e em todos os painéis de botões</li><br>
   * @param string a String que  será exibida no rodapé
   */
  public void setMessage (String string) {
    labelMensagens.setText(string);
    }

  public void keyReleased (KeyEvent e) {
    }

  public void keyTyped (KeyEvent e) {}
  public void keyPressed (KeyEvent e) {}

  //    public void componentHidden(ComponentEvent e) {
  //        // TODO Auto-generated method stub
  //       
  //    }
  //   
  //    public void componentMoved(ComponentEvent e) {
  //        // TODO Auto-generated method stub
  //       
  //    }
  //   
  //    public void componentResized(ComponentEvent e) {
  //        int w = e.getComponent().getWidth();
  //        System.out.println("resized..............." + w);
  //        Constants.PANEL_WIDTH = w;
  //        repaint();
  //    }
  //   
  //    public void componentShown(ComponentEvent e) {
  //        // TODO Auto-generated method stub
  //       
  //    }

  }
