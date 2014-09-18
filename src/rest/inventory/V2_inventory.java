package rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
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
				return Response.status(400).entity("Error : please specify brand for this search").build();
			
			Schema sm = new Schema();
			json = sm.queryReturnBrandParts(brand);
			
			returnString = json.toString();
			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
	
	@Path("/{brand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(@PathParam("brand") String brand) throws Exception{
		String returnString = null;
		JSONArray json = new JSONArray();
		try{
			Schema sm = new Schema();
			json = sm.queryReturnBrandParts(brand);
			returnString = json.toString();					
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		return Response.ok(returnString).build();
	}
	
	@Path("/{brand}/{itemNumber}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandItemNumber(
			@PathParam("brand") String brand,
			@PathParam("itemNumber") int itemNumber)throws Exception{
		String returnString = null;
		JSONArray json = new JSONArray();
		try{
			Schema sm = new Schema();
			json = sm.queryReturnBrandItemNumber(brand, itemNumber);
			returnString = json.toString();					
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		return Response.ok(returnString).build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception{
		String returnString = null;
		Schema dao = new Schema();
		
		try{
			System.out.println("incomingData: " + incomingData);
			ObjectMapper mapper = new ObjectMapper();
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);
			int httpCode =	dao.insertIntoPC_PARTS(itemEntry.PC_PARTS_TITLE, 
					itemEntry.PC_PARTS_CODE, 
					itemEntry.PC_PARTS_MAKER, 
					itemEntry.PC_PARTS_AVAIL, 
					itemEntry.PC_PARTS_DESC);
			
			if(httpCode == 200){
				returnString = "Item inserted";
			}
			else{
				return Response.status(500).entity("Unable to process Item").build();
			}
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}

class ItemEntry{
	public String PC_PARTS_TITLE;
	public String PC_PARTS_CODE;
	public String PC_PARTS_MAKER;
	public String PC_PARTS_AVAIL;
	public String PC_PARTS_DESC;
}
