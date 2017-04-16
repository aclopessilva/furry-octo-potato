package icomb.ui;

import icomb.IComb;
import icomb.events.ObjectManager;
import icomb.objects.Exercicio;
import icomb.util.Configuracoes;
import icomb.util.Constants;
import icomb.util.I18n;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FinalPanel extends Panel  implements ActionListener {

  private TextArea label;
  private SetMachinePanel pai;
  private Button btnSend;


  public FinalPanel(SetMachinePanel pai) {

    this.pai = pai;
    //setBackground(new Color(230,230,230));
    // try { System.out.println("FinalPanel:"); String str = null; System.out.println(str.charAt(3));
    // }catch(Exception e) {e.printStackTrace();}
    setLayout(new BorderLayout());
    label = new TextArea(ObjectManager.setMachine.getStringFinal().toString(),2,5,TextArea.SCROLLBARS_NONE);
    label.setBackground(new Color(255,255,128));
    label.setForeground(Color.BLACK);
    label.setEditable(false);

    Panel leftPanel = new Panel(new BorderLayout()) {
      public Insets getInsets() {
        return new Insets(0,0,0,5);
        }
      };
    leftPanel.add(label,BorderLayout.CENTER);
    add(leftPanel,BorderLayout.CENTER);

    Panel footer = new Panel(new BorderLayout());
    footer.setBackground(Configuracoes.CINZA);
    btnSend = ObjectManager.criaBotao(footer,
    		I18n.getInstance().getString("send"),
    		I18n.getInstance().getString("send-dica"));
    
    
    if (ObjectManager.setMachine.isOnline() || IComb.notSend()) 
       btnSend.setEnabled(false); 
    else 
       btnSend.setEnabled(true);
    btnSend.addActionListener(this);
    footer.add(btnSend,BorderLayout.EAST);
    add(footer,BorderLayout.SOUTH);

    this.setVisible(true);
    }

  public Dimension getPreferredSize () {
    return new Dimension(Constants.PANEL_WIDTH, 503);
    }

  public void paint (Graphics g) {
	g.setColor(Color.black);
    g.drawRect(2, 2, getSize().width-4, getSize().height-4);
    }

  public Insets getInsets () {
    return new Insets(20,5,5,5);
    }

  public void actionPerformed (ActionEvent arg0) {
    this.pai.enviar();
    }

  }
