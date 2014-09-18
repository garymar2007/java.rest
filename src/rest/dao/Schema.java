package rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.codehaus.jettison.json.JSONArray;

import util.ToJSON;

public class Schema extends OracleDB{
	
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
}
