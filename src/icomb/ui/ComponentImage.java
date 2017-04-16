/* 
 * ComponentImage
 *
 * @author Leônidas de Oliveira Brandão
 * versão 0: 23/08/2003
 * Para gerar imagem a partir de Image
 */

package icomb.ui;

import icomb.ui.TrataImagem;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;


public class ComponentImage extends Component { //
 public Image img; //
   
 public final void paint (Graphics graphics) {
   if (img==null) return;
   graphics.drawImage(img, 0, 0, this);
   //graphics.drawString("iComb - http://www.matematica.br", 10, 15);
   }
    
 /* public ComponentImage (Image image, String nomeImg) throws NullPointerException {
   img = image;
   if (!TrataImagem.verificaImage(this, img))
      System.err.println("Erro: imagem "+nomeImg+" com dimensoes nulas!");
   }*/
 public ComponentImage (Image image, String nomeImg) {
   img = image;
   try {
     if (!TrataImagem.verificaImage(this, img))
       System.err.println("Erro: imagem "+nomeImg+" com dimensoes nulas!");
   } catch (Exception e) {
       System.err.println("Erro: em tratamento de componente imagem "+nomeImg+": "+e);
       }
   }

 }
