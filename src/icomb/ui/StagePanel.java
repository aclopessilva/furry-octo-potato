/*
 * iMath project
 * iComb: http://www.matematica.br/icomb
 *
 * @author AKE, LOB
 * @description Painel central: define numero de elementos e numero de propriedade e para cada um define "composicao" e valores (total de elem.)
 * @see src/icomb/ui/ConstructionPanel.java: new StagePanel
 * 
 */

package icomb.ui;

import icomb.components.CustomChoice;
import icomb.events.ObjectManager;
import icomb.objects.Condicao;
import icomb.objects.Expressao;
import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.I18n;
import icomb.util.Util;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;
import java.util.Vector;

public class StagePanel extends Panel implements ItemListener, ActionListener {

  private static int cont = 0;

  private static final String
      STR_LBL_AND = I18n.getString("and"), // and=e
      STR_LBL_AND_ABOUT = I18n.getString("about_and"); // about_and=escolha configurações que definem elementos de propriedade

  private static final Label LBL_AND1 = new Label(""), LBL_AND2 = new Label(STR_LBL_AND), LBL_AND_ABOUT = new Label(STR_LBL_AND_ABOUT);

  private static final int TOTAL = 50, // max for elements in any set
      ALT = 10, // altura de botao de 'choice'
      ALT2 = 12; // altura de button

  private final ConstructionPanel painelConstrucao; // painel pai
  private final Button btnVerifyStage;
  private final Button btnAddStage;
  private CustomChoice chNumberOfElements;
  private CustomChoice chNumberOfProp;
  private CustomChoice chAttributes1;
  private CustomChoice chAttributes2;
  private CustomChoice chRelations1;
  private CustomChoice chRelations2;
  private CustomChoice chElements1;
  private CustomChoice chElements2;
  private Label lblIWant;
  private Label lblElementsVerifying;
  // private Label lblSpace; // and=e
  private Label lblExplain = null; // about_and=escolha configurações que definem elementos de propriedade

  private Label lblAnd1, lblAnd2; // and=e
  private final Panel panelFormula;
  private final Panel panelFormulaEdit;
  private final Label lblFormula;
  private final Label lblN;
  private final Label lblP;
  private final CustomChoice chFormula;
  private final CustomChoice chN;
  private final CustomChoice chP;
  private final Panel panelFormulaCancel;
  private final Label lblCancel;
  private final Button btnCancelFormula;
  private final Button btnOk;
  private final ListFrame listFrame;
  private final Label lblListElements1;
  private String strListElements1;
  private final Label lblListElements2;
  private String strListElements2;

  private Button btnEscolhe1; // seleção de vários elementos: "está em" ou "não está em"

  private Button btnEscolhe2; // seleção de vários elementos: "está em" ou "não está em"

  private static void setFontStatic (Component cmp) {
    try {
      cmp.setFont(Configuracoes.FONTE_BOTAO);
    } catch (Exception e) {
      System.err.println("Error: in stage panel, while defining font "+Configuracoes.FONTE_BOTAO+": "+e);
      }
    }

  public void limpaListElementos1 () {
    lblListElements1.setText("");
    strListElements1 = "";
    }

  public void limpaListElementos2 () {
    lblListElements2.setText("");
    strListElements1 = "";
    }

  // Chamado em: ConstructionPanel.java
  public StagePanel (ConstructionPanel pai) {
    // System.out.println("StagePanel: "+pai.getName());
    // try{String str="";System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
    this.painelConstrucao = pai;
    this.setBackground(Configuracoes.CINZA);

    lblListElements1 = new Label();
    lblListElements2 = new Label();
    strListElements1 = "";
    strListElements2 = "";

    setLayout(new BorderLayout());

    Panel northPanel = new Panel(new BorderLayout());
    northPanel.setBackground(Configuracoes.CINZA);

    Panel centerPanel = new Panel(new GridLayout(0, 1, 0, 0)); // GridLayout(int rows, int cols, int hgap, int vgap):0 => qtas desejar lnh/col.
    centerPanel.setVisible(false);
    centerPanel.add(firstLine()); // "Eu quero <N> elementos que verifiquem <prop>"
    centerPanel.add(secondLine()); // "e <valor,naipe> <é, não é,...> <...>" (propriedade 1)
    centerPanel.add(thirdLine()); // "e <valor,naipe> <é, não é,...> <...>" (propriedade 2)

    invisible();
    centerPanel.setVisible(true);
    // centerPanel.setSize(700,500);
    // System.out.println("StagePanel: "+painelConstrucao.getName()+" chAttributes1="+chAttributes1);

    this.add(centerPanel, BorderLayout.CENTER); // :- adiciona ao painel

    Panel southPanel = new Panel(new BorderLayout() { // "Validar Estágio <form.> OK"
          public Insets getInsets() {
            return new Insets(0, 0, 0, 0);
            }
        });
    // Panel southPanel = new Panel(new GridLayout(1,2)); // //
    // "Validar Estágio <form.> OK"

    lblFormula = new Label(I18n.getString("formula"));
    lblFormula.setForeground(Configuracoes.FACE_DARK1);
    lblFormula.setFont(Configuracoes.FONTE_BOTAO); // compatível com 'src/icomb/events/ObjectManager.java!defineCoresFonte(...)'

    lblN = new Label("n");
    lblN.setForeground(Configuracoes.FACE_DARK1);
    lblP = new Label("p");
    lblP.setForeground(Configuracoes.FACE_DARK1);

    panelFormulaEdit = new Panel(new FlowLayout(FlowLayout.LEFT));
    panelFormulaEdit.setBackground(Configuracoes.CINZA);

    //
    chFormula = ObjectManager.criaCombo(panelFormulaEdit, Util.getListFormula(), I18n.getString("choiceFormula-dica"));
    chFormula.addItemListener(this);

    // 
    chN = ObjectManager.criaCombo(panelFormulaEdit, Util.getListNumbers(50), I18n.getString("choiceN-dica"));
    chP = ObjectManager.criaCombo(panelFormulaEdit, Util.getListNumbers(50), I18n.getString("choiceP-dica"));
    chN.addItemListener(this);
    chP.addItemListener(this);

    lblCancel = new Label("");
    panelFormulaCancel = new Panel(new FlowLayout(FlowLayout.LEFT));
    panelFormulaCancel.setBackground(Configuracoes.CINZA);

    //
    btnCancelFormula = ObjectManager.criaBotao(panelFormulaCancel, I18n.getString("cancel"), I18n.getString("cancel-dica"));
    btnCancelFormula.addActionListener(this);
    panelFormulaCancel.add(lblCancel);
    panelFormulaCancel.add(btnCancelFormula);

    //
    btnOk = ObjectManager.criaBotao(panelFormulaEdit, I18n.getString("ok"), I18n.getString("ok-dica"));
    btnOk.setSize(new Dimension(23, 23));
    btnOk.setEnabled(false);
    btnOk.addActionListener(this);

    lblFormula.setVisible(false);
    lblN.setVisible(false);
    lblP.setVisible(false);
    chFormula.setVisible(false);
    chN.setVisible(false);
    chP.setVisible(false);
    btnOk.setVisible(false);

    panelFormulaEdit.add(lblFormula);
    panelFormulaEdit.add(chFormula);
    panelFormulaEdit.add(lblN);
    panelFormulaEdit.add(chN);
    panelFormulaEdit.add(lblP);
    panelFormulaEdit.add(chP);
    panelFormulaEdit.add(btnOk);

    panelFormula = new Panel(new BorderLayout());
    panelFormula.add(panelFormulaEdit); // Panel formulae: choose formulae composition

    southPanel.setBackground(Configuracoes.CINZA);

    // Button to do the first verifiction of the user's solution: if it is OK, after the user provide the stage cardinality, add it to the composition
    btnVerifyStage = ObjectManager.criaBotao(southPanel, I18n.getString("verifyStage"), I18n.getString("verifyStage-dica")); // verifyStage=Validar Estágio
    btnVerifyStage.setEnabled(false);
    btnVerifyStage.addActionListener(this);

    // Finish this stage: this triggers the registration of a text information about the stage in 'icomb.ui.ListPanel'
    btnAddStage = ObjectManager.criaBotao(southPanel, I18n.getString("addStage"), I18n.getString("addStage-dica")); // addStage=Adicionar Estágio
    btnAddStage.setEnabled(false);
    btnAddStage.addActionListener(this);

    // Panel southPanel = new Panel(new GridLayout(1,2));
    southPanel.setBackground(Configuracoes.CINZA);

    Panel panelValStage = new Panel();
    //    panelValStage.setLayout(null); // new BorderLayout()); // ();
    // panelValStage.setSize(Configuracoes.LARG_CENTRAL, 15);
    // System.out.println("StagePanel: "+btnVerifyStage.getLabel()+" (verificar estagio) "+btnVerifyStage.getWidth()+","+btnVerifyStage.getHeight());

    // Panel: <Verify Stage> <formulae...> <Add Stage>
    panelValStage.add(btnVerifyStage); // panelValStage.add(btnVerifyStage,BorderLayout.WEST);
    panelValStage.add(panelFormula);
    panelValStage.add(btnAddStage);

    // panelValStage.add(btnAddStage,BorderLayout.EAST);
    panelValStage.setSize(Configuracoes.LARG_CENTRAL, 30); // 26);
    btnVerifyStage.setSize(90, 23);
    btnVerifyStage.setLocation(0, 3); // ,100,15);
    panelFormula.setSize(300, 25);
    panelFormula.setLocation(100, 0); //

    //    Panel auxEraseStage = new Panel(); // new FlowLayout());
    //    auxEraseStage.add(btnAddStage);
    // this.add(auxEraseStage,BorderLayout.EAST); // :- adiciona ao painel

    // tar cvfz alt-2009-07-21.tgz alteracoes.txt i18n_*.properties
    // icomb/util/Configuracoes.java

    // L southPanel.add(btnVerifyStage,BorderLayout.WEST);
    // L southPanel.add(panelFormula);
    // L southPanel.add(btnAddStage,BorderLayout.EAST);
    // L this.add(southPanel,BorderLayout.SOUTH); //
    // "Validar Estágio <form.> OK" // :- adiciona ao painel
    southPanel.add(panelValStage, BorderLayout.WEST);
    //    southPanel.add(auxEraseStage, BorderLayout.EAST);

    // "Validar Estágio <form.> OK" //
    this.add(southPanel, BorderLayout.SOUTH); // :- adiciona ao painel

    listFrame = new ListFrame(this);

    validate();

    close();

    // Sobre dimensões: dimensão definida em 'ConstructionPanel.java' que
    // estende 'VerticalPanel.java' que faz
    // 'dimension = new
    // Dimension(Constants.PANEL_WIDTH,Configuracoes.ALT_CENTRAL);

    this.setVisible(true);
    } // StagePanel(ConstructionPanel painelConstrucao)

  // Painel "Estágio": definicao das propriedades e construcao de elementos
  public Panel constroiPainel (int ind, CustomChoice cc1, CustomChoice cc2,
    CustomChoice cc3, Button btn) {
    Panel painelGeral = new Panel();
    Panel painelCombos = new Panel(new FlowLayout(FlowLayout.LEFT)); // panel with: [valor,naipe]; [é, não é,...];
    painelCombos.setSize(Configuracoes.LARG_CENTRAL, 60); // 100);
    if (ind == 1) { // painel da linha 1: propriedade 1
      // é primeira vez ("secondLine") => coloca msg explicativa
      painelGeral.setSize(Configuracoes.LARG_CENTRAL, 190);// 290);
      painelGeral.setLayout(new BorderLayout());
      lblExplain = LBL_AND_ABOUT; // about_and=escolha configurações que
      // definem elementos de propriedade
      setFontStatic(lblExplain);
//System.out.println("StagePanel: ind==1");
lblExplain.setSize(132,ALT);
      lblExplain.setForeground(Configuracoes.FACE_DARK1);
      lblExplain.setVisible(false); // nao mostra termo "e" ate que defina num. de propriedades
      // lblExplain.setVisible(true); // nao mostra termo "e" ate que defina num. de propriedades
      painelGeral.add(lblExplain, BorderLayout.NORTH); // -> add to panel
      lblAnd1 = LBL_AND1; // label "and"
      setFontStatic(lblAnd1);
      lblAnd1.setForeground(Configuracoes.FACE_DARK1);
      lblAnd1.setVisible(false); // nao mostra termo "e" ate que defina num. de propriedades
      painelCombos.add(lblAnd1);
      painelGeral.add(painelCombos, BorderLayout.CENTER); // -> add to panel
    } else { // painel da linha 2: propriedade 2
      painelGeral.setSize(Configuracoes.LARG_CENTRAL, 190);// (Configuracoes.LARG_CENTRAL,150);
      painelGeral.setLayout(new FlowLayout(FlowLayout.LEFT));
      lblAnd2 = LBL_AND2; // and=e
      setFontStatic(lblAnd2);
      lblAnd2.setForeground(Configuracoes.FACE_DARK1);
      lblAnd2.setVisible(false); // nao mostra termo "e" ate que defina num. de propriedades
      painelCombos.add(lblAnd2);
      painelGeral.add(painelCombos); // -> add to panel
      }

    // java.awt.Container parent = painelGeral.getParent();
    // String str = painelGeral.getX()+","+painelGeral.getY() +
    // " com "+painelGeral.getWidth()+","+painelGeral.getHeight();
    // //if (parent!=null) str = parent.getX()+","+parent.getY() +
    // " com "+parent.getWidth()+","+parent.getHeight();
    // //else str = this.getX()+","+this.getY() +
    // " com "+this.getWidth()+","+this.getHeight();
    // System.out.println("StagePanel: constroiPainel: ind="+ind+" em "+str);

    painelCombos.add(cc1);
    cc1.setVisible(false);
    painelCombos.add(cc2);
    cc2.setVisible(false);
    painelCombos.add(cc3);
    cc3.setVisible(false);
    painelCombos.add(btn);
    btn.setVisible(false); // seleção de vários elementos: "está em" ou "não está em"

    return painelGeral;
    } // Panel constroiPainel(CustomChoice cc1, CustomChoice cc2, CustomChoice cc3, Button btn)

  // Painel "Estágio": "Eu quero <N> elementos que verifiquem <prop>"
  public Panel firstLine () {
    Panel panel = new Panel(new FlowLayout(FlowLayout.LEFT));
    panel.setForeground(Configuracoes.FACE_DARK1);
    panel.setBackground(Configuracoes.CINZA);

    lblIWant = new Label(I18n.getString("iWant")); // iWant=Eu quero
    setFontStatic(lblIWant);
    panel.add(lblIWant);

    // Choice for natural numbers: number of elements TOTAL
    chNumberOfElements = ObjectManager.criaCombo(panel, Util.getListNumbers(TOTAL), I18n.getString("iWant-dica"));
    chNumberOfElements.addItemListener(this);
    panel.add(chNumberOfElements);

    lblElementsVerifying = new Label(I18n.getString("elementsVerifying")); // elementsVerifying=elementos que verifiquem
    setFontStatic(lblElementsVerifying);
    panel.add(lblElementsVerifying);

    // Choices for element property: number of properties is 'no property', 'one property' or 'two property'
    chNumberOfProp = ObjectManager.criaCombo(panel, Util.getListPropertyOptions(), I18n.getString("choiceNumberOfPrepositions-dica")); // icomb.events.ObjectManager
    chNumberOfProp.addItemListener(this);
    panel.add(chNumberOfProp);
    return panel;
    } // Panel firstLine()

  // src/icomb/ui/DefinedExercisePanel.java: define 'SetMachinePanel
  // painelSetMachine'
  // src/icomb/ui/SetMachinePanel.java: define 'ConstructionPanel cp'
  // src/icomb/ui/ConstructionPanel.java: define 'StagePanel painel_sp'

  // src/icomb/ui/ConstructionPanel.habilitarNovoEstagio(): para forçar
  // carregar propriedades do universo
  public void carregaAtributos (icomb.objects.Universo universo) {
    // quando carregava este painél após iniciar "fazer exercício" entrava
    // aqui, com 'src/icomb/machine/SetMachine.java!universo' definido
    chAttributes1.removeAll();
    chAttributes2.removeAll();
    // ObjectManager.java:
    //  CustomChoice criaCombo(Component pai, Vector keys, String tooltip): return criaCombo(pai, keys, null, tooltip);
    //  CustomChoice criaCombo(Component pai, Vector keys, Vector values, String tooltip): return components.add(choice);
    Vector vet = universo.getAtributos(); // icomb.objects.Universo.getElementos()
    chAttributes1.populate(vet); // insira em 'CustomChoice' o vetor de atributos do universo
    chAttributes2.populate(vet); // insira em 'CustomChoice' o vetor de atributos do universo
    ObjectManager.defineCoresFonte(-2, null, this, chAttributes1); // define fonte/cores
    ObjectManager.defineCoresFonte(-2, null, this, chAttributes2); // define fonte/cores
    //T System.out.println("StagePanel.carregaAtributos(...): fore=" + chAttributes1.getForeground() + ", back=" + chAttributes1.getBackground());
    //T icomb.util.Util.listVectorComp(vet);
    } // void carregaAtributos(icomb.objects.Universo universo)

  // Painel "Estágio": definicao das propriedades: primeira propriedade
  public Panel secondLine () {
    // Lista: atributos do conjunto universo
    // ObjectManager.setMachine.loadUniverso: está indefinido!
    // - String str = " contador="+ObjectManager.setMachine.contador;
    // -
    // System.out.println("StagePanel.secondLine(): ObjectManager.setMachine.getUniverso="+ObjectManager.setMachine.getUniverso()+" "+str);
    if (ObjectManager.setMachine.getUniverso() == null) {
      // Carregado de: ObjectManager.setMachine.loadUniverso(MontaFace);
      // src/icomb/ui/StagePanel.java: agora é completado em
      // 'ConstructionPanel.habilitarNovoEstagio()' ->
      // 'StagePanel.carregaAtributos(icomb.events.ObjectManager.setMachine.getUniverso())'

      // This choice is filled under 'void carregaAtributos(icomb.objects.Universo universo)' with 'chAttributes1.populate(vet)'
      Vector vetor = new Vector();
      vetor.add("");
      // choiceAttribute1-dica = Escolha se conjunto do estagio sera definido por valor ou por tipo (naipe)
      chAttributes1 = ObjectManager.criaCombo(this, vetor, I18n.getString("choiceAttribute1-dica"));
      // System.out.println("StagePanel.secondLine(): "+I18n.getString("choiceAttribute1-dica")+"\n - fore="+chAttributes1.getForeground()+" back="+chAttributes1.getBackground());

      // System.out.println("StagePanel.secondLine(): Universo vazio: ");
      //
    } else {
      // quando carregava este painél após iniciar "fazer exercício"
      // entrava aqui, com 'src/icomb/machine/SetMachine.java!universo'
      // definido
      chAttributes1 = ObjectManager.criaCombo(this, ObjectManager.setMachine.getUniverso().getAtributos(), I18n.getString("choiceAttribute1-dica"));
      // System.out.println("StagePanel.secondLine(): ObjectManager: "+ObjectManager.setMachine.getUniverso().getAtributos());
      }

    // setFontStatic(chAttributes1);
    chAttributes1.addItemListener(this);
    chAttributes1.setSize(100, ALT); //S new Dimension(100, ALT));
    // System.out.println("StagePanel: "+ALT);

    // Lista: relações sobre o conjunto universo (é, não é,...)
    chRelations1 = ObjectManager.criaCombo(this, Util.getListRelationOptions(), I18n.getString("choiceRelation1-dica"));
    chRelations1.addItemListener(this);
    // setFontStatic(chRelations1);
    chRelations1.setSize(80, ALT); //S new Dimension(80, ALT));

    // Lista: tipo de elementos do conjunto universo (copas, ouros,... Ás, ...)
    chElements1 = ObjectManager.criaCombo(this, new Vector(), I18n.getString("choiceElements1-dica"));
    // setFontStatic(chElements1);
    chElements1.setSize(150, ALT); //S new Dimension(150, ALT));
    chElements1.addItemListener(this);
    // L painelCombos.add(chElements1);

    // Lista: tipo de elementos para operações em conjunto ('está em' e 'não está em')
    // inNotInSet-dica = Selecione os valores para propriedade do tipo
    // pertinência (ou não) a conjunto
    btnEscolhe1 = ObjectManager.criaBotao(this, I18n.getString("inNotInSet"), I18n.getString("inNotInSet-dica"));
    btnEscolhe1.setSize(23, ALT2); //S new Dimension(23, ALT2));
    // setFontStatic(btnEscolhe1);
    // btnEscolhe1.setVisible(false);
    btnEscolhe1.addActionListener(this);

    lblListElements1.setSize(150, ALT); //S new Dimension(150, ALT));
    // painelGeral.add(painelCombos, BorderLayout.SOUTH);

    // strListElements1="";
    limpaListElementos1();
    return constroiPainel(1, chAttributes1, chRelations1, chElements1, btnEscolhe1);
    } // Panel secondLine()

  // Painel "Estágio": definicao das propriedades: segunda propriedade (se houver)
  public Panel thirdLine () {
    // Kind of value: [valor, naipe]
    if (ObjectManager.setMachine.getUniverso() == null) {
      chAttributes2 = ObjectManager.criaCombo(this, new Vector(), "choiceAttribute2-dica");
    } else {
      chAttributes2 = ObjectManager.criaCombo(this, ObjectManager.setMachine.getUniverso().getAtributos(), "choiceAttribute2-dica");
      }

    // setFontStatic(chAttributes2);
    chAttributes2.addItemListener(this);
    chAttributes2.setSize(100, ALT); //S new Dimension(100, ALT));

    //
    chRelations2 = ObjectManager.criaCombo(this, Util.getListRelationOptions(), "choiceRelation2-dica");
    // setFontStatic(chRelations2);
    chRelations2.addItemListener(this);
    chRelations2.setSize(80, ALT); //S new Dimension(80, ALT));

    // 
    chElements2 = ObjectManager.criaCombo(this, new Vector(),"choiceElement2-dica");
    // setFontStatic(chElements2);
    chElements2.setSize(150, ALT); //S new Dimension(150, ALT));
    chElements2.addItemListener(this);

    // para selecionar vários elementos: "está em" ou "não está em"
    btnEscolhe2 = ObjectManager.criaBotao(this, I18n.getString("inNotInSet"), I18n.getString("inNotInSet-dica"));
    setFontStatic(btnEscolhe2);
    btnEscolhe2.setSize(23, ALT2); //S new Dimension(23, ALT2));
    btnEscolhe2.addActionListener(this);

    lblListElements2.setSize(150, ALT); //S new Dimension(150, ALT));

    // strListElements2="";
    limpaListElementos2();

    return constroiPainel(2, chAttributes2, chRelations2, chElements2, btnEscolhe2);
    } // Panel thirdLine()

  // Importante: define dimensoes do painel com "Estagio"
  // Geral está em 'src/icomb/ui/ConstructionPanel.java' que estende
  // 'VerticalPanel'
  // 'VerticalPanel' (MontaFace mf)': dimension = new
  // Dimension(Configuracoes.LARG_CENTRAL,Configuracoes.ALT_CENTRAL);
  // "Construcao"; "Estagio" e "Lista de estagios"

  public Dimension getPreferredSize () {
    // System.out.println("StagePanel: getPreferredSize: ("+(Constants.PANEL_WIDTH-10)+","+
    // Configuracoes.ALT_CENTRAL_ESTAGIO+")");
    // System.out.println("SP: getPreferredSize: em ("+this.getX()+","+
    // this.getY()+") com ("+this.getWidth()+","+ this.getHeight()+")");
    return new Dimension(Constants.PANEL_WIDTH - 10, Configuracoes.ALT_CENTRAL_ESTAGIO); // 731 x 190
    }

  // Panel: ???
  public void paint (Graphics g) {
    // System.out.println("StagePanel: "+this.getWidth()+","+this.getHeight());
    g.setColor(java.awt.Color.red);//Configuracoes.FACE_DARK1); //
    g.drawString(I18n.getString("stage"), 5, 15);
    g.setColor(Configuracoes.CINZA_ESCURO); // bordos do bloco de definicoes de "Estagio"
    g.drawRect(2, 2, getSize().width - 4, getSize().height - 4);
    }

  public Insets getInsets () {
    // return new Insets(18,5,5,5);
    return new Insets(3, 3, 3, 3);
    }

  private boolean multiple1 () {
    return chRelations1.getSelectedKey().equals(Constants.IS_IN)
        || chRelations1.getSelectedKey().equals(Constants.IS_NOT_IN);
    }

  private boolean multiple2 () {
    return chRelations2.getSelectedKey().equals(Constants.IS_IN)
        || chRelations2.getSelectedKey().equals(Constants.IS_NOT_IN);
    }

  // Para teste
  private String testChoice (ItemEvent evt) {
    CustomChoice cc = (CustomChoice) evt.getSource();
    if (cc == chFormula)
      return "chFormula";
    if (cc == chN)
      return "chN";
    if (cc == chP)
      return "chP";
    if (cc == chNumberOfElements)
      return "chNumberOfElements";
    if (cc == chNumberOfProp)
      return "chNumberOfProp";
    if (cc == chAttributes1)
      return "chAttributes1";
    if (cc == chRelations1)
      return "chRelations1";
    if (cc == chElements1)
      return "chElements1";
    if (cc == chAttributes2)
      return "chAttributes2";
    if (cc == chRelations2)
      return "chRelations2";
    if (cc == chElements2)
      return "chElements2";
    return "<>";
    }

  public void itemStateChanged (ItemEvent evt) {
    // - System.out.println("StagePanel: "+evt.getSource());
    // //+" chRelations1="+chRelations1+" chAttributes1="+chAttributes1);
    if (evt.getSource() == chRelations1 || evt.getSource() == chAttributes1) {
      // não são eventos vazios
      if (!chRelations1.getSelectedKey().equals(Util.EMPTY_STRING)
          && !chAttributes1.getSelectedKey().equals(Util.EMPTY_STRING)) {
        if (multiple1()) {
          // - System.out.println("StagePanel: primeira condicao: "+evt);
          chElements1.setVisible(false); // esconde 'choice' para
          btnEscolhe1.setVisible(true); // mostra - seleção de vários elementos: "está em" ou "não está em"
        } else {
          // - System.out.println("StagePanel: primeira condicao: "+evt);
          chElements1.populate(ObjectManager.setMachine.getUniverso().getPredicados(chAttributes1.getSelectedKey()));
          chElements1.setVisible(true);
          btnEscolhe1.setVisible(false); // seleção de vários elementos: "está em" ou "não está em"
          }
        limpaListElementos1();
        }
      updateBtnOk();
      updateBtnVerify();
      }
    else
    if (evt.getSource() == chRelations2 || evt.getSource() == chAttributes2) {
      // - System.out.println("StagePanel: 2: "+evt+" -> "+chRelations2.getSelectedKey());
      if (!chRelations2.getSelectedKey().equals(Util.EMPTY_STRING)
          && !chAttributes2.getSelectedKey().equals(Util.EMPTY_STRING)) {
        if (multiple2()) {
          chElements2.setVisible(false);
          btnEscolhe2.setVisible(true); // seleção de vários elementos: "está em" ou "não está em"
        } else {
          chElements2.populate(ObjectManager.setMachine.getUniverso()
              .getPredicados(chAttributes2.getSelectedKey()));
          chElements2.setVisible(true);
          btnEscolhe2.setVisible(false); // seleção de vários elementos: "está em" ou "não está em"
          }
        limpaListElementos2();
        }
      updateBtnOk();
      updateBtnVerify();
      }
    else
    if (evt.getSource() == chFormula) {
      if (!chFormula.getSelectedKey().equals(Util.EMPTY_STRING)) {
        ObjectManager.setMachine.setFormulaAtual(chFormula.getSelectedKey()); // -------------------------------------------------------------------------

        inputFormulaMode();
        if (ObjectManager.setMachine.formulaAtualTemParametroN()) {
          chN.setVisible(true);
          lblN.setVisible(true);
        } else {
          chN.setVisible(false);
          lblN.setVisible(false);
          }
        if (ObjectManager.setMachine.formulaAtualTemParametroP()) {
          chP.setVisible(true);
          lblP.setVisible(true);
        } else {
          chP.setVisible(false);
          lblP.setVisible(false);
          }
      } else {
        chN.setVisible(false);
        lblN.setVisible(false);
        chP.setVisible(false);
        lblP.setVisible(false);
        }
      updateBtnOk();
      // updateBtnVerify();
      }
    else
    if (evt.getSource() == chN || evt.getSource() == chP) { // CustomChoice chN, CustomChoice chP
      // - System.out.println("StagePanel.itemStateChanged: 3: ("+chN+","+chN.getSelectedKey()+") - ("+chP+","+chP.getSelectedKey()+")");
      // ((CustomChoice)evt.getSource()).listCustomChoice();
      if (!chN.getSelectedKey().equals("chooseOption") || !chP.getSelectedKey().equals("chooseOption")) { //
         ObjectManager.setMachine.defineNeP(chN.getSelectedKey(), chP.getSelectedKey());
         // at icomb.machine.SetMachine.defineNeP(SetMachine.java:251)
         // at icomb.ui.StagePanel.itemStateChanged(StagePanel.java:621)
         // at java.awt.Choice.processItemEvent(Choice.java:623)
         }
      updateBtnOk();
      // updateBtnVerify();
      }
    else
    // :- linha 1: definido num. de propriedades
    if (evt.getSource() == chNumberOfProp) {
      // - System.out.println("StagePanel: 4: "+evt+" -> "+chNumberOfProp);
      // lblSpace.setVisible(true); // torna visível o Label com "e":
      // mostra termo "e" ate que defina num. de propriedades
      // lblAnd.setVisible(true); // torna visível o Label com "e": mostra

      // termo "e" ate que defina num. de propriedades
      lblExplain.setVisible(false); // nao mostra expl. sobre construcao
      // de propriedades ate que defina num. de propriedades
      lblAnd1.setVisible(false); // nao mostra termo "e" ate que defina
      // num. de propriedades (prop. 1)
      lblAnd2.setVisible(false); // nao mostra termo "e" ate que defina
      // num. de propriedades (prop. 2)
      lblAnd1.setVisible(false); // nao mostra termo "e" ate que defina - ate 25/03/2011 estava 'lblExplain.setVisible(true);'
      //System.out.println("StagePanel.itemStateChanged: 'lblAnd1' colocado false, e nao mais 'lblExplain' como 'true'");

      // num. de propriedades
      String value = chNumberOfProp.getSelectedKey();
      if (value.equals(Constants.NO_PROPERTY)) {
        lblExplain.setVisible(false); // nao mostra expl. sobre
        // construcao de propriedades ate que defina num. de propriedades
        lblAnd1.setVisible(false); // nao mostra termo "e" ate que
        // defina num. de propriedades (prop. 1)
        lblAnd2.setVisible(false); // nao mostra termo "e" ate que
        // defina num. de propriedades (prop. 2)
        chAttributes1.setVisible(false);
        chRelations1.setVisible(false);
        chElements1.setVisible(false);
        btnEscolhe1.setVisible(false); // seleção de vários elementos: "está em" ou "não está em"
        chAttributes2.setVisible(false);
        chRelations2.setVisible(false);
        chElements2.setVisible(false);
        btnEscolhe2.setVisible(false); // seleção de vários elementos: "está em" ou "não está em"
      } else if (value.equals(Constants.ONE_PROPERTY)) {
        lblExplain.setVisible(true); // mostra expl. sobre construcao de
        // propriedades ate que defina num. de propriedades
        lblAnd1.setVisible(true); // mostra termo "e" ate que defina
        // num. de propriedades (prop. 1)
        chAttributes1.setVisible(true);
        chRelations1.setVisible(true);
        boolean multiple1 = multiple1();
        chElements1.setVisible(!multiple1);
        btnEscolhe1.setVisible(multiple1); // seleção de vários
        // elementos: "está em" ou "não está em"
        chAttributes2.setVisible(false);
        chRelations2.setVisible(false);
        chElements2.setVisible(false);
      } else if (value.equals(Constants.TWO_PROPERTIES)) {
        lblExplain.setVisible(true); // mostra expl. sobre construcao de
        // propriedades ate que defina num. de propriedades
        lblAnd1.setVisible(true); // mostra termo "e" ate que defina
        // num. de propriedades (prop. 1)
        lblAnd2.setVisible(true); // mostra termo "e" ate que defina
        // num. de propriedades (prop. 2)
        chAttributes1.setVisible(true);
        chRelations1.setVisible(true);
        boolean multiple1 = multiple1();
        chElements1.setVisible(!multiple1);
        btnEscolhe1.setVisible(multiple1); // seleção de vários
        // elementos: "está em" ou "não está em"
        chAttributes2.setVisible(true);
        chRelations2.setVisible(true);
        boolean multiple2 = multiple2();
        chElements2.setVisible(!multiple2);
        btnEscolhe2.setVisible(multiple2); // seleção de vários
        // elementos: "está em" ou "não está em"
        }
      updateBtnOk();
      updateBtnVerify();
      // btnVerifyStage.setEnabled(true);
      } // if (evt.getSource() == chNumberOfProp)
    else
    if (evt.getSource() == chElements1 || evt.getSource() == chElements2
        || evt.getSource() == chNumberOfElements || evt.getSource() == chNumberOfProp) {
      updateBtnOk();
      updateBtnVerify();
      }

    validate();

    } // void itemStateChanged(ItemEvent evt)

  public void invisible () {
    lblAnd1.setVisible(false); // nao mostra termo "e" ate que defina num.
    // de propriedades
    lblAnd2.setVisible(false); // nao mostra termo "e" ate que defina num.
    // de propriedades
    lblExplain.setVisible(false); // nao mostra expl. sobre construcao de
    // propriedades ate que defina num. de propriedades
    chAttributes1.setVisible(false);
    chRelations1.setVisible(false);
    chElements1.setVisible(false);
    btnEscolhe1.setVisible(false); // seleção de vários elementos: "está em"
    // ou "não está em"
    chAttributes2.setVisible(false);
    chRelations2.setVisible(false);
    chElements2.setVisible(false);
    btnEscolhe2.setVisible(false); // seleção de vários elementos: "está em"
    // ou "não está em"
    }

  public Condicao criaCondicao (StringBuffer sb) {
    Condicao condicao = new Condicao(Integer.parseInt(chNumberOfElements.getSelectedKey()));
    // 123456789012345678
    sb.append(Constants.LOG_VERIFICA_ESTAGIO + Constants.LOG_DELIMITADOR);
    sb.append(Integer.parseInt(chNumberOfElements.getSelectedKey()));
    sb.append(" " + I18n.getString("elementsSuch") + " "); // elementos tais que
    if (chAttributes1.isVisible()) {
      String listElements = chElements1.getSelectedKey();
      if (!strListElements1.trim().equals(""))
        listElements = strListElements1;

      sb.append(I18n.getString(chAttributes1.getSelectedKey()) + " ");
      sb.append(I18n.getString(chRelations1.getSelectedKey()) + " ");
      sb.append(Util.translateSetString(listElements) + " ");

      boolean quero = chRelations1.getSelectedKey().equals(Constants.IS_IN) || chRelations1.getSelectedKey().equals(Constants.IS);

      Expressao expressao = new Expressao(chAttributes1.getSelectedKey(), quero, Util.produceStringSet(listElements));

      condicao.addExpressao(expressao);
      }
    if (chAttributes2.isVisible()) {
      String listElements = chElements2.getSelectedKey();
      if (!strListElements2.trim().equals(""))
        listElements = strListElements2;
      sb.append(" "+I18n.getString("msgE")+" "); // " e "
      sb.append(I18n.getString(chAttributes2.getSelectedKey()) + " ");
      sb.append(I18n.getString(chRelations2.getSelectedKey()) + " ");
      sb.append(Util.translateSetString(listElements) + " ");
      boolean quero = chRelations2.getSelectedKey().equals(Constants.IS_IN) || chRelations2.getSelectedKey().equals(Constants.IS);
      Expressao expressao = new Expressao(chAttributes2.getSelectedKey(), quero, Util.produceStringSet(listElements));
      condicao.addExpressao(expressao);
      }
    return condicao;
    } // Condicao criaCondicao(StringBuffer sb)

  // Click in 'btnVerifyStage' button - if it is OK => enable the selection for definition of stage cardinality
  public void verifyStage () {
    StringBuffer sb = new StringBuffer();
    try {
      Condicao condicao = criaCondicao(sb);
      //T System.out.println("\nStagePanel.java: verifyStage(): condicao="+condicao);
      // src/icomb/erro/Avaliador.java: 'Element[] elementos' e 'Condicao[] condicoes'
      ObjectManager.setMachine.verificaErro(condicao); // icomb.machine.SetMachine
      ObjectManager.getInstance().registraAcao(sb);
    } catch (RuntimeException e) { // cames from: 'Avaliador.java'
      String strError = e.getMessage();
      if (strError==null) { // no one throws this message => could be from 'icomb.erro.Avaliador.adicionaCondicao(...)'
         // the element is not found in any exercise-model condition - since condition describe all possibility => solution does not works
         // evaluationMessage02=Nenhum elemento do conjunto universo atende as restriï¿½ï¿½es informadas. Cada estï¿½gio criado deverï¿½ selecionar ao menos 1 elemento.
         System.err.println("StagePanel.verifyStage(...): Error: condition not found\n"+I18n.getString("evaluationMessage02"));
         strError = I18n.getString("evaluationMessage02");
         }

      sb.append(Constants.LOG_ERRO);
      sb.append(strError);
      MessageFrame.showMessage(strError, painelConstrucao);
      ObjectManager.getInstance().registraAcao(sb);
      System.err.println("Erro: em painel de estagios, ao verificar o estagio: " + e + ": " + strError);
      // e.printStackTrace();
      return;
      }

    // Panel: [Verify Stage] <Formula <choose> [Ok]
    btnVerifyStage.setEnabled(false); // Button [Verify Stage]
    lblFormula.setVisible(true); // Label <Formula>
    chFormula.setVisible(true); // CustomChoice <choose>
    btnOk.setVisible(true);  // Button [Ok]
    btnOk.setEnabled(false); //

    btnAddStage.setVisible(false);  //2011_07_07
    // System.out.println("StagePanel.verifyStage(): add btnAddStage");

    // Temporario: dim. botao esta sendo alterada, entao forcar tamanho
    // original: 132 x 23
    // System.out.println("StagePanel: verificar estagio "+btnVerifyStage.getWidth()+","+btnVerifyStage.getHeight());
    // btnVerifyStage.setSize(132,23); btnVerifyStage.repaint(); <- tb sem efeito

    // panelFormula has 'panelFormulaEdit' or 'panelFormulaCancel'
    panelFormula.setVisible(true); // with 'Panel panelFormulaEdit' - with 'Label lblFormula, CustomChoice chFormula, Label lblN, CustomChoice chP, Button btnOk
    panelFormulaEdit.setVisible(true);
    panelFormulaCancel.setVisible(false); //2011_07_07
    // panelFormulaCancel.setVisible(true); //2011_07_07
    panelFormula.add(panelFormulaEdit); //2011_07_07
    panelFormula.remove(panelFormulaCancel); //2011_07_07
    // panelFormula.setVisible(true);

    } // void verifyStage()

  // Acerto para adicionar novo estagio esta em 'NewStageButtonPanel'
  // 
  public void actionPerformed (ActionEvent evt) {
    // Panel: [button/choice1] <button/choice2> - means "button/choice1" is disabled and "button/choice2" is enabled

    // if (evt.getSource() == btnEraseStage) {
    // StringBuffer sb = new StringBuffer();
    // sb.append(Constants.LOG_APAGA_ESTAGIO + Constants.LOG_DELIMITADOR);
    // ObjectManager.getInstance().registraAcao(sb);
    // ObjectManager.setMachine.apagarEstagio();
    // close();
    // painelConstrucao.addStagePanel();
    // }
    // else
    if (evt.getSource() == btnVerifyStage) {
      // Panel: [Verify Stage] <Formula <choose> [Ok]

      verifyStage();
      //System.out.println("StagePanel.verifyStage(): 1: dim. panelFormula "+panelFormula.getWidth()+"");

    } else if (evt.getSource() == btnOk) {
      // Panel: [Verify Stage] <<N> <Cancel>> <Add Stage>

      lblCancel.setBackground(Configuracoes.CINZA_ESCURO); // cor de fundo da "formula" da resposta
      lblCancel.setText(getFormula());
      btnAddStage.setEnabled(true);
      panelFormulaEdit.setVisible(false);
      panelFormulaCancel.setVisible(true);
      panelFormula.remove(panelFormulaEdit); //2011_07_07
      panelFormula.add(panelFormulaCancel);  // Panel formulae: <value> <Cancel> - panelFormulaCancel: lblCancel; btnCancel
      btnAddStage.setVisible(true); //2011_07_07
      //System.out.println("StagePanel.verifyStage(): 2: adiciona btnAddStage - dim. panelFormula "+panelFormula.getWidth()+"");
    } else if (evt.getSource() == btnCancelFormula) {

      inputFormulaMode();
      //
System.out.println("StagePanel.verifyStage(): 3: dim. panelFormula "+panelFormula.getWidth()+"");

    } else if (evt.getSource() == btnAddStage) {
      // Panel: [Verify Stage] [Add Stage]

      addStage(); // add this stage
      //System.out.println("StagePanel.verifyStage(): 4: dim. panelFormula "+panelFormula.getWidth()+"");

    } else if (evt.getSource() == btnEscolhe1) { // seleção de vários
      // elementos: "está em" ou "não está em"

      openChoosenFrame(ObjectManager.setMachine.getUniverso().getPredicados(chAttributes1.getSelectedKey()), 1);
      //System.out.println("StagePanel.verifyStage(): 5: dim. panelFormula "+panelFormula.getWidth()+"");

    } else if (evt.getSource() == btnEscolhe2) { // seleção de vários
      // elementos: "está em" ou "não está em"

      openChoosenFrame(ObjectManager.setMachine.getUniverso().getPredicados(chAttributes2.getSelectedKey()), 2);
      //System.out.println("StagePanel.verifyStage(): 6: dim. panelFormula "+panelFormula.getWidth()+"");

      }
    validate();
    } // void actionPerformed(ActionEvent evt)

  private String getFormula () {
    String result = "";
    if (ObjectManager.setMachine.estagioAtualEstaDefinido()
        && ObjectManager.setMachine.formulaAtualEstaDefinida()) {
      result = ObjectManager.setMachine.getNomeFormulaAtual();
      if (result.equals(Constants.VALUE))
        result = "" + ObjectManager.setMachine.getEstagioAtualN();
      else {
        result = result.replaceAll("n", "" + ObjectManager.setMachine.getEstagioAtualN());
        result = result.replaceAll("p", "" + ObjectManager.setMachine.getEstagioAtualP());
        }
      }
    return result;
    }

  public void updateBtnOk () {
    if (chN != null
        && (!chN.isVisible() || chN.isVisible() && !chN.getSelectedKey().equals(Util.EMPTY_STRING))
        && chP != null
        && (!chP.isVisible() || chP.isVisible() && !chP.getSelectedKey().equals(Util.EMPTY_STRING))) {
      if (chN.isVisible() || chP.isVisible())
        btnOk.setEnabled(true);
      else
        btnOk.setEnabled(false);
    } else {
      btnOk.setEnabled(false);
      }
    }

  // Hashtable to manage items of choices
  private Hashtable getMap () {
    Hashtable map = new Hashtable();

    map.put("cboNElementos", chNumberOfElements.getSelectedKey());
    map.put("cboPropOptions", chNumberOfProp.getSelectedKey());
    // System.out.println("StagePanel.getMap(): chAttributes1="+chAttributes1);
    if (chAttributes1 != null)
      map.put("cboAttributes1", chAttributes1.getSelectedKey());
    if (chRelations1 != null)
      map.put("cboRelations1", chRelations1.getSelectedKey());
    if (!strListElements1.equals(""))
      map.put("cboElements1", strListElements1);
    else if (chElements1 != null && chElements1.getSelectedKey() != null)
      map.put("cboElements1", chElements1.getSelectedKey());
    try {
      if (chAttributes2 != null)
        map.put("cboAttributes2", chAttributes2.getSelectedKey());
    } catch (Exception e) {
      System.out.println("Erro: StagePanel.getMap(): chAttributes2: " + e.toString());
      e.printStackTrace();
      e.printStackTrace();
      }
    if (chRelations2 != null)
      map.put("cboRelations2", chRelations2.getSelectedKey());
    try {
      if (!strListElements2.equals(""))
        map.put("cboElements2", strListElements2);
      else if (chElements2 != null && chElements2.getSelectedKey() != null)
        map.put("cboElements2", chElements2.getSelectedKey());
    } catch (Exception e) {
      System.err.println("Erro: StagePanel.getMap(): strListElements2=" + strListElements2 + ": " + e.toString());
      e.printStackTrace();
      }

    return map;
    }

  public void conditionSetted () {
    Hashtable map = getMap();
    ObjectManager.setMachine.alimentaEstagioAuxiliar(map);
    btnVerifyStage.setEnabled(true);
    painelConstrucao.redrawPanel();
    }

  public void updateBtnVerify () {
    //E Para isolar o erro de 'loop infinito' com botão de número de proprieadades
    //E if (cont==1) { System.out.println("StagePanel: "+testChoice(evt));
    //E try{String str="";System.out.println(str.charAt(3));}catch(Exception  e){e.printStackTrace();}  }
    //E if (cont<10) System.out.println("StagePanel: "+cont+": "+testChoice(evt)); //
    //E cont++;
    if (chNumberOfElements.isVisible()
        && !chNumberOfElements.getSelectedKey().equals(Util.EMPTY_STRING) && chNumberOfProp.isVisible()
        && !chNumberOfProp.getSelectedKey().equals(Util.EMPTY_STRING)) {
      String value = chNumberOfProp.getSelectedKey();
      if (value.equals(Constants.NO_PROPERTY)) {
        conditionSetted();
        return;
      } else if (value.equals(Constants.ONE_PROPERTY)) {
        if (preenchido1()) {
          conditionSetted();
          return;
          }
      } else if (value.equals(Constants.TWO_PROPERTIES)) {
        if (preenchido1() && preenchido2()) {
          conditionSetted();
          return;
          }
        }
      }
    cleanFormula(); // - ERRO: recorrência, 'cleanFormula()' faz vários
    // 'chP.select(Util.EMPTY_STRING);' que
    // dispara 'itemStateChanged(ItemEvent)', que por sua vez chama
    // novamente este método!!
    btnVerifyStage.setEnabled(false);
    }

  private boolean preenchido1 () {
    // System.out.println("StagePanel.preenchido1(): chAttributes1="+chAttributes1);
    // System.out.println("StagePanel.preenchido1(): "+chAttributes1.getForeground()+", "+chAttributes1.getBackground());
    try {
      return chAttributes1.isVisible() && !chAttributes1.getSelectedKey().equals(Util.EMPTY_STRING)
             && chRelations1.isVisible() && !chRelations1.getSelectedKey().equals(Util.EMPTY_STRING)
             && (chElements1.isVisible() && !chElements1.getSelectedKey().equals(Util.EMPTY_STRING)
             && !chElements1.getSelectedKey().equals("") || (!lblListElements1.getText().equals("") && !lblListElements1.getText().equals("{}")));
    } catch (Exception e) {
      e.printStackTrace();
      return false;
      }
    }

  private boolean preenchido2 () {
    return chAttributes2.isVisible() && !chAttributes2.getSelectedKey().equals(Util.EMPTY_STRING)
           && chRelations2.isVisible() && !chRelations2.getSelectedKey().equals(Util.EMPTY_STRING)
           && (chElements2.isVisible() && !chElements2.getSelectedKey().equals(Util.EMPTY_STRING)
            && !chElements2.getSelectedKey().equals("") || (!lblListElements2.getText().equals("") && !lblListElements2.getText().equals("{}")));
    }

  public void close () {
    String strErr = "";
    try {
      lblIWant.setVisible(false);
    } catch (Exception e) {
      strErr += "close 1: " + e.toString();
      }
    try {
      lblElementsVerifying.setVisible(false);
    } catch (Exception e) {
      strErr += "close 2: " + e.toString();
      }
    try {
      // btnEraseStage.setEnabled(false);
    } catch (Exception e) {
      strErr += "close 1: " + e.toString();
      }
    try {
      chNumberOfElements.select(Util.EMPTY_STRING);
      chNumberOfElements.setVisible(false);
    } catch (Exception e) {
      strErr += "close 1: " + e.toString();
      }
    try {
      chNumberOfProp.setVisible(false);
      chNumberOfProp.select(Util.EMPTY_STRING);
    } catch (Exception e) {
      strErr += "close 1: " + e.toString();
      }
    try {
      btnAddStage.setEnabled(false);
      btnVerifyStage.setEnabled(false);
    } catch (Exception e) {
      strErr += "close 1: " + e.toString();
      }
    if (strErr != "")
      System.err.println("Erro: " + strErr);

    panelFormula.setVisible(false);
    invisible();
    chAttributes1.select("");
    chElements1.populate(new Vector());
    chRelations1.select(Util.EMPTY_STRING);
    chRelations2.select(Util.EMPTY_STRING);
    chAttributes2.select("");
    chElements2.populate(new Vector());
    chFormula.select(Util.EMPTY_STRING);
    chP.select(Util.EMPTY_STRING);
    chN.select(Util.EMPTY_STRING);
    // System.out.println("StagePanel: close");

    lblListElements1.setText("");
    lblListElements2.setText("");
    strListElements1 = "";
    strListElements2 = "";
    } // void close()

  public void cleanFormula () {
    chFormula.select(Util.EMPTY_STRING);
    chP.select(Util.EMPTY_STRING);
    chN.select(Util.EMPTY_STRING);
    chP.setVisible(false);
    chN.setVisible(false);
    lblN.setVisible(false);
    lblP.setVisible(false);
    btnOk.setVisible(false);
    panelFormula.setVisible(false);
    }

  public void open () {
    lblIWant.setVisible(true);
    lblElementsVerifying.setVisible(true);
    // btnEraseStage.setEnabled(true);
    chNumberOfElements.setVisible(true);
    chNumberOfProp.setVisible(true);
    panelFormula.setVisible(false);
    invisible();
    }

  private void inputFormulaMode () {
    btnAddStage.setEnabled(false);
    panelFormulaEdit.setVisible(true);
    panelFormulaCancel.setVisible(false);
    panelFormula.add(panelFormulaEdit);
    }

  // Add one stage
  // Finish this stage: this triggers the registration of a text information about the stage in 'icomb.ui.ListPanel'
  public void addStage () {
    try {
      Hashtable map = getMap(); // get all elements and conditions of this new stage

      ObjectManager.setMachine.alteraEstagioAtual(map); // makes: 'SetMachine: Estagio estagioAtual' get 'estagioAtual.alimentaEstagio(map)'
      ObjectManager.setMachine.validaEstagioAtual();

      painelConstrucao.addStagePanel(); // add information about the finished stage - ConstructionPanel painelConstrucao
      close();
      StringBuffer sb = new StringBuffer();

      sb.append(Constants.LOG_ADICIONA_ESTAGIO + "(" + ObjectManager.setMachine.getEstagioAtualId() + ")"
        + Constants.LOG_DELIMITADOR + " " + ObjectManager.setMachine.getEstagioAtualTextoFormula());

// como imprimir algo legivel? onde encontrar o texto...?
System.out.println("StagePanel.addStage(): "+sb);


      ObjectManager.getInstance().registraAcao(sb);
    } catch (RuntimeException e) {
      StringBuffer sb = new StringBuffer();
      sb.append(Constants.LOG_ADICIONA_ESTAGIO + Constants.LOG_DELIMITADOR + " "
        + ObjectManager.setMachine.getEstagioAtualTextoFormula() + Constants.LOG_ERRO + e.getMessage());
      ObjectManager.getInstance().registraAcao(sb);
      MessageFrame.showMessage(e.getMessage(), painelConstrucao);
      }
    }

  public void openChoosenFrame (Vector model, int conditionNumber) {
    listFrame.removeListeners();
    listFrame.changeModel(model);
    if (conditionNumber == 1) {
      listFrame.mark(strListElements1);
      listFrame.addListFrameListener(new IListFrameListener() {
        public void setStringList(String strList) {
          strListElements1 = strList;
          lblListElements1.setText(Util.translateSetString(strList));
          updateBtnVerify();
          validate();
          }
      });
    } else if (conditionNumber == 2) {
      listFrame.mark(strListElements2);
      listFrame.addListFrameListener(new IListFrameListener() {
        public void setStringList(String strList) {
          strListElements2 = strList;
          lblListElements2.setText(Util.translateSetString(strList));
          updateBtnVerify();
          validate();
          }
      });
      }

    listFrame.setLocationRelativeTo(this);
    listFrame.setVisible(true);

    }

  }
