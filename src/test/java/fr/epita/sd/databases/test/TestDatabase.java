package fr.epita.sd.databases.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

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
		
		db.insertStudent();
		
		ResultSet rs = db.getStudents();
		
		while(rs.next())
		{
			
			System.out.println(rs.getInt(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getString("LAST_NAME"));
			System.out.println(rs.getDate(4));
		}
	  rs.close();
	}


		
	@Test
	public void createXML() throws ParserConfigurationException, SQLException, TransformerException, IOException
	{
		
		ResultSet rs = db.getStudents();
		xml.writeXML(rs);
	}
	
	@Test
	public void testXLST() throws TransformerException, URISyntaxException, IOException
	{
		TransformerFactory tFactory = TransformerFactory.newInstance();

	//Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File styleSheet = ResourceUtils.getFile(this.getClass().getResource("/CdCollectionStylesheet.xsl"));
				//new File(classLoader.getResource("CdCollectionStylesheet.xsl").getFile());
		
    Transformer transformer =
      tFactory.newTransformer
         (new javax.xml.transform.stream.StreamSource
            (styleSheet));

    File xmlSrc = ResourceUtils.getFile(this.getClass().getResource("/CdCollection.xml"));
    		//new File(classLoader.getResource("CdCollection.xml").getFile());
    
    StringWriter writer = new StringWriter();
    
    File html = new File(("/Users/vanessavargas/Documents/EPITA/S8/Structured Data/Project1/CdCollection.html"));
    html.getParentFile().mkdirs();
		if (!html.exists()){
			html.createNewFile();
		}
    
    transformer.transform
      (new javax.xml.transform.stream.StreamSource
            (xmlSrc),
       new javax.xml.transform.stream.StreamResult
            ( writer)); //new FileOutputStream("/CdCollection.html")));
    String result = writer.toString();
    FileWriter fw = new FileWriter(html);
		fw.write(result);
		fw.flush();
		fw.close();
    
    System.out.println(result);
	}
}
	
