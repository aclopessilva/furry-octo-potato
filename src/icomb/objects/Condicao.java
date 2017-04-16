/*
 * iMath project
 * iComb: http://www.matematica.br/icomb
 * 
 * @author AKE, LOB
 * 
 */

package icomb.objects;

import java.util.Iterator;
import java.util.Vector;

public class Condicao {

 private Vector expressoes;

 private int quantidade;

 public Vector getExpressoes () {
  return expressoes;
  }

 public Condicao (int quantidade) {
  this.quantidade = quantidade;
  expressoes = new Vector();
  }

 public void addExpressao (Expressao expressao) {
  expressoes.add(expressao);
  }

 public boolean evaluate (Element element) {
  boolean result = true;
  for (int i=0; i<expressoes.size(); i++) {
   Expressao expressao = (Expressao) expressoes.get(i);
   result = result && expressao.evaluate(element);
   if (!result)
    break;
   }
  return result;
  }

 public int getQuantidade ()  {
  return quantidade;
  }

 public String toFormatoReduzido () {
  StringBuffer sb = new StringBuffer();
  boolean primeiro =true;
  for (int i=0; i<expressoes.size(); i++) {
   if (primeiro)
    primeiro=false;
   else
    sb.append(" E ");
   Expressao expressao = (Expressao) expressoes.get(i);
   sb.append(expressao.toFormatoReduzido());
   }
  return quantidade + ":" + sb.toString();
  }

 public String toString () {
  String sb = "";
  boolean primeiro =true;
  for (int i=0; i<expressoes.size(); i++) {
   if (primeiro)
    primeiro=false;
   else
    sb += " E ";
   Expressao expressao = (Expressao) expressoes.get(i);
   sb += expressao.toString();
   }
  return quantidade + ":" + sb;
  }


 }
