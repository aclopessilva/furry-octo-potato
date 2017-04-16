/*
 * iMath project
 * iComb: http://www.matematica.br/icomb
 * 
 * @author AEK, LOB
 * @description Elements and conditions of any stage (of any exercise solution/creation)
 * @see icomb.machine.SetMachine: that does 'estagioAtual.alimentaEstagio(Hashtable)'
 * 
 */

package icomb.objects;

import icomb.formula.Formula;
import icomb.util.Constants;
import icomb.util.I18n;
import icomb.util.Util;

import java.util.Hashtable;


public class Estagio {

  // DEBUG feature...
  private static int static_cont=0; private int cont = static_cont++;
  public int getCont () { return cont; }
  //public Estagio () { if (this.cont>1)try{String str="";System.out.println(str.charAt(3));}catch(Exception  e){e.printStackTrace();} }

  private String atributo1;
  private String relacao1;
  private String elemento1;

  private String atributo2;
  private String relacao2;
  private String elemento2;

  private int nElementos;
  private int nPropriedades;
  private String propriedades;

  private Formula formula;
  private int n;
  private int p;

  // From: icomb.machine.SetMachine: that does 'estagioAtual.alimentaEstagio(Hashtable)'
  public void alimentaEstagio (Hashtable  map) {
    nElementos = Integer.parseInt(""+map.get("cboNElementos"));
    propriedades = (String) map.get("cboPropOptions");
    if (propriedades.equals(Constants.ONE_PROPERTY))
      nPropriedades=1;
    else if (propriedades.startsWith(Constants.TWO_PROPERTIES))
      nPropriedades=2;
    else
      nPropriedades=0;
    atributo1 = (String) map.get("cboAttributes1");
    relacao1 = (String) map.get("cboRelations1");
    elemento1 = (String) map.get("cboElements1");

    atributo2 = (String) map.get("cboAttributes2");
    relacao2 = (String) map.get("cboRelations2");
    elemento2 = (String) map.get("cboElements2");    
    }

  public String getAtributo1 () {
    return atributo1;
    }

  public String getAtributo2 () {
    return atributo2;
    }

  public String getElemento1 () {
    return elemento1;
    }

  public String getElemento2 () {
    return elemento2;
    }

  public int getNElementos () {
    return nElementos;
    }

  public int getNPropriedades () {
    return nPropriedades;
    }
  public String getPropriedades () {
    return propriedades;
    }

  public String getRelacao1 () {
    return relacao1;
    }

  public String getRelacao2 () {
    return relacao2;
    }

  public Condicao criaCondicao () {
    Condicao condicao = new Condicao(getNElementos());
    if (getNPropriedades()>=1) {
      boolean quero = relacao1.equals(Constants.IS_IN) || relacao1.equals(Constants.IS);
      Expressao expressao = new Expressao(atributo1, quero, Util.produceStringSet(elemento1));
      condicao.addExpressao(expressao);
      }
    if (getNPropriedades()>=2) {
      boolean quero = relacao2.equals(Constants.IS_IN) || relacao2.equals(Constants.IS);
      Expressao expressao = new Expressao(atributo2, quero, Util.produceStringSet(elemento2));
      condicao.addExpressao(expressao);
      }
    return condicao;
    }

  
  // From: src/icomb/machine/SetMachine.java - defineNeP(int,int)
  public void defineNeP (int n, int p) {
    // System.out.println("Estagio.defineNeP("+n+","+p+"): this.cont="+cont);
    // if (this.cont>1)
    // try{String str="";System.out.println(str.charAt(3));}catch(Exception  e){e.printStackTrace();}
    this.n = n;
    this.p = p;
    }

  public String toString () {
    StringBuffer sb = new StringBuffer();
    sb.append("nElements :" + nElementos);
    sb.append("nProperties :" + nPropriedades);
    sb.append("attribute1: " + atributo1);
    sb.append("relation1: " + relacao1);
    sb.append("element1: " + elemento1);
    sb.append("attribute2: " + atributo2);
    sb.append("relation2: " + relacao2);
    sb.append("element2: " + elemento2);
    return sb.toString();
    }

  // List short description of this stage
  // From: src/icomb/ui/ListPanel.java - redo(): when user "add stage"
  // From: src/icomb/machine/SetMachine.java - getConjuntoEstagioString()
  public String getDescricao () {
    StringBuffer sb = new StringBuffer();
    sb.append(nElementos + " "+I18n.getString("elements"));

    if (nPropriedades>=1)
      sb.append(" "+I18n.getString("suchThat")+ " " + I18n.getString(atributo1) + " " + I18n.getString(relacao1) + " " + Util.translateSetString(elemento1));

    if (nPropriedades==2)
      sb.append(" "+I18n.getString("and")+ " " + I18n.getString(atributo2) + " " + I18n.getString(relacao2) + " " + Util.translateSetString(elemento2));

    sb.append(" ");
System.out.println("Estagio.getDescricao(): cont="+this.cont+": "+sb);
    return sb.toString();
    }

  public void setFormula (icomb.formula.Formula formula) {
    this.formula = formula;
    }

  public String getTextoFormula () {
    String result =  formula.getNome();
    if (result.equals(Constants.VALUE))
       return ""+n;
    else {
        result = result.replaceAll("n",""+n);
        result = result.replaceAll("p",""+p);
        return result;
        }
    }

  public Formula getFormula () {
    return formula;
    }
  public int getN () {
    return n;
    }
  public int getP () {
    return p;
    }

  public long getValorFinal () {
    return formula.calcula(n,p);
    }

  }
