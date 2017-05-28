package fr.epita.sd.databases.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import fr.epita.structureddata.technologies.services.ConvertToXML;
import fr.epita.structureddata.technologies.services.DataBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TestDatabase {
	@Inject
	DataBase db;
	@Inject
	ConvertToXML xml;
	
	@Test
	public void firstDbTest() throws SQLException, IOException
	{
		db.createStudentTable();
		//		Connection connection = getConnection();
//		
//		String tableCreation = "CREATE TABLE STUDENT ( "
//				+ " ID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT STUDENT_PK PRIMARY KEY"
//				+ ", FIRST_NAME VARCHAR(40)"
//				+ ", LAST_NAME VARCHAR(40)"
//				+ ", BIRTHDATE DATE"
//				+ ")";
//		PreparedStatement stmt = connection.prepareStatement(tableCreation);
//		
//		stmt.execute();
//		stmt.close();
		
		db.insertStudent();
		
//		String creationString = "INSERT INTO STUDENT ( FIRST_NAME, LAST_NAME, BIRTHDATE )"
//				+ "VALUES ('Thomas', 'Broussard', '1980-04-13')";
//		PreparedStatement insertion = connection.prepareStatement(creationString);
//		insertion.execute();
//		insertion.close();

		ResultSet rs = db.getStudents();
		
//		String select="SELECT * FROM STUDENT";
//		PreparedStatement selectStatement = connection.prepareStatement(select);
//		
//		ResultSet rs = selectStatement.executeQuery();
		while(rs.next())
		{
			
			System.out.println(rs.getInt(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getString("LAST_NAME"));
			System.out.println(rs.getDate(4));
		}
	  rs.close();
//		selectStatement.close();
//		connection.close();
			
	}


		
	@Test
	public void createXML() throws ParserConfigurationException, SQLException, TransformerException, IOException
	{
		
		ResultSet rs = db.getStudents();
		xml.writeXML(rs);
	}
}
	
	/*
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			
			Connection connection = db.getConnection();
			ResultSet rs = getStudents(connection);
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			
			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(metaData.getTableName(1));//"students");
			doc.appendChild(rootElement);

			
			while(rs.next()){
				// staff elements
				Element student = doc.createElement("Row");//"Student");
				rootElement.appendChild(student);
				for(int i = 1; i<= colCount; i++){
					String columnName = metaData.getColumnName(i);
					Object value = rs.getObject(i);
					Element elemt = doc.createElement(columnName);
					elemt.setTextContent(String.valueOf(value));
					student.appendChild(elemt);
				}
//				// set attribute to staff element
//				Attr attr = doc.createAttribute("id");
//				attr.setValue(String.valueOf(rs.getInt("ID")));
//				student.setAttributeNode(attr);
//
//				// firstname elements
//				Element firstname = doc.createElement("firstname");
//				firstname.setTextContent(rs.getString("FIRST_NAME"));
//				student.appendChild(firstname);
//
//				// lastname elements
//				Element lastname = doc.createElement("lastname");
//				lastname.setTextContent(rs.getString("LAST_NAME"));
//				student.appendChild(lastname);
//				
//				//birthdate elements
//				Element birthdate = doc.createElement("birthdate");
//				birthdate.setTextContent(rs.getString("BIRTHDATE"));
//				student.appendChild(birthdate);
			}
			rs.close();
			connection.close();
			
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

		  File file = new File("XML/student-list.xml");
		  file.getParentFile().mkdirs();
		  if(!file.exists())
		  {
		  	file.createNewFile();
		  }
		  FileWriter fw = new FileWriter(file);
		  fw.write(result);
		  fw.flush();
		  fw.close();
			
		}
		
//	public ResultSet getStudents(Connection connection) throws SQLException
//	{
//		String select="SELECT * FROM STUDENT";
//		PreparedStatement selectStatement = connection.prepareStatement(select);
//		
//		ResultSet rs = selectStatement.executeQuery();
//		return rs;
//	}
//		
		
		
		
		
		
   
}
*/
