package contact.registry.webservice.HelloRestExample;

import contact.registry.webservice.HelloRestExample.dtos.TestResponseDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/test")
public class HelloResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/hello")
    public Response getHelloMessage(){
        return Response.status(Response.Status.OK)  // 200 OK
                .entity(new TestResponseDTO("LEGACY CODE SUCKS", 200))
                .build();
    }


}
