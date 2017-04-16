/*
 * iMath Project
 * iComb: http://www.matematica.br/icomb
 * 
 * Help framework
 * @autho RP, LOB
 */

package ajuda.visao.navegador;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class PainelTopicosAjuda extends Panel implements MouseListener {

 Label itemPrincipal;
 private final int lineHeight = 20;
 Dimension d = new Dimension(210, lineHeight);
 Vector listaSubItens = new Vector();
 Vector listaAuxiliar = new Vector();
 private boolean show = true;
 
 public PainelTopicosAjuda (String s) {
  setLayout(new GridLayout(0, 1));
  itemPrincipal = new Label(s);
  itemPrincipal.addMouseListener(this);
  add(itemPrincipal);
  
  addSubItem("dois");
  addSubItem("um");
  addSubItem("tres");
  }
 
 public Dimension getPreferredSize () {
  return d;
  }
 
 public void addSubItem (String subItem) {
  String space = "     ";
  Label l = new Label(space+subItem);
  l.addMouseListener(this);
  listaSubItens.addElement(l);
  listaAuxiliar.addElement(l);
  add(l);
  addLine();
  }
 
 private void addLine () {
  d.height += lineHeight;
  validate();
  }
 
 private void ocultaSubItens () {
  listaSubItens.removeAllElements();
  }
 
 private void exibeSubItens () {
  listaSubItens = (Vector)listaAuxiliar.clone();
  }
 
 public Label getItemPrincipal () {
  return itemPrincipal;
  }
 
 public Vector getSubItemList () {
  return listaSubItens;
  }

 public void mouseClicked (MouseEvent e) {
  Label l = (Label) e.getSource();
  if (l == itemPrincipal){
   if (show)
    ocultaSubItens();
   else
    exibeSubItens();
   show = !show;
   }  
  }

 public void mouseEntered (MouseEvent e) { }
 public void mouseExited (MouseEvent e) { }
 public void mousePressed (MouseEvent e) { }
 public void mouseReleased (MouseEvent e) { }
 
 public void adicionaItens (Frame f) {
  for (int i = 0; i < listaSubItens.size(); i++) {
   f.add((Label)listaSubItens.elementAt(i));
   }
  }

 public static void main (String[] args) {
  PainelTopicosAjuda ip = new PainelTopicosAjuda("Principal");
  Frame f = new Frame();
  f.setLayout(new GridLayout(0,1));
  f.setVisible(true);
  
  try {
   Thread.sleep(2000);
   f.add(ip.getItemPrincipal());
   ip.adicionaItens(f);
   f.validate();
   f.pack();
   
   Thread.sleep(2000);
   f.add(ip.getItemPrincipal());
   ip.adicionaItens(f);
   ip.ocultaSubItens();
   f.validate();
   f.pack();
   
   Thread.sleep(2000);
   f.add(ip.getItemPrincipal());
   ip.adicionaItens(f);
   ip.exibeSubItens();
   f.validate();
   f.pack();

   } catch (InterruptedException e) { }
  }

 }
