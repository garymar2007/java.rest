package rest.inventory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import rest.dao.OracleDB;
import util.ToJSON;

@Path("/v1/inventory")
public class V1_inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPcParts() throws Exception{
		PreparedStatement query = null;
		Connection conn = null;
		String returnString = null;
		Response rb = null;
		
		try{
			conn = OracleDB.OracleDBConn().getConnection();
			query = conn.prepareStatement("Select * from PC_PARTS");
			
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			json = converter.toJSONArray(rs);
			
			query.close();
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if (conn != null)
				conn.close();
		}
		
		return rb;
	}
}
