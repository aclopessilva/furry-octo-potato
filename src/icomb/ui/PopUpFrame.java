
/*
 * iComb: http://www.matematica.br/icomb
 *
 */

package icomb.ui;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import base.EsquemaVisual;

public abstract class PopUpFrame extends Frame implements EsquemaVisual {

  boolean visivel;

  PopUpFrame () {
    setBackground(corAreaDesenho);
    setLocation(200, 100);  
    addCloser();
    }

  protected PopUpFrame (String title) {
    super(title);
    addCloser();
    setBackground(corAreaDesenho);
    }

  void addComponent (Component c, int x, int y, int w, int h) {
    c.setSize(w, h);
    c.setLocation(x, y);
    add(c);
    }

  private void addCloser () {
    addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) { visibilidade(); }
        });
    }

  /**
   * Alterna a visibilidade deste frame entre visível e não visível 
   * ao usuário.
   * <li>Método chamado em MontaFace.showDialog() e no construtor local.</li><br>
   */
  public void visibilidade () {
    if (!visivel)
       try {
         setVisible(true);
       } catch (Exception exept) {
         System.err.println("Erro: ao tentar fazer PopUpFrame visível!");
         exept.printStackTrace();
         }
   else
       try {
         setVisible(false);
         dispose();
       } catch (Exception exept) {
         System.err.println("Erro: ao tentar fazer PopUpFrame visível!");
         exept.printStackTrace();
         }
       visivel = !visivel;
       }
  }
