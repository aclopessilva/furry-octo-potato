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

public class Value extends Formula {

 public long calcula (long n) throws IllegalArgumentException {
  return n;
  }

 public long calcula (long n, long p) throws IllegalArgumentException {
  return calcula(n);
  }

 public String getNome () {
  // return I18n.getInstance().getString("value");
  return Constants.VALUE;
  }

 public boolean temParametroN () {
  return true;
  }

 public boolean temParametroP () {
  return false;
  }

 }
