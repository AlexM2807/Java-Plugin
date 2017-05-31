package paresers;

import java.io.File;
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

import preh.alexmaftei.editor.views.Editor;

public class FileGenerator extends Editor
{

	private String path = Editor.path;
	//private String path = "/Users/alexandrumaftei/Desktop/Modules/";
	
	public void createFile(String fileName ,String uuid)
	{

		try
		{

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			
			// root elements
			
			Element rootElement = doc.createElement("AUTOSAR");
			doc.appendChild(rootElement);

			
			Element ar_packages = doc.createElement("AR-PACKAGES");
			rootElement.appendChild(ar_packages);
			
			// staff elements
			Element ar_package = doc.createElement("AR-PACKAGE");
			ar_packages.appendChild(ar_package);

			// set attribute to staff element
			Attr attr = doc.createAttribute("UUID");
			attr.setValue(uuid);
			ar_package.setAttributeNode(attr);

			// shorten way
			// staff.setAttribute("id", "1");

			
			Element short_name = doc.createElement("SHORT-NAME");
			ar_package.appendChild(short_name);

			short_name.setTextContent("arpackage");
			
			Element elements = doc.createElement("ELEMENTS");
			ar_package.appendChild(elements);

			Element ecuc = doc.createElement("ECUC-MODULE-CONFIGURATION-VALUES");
			elements.appendChild(ecuc);
			
			Element short_name1 = doc.createElement("SHORT-NAME");
			ecuc.appendChild(short_name1);
			
			short_name1.setTextContent(fileName);
			
			Element definistion_ref = doc.createElement("DEFINITION-REF");
			ecuc.appendChild(definistion_ref);
			
			definistion_ref.setAttribute("DEST", "ECUC-MODULE-DEF");
			definistion_ref.setTextContent("/arpackage/EcucDefs/"+fileName);
			
			Element containers = doc.createElement("CONTAINERS");
			ecuc.appendChild(containers);
			
			
			
			

			
			// write the content into xml file
			
			
			
			Transformer transformerFactory = TransformerFactory.newInstance().newTransformer();
			// Transformer transformer = transformerFactory.newTransformer();
			transformerFactory.setOutputProperty(OutputKeys.INDENT, "yes");
			transformerFactory.setOutputProperty(OutputKeys.METHOD, "xml");
			transformerFactory.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			
			// MAC 
			//StreamResult result = new StreamResult(new File(path+"/"+fileName+".arxml"));

			// WINDOWS 
			StreamResult result = new StreamResult(new File(path+File.separator+fileName+".arxml"));
			
			// write the content into xml file

			transformerFactory.transform(source, result);		
			


			System.out.println("File saved!");

		}
		catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		}
		catch (TransformerException tfe)
		{
			tfe.printStackTrace();
		}
	}

	
	public void deleteFile(File folder ,String target)
	{
		for (final File fileEntry : folder.listFiles())
		{

				if (fileEntry.getName().toString().contains(".arxml"))
				{
					String fileName = fileEntry.getName().toString().replaceFirst(".arxml", "");
					
					if(fileName.equals(target))
					{
						fileEntry.delete();
						
					}
				}

		}
		
	}
}
