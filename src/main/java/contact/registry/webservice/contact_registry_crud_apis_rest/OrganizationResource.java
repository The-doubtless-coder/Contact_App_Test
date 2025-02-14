package contact.registry.webservice.contact_registry_crud_apis_rest;

import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;
import contact.registry.webservice.contact_registry_crud_apis_rest.service.ContactServiceI;
import contact.registry.webservice.contact_registry_crud_apis_rest.service.ContactServiceImpl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/v1/organizations")
public class OrganizationResource {

//    private ContactServiceI contactServiceI;
//
//    OrganizationResource(){
//        contactServiceI = new ContactServiceImpl();
//    }

    @GET
    @Path("/{orgName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContactsByOrgName(@PathParam("orgName") String orgName){
        if(orgName.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        try {
            List<Contact> contactsByOrgName = new ContactServiceImpl().getContactsByOrgName(orgName);
            if(contactsByOrgName.isEmpty()){
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(contactsByOrgName).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
