package contact.registry.webservice.contact_registry_crud_apis_rest.service;

import contact.registry.webservice.contact_registry_crud_apis_rest.dto.ContactDTO;
import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;
import contact.registry.webservice.contact_registry_crud_apis_rest.repository.ContactDAO;

import java.util.List;

public interface ContactServiceI {

    public Contact createContact(ContactDTO contactDTO) throws Exception;
    public boolean updateContact(int id, ContactDTO contactDTO) throws Exception;
    public Contact getContactById(int id) throws Exception;
    public List<Contact> getAllContacts() throws Exception;
    public boolean deleteContact(int id) throws Exception;
    public List<Contact> getContactsByOrgName(String orgName) throws Exception;
    public Contact getContactsPhoneNumber(String phoneNumber) throws Exception;
    public Contact getContactsByEmail(String email) throws Exception;
    public Contact getContactByIdNumber(String idNumber) throws Exception;
    public List<Contact> searchContactsByHashedPhoneMaskedNameMaskedPhone(String hashedPhone, String maskedName, String maskedPhone) throws Exception;
    public boolean existsByEmail(String email, int id) throws Exception;
    public boolean existsByIdNumber(String IdNumber, int id) throws Exception;
    public boolean existsByPhone(String phone, int id) throws Exception;

}
