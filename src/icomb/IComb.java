/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br / http://www.usp.br/line
 *
 * @author Alexandre Eisenmann, Leônidas de Oliveira Brandão
 * @version Version 0.8.9 in 08/08/2013 - all files in a single JAR (iComb.jar includes all universes)
 *          First stable version in 30/04/2008
 *
 * @description iComb is am iMath project to provides a tool to teach/learn counting:
 *  teacher: can create new exercises (authoring)
 *  student: can solve the exercise, with an integrated Inteligent Tutoring System
 *
 *  iComb exercise model is based in a decomposition of the problem, following the "Conbien?" scheme.
 *
 *  iComb can be integrated in any Learning Managment System, in particular we also
 *  provides a package to integrate iComb with Moodle (http://www.moodle.org): you
 *  must install the free software iAssign in your Moodle.
 *  Get iAssign under: http://www.matematica.br/iassign
 *
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb;

import icomb.events.ObjectManager;
import icomb.objects.Exercicio;
import icomb.objects.Universo;
import icomb.ui.SetMachinePanel;
import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.Crypto;
import icomb.util.I18n;
import icomb.util.ParserParametros;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Hashtable;

import base.MontaFace;

/**
 * Esta é classe principal do programa e está implementada na forma <b>superapplet</b>, ou seja, funciona tanto como applet quanto
 * como uma aplicação independente. 
 * Instancia todos os participantes e estabelece seus relacionamentos.
 */

public class IComb extends Applet {

  // Versao
  public static final String versao = Configuracoes.strVersao;
      // "0.8.9": // Released 07/08/2013: muitos acertos de interface, agora funciona tudo num so JAR
      // "0.8.4": // Released 07/07/2011: muitos acertos de interface, de formula apos estagio 2
      // "0.8.3": // Released 04/07/2011: pequeno acerto para aplicativo que tentava pegar imagens via 'getCodeBase' de applet
      // "0.8.1": // Released 30/05/2011: pequeno acerto para aplicativo que tentava pegar imagens via 'getCodeBase' de applet
      // "0.8 "; // Released 25/01/2010
      // "0.3 "; // Released 22/02/2009 (final da noite)
      // "0.2"; // 22/02/2009
      // "0.1"; // 20,21/02/2009
      // Apos alterar compilar tambem: javac -encoding iso-8859-1 icomb/IComb.java base/MontaFace.java

  public static final boolean DEBUG = true; //DEBUG: to print path in error messages
  public static String debugMsg (String msg) {
    if (!DEBUG || msg==null) return "";
    return msg + ": ";
    }
  public static String debugMsg (Object obj) {
    if (!DEBUG || obj==null) return "";
    String str = obj.getClass().getName();
    return str + ": ";
    }

  // Definidos em: icomb/util/Configuracoes.java
  // public static final intge LARG = 750, ALT = 688; 
  // public static final int ALT_LOGO = 32+40; // base/MontaFace: altura do painel com logo (barra superior)

  // "Universes" implemented
  public static final String
    UNIVERSE1 = "baralho/baralho.xml",
    UNIVERSE2 = "futebol/futebol.xml";

  public  static IComb getInstance () { return instanceIcomb; }
  private static IComb instanceIcomb = null;
  private static boolean notSend = false;
  public  static boolean notSend () { return notSend; }
  private static boolean isApplet = false;
  public  static boolean isApplet () { return isApplet; }
  private static int width = -1; // getParameter("width")
  public  static int getAppletWidth () {
    if (width>-1)
       return width;
    if (instanceIcomb!=null)
       return instanceIcomb.getLargura();
    return 0;
    } // src/base/MontaFace.java

  // Problema "default"
  String ArquivoUniverso = "baralho/baralho.xml";
  String dica="Um baralho de 32 cartas corresponde a um baralho comum sem as cartas cujos valores são menores do que Sete. Portanto, um baralho de 32 cartas 8 cartas de cada naipe (7,8,9,10,Valete, Dama, Rei e Ás)";
  String enumGabarito ="Com um baralho de 32 cartas, quantas mãos de 5 cartas é possível formar com exatamente 2 cartas de Copas e exatamente duas cartas de Espadas?";
  String respostaEnviada="";
  String solucao="2:suit={hearts};2:suit={spades};1:suit#{hearts,spades}";
  String resposta = "12544";

  // interactive Learning Modules (iLM): applet's parameter
  // MA_propositionURL="true" => MA_proposition has the URL's file; otherwise MA_proposition has an string with the file content
  String MA_propositionURL = "", MA_proposition = "";

  boolean corrige = true;
  boolean online = false; // se 'true' => nao mostrar resposta da avaliacao do exerc. para usuario

  SetMachinePanel set_m_panel;
  Panel centralPanel;
  private MontaFace montaFace;

  public static final Frame appletFrame = new Frame("iComb"); // to be accessed from 'icomb/ui/ConstructionPanel.java' - 'boolean criarExercicio()'

  private static final String LINE = "\n--------------------------------------------------------------------------------\n";
  private static String ICOMB = " .: iComb : " + I18n.getString("iComb") + " ("+versao+") :.\n    http://www.matematica.br/icomb\n";
  // .: iComb = Combinatória Interativa na Internet (0.9.2") :.
  //    http://www.matematica.br/icomb");
  //TODO: internacionalizar: falta possibilitar troca de idioma dinamicamente

  // CAUTION: running under application, if this variable fails then the universe images will NOT be loaded!
  private static boolean ISJAR = false;
  public static boolean getISJAR () { return ISJAR; }

  public static void main (String[] args) throws IOException {
    // iComb
    // System.out.println(LINE+ICOMB);
    // "\n * args={ "+icomb.util.GerenciadorRecursos.printVectorString(args) + " }"

    // CAUTION: running under application, if this variable fails then the universe images will NOT be loaded!
    // To register if is used JAR: path: jar:file:/home/leo/projetos/iMA/iMA0/icomb/novo/iComb.jar!/icomb/futebol/futebol.xml
    // Used by:
    // * icomb.util.GerenciadorRecursos.getImagem(MontaFace mf, String nomeCompletoImagem)
    // * icomb.utilParse.parseUniverso(String nomeDoArquivo)
    String strAux = icomb.util.GerenciadorRecursos.getClassPath("icomb/IComb.class"); //futebol/futebol.xml");
    if (strAux!=null)
      if (strAux.length()>4 && strAux.substring(0,4).equalsIgnoreCase("jar:"))
	ISJAR = true;
     //D System.out.println("\n * JAR? " + strAux + " -> " + ISJAR);
     //D * JAR? jar:file:/home/leo/projetos/iMA/iMA0/icomb/novo/iComb.jar!/icomb/IComb.class -> true

    try {

    IComb applet = new IComb(); // IComb extends Applet
    applet.setVisible(true);

    I18n.setConfig(args); // define lingua => tem prioridade sobre arquivo de lingua 'igeom.lang'
    I18n.defineBundle(true); // define 'Messages*.properties'
    // I18n.changeInstance("pt","BR");

    // Cria o frame em que este applet ira rodar
    // Frame appletFrame = new Frame("iComb");
    appletFrame.addWindowListener(new WindowAdapter () {
      public void windowClosing(WindowEvent e) { System.exit(0); }
      });

    // 
    MontaFace.setSize(appletFrame); // this.setSize(Constants.PANEL_WIDTH,Configuracoes.ALT);

    // Adicionando o applet no frame
    appletFrame.add(applet);

    // Get the e-Learning Module content
    // try to get file by comand line
    // See 'static String readFileAsString(String filePath)'
    java.io.File file = file = icomb.util.GerenciadorRecursos.getFileArg(args); // if there is an arg. with file name => get the file content
    String strContent = "";
    if (file!=null) {
      java.io.FileInputStream fin = new java.io.FileInputStream(file);
      strContent = icomb.util.GerenciadorRecursos.getStringFile(fin, icomb.util.GerenciadorRecursos.getFileName());
      // applet.getContentFromStr(strContent);
      // System.out.println("iComb: "+strContent);

      // icomb.ui.SetMachinePanel -> icomb.ui.ChooseExercisePanel 'icomb.ui.SetMachinePanel.setEnabledStartDUPanel()' => 'icomb.ui.SetMachinePanel: DefinedExercisePanel dePanel.setEnabled()'
      // applet.set_m_panel.getCePanel().loadText(file.getName(), strContent); // load statement in panel <- ERROR: 'DefinedExercisePanel dePanel.setEnabled()' vazio!

      if (strContent!=null && strContent!="") { // set the exercise data
         applet.getContentFromStr(strContent);
         ObjectManager.setMachine.setExerciseInitiated(); // icomb.machine.SetMachine.estado = icomb.machine.SetMachine.ESTADO_EXERCICIO_CONFIRMADO
         }
      }

    // Iniciando o applet
    applet.iniciaExercicio();
    applet.monta();
    if (!isApplet && strContent!=null && strContent!="") { // if applicative version, avoid the user pick up "another" file with exercise (that means, directly)
       // Applicative: Define universe (Universo)
       ObjectManager.setMachine.loadUniverso(applet.montaFace); // icomb.objects.Universo: só aqui define ''src/icomb/machine/SetMachine.java!universo'
       }
    appletFrame.pack();

    } catch (Exception e) {
      System.err.println("Error: iComb.main "+e);
      e.printStackTrace();
      }


    // O frame deverá aparecer na tela antes que o método init() do applet seja chamado
    appletFrame.setVisible(true);

    } // void main(String[] args)


  // Get the HTML content to iComb shows the exercise (if there is one)
  private void getContentFromStr (String strContent) {
    Hashtable hash = ParserParametros.parser(strContent);
    solucao = (String) hash.get("answer_set");
    if (solucao==null || solucao=="") solucao = (String) hash.get("solucao");

    resposta = (String) hash.get("answer_value");
    if (resposta==null || resposta=="")
       resposta = (String) hash.get("resposta");
    if (resposta.equals("@resposta@"))
      resposta = "-1";

    enumGabarito = (String) hash.get("EnumGabarito");
    respostaEnviada = (String) hash.get("RespostaEnviada");
    ArquivoUniverso = (String) hash.get("ArquivoUniverso");
    dica = (String) hash.get("dica");
    corrige = (String) hash.get("corrige") == null ? true : ((String) hash.get("corrige")).trim().equals("true");

    // 05/09/2011: online =  (String) hash.get("online") != null && ((String) hash.get("online")).equals("1");
    String strOnline = (String) hash.get("online");
    if (strOnline!=null) {
      if (strOnline.equals("@online@") || strOnline.equals("1")) // if '@online@' => JS falhou...
        online = true;
      else
        online = false;
      }
   else
      online = false;

    }

  public void init () {
     isApplet = true;
     notSend = true;
     this.iniciaParam();
     this.iniciaExercicio();
     this.monta(); //

     // Applet: Define universe (Universo)
     if (resposta!=null) // if empty exercise => do not try to define exercise datas!
        ObjectManager.setMachine.loadUniverso(this.montaFace); // icomb.objects.Universo: só aqui define 'src/icomb/machine/SetMachine.java!universo'

     this.start();
     }


  private void iniciaParam () {
    try {
      String strParamAux = getParameter("lang");
      String [] strLang = { "lang=" + strParamAux }; // define language: pt_BR, en_US, fr_FR (other?)
      if (strParamAux!=null) {
         I18n.setConfig(strLang); // define lingua => tem prioridade sobre arquivo de lingua 'igeom.lang'
         I18n.defineBundle(true); // define 'Messages*.properties'
         }
      else {
         I18n.setConfig(null);
         I18n.defineBundle(true);
         }
       
      String strWidth = getParameter("width");
      if (strWidth!=null) try {
         width = Integer.parseInt(strWidth);
      } catch (Exception e) {
         System.err.println("Error: parameter 'width' not integer? "+strWidth+" : "+e);
         }
      String MA_PARAM_notSEND = getParameter("MA_PARAM_notSEND");
      if (MA_PARAM_notSEND == null || MA_PARAM_notSEND.trim().equals("")) {
         notSend = false;
         }
      else
      if (MA_PARAM_notSEND.equalsIgnoreCase("true")) {
         notSend=true;
         } 
      else {
         notSend=false;
         }

      // ---
      // iLM protocol 2.0: start
      //
      // Parameters defined after 2012, Feb 02 (02/02/2012)
      //   IGraf.iLM_PARAM_AssignmentURL        = this.getParameter("iLM_PARAM_AssignmentURL");        // URL where the file content is
      //   IGraf.iLM_PARAM_authoring            = this.getParameter("iLM_PARAM_authoring");            // it allow create/edit exercise if, and only if, "true"
      //   IGraf.iLM_PARAM_Feedback             = this.getParameter("iLM_PARAM_Feedback");             // do not show feedback to the studend if, and only if, "false"
      //   IGraf.iLM_PARAM_ServerToGetAnswerURL = this.getParameter("iLM_PARAM_ServerToGetAnswerURL"); // Web Service to get the iLM answer (in LMS)
      MA_proposition = getParameter("iLM_PARAM_AssignmentURL");

      if (MA_proposition!=null && MA_proposition.length()>2) {
         // iLM 2.0: it is protocol 2.0
         // read from remote host
         MA_proposition = lerDeURL(this, MA_proposition);
         }
      else {
         // iLM 1.0: try the previous protocol
         MA_proposition = getParameter("MA_PARAM_Proposition"); // iLM 1.0: try protocol 1.0
         if (MA_proposition==null || MA_proposition=="") {
            MA_proposition = getParameter("MA_PARAM_proposition");
            if (MA_proposition==null || MA_proposition=="") {
               MA_proposition = getParameter("MA_proposition");
               }
            }
         // MA_propositionURL="true" => MA_proposition has the URL's file; otherwise MA_proposition has an string with the file content
         MA_propositionURL = getParameter("MA_PARAM_PropositionURL");

         if (MA_propositionURL!=null && MA_propositionURL.equalsIgnoreCase("true")) {
            // read from remote host
            MA_proposition = lerDeURL(this, MA_proposition);
            //T  Create a Dialog to test: in applet mode it can show the content file for check its consistence
            //T icomb.util.Util.createDialog("iLM: test", new Frame("iLM: test"), MA_proposition);
            }
         // else System.out.println("iComb: 2 - "+MA_proposition);

         // Very old protocol: each item of iComb exercise is separated
         if (MA_proposition==null || MA_proposition=="") {
           solucao = getParameter("answer_set");
           if (solucao==null || solucao=="") solucao = getParameter("solucao");
           resposta = getParameter("answer_value");
           if (resposta==null || resposta=="") resposta = getParameter("resposta");
           enumGabarito = getParameter("EnumGabarito");
           respostaEnviada = getParameter("RespostaEnviada");
           ArquivoUniverso = getParameter("ArquivoUniverso");
           dica = getParameter("dica");
           corrige = getParameter("corrige") == null ? true : getParameter("corrige").trim().equals("true"); 
           online =  getParameter("online") != null && getParameter("online")=="1" ; 
           }
         else {
           // pega do HTML o conteudo para o iComb mostrar
           getContentFromStr(MA_proposition);
           }

         }


      // System.out.println("sol: " + solucao);
      if (isApplet) {
         if (solucao==null) {
            System.err.println("applet: empty file!");
            return;
            }
         System.out.println("Applet: model answer: " + solucao);
         solucao = Crypto.hexToString(solucao);
         }
      System.out.println("model answer: " + solucao);

      }
    catch (Exception e) { // considerar valores default
      System.err.println("Erro: IComb: "+e);
      e.printStackTrace();
      }
    }


  //ler arquivo de URL
  public static String lerDeURL (java.applet.Applet applet, String strURL) {
   // Permite receber URL
   java.io.InputStream inputStream = null;
   java.net.URL endURL = null;
   try { //
     endURL = new java.net.URL(strURL); // se for URL
   } catch (java.net.MalformedURLException e) {
     try { // se falhou, tente completar com endereço base do applet e completar com nome de arquivo
       // applet.getDocumentBase().toString() = "http://localhost/igeom/exemplo.html"
       endURL = new java.net.URL(applet.getCodeBase().toString()+"/"+strURL); // se for URL
       // System.out.println("[Sistema.lerDeURL: "+strURL+" - "+endURL);
     } catch (java.net.MalformedURLException ue) {
       System.err.println("Erro: leitura URL: applet="+applet+", "+strURL+" nao e URL: "+ue);
       return "";
       }
     }
   try {
     inputStream = endURL.openStream();
     return icomb.util.GerenciadorRecursos.getStringFile(inputStream, strURL);
   } catch (java.io.IOException ioe) {
     System.out.println("Erro: leitura URL: "+strURL+": "+ioe.toString());
   } catch (java.lang.Exception e) {
     System.out.println("Erro: leitura URL: "+strURL+": "+e.toString());
      }
   return "";
   }


  private void iniciaExercicio () {
    if (isApplet() || ObjectManager.setMachine.isExerciseInitiated()) {
       Exercicio ex = new Exercicio(50,enumGabarito,enumGabarito); // icomb/objects

//SetMachine.java:  public void setArquivoUniverso (String directory, String arquivo) 

       // Se resposta foi enviada, mostrar resultado ao invés do todo 
       if (respostaEnviada != null && !respostaEnviada.trim().equals("")) {
          ObjectManager.setMachine.setExercicio(ex);
          ObjectManager.setMachine.defineResposta(respostaEnviada);
          ObjectManager.setMachine.setOnline(true);
          }
       else {
          if (solucao==null || solucao=="" || resposta==null || resposta=="") {
            // System.out.println("icomb/IComb.java: solucao=" + solucao + ", resposta=" + resposta);
            return;
            }
          ex.setSolucao(Universo.interpretaCondicao(solucao)); // icomb/objects/Universo: Condicao[] interpretaCondicao(String strCond)
          ex.setResposta(Long.parseLong(resposta));
          ObjectManager.setMachine.setCorrige(corrige);
          ObjectManager.setMachine.setExercicio(ex);

          //r ObjectManager.setMachine.setArquivoUniverso("icomb/", ArquivoUniverso);
          String strPath = icomb.util.GerenciadorRecursos.getPath(this);
          ObjectManager.setMachine.setArquivoUniverso(strPath, ArquivoUniverso);
          //T
System.out.println("\nicomb/IComb.iniciaExercicio(): iniciaExercicio(): "+strPath+": "+ArquivoUniverso + " solucao="+solucao+", resposta="+resposta);

          ObjectManager.setMachine.defineDica(dica);
          ObjectManager.setMachine.setOnline(online);
          }
       }
    else {
       ObjectManager.setMachine.iniciar();
       }
    } // void iniciaExercicio()


  // Monta face iComb
  private void monta () {
    // I18n.changeInstance("en","US");
    // I18n.changeInstance("pt","BR");
    ICOMB = " .: iComb : " + I18n.getString("iComb") + " ("+versao+") :.\n    http://www.matematica.br/icomb\n";
    System.out.println(LINE+ICOMB);
    //" .: iComb : Combinatória Interativa na Internet ("+versao+") :.\n    http://www.matematica.br/icomb");

    // Monta paineis no Frame
    MontaFace face = new MontaFace(this,false); // base.MontaFace
    face.completaMontaFace(this,false);

    //? ObjectManager.setMachine.confirmaExercicio(); // em 'icomb/machine/SetMachine.java' faz 'estado=ESTADO_EXERCICIO_CONFIRMADO;'
    //? ObjectManager.setMachine.loadUniverso(montaFace); // icomb.objects.Universo: só aqui define ''src/icomb/machine/SetMachine.java!universo'

    if (!isApplet) // if applicative version, get the dimensions 'Constants.PANEL_WIDTH,Configuracoes.ALT'
       face.setSize();

    set_m_panel = new SetMachinePanel(face); // MontaFace extends Panel: usa Universo via 'ObjectManager.setMachine.getUniverso()'
    setLayout(new BorderLayout());

    insereWindowListener();

    centralPanel = new Panel(new BorderLayout()) { // PANEL_WIDTH = 650
        public Dimension getPreferredSize() { return new Dimension(Constants.PANEL_WIDTH, Configuracoes.ALT); }
        };

    centralPanel.add(set_m_panel);

    face.addCentral(centralPanel);

    this.add(face);

    montaFace = face;

System.out.println("\nicomb/IComb.monta(): solucao="+solucao+", resposta="+resposta);
    if (solucao!=null && resposta==null) { // when iComb is started with no exercise => 'solucao' and 'resposta' are 'null' => must avoid lines bellow
//??    if (solucao!=null && resposta!=null) { // when iComb is started with no exercise => 'solucao' and 'resposta' are 'null' => must avoid lines bellow
       // System.out.println("icomb/IComb.java: solucao=" + solucao + ", resposta=" + resposta);
       ObjectManager.setMachine.loadUniverso(montaFace); // icomb.objects.Universo: só aqui define ''src/icomb/machine/SetMachine.java!universo'
       ObjectManager.setMachine.confirmaExercicio(); // em 'icomb/machine/SetMachine.java' faz 'estado=ESTADO_EXERCICIO_CONFIRMADO;'
System.out.println("icomb/IComb.monta(): ObjectManager.setMachine.loadUniverso ------------");
       }
else System.out.println("icomb/IComb.monta(): NAO carregou ObjectManager.setMachine.loadUniverso ------------");


    if (!isApplet) // if applicative version, let the user pick up the file with exercise
       return;

    // System.out.println("IComb.java: ArquivoUniverso="+ArquivoUniverso);
    this.setSize(Configuracoes.lTFP+1,Configuracoes.aTFP+1); // see 'base/MontaFace.java: void setSize()'
    this.setVisible(true);

    } // void monta()


  public IComb () {
    // this(true);
    instanceIcomb = this;
    }

  //Leo public IComb (boolean isApplet) { this.isApplet = isApplet; }


  private void insereWindowListener () {
    //  addWindowListener(new WindowAdapter () { public void windowClosing(WindowEvent e) { fechaJanela(); } });
    }

  private void fechaJanela () {
    setVisible(false);
    }

  public void abreJanela () {
    setVisible(true);
    }

  /**
   * Resultado da Avaliação do Exercício 
   *
   * @return int 0=>Errado 1=>Certo
   */
  public int getAvaliacao () {
    return ObjectManager.setMachine.getAvaliacao();
    }

  public int getLargura () {
    // return Constants.PANEL_WIDTH;
    return Configuracoes.lTFP;
    }
 
  /**
   * Resultado da Avaliação do Exercício 
   *
   * @return int 0=>Errado 1=>Certo
   */
  public int getEvaluation () {
    return ObjectManager.setMachine.getAvaliacao();
    }

  /**
   * Retorna a resposta do aluno
   * @return
   */
  public String getAnswer () {
    return ObjectManager.setMachine.getAnswer();
    }

  /**
   * Retorna o trace do aluno
   * @return
   */
  public String getTrace () {
    return ObjectManager.getInstance().tracePilhaDeAcoes();
    }


  }
