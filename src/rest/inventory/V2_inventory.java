package rest.inventory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;

import rest.dao.Schema;

@Path("v2/inventory")
public class V2_inventory {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(
			@QueryParam("brand") String brand)
			throws Exception{
		
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try{
			if(brand == null)
				return Response.status(400).entity("Error: please specify the value for the search").build();
			
			Schema sm = new Schema();
			json = sm.queryReturnBrandParts(brand);
			
			returnString = json.toString();
			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}
