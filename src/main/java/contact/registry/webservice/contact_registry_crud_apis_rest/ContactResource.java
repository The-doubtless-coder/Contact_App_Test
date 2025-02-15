package contact.registry.webservice.contact_registry_crud_apis_rest;

import contact.registry.webservice.contact_registry_crud_apis_rest.dto.ContactDTO;
import contact.registry.webservice.contact_registry_crud_apis_rest.dto.StatusDTO;
import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;
import contact.registry.webservice.contact_registry_crud_apis_rest.service.ContactServiceImpl;

import javax.annotation.Resource;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
                return Response.status(Response.Status.CONFLICT).entity(new StatusDTO(
                        409,
                        "409 CONFLICT",
                        "Contact with ID number " + contactByIdNumber.getIdNumber() + " already exists"
                )).build();
            }
            if(contactsByEmail!=null){
                return Response.status(Response.Status.CONFLICT).entity(new StatusDTO(
                        409,
                        "409 CONFLICT",
                        "Contact with Email " + contactsByEmail.getEmail() + " already exists")).build();
            }
            if(contactsPhoneNumber!=null){
                return Response.status(Response.Status.CONFLICT).entity(new StatusDTO(
                        409,
                        "409 CONFLICT",
                        "Contact with Phone Number " + contactsPhoneNumber.getPhoneNumber() + " already exists")).build();
            }
            Contact contact = contactService.createContact(contactDTO);
            if(contact.getId()==0 || contact.getId()==-1){
                return Response.status(Response.Status.EXPECTATION_FAILED).entity(new StatusDTO(
                   500,
                   "INTERNAL SERVER ERROR",
                   "Creating contacts failed, no rows affected"
                )).build();
            } else return Response.status(Response.Status.CREATED).entity(
                    contact
            ).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new StatusDTO(
                            500,
                            e.getMessage(),
                            "Creating contacts failed"
                    )).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response readContacts(){
        try{
            List<Contact> allContacts = contactService.getAllContacts();
            if(allContacts.size()==0){
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.ok().entity(allContacts).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new StatusDTO(
               500,
               "INTERNAL SERVER ERROR",
               e.getMessage()
            )).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readContactById(@PathParam("id") int id){
        try{
            Contact contactById = contactService.getContactById(id);
            if(contactById==null){
                return Response.status(Response.Status.NOT_FOUND).entity(
                        new StatusDTO(404,
                                "NOT FOUND",
                                "Contact with id " + id + " not found"
                        )
                ).build();
            }
            return Response.ok().entity(contactById).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new StatusDTO(
                    500,
                    "INTERNAL SERVER ERROR",
                    e.getMessage()
            )).build();
        }
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response updateContact(@PathParam("id") int id, ContactDTO contactDTO) {
        try{
            Contact contactById = contactService.getContactById(id);
            if(contactById==null){
                return Response.status(Response.Status.NOT_FOUND).entity(
                        new StatusDTO(404,
                                "NOT FOUND",
                                "Contact with id " + id + " not found"
                        )
                ).build();
            }
            //email is unique
            //id number is unique
            //phone number is unique
            Contact contactByIdNumber = contactService.getContactByIdNumber(contactDTO.getIdNumber());
            Contact contactsByEmail = contactService.getContactsByEmail(contactDTO.getEmail());
            Contact contactsPhoneNumber = contactService.getContactsPhoneNumber(contactDTO.getPhoneNumber());
            if(contactByIdNumber!=null){
                return Response.status(Response.Status.CONFLICT).entity(new StatusDTO(
                        409,
                        "409 CONFLICT",
                        "Contact with ID number " + contactByIdNumber.getIdNumber() + " already exists"
                )).build();
            }
            if(contactsByEmail!=null){
                return Response.status(Response.Status.CONFLICT).entity(new StatusDTO(
                        409,
                        "409 CONFLICT",
                        "Contact with Email " + contactsByEmail.getEmail() + " already exists")).build();
            }
            if(contactsPhoneNumber!=null){
                return Response.status(Response.Status.CONFLICT).entity(new StatusDTO(
                        409,
                        "409 CONFLICT",
                        "Contact with Phone Number " + contactsPhoneNumber.getPhoneNumber() + " already exists")).build();
            }
            //continue with updates
            if(contactService.updateContact(id, contactDTO)){
                return Response.status(Response.Status.OK).entity(
                        new StatusDTO(
                                200,
                                "OK",
                                "Contact with id " + id + " successfully updated"
                        )
                ).build();
            }else return Response.status(Response.Status.EXPECTATION_FAILED).entity(
                    new StatusDTO(
                            417,
                            "EXPECTATION FAILED",
                            "Updation failed"
                    )
            ).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    new StatusDTO(500,
                            "INTERNAL SERVER ERROR",
                            e.getMessage())
            ).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id){
        try{
            Contact contactById = contactService.getContactById(id);
            if(contactById==null){
                return Response.status(Response.Status.NOT_FOUND).entity(
                        new StatusDTO(404,
                                "CONTACT NOT FOUND",
                                "Contact with id " + id + " not found"
                        )
                ).build();
            }
            if(contactService.deleteContact(id)){
                return Response.status(Response.Status.OK).entity(
                        new StatusDTO(
                                200,
                                "OK",
                                "Contact with id " + id + " successfully deleted"
                        )
                ).build();
            }else return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    new StatusDTO(500,
                            "INTERNAL SERVER ERROR",
                            "Contact with id " + id + " not deleted, DB error"

                    )
            ).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    new StatusDTO(500,
                            "INTERNAL SERVER ERROR",
                            e.getMessage())
            ).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/search")
    public Response searchContact(@QueryParam("hashedPhone") String hashedPhone, @QueryParam("maskedName") String maskedName, @QueryParam("maskedPhone") String maskedPhone) {
        try{
            List<Contact> contacts = contactService.searchContactsByHashedPhoneMaskedNameMaskedPhone(hashedPhone, maskedName, maskedPhone);
            if(contacts.size()==0){
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            return Response.status(Response.Status.OK).entity(contacts).build();
        }catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    new StatusDTO(500,
                            "INTERNAL SERVER ERROR",
                            e.getMessage())
            ).build();
        }
    }

}
