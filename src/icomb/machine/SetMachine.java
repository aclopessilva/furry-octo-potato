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
 * @description
 *
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of S<E3>o Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Le<F4>nidas O. Brand<E3>o.
 *
 */

package icomb.machine;

import icomb.erro.Avaliador;
import icomb.events.ObjectManager;
import icomb.formula.Formula;
import icomb.objects.Condicao;
import icomb.objects.Estagio;
import icomb.objects.Exercicio;
import icomb.objects.Universo;
import icomb.ui.MessageFrame;
import icomb.util.Constants;
import icomb.util.GerenciadorRecursos;
import icomb.util.I18n;
import icomb.util.Parse;

import java.util.Hashtable;
import java.util.Vector;

import base.MontaFace;

public class SetMachine {

  private static int contadorStatic = 0; // auxliar
  public  int contador;

  public static int ESTADO_INICIAL = 0;
  public static int ESTADO_ESCOLHENDO_EXERCICIO = 1;
  public static int ESTADO_EXERCICIO_CONFIRMADO = 2;
  public static int ESTADO_CRIANDO_EXERCICIO = -1;
  public static int ESTADO_CONSTRUCAO_INICIADA = 3;
  public static int ESTADO_EDITANDO_ESTAGIO = 4;
  public static int ESTADO_EDITANDO_FORMULA = 5;
  public static int ESTADO_FORMULA_DEFINIDA = 6;
  public static int ESTADO_OPERACAO_DEFINIDA = 7;
  public static int ESTADO_VALIDA_CONSTRUCAO = 8;
  public static int ESTADO_CONSTRUCAO_FINALIZADA = 9;

  private boolean modoCriacao = false; // is it a creation of an exercise?

  private int estado;
  private Exercicio exercicioAtual;
  private int nElementos;

  private Estagio estagioAtual;
  private Estagio estagioAuxiliar;

  private Formula formulaAtual;
  private Vector estagios; // stages already defined
  private String operacao;

  private String directory; // must be 'icomb/'
  private String arquivoUniverso; // must be 'baralho/baralho.xml' or 'futebol/futebol.xml' - will define 'Universo: String arquivo'
  private String dica; // a hint to help student to solve problem

  private boolean corrige;
  private Universo universo;
  private Avaliador avaliador;

  private boolean online;

  private String respostaEnviada;

  // Chamado em: icomb.IComb.iniciaExercicio(...)
  public void setOnline (boolean online) {
    // try{String str=""; System.out.println(str.charAt(3)); }catch(Exception e){e.printStackTrace();}
    this.online = online;
    }

  public boolean isOnline () {
    return online;
    }

  // Return if it is an exercise creation
  public boolean isModoCriacao () {
    return modoCriacao;
    }

  public static Exercicio [] getExercicios () {
    I18n i18n = I18n.getInstance();
    Exercicio[] exercise = new Exercicio[10];
    for (int i=0; i<exercise.length; i++) {
      exercise[i] = new Exercicio(i+1);
      }
    return exercise;
    }

  public SetMachine () {
    // System.out.println("SetMachine.java: contador="+contador);
    contador = contadorStatic; // auxiliar
    contadorStatic++; // auxiliar
    reinicia();
    }

  // From: icomb.IComb.main(String[] args) to initiate an exercise from command line
  public void setExerciseInitiated () {
    estado = ESTADO_EXERCICIO_CONFIRMADO;
    }
  // From: icomb.IComb.main(String[] args) to check if the exercise has been initiated from command line
  public boolean isExerciseInitiated () {
    return estado == ESTADO_EXERCICIO_CONFIRMADO;
    }

  public int getEstado () {
    return estado;
    }

  public void iniciar () {
    estado = ESTADO_INICIAL;
    }

  // icomb/ui/CreateExercisePanel.java: re-start all components
  public void reinicia () {
    estagioAuxiliar = null; // necessary to clear any eventual stage already registered
    exercicioAtual = null;
    estagioAtual = null;
    operacao = null;
    formulaAtual = null;
    estagios = new Vector();
    estado = ESTADO_INICIAL; // from scratch: includes button 'Open file' in aplication
    }

  public void setExercicio (Exercicio ex) {
    exercicioAtual = ex;
    estagioAtual = null;
    operacao = null;
    formulaAtual = null;
    estagios = new Vector();
    estado = ESTADO_EXERCICIO_CONFIRMADO; // now the user must provide an stage decomposition - a solution template
    // ESTADO_ESCOLHENDO_EXERCICIO;
    }

  public void reiniciaConstrucao () {
    exercicioAtual = null;
    estagioAtual = null;
    operacao = null;
    formulaAtual = null;
    estagios = new Vector();
    estado = ESTADO_CONSTRUCAO_INICIADA;
    }

  // From: 'src/icomb/ui/ChooseExercisePanel.loadExercise()'
  public void notExerciseCreation () {
    modoCriacao = false; // important when loading 'file exercise' - avoid to show the choice to change Universe set
    }

  // From: src/icomb/IComb.monta(); src/icomb/ui/ChooseExercisePanel.loadExercise(); src/icomb/ui/DefinedExercisePanel.actionPerformed(ActionEvent)
  public void confirmaExercicio () {
    estado = ESTADO_EXERCICIO_CONFIRMADO;
    }

  public Exercicio getExercicioAtual () {
    return exercicioAtual;
    }

  public void inicialEstagioAtual () {
    // Cria 'new Estagio()' apos clicar no
    // Neste ponto nada eh feito, apos completar ultima etapa estagio, o 'alteraEstagioAtual(Hashtable)' copia 'Hashtable' de 'estagioAuxiliar' em 'estagioAtual'
    this.estagioAtual = new Estagio();
    estado = ESTADO_EDITANDO_ESTAGIO;
    }

  // From: src/icomb/ui/ChooseExercisePanel.java: em 'actionPerformed(ActionEvent): if (e.getSource() == btnCriar)'
  public void criandoExercicio () {
    estado = ESTADO_CRIANDO_EXERCICIO;
    modoCriacao = true;
    }

  // From: src/icomb/ui/StagePanel.java: in 'addStage()'
  public void alteraEstagioAtual (Hashtable map) {
    // System.out.println("SetMachine.alteraEstagioAtual: #estagioAtual="+estagioAtual.getCont());
    //if (estagioAtual.cont>1) //try{String str="";System.out.println(str.charAt(3));}catch(Exception  e){e.printStackTrace();}
    estagioAtual.alimentaEstagio(map); // 'icomb.objects.Estagio estagioAtual'
    }


  // Use an auxilliary stage to generate elements-properties -
  public void alimentaEstagioAuxiliar (Hashtable map) {
    estagioAuxiliar = new Estagio();
    //System.out.println("SetMachine.alimentaEstagioAuxiliar -------------------- new Estagio() "+estagioAuxiliar.getCont()+" aqui");//
    //try{String str="";System.out.println(str.charAt(3));}catch(Exception  e){e.printStackTrace();}
    estagioAuxiliar.alimentaEstagio(map);
    }

  public boolean estagioAtualEstaDefinido () {
    return estagioAtual != null;
    }

  public int getEstagioAtualId () {
    if (estagioAtual == null)
       throw new RuntimeException("Estagio atual não definido!");
    return estagioAtual.hashCode();
    }

  public int getEstagioAtualN () {
    if (estagioAtual == null)
       throw new RuntimeException("Estagio atual não definido!");
    return estagioAtual.getN();
    }

  public int getEstagioAtualP () {
    if (estagioAtual == null)
       throw new RuntimeException("Estagio atual não definido!");
    return estagioAtual.getP();
    }

  // From: src/icomb/ui/StagePanel.java: in 'addStage()'
  public String getEstagioAtualTextoFormula () {
    if (estagioAtual == null)
       throw new RuntimeException("Estagio atual não definido!");
    return estagioAtual.getTextoFormula();
    }

  // From: icomb.ui.StagePanel.itemStateChanged(StagePanel.java:586)
  public void setFormulaAtual (String strFormula) {
System.out.println("SetMachine.setFormulaAtual -------------------- "+strFormula);//
//
    formulaAtual = Formula.getStaticMapFormula(strFormula); // Formula.getFormulaInstancia(strFormula); //
//    estagioAtual.defineNeP(0, 0); // Estagio.n=0; Estagio.p=0;
    estado = ESTADO_EDITANDO_FORMULA;
    }

  public void validaEstagioAtual () {
    estagioAtual.setFormula(formulaAtual);
    if (corrige)
      Avaliador.deteccaoDeErroFormula(universo, estagioAtual);
    estagioAuxiliar = null;
    estagios.add(estagioAtual);
    //
System.out.println("\nSetMachine.java: validaEstagioAtual(): #estagios="+estagios.size());
    }

  // icomb.ui.StagePanel.itemStateChanged(StagePanel.java:618)
  public void defineNeP (String strN, String strP) {
    int n = -1;
    int p = -1;
    try {
      n = Integer.parseInt(strN);
    } catch(NumberFormatException e) {
      n=-1;
      }
    try {
      p = Integer.parseInt(strP);
    } catch(NumberFormatException e) {
      p = -1;
      // e.printStackTrace();
      }
    if (estagioAtual!=null) // está vindo vazio na versão de carga inicial completa dos paineis
       estagioAtual.defineNeP(n, p);
    // else System.err.println("Erro: SetMachine; defineNeP: "+strN+", "+strP);
    estado = ESTADO_FORMULA_DEFINIDA;
    } // void defineNeP(String strN, String strP)

  public boolean formulaAtualEstaDefinida () {
    return formulaAtual!=null;
    }

  public boolean formulaAtualTemParametroN () {
    if (formulaAtual==null)
       throw new RuntimeException("Formula não está definida!");
    return formulaAtual.temParametroN();
    }

  public boolean formulaAtualTemParametroP () {
    if (formulaAtual==null)
       throw new RuntimeException("Formula não está definida!");
    return formulaAtual.temParametroP();
     }

  public String getNomeFormulaAtual () {
    if (formulaAtual==null)
      throw new RuntimeException("Formula não está definida!");
    return formulaAtual.getNome();
    }


  public Vector getEstagios () {
    return estagios;
    }

  public void apagarEstagio () {
    estagioAuxiliar = null;
    }

  public Vector getEstagiosParaDesenhar () {
    Vector listaDeEstagios = (Vector) estagios.clone();
    if (estagioAuxiliar != null) {
       listaDeEstagios.add(estagioAuxiliar);
       }
    return listaDeEstagios;
    }

  public void setOperacao (String operacao) {
    if (operacao != null)
       this.operacao = operacao;
    estado = ESTADO_OPERACAO_DEFINIDA;
    }

  public void defineResposta (String respostaEnviada) {
    this.respostaEnviada = respostaEnviada;
    estado = ESTADO_CONSTRUCAO_FINALIZADA;
    }

  public String getExpressaoFinal () {
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<estagios.size(); i++) {
      Estagio estagio =(Estagio) estagios.get(i);
      String text;
      if (estagio.getFormula().temParametroP())
         text = estagio.getTextoFormula();
      else
         text = estagio.getTextoFormula();
      if (i==0)
         sb.append(text);
      else
      if (operacao != null) {
         if (operacao.equals(Constants.SOMA))
            sb.append(" + " + text);
         else if (operacao.equals(Constants.MULTIPLICA))
            sb.append(" * " + text);
         }
      }
    return sb.toString();
    }

  public long getResultadoFinal () {
    long retorno = 0;
    if (operacao == null)
       return 0;
    if (operacao.equals(Constants.MULTIPLICA))
       retorno=1;
    for (int i=0; i<estagios.size(); i++) {
      Estagio estagio =(Estagio) estagios.get(i);
      long result = 0;
      if (estagio.getFormula().temParametroP())
         result = estagio.getFormula().calcula(estagio.getN(), estagio.getP());
      else
         result = estagio.getFormula().calcula(estagio.getN());
      if (operacao.equals(Constants.SOMA))
         retorno+=result;
      else if (operacao.equals(Constants.MULTIPLICA))
         retorno*=result;
      }
    return retorno;
    }

  public String toFormatoReduzido () {
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<estagios.size(); i++) {
      Estagio stage =(Estagio) estagios.get(i);
      Condicao condicao = stage.criaCondicao();
      String result = condicao.toFormatoReduzido();
      if (i==0)
         sb.append(result);
      else
         sb.append(";" + result);
      }
    return sb.toString();
    }

  public String getEstagioString () {
    StringBuffer sb = new StringBuffer();
    //_ System.out.println("SetMachine.getEstagioString: #estagios="+estagios.size());
    for (int i=0; i<estagios.size(); i++) {
      Estagio stage = (Estagio) estagios.get(i);
      long result = 0;
      // icomb.objects.Estagio int n;
      if (stage.getFormula().temParametroP()) // abstract icomb.formula.Formula: icomb.formula.[Value |
         result = stage.getFormula().calcula((long)stage.getN(), stage.getP());
      else
         result = stage.getFormula().calcula((long)stage.getN());
      if (i==0)
         sb.append(result);
      else
         sb.append(" ; " + result);
      //_ System.out.print(" ("+i+","+stage.getCont()+"): result="+result+" <getN="+stage.getN()+", getP="+stage.getP()+", temParam="+stage.getFormula().temParametroP()+">: sb='"+sb+"'");
      //_ if (result<0) System.out.println(" <-- ");
      //_ else System.out.println();//" getP="+stage.getP());
      }
    return sb.toString();
    }

  // From: src/icomb/ui/ListPanel.java - addStageFromConstructionPanel() and redo()
  // The total amount of combinations of each stage
  public String getOperacaoTexto () {
    String result = getEstagioString();
    if (operacao == null)
       return result;
    else
    if (operacao.equals(Constants.SOMA))
       return result.replaceAll(";","+");
    else
    if (operacao.equals(Constants.MULTIPLICA))
       return result.replaceAll(";","*");
    else
      return result;
    }

  public String getOperacao () {
    return operacao;
    }


  public void finaliza () {
    estado = ESTADO_CONSTRUCAO_FINALIZADA;
    }

  /**
   * Resultado da Avaliação do Exercício
   *
   * @return int 0=>Errado 1=>Certo
   */
  public int getAvaliacao () {
    if (estado != ESTADO_VALIDA_CONSTRUCAO) {
      return -1;
      }
    try {
      verificaResposta();
    } catch(Exception e) {
      return 0;
      }
    return 1;
    }

  /**
   * Retorna a resposta do aluno
   * @return
   */
  public String getAnswer () {
    return getConjuntoEstagioString().toString();
    }

  /**
   * Retorna a resposta formatada para o professor
   * @return resposta
   */
  public String montaResposta () {
    int avaliacao = ObjectManager.setMachine.getAvaliacao();
    String answer = ObjectManager.setMachine.getAnswer();
    String trace = ObjectManager.getInstance().tracePilhaDeAcoes();
    if (avaliacao > 0)
       return answer + "\n"  + "RESPOSTA CORRETA!" + "\n" + trace;
    else
       return answer + "\n"  + "RESPOSTA INCORRETA!"+ "\n" + trace;
    }

  public String getStringFinal () {
    if (respostaEnviada != null)
      return respostaEnviada;
    StringBuffer sb = new StringBuffer();
    sb.append(I18n.getString("hereIsYourSolution"))
      .append("\n\n")
      .append(I18n.getString("universe"))
      .append("\n")
      .append(I18n.getString("theSetOfSubsetsWith"))
      .append(nElementos)
      .append(I18n.getString("elementsTakenIn"))
      .append(I18n.getString(universo.getNome()))
      .append("\n\n")
      .append(I18n.getString("construction"))
      .append("\n")
      .append(getConjuntoEstagioString());
    return sb.toString();
    }

  public String getConjuntoEstagioString () {
    StringBuffer sb = new StringBuffer();
    if (getEstagios()!=null && getEstagios().size()>0) {
      for (int i=0; i<getEstagios().size(); i++) {
          Estagio estagio = (Estagio) getEstagios().get(i);
          sb.append(estagio.getDescricao());
          //_ sb.append(I18n.getString("numberOfPossibilities")).append(":").append(estagio.getTextoFormula()).append("=").append(estagio.getValorFinal()).append("\n");
          sb.append(I18n.getString("numberOfPossibilities") + ":" + estagio.getTextoFormula() + "=" + estagio.getValorFinal() + "\n");
          }
      //_ sb.append("\n").append(I18n.getString("numberOfPossibilities")).append(":").append(getExpressaoFinal()).append("=").append(getResultadoFinal());*/
      sb.append("\n" + I18n.getString("numberOfPossibilities") + ":" + getExpressaoFinal() + "=" + getResultadoFinal());
      }
    return sb.toString();
    }

  public void setNElementos (int elementos) {
    nElementos = elementos;
    }

  public void defineDica (String dica) {
    this.dica = dica;
    if (ObjectManager.getInstance().getSetMachinePanel()!=null)
       ObjectManager.getInstance().getSetMachinePanel().drawDica();
    }

  // Muda a dica
  public void mudaDic (String dica) {
    if (modoCriacao)
       return;
    this.dica = dica;
    if (ObjectManager.getInstance().getSetMachinePanel()!=null)
       ObjectManager.getInstance().getSetMachinePanel().drawDica();
    }

  // Empty from: icomb.ui.DefinedUniversePanel; icomb.ui.StagePanel.secondLine; icomb.ui.StagePanel.thirdLine; icomb.ui.DrawingPanel.recalcula; icomb.ui.DrawingPanel.paint
  // Valid from: icomb.ui.DefinedUniversePanel <- icomb.ui.SetMachinePanel.update
  public Universo getUniverso () {
    return universo;
    }

  public String getArquivoUniverso () { return this.arquivoUniverso; }
  public String getDirectory () { return this.directory; }

  // From: icomb.ui.ChooseExercisePanel.loadExercise()
  // From: icomb.IComb.iniciaExercicio(IComb.java:390) :: when application with a CMB file at line command
  // From: icomb.IComb.iniciaExercicio(IComb.java:401) :: when applet with a HTML/CMB file
  public void setArquivoUniverso (String directory, String arquivo) {
    //T System.out.println("src/icomb/machine/SetMachine.java: setArquivoUniverso: directory="+directory+", arquivoUniverso="+arquivo);
    //T
try { String str=""; System.out.println(str.charAt(3)); }catch(Exception e) {e.printStackTrace();}

    if (!icomb.IComb.isApplet()) { // Application: clear 'file:/' from the name 'file:/home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/baralho/imagens/ad.gif'
//      directory = icomb.util.GerenciadorRecursos.clearFileFromName(directory);
      directory = icomb.util.GerenciadorRecursos.getPath(this);
      }

System.out.println("\n\nSetMachine.setArquivoUniverso: directory="+directory+", arquivo="+arquivo);

    this.directory = directory;
    this.arquivoUniverso = directory + arquivo; // It will define 'Universo.arquivo': 'String arquivo' must have the complete name to the file
    }

  // From: icomb.IComb.main(IComb.java:174)
  public void loadUniverso (MontaFace mf) {
    Universo uni = null;
    try {
      // if (icomb.IComb.DEBUG) System.err.println(icomb.IComb.debugMsg("icomb/machine/SetMachine.java:")+" teste ");

      uni = Parse.parseUniverso(GerenciadorRecursos.getInputStream(mf, this.arquivoUniverso), this.arquivoUniverso);
      //X uni = Parse.parseUniverso(GerenciadorRecursos.getContentTextJAR(mf.getApplet(), "/"+this.arquivoUniverso));

    } catch(Exception ex) {
      //L MessageFrame.showMessage("Universo Inválido!", mf);
      String msg = I18n.getString("invalid_universe");
      MessageFrame.showMessage(msg,mf);
      System.err.println(icomb.IComb.debugMsg(this) + "Error: in SetMachine: " + ex + "Error msg: " + msg + " - in arquivoUniverso: " + arquivoUniverso);
      // ex.printStackTrace();
      return;
      }

    // Define path to get imagens from de Universe
    uni.setArquivo(this.directory, this.arquivoUniverso);
    //T System.out.println("\n\nSetMachine.java: loadUniverso(MontaFace): path="+this.directory+", arquivoUniverso="+this.arquivoUniverso);
    //T try { String str=""; System.out.println(str.charAt(3)); }catch(Exception e) {e.printStackTrace();}

    // Agora define 'this.universo':
    ObjectManager.setMachine.setUniverso(uni); // método abaixo 'setUniverso (Universo universo)'

    } // void loadUniverso(MontaFace mf)

  // Also came from 'src/icomb/ui/DefinedUniversePanel.java'
  public void setUniverso (Universo universo) {
    formulaAtual = null;
    estagios = new Vector(); //

    this.universo = universo; // icomb.objects.Universo
    if (!modoCriacao) {
       avaliador = new Avaliador(universo,exercicioAtual.getSolucao()); // icomb.objects.Exercicio
       }
    else {
       if (universo!=null)
          this.arquivoUniverso = universo.getArquivo();
       }
    // System.out.println("SetMachine.java: setUniverso: modoCriacao="+modoCriacao+", #estagios="+estagios.size()+", arquivoUniverso="+this.arquivoUniverso);
    // try { String str=""; System.out.println(str.charAt(3)); }catch(Exception e) {e.printStackTrace();}

    estado = ESTADO_CONSTRUCAO_INICIADA;
    if (universo!=null) // set 'icomb.ui.SetMachinePanel.setEnabledStartDUPanel()'
{
       ObjectManager.getInstance().getSetMachinePanel().setEnabledStartDUPanel(); // enable "Start" button to start the exercise resolution - from 'DefinedUniversePanel dePanel.setEnabled()'
System.out.println("SetMachine.java: setUniverso: OK - modoCriacao="+modoCriacao+", estado="+estado);
} else System.out.println("SetMachine.java: setUniverso: NAO - modoCriacao="+modoCriacao+", estado="+estado);
     


    } // void setUniverso(Universo universo)

  public void verificaResposta () {
    if (corrige) {
       avaliador.valida();
       }
    // ObjectManager.setMachine.
    if (!(getResultadoFinal() == getExercicioAtual().getResposta())) { // Exercicio exercicioAtual
      throw new RuntimeException("Resultado Incorreto!"); // 'getResultadoFinal()' -> "student answer" or "answer of exercise creation"
      }
    }

  public void validaConstrucao () {
    verificaResposta();
    estado = ESTADO_VALIDA_CONSTRUCAO;
    }

  // Try to identify error in the student stage decomposition
  public void verificaErro (Condicao condicao) {
    if (!corrige) return;
    avaliador.reset();
    for (int i=0; i<estagios.size(); i++) {
      Estagio stage = (Estagio) estagios.get(i);
      avaliador.adicionaCondicao(stage.criaCondicao());
      }
    avaliador.adicionaCondicao(condicao);
    }

  public int getNElementos () {
    return nElementos;
    }

  public String getDica () {
    return dica;
    }

  public boolean isCorrige () {
    return corrige;
    }

  public void setCorrige (boolean corrige) {
    this.corrige = corrige;
    }

  }
