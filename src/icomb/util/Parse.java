/*
 * iComb - Interactive Combinatory on the Internet: http://www.matematica.br/icomb
 *
 * Free interactive solutions to teach and learn
 *
 * iMath Project: http://www.matematica.br
 * LInE           http://line.ime.usp.br
 *
 * @author AEK, LOB
 * @description Parser to construct the Universe set from any iComb file
 * @see
 *
 * @credits This source is free and provided by iMath Project (University of São Paulo - Brazil).
 * In order to contribute, please, contact the LInE coordinator Leônidas O. Brandão.
 *
 */

package icomb.util;

import icomb.objects.Universo;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class Parse {

 // Name could be: "baralho/baralho.xml" or "futebol/futebol.xml"
 // From: 'src/icomb/ui/ChooseUniversePanel.java, src/icomb/util/Parse.java, src/icomb/machine/SetMachine.java'
 public static Universo parseUniverso (String nomeDoArquivo) {
  // System.out.println("\nParse.parseUniverso: "+nomeDoArquivo);
  // try{String str="";System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
  InputStream is = null;
  try {
   System.err.println(icomb.IComb.debugMsg("icomb/util/Parse.java: parseUniverso(...)")+"Trying to load the universe '"+nomeDoArquivo+"'");
   if (!icomb.IComb.getISJAR()) // is not application with command line 'java -jar iComb.jar'
      is = new BufferedInputStream(new FileInputStream(nomeDoArquivo));
   else // it is application with command line 'java -jar iComb.jar'
      is = icomb.IComb.class.getClassLoader().getResourceAsStream(nomeDoArquivo);
  } catch (FileNotFoundException e1) {
   System.err.println(icomb.IComb.debugMsg("icomb/util/Parse.java: parseUniverso(...)")+"Error: trying to load the universe '"+nomeDoArquivo+"': " + e1.toString());
   e1.printStackTrace();
   }
  return parseUniverso(is, nomeDoArquivo);
  }

 public static Universo parseUniverso (InputStream is, String univFileName) {
  return load(is,univFileName);
  }

 /**
  * Leitura do arquivo de colecao
  * @throws RuntimeException quando ocorrem problemas no "analisar sintatico"
  * RuntimeException when quando arquivo na existe
  * From: 'icomb.util.Parse.parseUniverso' from 'icomb.machine.SetMachine.loadUniverso(MontaFace)'
  */
 private static Universo load (InputStream is, String univFileName) {
  // System.out.println("\nParse.load: "+univFileName);
  Universo universo = new Universo();
  try {
   DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
   DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
   Document doc = docBuilder.parse (is);

   doc.getDocumentElement().normalize();

   NodeList listOfUniversos = doc.getElementsByTagName("universo");
   Element elementUniverso = (Element) listOfUniversos.item(0);
   String nomeUniverso = elementUniverso.getAttribute("nome");
   universo.setNome(nomeUniverso);

   NodeList listOfElements = doc.getElementsByTagName("elemento");
   for (int s=0; s<listOfElements.getLength(); s++) {
    Node element = listOfElements.item(s);
    if (element.getNodeType() == Node.ELEMENT_NODE) {
     Element elementElement = (Element) element;
     String elementName = elementElement.getAttribute("nome");
     String imagem = elementElement.getAttribute("imagem");
     //D System.out.println("Parse.java: elementName="+elementName+", imagem="+imagem);

     icomb.objects.Element iCombElement = new icomb.objects.Element(elementName);
     iCombElement.setImagem(imagem);

     NodeList listOfAttributes = elementElement.getElementsByTagName("atributo");
     for (int i=0; i<listOfAttributes.getLength(); i++) {
      Node attribute = listOfAttributes.item(i);
      if (attribute.getNodeType() == Node.ELEMENT_NODE) {
       Element attributeElement = (Element) attribute;
       String attributeName = attributeElement.getAttribute("nome");

       NodeList listOfValues = attributeElement.getElementsByTagName("valor");
       if (listOfValues.getLength()>1)
        throw new RuntimeException("Estrutura com problemas");

       Node value = listOfValues.item(0);
       Element  valueElement = (Element) value;
       String strValue = valueElement.getChildNodes().item(0).getNodeValue().trim();

       Vector vecPredicates = new Vector();
       NodeList listOfPredicates = attributeElement.getElementsByTagName("predicado");
       for (int j=0; j<listOfPredicates.getLength(); j++) {
        Node predicate = listOfPredicates.item(j);
        if (predicate.getNodeType() == Node.ELEMENT_NODE) {
         Element predicateElement = (Element) predicate;
         String strPredicate = predicateElement.getChildNodes().item(0).getNodeValue().trim();
         vecPredicates.add(strPredicate);
         }
        }
       iCombElement.addAttribute(attributeName, strValue, vecPredicates);

       } // if (attribute.getNodeType() == Node.ELEMENT_NODE)

      } // for (int i=0; i<listOfAttributes.getLength(); i++)

     universo.addElemento(iCombElement);
     } // if (element.getNodeType() == Node.ELEMENT_NODE)

    } // for (int s=0; s<listOfElements.getLength(); s++)

   }
  catch (ParserConfigurationException e) {
   e.printStackTrace();
   throw new RuntimeException("Problema de sintaxe. O arquivo  deve estar corrompido.",e);
  } catch (SAXException e) {
   e.printStackTrace();
   throw new RuntimeException("Problema de sintaxe. O arquivo  deve estar corrompido.",e);
  } catch (IOException e) {
   throw new RuntimeException("O arquivo  não existe.",e);
   }
  return universo;

  } // static Universo load(InputStream is)

 }
