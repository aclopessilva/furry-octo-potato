/* 
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 * 
 * @author Leônidas de Oliveira Brandão
 * @version Present in 21/02/2009; First one in 23/08/2003
 *
 * @description To process images/files, based on original code of iGeom (http://www.matematica.br/igeom)
 * 
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of S<E3>o Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Le<F4>nidas O. Brand<E3>o.
 *
 */

package icomb.ui;

import java.applet.Applet; // 

import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Hashtable;

import icomb.IComb; // 23/05/2008
import icomb.LocalizacaoImagens;

public class TrataImagem { //

 public static final String str_imagens = "icomb/"; // endereço dos ícones (botões)

 private static String msgErroPC   = "[TI] 1"; //"[TrataImagem!pegaClasse]";
 private static String msgErroPCpI = "[TI] 2"; //"[TrataImagem!pegaImagem]";

 public static Frame frameImg; //
 public static MediaTracker mediaTracker; //
 public static Hashtable hash_img = new Hashtable(); //
 public static Class trataClasse; // ze

 // O diretório em que está o arquivo "LocalizacaoImagens.java" não pode mudar!!
 private static String ComponentImage = (new LocalizacaoImagens()).nome; // truque para usar classe para pegar diretório
 
 // pega a classe de nome "str_classe"
 public static Class pegaClasse (String str_classe) { //
   try {
       Class classe = Class.forName(str_classe);
       //System.out.println("pegaClasse: "+str_classe+" -> "+classe);
       return classe;
       //return Class.forName(str_classe);
   } catch (ClassNotFoundException classnotfoundexception) {
       System.err.println(msgErroPC+": classe nao encontrada "+str_classe);
       throw new NoClassDefFoundError(classnotfoundexception.getMessage());
       }
   }

  // Testa se imagem tem dimensao, senao => erro (devolve 'false')
  public static boolean verificaImage (Component comp, Image image) {
    int larg, alt;
    larg = image.getWidth(comp);
    alt  = image.getHeight(comp);
    if (larg<=0) larg=10;
    if (alt <=0) alt =10;
    java.awt.image.PixelGrabber grabber = new java.awt.image.PixelGrabber(image, 0,0, larg,alt, true);
    boolean bol = false;
    try { // IMPORTANTE para que o 'pegaImagemIGeom(String)' ao usar o 'Toolkit' devolve Image!=null mesmo no caso
          //            de não conseguir montar a Image.
      grabber.grabPixels(5); // só para esperar
    } catch (java.lang.InterruptedException ie) {}
    int [] pixels = (int[])grabber.getPixels();
    String str = "";
    if (pixels!=null) str = ""+pixels.length;
    if (pixels!=null) return true;
    return false;
    }



 // Chamado em: 
 public static Image pegaImagemIComb (String str_imagem) {
   Image image = null;

   // Permite receber URL
   java.net.URL imgURL = null;
   try {
      try { //
         imgURL = new java.net.URL(str_imagem); // se for URL
      } catch (java.net.MalformedURLException e) { } // System.out.println("[TrataImagem!pegaImagemIComb] ERRO! "+e);

      // System.out.println("[TrataImagem!pegaImagemIComb] str_imagem="+str_imagem+" imgURL="+imgURL);
      if (imgURL!=null) {
         // - cai aqui: aplicativo com URL
         // file:///home/leo/projetos/.../a.gif
         InputStream inputstream = imgURL.openStream();
         byte [] is = new byte[inputstream.available()];
         inputstream.read(is);
         image = Toolkit.getDefaultToolkit().createImage(is);
         //- System.out.println("[TI] Toolkit+inputstream: image="+image+" is="+is);
         inputstream.close();         
         return image;
         }
   } catch (java.lang.Exception e) {
         System.err.println(icomb.IComb.debugMsg(new TrataImagem()) + "Erro: ao tentar carregar uma imagem: inputstream: "+e);
         // e.printStackTrace(); 
         try {
           Applet app = new Applet();
           image = app.getImage(imgURL);
           //- System.out.println("[TI] igeomApplet.getImage: image="+image);
         } catch (java.lang.Exception e1) {
           System.err.println(icomb.IComb.debugMsg(new TrataImagem()) + "Erro: ao tentar carregar uma imagem via URL ("+imgURL+"): "+e1);
           e1.printStackTrace(); 
           }
         }

   try {
     // - cai aqui: applet com URL
     image = Toolkit.getDefaultToolkit().getImage(str_imagem);
   } catch (java.lang.Exception e) { // NullPointerException npe
     System.err.println(icomb.IComb.debugMsg(new TrataImagem()) + "Erro: em tratamento de imagens, para ler imagem "+str_imagem+" - "+image+"): "+e);
     // e.printStackTrace();
     }
   // System.out.println("[TrataImagem!pegaImagemIComb] image="+image);
   return image;
   } // static Image pegaImagemIComb(String str_imagem)


 public static String getClassPath (String strClass) { //
   Class classPath = pegaClasse(strClass);
   return classPath.getName();
   }


 private static int count=0; // show only the first image load error

 //D private static int countE=0; // show only the first image load error
 // Presents each image from the current universe (Universo)
 // From: (applet)      icomb.ui.ImagePanel.getElement(ImagePanel.java:72)
 // From: (application) base.MontaFace.criaPainelSuperior(MontaFace.java:140) - "icomb/img/icomb_header.gif" or "img/cabecalho-icomb_small.gif"
 public static Image pegaImagem (String str_imagem) { //
   Image image = null;
   boolean erro = false;
   //D if(countE==0){ System.out.println("src/icomb/ui/TrataImagem: pegaImagem("+str_imagem+")");
   //D {try { String str=""; System.err.println(str.charAt(3)); } catch (Exception e) {e.printStackTrace();}} countE++; }   
   try {

      //SECURITY: alwary get 'trataClass' in order to avoid an error of error of a method change it to a wrong class (must be always 'class icomb.LocalizacaoImagens'
      // do not use this: InputStream inputstream = (trataClasse != null ? trataClasse : (trataClasse = pegaClasse(ComponentImage))).getResourceAsStream(str_imagem);
      InputStream inputstream = (trataClasse = pegaClasse(ComponentImage)).getResourceAsStream(str_imagem);
      if (inputstream==null) {
        if (count++<5) {
          if (count==1) try { String str=""; System.out.println(str.charAt(3)); }catch(Exception e) {e.printStackTrace();}
          System.err.println("TrataImagem.pegaImagem: error: trataClasse='" + trataClasse + "': " + str_imagem + "");
	  // TrataImagem.pegaImagem: error: trataClasse='class icomb.LocalizacaoImagens': /home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/baralho/imagens/ad.gif
	  //Universo.java: setArquivo: directory=/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/, arquivo=/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/baralho/baralho.xml
          }
        return null;
        }

      byte [] is = new byte[inputstream.available()];
      inputstream.read(is);
      image = Toolkit.getDefaultToolkit().createImage(is);
      inputstream.close();

   } catch (Exception e) { // IOException ioexception
      if (count++<2) { // only the first error
         System.err.println(icomb.IComb.debugMsg(new TrataImagem()) + "Error: it was impossible to read image '" + str_imagem + "': "+e);
         System.err.println("src/icomb/ui/TrataImagem.java: pegaImagem(" + str_imagem + "): ComponentImage="+ComponentImage+", str_imagem="+str_imagem);
         erro = true;
         e.printStackTrace();
         }
      }
   //QUARENTINE: 08/08/2013 - after 1 or 2 months of iComb use with no error in this melhod, erase the this 11 lines bellow
   //q // Class cl = Class.forName("java.lang.Class");
   //q else if ( (IComb.isApplet() && erro) || !IComb.isApplet() ) { // 
   //q    try { // Chamado em: 'base/MontaFace.java: void criaPainelSuperior()'
   //q       image = (Toolkit.getDefaultToolkit().getImage((trataClasse != null ? trataClasse : (trataClasse = pegaClasse(ComponentImage))).getResource(str_imagem)));
   //q    } catch (Exception e) {  // java.lang.NullPointerException npe
   //q       System.err.println(icomb.IComb.debugMsg(new TrataImagem()) + "Erro: em tratamento de imagem, ao tentar ler imagem "+str_imagem+" - "+image+" via Toolkit: "+e);
   //q       System.err.println("     classes: "+trataClasse+", "+ComponentImage);
   //q       // e.printStackTrace();
   //q       return image;
   //q       }
   //q    }

   return image;

   } // static Image pegaImagem(String str_imagem)


 // Para leitura de arquivo via iComb
 private static Image trataImagemComoStream (String str_arquivo) {
   return trataImagemComoStream(str_arquivo,0);
   }

   
 // Para leitura de arquivo via applet
 // Também não consegui fazer funcionar no Netscape...
 private static Image trataImagemComoStream (String str_arquivo, int tipo) {
   Component component = null; // <-------------------- IGeomApplet.iGeomAppletStatic;
   InputStream is;
   ByteArrayOutputStream baos;
   Image img1 = null;
   if (tipo==0)
      str_arquivo = str_imagens + str_arquivo; // str_imagens = "igeom/img/";
   trataClasse = pegaClasse(ComponentImage);

   try {
     is = trataClasse.getClass().getResourceAsStream(str_arquivo); //

     baos = new ByteArrayOutputStream();

     int c;
     while ((c = is.read()) >= 0)
        baos.write(c);
     img1 = component.getToolkit().createImage(baos.toByteArray());

   } catch (Exception e) { // (IOException e)
     System.err.println(icomb.IComb.debugMsg(new TrataImagem()) + "Erro: em tratamento de imagem, ao tentar ler imagem via ByteArrayOutputStream: "+e);
     //e.printStackTrace();
     }

   return img1;
   }

 public static Image trataImagem (String str_imagem) { //
   Image image;
   try {
     // if (str_imagem.equals("icomb/baralho/imagens/ad.gif"))
     image = pegaImagem(str_imagem); // monta o hash com nomes das imagens
     return image;
   } catch (Exception e) { // java.lang.NullPointerException
     System.err.println(icomb.IComb.debugMsg(new TrataImagem()) + "Erro: em tratamento de imagem, ao tentar ler imagem, vazia, "+ str_imagem + ": "+e);
     return null;
   }
   }

 /*
 public static Image devolveImagem (String str_imagem) { //
   return trataImagem(nomeImagem(str_imagem));
   }
 public static String nomeImagem (String str_imagem) { // 
   String nomeImgStr = (str_imagem.substring(0, str_imagem.lastIndexOf("/") + 1) + "img-" + str_imagem.substring(str_imagem.lastIndexOf("/") + 1));
   return nomeImgStr;
   }
 */
   
 }
