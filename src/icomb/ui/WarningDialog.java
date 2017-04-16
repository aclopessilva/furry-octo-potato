package icomb.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
//import java.awt.;

import java.awt.event.*;


public class WarningDialog extends PopUpFrame 
implements KeyListener, FocusListener {   
// java.awt.Dialog implements KeyListener, FocusListener

  private static final int ALTURA = 11; // utilizada para fazer altura da janela proporcional ao número de msgs

  protected Button ok;
  Panel p1 = new Panel(new FlowLayout());

  public void focusOK () {
    ok.requestFocus();	
    }   

  /*
  // só para teste no iMath.java
  public WarningDialog (iMath imath, String comentario, String nome) {
    this(comentario);
    addWindowListener(new WindowAdapter() { // para poder fechar a janela com clique sobre o X
            public void windowClosing(WindowEvent evt) { dispose(); }
            } );
    focusOK();
    }
  */
   
  // Chamado em: Exercicio. esconda()
  public WarningDialog (String [] comentarios) {
    montaWarning(comentarios);
    }

  // Chamado em: Exercicio. esconda()
  public WarningDialog (String comentario) {
    String [] strC = new String[1];
    strC[0] = comentario;
    montaWarning(strC);
    }
/*
    addWindowListener(new WindowAdapter() { // para poder fechar a janela com clique sobre o X
            public void windowClosing(WindowEvent evt) { dispose(); }
            } );
    setLayout (new BorderLayout());
    setBackground( BarraDeDesenho.fundo_azul_claro); //fundo_menu_primario ); //_secundario ); //fundo_titulo );
    setFont( new Font("Arial", Font.BOLD, 10) ); //PLAIN, 12) );

    ok = new Button ("Ok");
    ok.addFocusListener(this);
    ok.addKeyListener(this);
    //synchronized (this) {
    ok.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) { botaoOk(); }
        });
    //}
    p1.add (ok);
    //this.requestFocus(); // quem constrói este Frame dará o focus
    Panel p2 = new Panel(new FlowLayout());
    Label l = new Label (comentario);
    p2.add (l);
    add ("Center", p2);
    add ("South", p1);
    setSize(500,130);
    //java.awt.Container.setFocusOwner((Component)ok); //setFocus(ok); // setFocusOwner(java.awt.Component);
    setVisible(true);
    focusOK();
    }
*/


  private void montaWarning (String [] comentarios) {
    addWindowListener(new WindowAdapter() { // para poder fechar a janela com clique sobre o X
            public void windowClosing(WindowEvent evt) { dispose(); }
            } );
    int numL = comentarios.length;
    setLayout (new BorderLayout());
    setBackground(new Color(64, 128, 198)); // (BarraDeDesenho.fundo_azul_claro)
    setFont( new Font("Arial", Font.BOLD, 10) ); //PLAIN, 12) );

    ok = new Button ("Ok");
    ok.addFocusListener(this);
    ok.addKeyListener(this);
    //synchronized (this) {
    ok.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) { botaoOk(); }
        });
    //}
    p1.add (ok);
    //this.requestFocus(); // quem constrói este Frame dará o focus
    Panel p2 = new Panel(new GridLayout(numL,1));
    Label [] l = new Label[numL];
    for (int i=0; i<numL; i++) {
        l[i] = new Label(comentarios[i]);
        p2.add(l[i],i);
        }
    add("Center", p2);
    add("South", p1);
    setSize(500,120+(ALTURA*numL));
    //java.awt.Container.setFocusOwner((Component)ok); //setFocus(ok); // setFocusOwner(java.awt.Component);
    setVisible(true);
    focusOK();
    }

  protected void botaoOk () {
    setTitle("OK");
    dispose();
    }

  //public final void keyPressed(KeyEvent keyevent) {
  public synchronized void keyPressed(KeyEvent keyevent) {// é extented em JanelaArqExistente
    int keyCode = keyevent.getKeyCode();
    if ( keyCode == KeyEvent.VK_ENTER) {
       //System.out.println("OK - KeyPressed: "+ok.getActionCommand());
       //System.out.println("KeyPressed: "+ paramString());
       Button botao_ativo = (Button) keyevent.getSource();
       if ( botao_ativo == ok ) botaoOk();
       }
    //System.out.println("KeyPressed: ENTER="+keyCode); //keyevent.getKeyCode());    
    }   

  //public final void keyTyped(KeyEvent keyevent) {
  public void keyTyped(KeyEvent keyevent) { // é extented em JanelaArqExistente
    int keyCode = keyevent.getKeyCode();
    if ( keyCode == KeyEvent.VK_ENTER) {
       //System.out.println("KeyTyped: "+ paramString());
       Button botao_ativo = (Button) keyevent.getSource();
       if ( botao_ativo == ok ) botaoOk();
       }     
    //System.out.println("KeyTyped: "+keyCode); //keyevent.getKeyCode());    
    }
   
  public final void keyReleased(KeyEvent keyevent) {
    // System.out.println("KeyReleased: "+keyevent.getKeyCode());
    }
   
  public void focusGained (FocusEvent focusevent) {
    // System.out.println("focusGained: ganhou foco");
    }

  public void focusLost (FocusEvent focusevent) {
    }

}
