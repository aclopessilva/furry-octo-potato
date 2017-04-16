package ajuda.evento;


import java.util.EventObject;

import ajuda.modelo.TextoAjuda;
import ajuda.visao.navegador.NavigatorPanel;


public class NavigatorPanelEvent extends EventObject {
	
	TextoAjuda ta;
	NavigatorPanel np;

	public NavigatorPanelEvent(NavigatorPanel source) {
		super(source);
		np = source;
	}

	public TextoAjuda getSelectedText(){
		return np.getConteudoSelecionado();
	}
	
	public int getSelectedTextIndex(){
		return np.getTextIndex();
	}
}
