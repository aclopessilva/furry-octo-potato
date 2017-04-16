/*
 * iMath project
 * iComb: http://www.matematica.br/icomb
 * 
 */

package icomb.objects;

import java.util.Iterator;
import java.util.Set;

public class Expressao {

 private String attributeName;

 private boolean belong;

 private Set elements;

 public Expressao (String attributeName, boolean belong, Set elements) {
  this.attributeName = attributeName;
  this.belong = belong;
  this.elements = elements;
  }

 public String toString () {
  return attributeName+": "+(belong?"in":"not in")+elements;
  }

 public boolean evaluate (Element element) {
  boolean ret = false;
  if (elements.contains(element.getAttribute(attributeName)))
   ret = true;
  else {
   Set predicates = element.getPredicates(attributeName);
   if (predicates!=null) {
    Iterator it = predicates.iterator();
    while (it.hasNext()) {
     if (elements.contains(it.next())) {
      ret=true;
      break;
      }
     }
    }
   }

  if (belong)
    return ret;
  else
    return !ret;
  }

 public String getAttributeName () {
  return attributeName;
  }

 public Set getElements () {
  return elements;
  }
 
 public String toFormatoReduzido () {
  StringBuffer sb = new StringBuffer();
  Iterator it = elements.iterator();
  boolean primeiro =true;
  while (it.hasNext()) {
   if (primeiro)
    primeiro=false;
   else
    sb.append(",");   
   sb.append(it.next());
   }

  return attributeName + (belong?"=":"#") + "{" + sb + "}";
  }


 }
