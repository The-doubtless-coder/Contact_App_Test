package contact.registry.webservice.contact_registry_crud_apis_rest;

import contact.registry.webservice.contact_registry_crud_apis_rest.dto.ContactDTO;
import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;
import contact.registry.webservice.contact_registry_crud_apis_rest.service.ContactServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/contacts")
public class ContactResource {
        private final ContactServiceImpl contactService = new ContactServiceImpl();


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createContact(ContactDTO contactDTO){
        //email unique, idN unique, phone unique = return 400 otherwise
        try{
            Contact contactByIdNumber = contactService.getContactByIdNumber(contactDTO.getIdNumber());
            Contact contactsByEmail = contactService.getContactsByEmail(contactDTO.getEmail());
            Contact contactsPhoneNumber = contactService.getContactsPhoneNumber(contactDTO.getPhoneNumber());
            if(contactByIdNumber!=null){
                return Response.status(Response.Status.CONFLICT).entity("ID " +
                        contactByIdNumber.getIdNumber() + " already exists").build();
            }
            if(contactsByEmail!=null){
                return Response.status(Response.Status.CONFLICT).entity("Email " +
                        contactsByEmail.getEmail() + " already exists").build();
            }
            if(contactsPhoneNumber!=null){
                return Response.status(Response.Status.CONFLICT).entity("Phone Number " +
                        contactsPhoneNumber.getPhoneNumber() + " already exists").build();
            }
            return Response.status(Response.Status.OK).entity(contactService.createContact(contactDTO)).build();
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readContact(){
        return null;
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateContact(){
        return null;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteContact(){
        return null;
    }
}
