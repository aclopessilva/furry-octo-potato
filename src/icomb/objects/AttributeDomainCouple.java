package icomb.objects;

public class AttributeDomainCouple {

	private Attribute atribute;
	private Domain domain;

	public AttributeDomainCouple(Attribute attribute, Domain domain)
	{
		this.atribute = attribute;
		this.domain = domain;
	}

	public Attribute getAtribute()
	{
		return atribute;
	}

	public Domain getDomain()
	{
		return domain;
	}
}
