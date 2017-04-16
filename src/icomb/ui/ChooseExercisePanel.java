/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author AEK, LOB
 * @description A panel with option to load any exercise or to create new exercise
 * @see icomb/ui/SetMachinePanel.java: void update()
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.ui;

import icomb.components.CustomChoice;
import icomb.events.ObjectManager;
import icomb.objects.Exercicio;
import icomb.objects.Universo;
import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.Crypto;
import icomb.util.I18n;
import icomb.util.ParserParametros;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class ChooseExercisePanel extends Panel implements ItemListener, ActionListener {

 // private CustomChoice choice;
 private TextField txtFileName;
 private TextArea label;
 private Exercicio [] exercises;
 private SetMachinePanel painelSetMachine;
 private Button buttonValidate;
 private Button openFile;
 private Button btnBack; // button to go back to the beginning
 private Button btnCriar; // button to create a new exercise

 private Hashtable mapExercicio; // Exercise properties: EnumGabarito, solucao

 public ChooseExercisePanel (SetMachinePanel pai) {
  this.painelSetMachine = pai;
  exercises = ObjectManager.setMachine.getExercicios();
  Vector keys = new Vector();
  keys.add("emptyString");

  Panel leftPanel = new Panel(new BorderLayout()) {
   public Insets getInsets () {
    return new Insets(0, 0, 0, 5);
    }
   };

  // choice = ObjectManager.criaCombo(leftPanel, keys,"choseExcercise-dica");

  setLayout(new BorderLayout());
  // choice.addItemListener(this);

  txtFileName = new TextField();
  txtFileName.setBackground(new Color(240,240,240));
  txtFileName.setForeground(Color.BLACK);
  txtFileName.setEditable(false);

  leftPanel.add(txtFileName, BorderLayout.NORTH);

  label = new TextArea("", 2, 5, TextArea.SCROLLBARS_NONE);
  label.setEditable(false);
  label.setForeground(Color.BLACK);

  leftPanel.add(label, BorderLayout.CENTER);
  add(leftPanel, BorderLayout.CENTER);

  Panel panelButtons = new Panel(new BorderLayout());

  // Not necessary: when the exercise is loaded, automatically load its content
  // buttonValidate = ObjectManager.criaBotao(panelButtons, I18n.getString("validateExercise"), I18n.getString("validateExercise-dica"));
  // buttonValidate.addActionListener(this);
  // panelButtons.add(buttonValidate, BorderLayout.SOUTH);

  openFile = ObjectManager.criaBotao(panelButtons, I18n.getString("openFile"), I18n.getString("openFile-dica"));
  // String str="";try{System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}

  if (!icomb.IComb.isApplet()) { // if it is not an applet => allow the file button
    openFile.addActionListener(this);
    panelButtons.add(openFile, BorderLayout.NORTH);
    }

  // -: button to create a new execise
  // changeExercise=Criar Exercício
  btnCriar = ObjectManager.criaBotao(panelButtons, I18n.getString("changeExercise"), I18n.getString("changeExercise-dica"));
  btnCriar.setEnabled(true);
  btnCriar.addActionListener(this);
  panelButtons.add(btnCriar, BorderLayout.SOUTH);

  add(panelButtons, BorderLayout.EAST);

  this.setVisible(true);
  }

 public Dimension getPreferredSize () {
  return new Dimension(Constants.PANEL_WIDTH, 100);
  }

 // Panel: choose a exercise in some file
 public void paint (Graphics g) {
  g.setColor(Configuracoes.FACE_DARK1); //
  g.drawString(I18n.getString("exercises"), 5, 15); // Exercises
  g.setColor(Color.black);
  g.drawRect(2, 2, getSize().width - 4, getSize().height - 4);
  }

 public Insets getInsets () {
  return new Insets(20, 5, 5, 5);
  }

 private static String readFileAsString (String filePath) throws java.io.IOException {
  StringBuffer fileData = new StringBuffer(1000);
  BufferedReader reader = new BufferedReader(new FileReader(filePath));
  char[] buf = new char[1024];
  int numRead = 0;
  while ((numRead = reader.read(buf)) != -1) {
   String readData = String.valueOf(buf, 0, numRead);
   fileData.append(readData);
   buf = new char[1024];
   }
  reader.close();
  return fileData.toString();
  }

 public void itemStateChanged (ItemEvent e) {
  String pathName = e.getItem() + "";
  String content = "";
  try {
   content = readFileAsString(pathName);
  } catch (IOException e1) {
   // TODO Auto-generated catch block
   e1.printStackTrace();
   }

  mapExercicio = ParserParametros.parser(content);
  String enumGabarito = (String) mapExercicio.get("EnumGabarito");

  label.setText(enumGabarito);
  }

 public void actionPerformed (ActionEvent e) {
  // Create a new exercise: phase 1
  if (e.getSource() == btnCriar) {
   btnCriar.setEnabled(false);
   // System.out.println("ChooseExercisePanel.actionPerformed(...): criar");
   ObjectManager.setMachine.criandoExercicio(); // icomb.machine.SetMachine: criandoExercicio()
   painelSetMachine.redraw(); // icomb.ui.SetMachinePanel painelSetMachine estende VerticalPanel: redraw()' em 'VerticalPanel'
   }
  else
  if (e.getSource() == openFile) {
   JFileChooser fileChooser = new JFileChooser(".");
   fileChooser.addChoosableFileFilter(new FileFilter() {
    public boolean accept(File f) {
     if (f.isDirectory()) { return true; }
     return f.getAbsolutePath().endsWith(".cmb");
     }
    public String getDescription() {
     return "*.cmb";
     }
   });

   int returnVal = fileChooser.showOpenDialog(this);
   if (returnVal == JFileChooser.APPROVE_OPTION) {
    File file = fileChooser.getSelectedFile();
    // This is where a real application would open the file.
    String content = "";
    try {
     content = readFileAsString(file.getAbsolutePath());
    } catch (IOException e1) {
     // TODO Auto-generated catch block
     e1.printStackTrace();
     }
    txtFileName.setText(file.getAbsolutePath());
    mapExercicio = ParserParametros.parser(content);
    String enumGabarito = (String) mapExercicio.get("EnumGabarito");
    label.setText(enumGabarito);

    loadExercise();

    }

   }

  }

 // From: icomb.IComb.main(...)
 public void loadText (String strName, String strContent) {
   mapExercicio = ParserParametros.parser(strContent);
   String strEnum = (String) mapExercicio.get("EnumGabarito");
   txtFileName.setText(strName);
   label.setText(strEnum);
   loadExercise();
   }


 // src/icomb/ui/DefinedExercisePanel.java
 // public void setEnabled () { btnIniciar.setEnabled(true); } // SetMachinePanel.void update(): if (estadoAtualMachine==SetMachine.ESTADO_EXERCICIO_CONFIRMADO)

 // Load the exercise
 private void loadExercise () {
  Exercicio ex = new Exercicio(60, "", (String) mapExercicio.get("EnumGabarito"));
  String solucao = Crypto.hexToString((String) mapExercicio.get("solucao"));
  // To exam the solution-model:
  // System.out.println("ChooseExercisePanel.actionPerformed(...): solucao="+solucao);
  ex.setSolucao(Universo.interpretaCondicao(solucao)); // icomb/objects/Universo: Condicao[] interpretaCondicao(String strCond)

  ex.setResposta(Long.parseLong((String) mapExercicio.get("resposta")));
  ObjectManager.setMachine.setCorrige((String) mapExercicio.get("corrige") == null ? true : ((String) mapExercicio.get("corrige")).trim().equals("true"));

  // get "baralho/baralho.xml" or "futebol/futebol.xml" from directory 'icomb/'
  ObjectManager.setMachine.setArquivoUniverso("icomb/", (String) mapExercicio.get("ArquivoUniverso"));

  ObjectManager.setMachine.defineDica((String) mapExercicio.get("dica"));
  ObjectManager.setMachine.setOnline((String) mapExercicio.get("online") != null && ((String) mapExercicio.get("online")).equals("1"));
  ObjectManager.setMachine.setExercicio(ex);

  // icomb.ui.SetMachinePanel painelSetMachine
  ObjectManager.setMachine.loadUniverso(painelSetMachine.getMf()); // 'icomb.machine.SetMachine.loadUniverso(MontaFace)' makes 'icomb.ui.SetMachinePanel: DefinedExercisePanel dePanel.setEnabled()'
  ObjectManager.setMachine.confirmaExercicio(); // icomb.machine.SetMachine: confirmaExercicio() -> 'estado=ESTADO_EXERCICIO_CONFIRMADO; modoCriacao=true;'

  // important when loading 'file exercise' - avoid to show the choice to change Universe set
  ObjectManager.setMachine.notExerciseCreation(); // icomb.machine.SetMachine: modoCriacao=false; 

  painelSetMachine.redraw();
  } // void loadExercise()


 // Store the exercise: from 'ConstructionPanel.criarExercicio()'
 public String storeExercise () {
  String strContent = "";
  String str_enumGabarito, str_tipo, str_solucao, str_resposta, str_corrige, str_ArquivoUniverso, str_dica, str_online;
  try {
    // solucao answer_set
    // resposta answer_value

    // "# iComb: http://www.matematica.br\n[ .: version: 0.1 :. ]";
    strContent = Configuracoes.fileICombMark+"\n[ .: version: "+Configuracoes.strVersao+" :. ]\n" +
                 icomb.util.Util.getUserSystemData(); // get some information about the sender (could help teacher to keep track of things...)
    //- str_enumGabarito = "{EnumGabarito: " + (String) mapExercicio.get("enumGabarito") + "}\n";
    //- str_tipo = "{Tipo: exercicio: 1}" + "}\n"; // como numerar ? - (String) mapExercicio.get("") + "}\n";
    //- str_solucao = "{:" + Crypto.hexToString( (String) mapExercicio.get("solucao") ) + "}\n"; // icomb/objects/Universo: Condicao[] interpretaCondicao(String strCond)
    //- str_resposta = "{:" + (String) mapExercicio.get("resposta") + "}\n";
    //- str_corrige = "{:" + (String) mapExercicio.get("corrige") + "}\n";
    //- str_ArquivoUniverso = "{:" + (String) mapExercicio.get("ArquivoUniverso") + "}\n";
    //- str_dica = "{:" + (String) mapExercicio.get("dica") + "}\n";
    //- str_online = "{:" + (String) mapExercicio.get("online") + "}\n";
    str_enumGabarito = "{EnumGabarito: " + ObjectManager.setMachine.getExercicioAtual().getLongDescription() + "}\n";
    str_tipo = "{Tipo: exercicio: 1" + "}\n"; // como numerar ? - (String) mapExercicio.get("") + "}\n";
    str_solucao = "{solucao: " + Crypto.hexToString( ObjectManager.setMachine.toFormatoReduzido() ) + "}\n"; // icomb/objects/Universo: Condicao[] interpretaCondicao(String strCond)
    str_resposta = "{resposta: " + ObjectManager.setMachine.getResultadoFinal() + "}\n"; // resposta
    str_ArquivoUniverso = "{ArquivoUniverso: " + ObjectManager.setMachine.getUniverso().getArquivo() + "}\n"; // ArquivoUniverso
    str_dica = "{dica: " + ObjectManager.setMachine.getDica() + "}\n"; // dica
    str_online = "{online: " + (base.MontaFace.staticApplet? "1" : "0") + "}\n"; // online
    strContent += str_enumGabarito + str_tipo + str_solucao + str_resposta + str_ArquivoUniverso + str_dica + str_online;
  } catch (Exception e) {
    System.err.println("Error: in ChooseExercisePanel.storeExercise(): "+strContent);
    String str1 = (ObjectManager.setMachine!=null) ? ObjectManager.setMachine.getExercicioAtual()+"" : "<>";
    System.err.println("Error: "+str1); // ObjectManager.setMachine
    e.printStackTrace();
    }
  // To exam the solution-model:
  // System.out.println("ChooseExercisePanel.storeExercise(): \n"+strContent);
  return strContent;
  } // String storeExercise()


 }
