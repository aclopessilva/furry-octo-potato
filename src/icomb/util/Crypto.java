/*
 *
 * @author LOB, RP
 * @description Para criptografar exerc�cios - ver 'igraf/io/Sistema.java'
 * Restri��es de uso
 *
 * O c�digo fonte deste programa pode ser utilizado dentro do projeto iM�tica, mas
 * n�o deve ser distribuido. Qualquer d�vida sobre uso entre em contato com o coordenador
 * do projeto iMatica: http://www.matematica.br/
 *
 */

package icomb.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public final class Crypto {

  private static final String hexDigits = "0123456789abcdef";

  /**
   * Realiza um digest em um array de bytes atrav�s do algoritmo especificado
   * @param input - O array de bytes a ser criptografado
   * @param algoritmo - O algoritmo a ser utilizado
   * @return byte[] - O resultado da criptografia
   * @throws NoSuchAlgorithmException - Caso o algoritmo fornecido n�o seja
   * v�lido
   */
  public static byte[] digest (byte[] input, String algoritmo) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance(algoritmo);
    md.reset();
    return md.digest(input);
  }
  
  
  /** Embaralha uma String selecionando letras de n em n caracteres
   * at� esgotar a �ltima string
   * @param texto
   * @return a String embaralhada
   */
  public static String embaralha(String texto) {
	
	  int size = texto.length();
      int pulo = size/3 ;

	  LinkedList ll = new LinkedList();
	  for(int i=0 ; i<texto.length() ; i++) {
		  ll.add(""+texto.charAt(i));
	  }
	 
	  int index = pulo;
	  StringBuffer sb = new StringBuffer();
	  sb.append(ll.remove(index));
	  while (ll.size()>0) {
		  
		  index = (index + pulo) % ll.size();
		  sb.append(ll.remove(index));
	  }
	  return sb.toString();
  }
  
  
  /** Recupera a string embaralhada pelo m�todo acima
   * at� esgotar a �ltima string
   * @param texto
   * @return a String original
   */
  public static String desembaralha(String texto) {
	int size = texto.length();
	int pulo = size/3;
	char[] caracteres = new char[size];
	int index = 0;
	int j=1;
    for(int i=0 ; i<texto.length() ; i++) {
	   
    	while (j<=pulo) {
			index = (index + 1) % size;
    		if (caracteres[index] == 0) 
    			j++;
    	}
    	j=0;
    	caracteres[index] = texto.charAt(i);
	}
    
    return new String(caracteres);
  }
  
  

  /**
   * Converte o array de bytes em uma representa��o hexadecimal.
   * @param input - O array de bytes a ser convertido.
   * @return Uma String com a representa��o hexa do array
   */
  private static String byteArrayToHexString(byte[] b) {
    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < b.length; i++) {
    	int j = ((int) b[i]) & 0xFF; 
    	buf.append(hexDigits.charAt(j / 16)); 
    	buf.append(hexDigits.charAt(j % 16)); 
        }

    return buf.toString();
    }

  /**
   * Converte uma String hexa no array de bytes correspondente.
   * @param hexa - A String hexa
   * @return O vetor de bytes
   * @throws IllegalArgumentException - Caso a String n�o sej auma
   * representa��o haxadecimal v�lida
   */
  private static byte[] hexStringToByteArray (String hexa) throws IllegalArgumentException {
    //verifica se a String possui uma quantidade par de elementos
    if (hexa.length() % 2 != 0) {
       throw new IllegalArgumentException("String hexa inv�lida");  
       }

    byte[] b = new byte[hexa.length() / 2];

    for (int i = 0; i < hexa.length(); i+=2) {
        b[i / 2] = (byte) ((hexDigits.indexOf(hexa.charAt(i)) << 4) | (hexDigits.indexOf(hexa.charAt(i + 1))));
        }
    return b;
    }

  
  public static String stringToHex (String s) {
    String aux = "_h";
    aux += embaralha(byteArrayToHexString(s.getBytes()));
    return aux;
    }
  
  
  public static String hexToString (String s) {
    if (s.length() < 2 || !s.substring(0, 2).equals("_h")){
       return s;
       }
    return new String(hexStringToByteArray(desembaralha(s.substring(2))));
    }
  
  
  public static String criptografiaMD5 (String s) {
    String result = null;
    try {
     result = new String(Crypto.digest(s.getBytes(), "md5"));
    } catch (NoSuchAlgorithmException e) {}
    return result;
    }

  // a id�ia � gravar a resosta do prof criptografada e, quando for 
  // conferir a resposta do aluno criptograf�-la e fazer a compara��o
  // sobre os resultados criptografados

  public static void main (String[] args) {
    byte[] b = null;
    
    
    System.out.println(embaralha("ALEXANDRE"));
    String texto = "----------------------------------------------------------------------------------\n" +
                   "problemas\n" +

                   "figs ch03.tex e mandar para o leo\n" +

                   "abri o ex07, depois o ex05 ... e deu erro!!!\n" +

                   "avaliador expr equivalente: mudar a conven��o de vazio e o crit�rio de 5% de erro\n" + 

                   "resolver problema de derivadas com restri��es de dom�nio - ex. tan (x)\n" +

                   "arrumar a aplica��o da regra dos trap�zios\n" +
                   "verificar a conformidade da implementa��o com o algoritmo\n" +
                   "o que determina a constante aparentemente � o tamanho do intervalo;\n" +
             
                   "k = f((a+b)/2)\n" +

                   "problema do coment�rio para o aluno\n" +

                   "thread: usar ou n�o"; 
//    try {
//      b = Crypto.digest(texto.getBytes(), "md5");
//    } catch (NoSuchAlgorithmException e) {}

    
    texto = "2:suit={hearts};2:suit={spades};1:suit#{hearts,spades}";
    System.out.println("texto original:\n" + texto);
    
    // converte os bytes criptografados em string
    String criptografada = Crypto.stringToHex(texto);

    System.out.println("texto criptografado:\n" + criptografada);
    System.out.println(hexToString(criptografada));
    String s = new String(hexToString(criptografada));
    System.out.println("\ntexto recuperado:\n" + s);
    }

  }
