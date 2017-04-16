/*
 * iMath Project
 * iComb: http://www.matematica.br/icomb
 * 
 * @description Choice options:
 * @see icomb/events/ObjectManager.java: static void defineCoresFonte(int corRGB, String texto, Component compPaiAWT, Component compAWT) define as cores
 * @author AKE, LOB
 *
 */

package icomb.components;

import icomb.util.I18n;
import icomb.util.Configuracoes;

import java.awt.Choice;
import java.awt.Component;
import java.util.Vector;

public class CustomChoice extends Choice {

  private static void setFontStatic (Component cmp) {
    cmp.setFont(Configuracoes.FONTE_BOTAO);
    // cmp.setForeground(Configuracoes.); - icomb/events/ObjectManager.java: static void defineCoresFonte(...)
    }

  private Vector keys;
  private Vector values;

  public CustomChoice (Vector keys) {
    this(keys,null);
    setFontStatic(this);
    }

  public CustomChoice (Vector keys, Vector val) {
    if (val == null) {
       populate(keys);
       setFontStatic(this);
       }
    else 
       include(keys,val); 
    }

  public void include (Vector keys, Vector val) {
    this.removeAll();
    // this.setForeground(Configuracoes.); - icomb/events/ObjectManager.java: static void defineCoresFonte(...)
    this.values = new Vector();
    this.keys = keys;
    for (int i=0 ;i<val.size();i++) {
    	String v = (String)val.get(i);
    	values.add(v);
    	super.add(v);
        }
    }

  //- enquanto testando... - usada em ' icomb/events/ObjectManager.java !criaCombo(Component,Vector,Vector)'
  public  boolean lst = false; //- eliminar! 

  // To debug
  // icomb.ui.StagePanel.itemStateChanged(ItemEvent)
  public void listCustomChoice () {
    System.out.println("CustomChoice.listCustomChoice(): #keys="+keys.size());
    for (int i=0; i<keys.size(); i++) {
        String key = (String)keys.get(i);
        String value = I18n.getInstance().getString(key);
        System.out.println(" "+i+": "+key+" = "+value);
        }
    }

  public void populate (Vector keys) {
    this.removeAll();
    // this.setForeground(Configuracoes.); - icomb/events/ObjectManager.java: static void defineCoresFonte(...)
    this.values = new Vector();
    this.keys = keys;
    //- String str1="", str2="";
    //- str1 = "CustomChoice: populate: "; str2 = "color="+this.getForeground();
    for (int i=0; i<keys.size(); i++) {
    	String key = (String)keys.get(i);
    	String value = ""; 
    	value = I18n.getInstance().getString(key);
        //- if (value.equals("valor")) {
        //-  System.out.println("CustomChoice.populate(Vector): fore="+this.getForeground()); lst=true;
        //-  try{String str=""; System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
        //-  }
    	values.add(value);
        // System.out.print(value+", ");
        //- str2 += "<"+key+"="+value+"> ";
    	super.add(value);
        }
    //- if (lst) System.out.println(str1+str2);
    }

  public void populate (Vector keys, Vector values) {
    this.removeAll();
    // this.setForeground(Configuracoes.); - icomb/events/ObjectManager.java: static void defineCoresFonte(...)
    this.values = new Vector();
    this.keys = keys;
    for (int i=0 ;i<keys.size();i++) {
    	String value = (String) values.get(i);
    	this.values.add(value);
    	super.add(value);
        }
    }


  public void select (String key) {
    int index=-1;
    for (int i=0 ;i<keys.size();i++) {
    	if (((String)keys.get(i)).equals(key)) {
    	   index = i;
    	   break;
           }
        }
    if (index>=0)
       super.select(index);
    }

  public String getSelectedKey () {
    if (this.getSelectedIndex()<0) 
       return null;

    String selectedValue = (String) values.get(this.getSelectedIndex());

    int index=-1;
    for (int i=0 ;i<values.size();i++) {
       if (((String)values.get(i)).equals(selectedValue)) {
          index = i;
          break;
          }
       }
    if (index>=0)
       return (String) keys.get(index);
    else
       return null;
    }

  public void add (String item)  {
    keys.add(item);
    values.add(item);
    super.add(item);
    }

  public void add (String key, String value) {
    keys.add(key);
    values.add(value);
    super.add(value);
    }

  }
