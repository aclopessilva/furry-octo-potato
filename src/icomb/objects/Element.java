/* 
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 * 
 * @author Leônidas de Oliveira Brandão, Alexandre Eisenmann
 
 * @version
 *
 * @description Basic data from each element from a fixed universe.
 * 
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class Element {

 private String name;
 private Hashtable attributes;
 private Hashtable predicates;
 private String imagem;
 
 public String getName ()  {
  return name;
  }


 public void setImagem (String imagem) {
  this.imagem = imagem;
  }


 public String getImagem () {
  return imagem;
  }


 public Element (String name) {
  this.name = name;
  attributes = new Hashtable();
  predicates = new Hashtable();
  }


 public Set getPredicates (String nameOfAttribute) {
  return (Set) predicates.get(nameOfAttribute);
  }

 public String getAttribute (String nameOfAttribute) {
  return (String) attributes.get(nameOfAttribute);
  }


 public Set getAttributes () {
  return  attributes.keySet();
  }

 
 public void addAttribute (String type, String attribute) {
  addAttribute(type,attribute,null);
  }


 public void addAttribute (String type, String attribute,Vector arrPred) {
  attributes.put(type, attribute);
  
  if (arrPred == null) return;
  HashSet set = new HashSet();

   if (arrPred!=null) {
    for(int i=0; i<arrPred.size() ; i++) {
     set.add(arrPred.get(i));
     }
    
    }
  predicates.put(type, set);
  }


 public String toString () {
  return name;
  }


 }
