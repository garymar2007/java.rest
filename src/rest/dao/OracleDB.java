package rest.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.*;
import javax.sql.*;

public class OracleDB {

	private static DataSource m_oracleDB = null;
	private static Context m_context = null;

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
