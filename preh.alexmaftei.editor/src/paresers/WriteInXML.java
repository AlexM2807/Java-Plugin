package paresers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import preh.alexmaftei.editor.classs.Data;
import preh.alexmaftei.editor.views.Editor;

public class WriteInXML
{
	private String path = Editor.path;
	public void createNode(String fileName, String nodeName)
	{
		try
		{

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			
			//MAC
			//Document doc = docBuilder.parse(path +"/"+fileName + ".arxml");

			// WINDWOS
			Document doc = docBuilder.parse(path +File.separator+fileName + ".arxml");
			NodeList rootList = doc.getElementsByTagName("CONTAINERS");
			Node root = rootList.item(0);

			Element eElement = (Element) root;

			// root elements

			Element ecuc = doc.createElement("ECUC-CONTAINER-VALUE");
			eElement.appendChild(ecuc);
			ecuc.setAttribute("UUID", "");

			Element short_name = doc.createElement("SHORT-NAME");
			ecuc.appendChild(short_name);
			short_name.setTextContent(nodeName);

			Element definistion_ref = doc.createElement("DEFINITION-REF");
			ecuc.appendChild(definistion_ref);

			definistion_ref.setAttribute("DEST", "ECUC-PARAM-CONF-CONTAINER-DEF");
			definistion_ref.setTextContent("/arpackage/EcucDefs/" + fileName + "/" + nodeName);

			if (nodeName.contains("General"))
			{
				Element paremeter_valeues = doc.createElement("PARAMETER-VALUES");
				ecuc.appendChild(paremeter_valeues);
			}
			else if (nodeName.contains("Config"))
			{
				Element sub_containers = doc.createElement("SUB-CONTAINERS");
				ecuc.appendChild(sub_containers);
			}

			// shorten way
			// staff.setAttribute("id", "1");

			// write the content into xml file

			Transformer transformerFactory = TransformerFactory.newInstance().newTransformer();
			// Transformer transformer = transformerFactory.newTransformer();
			transformerFactory.setOutputProperty(OutputKeys.INDENT, "yes");
			transformerFactory.setOutputProperty(OutputKeys.METHOD, "xml");
			transformerFactory.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			// StreamResult result = new StreamResult(new
			// File("/Users/alexandrumaftei/Desktop/"+fileName+".arxml"));
			StreamResult result = new StreamResult(new File(fileName + ".arxml"));
			// write the content into xml file

			transformerFactory.transform(source, result);



		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void CreateParameter(String fileName, String target, String newNode)
	{
		try
		{

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(fileName + ".arxml");

			NodeList rootList = doc.getElementsByTagName("CONTAINERS");
			Node root = rootList.item(0);

			NodeList parent = root.getChildNodes();

			for (int i = 0; i < parent.getLength(); i++)
			{
				NodeList child = parent.item(i).getChildNodes();

				for (int i1 = 0; i1 < child.getLength(); i1++)
				{
					if (child.item(i1).getNodeName().equals("SHORT-NAME"))
					{

						if (child.item(i1).getTextContent().equals(target))
						{

							NodeList nodeList = child.item(i1).getParentNode().getChildNodes();

							for (int i2 = 0; i2 < nodeList.getLength(); i2++)
							{
								if ((nodeList.item(i2).getNodeName().equals("PARAMETER-VALUES"))
										|| (nodeList.item(i2).getNodeName().equals("SUB-CONTAINERS")))
								{
									Node values = nodeList.item(i2);

									Element elem = (Element) values;

									Element ecuc = doc.createElement("ECUC-CONTAINER-VALUE");
									elem.appendChild(ecuc);

									Element short_name = doc.createElement("SHORT-NAME");
									ecuc.appendChild(short_name);
									short_name.setTextContent(newNode);

									Element definition_Ref = doc.createElement("DEFINITION-REF");
									ecuc.appendChild(definition_Ref);

									definition_Ref.setAttribute("DEST", "ECUC-PARAM-CONF-CONTAINER-DEF");
									definition_Ref.setTextContent(
											"/arpackage/EcucDefs/" + fileName + "/" + target + "/" + newNode);

									Element sub_containers = doc.createElement("SUB-CONTAINERS");
									ecuc.appendChild(sub_containers);

								}
							}
						}
					}
				}
			}

			// write the content into xml file

			Transformer transformerFactory = TransformerFactory.newInstance().newTransformer();
			// Transformer transformer = transformerFactory.newTransformer();
			transformerFactory.setOutputProperty(OutputKeys.INDENT, "yes");
			transformerFactory.setOutputProperty(OutputKeys.METHOD, "xml");
			transformerFactory.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			// StreamResult result = new StreamResult(new
			// File("/Users/alexandrumaftei/Desktop/"+fileName+".arxml"));
			
			//MAC
		//	StreamResult result = new StreamResult(new File(path+"/"+fileName + ".arxml"));
			
			// WINDOWS
			StreamResult result = new StreamResult(new File(path+File.separator+fileName + ".arxml"));
			// write the content into xml file

			transformerFactory.transform(source, result);

		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void save(String fileName, String target, List<Data> data, String definitionRef)
	{
	
	int ok = 0;
		try
		{

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			//MAC
			//Document doc = docBuilder.parse(path+"/"+fileName + ".arxml");
			// WINDWOS
			Document doc = docBuilder.parse(path+File.separator+fileName + ".arxml");

			NodeList rootList = doc.getElementsByTagName("SHORT-NAME");

			for (int i = 0; i < rootList.getLength(); i++)
			{
				if (rootList.item(i).getTextContent().equals(target))
				{
					

					Node parent = rootList.item(i).getParentNode();
					
					if(data.size() > 1)
						checkForParameterContainer(parent, doc);
					
					NodeList parentElements = parent.getChildNodes();

					for (int i1 = 0; i1 < parentElements.getLength(); i1++)
					{
						if(parentElements.item(i1).getNodeName().equals("SHORT-NAME"))
						{
							
							for(int d = 0 ; d < data.size();d++)
							{
								if(data.get(d).getName().equals("SHORT-NAME"))
								{
									
									parentElements.item(i1).setTextContent(data.get(d).getValue());
									data.remove(d);
								}
							}
						}
						
						
					else if (parentElements.item(i1).getNodeName().equals("PARAMETER-VALUES"))
						{
							ok++;
							Node param =parentElements.item(i1);
							NodeList parameterList = parentElements.item(i1).getChildNodes();
							for (int i2 = 0; i2 < parameterList.getLength(); i2++)
							{
								NodeList value = parameterList.item(i2).getChildNodes();
								for (int i3 = 0; i3 < value.getLength(); i3++)
								{
									if (value.item(i3).getNodeName().equals("DEFINITION-REF"))
									{

										String test = value.item(i3).getTextContent().toString();

										for (int d = 0; d < data.size(); d++)
										{
											
											if (test.substring(test.lastIndexOf("/") + 1).equals(data.get(d).getName()))
											{
												NodeList nodeToEdit = value.item(i3).getParentNode().getChildNodes();

												for (int i4 = 0; i4 < nodeToEdit.getLength(); i4++)
												{
													if (nodeToEdit.item(i4).getNodeName().equals("VALUE"))
													{
														nodeToEdit.item(i4).setTextContent(data.get(d).getValue());
														data.remove(d);
														
													}
												}
											}
										}

									}
								}
							}
							for(int x= 0 ; x< data.size();x++)
							{
						
								createParamContainer(doc,param, data.get(x).getName() , data.get(x).getValue(), data.get(x).getType() , definitionRef);
							}
						}

					}

					if(ok == 0)
					{
						
						Element ecucParam = doc.createElement("PARAMETER-VALUES");
						parent.appendChild(ecucParam);
						//save(fileName,target,data,definitionRef);
					}
				}
			}
			
			

				System.out.println("SAVE  THIS!");
				// write the content into xml file

				Transformer transformerFactory = TransformerFactory.newInstance().newTransformer();
				transformerFactory.setOutputProperty(OutputKeys.INDENT, "yes");
				transformerFactory.setOutputProperty(OutputKeys.METHOD, "xml");
				transformerFactory.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				DOMSource source = new DOMSource(doc);
			// MAC
				//StreamResult result = new StreamResult(new File(path+"/"+fileName + ".arxml"));
				//WINDWOS 
				StreamResult result = new StreamResult(new File(path+File.separator+fileName + ".arxml"));
				// write the content into xml file

				transformerFactory.transform(source, result);
			
		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void createParamContainer(Document doc,Node parent ,String name, String value, String type, String defRef)
	{
		
		String	paramType = null;
		String destType = null;
		
		if(type.equals("Boolean"))
		{
			paramType = "NUMERICAL";
			destType = "BOOLEAN";
		}
		else if(type.equals("Integer"))
		{
			paramType = "NUMERICAL";
			destType = "INTEGER";
		}
		else if(type.equals("Enumeration"))
		{
			paramType = "TEXTUAL";
			destType = "ENUMERATION";
		}
		
		Element ecucParam = doc.createElement("ECUC-"+paramType+"-PARAM-VALUE");
		parent.appendChild(ecucParam);
		
		Element definitionRef = doc.createElement("DEFINITION-REF");
		definitionRef.appendChild(doc.createTextNode(defRef+ name));
		ecucParam.appendChild(definitionRef);

		Attr a = doc.createAttribute("DEST");
		a.setValue("ECUC-"+destType+"-PARAM-DEF");
		definitionRef.setAttributeNode(a);

		// VALUE
		Element values = doc.createElement("VALUE");
		values.appendChild(doc.createTextNode(value));
		ecucParam.appendChild(values);
	
		
		
	}
	
	
	public void createEcucContainerValue(String fileName, String parentNode, String noteToCreate , String defRef)
	{
		try
		{

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			//MAC
		//	Document doc = docBuilder.parse(path +"/"+fileName + ".arxml");
			//WINDWOS
			Document doc = docBuilder.parse(path +File.separator+fileName + ".arxml");

			NodeList rootList = doc.getElementsByTagName("SHORT-NAME");
			
			for(int i = 0 ; i < rootList.getLength(); i++)
			{
				if(rootList.item(i).getTextContent().equals(parentNode))
				{
					NodeList parent = rootList.item(i).getParentNode().getChildNodes();
					
					for(int i1 = 0; i1 < parent.getLength(); i1 ++)
					{
						if(parent.item(i1).getNodeName().contains("CONTAINERS"))
						{
							Node subContainer = parent.item(i1);
							Element eElement = (Element) subContainer;

							// root elements

							Element ecuc = doc.createElement("ECUC-CONTAINER-VALUE");
							eElement.appendChild(ecuc);
							ecuc.setAttribute("UUID", "");
							
							Element short_name = doc.createElement("SHORT-NAME");
							ecuc.appendChild(short_name);
							short_name.setTextContent(noteToCreate);
							
							Element definistion_ref = doc.createElement("DEFINITION-REF");
							ecuc.appendChild(definistion_ref);

							definistion_ref.setAttribute("DEST", "ECUC-PARAM-CONF-CONTAINER-DEF");
							definistion_ref.setTextContent(defRef);
							ParsConfig parsConfig = new ParsConfig();
							
							if(parsConfig.checkForType(noteToCreate) != null)
							{
								if((parsConfig.checkForType(noteToCreate).equals("parameters")) || (parsConfig.checkForType(noteToCreate).equals("sub&param")) )
								{
									Element paremeter_valeues = doc.createElement("PARAMETER-VALUES");
									ecuc.appendChild(paremeter_valeues);
								}
								 if((parsConfig.checkForType(noteToCreate).equals("sub-containers")) || (parsConfig.checkForType(noteToCreate).equals("sub&param")))
								{
									Element sub_containers = doc.createElement("SUB-CONTAINERS");
									ecuc.appendChild(sub_containers);
								}
							}
						}
					}
				}
			}
			
			

			

			// write the content into xml file

			Transformer transformerFactory = TransformerFactory.newInstance().newTransformer();
			// Transformer transformer = transformerFactory.newTransformer();
			transformerFactory.setOutputProperty(OutputKeys.INDENT, "yes");
			transformerFactory.setOutputProperty(OutputKeys.METHOD, "xml");
			transformerFactory.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
//MAC
			//StreamResult result = new StreamResult(new File(path +"/"+ fileName + ".arxml"));
			//WINDWOS
			StreamResult result = new StreamResult(new File(path +File.separator+ fileName + ".arxml"));
			// write the content into xml file

			transformerFactory.transform(source, result);

		

		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	
	public boolean checkForDuplicate(String fileName, String target)
	{
		try
		{

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(path +File.separator+fileName + ".arxml");

			NodeList rootList = doc.getElementsByTagName("SHORT-NAME");
			
			for(int i = 0 ; i < rootList.getLength(); i++)
			{
				if(rootList.item(i).getTextContent().equals(target))
				{
					return true;
				}
			}
			

		}
		
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
	
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public void deleteNode(String fileName, String nodeName)
	{
		
		try
		{

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(path +File.separator+fileName + ".arxml");

		
			NodeList rootList = doc.getElementsByTagName("SHORT-NAME");
			
			for(int i  = 0 ; i < rootList.getLength() ; i ++)
			{
				if(rootList.item(i).getTextContent().equals(nodeName))
				{
					Node nodeToRemove =  rootList.item(i).getParentNode();
					
					Node parent = nodeToRemove.getParentNode();
					
					parent.removeChild(nodeToRemove);
					
					System.out.println("REMOVE");
					
					
				}
				
			}
			

		
			// write the content into xml file

			Transformer transformerFactory = TransformerFactory.newInstance().newTransformer();
	
			transformerFactory.setOutputProperty(OutputKeys.INDENT, "yes");
			transformerFactory.setOutputProperty(OutputKeys.METHOD, "xml");
			transformerFactory.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			
			StreamResult result = new StreamResult(new File(path+File.separator+fileName + ".arxml"));
			// write the content into xml file

			transformerFactory.transform(source, result);



		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
		}
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	
	public void checkForParameterContainer(Node node , Document doc)
	{
		
		NodeList childs = node.getChildNodes();
		int ok = 0 ;
		for(int i = 0 ; i < childs.getLength(); i++)
		{
			if(childs.item(i).getNodeName().equals("PARAMETER-VALUES"))
				ok++;
		}
		
		if(ok == 0)
		{
			Element paremeter_valeues = doc.createElement("PARAMETER-VALUES");
			node.appendChild(paremeter_valeues);
		}
	}
	
}
