package icomb.objects;

import java.util.ArrayList;
import java.util.Vector;

public class Referential
{

	private String name;
	private Vector attributes;
	private Vector subReferentials;

	public Referential(String name)
	{
		this.name = name;
	}

	public void addAttribute(Attribute attribute)
	{
		this.attributes.add(attribute);
	}

	public void addSubReferential(SubReferential subReferential)
	{
		this.subReferentials.add(subReferential);
	}



}
