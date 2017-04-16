package ajuda.visao.componentesDoTexto;

import icomb.util.Configuracoes;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.StringTokenizer;

public class Paragrafo extends ComponenteDoTexto {

	FontMetrics fm;
	private int size = 14, numLines = 0, lineHeight;
	private String texto;	
	private Font f;
	
	// aqui estou supondo que cada caracter, em média, tem a largura igual à metade
	// do tamanho da fonte... essa suposição funcionou surpreendentemente bem, mas 
	// precisa ser substituída por algo mais consistente. 
	public Paragrafo(String texto, int largura) {
		super(largura);
		this.texto = texto;
		f = new Font("Arial", Font.PLAIN, size);
		setFont(f);
		setBackground(corAreaDesenho);
		altura = (texto.length()*(size/2)/(largura-2*margem)+3)*size;
	}

	
	public void paint(Graphics g) {
		fm = g.getFontMetrics();	
		lineHeight = fm.getAscent() + fm.getDescent();
		
		desenhaTexto(g);
	}
	
	
	private void desenhaTexto(Graphics g) {
		StringTokenizer st = new StringTokenizer(texto, " ", true);
		int lineWidth = 40;
		String s;
		int n = 1;
		
		while (st.hasMoreTokens()) {
			s = st.nextToken();
			
			int k = fm.stringWidth(s);			
			
			if(lineWidth+k > largura-2*margem){
				lineWidth = 10;
				n++;
			}
			g.drawString(s, lineWidth, n*lineHeight);
			lineWidth += k;
			numLines = n;		
		}
	}

	public int getAltura() {
		return  altura;
	}

	void setAltura(int altura) {}

}
