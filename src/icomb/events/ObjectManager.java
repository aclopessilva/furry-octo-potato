/*
 * iMath: http://www.matematica.br
 * iComb: http://www.matematica.br/icomb
 *
 * @author: AKE, LOB
 * @description: Define botoes laterais de painel central: [Iniciar; Ajuda; Criar exerc.] [Resolver exerc.]
 */

package icomb.events;

import icomb.components.CustomChoice;
import icomb.components.CustomList;
import icomb.machine.SetMachine;
import icomb.ui.SetMachinePanel;
import icomb.ui.ToolTip;
import icomb.util.Acao;
import icomb.util.Constants;
import icomb.util.Configuracoes;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

public class ObjectManager implements ItemListener, ActionListener {

  private static String staticErrTool=""; // auxiliary to debug in 'defineCoresFonte' from 'criaBotao'
  private static ToolTip staticToolTip = null; // to dismiss the last popup tool tip (in case it lost focus)

  private static ObjectManager uniqueInstance;

  private static Vector components = new Vector();

  private Vector acoes = new Vector();

  private long tempoInicial;

  private Object unicoFrame;

  private SetMachinePanel setMachinePanel;

  public static SetMachine setMachine = new SetMachine();

  public void zerarCronometro () {
    tempoInicial = System.currentTimeMillis();
    }

  public void trocaFrame (Object novoFrame) {
    if (unicoFrame != null) {
       if (unicoFrame instanceof Frame)
          ((Frame) unicoFrame).setVisible(false);
       if (unicoFrame instanceof Applet)
          ((Applet) unicoFrame).setVisible(false);    
       }
    acoes = new Vector();
    unicoFrame = novoFrame;
    }

  public static ObjectManager getInstance () {
    if (uniqueInstance == null)
      uniqueInstance = new ObjectManager();
    return uniqueInstance;
    }

  private ObjectManager () {
    }

  // From: src/icomb/ui/StagePanel.java: in 'addStage()'
  public void registraAcao (Object o) {
    Acao acao = new Acao(o,(System.currentTimeMillis()-tempoInicial)/1000);
    acoes.add(acao);
    System.out.println("ObjectManager.registraAcao: "+acao);
    // System.out.println(setMachine.getState());
    }

  public Vector pilhaDeAcoes () {
    return acoes;
    }

  public String tracePilhaDeAcoes () {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < acoes.size(); i++) {
      Acao acao = (Acao) acoes.get(i);
      sb.append(acao).append('\n');
      }
    return sb.toString();
    }

  public void itemStateChanged (ItemEvent e) {
    registraAcao(e.getSource());
    }

  public void actionPerformed (ActionEvent e) {
    registraAcao(e.getSource());
    }

  public static void defineCoresFonte (int corRGB, String texto, Component compPaiAWT, Component compAWT) {
    Color corPai = compPaiAWT.getBackground();
    // System.out.println("ObjectManager.defineCoresFonte(...): : "+texto+" - "+corPai);
    try {
      corPai = compPaiAWT.getBackground();
      boolean nova_corRGB = false;
      if (corPai!=null) {
         nova_corRGB = true;
         corRGB = -corPai.getRGB(); // linearizar para poder ver se é mais claro ou escuro
         compAWT.setBackground(corPai);
         }
      else { // auxiliar para listar msg de erro em console
         String str = "";
         int tam = 0;
         if (texto!=null) {
            tam = texto.length();
            if (tam > 20) tam = 20;
            str = texto.substring(0,tam);
            }
         System.err.println("\nErro: gerenciador de obj. cor de pai vazia! "+str);
         corRGB = Configuracoes.CINZA.getRGB(); // Color.gray.getRGB();
         //System.out.println("ObjectManager.java: corPai="+(corPai!=null?corPai.toString():"<>")+", corRGB="+Configuracoes.CINZA.toString());
         //System.out.println("ObjectManager.java: "+staticErrTool+": "+compPaiAWT);
         //try { String str1=""; System.out.println(str1.charAt(3)); } catch(Exception e) {e.printStackTrace();}
         compPaiAWT.setBackground(Configuracoes.CINZA); // (Color.gray);
         }

      //T String aux;
      // Color.black => getRGB = -16777216 / Color.white => getRGB = -1
      if (compAWT.getForeground()==null) {
         // se não houver cor definida, veja cor do pai
         // System.out.println("ObjectManager: color=null em compAWT="+compAWT);
         if (corPai == Configuracoes.corBarraSupInf) { // cor do pai escura => letra clara
            //T aux="1: ";
            // compAWT.setForeground(Color.white);
            compAWT.setForeground(Configuracoes.corBarraSupInf); // = new Color(32, 64, 128)
            compAWT.setBackground(Configuracoes.CINZA);
            }
         else { // cor do pai clara => letra mais escura //T aux="2: ";
            compAWT.setForeground(Configuracoes.corBarraSupInf);
            }
         }
      else { //T aux="3: ";
         compAWT.setForeground(Configuracoes.corBarraSupInf); // = new Color(32, 64, 128)
         }
      // System.out.println("ObjectManager.defineCoresFonte(...): "+aux+texto+" - "+compAWT.getForeground());

      // if (compAWT.getForeground()==Color.white)
      //    System.out.println("ObjectManager: color="+compAWT.getForeground()+" em compAWT="+compAWT);

      compAWT.setFont(Configuracoes.FONTE_BOTAO);
      compAWT.setSize(Constants.DIMENSION_BUTTON);
    } catch (Exception e) {
      System.err.println("Erro: em gerenciador de objeto, ao tentar definir cor/fonte de "+compAWT+"");
      }
    } // static void defineCoresFonte(int corRGB, String texto, Component compPaiAWT, Component compAWT)


  //  public static Button criaBotao (Component pai, String texto) {
  //   return criaBotao(pai, texto, null);
  //  }

  private static int cont = 0;
  private static TreeSet lixo = new TreeSet();
  private static void registra (String tooltip) {
    lixo.add(tooltip);
    // Iterator it = lixo.iterator();
    // while (it.hasNext()) {      
    //   System.out.println((String) it.next());
    //       }
    // System.out.println("***********************************");
    }

  // Cria botão com rótulo "texto" e dica tipo PopUp "tooltip"
  public static Button criaBotao (Component pai, String texto, String tooltip) {
    registra(cont++ + "=" + tooltip); // tooltip + "=" + tooltip);
    Button button = new Button(texto);
    if (tooltip != null && !tooltip.trim().equals("")) {
       staticToolTip = new ToolTip(tooltip, button);
       }
    Color corPai = pai.getBackground();
    if (corPai==null)
       pai.setBackground(Configuracoes.CINZA);
    staticErrTool = tooltip;
    defineCoresFonte(-2, texto, pai, button);
    // System.out.println("ObjectManager: "+pai+" cor="+pai.getBackground());
    components.add(button);
    return button;
    }


  public static CustomChoice criaCombo (Component pai, Vector keys, String tooltip) {
    // System.out.println("ObjectManager.criaCombo: #keys="+keys.size()+" - "+tooltip);
    registra(cont++ + "=" + tooltip); // tooltip + "=" + tooltip);
    return criaCombo(pai, keys, null, tooltip);
    }

  // Called: 'icomb/ui/StagePanel.java' in 'secondLine()' and 'thirdLine()'
  private static CustomChoice criaCombo (Component pai, Vector keys, Vector values, String tooltip) {
    CustomChoice choice; // icomb/components/CustomChoice.java
    if (values!=null) choice = new CustomChoice(keys,values);
    else choice = new CustomChoice(keys);
    if (tooltip!=null && !tooltip.trim().equals("")) {
       staticToolTip = new ToolTip(tooltip, choice); // popup with a tip for this choice
       }
    defineCoresFonte(-2, null, pai, choice);
    //- CustomChoice: populate: color=null<=> <value=valor> <suit=naipe> 
    //- ObjectManage: criaCombo: java.awt.Color[r=32,g=64,b=128] -> -14663552: keys=
    //choice.addItemListener(ObjectManager.getInstance());
    // if (choice.lst) {
    //  String str = keys!=null && keys.size()>0?""+keys.elementAt(1):"<>";
    //  System.out.println("ObjectManage: criaCombo: "+choice.getForeground()+" -> "+choice.getForeground().getRGB()+": keys="+str);
    //  }
    components.add(choice);
    return choice;
    }

  public static CustomList criaList (Vector keys) {
    // String str = keys!=null && keys.size()>0?""+keys.elementAt(0):"<>";
    // System.out.println("ObjectManage: criaList: keys="+str);

    CustomList list = new CustomList();
    //list.addItemListener(ObjectManager.getInstance());
    components.add(list);
    return list;
    }

  public SetMachinePanel getSetMachinePanel () {
    return setMachinePanel;
    }

  public void setSetMachinePanel (SetMachinePanel setMachinePanel) {
    this.setMachinePanel = setMachinePanel;
    }

  }
