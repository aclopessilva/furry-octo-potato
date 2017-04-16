/*
 * iMath project
 * iComb: http://www.matematica.br/icomb
 * 
 * @author AEK, LOB
 * @description Formulae to be applied to each stage
 * @see icomb.machine.SetMachine
 * 
 */

package icomb.formula;

import icomb.util.Constants;
import icomb.util.I18n;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Hashtable;


public abstract class Formula {

 private static final Hashtable mapFormula;

 static {
  mapFormula = new Hashtable();
  mapFormula.put("C(n,p)", new Binomio());
  mapFormula.put("A(n,p)", new Arranjo());
  mapFormula.put("n^p", new Potencia());
  mapFormula.put("n!", new Fatorial());
  mapFormula.put(Constants.VALUE, new Value());
  }

 public abstract String getNome ();
 public abstract long calcula (long n, long p);
 public abstract long calcula (long n);
 public abstract boolean temParametroN ();
 public abstract boolean temParametroP ();

 public static Formula getStaticMapFormula (String name) { // getFormulaInstancia
  return (Formula) mapFormula.get(name);
  }

 }
