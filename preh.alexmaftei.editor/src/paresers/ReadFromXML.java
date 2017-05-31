package paresers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import preh.alexmaftei.editor.views.Editor;

public class ReadFromXML extends Editor
{
	// private String path = "/Users/alexandrumaftei/Desktop/Modules";

	private String path = Editor.path;

	public HashMap<String, String> takePinValueToHasMap(String filename, String pinNameToTakeValue)
	{
		HashMap<String, String> hmap;
		String val = null;
		String key = null;
		try
		{
			// MAC
			// String filepath = path + "/"+filename + ".arxml";

			// WINDOWS
			String filepath = path + File.separator + filename + ".arxml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			hmap = new HashMap<String, String>();
			NodeList nodes = doc.getElementsByTagName("SUB-CONTAINERS");

			Element element = (Element) nodes.item(0);
			NodeList ecucContainerValue = element.getChildNodes();

			for (int x = 0; x < ecucContainerValue.getLength(); x++)

			{

				NodeList values = ecucContainerValue.item(x).getChildNodes();

				for (int i = 0; i < values.getLength(); i++)
				{

					if (values.item(i).getNodeName().toString().equals("SUB-CONTAINERS"))
					{
						NodeList subContaienrsValues = values.item(i).getChildNodes();
						for (int i1 = 0; i1 < subContaienrsValues.getLength(); i1++)
						{

							NodeList ecucContainerValues = subContaienrsValues.item(i1).getChildNodes();

							for (int i2 = 0; i2 < ecucContainerValues.getLength(); i2++)
							{

								if (ecucContainerValues.item(i2).getNodeName().toString().equals("SHORT-NAME"))
								{
									if (ecucContainerValues.item(i2).getTextContent().equals(pinNameToTakeValue))
									{
										Node p = ecucContainerValues.item(i2).getParentNode();

										NodeList child = p.getChildNodes();
										for (int x1 = 0; x1 < child.getLength(); x1++)
										{

											if (child.item(x1).getNodeName().toString().equals("SHORT-NAME"))
											{
												key = child.item(x1).getNodeName().toString();
												val = child.item(x1).getTextContent();
												hmap.put(key, val);
											}
											else if (child.item(x1).getNodeName().toString().equals("PARAMETER-VALUES"))
											{
												NodeList parametersValues = child.item(x1).getChildNodes();
												for (int i3 = 0; i3 < parametersValues.getLength(); i3++)
												{
													NodeList value = parametersValues.item(i3).getChildNodes();
													for (int i4 = 0; i4 < value.getLength(); i4++)
													{
														if (value.item(i4).getNodeName().equals("VALUE"))
														{
															val = value.item(i4).getTextContent();
														}
														else if (value.item(i4).getNodeName().equals("DEFINITION-REF"))
														{

															String test = value.item(i4).getTextContent().toString();
															key = test.substring(test.lastIndexOf("/") + 1);

														}
														else if ((key != null) && (val != null))
															hmap.put(key, val);
													}
												}

											}
										}

									}
								}

							}

						}
					}
				}

			}

			return hmap;

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

	public LinkedHashMap<String, String> takeParameterToHashMap(String filename, String target)
	{
		LinkedHashMap<String, String> hmap;
		String val = null;
		String key = null;
		try
		{
			// MAC
			// String filepath = path + "/"+filename + ".arxml";

			// WINDOWS
			String filepath = path + File.separator + filename + ".arxml";

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			hmap = new LinkedHashMap<String, String>();
			NodeList nodes = doc.getElementsByTagName("SHORT-NAME");

			for (int i = 0; i < nodes.getLength(); i++)
			{
				if (nodes.item(i).getTextContent().toString().equals(target))
				{
					Node parent = nodes.item(i).getParentNode();

					NodeList child = parent.getChildNodes();
					for (int i1 = 0; i1 < child.getLength(); i1++)
					{

						if (child.item(i1).getNodeName().toString().equals("SHORT-NAME"))
						{
							key = child.item(i1).getNodeName().toString();
							val = child.item(i1).getTextContent();

							hmap.put(key, val);

						}
						if (child.item(i1).getNodeName().toString().equals("PARAMETER-VALUES"))
						{
							NodeList parameters = child.item(i1).getChildNodes();
							for (int i2 = 0; i2 < parameters.getLength(); i2++)
							{
								NodeList parameterChild = parameters.item(i2).getChildNodes();
								for (int i3 = 0; i3 < parameterChild.getLength(); i3++)
								{
									if (parameterChild.item(i3).getNodeName().equals("VALUE"))
									{
										val = parameterChild.item(i3).getTextContent();
									}
									else if (parameterChild.item(i3).getNodeName().equals("DEFINITION-REF"))
									{

										String test = parameterChild.item(i3).getTextContent().toString();
										key = test.substring(test.lastIndexOf("/") + 1);

									}
									else if ((key != null) && (val != null))
										hmap.put(key, val);
								}
							}
						}
					}
				}
			}

			return hmap;

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

	public boolean checkIfHaveParmeterChild(String filename, String target)
	{

		try
		{
			// MAC
			// String filepath = path + "/"+filename + ".arxml";

			// WINDOWS
			String filepath = path + File.separator + filename + ".arxml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			NodeList nodes = doc.getElementsByTagName("SHORT-NAME");

			for (int i = 0; i < nodes.getLength(); i++)
			{
				if (nodes.item(i).getTextContent().toString().equals(target))
				{
					Node parent = nodes.item(i).getParentNode();

					NodeList child = parent.getChildNodes();
					for (int i1 = 0; i1 < child.getLength(); i1++)
					{

						if (child.item(i1).getNodeName().toString().equals("PARAMETER-VALUES"))
						{
							return true;
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
		return false;
	}

}
