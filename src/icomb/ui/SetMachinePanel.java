/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author AEK, LOB
 * @description
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.ui;

import icomb.events.ObjectManager;
import icomb.machine.SetMachine;
import icomb.util.Configuracoes;
import icomb.util.Crypto;

import java.applet.Applet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import base.MontaFace;

public class SetMachinePanel extends VerticalPanel {

 private static final String CODIFICACAO = Configuracoes.CODIFICACAO; // CODIFICACAO = "UTF-8" ou "ISO-8859-1"
 public static String staticErrSent = ""; // to take note of error in 'boolean criarExercicio()'

 private ChooseExercisePanel cePanel;
 private CreateExercisePanel crePanel;
 private DefinedExercisePanel dePanel;
 private ChooseUniversePanel cuPanel;
 private DefinedUniversePanel duPanel;
 private ConstructionPanel cPanel;
 private ValidationPanel vPanel;
 private FinalPanel fPanel;
 private DrawingPanel dPanel = null;

 // icomb.IComb.main(...): show statement in panel 'ChooseExercisePanel'
 public ChooseExercisePanel getCePanel () { return cePanel; }
 public DefinedExercisePanel getDePanel () { return dePanel; } // to 'DefinedUniversePanel.itemStateChanged(ItemEvent)'

 //No VerticalPanel: private final MontaFace mf;

 public SetMachinePanel (MontaFace mf) {
  super(mf);
  //No VerticalPanel! this.mf = mf;
  ObjectManager.getInstance().zerarCronometro();
  ObjectManager.getInstance().setSetMachinePanel(this);
  this.addComponentListener(this);
  }

 // habilitar botões para criar "novo estágio" (chama 'src/icomb/ui/NewStageButtonPanel.java: open();'
 public void habilitarNovoEstagio () {
  if (cPanel!=null) // ConstructionPanel
   cPanel.habilitarNovoEstagio(); // habilitar botões para criar "novo estágio" (chama 'src/icomb/ui/NewStageButtonPanel.java: open();'
  else
   System.err.println("Error: SetMachinePanel: ConstructionPanel cPanel empty!");
  }

 // Defined Universe set => enable the Start button (in 'icomb.ui.DefinedExercisePanel')
 // From: icomb.machine.SetMachine.setUniverso(Universo)
 public void setEnabledStartDUPanel () {
   if (dePanel == null)  { // CAUTION: necessary from 'ChooseExercisePanel.loadExercise()' in 'ObjectManager.setMachine.loadUniverso(painelSetMachine.getMf())'
    dePanel = new DefinedExercisePanel(this);
    addComponent(dePanel);
    }
   dePanel.setEnabled(); // DefinedExercisePanel: enable "Start" button to start the exercise resolution
   // try { String str=""; System.out.println(str.charAt(3)); }catch(Exception e) {e.printStackTrace();}
   }

 // icomb.ui.VerticalPanel.redraw(VerticalPanel.java:116)
 public void update () {
  //L removeAllComponents();
  int estadoAtualMachine = ObjectManager.setMachine.getEstado();
  // System.out.println("SetMachinePanel.update(): estadoAtualMachine="+estadoAtualMachine+" #estagios="+ObjectManager.setMachine.getEstagiosParaDesenhar().size());
  //_ Start iComb without any file :: choose to open a file (if application) | Create an exercise
  if (estadoAtualMachine==SetMachine.ESTADO_INICIAL) { // = 0
   //-
System.out.println("\n\nSetMachinePanel.update(): estadoAtualMachine="+estadoAtualMachine+" (0) inicio (nao - vazio)");
   removeAllComponents(); // IMPORTANT: when it cames from 'CreateExercisePanel.actionPerformed(ActionEvent e)' there is 'crePanel'
   cePanel = new ChooseExercisePanel(this); // 'DefinedExercisePanel.setEnabled()' via 'icomb.ui.SetMachinePanel.setEnabledStartDUPanel'
   addComponent(cePanel);
   dePanel = null; // if (dePanel == null) { dePanel = new DefinedExercisePanel(this); addComponent(dePanel); }
   duPanel = new DefinedUniversePanel(this); // define 'DefinedExercisePanel.btnIniciar.setEnabled(true)'
   addComponent(duPanel);
   cPanel = new ConstructionPanel(this);
   addComponent(cPanel);

//   dPanel = new DrawingPanel(mf);
   // addComponent(dPanel);
//   dPanel.removeComponentListener(this); // is.addMouseListener(this);

   fPanel = null;
   dPanel = null;
   crePanel = null;// necessary when arrived from 'CreateExercisePanel.actionPerformed(...)'

   } // if (estadoAtualMachine==SetMachine.ESTADO_INICIAL)
  else

  if (estadoAtualMachine==SetMachine.ESTADO_ESCOLHENDO_EXERCICIO) { // = 1
   //-
System.out.println("\n\nSetMachinePanel.update(): estadoAtualMachine="+estadoAtualMachine+" - (1) desabilita Iniciar");
   if (dePanel == null)  {
    dePanel = new DefinedExercisePanel(this);
    addComponent(dePanel);
    }
   if (duPanel == null)  {
    duPanel = new DefinedUniversePanel(this);
    addComponent(duPanel);
    }
   // if (cuPanel == null) cuPanel = new ChooseUniversePanel(this); addComponent(cuPanel);
   if (cPanel == null)  {
    cPanel = new ConstructionPanel(this);
    addComponent(cPanel);
    }
   else
    cPanel.habilitarNovoEstagio(); // habilitar botões para criar "novo estágio" (chama 'src/icomb/ui/NewStageButtonPanel.java: open();'
   if (dPanel == null) {
    dPanel = new DrawingPanel(mf);
    addComponent(dPanel);
    }

   // System.out.println("SetMachinePanel: ESTADO: " + estadoAtualMachine +" -> "+str);
   fPanel = null;
   dePanel.setDisabled(); // by default, disable "Start" button to start the exercise resolution
   } // if (ObjectManager.setMachine.getEstado()==SetMachine.ESTADO_ESCOLHENDO_EXERCICIO)
  else

  //_ Create exercise <phase 1> :: enter statement and a hint
  if (estadoAtualMachine==SetMachine.ESTADO_CRIANDO_EXERCICIO) { // = -1
System.out.println("\n\nSetMachinePanel.update(): estadoAtualMachine="+estadoAtualMachine+" - (-1) criando exerc. (nao - vazio)...");
   // Create a new exercise <phase 1>
   // System.out.println("SetMachinePanel.update(): estadoAtualMachine="+estadoAtualMachine);
   removeAllComponents(); // IMPORTANT: when it cames from 'ChooseExercisePanel.actionPerformed(ActionEvent e)' there is 'crePanel'
   if (crePanel == null)
    crePanel = new CreateExercisePanel(this);
   addComponent(crePanel);
   dePanel = null; // panel to define Universe
   duPanel = null;
   cPanel = null;
   vPanel = null;
   }
  else

  //_ Create exercise <phase 2> :: choose universe -> click in "Start"
  //_ Open exercise :: choose universe -> click in "Start"
  if (estadoAtualMachine==SetMachine.ESTADO_EXERCICIO_CONFIRMADO) { // = 2 : exerc. escolhido, iniciando-se resolucao
   // Create a new exercise: phase 2
   //-
System.out.println("\n\nSetMachinePanel.update(): estadoAtualMachine="+estadoAtualMachine+" - (2) fase2_criar (nao) ou aberto_exercicio (sim)");
   // System.out.println(" ESTADO: " + SetMachine.ESTADO_EXERCICIO_CONFIRMADO);
   removeAllComponents();
   dePanel = new DefinedExercisePanel(this);
   addComponent(dePanel);
   duPanel = new DefinedUniversePanel(this);
   addComponent(duPanel);
   cPanel = new ConstructionPanel(this);
   addComponent(cPanel);

   if (dPanel == null) //___*___
      dPanel = new DrawingPanel(mf);

   addComponent(dPanel); // VerticalPanel.addComponent(Component component): panels.add(component);
System.out.println("\n\n******************* SetMachinePanel.update(): DrawingPanel");
//dPanel.removeComponentListener(this); // is.addMouseListener(this);
//this.addMouseListener(dPanel);

System.out.println("SetMachinePanel.java ObjectManager.setMachine.getUniverso="+ObjectManager.setMachine.getUniverso());
   if (ObjectManager.setMachine.getUniverso()!=null) // Universe set defined => enable "start" button
      dePanel.setEnabled(); // enable "Start" button to start the exercise resolution
   else
      dePanel.setDisabled(); // by default, disable "Start" button to start the exercise resolution
   fPanel = null;
   }
  else

  if (estadoAtualMachine==SetMachine.ESTADO_CONSTRUCAO_INICIADA) { // = 3
System.out.println("\n\nSetMachinePanel.update(): estadoAtualMachine="+estadoAtualMachine+" - (3) construcao iniciada (nao?)...");
   // iniciada a resolução do exercício
   if (dePanel == null) {
    dePanel = new DefinedExercisePanel(this);
    addComponent(dePanel);
    }
   if (duPanel == null) {
    duPanel = new DefinedUniversePanel(this);
    addComponent(duPanel);
    }
   // Iniciar necessariamente ConstructionPanel.
   if (cPanel == null) {
    // src/icomb/ui/NewStageButtonPanel.java: 'open()' -> ' btnNewStage.setEnable(true)' e outras habilitações
    cPanel = new ConstructionPanel(this);
    addComponent(cPanel);
    }
   if (dPanel == null) {
    dPanel = new DrawingPanel(mf);
    addComponent(dPanel);
    }
   vPanel = null;
   fPanel = null;
   dePanel.setDisabled(); // by default, disable "Start" button to start the exercise resolution
   // System.out.println(" ESTADO_CONSTRUCAO_INICIADA: " + SetMachine.ESTADO_CONSTRUCAO_INICIADA+" -> "+str);
   }
  else

  if (estadoAtualMachine==SetMachine.ESTADO_OPERACAO_DEFINIDA) { // = 7
System.out.println("\n\nSetMachinePanel.update(): estadoAtualMachine="+estadoAtualMachine+" - (7) op. definida (nao?)...");
   // Se o painel vPanel já estiver sido aberto, é necessário
   // limpar todo os painéis para voltar a etapa de edição
   if (vPanel != null) {
    removeAllComponents();
    }
   if (dePanel == null) dePanel = new DefinedExercisePanel(this);
   addComponent(dePanel);
   if (duPanel == null) duPanel = new DefinedUniversePanel(this);
   addComponent(duPanel);
   if (cPanel == null) cPanel = new ConstructionPanel(this);
   addComponent(cPanel);

   if (dPanel == null) { //___*___
      dPanel = new DrawingPanel(mf);
      addComponent(dPanel);
      }
//___*___   if (dPanel == null) dPanel = new DrawingPanel(mf);
//___*___   addComponent(dPanel);

   vPanel = null;
   fPanel = null;
   dePanel.setDisabled(); // by default, disable "Start" button to start the exercise resolution
   }
  else

  if (estadoAtualMachine==SetMachine.ESTADO_VALIDA_CONSTRUCAO) { // = 8
System.out.println("\n\nSetMachinePanel.update(): estadoAtualMachine="+estadoAtualMachine+" - (8) valida construcao (nao?)...");
   removeAllComponents();
   if (dePanel == null) dePanel = new DefinedExercisePanel(this);
   addComponent(dePanel);
   if (duPanel == null) duPanel = new DefinedUniversePanel(this);
   addComponent(duPanel);
   if (vPanel == null) vPanel = new ValidationPanel(this);
   addComponent(vPanel);
   fPanel = null;
   dePanel.setDisabled(); // by default, disable "Start" button to start the exercise resolution
   }
  else

  if (estadoAtualMachine==SetMachine.ESTADO_CONSTRUCAO_FINALIZADA) { // = 9
System.out.println("\n\nSetMachinePanel.update(): estadoAtualMachine="+estadoAtualMachine+" - (9) construcao finalizada (nao?)...");
   if (dePanel == null) dePanel = new DefinedExercisePanel(this);
   addComponent(dePanel);
   if (fPanel == null) fPanel = new FinalPanel(this);
   addComponent(fPanel);
   dePanel.setDisabled(); // by default, disable "Start" button to start the exercise resolution
   }

  } // void update()

 public void drawDica () {
  if (duPanel!= null)
   duPanel.mudaDica();
  }

 public void drawAgain () {
  dPanel.recalcula(); // DrawingPanel dPanel
//  dPanel.repaint();
  dPanel.myPaint();
  }

 //  public static void main (String[] args) {
 //     Frame f = new Frame("Set Machine");
 //     SetMachinePanel setMachinePanel = new SetMachinePanel(null);
 //     setMachinePanel.setDimension(new Dimension(595,605));
 //     f.add(setMachinePanel);
 //     f.pack();
 //     f.addWindowListener(new WindowAdapter() {
 //
 //    public void windowClosing(WindowEvent e) { System.exit(0); }
 //       });
 //     f.setVisible(true);
 //     }

 public void enviar () {
  // Cria a resposta para o professor
  String resposta;
  String str_envWebValor; //str_envWebArquivo = URLEncoder.encode(resposta, CODIFICACAO),

  try {
   resposta = ObjectManager.setMachine.montaResposta();
   str_envWebValor   = URLEncoder.encode("0", CODIFICACAO);
  } catch (Exception e) { System.err.println("Erro: em def. painel maq. (resposta): "+e); return; }

  if (!icomb.IComb.isApplet()) try {
   if (!ObjectManager.setMachine.isOnline()) { // se nao for exerc. 'online' mostra resposta ao usuario
    System.out.println("Resposta:\n"+resposta);
    System.out.println("Cod. str_envWebValor="+str_envWebValor);
    }
   // else {
   //   System.out.println("Resposta 0:\n"+resposta);
   //   System.out.println("Cod. str_envWebValor="+str_envWebValor);
   //   }
   return;
   } catch (Exception e) { System.err.println("Erro: em def. painel maq. (nao applet): "+e); return; }

  Applet applet = mf.getApplet();
  try {
   String enderecoPost = applet.getParameter("MA_PARAM_addresPOST");
   // Construct data
   String data = URLEncoder.encode("envWebArquivo", CODIFICACAO) + "=" + URLEncoder.encode(resposta, CODIFICACAO)
                 +"&"+URLEncoder.encode("envWebValor", CODIFICACAO) + "=" + URLEncoder.encode("0", CODIFICACAO);

   // Send data
   URL url = new URL(enderecoPost);
   URLConnection conn = url.openConnection();
   conn.setDoOutput(true);
   OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
   wr.write(data);
   wr.flush();

   // Get the response
   BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
   String line;
   while ((line = rd.readLine()) != null) {
    System.out.println(line);
    }
   wr.close();
   rd.close();
  } catch (Exception e) {
    e.printStackTrace();
    }
  ObjectManager.getInstance().trocaFrame(null);
  }

 // :- LMS conection -:
 // Send exercise solution packaged to make a POST
 // From: 'icomb/ui.ConstructionPanel.criarExercicio()'
 public boolean sent2Server () {
  staticErrSent = "";
  String enderecoPost = "";
  Applet applet = mf.getApplet();
  try {
   String exercicio = ObjectManager.setMachine.getExercicioAtual().getLongDescription(); // exercise response
   String dica = ObjectManager.setMachine.getDica();
   String universo = ObjectManager.setMachine.getUniverso().getArquivo();
   String solucao = ObjectManager.setMachine.toFormatoReduzido();

   solucao = Crypto.stringToHex(solucao);
   System.out.println(solucao);

   // Construct data
   String data = URLEncoder.encode("envWebExercicio", CODIFICACAO) + "=" + URLEncoder.encode(exercicio, CODIFICACAO)
               +"&"+URLEncoder.encode("envWebDica", CODIFICACAO) + "=" + URLEncoder.encode(dica, CODIFICACAO)
               +"&"+URLEncoder.encode("envWebUniverso", CODIFICACAO) + "=" + URLEncoder.encode(universo, CODIFICACAO)
               +"&"+URLEncoder.encode("envWebSolucao", CODIFICACAO) + "=" + URLEncoder.encode(solucao, CODIFICACAO);

   System.out.println(exercicio);
   System.out.println(dica);
   System.out.println(universo);
   System.out.println(solucao);

   // :- LMS : address to POST -:
   // AQUI: precisamos
   enderecoPost = applet.getParameter("MA_PARAM_addresPOST");

   // Send data
   URL url = new URL(enderecoPost);
   URLConnection conn = url.openConnection();
   conn.setDoOutput(true);
   OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
   wr.write(data);
   wr.flush();

   // -: wait for an URL to change the page :-
   // Get the response
   BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
   String line;
   while ((line = rd.readLine()) != null) {
    System.out.println(line);
    }
   wr.close();
   rd.close();
  } catch (Exception e) {
   staticErrSent = "POST address: "+enderecoPost+": "+e.toString();
   e.printStackTrace();
   return false;
   }
  // makes nothing with this 'line'...
  ObjectManager.getInstance().trocaFrame(null); // method to clear the 'ObjectManager.unicoFrame = null'
  return true;
  } // boolean criarExercicio()

 public MontaFace getMf () {
  return mf;
  }

 }
