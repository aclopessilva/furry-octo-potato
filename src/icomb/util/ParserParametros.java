package icomb.util;
import java.util.Hashtable;


public class ParserParametros {

  public static String enunciado="# iComb: http://www.matematica.br" +
      "[ .: version: 0.1 :. ]" +
      "{EnumGabarito: Com um baralho de 32 cartas, quantas mãos de 2 cartas é possível formar com exatamente uma Dama e um Ás?}" +
      "{Tipo: exercicio: 1}" +
      "{solucao: 1:value={queen};1:value={ace}}" +
      "{resposta: 16}" +
      "{ArquivoUniverso: baralho/baralho.xml}" +
      "{Dica: Um baralho de 32 cartas corresponde a um baralho comum sem as cartas cujos valores são menores do que Sete. Portanto, um baralho de 32 cartas 8 cartas de cada naipe (7,8,9,10,Valete, Dama, Rei e Ás) }" +
      "{online:1}";

  public static Hashtable parser(String enunciado) {
    Hashtable mapa = new Hashtable();
    int balanco=0;
    int posDe = 0;
    char caracter;
    for (int i=0; i< enunciado.length(); i++) {
        caracter = enunciado.charAt(i);
        if (caracter == '{') {
           if (balanco == 0)
              posDe = i;
           balanco++;
           }
        if (caracter == '}') {
           balanco--;
           if (balanco==0) {
              String chaveEValor = enunciado.substring(posDe+1,i);
              int doisPontos = chaveEValor.indexOf(":");
              String chave = chaveEValor.substring(0,doisPontos).trim();
              String valor = chaveEValor.substring(doisPontos+1).trim();
              mapa.put(chave, valor);
              }
           }
        } // for (int i=0; i< enunciado.length(); i++)
        return mapa;
    }
            
  public static void main(String[] args) {
    System.out.println(parser(enunciado));
    }
     
  }
