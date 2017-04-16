/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author Alexandre Eisenmann, Leônidas de Oliveira Brandão
 * @version Version 0.8.9 in 08/08/2013 - all files in a single JAR (iComb.jar includes all universes)
 *          First stable version in 30/04/2008
 *
 * @description Para tratamento multi-linguas
 *
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

// Singleton for Internacionalization
public class I18n  {

  private static I18n instance = null;
  
  private static ResourceBundle bundle;

  private static String lingua = null, pais = null; // para configurar lingua em chamado do iComb: java icomb.IComb lang=pt

  // Decompõe: 'lang=pt_BR' em String("pt","BR")
  private static boolean decompoeConfig (String str) {
    // System.out.println("[B.decompoeConfig] inicio: str="+str);
    //try {String str1=""; System.err.println(str1.charAt(3)); } catch(Exception e) {e.printStackTrace();}
    if (str==null)
       return false;
    StringTokenizer tokens = new StringTokenizer(str,"=");
    String item;
    int tam_item;

    //- System.out.println("[RR.decompoeConfig] #tokens="+tokens.countTokens());
    if (tokens.hasMoreTokens()) {
       item = tokens.nextToken();
       /*//-*/ System.out.println("[RR.decompoeConfig] item="+item);
       if (item==null) return false;
       if (item.equals("lang") && tokens.hasMoreTokens()) {
          // pegou 'pt_BR'
          item = tokens.nextToken();
          tam_item = item.length();
          ///*//-*/ System.out.println("[RR.decompoeConfig] item="+item+" #item="+tam_item);
          if (tam_item>2) {
             // é da forma: 'pt_BR'
             lingua = item.substring(0,2); //
             if (tam_item>4)
                pais = item.substring(3,tam_item).toUpperCase(); //
             ///*//-*/ System.out.println("[RR.decompoeConfig] lingua="+lingua+" pais="+pais);
             return true;
             }
          else {
             // é da forma: 'pt'
             lingua = item.substring(0,2); //
             // System.out.println("[B.decompoeConfig] lingua="+lingua+" [pais="+pais+"]");
             return true;
             }
          }
       else
       if (item.equals("bg") && tokens.hasMoreTokens()) {
          // pegou 'pt_BR'
          item = tokens.nextToken();
          if (item!=null && item.equals("contrast1")) // Bundle.msg("contraste")
             ; // para colocar em modo contraste
          }
       else { // problema: veio 'lang='
          return false; // new String[2];
          }
       }
    return false; // new String[2];
    } // boolean decompoeConfig(String str)

  // iComb aplicativo: define lingua, tem prioridade sobre outros métodos
  //                   java -jar iGeom.jar icomb.IComb lang=es
  // Arquivo: 'igeom.lang' define a lingua (conteúdo: "lang=pt", "lang=en" ou "lang=es")
  // Parâm. : 'lingua', 'pais' ou 'lang' (nesta ordem) -> param name='lang' value="pt" ou "en" ou "es" (default: "pt")
  public static void setConfig (String [] args) {
    // lang=pt; lang=en; lang=es
    int i = -1;
    if (lingua==null || lingua=="") // evita sobrescrever definicao de 'igeom.cfg'
       lingua = "pt"; // default
    pais = "BR";
    if (args!=null && args.length>0) {
       String item;
       ///*//-*/ System.out.println("[RR.setConfig] args="+icomb.util.Util.listStrArray(args,false));
       for (i=0; i<args.length; i++) {
           // tokens = new StringTokenizer(" ",args[i]);
           item = args[i].toLowerCase().trim(); // tokens.nextToken().toLowerCase();
           ///*//-*/ System.out.println(" ("+i+","+item+") ");
           try {
             if (decompoeConfig(item)) {
                // System.out.println(" <- OK");
                }
           } catch (Exception e) { System.err.println("Erro: leitura de parametros para configuracao: "+e);
             e.printStackTrace(); }
           } // for (i=0; i<args.length; i++)
       }
    if (!icomb.IComb.isApplet()) try {
      Locale loc = new Locale(lingua,pais);
      Locale.setDefault(loc);
      } catch (Exception e2) { e2.printStackTrace(); }
    //- System.out.println("\nFim! i="+i);
    //- java.util.Properties prop = System.getProperties(); //
    }

  //
  public static void defineBundle (boolean chamaDefine) {
    String msg_nome_default = "i18n", msg_nome,
           lingua_aux, pais_aux;
    lingua_aux = (lingua!=null && lingua.length()>0 && lingua.charAt(0)!='_') ? "_"+lingua : lingua;
    pais_aux = (pais!=null && pais.length()>0 && pais.charAt(0)!='_') ? "_"+pais : pais;

    // Com linha abaixo => aplicativo tenta entrar no prim. 'bundle=ResouceBundle...' com 'i18n_pt_BR_pt_BR"
    msg_nome = "i18n"+lingua_aux.toLowerCase()+pais_aux.toUpperCase();

    String s_aux = instance!=null ? instance.getString("iComb") : "Combinatória Interativa na Internet"; // iComb=Combinatória Interativa na Internet
    // System.out.println("\n .: iComb : "+s_aux+" :.\n"); // iComb: 

    // 'i18n*.properties'
    try { //try1
        // 'i18n_lingua_pais.properties'
        try { //try2
          bundle = ResourceBundle.getBundle(msg_nome);
          ///*//-*/ System.out.println("1: msg_nome="+msg_nome);
        } catch (Exception e_lingua_pais) { // (java.util.MissingResourceException mre)
          // Tente agora só com lingua
          msg_nome = "i18n"+lingua_aux.toLowerCase();
          // 'i18n_lingua_pais.properties'
          try { //try3
            bundle = ResourceBundle.getBundle(msg_nome);
            /*//-*/ System.out.println("2: msg_nome="+msg_nome);
          } catch (Exception e_lingua) { // (java.util.MissingResourceException mre)
            // msgGeomInteratInternet = Geometria Interativa na Internet

            try { //try4
              // usualmente entra aqui: ao fazer 'java icomb.IComb ...'
              bundle = ResourceBundle.getBundle("i18n"); // ./i18n_en_US.properties
            } catch (Exception e) {
              System.err.println(" Tenta: tentaResourceURL:"+" msg_nome="+msg_nome+": "+e);
              // tentaResourceURL(msg_nome);
              e.printStackTrace();
              } //try-catch4

           } //try-catch3

        } //try-catch2

    } catch (java.util.MissingResourceException mre) {
      System.err.println("Erro: RB: "+mre);
      // tentaResourceURL("Messages");
      } //  catch (java.util.MissingResourceException mre)

    instance = new I18n(lingua, pais);
    // System.err.println("i18n: "+lingua+"_"+pais+": "+instance);

    } // void defineBundle(boolean chamaDefine)


  private I18n (String language, String country) {
    Locale currentLocale = new Locale(language, country);
    try {
      bundle = ResourceBundle.getBundle("i18n",currentLocale);
    } catch (MissingResourceException e) {
      System.err.println("Erro: falta o arquivo de mensagens para linguas! Definido: "+language+"_"+country+": "+e);
      System.err.println("Error: there is missing the message file! Definided: "+language+"_"+country+": "+e);
      //e.printStackTrace();
      }
    }
  
  public static I18n getInstance () {
    if (instance == null)
       instance = new I18n("en","us");
    return instance;
    }

  // not necessary with 'static getString(String)'
  public static void changeInstance (String language, String country)  {
    instance = new I18n(language,country);
    }
  
  public static String getString (String key) {
    if (bundle==null) return key; // para evitar erro de 'java.lang.NullPointerException' no caso de não ter criado Bundle
    try {
      return bundle.getString(key);
    } catch (MissingResourceException e) {
      return key;
      }
    }

  public static String getString (String key, Object[] parametros) {
    try {
      String message = bundle.getString(key);
      int pos = message.indexOf("?");
      int k=0;
      while (pos >=0) {
      	if (k< parametros.length) {
           message = message.substring(0,pos) + parametros[k++] + message.substring(pos+1);
           pos = message.indexOf("?");
           }
	else {
           break;
           }
        }
      return message;
    } catch(MissingResourceException e) {
      return key;
      }
    }

  }
