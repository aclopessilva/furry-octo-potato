package ajuda.modelo;


import icomb.util.I18n;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Panel;

import ajuda.visao.componentesDoTexto.Paragrafo;
import ajuda.visao.componentesDoTexto.Topico;

/**
 * Classe que define o comportamento básico de todos os objetos criados
 * para exibir o texto da ajuda.
 * @author Reginaldo
 *
 */
public class TextoAjuda extends Panel {
	String topico;
	private int altura = 45;
	private final int largura = 600;
	Dimension d = new Dimension(largura, altura);
	
	public TextoAjuda(String topico) {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));				
		setTitulo(topico);		
		setSize(d);
	}

	/**
	 * Recebe uma string titulo e a posiciona no topo da página de conteúdo da ajuda.
	 * Caso esse método seja chamado mais de uma vez, o resultado pode ser imprevisível.
	 * @param titulo
	 */
	private void setTitulo(final String titulo){
		insereTopico(titulo, Topico.TITULO);
		this.topico = titulo;
	}
	
	public String getTopico(){
		return topico;
	}
	
	/**
	 * Recebe uma string subTitulo e a posiciona imediatamente após o último parágrafo 
	 * editado.  Nesse contexto, a chamada ao método titulo() pode ser considerada como
	 * inserção de um parágrafo.
	 * @param subTitulo
	 */
	void insereSubTitulo(String subTitulo){
        subTitulo = I18n.getInstance().getString(subTitulo);
		insereTopico(subTitulo, Topico.SUBTITULO);
	}
	
	/**
	 * Recebe uma string subTitulo e a posiciona imediatamente após o último parágrafo 
	 * editado.  Nesse contexto, a chamada ao método titulo() pode ser considerada como
	 * inserção de um parágrafo.
	 * @param subTitulo
	 */
	void insereSessao(String nomeSessao){
		insereTopico(nomeSessao, Topico.SESSAO);
	}
	
	private void insereTopico(String nomeTopico, int nivel){
        Topico c = null;
        try {
          c = new Topico(nomeTopico, nivel, largura);
          try {
              atualizaAltura(c.getAltura());
          } catch (java.lang.IllegalAccessError ia) {
              System.err.println("[help/model/text/TextoAjuda.insereTopico] Erro: "
            		  +ia+"\nAcesso ilegal: nomeTopico="+nomeTopico+" nivel="+nivel+" c="+c);
          }
              add(c);
        } catch (Exception e) {
	   System.err.println("[help/model/text/TextoAjuda.insereTopico] Erro: "+e+
			              "\nnomeTopico="+nomeTopico+" nivel="+nivel+" c="+c);
}
	}
	
	/**
	 * Recebe uma string texto e a posiciona imediatamente após o último parágrafo 
	 * editado.  Nesse contexto, a chamada ao método titulo() pode ser considerada como
	 * inserção de um parágrafo.
	 * @param texto
	 */
	void insereParagrafo(String texto){
        texto = I18n.getInstance().getString(texto);
		Paragrafo p = new Paragrafo(texto, largura);
		atualizaAltura(p.getAltura());
		add(p);
	}
	
	/**
	 * Recebe uma string nomeFigura, tenta carregar a figura dentre os recursos diponíveis e,
	 * estando a figura pronta, exibe-a posicionando imediatamente após o último parágrafo 
	 * editado.  Nesse contexto, a chamada ao método titulo() pode ser considerada como
	 * inserção de um parágrafo.   Para que essa figura possa ser carregada corretamente, deve
	 * ser colocada dentro do diretório igraf.Resource.   Retorna falso caso não seja 
	 * possível carregar a figura.
	 * @param titulo
	 */
	boolean insereFigura(String nomeFigura){
		return false;
	}
	
	private void atualizaAltura(int valor){
		d.setSize(largura, altura+= valor + 10);
	}
	
	
    public Dimension getPreferredSize() {
		return d;
	}	
	
	
    public Insets getInsets() {
		return new Insets(0,10,10,10);
	}
	
	
    public String toString() {
		return topico;
	}
}
