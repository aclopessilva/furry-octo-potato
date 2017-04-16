/*
 * iComb: http://www.matematica.br/icomb
 *
 * @author AKE, LOB
 * Avaliador de solucoes: controi conjunto universo, avalia solucoes, montando conjunto solucao de condicoes
 * Esta classe verifica a solução de um problema do IComb.
 * No construtor informamos a lista de elementos do universo e um conjunto solução de condições.
 * 
 * @see  icomb.machine.SetMachine.setUniverso(SetMachine.java:499)
 * 
 */


package icomb.erro;

import icomb.formula.Binomio;
import icomb.objects.Condicao;
import icomb.objects.Element;
import icomb.objects.Estagio;
import icomb.objects.Universo;
import icomb.util.I18n;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set; // Set extends Collection - HashSet implements Set
import java.util.Vector;

public class Avaliador {

  private Element[] elementos;
  private Condicao[] condicoes;
  private Map elemParticao;
  private Map elemResposta;
  private Map particoes;

  /**
   * O array de condicoes representa um conjunto solução para o problema
   *
   * @param universo
   * @param condicoes que são uma solução para o problema
   */
  public Avaliador (Universo universo, Condicao[] condicoes) {
    Vector vecElementos = universo.getElementos();
    elementos = new Element[vecElementos.size()];
    for (int i=0 ; i<vecElementos.size(); i++) {
      elementos[i] = (Element) vecElementos.get(i);
      }
    this.condicoes = condicoes;
    this.elemParticao = new Hashtable();
    this.elemResposta = new Hashtable();
    this.particoes = new Hashtable();
    //T System.out.println("Avaliador.Avaliador(): insert "+vecElementos.size()+" in 'elemParticao': ");
    for (int i=0; i<elementos.length; i++) {
      //T System.out.print("  "+i+": elementos["+i+"]="+elementos[i]+": condicoes=<");
      for (int j=0; j<condicoes.length; j++) {
        // Get only the elements respecting the condition 'condicoes[j]' - condition is defined in the solution-model
        if (condicoes[j].evaluate(elementos[i])) {
           if (elemParticao.get(elementos[i])!=null)
              throw new RuntimeException(I18n.getString("evaluationMessage01"));
           else {
              elemParticao.put(elementos[i], condicoes[j]);
              //T System.out.print(condicoes[j].toString()+" "); //FormatoReduzido()+" ");
              Set set = (Set) particoes.get(condicoes[j]);
              if (set == null)
                 set = new HashSet();
              set.add(elementos[i]);
              particoes.put(condicoes[j],set);
              }
           }
        } // for (int j=0; j<condicoes.length; j++)
      //T System.out.println(">");
      } // for (int i=0; i<elementos.length; i++)
    //T // String str=""; try{System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
    }


  /**
   * Este método permite adicionarmos uma condição candidata para o problema.
   * A Condição não precisa ser idêntica a alguma das condições informadas no
   * contrutor, porém precisa ser compatível.
   *
   * A todo o momento o conjunto de soluções candidatas será confrontado com
   * o conjunto solução informado no construtor e uma mensagem de erro será
   * lançada quanto os conjuntos não forem compatíveis
   *
   * @param condicao candidata
   */
  public void adicionaCondicao (Condicao condicao) {

    String trace_err = "   #elementos="+elementos.length+" - inserted in 'setCondicoes':\n"; // "Avaliador.adicionaCondicao: "+condicao;
    Set setCondicoes = new HashSet(); // Set is interface - HashSet implements Set
    Set setElementos = new HashSet();
    for (int i=0; i<elementos.length; i++) {
        // 'setCondicoes' has its value define by the example - see the imagens in the iComb botton
        if (condicao.evaluate(elementos[i])) {
           setElementos.add(elementos[i]);
           setCondicoes.add(elemParticao.get(elementos[i]));
           // System.out.println(" "+i+": "+elementos[i]+" - "+elemParticao.get(elementos[i]));
           trace_err += "   "+i+": "+elementos[i]+" - "+elemParticao.get(elementos[i])+"\n";
           }
        }
    trace_err += "   #setCondicoes="+setCondicoes.size()+"\n";
    if (setElementos.size()==0)
       throw new RuntimeException(I18n.getString("evaluationMessage02"));
    if (setCondicoes.size()==0 || setCondicoes.size()==1 && (setCondicoes.iterator().next() == null))
       throw new RuntimeException(I18n.getString("evaluationMessage03"));

    Condicao c1 = null, c2 = null;
    // Frequencia dos elementos não é igual a 1 isto é
    // nem todos os elementos estão sendo selecionados
    if (condicao.getQuantidade() != setElementos.size()) {
       if (setCondicoes.size()>1) try {
          Iterator it = setCondicoes.iterator();
          c1 = (Condicao) it.next();
          c2 = (Condicao) it.next();
          // System.out.println("c1="+c1+", c2="+c2);

          HashSet conjElementos1 = (HashSet) particoes.get(c1);
          HashSet conjElementos2 = (HashSet) particoes.get(c2);

          Iterator it2 = setElementos.iterator();
          Element e1 = null;
          while (it2.hasNext()) {
            Element elemento = (Element) it2.next();
            if (conjElementos1.contains(elemento)) {
               e1 = elemento;
               break;
               }
            }

          Iterator it3 = setElementos.iterator();
          Element e2 = null;
          while (it3.hasNext()) {
            Element elemento = (Element) it3.next();
            if (conjElementos2.contains(elemento)) {
               e2 = elemento;
               break;
              }
            }

          throw new RuntimeException(I18n.getString("evaluationMessage04", new Object[]{e1,e2}));

          } catch (Exception e) {
            System.err.println("Erro: formato nao iComb?: "+e+"\nCondicoes: <("+c1+")> e <("+c2+")>");
            }

      Condicao baseCondicao = (Condicao) setCondicoes.iterator().next();
      Set baseElementos = (Set) particoes.get(baseCondicao);

      if (setElementos.equals(baseElementos)) {
        if (condicao.getQuantidade() != baseCondicao.getQuantidade())
           throw new RuntimeException(I18n.getString("evaluationMessage05"));
        }
      else
        throw new RuntimeException(I18n.getString("evaluationMessage06"));
      } // if (condicao.getQuantidade() != setElementos.size())
    else {
      Condicao baseCondicao = null;
      Set baseElementos = null;
      Iterator it = setCondicoes.iterator();
      //T System.out.println("Avaliador.adicionaCondicao(...): #condicao="+condicao.getQuantidade()+", #setElementos="+setElementos.size()+": it="+it);
      while (it.hasNext()) {
        //T try {
        baseCondicao = (Condicao) it.next();
        baseElementos = (Set) particoes.get(baseCondicao); // 

	// The 'RuntimeException' in 'icomb.ui.StagePanel.verifyStage()'
        if (baseCondicao.getQuantidade() != baseElementos.size()) {
           System.err.println("Error: condition does not match\nAvaliador.adicionaCondicao(...): "+I18n.getString("evaluationMessage07"));
           throw new RuntimeException(I18n.getString("evaluationMessage07"));
           // Alguns elementos terï¿½o sua frequï¿½ncia alterada para 1, isto ï¿½, sempre irï¿½o aparecer.
	   }

        //T } catch (Exception e) {
        //T   System.err.println("Erro: Avaliador.adicionaCondicao, nao foi possivel construir conjunto: "+e);
        //T   System.err.println(" - particoes="+particoes+"\n - baseCondicao="+baseCondicao);
        //T   System.err.println(" - contrucao:\n"+trace_err);

        //T   e.printStackTrace();
        //T   if (baseCondicao!=null && baseCondicao.getQuantidade() != baseElementos.size())
        //T      throw new RuntimeException(I18n.getString("evaluationMessage07"));
        //T   }
        } // while
      }
    // Se chegou aqui, condicao individual é ok
    consolida(condicao,setElementos);

    } // void adicionaCondicao(Condicao condicao)


  /**
   * Depois das verificacoes, consolida condição ao conjunto de elementos.
   *
   * @param cond
   * @param setElementos
   */
  private void consolida (Condicao cond, Set setElementos) {
    Iterator it = setElementos.iterator();
    while (it.hasNext()) {
      Element e = (Element) it.next();
      Condicao c = (Condicao) elemResposta.get(e);
      if (c != null)
         throw new RuntimeException(I18n.getString("evaluationMessage08"));
      }

    it = setElementos.iterator();
    while (it.hasNext()) {
      Element e = (Element) it.next();
      elemResposta.put(e,cond);
      }

    }


  /**
   * Valida solução completa. Mesmo que todas as condições
   * candidatas sejam individualmente compatíveis é possível
   * que ao final do processo alguns elementos não tenham sido
   * selecionados.
   *
   */
  public void valida () {
    Iterator it = elemParticao.keySet().iterator();
    while (it.hasNext()) {
      Element e = (Element) it.next();
      Condicao cSolucao = (Condicao) elemParticao.get(e);
      Condicao cResposta = (Condicao) elemResposta.get(e);
      if (cSolucao == null && cResposta !=null)
          throw new RuntimeException(I18n.getString("evaluationMessage09"));
      if (cResposta == null && cSolucao !=null)
          throw new RuntimeException(I18n.getString("evaluationMessage10",new Object[]{e}));
        }
    }

  /**
   * Reinicia a verificação
   */
  public void reset () {
    this.elemResposta = new Hashtable();
    }

  /**
   * Solução informada está correta? 1 - Sim, 0 - Não
   *
   * @return 1 - Sim, 0 - Não
   */

  public int getResposta () {
    try {
      valida();
      }
    catch(Exception e) {
      return 0;
      }
    return 1;
    }

  /**
   * Verifica se a fórmula do estágio está correta
   * @param universo
   * @param stage
   */
  public static void deteccaoDeErroFormula (Universo universo, Estagio stage) {
    Condicao condicao = stage.criaCondicao();
    Vector elementos = universo.getElementos();
    int n=0;
    for (int i=0; i<elementos.size(); i++) {
      Element element = (Element) elementos.get(i);
      if (condicao.evaluate(element))
         n++;
         }
    int p = condicao.getQuantidade();
    Binomio binomio = new Binomio();
    long resultado1 = binomio.calcula(n,p);
    long resultado2 = 0;
    resultado2 = stage.getFormula().calcula(stage.getN(), stage.getP());
    if (resultado1!=resultado2)
       throw new RuntimeException(I18n.getString("evaluationMessage11"));
    }

  }


