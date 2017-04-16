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


public class Potencia extends Formula {

 public long calcula (long n) throws IllegalArgumentException {
  throw new IllegalArgumentException();
  }

 public long calcula (long n, long p) throws IllegalArgumentException {
  return (long) Math.pow(n, p);
  }

 public String getNome () {
  return "n^p";
  }

 public boolean temParametroN () {
  return true;
  }

 public boolean temParametroP () {
  return true;
  }

 }
