package contact.registry.webservice.contact_registry_apis_soap.wrappers;

import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "ContactListResponse")
public class ContactListResponse {
    
    private List<Contact> contacts;
    private String message;
    private boolean isSuccessful;

    @XmlElement(name = "contact")
    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @XmlElement(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement(name = "isSuccessful")
    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
