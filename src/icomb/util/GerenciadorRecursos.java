/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br / http://www.usp.br/line
 *
 * @author Alexandre Eisenmann, Leônidas de Oliveira Brandão
 *
 * @version
 *
 * @description Para gerar tela de abertura do iComb
 *
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.util;

import icomb.IComb;
import base.MontaFace;
import icomb.LocalizacaoImagens; //?
import icomb.ui.TrataImagem;

import java.lang.Class;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class GerenciadorRecursos {


 public static String printVectorString (String vetStr[]) {
   if (vetStr==null) return "";
   String str = "", aux = "";
   int count = vetStr.length;
   for (int i_=0; i_<count; i_++) {
      str += aux + vetStr[i_];
      aux = ", ";
      }
   return str;
   }


 // ----------------------------------------
 // Read text file from JAR
 public static String getContentTextJAR (java.applet.Applet applet, String str_arquivo) {
   // Component component = null;
   InputStream is;
   java.io.ByteArrayOutputStream baos;
   String strTxt = null;
   try {
     Class classe = applet.getClass(); // Class.forName(str_classe);

     // Adjust name: avoid "/" at the begning
     if (str_arquivo!=null && str_arquivo.charAt(0)=='/')
        str_arquivo = str_arquivo.substring(1,str_arquivo.length());

     is = icomb.IComb.class.getResourceAsStream(str_arquivo); // "baralho/baralho.xml"

     // System.out.println("GerenciadorRecursos.getContentTextJAR: file="+str_arquivo+", is="+is+"");
     baos = new java.io.ByteArrayOutputStream();	     
     int c;
     while ((c = is.read()) >= 0)
        baos.write(c);
     strTxt = baos.toString(); // component.getToolkit().createImage(baos.toByteArray());
   } catch (Exception e) {
     System.err.println("GerenciadorRecursos.getContentTextJAR: "+str_arquivo+": "+e);
     e.printStackTrace();
     }
   //DEBUG
   if (icomb.IComb.DEBUG) // debugMsg
     System.out.println("GerenciadorRecursos.getContentTextJAR: conteudo: ---\n"+(strTxt!=null&&strTxt.length()>50?strTxt.substring(0,50):strTxt)+"\n---");
   return strTxt;
   } // public static String getContentTextJAR(java.applet.Applet applet, String str_arquivo)


 // ----------------------------------------
 // Read Image file from JAR
 // From: public static Image getImagem(MontaFace mf, String nomeCompletoImagem)
 //TODO ??? Parece ser fonte do erro de repintar DrawingPanel o tempo todo!!!! ???
 public static Image getImageFromJAR (java.applet.Applet applet, String strImageName) {
   // if (1==1) return null;
    java.awt.Component component = icomb.IComb.getInstance();
   InputStream is;
   java.io.ByteArrayOutputStream baos;
   Image image = null; //String strTxt = null;
   try {
     Class classe = applet.getClass(); // Class.forName(str_classe);

     // Adjust name: avoid "/" at the begning
     if (strImageName!=null && strImageName.charAt(0)=='/')
        strImageName = strImageName.substring(1,strImageName.length());

     is = icomb.IComb.class.getResourceAsStream(strImageName); // "baralho/baralho.xml"

     //Versao 1
     byte [] isvet = new byte[is.available()];
     is.read(isvet);
     image = Toolkit.getDefaultToolkit().createImage(isvet);
     is.close();

   } catch (Exception e) {
     if (countError++==0) {
        System.err.println("GerenciadorRecursos.getImageFromJAR: "+strImageName+": (just the first error) "+e);
        e.printStackTrace();
        }
     }
   return image;
   } // public static Image getImageFromJAR(java.applet.Applet applet, String strImageName)


 // ----------------------------------------
 // Read text file from URL
 public static String getContentTextURL (java.applet.Applet applet, String strURL) {
   // Permite receber URL
   java.io.InputStream inputStream = null;
   java.net.URL endURL = null;

   java.lang.StringBuffer stringbuffer = null;
   try { //
     endURL = new java.net.URL(strURL); // se for URL
     // System.out.println("[Sistema.readFromURL: 1 "+strURL+" -> "+endURL+" -> "+endURL);
   } catch (java.net.MalformedURLException e) {
     try { // se falhou, tente completar com endereço base do applet e completar com nome de arquivo

       endURL = new java.net.URL(applet.getCodeBase().toString()+strURL); // se for URL

       // If the file was under 'igraf' directory, it could be used:
       // endURL = igraf.IGraf.class.getResource(strURL); // se for URL

     } catch (java.net.MalformedURLException ue) {
       System.err.println("[GravadorLeitor.readFromURL: failed while reading '"+strURL+"' - it also results in URL="+endURL);
       // ue.printStackTrace();
       return "";
       } 
     } 
   try {
     inputStream = endURL.openStream();
     java.io.InputStreamReader inputstreamreader = new java.io.InputStreamReader(inputStream);
     java.io.StringWriter stringwriter = new java.io.StringWriter();
     int i = 8192;
     char[] cs = new char[i];
     try {
       for (;;) {
           int i_11_ = inputstreamreader.read(cs, 0, i);
           if (i_11_ == -1)
              break;
           stringwriter.write(cs, 0, i_11_);
           }
       stringwriter.close();
       inputStream.close();
     } catch (java.io.IOException ioexception) {
       System.err.println("Erro: leitura URL: "+strURL+": "+ioexception); //throw Trace.error(34, ioexception.getMessage());
       // ioexception.printStackTrace();
       }
     return stringwriter.toString();
   } catch (java.io.IOException ioe) {
     System.out.println("Erro: leitura URL: "+strURL+" -> "+endURL+": "+ioe.toString());
     // ioe.printStackTrace();
     }
   return "";

   } // public static String getContentTextURL(String strName)


  // From: src/icomb/ui/ImagePanel.java: getElement(Universo universo, Element element)
  //       src/icomb/ui/UniversoFrame.java:
  //       + load(): img_aux = imgPanel.getElement(universo, element); // ImagePanel imgPanel
  //       + imgPanel.setElement(universo, element, imgPanel.getNome(), img_aux);
  public static Image getImagemApplication (String nomeCompletoImagem) {
    Image image = null;
    try {
      return icomb.ui.TrataImagem.pegaImagem(nomeCompletoImagem);
      // return Toolkit.getDefaultToolkit().getImage(classUniverse.getResource(nomeCompletoImagem));
      // It is iComb application
      // - baralho/LocalizacaoImagensBaralho.java: baralho/baralho.xml
      // - futebol/LocalizacaoImagensFutebol.java: futebol/futebol.xml
      // src/futebol/LocalizacaoImagensFutebol.java
    } catch (Exception e1) {
      String strAux = "";//classUniverse==null ? "" : classUniverse.getClass().getName();
      // System.err.println("Error: in GR.getImagemApplication: failed while geting image " + strAux + "/" + nomeCompletoImagem);// + ": "+e1);
      return null;
      }
    }


  // From: icomb/ui/ImagePanel.java: void setElement(Universo universo, Element element, String nome, Image img): imgItemUniverso = GerenciadorRecursos.getImagem(mf, nameOfImage);
  private static int countError = 0;
  public static Image getImagem (MontaFace mf, String nomeCompletoImagem) {
    Image image = null;
    if (mf==null)
      System.err.println(IComb.debugMsg("icomb/util/GerenciadorRecursos.java")+"Error: MontaFace mf empty: " + mf);

    if (countError++==0)
       System.err.println("\n\n------------------------------------------------------\n"+
                          IComb.debugMsg("icomb/util/GerenciadorRecursos.java: getImagem(...)")+"Error: MontaFace mf empty: " + mf);

    //_ 2014/05/30 - tentei pegar esta versao iGeom mas resulta num 'loop' de atualizacao das instancias na area abaixo (em 'DrawingPanl')
    //_ try {
    //_   // It is 'applet' or 'application'
    //_   // if 'applet' you could use 'mf.getApplet().getImage(mf.getApplet().getCodeBase(), nomeCompletoImagem)'
    //_   return GerenciadorRecursos.getImagemApplication(nomeCompletoImagem);
    //_ } catch (Exception e) {
    //_   System.err.println("Error: in GR, failed in applet while geting image "+nomeCompletoImagem+": "+e);
    //_   System.err.println("src/icomb/util/GerenciadorRecursos.java: " + nomeCompletoImagem);
    //_   e.printStackTrace();
    //_   return null;
    //_   }
    //TODO: 07/08/2013

    if (mf.getApplet()!=null && IComb.isApplet()) try {
      // It is 'applet'
      return GerenciadorRecursos.getImageFromJAR(mf.getApplet(), nomeCompletoImagem);

    } catch (Exception e) {
      if (countError++==0)
        System.err.println("GerenciadorRecursos.java: getImagem(...): Error: failed in applet while geting image <"+nomeCompletoImagem+">: "+e);
      return null;
      }
    else try {
      // It is 'application'
      // return icomb.ui.TrataImagem.pegaImagem(nomeCompletoImagem); // <- isso esta gerando 'loop' nas instancias em 'DrawingPanel'!!!
      // System.out.println("GerenciadorRecursos.getImagem: " + nomeCompletoImagem);

      //D System.out.println("GerenciadorRecursos.getImagem: "+IComb.getISJAR()+", "+nomeCompletoImagem);
      if (IComb.getISJAR()) // if application running with 'IComb.jar'
        return GerenciadorRecursos.getImageFromJAR(mf.getApplet(), nomeCompletoImagem);
      else // if application running under command line 'java -cp bin icomb.IComb'
        return Toolkit.getDefaultToolkit().getImage(nomeCompletoImagem);

    } catch (Exception e1) {
      System.err.println("Error: in GR, failed in while geting image "+nomeCompletoImagem+": "+e1);
      return null;
      }

    } // public static Image getImagem(MontaFace mf, String nomeCompletoImagem)


  // Important to define path to all images (to show universe or each model instances)
  // From: icomb.ui.DefinedUniversePanel.itemStateChanged(DefinedUniversePanel.java:149)
  // From: icomb.IComb.iniciaExercicio(): when <applet> or <application with CMB file parameter>
  public static String getPath (Object obj) {
    String strURLpath = icomb.IComb.class.getResource(".") + ""; // if 'getResource' returns 'null' => "null"
    String strAux = TrataImagem.getClassPath(obj.getClass().getName());
    //GerenciadorRecursos.getPath: strURLpath=file:/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/, strAux=icomb.ui.DefinedUniversePanel
    //try{String str=""; System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}

    // If applet 'strURLpath==null'
    if (strURLpath==null || strURLpath.equals("null")) { // IComb.isApplet()
       //D strAux = TrataImagem.getClassPath(obj.getClass().getName());
       strURLpath = "/";//strURLpath=""; //TrataImagem.getClassPath((IComb)obj);
       return strURLpath;
       } // if (strURLpath==null || strURLpath.equals("null"))
    //D else System.out.println("\nGerenciadorRecursos.getPath(Object): NAO obj="+obj+", strURLpath="+strURLpath);

    //Q 27/08/2014: quarentena
    // Avoid 'file:' like in 'file:/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/'
    if (strURLpath.substring(0,5).equalsIgnoreCase("file:")) //Q
      strURLpath = strURLpath.substring(5,strURLpath.length());

    System.out.println("GerenciadorRecursos.getPath(Object): "+strURLpath);

    return strURLpath;
    }


  // Get path: usefull in application with 'java -cp bin icom.IComb' to get path '/home/leo/.../bin/'
  public static String getPath () {
    String strURLpath = icomb.IComb.class.getResource(".") + ""; // if 'getResource' returns 'null' => "null"
    //D System.out.println("\nGerenciadorRecursos.getPath(): strURLpath="+strURLpath);
    if (strURLpath==null || strURLpath.length()<5) { // it is null
       System.err.println("src/icomb/util/GerenciadorRecursos.java: error in 'getPath()': " + strURLpath);
       return "";
       }
    return strURLpath.substring(5, strURLpath.length());
    }


  // Get the XML descriptor of the universe: "baralho/baralho.xml" or "futebol/futebol.xml"
  // src/icomb/IComb.java: void iniciaExercicio(): String strPath = icomb.util.GerenciadorRecursos.getPath(this); ObjectManager.setMachine.setArquivoUniverso(strPath, ArquivoUniverso);
  // From: icomb.machine.SetMachine.loadUniverso(SetMachine.java:553) - command line loading file CMB
  public static InputStream getInputStream (MontaFace mf, String nomeCompletoArquivo) throws FileNotFoundException {
    Image image = null;
    File locationFile = null;
    //D System.out.println("\n[---------------------------------]\nGerenciadorRecursos.getInputStream: nomeCompletoArquivo="+nomeCompletoArquivo);
    if (mf.getApplet()!=null && IComb.isApplet())
      try {
        //D System.out.println("\nGerenciadorRecursos.getInputStream: applet="+mf.getApplet().getClass()+": load file="+nomeCompletoArquivo);

        // Adjust name: avoid "/" at the begning: '/baralho/baralho.xml' in 'baralho/baralho.xml'
        if (nomeCompletoArquivo!=null && nomeCompletoArquivo.charAt(0)=='/')
           nomeCompletoArquivo = nomeCompletoArquivo.substring(1,nomeCompletoArquivo.length());

        // It is an applet
        InputStream is =  mf.getApplet().getClass().getResourceAsStream(nomeCompletoArquivo);
        //D System.out.println("GerenciadorRecursos.getInputStream: (applet) is="+is);
        //icomb.ui.TrataImagem.pegaImagem(nomeCompletoImagem);

        if (is == null) {
          System.err.println("GerenciadorRecursos.getInputStream: Error: applet="+mf.getApplet().getClass()+", is="+is+": load file="+nomeCompletoArquivo);
          }

        return is;
      } catch (Exception e) {
        System.err.println("Error: in GR, failed in applet while opening image "+nomeCompletoArquivo+": "+e);
        e.printStackTrace();
        return null;
        }
    else // if (mf.getApplet()!=null && IComb.isApplet())
      try {
        //T System.out.println("\nGerenciadorRecursos.getInputStream: application: load file="+nomeCompletoArquivo);
        // It is an application
        //R1 nomeCompletoArquivo = clearFileFromName(nomeCompletoArquivo);
	//Q quarente: 27/08/2014 - apos testes eliminar os '//R11'
        //Q entra aqui qdo aplicativo linha comando carregando arquivo CMB

        //D System.out.println("GerenciadorRecursos.getInputStream: nomeCompletoArquivo="+nomeCompletoArquivo);

        // When 'java -cp bin icomb.IComb': it is necessary the prefix "icomb"
        // When 'java -cp bin icomb.IComb exerc_fut_1.cmb': it is not necessary
        int count = countSlashesString(nomeCompletoArquivo);

	//TODO: is necessary this counting solution?
	if (count<3) { // /home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/futebol/futebol.xml
	  //D System.out.print("GerenciadorRecursos.getInputStream: TROCOU! count="+count+": nomeCompletoArquivo: "+nomeCompletoArquivo+" -> ");
	  nomeCompletoArquivo = "icomb" + nomeCompletoArquivo; //Q
	  //D System.out.println(nomeCompletoArquivo+" ********************** ");
	  }
	else {
	  if (count>2) { // replace '/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/futebol/futebol.xml' by 'icomb/futebol/futebol.xml'
	    int index = nomeCompletoArquivo.lastIndexOf("icomb/");
	    nomeCompletoArquivo = nomeCompletoArquivo.substring(index, nomeCompletoArquivo.length());
	    }
	 //D System.out.println("GerenciadorRecursos.getInputStream: NAO trocou: nomeCompletoArquivo: "+nomeCompletoArquivo);
	 }

	// To work as application must have: nomeCompletoArquivo0 = "icomb/futebol/futebol.xml";
	InputStream is =  IComb.class.getClassLoader().getResourceAsStream(nomeCompletoArquivo);
	System.out.println("GerenciadorRecursos.getInputStream: *** is="+is + " ("+nomeCompletoArquivo+")");

	return is;

       } catch (Exception e) {
        System.err.println("Error: in GR, failed while opening file "+nomeCompletoArquivo+": "+e);
        e.printStackTrace();
        return null;
        }
    } // public static InputStream getInputStream(MontaFace mf, String nomeCompletoArquivo) throws FileNotFoundException



  // Clear 'file:/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/baralho/baralho.xml' to '/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/baralho/baralho.xml'
  public static String clearFileFromName (String fileName) {
    if (fileName==null) return null;
    int count = countSlashesString(fileName);
    if (count==2) {
      fileName = getClassPath("icomb"+fileName); //user.home
      // System.out.println("GerenciadorRecursos.clearFileFromName: getProperty="+System.getProperty("user.dir")+", "+System.getProperty("user.home")+", "+System.getProperty("java.class.path"));
      System.out.println("GerenciadorRecursos.clearFileFromName: return '"+fileName+"'");
      return fileName;
      }
    // it must be in (e.g.) './bin/icomb/futebol/futebol.xml'
    int index = fileName.indexOf("/"); // find first '/' // fileName.lastIndexOf("/"); // find first '/'
    //D System.out.println("GerenciadorRecursos.clearFileFromName: fileName="+fileName+", index="+index+": "+fileName.substring(index, fileName.length()));
    return fileName.substring(index, fileName.length());
    }

  // Usage: getClassPath("icomb/futebol/futebol.xml")
  public static String getClassPath (String strName) {
    ClassLoader loader = IComb.class.getClassLoader();//+", "+loader
    //D System.out.println("GerenciadorRecursos.getClassPath: "+strName);
    String strAux = "";
    try {
      strAux = loader.getResource(strName).toString();
    } catch (Exception e) {
      System.err.println(IComb.debugMsg("icomb/util/GerenciadorRecursos.java: getClassPath(String)")+"Error: can not use 'loader.getResource' with '"+strName+"': " + e.toString());
      }
    //D System.out.println("GerenciadorRecursos.getClassPath: "+strName+": "+strAux);
    return strAux;
    }
   

  public static int countSlashesString (String nome) {
    String sep, token = "";
    int count = 0;
    boolean lista = false;
    sep = "/";
    if (lista) System.out.println("GerenciadorRecursos.countSlashesString(String nome) nome="+nome+", sep=<"+sep+">");
    java.util.StringTokenizer st = new java.util.StringTokenizer(nome, sep);
    while (st.hasMoreTokens()) {
      if (lista) System.out.println(" "+token);
      token = st.nextToken();
      count++;
      }
    return count;
    }

  public static String clearFileName (String nome) {
    String sep, token = "";
    boolean lista = false;
    sep = "/";
    if (lista) System.out.println("GerenciadorRecursos.clearFileName(String nome) nome="+nome+", sep=<"+sep+">");
    java.util.StringTokenizer st = new java.util.StringTokenizer(nome, sep);
    while (st.hasMoreTokens()) {
      if (lista) System.out.println(" "+token);
      token = st.nextToken();
      }
    return token;
    }

  // Verify if "name" describe a valid file, readable
  public static boolean isFile (String name) {
    File arq;
    try {
      arq = new File(name);
      return arq.isFile();
    } catch (Exception e) {	 
      return false;                                                                                                                
      }
    }

  private static String fileName = "";
  public static String getFileName () { return fileName; }

  // Try to read a file by comand line arguments
  // To be used by:
  public static java.io.File getFileArg (String [] args) {
    File fileArg = null;

    String diretorio = "", str_temp, name_arq = "", nameStr = "";
    int ind = -1;

    // Verifica se um dos parâmetros é nome de arquivo: devolve o primeiro
    for (int i=0; i<args.length; i++) {
       str_temp = args[i];
       if (isFile(str_temp)) {
          System.out.println("Open file...: "+str_temp); // "Abrir arquivo..." + " é arquivo válido");
          name_arq = str_temp;
          ind = i;
          break;
          }
       else {
          // System.out.println(i+": "+str_temp+" NÃO é arquivo");
          }
       }

    if (ind>-1) {
       fileArg = new File(name_arq);
       if (!fileArg.isAbsolute()) {
          fileArg = new File(".",name_arq);
          diretorio = fileArg.getAbsolutePath();
          }
       if (!fileArg.isFile()) {
          fileArg = new File(name_arq + ".cmb");
          if (!fileArg.isAbsolute()) {
             diretorio = fileArg.getAbsolutePath();
             fileArg = new File(".",name_arq);
             }
          }
       // nameStr = fileArg.getName();
       fileName = clearFileName(diretorio+"/"+fileArg.getName()); // file to be load...
       }
    // System.out.println(" nameStr="+nameStr+" fileArg="+fileArg);
    // try{String str=""; System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
    return fileArg;
    } // static java.io.File getFileArg(String [] args)

  // Get String form 'java.io.InputStream'
  // icomb.IComb.lerDeURL(...)
  public static String getStringFile (java.io.InputStream inputStream, String strURL) {
    java.io.InputStreamReader inputstreamreader = new java.io.InputStreamReader(inputStream);
    java.io.StringWriter stringwriter = new java.io.StringWriter();
    int numbytes = 8192;
    char[] cs = new char[numbytes];
    try {
      for (;;) {
         int j = inputstreamreader.read(cs, 0, numbytes);
         if (j == -1)
            break;
         stringwriter.write(cs, 0, j);
         }
      stringwriter.close();
      inputStream.close();
    } catch (java.io.IOException ioexception) {
      System.err.println("Error: reading in 'GerenciadorRecursos': "+strURL+": "+ioexception); //throw Trace.error(34, ioexception.getMessage());
      }
    return stringwriter.toString();
    }
 

  } // public class GerenciadorRecursos
