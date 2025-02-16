package contact.registry.webservice.contact_registry_crud_apis_rest.service;

import contact.registry.webservice.contact_registry_crud_apis_rest.dto.ContactDTO;
import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;
import contact.registry.webservice.contact_registry_crud_apis_rest.repository.ContactDAO;
import contact.registry.webservice.contact_registry_crud_apis_rest.repository.ContactDAOImpl;

import java.util.List;

public class ContactServiceImpl implements ContactServiceI{

    ContactDAO contactDAO;

    public ContactServiceImpl(){
        contactDAO = new ContactDAOImpl();
    }

    @Override
    public Contact createContact(ContactDTO contactDTO) throws Exception {
        //create contact object
        //hash things
        //then pass to repo layer
        Contact contact = new Contact();
        contact.setFullName(contactDTO.getFullName());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setEmail(contactDTO.getEmail());
        contact.setIdNumber(contactDTO.getIdNumber());
        contact.setDob(contactDTO.getDob());
        contact.setGender(contactDTO.getGender());
        contact.setOrganization(contactDTO.getOrganization());
        contact.setMaskedName(contact.maskName(contactDTO.getFullName()));
        contact.setMaskedPhone(contact.maskPhone(contactDTO.getPhoneNumber()));
        contact.setHashedPhone(contact.hashPhone(contactDTO.getPhoneNumber()));
        int contactID = contactDAO.createContact(contact);
        contact.setId(contactID);
        return contact;
    }

    @Override
    public boolean updateContact(int id, ContactDTO contactDTO) throws Exception {
        //already checked existence
        Contact contact = new Contact();
        contact.setId(id);
        contact.setFullName(contactDTO.getFullName());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setEmail(contactDTO.getEmail());
        contact.setIdNumber(contactDTO.getIdNumber());
        contact.setDob(contactDTO.getDob());
        contact.setGender(contactDTO.getGender());
        contact.setOrganization(contactDTO.getOrganization());
        contact.setMaskedName(contact.maskName(contactDTO.getFullName()));
        contact.setMaskedPhone(contact.maskPhone(contactDTO.getPhoneNumber()));
        contact.setHashedPhone(contact.hashPhone(contactDTO.getPhoneNumber()));
        return contactDAO.updateContact(id, contact);
    }

    @Override
    public Contact getContactById(int id) throws Exception {
        return contactDAO.queryById(id);
    }

    @Override
    public List<Contact> getAllContacts() throws Exception {
        return contactDAO.queryForAllContacts();
    }

    @Override
    public boolean deleteContact(int id) throws Exception {
        return contactDAO.deleteContact(id);
    }

    @Override
    public List<Contact> getContactsByOrgName(String orgName) throws Exception {
        return contactDAO.queryAllByOrganizationName(orgName);
    }

    @Override
    public Contact getContactsPhoneNumber(String phoneNumber) throws Exception {
        return contactDAO.queryByPhone(phoneNumber);
    }

    @Override
    public Contact getContactsByEmail(String email) throws Exception {
        return contactDAO.queryByEmail(email);
    }

    @Override
    public Contact getContactByIdNumber(String idNumber) throws Exception {
        return  contactDAO.queryByIdNumber(idNumber);
    }

    @Override
    public List<Contact> searchContactsByHashedPhoneMaskedNameMaskedPhone(String hashedPhone, String maskedName, String maskedPhone) throws Exception {
        return contactDAO.searchContact(hashedPhone, maskedName, maskedPhone);
    }

    @Override
    public boolean existsByEmail(String email, int id) throws Exception {
        return contactDAO.existsByEmail(email, id);
    }

    @Override
    public boolean existsByIdNumber(String IdNumber, int id) throws Exception {
        return contactDAO.existsByIdNumber(IdNumber, id);
    }

    @Override
    public boolean existsByPhone(String phone, int id) throws Exception {
        return contactDAO.existsByPhone(phone, id);
    }
}
