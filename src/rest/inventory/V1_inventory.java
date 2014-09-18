package rest.inventory;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import rest.dao.Schema;

@Path("/v1/inventory")
public class V1_inventory {

	/**
	 * This method will return all computer parts tha tare listed in PC_PARTS
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnAllPcParts() throws Exception{
		Schema dao = new Schema();
		String returnString = dao.queryAllPcParts().toString();
		
		return Response.ok(returnString).build();
	}
}
