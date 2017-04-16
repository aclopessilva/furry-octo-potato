package icomb.ui;


import icomb.components.CustomList;
import icomb.util.Util;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;
import java.util.Vector;





public class ListFrame extends PopUpFrame implements ActionListener, ItemListener
{
	private Button btnExit;
	private CustomList listContent;
	private StagePanel father;
	private Vector listeners;

	public ListFrame(StagePanel father)
	{

		super("Selecionar Opções");
		this.father = father;
		setSize(200,200);
		setLocationRelativeTo(father);
//		setAlwaysOnTop(true);
		setVisible(false);
		
		listeners = new Vector();
		Panel panel= new Panel(new FlowLayout());
		
		btnExit = new Button("OK");
		btnExit.setEnabled(false);
		panel.add(btnExit);
		btnExit.addActionListener(this);
		listContent = new CustomList(new Vector());
		listContent.setMultipleMode(true);
		listContent.addItemListener(this);
		ScrollPane scroll = new ScrollPane();
		scroll.add(listContent);
		add(scroll,BorderLayout.CENTER);
		add(panel,BorderLayout.SOUTH);

	}
	
	public void removeListeners()
	{
		listeners = new Vector();
	}
	
	public void addListFrameListener(IListFrameListener listener)
	{
		listeners.add(listener);
	}

	public void actionPerformed(ActionEvent e) {
	
		fireEvents(createSetString());
		
		setVisible(false);
	}

	private String createSetString()
	{
		String[] selectedValues = listContent.getSelectedKeys();
		StringBuffer sb = new StringBuffer("{");
		boolean first=true;
		for(int i=0; i<selectedValues.length ; i++)
		{
			String element = selectedValues[i];
			if (first)
				first=false;
			else
				sb.append(",");

			sb.append(element);
		}
		sb.append("}");
		return sb.toString();
	}
	
	public void changeModel(Vector keys)
	{
		listContent.populate(keys);
		
	}

	public void changeModel(Vector keys, Vector values)
	{
		listContent.populate(keys,values);
		
	}
	
	public void mark(String setString)
	{
		Set set = Util.produceStringSet(setString);
		listContent.select(set);

		
		
	}


	
	public void fireEvents(String setString)
	{
		for (int i = 0; i<listeners.size();i++) {
			IListFrameListener listener  =(IListFrameListener) listeners.get(i);
			listener.setStringList(setString);
		}

	}

	public void itemStateChanged(ItemEvent e) {
		if (listContent.getSelectedIndexes().length>0)
			btnExit.setEnabled(true);
		else
			btnExit.setEnabled(false);		
	}

}
