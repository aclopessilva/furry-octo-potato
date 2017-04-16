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

import java.math.BigInteger;
import java.util.HashMap;

public class Fatorial extends Formula {

 public long calcula (long n) throws IllegalArgumentException {
  long result=n;
  for (long i=n-1; i>1; i--) {
   result *=i;
   }
  return result;
  }

 public long calcula (long n, long p) throws IllegalArgumentException {
  return calcula(n);
  }

 public String getNome () {
  return "n!";
  }

 public boolean temParametroN () {
  return true;
  }

 public boolean temParametroP () {
  return false;
  }

 }
