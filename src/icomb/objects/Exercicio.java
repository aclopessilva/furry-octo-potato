/*
 * iComb: http://www.matematica.br/icomb
 *
 * @author AKE, LOB<br>
 *
 */

package icomb.objects;

import java.util.TreeSet;

import icomb.util.I18n;

public class Exercicio {

  private int number;
  private String description;
  private String longDescription;
  private Condicao[] solucao;
  private long resposta;

  public long getResposta () {
    return resposta;
    }


  public void setResposta (long resposta) {
    this.resposta = resposta;
    }


  public String getLongDescription () {
    if (longDescription==null)
       return I18n.getInstance().getString("smEx" + number + "LongDescription");
    else
     return longDescription;
    }


  public Exercicio (int number) {
    super();
    this.number = number;
    }

  public Exercicio (int number,String description,String longDescription) {
    super();
    this.number = number;
    this.description = description;
    this.longDescription = longDescription;
    }

  public String getDescription () {
    if (description== null)
       return I18n.getInstance().getString(getCodDescription());
    else
       return description;
    }

  public String getCodDescription () {
    return "smEx" + number + "Description";
    }

  public int getNumber () {
    return number;
    }

  public String toString () {
    return getDescription();
    }

  public Condicao[] getSolucao () {
    return solucao;
    }


  // Define condictions for this exercise - called by 'icomb/ui/ChooseExercisePanel.java'
  public void setSolucao (Condicao[] solucao) {
    this.solucao = solucao;
    // System.out.println("Exercicio.setSolucao: #solucao="+solucao.length);
    // for (int i=0; i<solucao.length; i++) {
    //    System.out.println(" "+i+": "+solucao[i]);
    //    }
    // // String str=""; try{System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
    }

  }
