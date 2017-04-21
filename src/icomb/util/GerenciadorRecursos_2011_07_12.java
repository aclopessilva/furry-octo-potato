///* 
// * iMath Project
// * iComb: http://www.matematica.br/icomb
// * 
// * @description Para gerar tela de abertura do iComb
// * @author AEK, LOB
// *
// */
//
//package icomb.util;
//
//import icomb.IComb;
//
//import java.awt.Image;
//import java.awt.Toolkit;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//
//import base.MontaFace;
//
//public class GerenciadorRecursos {
//
//  public static Image getImagem (MontaFace mf,String nomeCompletoImagem) {
//    Image image = null;
//    if (mf.getApplet()!=null && IComb.isApplet()) try {
//      // E um applet
//      return mf.getApplet().getImage(mf.getApplet().getCodeBase(), nomeCompletoImagem);
//      } catch(Exception e) { 
//        System.err.println("Error: in GR, failed in applet while geting image "+nomeCompletoImagem+": "+e);
//        //e.printStackTrace();
//        return null;
//        }
//    else try {
//      // E uma aplicacao
//      return Toolkit.getDefaultToolkit().getImage(nomeCompletoImagem);
//      } catch (Exception e1) {
//        System.err.println("Error: in GR, failed in while geting image "+nomeCompletoImagem+": "+e1);
//        //e1.printStackTrace();
//        return null;
//        }
//    }
//
//
//  public static InputStream getInputStream (MontaFace mf,String nomeCompletoArquivo) throws FileNotFoundException {
//    // System.out.println("GerenciadorRecursos: mf.getApplet()="+mf.getApplet()+", nomeCompletoArquivo="+nomeCompletoArquivo);
//    Image image = null;
//    if (mf.getApplet()!=null)
//      try {
//        // E um applet
//        InputStream is =  mf.getApplet().getClass().getResourceAsStream("/" +nomeCompletoArquivo);
//        if (is == null) {
//      	  File locationFile = new File(nomeCompletoArquivo);
//          return new FileInputStream(locationFile);
//          }
//        // System.out.println("GerenciadorRecursos: is="+is+" -> "+mf.getApplet().getClass());
//        return is;
//      } catch (Exception e) {
//        System.err.println("Error: in GR, failed in applet while opening image "+nomeCompletoArquivo+": "+e);
//        //
//e.printStackTrace();
//        return null;
//        }
//    else
//      try {
//        // E uma aplicacao
//        File locationFile = new File(nomeCompletoArquivo);
//        return new FileInputStream(locationFile);
//       } catch (Exception e) {
//        System.err.println("Error: in GR, failed while opening image "+nomeCompletoArquivo+": "+e);
//        return null;
//        }
//    }
//
//  public static String clearFileName (String nome) {
//    String sep, token = "";
//    boolean lista = false;
//    sep = "/";
//    if (lista) System.out.println("GerenciadorRecursos.clearFileName(String nome) nome="+nome+", sep=<"+sep+">");
//    java.util.StringTokenizer st = new java.util.StringTokenizer(nome, sep);
//    while (st.hasMoreTokens()) {
//      if (lista) System.out.println(" "+token);
//         token = st.nextToken();
//         }
//    return token;
//    }
//
//  // Verify if "name" describe a valid file, readable
//  public static boolean isFile (String name) {
//    File arq;
//    try {
//      arq = new File(name);
//      return arq.isFile();
//    } catch (Exception e) {	  
//      return false;                                                                                                                 
//      }
//    }
//
//  private static String fileName = "";
//  public static String getFileName () { return fileName; }
//
//  // Try to read a file by comand line arguments
//  // To be used by: 
//  public static java.io.File getFileArg (String [] args) {
//    File fileArg = null;
//
//    String diretorio = "", str_temp, name_arq = "", nameStr = "";
//    int ind = -1;
//
//    // Verifica se um dos parâmetros é nome de arquivo: devolve o primeiro
//    for (int i=0; i<args.length; i++) {
//       str_temp = args[i];
//       if (isFile(str_temp)) {
//          System.out.println("Open file...: "+str_temp); // "Abrir arquivo..." + " é arquivo válido");
//          name_arq = str_temp;
//          ind = i;
//          break;
//          }
//       else {
//          // System.out.println(i+": "+str_temp+" NÃO é arquivo");
//          }
//       }
//
//    if (ind>-1) {
//       fileArg = new File(name_arq);
//       if (!fileArg.isAbsolute()) {
//          fileArg = new File(".",name_arq);
//          diretorio = fileArg.getAbsolutePath();
//          }
//       if (!fileArg.isFile()) {
//          fileArg = new File(name_arq + ".cmb");
//          if (!fileArg.isAbsolute()) {
//             diretorio = fileArg.getAbsolutePath();
//             fileArg = new File(".",name_arq);
//             }
//          }
//       // nameStr = fileArg.getName();
//       fileName = clearFileName(diretorio+"/"+fileArg.getName()); // file to be load...
//       }
//    // System.out.println(" nameStr="+nameStr+" fileArg="+fileArg);
//    // try{String str=""; System.out.println(str.charAt(3));}catch(Exception e){e.printStackTrace();}
//    return fileArg;
//    } // static java.io.File getFileArg(String [] args)
//
//  // Get String form 'java.io.InputStream'
//  // icomb.IComb.lerDeURL(...)
//  public static String getStringFile (java.io.InputStream inputStream, String strURL) {
//    java.io.InputStreamReader inputstreamreader = new java.io.InputStreamReader(inputStream);
//    java.io.StringWriter stringwriter = new java.io.StringWriter();
//    int numbytes = 8192;
//    char[] cs = new char[numbytes];
//    try {
//      for (;;) {
//         int j = inputstreamreader.read(cs, 0, numbytes);
//         if (j == -1)
//            break;
//         stringwriter.write(cs, 0, j);
//         }
//      stringwriter.close();
//      inputStream.close();
//    } catch (java.io.IOException ioexception) {
//      System.err.println("Error: reading in 'GerenciadorRecursos': "+strURL+": "+ioexception); //throw Trace.error(34, ioexception.getMessage());
//      }
//    return stringwriter.toString();
//    }
//  
//
//  }
