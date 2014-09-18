package rest.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.*;
import javax.sql.*;

/**
 * This class returns the Oracle database connect object from
 * a CentOS Oracle Express Virtual Machine
 * 
 * The method and variable in this class are static to save resources
 * You only need one instance of this class running.
 * 
 * This was explained in episode 3 of the Java Rest Tutorial Series on YouTube
 * 
 * We can some significant changes to this episode 5.
 * 
 * @author gary.ma
 *
 */
public class OracleDB {

	private static DataSource m_oracleDB = null;
	private static Context m_context = null;

	/**
	 * This is a public method that will return the 308tube database connection.
	 * 
	 * On Episode 5, I discussed that this method should not be private instead of public.
	 * This will make sure all sql/database relate code be place in the dao package.
	 * I am not doing this because I do not want to break the previous code... since this
	 * is just a tutorial project.
	 * 
	 * Pre-episode 6, updated this to a private scope, as it should be. That means, V1_inventory
	 * and V1_status methods needs to be updated.
	 * 
	 * @return Database object
	 * @throws Exception
	 */
	public static DataSource OracleDBConn() throws Exception{
		try{
			if (m_context == null){
				m_context = new InitialContext();
			}
			
			m_oracleDB = (DataSource)m_context.lookup("oracleDatabase");
			
		}catch(Exception e){
			throw e;
		}
		
		return m_oracleDB;
	}
	
	/**
	 * This method will return the connection to the Oracle 308tube schema
	 * Note that the scope is protected which means only java class in the
	 * dao package can use this method.
	 * 
	 * @return Connection to 308tube Oracle database.
	 */
	protected static Connection oraclePcPartsConnection(){
		Connection conn = null;
		try{
			conn = OracleDB.OracleDBConn().getConnection();
			return conn;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return conn;
	}

	/**
	 * Test method
	 * @param args
	 */
	public static void main(String[] args){
		try {
			
			Connection conn = OracleDB.OracleDBConn().getConnection();
			
			String sql = "select count(*) from AR$ARAGREEMENT;";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println(rs.getInt(1));
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
