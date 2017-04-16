package ajuda;


import icomb.util.I18n;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ajuda.visao.navegador.ItemNavigator;
import ajuda.visao.navegador.LinearNavigator;
import ajuda.visao.navegador.PainelConteudo;


public class Help extends Frame {

	LinearNavigator linear;
	ItemNavigator item;
	PainelConteudo conteudo;
	
	public Help() {
		super(I18n.getInstance().getString("helpWindow"));		
		setSize(828, 600);
		setLayout(new BorderLayout(3,3));
				
		linear = new LinearNavigator();
		item = new ItemNavigator();
		conteudo = new PainelConteudo();
		
		linear.addNavigatorPanelListener(conteudo);
		linear.addNavigatorPanelListener(item);
		item.addNavigatorPanelListener(conteudo);
		item.addNavigatorPanelListener(linear);
		
		insereWindowListener();		
		
		add("North", linear);
		add("West", item);
		add(conteudo);
		
		setVisible(true);
	}
	
	private void insereWindowListener(){
		addWindowListener(new WindowAdapter(){
			
            public void windowClosing(WindowEvent e) {
				fechaJanela();
			}
		});
	}
	
	private void fechaJanela(){
		setVisible(false);
	}
		
	public void abreJanela(){
		setVisible(true);
	}
	
	/** Método usado para remover o painel lateral da janela principal com o objetivo
	de ampliar a área de visualização da ajuda */
	public void removePainelLateral(){
		remove(item);
		validate();
	}
	
	/** Método que insere o painel principal lateral esquerdo na janela principal.  Esse
	painel, por padrão é exibido; esse método só será chamado quando se o painel foi
	removido da tela por solicitação do usuário */
	public void inserePainelLateral(){
		add("West", item);
		validate();
	}
	
	
    public Insets getInsets() {
		return new Insets(32,7,7,7);
	}
	
	public static void main(String[] args) {
		new Help();
	}
}
