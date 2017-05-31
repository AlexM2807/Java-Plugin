package paresers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import preh.alexmaftei.editor.classs.Parameters;

import preh.alexmaftei.editor.views.Editor;


public class ParsConfig
{
//	private String filepath = "AUTOSAR_MOD_ECUConfigurationParameters.arxml";
	//private String filepath = Content.configARXML;
	private String filepath = Editor.configARXML;
	public Node findParaetersNode(String target)
	{

		try
		{
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			NodeList nodes = doc.getElementsByTagName("ELEMENTS");

			Element element = (Element) nodes.item(0);
			NodeList va = element.getElementsByTagName("SHORT-NAME");

			for (int i = 0; i < va.getLength(); i++)
			{

				if (va.item(i).getTextContent().equals(target))
				{
					NodeList parent = va.item(i).getParentNode().getChildNodes();

					for (int i1 = 0; i1 < parent.getLength(); i1++)
					{
						if (parent.item(i1).getNodeName().equals("PARAMETERS"))
						{
							return parent.item(i1);
						}
					}

				}

			}
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (SAXException sae)
		{
			sae.printStackTrace();
		}
		return null;

	}

	public List<Parameters> takeParameters(String target)
	{

		List<String> list = new ArrayList<String>();
		List<Parameters> listParam = new ArrayList<Parameters>();
		Parameters param;
		String parametersType;
		String shortName = null;
		int minValue = 0;
		int maxValue = 0;
		// NodeList paramList = findParaetersNode(parentNode,childNode);
		// paramList.item(index)
		Node paramList = findParaetersNode(target);

		param = new Parameters("TextField", "SHORT-NAME", list);
		listParam.add(param);

		if (paramList != null)
		{

			NodeList parameters = paramList.getChildNodes();
			for (int i = 0; i < parameters.getLength(); i++)
			{
				Node nNode = parameters.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					parametersType = parameters.item(i).getNodeName();

					if (parameters.item(i).getNodeName().toString().equals("ECUC-ENUMERATION-PARAM-DEF"))
					{
						NodeList root = parameters.item(i).getChildNodes();

						for (int r = 0; r < root.getLength(); r++)
						{
							Node nNode3 = root.item(r);
							if (nNode3.getNodeType() == Node.ELEMENT_NODE)
							{

								if ((root.item(r).getNodeName().equals("SHORT-NAME")))
								{
									shortName = root.item(r).getTextContent().toString();

									if (root.item(r).getTextContent().toString().equals(shortName))
									{

										NodeList parent = root.item(r).getParentNode().getChildNodes();
										for (int i1 = 0; i1 < parent.getLength(); i1++)
										{

											if (parent.item(i1).getNodeName().equals("LITERALS"))
											{

												NodeList literals = parent.item(i1).getChildNodes();

												for (int l = 0; l < literals.getLength(); l++)
												{
													if (literals.item(l).getNodeName()
															.equals("ECUC-ENUMERATION-LITERAL-DEF"))
													{
														NodeList enumeration = literals.item(l).getChildNodes();

														for (int e = 0; e < enumeration.getLength(); e++)
														{
															if (enumeration.item(e).getNodeName().equals("SHORT-NAME"))
															{
																String enumarationValue = enumeration.item(e)
																		.getTextContent().toString();

																list.add(enumarationValue);

															}
														}

													}
												}

											}
										}

										param = new Parameters(parametersType, shortName, list);
										listParam.add(param);
										list = new ArrayList<String>();

									}

								}

							}

						}

					}
					else if (parameters.item(i).getNodeName().toString().equals("ECUC-BOOLEAN-PARAM-DEF"))
					{
						NodeList root = parameters.item(i).getChildNodes();

						for (int r = 0; r < root.getLength(); r++)
						{
							Node nNode3 = root.item(r);
							if (nNode3.getNodeType() == Node.ELEMENT_NODE)
							{

								if ((root.item(r).getNodeName().equals("SHORT-NAME")))
								{
									shortName = root.item(r).getTextContent().toString();
								}
							}

						}
						param = new Parameters(parametersType, shortName);
						listParam.add(param);
					}
					else if (parameters.item(i).getNodeName().toString().equals("ECUC-INTEGER-PARAM-DEF"))
					{
						NodeList root = parameters.item(i).getChildNodes();

						for (int r = 0; r < root.getLength(); r++)
						{
							Node nNode3 = root.item(r);
							if (nNode3.getNodeType() == Node.ELEMENT_NODE)
							{

								if (root.item(r).getNodeName().equals("SHORT-NAME"))
								{
									shortName = root.item(r).getTextContent().toString();

									if (root.item(r).getTextContent().toString().equals(shortName))
									{

										NodeList parent = root.item(r).getParentNode().getChildNodes();
										for (int i1 = 0; i1 < parent.getLength(); i1++)
										{

											if (parent.item(i1).getNodeName().toString().equals("MIN"))
											{

												minValue = Integer.parseInt(parent.item(i1).getTextContent());

											}

											if (parent.item(i1).getNodeName().toString().equals("MAX"))
											{
												try
												{
													maxValue = Integer.parseInt(parent.item(i1).getTextContent());
												}
												catch (NumberFormatException ex)
												{ // handle your exception
													maxValue = 2147483647;
												}

											}
										}
										param = new Parameters(parametersType, shortName, minValue, maxValue);
										listParam.add(param);

									}
								}
							}
						}
					}
				}
			}
		}
		return listParam;
	}

	public List<String> takeValuesForPopUpMenu(String targetType)
	{
		List<String> list = new ArrayList<String>();
		try
		{
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			NodeList nodes = doc.getElementsByTagName("SHORT-NAME");

			for (int i = 0; i < nodes.getLength(); i++)
			{
				if (nodes.item(i).getTextContent().equals(targetType))
				{
					NodeList parent = nodes.item(i).getParentNode().getChildNodes();

					for (int i1 = 0; i1 < parent.getLength(); i1++)
					{
						if (parent.item(i1).getNodeName().contains("CONTAINERS"))
						{
							NodeList subContainers = parent.item(i1).getChildNodes();

							for (int i2 = 0; i2 < subContainers.getLength(); i2++)
							{
								NodeList childs = subContainers.item(i2).getChildNodes();
								for (int i3 = 0; i3 < childs.getLength(); i3++)
								{
									if (childs.item(i3).getNodeName().equals("SHORT-NAME"))
									{
										list.add(childs.item(i3).getTextContent().toString());
									}
								}

							}
						}
					}
				}
			}

			return list;
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (SAXException sae)
		{
			sae.printStackTrace();
		}
		return null;

	}

	public List<String> findAllModules()
	{
		List<String> list = new ArrayList<String>();
		try
		{
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			NodeList nodes = doc.getElementsByTagName("MODULE-REFS");

			Node parent = nodes.item(0);

			NodeList child = parent.getChildNodes();

			for (int i = 0; i < child.getLength(); i++)
			{
				if (child.item(i).getNodeName().equals("MODULE-REF"))
				{
					String name = child.item(i).getTextContent().toString();
			
					list.add(name.substring(name.lastIndexOf("/") + 1));
					
					
				}

			}

			return list;
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (SAXException sae)
		{
			sae.printStackTrace();
		}
		return null;

	}

	public String checkForType(String target)
	{
		
		int p = 0;
		int s = 0 ;
		try
		{
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			NodeList nodes = doc.getElementsByTagName("SHORT-NAME");

			for (int i = 0; i < nodes.getLength(); i++)
			{
				if (nodes.item(i).getTextContent().equals(target))
				{
					NodeList parent = nodes.item(i).getParentNode().getChildNodes();

					for (int i1 = 0; i1 < parent.getLength(); i1++)
					{
						if (parent.item(i1).getNodeName().equals("PARAMETERS"))
						{
							p++;
							
						}
						else if (parent.item(i1).getNodeName().equals("SUB-CONTAINERS"))
						{
							s++;
							
						}
					}
				}
			}

			if((p > 0) && (s > 0))
				return "sub&param";
			else if((p > 0) && (s == 0))
				return "parameters";
			else if((s > 0) && (p == 0))
				return "sub-containers";
			return null;
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		catch (SAXException sae)
		{
			sae.printStackTrace();
		}
		return null;

	}

}
