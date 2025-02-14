package contact.registry.webservice.contact_registry_crud_apis_rest.service;

import contact.registry.webservice.contact_registry_crud_apis_rest.dto.ContactDTO;
import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;
import contact.registry.webservice.contact_registry_crud_apis_rest.repository.ContactDAO;

import java.util.List;

public interface ContactServiceI {

    public Contact createContact(ContactDTO contactDTO) throws Exception;
    public void updateContact(int id, ContactDTO contactDTO);
    public void getContact();
    public void getAllContacts();
    public void deleteContact();
    public List<Contact> getContactsByOrgName(String orgName) throws Exception;
    public Contact getContactsPhoneNumber(String phoneNumber) throws Exception;
    public Contact getContactsByEmail(String email) throws Exception;
    public Contact getContactByIdNumber(String idNumber) throws Exception;



}
