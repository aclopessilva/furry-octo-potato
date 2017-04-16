/*
 * iMath project
 * iComb: http://www.matematica.br/icomb
 * 
 * @author AEK, LOB
 * @description List of elements of the universe set
 * 
 */

package icomb.util;

import java.util.Vector;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

  public static final String EMPTY_STRING = "chooseOption"; // "emptyString" - chooseOption=Choose one option
  public static final String THREE_DOTS = "threeDots";

  // Used in: 'icomb.ui.DefinedUniversePanel.itemStateChanged(DefinedUniversePanel.java:135)'
  public static final String univSet1 = "baralho/baralho.xml"; // universeDeck
  public static final String univSet2 = "futebol/futebol.xml"; // universeFoot
  public static final String univNameSet1 = I18n.getString("universeDeck"); // Universe: card deck - file "baralho/baralho.xml"
  public static final String univNameSet2 = I18n.getString("universeFoot"); // Universe: football teams - file "futebol/futebol.xml"

  public static String getUserSystemData () {
    String dados_str = "[ "; // se for applet dar� erro de "sun.applet.AppletSecurityException: checkpropsaccess.key"
    String strU = "", str1 = "", str2 = "", str3 = "";
    if (!base.MontaFace.staticApplet) {
      try {
        strU = "; " + System.getProperty("user.name");
      } catch (java.lang.Exception e) { }
      try {
        str1 = System.getProperty("user.dir")+"; ";
      } catch (java.lang.Exception e) { }
      try {
        str2 = System.getProperty("user.home")+"; ";
      } catch (java.lang.Exception e) { }
      try {
        str3 = System.getProperty("java.home")+"; ";
      } catch (java.lang.Exception e) { }
      if (str1!="" || str2!="" || str3!="") {
        str1  = "[ " + str1 + str2 + str3 + " ]\n";
        }
      }
    else {
      java.net.InetAddress inetaddress; // se quiser pegar tamb�m a m�quina de quem gravou  
      try {
        inetaddress = java.net.InetAddress.getLocalHost(); 
      } catch (java.net.UnknownHostException unknownhostexception) { inetaddress = null; }
      }
    try {
      dados_str = java.text.DateFormat.getDateTimeInstance().format(new java.util.Date(System.currentTimeMillis()));   
      dados_str += strU +  " ]\n"; // + "@" + inetaddress);
    } catch (java.lang.Exception e) {
      dados_str += " ]\n";
      //System.out.println("GL: "+e);
      }
    return dados_str + str1;
    } // static String dadosUsuario()

  // Auxiliary to debug
  public static String listStrArray (String [] vec, boolean msg) {
    String str = "< ";
    int tam = vec==null ? 0 : vec.length;
    if (msg)
      System.out.println("List string array: #vec="+tam);
    if (vec==null) return "<>";
    for (int i=0; i<tam; i++) {
      if (msg)
        System.out.println(" "+i+": "+vec[i]);
      str += vec[i];
      if (i<tam-1)  str += ", ";
      }
    return str + " >";
    }

  public static void listVectorComp (Vector vec) {
    int tam = vec==null ? 0 : vec.size();
    System.out.println("List vector: #vec="+tam);
    if (vec==null) return;
    for (int i=0; i<tam; i++)
      System.out.println(" "+i+": "+vec.elementAt(0));
    }

  public static String listVector (Vector vec) {
    if (vec==null || vec.size()==0)
       return "<>";
    String str = "", aux = "";
    for (int i=0; i<vec.size(); i++) {
       str += aux+vec.elementAt(i).toString();
       aux = ", ";
       }
    return str;
    }

  // From: 'src/icomb/ui/DefinedUniversePanel.java' in 'choiceUniverses = ObjectManager.criaCombo(panelButtons, Util.geUniversetList(), "changeUniverse")'
  public static Vector geUniversetList () {
    Vector vet_universes = new Vector();
    vet_universes.add(EMPTY_STRING); // "chooseOption"
    vet_universes.add(univNameSet1); // card deck set universe - "baralho/baralho.xml"
    vet_universes.add(univNameSet2); // football teams set universe - "futebol/futebol.xml"
    return vet_universes;
    }

  public static Vector getListNumbers (int to) {
    Vector vet_numbers = new Vector();
    vet_numbers.add(EMPTY_STRING); // "chooseOption"
    for (int i=1; i<=to; i++) {
      vet_numbers.add(""+i);
      }
    return vet_numbers;
    }

  public static Vector getListPropertyOptions () {
    Vector options = new Vector();
    options.add(EMPTY_STRING); // "chooseOption"
    options.add(Constants.NO_PROPERTY);
    options.add(Constants.ONE_PROPERTY);
    options.add(Constants.TWO_PROPERTIES);
    return options;
    }

  public static Vector getListRelationOptions () {
    Vector options = new Vector();
    options.add(EMPTY_STRING); // "chooseOption"
    options.add(Constants.IS);
    options.add(Constants.IS_NOT);
    options.add(Constants.IS_IN);
    options.add(Constants.IS_NOT_IN);
    return options;
    }

  // From: src/icomb/ui/ListPanel.java - constructor
  // Final step to "close" a solution: how to compose all the stages (add or multiply)?
  public static Vector getListActions () {
    Vector options = new Vector();
    options.add(EMPTY_STRING); // "chooseOption"
    options.add(Constants.SOMA);
    options.add(Constants.MULTIPLICA);
    return options;
    }

  public static Vector getListFormula () {
    Vector options = new Vector();
    options.add(EMPTY_STRING); // "chooseOption"
    options.add(Constants.VALUE);
    options.add("C(n,p)");
    options.add("A(n,p)");
    options.add("n!");
    options.add("n^p");
    return options;
    }

  public static StringBuffer buildCombo (String name,List list,String selected,int width) {
    return buildCombo(name,list,selected,width,null);
    }

  public static StringBuffer buildCombo (String name,List list,String selected,int width,String onchange) {
    return buildCombo(name,list,selected,width,onchange,false);
    }

  public static StringBuffer buildCombo (String name, List list, String selected, int width, String onchange, boolean multiple) {
    StringBuffer sb = new StringBuffer();
    sb.append("<select "+ (multiple?"multiple size=3 ":" ")+(onchange!=null?"onchange="+onchange:"")+"style=\"width:" + width + "\" class=\"sonsSelect\" NAME=\"" + name + "\" >\n");
    sb.append("<option></option>\n");
    for (int i=0;i<list.size();i++) {
        String ite = ""+list.get(i);
        if (belong(ite,selected))
           sb.append("<option value=\"" + ite + "\" selected >" + I18n.getString(ite) + "</option>\n");
        else
          sb.append("<option value=\"" + ite + "\" >" + I18n.getString(ite) + "</option>\n");
        }
    sb.append("</select>\n");
    return sb;
    }


  public static StringBuffer horizontalLine () {
    return new StringBuffer("<tr><td></td><td colspan=\"4\"><img src=\"img/vas_sons_fioEndTable.gif\" alt=\"\" border=\"0\" /></td><td></td></tr>\n");
    }

  public static StringBuffer horizontalLine2 () {
    return new StringBuffer("<tr><td><img src=\"img/vas_sons_fioEndTable.gif\" alt=\"\" border=\"0\" /></td></tr>\n");
    }

  public static String builtButton (String name, String href) {
    return builtButton(name, href,true);
    }

  public static String builtButton (String name, String href, boolean enabled) {
    StringBuffer sb = new StringBuffer();
    if (enabled)
      sb.append("<a class=arial11Blue href=\"" + href + "\" class=\"btn pink\">" + name + "</a>\n");
    else
      sb.append("<span class=arial11White >" + name + "</span>\n");
    return sb.toString();
    }

  public static String parseCombo (Object o) {
    if (o instanceof String)
      return (String) o;
    else if (o instanceof String[]) {
      String[] array = (String[]) o;
      StringBuffer sb = new StringBuffer("{");
      for (int i=0 ; i < array.length ; i++) {
         sb.append(array[i]);
         if (i < array.length-1)
            sb.append(",");
         }
      sb.append("}");
      return sb.toString();
      }
    else
      return null;
    }

  public static Set produceStringSet (String source) {
    Set ret = new HashSet();
    Pattern pattern = Pattern.compile("[^{}, ]+");
    Matcher matcher = pattern.matcher(source);
    while (matcher.find()) {
      String token = matcher.group();
      ret.add(token);
      }
    return ret;
    }

  public static String translateSetString (String source) {
    Pattern pattern = Pattern.compile("[^{}, ]+");
    Matcher matcher = pattern.matcher(source);
    StringBuffer sb = new StringBuffer("");
    boolean primeiro=true;
    while (matcher.find()) {
      if (!primeiro)
         sb.append(",");
      primeiro=false;
      String token = matcher.group();
      sb.append(I18n.getString(token));
      }
    if (source.startsWith("{") && source.endsWith("}"))
       return "{"+sb.toString()+"}";
    else
       return  sb.toString();
    }

  public static boolean belong (String key, String set) {
    if (set == null)
     return false;
    if (set.equals(key))
     return true;
    if (("{" + key + "}").equals(set))
     return true;
    if (set.indexOf("," + key + "}")>=0)
     return true;
    if (set.indexOf("," + key + ",")>=0)
     return true;
    if (set.indexOf("{" + key + ",")>=0)
     return true;
    return false;
    }

  public static int [] permutation (int n) {
    int [] ret = new int[n];
    for (int i=0 ; i<n;i++) {
     ret[i] = i;
     }
    for (int i=0 ; i<n;i++) {
     int j = (int)(Math.random()*n);
     int aux = ret[j];
     ret[j] = ret[i];
     ret[i]=aux;
     }
    return ret;
    }

  public static String translateToGifName (String n, String v) {
    String naipe="";
    String valor="";
    if (v.equalsIgnoreCase(Constants.ACE))
      valor="a";
    else if (v.equalsIgnoreCase(Constants.TEN))
      valor = "t";
    else if (v.equalsIgnoreCase(Constants.JACK))
      valor = "j";
    else if (v.equalsIgnoreCase(Constants.QUEEN))
      valor = "q";
    else if (v.equalsIgnoreCase(Constants.KING))
      valor = "k";
    else
      valor = v.toLowerCase();

    if (n.equalsIgnoreCase(Constants.DIAMONDS)) naipe = "d";
    else if (n.equalsIgnoreCase(Constants.SPADES)) naipe = "s";
    else if (n.equalsIgnoreCase(Constants.HEARTS)) naipe="h";
    else naipe="c";

    return (valor+naipe);
    }

 // import icomb.util.I18n;
 // I18n.getString(String)

 /* if using Java Swing use bellow instead of method bellow
 // Create a Dialog to tests
 public static void createDialog (String strTitle, javax.swing.JFrame jFrame, String strMessage) {
   final javax.swing.JDialog jdialog = new javax.swing.JDialog(jFrame,strTitle); // accessed from itself (java.awt.event.WindowAdapter)
   final javax.swing.JOptionPane jPanel = new javax.swing.JOptionPane(); // accessed from itself (java.beans.PropertyChangeListener)
   javax.swing.JScrollPane jScrollPane = new javax.swing.JScrollPane();
   javax.swing.JTextArea jText = new javax.swing.JTextArea(30,150); jText.setText(strMessage);
   jScrollPane.setViewportView(jText);
   jScrollPane.setAutoscrolls(true); //getPreferredScrollableViewportSize()
   jPanel.setMessage(icomb.util.I18n.getString("iLM_debug")); // interactive Learning Module: debug help for iAssing
   jPanel.add(jScrollPane);
   jPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
             public void propertyChange(java.beans.PropertyChangeEvent e) {
               String prop = e.getPropertyName();
               // System.out.println("\n***StrUtilities.createDialog(...): "+prop);
               if (jdialog.isVisible() && (e.getSource() == jPanel) && (prop.equals(javax.swing.JOptionPane.VALUE_PROPERTY))) {
                  //If you were going to check something before closing the window, you'd do it here.
                  jdialog.setVisible(false);
                  jdialog.dispose();
                  }
               }
            });
   // int value = ((Integer)optionPane.getValue()).intValue();
   // if (value == javax.swing.JOptionPane.YES_OPTION) { setLabel("Good."); }
   // javax.swing.JOptionPane jOption = new javax.swing.JOptionPane.showMessageDialog(jFrame, strMessage, "Inane warning", javax.swing.JOptionPane.WARNING_MESSAGE);
 
   jdialog.setDefaultCloseOperation(javax.swing.JDialog.DO_NOTHING_ON_CLOSE);
   jdialog.setContentPane(jPanel); jdialog.setSize(398,498);
   jText.setEditable(true);
   jdialog.setSize(400,500);
   jdialog.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowClosing(java.awt.event.WindowEvent we) {
         System.out.println("StrUtilities.createDialog(): closing event "+we); // ContentPane.OK_OPTION
         jdialog.dispose();
         // setLabel("Thwarted user attempt to close window.");
         }
       });
 
   jdialog.setVisible(true);
   }
   */

 // Create a Dialog to test: in applet mode it can show the content file for check its consistence
 public static void createDialog (String strTitle, java.awt.Frame jFrame, String strMessage) {
   final java.awt.Dialog jdialog = new java.awt.Dialog(jFrame,strTitle); // accessed from itself (java.awt.event.WindowAdapter)
   final java.awt.Panel jPanel = new java.awt.Panel(); // accessed from itself (java.beans.PropertyChangeListener)
   // final java.awt.ScrollPane jScrollPane = new java.awt.ScrollPane();
   java.awt.TextArea jText = new java.awt.TextArea(30,150); jText.setText(strMessage);
   java.awt.Label label = new java.awt.Label(icomb.util.I18n.getString("iLM_debug"));
   label.setForeground(java.awt.Color.white);
   // label.setSize(398,50);
   jText.setEditable(true);
   jPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
             public void propertyChange(java.beans.PropertyChangeEvent e) {
               String prop = e.getPropertyName();
               // System.out.println("\n***StrUtilities.createDialog(...): "+prop);
               if (jdialog.isVisible() && (e.getSource() == jPanel)) { //  && (prop.equals(java.awt.OptionPane.VALUE_PROPERTY))
                  //If you were going to check something before closing the window, you'd do it here.
                  jdialog.setVisible(false);
                  jdialog.dispose();
                  }
               }
            });
   jPanel.setLayout(new java.awt.BorderLayout(2,1)); // (new java.awt.GridLayout(2,1));
   jPanel.add("North",label); // interactive Learning Module: debug help for iAssing
   jPanel.add("Center",jText);
   jdialog.add(jPanel);

   jdialog.setBackground(Configuracoes.DARK_BLUE2BG); // 0, 32, 64 = HTML 00 20 40

   // int value = ((Integer)optionPane.getValue()).intValue();
   // if (value == java.awt.OptionPane.YES_OPTION) { setLabel("Good."); }
   // java.awt.OptionPane jOption = new java.awt.OptionPane.showMessageDialog(jFrame, strMessage, "Inane warning", java.awt.OptionPane.WARNING_MESSAGE);
 
   jdialog.setSize(398,498);
   jdialog.setSize(400,500);
   jdialog.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowClosing(java.awt.event.WindowEvent we) {
         System.out.println("StrUtilities.createDialog(): closing event "+we); // ContentPane.OK_OPTION
         jdialog.dispose();
         // setLabel("Thwarted user attempt to close window.");
         }
       });
 
   jdialog.setVisible(true);
   }

 // -: File utilities - start :-

 // Store the iComb exercise in a file named 'fName'
 public static void storeFile (String fName, String strContent, String msgTracking) {
   try {
     // para nao dar problema de acento ISO -> UTF...
     // java -Dfile.encoding=iso-8859-1 igeom.IGeom s2.geo
     java.io.FileOutputStream fos = new java.io.FileOutputStream(fName);
     java.io.Writer out = new java.io.OutputStreamWriter(fos, "ISO8859_1"); // OutputStreamWriter(fos, "UTF8");
     out.write(strContent);
     out.close();
   } catch (Exception e) {
     System.err.println("Erro: ao tentar escrever arquivo "+fName+" ("+msgTracking+"): "+e); // e.printStackTrace();
     }
   }

 // -: File utilities - ends :-

 }
