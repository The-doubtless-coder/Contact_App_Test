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

        return contactDAO.createContact(contact);
    }

    @Override
    public void updateContact(int id, ContactDTO contactDTO) {


//        contactDAO.updateContact(id, new Contact(
//                id,
//
//        ))

    }

    @Override
    public void getContact() {

    }

    @Override
    public void getAllContacts() {

    }

    @Override
    public void deleteContact() {

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
}
