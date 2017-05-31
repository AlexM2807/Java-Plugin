package preh.alexmaftei.editor.classs;

import java.util.HashMap;
import java.util.List;

public class TreeElements
{
	private String name;
	private String type;
	private HashMap<String, List> hashMap;
	List<String> list;
	private TreeElements parent;
	private List<TreeElements> childs;
	
	
	public List<TreeElements> getChilds()
	{
		return childs;
	}
	public void setChilds(List<TreeElements> childs)
	{
		this.childs = childs;
	}
	
	public void addChilds(TreeElements childs)
	{
		this.childs.add(childs);
	}
	public TreeElements getParent()
	{
		return parent;
	}
	public void setParent(TreeElements parent)
	{
		this.parent = parent;
	}
	public List<String> getList()
	{
		return list;
	}
	public void setList(List<String> list)
	{
		this.list = list;
	}
	public TreeElements(String name, HashMap<String, List> hashMap, String  type)
	{
		this.name = name;
		this.hashMap = hashMap;
		this.type = type;
	}
	public TreeElements(String name, List<String> list ,TreeElements parent )
	{
		this.name = name;
		this.list = list;
		this.parent = parent;
		
		
	}
	public TreeElements()
	{
		// TODO Auto-generated constructor stub
	}
	
	
	public TreeElements(String name, HashMap<String, List> hashMap)
	{
		super();
		this.name = name;
		this.hashMap = hashMap;
	}
	public TreeElements(String name, List<String> list)
	{
	this.name = name;
	this.list = list;
	}
	public HashMap<String, List> getHashMap()
	{
		return hashMap;
	}
	public void setHashMap(HashMap<String, List> hashMap)
	{
		this.hashMap = hashMap;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	
	
	
	
	
}
