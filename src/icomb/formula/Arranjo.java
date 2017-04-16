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

public class Arranjo extends Formula {

 public long calcula (long n) throws IllegalArgumentException {
  throw new IllegalArgumentException();
  }

 public long calcula (long n, long p) throws IllegalArgumentException {
  long q = n-p;
  long max = Math.max(p,q);
  long min = Math.min(p,q);
  if (min==0) return 1;
  long result=n;
  for (long i=n-1; i>max; i--) {
   result *=i;
   }
  return result;
  }

 public String getNome () {
  return "A(n,p)";
  }

 public boolean temParametroN () {
  return true;
  }

 public boolean temParametroP () {
  return true;
  }

 }
