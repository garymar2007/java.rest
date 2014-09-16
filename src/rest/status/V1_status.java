package rest.status;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import java.sql.*;
import rest.dao.*;

/**
 * This is the root path for our restful api service
 * In the web.sml file, we specified that /api/* need to be in the URL to 
 * get to this class.
 * 
 * We are versioning the class in the URL path.  This is the first version v1.
 * Example how to get to the root of this api resource:
 * http://localhost:8080/rest/api/v1/status
 * 
 * @author gary.ma
 *
 */
@Path("/v1/status")
public class V1_status {

	private static final String api_version="00.01.00";
	
	/**
	 * This method sits at the rrot of the api.  It will return the name 
	 * of this api.
	 * 
	 * @return String - Title of the api
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle(){
		return "<p>Java Web Service</p>";
	}
	
	/**
	 * This method will return the version number of the api
	 * Note: this is nested one down from the root.  you will need to add version
	 * into the URL path.
	 * 
	 * Examp:
	 * http://localhost:8080/rest/api/v1/status/version
	 * 
	 * @return String - version number of the api
	 */
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion(){
		return "<p>Version: </p>" + api_version;
	}
	
	@Path("/database")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returenDatabaseStatus() throws Exception{
		PreparedStatement query = null;
		String myString = null;
		String returnString = null;
		Connection conn = null;
		try{
			conn = OracleDB.OracleDBConn().getConnection();
			query = conn.prepareStatement("select to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS') DATETIME from sys.dual");
			ResultSet rs = query.executeQuery();
			
			while(rs.next()){
				myString = rs.getString("DATETIME");
			}
			
			query.close();//close the connection
			
			returnString = "<p>Database status</p>" + "<p>Database Date/Time return: " + myString + "</p>";
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null)
				conn.close();
		}
		
		return returnString;
	}
}
