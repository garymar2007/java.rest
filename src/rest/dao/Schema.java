package rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;

import util.ToJSON;

/**
 * This java class will hold all the sql queries from episode 5 and onward.
 * V1_inventory.java and V1_status.java will not use this class for its sql code
 * since they were created before episode 5.
 * 
 * Having all sql/database code in one package makes it easier to maintain and audit
 * but increase complexity.
 * 
 * Note: we also used the extends Oracle308tube on this java class to inherit all
 * the methods in Oracle308tube.java
 * 
 * @author gary.ma
 */
public class Schema extends OracleDB{
	
	/**
	 * To work out the next primary key for table PC_PARTS
	 * @return
	 * @throws Exception
	 */
	private int getNextValue() throws Exception{
		PreparedStatement query = null;
		Connection conn = null;
		try{
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("select max(PC_PARTS_PK) from PC_PARTS");
			ResultSet rs = query.executeQuery();
			if(rs.next()){
				return rs.getInt(1) + 1;
			}else{
				return 1;
			}
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}finally{
			if(conn != null)
				conn.close();
		}
	}

	
	public int insertIntoPC_PARTS(String PC_PARTS_TITLE, String PC_PARTS_CODE, 
			String PC_PARTS_MAKER, String PC_PARTS_AVAIL,String PC_PARTS_DESC)
					throws Exception{
		PreparedStatement query = null;
		Connection conn = null;
		
		try{
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("insert into PC_PARTS (PC_PARTS_PK, PC_PARTS_TITLE, "
					+ "PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC) "
					+ "VALUES(?,?,?,?,?,?) ");
			query.setInt(1, getNextValue());
			query.setString(2, PC_PARTS_TITLE);
			query.setString(3, PC_PARTS_CODE);
			query.setString(4, PC_PARTS_MAKER);
			int avilInt = Integer.parseInt(PC_PARTS_AVAIL);
			query.setInt(5, avilInt);
			query.setString(6, PC_PARTS_DESC);
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			return 500;
		}finally{
			if(conn != null)
				conn.close();
		}
		
		return 200;
	}
	
	/**
	 * This method will search for a specific brand from the PC_PARTS table.
	 * By using prepareStatement and the ?, we are protecting against sql injection
	 * 
	 * Never add parameter straight into the prepareStatement
	 * 
	 * @param brand - product brand
	 * @return - json array of the results from the database
	 * @throws Exception
	 */
	public JSONArray queryReturnBrandParts(String brand) throws Exception{
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try{
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, "
					+ "PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC "
					+ "from PC_PARTS where UPPER(PC_PARTS_MAKER) = ?");
			query.setString(1,  brand.toUpperCase());
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close();
		}catch(SQLException sqlError){
			sqlError.printStackTrace();
			return json;			
		}catch(Exception e){
			e.printStackTrace();
			return json;
		}finally{
			if(conn != null)
				conn.close();
		}
		
		return json;
	}
	
	/**
	 * This method will search for the specific brand and the brands item number in
	 * the PC_PARTS table.
	 * 
	 * By using prepareStatement and the ?, we are protecting against sql injection
	 * 
	 * Never add parameter straight into the prepareStatement
	 * 
	 * @param brand - product brand
	 * @param item_number - product item number
	 * @return - json array of the results from the database
	 * @throws Exception
	 */
	public JSONArray queryReturnBrandItemNumber(String brand, int itemNumber) throws Exception{
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try{
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, "
					+ "PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC "
					+ "from PC_PARTS where UPPER(PC_PARTS_MAKER) = ? and PC_PARTS_CODE=?");
			query.setString(1,  brand.toUpperCase());
			query.setInt(2,	itemNumber);
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close();
		}catch(SQLException sqlError){
			sqlError.printStackTrace();
			return json;			
		}catch(Exception e){
			e.printStackTrace();
			return json;
		}finally{
			if(conn != null)
				conn.close();
		}
		
		return json;
	}
	
	/**
	 * This method will return all PC parts.
	 * Done pre-episode 6
	 * 
	 * @return - all PC parts in json format
	 * @throws Exception
	 */
	public JSONArray queryAllPcParts() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("select PC_PARTS_PK, PC_PARTS_TITLE, PC_PARTS_CODE, PC_PARTS_MAKER, PC_PARTS_AVAIL, PC_PARTS_DESC " +
											"from PC_PARTS");
			
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			System.out.println("JSON ARRAY: " + json.toString());
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return json;
	}
	
	/**
	 * This method will return a time/stamp from database.
	 * Done pre-episode 6
	 * 
	 * @return time/stamp in json format
	 * @throws Exception
	 */
	public JSONArray queryCheckDbConnection() throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		ToJSON converter = new ToJSON();
		JSONArray json = new JSONArray();
		
		try {
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("select to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') DATETIME " +
											"from sys.dual");
			
			ResultSet rs = query.executeQuery();
			
			json = converter.toJSONArray(rs);
			query.close(); //close connection
		}
		catch(SQLException sqlError) {
			sqlError.printStackTrace();
			return json;
		}
		catch(Exception e) {
			e.printStackTrace();
			return json;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return json;
	}
	
	/**
	 * This method allows you to delete a row from PC_PARTS table
	 * 
	 * If you need to do a delete, consider moving the data to a archive table, then
	 * delete. Or just make the data invisible to the user.  Delete data can be
	 * very dangerous.
	 * 
	 * @param pk
	 * @return
	 * @throws Exception
	 */
	public int deletePC_PARTS(int pk) throws Exception{
		PreparedStatement query = null;
		Connection conn = null;
		
		try{
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("delete from PC_PARTS where PC_PARTS_PK= ?");
			query.setInt(1, pk);
			query.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return 200;
	}
	
	/**
	 * This method allows you to update PC_PARTS table
	 * 
	 * Note: there is no validation being done... if this was a real project you
	 * must do validation here!
	 * 
	 * @param pk
	 * @param avail
	 * @return
	 * @throws Exception
	 */
	public int updatePC_PARTS(int pk, int avail) throws Exception {
		
		PreparedStatement query = null;
		Connection conn = null;
		
		try {
			/*
			 * If this was a real application, you should do data validation here
			 * before updating data.
			 */
			
			conn = oraclePcPartsConnection();
			query = conn.prepareStatement("update PC_PARTS " +
											"set PC_PARTS_AVAIL = ? " +
											"where PC_PARTS_PK = ? ");
			
			query.setInt(1, avail);
			query.setInt(2, pk);
			query.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
			return 500;
		}
		finally {
			if (conn != null) conn.close();
		}
		
		return 200;
	}
	
}
