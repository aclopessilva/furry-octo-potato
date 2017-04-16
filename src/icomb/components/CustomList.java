package icomb.components;

import icomb.util.I18n;

import java.awt.Choice;
import java.awt.List;
import java.util.Set;
import java.util.Vector;

public class CustomList extends List 
{
	private Vector keys;
	private Vector values;

	public CustomList()
	{
		this(new Vector());
	}
	public CustomList(Vector keys)
	{
		populate(keys);
	}

	
	public void populate(Vector keys)
	{
        this.removeAll();
		this.values = new Vector();
		this.keys = keys;
		for(int i=0 ;i<keys.size();i++)
		{
			String value = I18n.getInstance().getString((String)keys.get(i)); 
			values.add(value);
			super.add(value);
						
		}
	}
	

	public void populate(Vector keys, Vector values)
	{
        this.removeAll();
		this.values = new Vector();
		this.keys = keys;
		for(int i=0 ;i<keys.size();i++)
		{
			String value = (String) values.get(i); 
			this.values.add(value);
			super.add(value);
						
		}
	}
	
	public void select(String key)
	{
		int index=-1;
		for(int i=0 ;i<keys.size();i++)
		{
			
			if (((String)keys.get(i)).equals(key))
			{
				index = i;
				break;
			}
		}
		if (index>=0)
			super.select(index);
	}
	
	public void select(Set set)
	{
		for(int i=0 ;i<keys.size();i++)
		{
			if (set.contains(keys.get(i)))
			{
				select(i);
			}
		}
	}
		
	
	public String[] getSelectedKeys()
	{
		int[] selectedValues = this.getSelectedIndexes();
		String[] selectedKeys = new String[selectedValues.length];
		for (int i = 0; i < selectedValues.length; i++) {
			selectedKeys[i] = (String) keys.get(selectedValues[i]);
		}
		return selectedKeys;
		
	}
	
	
	public String getSelectedKey()
	{
		if (this.getSelectedIndex()<0) 
			return null;
		
		String selectedValue = (String) values.get(this.getSelectedIndex());
		
		int index=-1;
		for(int i=0 ;i<values.size();i++)
		{
			
			if (((String)values.get(i)).equals(selectedValue))
			{
				index = i;
				break;
			}
		}
		if (index>=0)
			return (String) keys.get(index);
		else
			return null;
		
	}
	
	
	public void add(String item) 
	{
		keys.add(item);
		values.add(item);
		super.add(item);
		
	}
	
	public void add(String key, String value)
	{
		keys.add(key);
		values.add(value);
		super.add(value);
	}
	
	
	public void removeAll()
	{
		keys = new Vector();
		values = new Vector();
		super.removeAll();
		
	}
	
	
	
	
	
	
	
	
	
	

}
