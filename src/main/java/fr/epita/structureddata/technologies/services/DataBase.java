package fr.epita.structureddata.technologies.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataBase {
	
	private Connection connection;

	private void getConnection() throws SQLException {
		try{
			this.connection.getSchema();
		}
		catch(Exception e){
			String connectionString = "jdbc:derby:memory:testDB;create=true";
			this.connection = DriverManager.getConnection(connectionString, "user", "test");
		}
		
	}
	
	public void createStudentTable() throws SQLException{
		getConnection();
		
		String tableCreation = "CREATE TABLE STUDENT ( "
				+ " ID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT STUDENT_PK PRIMARY KEY"
				+ ", FIRST_NAME VARCHAR(40)"
				+ ", LAST_NAME VARCHAR(40)"
				+ ", BIRTHDATE DATE"
				+ ")";
		PreparedStatement stmt = connection.prepareStatement(tableCreation);
		
		stmt.execute();
		stmt.close();
		
	}
	
	public void insertStudent() throws SQLException{
		getConnection();
		
		String creationString = "INSERT INTO STUDENT ( FIRST_NAME, LAST_NAME, BIRTHDATE )"
				+ "VALUES ('Thomas', 'Broussard', '1980-04-13')";
		PreparedStatement insertion = connection.prepareStatement(creationString);
		insertion.execute();
		insertion.close();
	}
	
	public ResultSet getStudents() throws SQLException
	{
		
		getConnection();
		
		String select="SELECT * FROM STUDENT";
		PreparedStatement selectStatement = connection.prepareStatement(select);
		
		return selectStatement.executeQuery();
//		selectStatement.close();
//		return rs;
	}
	
}
