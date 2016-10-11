package com.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

public interface SDHacks {
	
	// check if server has pending requests
	@GET
	@Path("/poll")
	public Response poll();
	
//	// create new group
//	@POST
//	@Path("/createGroup/{ip}/{port}")
//	public Response createGroup(@PathParam("ip") String ip, @PathParam("port") Integer port); 
//	
//	// join an existing group
//	@PUT
//	@Path("/joinGroup/{ip}/{port}/{groupId}")
//	public Response joinGroup(@PathParam("ip") String ip, @PathParam("port") Integer port, @PathParam("groupId") Integer groupId);
//	
	// send request to server for download
    @POST
    @Path("/download/{id}")
    public Response initRequest(@PathParam("id") Integer userId, @QueryParam("link") String link, @QueryParam("size") Integer size);

    // to know if request is finished/pending/processing
    @GET
    @Path("/getStatus/{id}")
    public Response getStatus(@PathParam("id") Integer id);

    // to know the detail status of request
    @GET
    @Path("/getInfo/{id}")
    public Response getInfo(@PathParam("id") Integer id);
    
    // user "id" has downloaded its part
    @PUT
    @Path("/done/{id}")
    public Response userIsDone(@PathParam("id") Integer id);
    
    // inform the server of the bandwidth of the user through the download response time 
    @PUT
    @Path("/response-time/{id}/{t}")
    public Response setResponseTime(@PathParam("id") Integer id, @PathParam("t") Integer responseTime); 
    
}
