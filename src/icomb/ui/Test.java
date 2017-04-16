package icomb.ui;

import java.util.Vector;

public class Test {
	
	private static String fixo = "\\carta{7}{\\heartsuit},\\carta{8}{\\heartsuit},\\carta{9}{\\heartsuit},\\carta{10}{\\heartsuit},\\carta{J}{\\heartsuit},\\carta{Q}{\\heartsuit},\\carta{K}{\\heartsuit},\\carta{A}{\\heartsuit},\\carta{7}{\\diamondsuit},\\carta{8}{\\diamondsuit},\\carta{9}{\\diamondsuit},\\carta{10}{\\diamondsuit},\\carta{J}{\\diamondsuit},\\carta{Q}{\\diamondsuit},\\carta{K}{\\diamondsuit},\\carta{A}{\\diamondsuit},\\carta{A}{\\spadesuit},"; 

 
	private static String[] cartas = new String[]{"\\carta{7}{\\spadesuit}",
		"\\carta{8}{\\spadesuit}",
		"\\carta{9}{\\spadesuit}",
		"\\carta{10}{\\spadesuit}",
		"\\carta{J}{\\spadesuit}",
		"\\carta{Q}{\\spadesuit}",
		"\\carta{K}{\\spadesuit}"};
	
	public static void main(String[] args)
	{
		combinacao(new Vector(),4,3);
		
	}
	
	
	
	
	public static void combinacao(Vector vec, int p, int q)
	{
	   if (p==0 && q==0)
	     print(vec,"Q");
		   
	   
	   if (q>0)
	   {
		   Vector v = (Vector) vec.clone();
		   v.add("Q");
		   combinacao(v,p,q-1);  
	   }
	   
	   if (p>0)
	   {
		   Vector v = (Vector) vec.clone();
		   v.add("P");
		   combinacao(v,p-1,q);  
	   }
	   
	}
	
	public static void print(Vector vec, String letra)
	{
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < vec.size(); i++) {
			String aux = (String) vec.get(i);
			if (aux.equals(letra))
			{
				if (result.length() > 0)
					result.append(",");
					
				result.append(cartas[i]);
				
			}
			
				
		}
		System.out.println(fixo + result + "\\rbrace \\\\ \\hline");
	}
	
		
		
			
			
		
		
		
		
}
