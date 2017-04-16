/*
 * iMath Project
 * iComb: http://www.matematica.br/icomb
 *
 * Help framework
 * @autho RP, LOB
 */

package ajuda.visao.componentesDoTexto;

import icomb.util.Configuracoes;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import ajuda.modelo.UnsuportedLevelException;


public class Topico extends ComponenteDoTexto {

 private int nivel;

 public static final int
   TITULO = 0, SUBTITULO = 1,
   SESSAO = 2;

 private String nomeTopico;
 private Font f;

 /**
  * Um tópico é um pequeno texto de, no máximo, uma linha que nomeia um bloco de
  * um texto.  O tópico pode ser um título, um subtitulo ou uma sessão.   A distinção
  * entre essas três opções se dá, principalmente, pelo tamanho e estilo da fonte.
  * @param nivel
  * @param nomeTopico
  * @throws UnsuportedLevelException
  */
 public Topico (String nomeTopico, int nivel, int largura) throws UnsuportedLevelException {
  super(largura);
  
  if (nivel < 0 || nivel > 2)
   throw new UnsuportedLevelException();
  this.nivel = nivel;
  this.nomeTopico = nomeTopico;

  f = new Font("Arial", Font.BOLD, 24-5*nivel);

  setFont(f);
  setAltura(nivel);
  setBackground(corBarraSupInf);
  }

 void setAltura (int nivel) {
  altura -= 5*nivel;
  }
 
 // Border and letter of each topic
 public void paint (Graphics g) {
  g.setColor(Configuracoes.FACE_CLEAR1);
  g.drawString(nomeTopico, 10, altura-8);
  g.drawRect(0, 0, getSize().width-2, getSize().height-2);
  }

 }
