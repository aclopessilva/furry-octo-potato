package icomb.objects;

import java.util.Vector;

public class SubReferential
{
	private Referential referential;
	private Vector attributeDomainCouples;

	public SubReferential(Referential referential)
	{
		this.referential = referential;
		attributeDomainCouples = new Vector();
	}

	public void addAttributeDomainCouple(AttributeDomainCouple attributeDomainCouple)
	{
		this.attributeDomainCouples.add(attributeDomainCouple);
	}

}
