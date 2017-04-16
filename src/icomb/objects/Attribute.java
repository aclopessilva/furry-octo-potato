package icomb.objects;
import java.util.Vector;


public class Attribute
{
	private String name;
	private String type;
	private Vector predicates;
	private Vector orders;

	public Attribute(String name)
	{
		this.name = name;
		predicates = new Vector();
		orders = new Vector();
	}


	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getName() {
		return name;
	}



	public Vector getOrders() {
		return orders;
	}



	public Vector getPredicates() {
		return predicates;
	}



	public void addPredicate(Predicate predicate)
	{
		predicates.add(predicate);
	}

	public void addOrder(Order order)
	{
		orders.add(order);
	}






}
