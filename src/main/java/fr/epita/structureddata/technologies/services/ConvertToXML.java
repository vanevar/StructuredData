package fr.epita.structureddata.technologies.services;

import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConvertToXML {
	
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;

	public void initConverter() throws ParserConfigurationException{
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = docFactory.newDocumentBuilder();
	}
	
	
	public void writeXML(ResultSet rs) throws ParserConfigurationException, SQLException, TransformerFactoryConfigurationError, TransformerException
	{
		if(docBuilder == null)
		{
			initConverter();
		}
		ResultSetMetaData metaData = rs.getMetaData();
		int colCount = metaData.getColumnCount();
		
	  // root elements
		Document doc = docBuilder.newDocument();
	  Element rootElement = doc.createElement(metaData.getTableName(1));
		doc.appendChild(rootElement);
    
		
		while(rs.next()){
			// staff elements
			Element student = doc.createElement("Row");
			rootElement.appendChild(student);
			for(int i = 1; i<= colCount; i++){
				String columnName = metaData.getColumnName(i);
				Object value = rs.getObject(i);
				Element elemt = doc.createElement(columnName);
				elemt.setTextContent(String.valueOf(value));
				student.appendChild(elemt);
		  }
	  }
		rs.close();
		
		// write the content into xml file
	
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");	
		transformer.setOutputProperty("{htttp://sml.pache.org.xslt}ident-amount", "2");
		
		StringWriter writer = new StringWriter();
		DOMSource source = new DOMSource(doc.getDocumentElement());
		StreamResult sResult = new StreamResult(writer); //new File("XML/student-list.xml"));

		transformer.transform(source, sResult);

		String result = writer.toString();
		System.out.println(result);
	}
}
