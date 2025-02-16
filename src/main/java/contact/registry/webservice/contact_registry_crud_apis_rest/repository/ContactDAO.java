package contact.registry.webservice.contact_registry_crud_apis_rest.repository;

import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;

import java.util.List;

public interface ContactDAO {
    public List<Contact> queryForAllContacts() throws Exception;
    public Contact queryForContactById(int id) throws Exception;
    public int createContact(Contact contact) throws Exception;
    public boolean updateContact(int id, Contact contact) throws Exception;
    public boolean deleteContact(int id) throws Exception;
    public List<Contact> queryAllByOrganizationName(String organizationName) throws Exception;
    public List<Contact> searchContact(String hashedPhone, String maskedName, String maskedPhone) throws Exception;
    public Contact queryByPhone(String phone) throws Exception;
    public Contact queryByEmail(String email) throws Exception;
    public Contact queryByIdNumber(String idNumber) throws Exception;
    public Contact queryById(int id) throws Exception;
    public boolean existsByEmail(String email, int id) throws Exception;
    public boolean existsByIdNumber(String IdNumber, int id) throws Exception;
    public boolean existsByPhone(String phone, int id) throws Exception;

}
