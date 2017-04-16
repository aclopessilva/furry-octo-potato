package icomb.objects;

import icomb.util.Util;

import java.util.HashSet;
import java.util.Set;


public class SetElement
{
	private Element[] elements;


	public SetElement(int numberOfElements)
	{
		elements =  new Element[numberOfElements];
	}

	public void addElement(int index, Element element)
	{
		elements[index]= element;
	}


	public Set extract(int n,Expressao[] conditions)
	{
		Set filter = new HashSet();

		int[] permutation = Util.permutation(elements.length);

		for(int i=0 ; i < permutation.length;i++)
		{

			boolean ok = true;
			for(int j=0;j<conditions.length;j++)
			{
			   ok = ok && conditions[j].evaluate(elements[permutation[i]]);
			   if (!ok) break;
			}

			if (ok)
			{
				filter.add(elements[permutation[i]]);
				if (filter.size()==n)
					break;
			}

		}
		return filter;
	}

}
