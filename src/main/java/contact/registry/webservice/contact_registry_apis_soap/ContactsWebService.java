package contact.registry.webservice.contact_registry_apis_soap;

import contact.registry.webservice.contact_registry_apis_soap.wrappers.ContactListResponse;
import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;
import contact.registry.webservice.contact_registry_crud_apis_rest.service.ContactServiceI;
import contact.registry.webservice.contact_registry_crud_apis_rest.service.ContactServiceImpl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.ArrayList;
import java.util.List;

@WebService
//, parameterStyle = SOAPBinding.ParameterStyle.BARE
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class ContactsWebService {

    private final ContactServiceI contactService = new ContactServiceImpl();
    @WebMethod
    public Contact getContactsTest(@WebParam(name = "userName") String name){
        return new Contact(1,
                name,
                "+254",
                "@gmail",
                "366903",
                "2024-01-01",
                "M",
                "MENE",
                "@@",
                "**",
                "%%%"
        );
    }

    @WebMethod
    public ContactListResponse getContactsByOrganizationName(@WebParam(name = "organizationName") String organizationName){
        if (organizationName == null || organizationName.isEmpty() || organizationName.trim().isEmpty()) {
            throw createSOAPFaultException("Organization name cannot be null or empty or whitespace");
        }
        ContactListResponse response = new ContactListResponse();
        try{
            List<Contact> contactsByOrgName = contactService.getContactsByOrgName(organizationName);
            if(contactsByOrgName.isEmpty()){
                response.setContacts(contactsByOrgName);
                response.setMessage("No contacts found");
                response.setSuccessful(false);
                return response;
            }
            response.setContacts(contactsByOrgName);
            response.setMessage("Transaction completed successfully");
            response.setSuccessful(true);
            return response;
        }catch (Exception e){
            if(e instanceof SOAPFaultException){
                throw (SOAPFaultException)e;
            }
            throw createSOAPFaultException("Error " + e.getMessage() + " occurred while retrieving contacts by organization name " + organizationName);
        }
    }

    private SOAPFaultException createSOAPFaultException(String message) {
        try {
            SOAPFactory soapFactory = SOAPFactory.newInstance();
            SOAPFault soapFault = soapFactory.createFault(message, new QName("http://schemas.xmlsoap.org/soap/envelope/", "Client"));
            return new SOAPFaultException(soapFault);
        } catch (Exception e) {
            throw new RuntimeException("Error creating SOAPFault", e);
        }
    }

}
