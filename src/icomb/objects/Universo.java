/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author AEK, LOB
 * @description This class provides the tools keep the Universe set
 * @see icomb/ui/UniversoFrame.java - instantiate 'ImagePanel.java'
 *
 * @credits This source is free and provided by iMath Project (University of S<E3>o Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Le<F4>nidas O. Brand<E3>o.
 *
 */

package icomb.objects;

import icomb.util.Constants;
import icomb.util.Util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Universo {

 private Set attributes;
 private Hashtable predicates;

 private Vector elementos;

 private String nome;
 private String arquivo;
 private String directory; // 2008-2013: it must be { "baralho/imagens/" or "futebol/imagens/" }

 public String getArquivo () {
   return arquivo;
   }

 public String getDirectory () { return directory; }
 private static String clearDirectoryFromName (String fileXML) {
   if (fileXML==null) return null;
   int index = fileXML.lastIndexOf("/"); // or java.io.File.separatorChar
   if (index<0) //security: separator '/' was not found!
      return fileXML;
   return fileXML.substring(0, index);
   }

 // From: icomb.machine.SetMachine.loadUniverso
 // Certo: icomb.ui.DefinedUniversePanel.itemStateChanged(DefinedUniversePanel.java:175)
 // Erro : icomb.machine.SetMachine.loadUniverso(SetMachine.java:555)
 public void setArquivo (String directory, String arquivo) {
System.out.println("Universo.java: setArquivo: directory="+directory+"->"+this.directory+", arquivo="+arquivo);

//  /home/leo/projetos/iMA/iMA0/icomb/novo/bin/icomb/
   this.arquivo = arquivo; //  2008-2013: it must be { "baralho/baralho.xml" or "futebol/futebol.xml" }

   //TODO: rever, todo mundo deveria usar esta caminho para pegar as imagens mas 'GerenciadorRecursos.getImagem(MontaFace mf, String nomeCompletoImagem)'
   //TODO: esta criando path especial a partir do metodo 'GerenciadorRecursos.getImageFromJAR("", nomeCompletoImagem);'
   this.directory = clearDirectoryFromName(arquivo) + "/imagens/" ; // 2008-2013: { "baralho/imagens/" or "futebol/imagens/" }
System.out.println("Universo.java: setArquivo: this.directory="+this.directory);

   //try { String str=""; System.out.println(str.charAt(3)); }catch(Exception e) {e.printStackTrace();}

   }

 // From: 'icomb.util.Parse': 'static Universo load(InputStream is)' from 'icomb.machine.SetMachine.loadUniverso()'
 public Universo () {
   // try{String str="";System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
   elementos = new Vector();
   attributes = new HashSet();
   predicates = new Hashtable();
   }

 public Vector getElementos () {
   return elementos;
   }

 public Vector getPredicados (String nameOfAttribute) {
   Set set = (Set) predicates.get(nameOfAttribute);
   Vector ret = new Vector();
   ret.add("");
   if (set != null) {
     Iterator it = set.iterator();
     while (it.hasNext()) {
       ret.add(it.next());
       }
     }
   return ret;
   }

 public Vector getAtributos () {
   Vector ret = new Vector();
   ret.add("");
   Iterator it = attributes.iterator();
   while (it.hasNext()) {
     ret.add(it.next());
     }
   return ret;
   }

 public void addElemento (Element element) {
   Set att = element.getAttributes();
   Iterator it = att.iterator();
   while (it.hasNext()) {
     String type = (String) it.next();
     String value = element.getAttribute(type);
     addAtributo(type,value,element.getPredicates(type));
     }
   elementos.add(element);
   }

 private void addAtributo (String type, String value,Set set) {
   Vector vec = new Vector();
   Iterator it = set.iterator();
   while (it.hasNext()) {
     vec.add(it.next());
     }
   addAtributo(type,value,vec);
   }

 private void addAtributo (String type, String value, Vector arrPred) {
   attributes.add(type);
   if (arrPred == null) return;
   Set set = (Set) predicates.get(type);
   if (set == null)
     set = new HashSet();
   set.add(value);
   if (arrPred!=null) {
     for (int i=0; i<arrPred.size() ; i++) {
       set.add(arrPred.get(i));
       }
     }
   predicates.put(type, set);
   }


 // ./icomb/ui/DrawingPanel.java: recalcula(): hand = ObjectManager.setMachine.getUniverso().getPossivelColecao(ObjectManager.setMachine.getEstagiosParaDesenhar());
 public Element [] getPossivelColecao (Vector stages) {

   SetElement conjunto = new SetElement(elementos.size());

   int k=0;
   for (int i=0 ; i<elementos.size();i++) {
     conjunto.addElement(k++, (Element) elementos.get(i));
     }

   Vector all = new Vector();
   int total_size=0;
   for (int j=0; j<stages.size(); j++) {
     Estagio stage = (Estagio) stages.get(j);
     int n = stage.getNElementos();
     Expressao [] conditions = new Expressao[stage.getNPropriedades()];

     for (int i=0; i<stage.getNPropriedades(); i++) {
       if (i==0) {
         Set elements = Util.produceStringSet(stage.getElemento1());
         conditions[i] = new Expressao(stage.getAtributo1(),stage.getRelacao1().equals(Constants.IS) || stage.getRelacao1().equals(Constants.IS_IN),elements);
         }
       else {
         Set elements = Util.produceStringSet(stage.getElemento2());
         conditions[i] = new Expressao(stage.getAtributo2(),stage.getRelacao2().equals(Constants.IS) || stage.getRelacao2().equals(Constants.IS_IN),elements);
         }
       }

     Set S = new HashSet();
     S = conjunto.extract(n, conditions);
     total_size+=S.size();
     all.add(S);
     } // for (int j=0; j<stages.size(); j++)

   Element [] result = new Element[total_size];
   int m=0;
   for (int i=0; i<all.size(); i++) {
     Set S = (Set) all.get(i);
     Iterator it = S.iterator();
     Element [] parcial = new Element[S.size()];
     int x=0;
     while (it.hasNext()) {
       Element e = (Element) it.next();
       parcial[x++] = e;
       }
     permutacao(parcial);
     for (int j = 0; j < parcial.length; j++) {
        result[m++] = parcial[j];
        }
     } // for (int i=0; i<all.size(); i++)
   return result;
   }

 public void permutacao (Element [] elementos) {
   for (int i=0; i<elementos.length ; i++) {
     int sorteio = (int) (Math.random()*elementos.length);
     Element aux = elementos[sorteio];
     elementos[sorteio] = elementos[i];
     elementos[i] = aux;
     }
   }

 public boolean compativel (Condicao [] condicoes) {
   Set predicatesSet = new TreeSet();
   Iterator it = predicates.keySet().iterator();
   while (it.hasNext()) {
     String key = (String) it.next();
     predicatesSet.addAll((Set) predicates.get(key));
     }

   for (int i=0; i<condicoes.length; i++) {
     Condicao condicao = condicoes[i];
     Vector expressoes = condicao.getExpressoes();
     for (int j=0 ; j<expressoes.size(); j++) {
       Expressao expressao = (Expressao) expressoes.get(j);
       String attributeName = expressao.getAttributeName();
       Set elements = expressao.getElements();
       if (!this.attributes.contains(attributeName))
         return false;
       Iterator it2 = elements.iterator();
       while (it2.hasNext()) {
         String elem = (String) it2.next();
         if (!predicatesSet.contains(elem))
           return false;
         }
       }
     }
   return true;
   }

 // Construct 'Condicao' from String following the form:
 //           "16:suit={red};1:suit={spades} E value={ace};3:suit={spades} E value#{ace}" that means
 //           16 "red cards"; 1 "spade ace"; 3 "spade that isn't ace"
 public static Condicao [] interpretaCondicao (String strCond) {

   if (strCond==null || strCond=="")
      return null; // in case iComb loaded with no exercise

   Pattern pattern = null;
   Matcher matcher = null;
   try {
     pattern = Pattern.compile("([^;]*)");
     matcher = pattern.matcher(strCond);
   } catch (Exception e) {
     System.err.println("Erro: em universo, ao tentar tratar condicao "+strCond+": "+e);
     }
   if (matcher==null) return null;

   // System.out.println("Universo.interpretaCondicao: "+strCond);
   // int j=0;

   Vector condicoes = new Vector();
   while (matcher.find()) {
     String group = matcher.group(0);
     int doisPontos = group.indexOf(":");
     if (doisPontos<=0)
      continue;
     String numero = group.substring(0,doisPontos);
     Condicao condicao = new Condicao(Integer.parseInt(numero));
     String cond = group.substring(doisPontos+1);
     // Pattern patternCondicao = Pattern.compile("(suit|value)([#=])(\\{[^\\}]*\\})");
     Pattern patternCondicao = Pattern.compile("([^ #=]*)([#=])(\\{[^\\}]*\\})");

     Matcher matcherCondicao = patternCondicao.matcher(cond);
     while (matcherCondicao.find()) {
       String att = matcherCondicao.group(1);
       String sign = matcherCondicao.group(2);
       String elem = matcherCondicao.group(3);

       Expressao expressao = new Expressao(att,sign.equals("="),Util.produceStringSet(elem));
       condicao.addExpressao(expressao);
       // System.out.println("  "+j+++": "+expressao.toString());
       }
     condicoes.add(condicao);
     }
   Condicao [] retorno = new Condicao[condicoes.size()];
   for (int i=0; i<condicoes.size(); i++) {
     retorno[i] = (Condicao) condicoes.get(i);
     }
   return retorno;
   }

 public String getNome () {
   return nome;
   }

 public void setNome (String nome) {
   this.nome = nome;
   }

 }
