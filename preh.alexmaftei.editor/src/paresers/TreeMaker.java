package paresers;

import java.io.File;
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

import TreeModel.TreeElement;

public class TreeMaker
{

	public TreeElement takeTreeValues(String path, String file)
	{
		String shortName = null;
		String type = null;
		TreeElement treeElement;
		String definitionRef = "/arpackage/EcucDefs/" + file + "/";
		// TreeElements tree;
		TreeElement rootTree = new TreeElement(file, file, file, definitionRef, "file");
		try
		{
			// MAC
			//String filepath = path + "/" + file + ".arxml";

			// WINDOWS
			String filepath = path + File.separator + file + ".arxml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			List<TreeElement> listToCheck = new ArrayList<TreeElement>();
			NodeList nodes = doc.getElementsByTagName("CONTAINERS");

			Element element = (Element) nodes.item(0);
			NodeList ecucContainerValue = element.getChildNodes();

			for (int i = 0; i < ecucContainerValue.getLength(); i++)
			{

				if (ecucContainerValue.item(i).hasChildNodes())
				{

					NodeList values = ecucContainerValue.item(i).getChildNodes();
					for (int i1 = 0; i1 < values.getLength(); i1++)
					{

						if (values.item(i1).getNodeName().equals("SHORT-NAME"))
						{
							shortName = values.item(i1).getTextContent();
							// list.add(name);
						}

						if (values.item(i1).getNodeName().equals("DEFINITION-REF"))
						{

							String test = values.item(i1).getTextContent().toString();
							
							 type = test.substring(test.lastIndexOf("/") + 1);
							

						}
						if ((shortName != null) && (type != null))
						{
							String ref = rootTree.getDefinitionRef() + type + "/";
							treeElement = new TreeElement(shortName, type, file, ref, "container");
							rootTree.add(treeElement);
							listToCheck.add(treeElement);
							shortName = null;
							type = null;
						}
					}

				}

			}

			while (listToCheck.size() != 0)
			{

				listToCheck = takeShortName(doc, listToCheck, file);

			}

			return rootTree;

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

	public boolean checkForSubContainer(Node node)
	{

		NodeList current = node.getChildNodes();
		for (int i = 0; i < current.getLength(); i++)
		{
			if (current.item(i).getNodeName().equals("SUB-CONTAINERS"))
			{
				return true;
			}

		}
		return false;
	}

	public List<TreeElement> takeShortName(Document doc, List<TreeElement> treeElem, String fileName)
	{

		String shortName = null;
		String type = null;
		TreeElement treeElement;
		List<TreeElement> newListToCheck = new ArrayList<TreeElement>();
		NodeList sub = doc.getElementsByTagName("SHORT-NAME");

		for (int l = 0; l < treeElem.size(); l++)
		{
			for (int i = 0; i < sub.getLength(); i++)
			{

				if (treeElem.get(l).getShortName().equals(sub.item(i).getTextContent().toString()))
				{

					NodeList parent = sub.item(i).getParentNode().getChildNodes();

					for (int s = 0; s < parent.getLength(); s++)
					{
						if (parent.item(s).getNodeName().equals("SUB-CONTAINERS"))
						{
							NodeList subCon = parent.item(s).getChildNodes();
							for (int i1 = 0; i1 < subCon.getLength(); i1++)
							{
								NodeList child = subCon.item(i1).getChildNodes();

								for (int i2 = 0; i2 < child.getLength(); i2++)
								{
									if (child.item(i2).getNodeName().equals("SHORT-NAME"))
									{
										shortName = child.item(i2).getTextContent();
										// list.add(name);
									}

									if (child.item(i2).getNodeName().equals("DEFINITION-REF"))
									{

										String test = child.item(i2).getTextContent().toString();
										type = test.substring(test.lastIndexOf("/") + 1);

									}
									if ((shortName != null) && (type != null))
									{
										String definitionRef = treeElem.get(l).getDefinitionRef() + type + "/";
										treeElement = new TreeElement(shortName, type, fileName, definitionRef,
												"container");
										treeElem.get(l).add(treeElement);

										newListToCheck.add(treeElement);
										shortName = null;
										type = null;
									}

								}

							}
						}

					}

				}

			}

			// treeElem.addChilds(treeE);

		}

		return newListToCheck;

	}

}
