package icomb.util;


public class ListElement 
{
	String value;
	
	public ListElement(String value)
	{
		this.value = value;
	}
	
	public String toString()
	{
		if (!value.trim().equals(""))
			return I18n.getInstance().getString(value);
		return "";
	}
	
	public String getValue()
	{
		return value;
	}
	
	

}
