package org.acme;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	//for hibernate orm with panache repo
	@Inject
	UserRepository userRepo;
	
	//for hibernate reactive without panache repo
	//@Inject Mutiny.Session mutinySession;
	 
	 
	 
	 
	@GET
	public  Multi<UserEntity> getAllUser()
	{
		return Multi.createFrom().items(userRepo.findAll().list().stream());
	}
	
	@POST
	@Transactional
	public Uni<Response> saveUser(UserEntity user)
	{
		userRepo.persist(user);
		return Uni.createFrom().item(Response.status(Response.Status.CREATED).entity(user).build());
	}
	
	
    @GET
    @Path("{id}")
    public Uni<Response> getUserById(@PathParam Long id) {

        return Uni.createFrom().item(Response.status(Response.Status.OK).entity(userRepo.findById(id)).build());
    }
    
    @DELETE
    @Path("{id}")
    public Uni<Response> deleteUserById(@PathParam Long id) {

        return Uni.createFrom().item(Response.status(Response.Status.OK).entity(userRepo.deleteById(id)).build());
    }
    
    @PUT
	public Response updateUser(UserEntity user)
	{
		userRepo.update();
		System.out.println("updateUser "+user);
		return Response.status(Response.Status.OK).entity(user).build();
	}	

}
