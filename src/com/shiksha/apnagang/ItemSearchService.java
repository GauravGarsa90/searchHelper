package com.shiksha.apnagang;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/ItemSearchService")
public class ItemSearchService {
//	ItemSearchDAO isd = new ItemSearchDAO();
	@GET
//	@Path("/itemSearch:.+")
	@Produces(MediaType.APPLICATION_JSON)
	public String search(
			@QueryParam("searchString") String searchString, 
			@DefaultValue("All") @QueryParam("category") String category,
			@DefaultValue("0") @QueryParam("minPrice") String minPrice,
			@QueryParam("maxPrice") String maxPrice,
			@DefaultValue("available") @QueryParam("availability") String availability
			){
		ItemSearch is = new ItemSearch(searchString, category, minPrice, maxPrice, availability);
		return searchString + " in " + category + " in range " + minPrice + "INR to " + maxPrice + "INR that is " + availability;
	}
}
