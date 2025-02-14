package contact.registry.webservice.contact_registry_crud_apis_rest.repository;

import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactDAOImpl implements ContactDAO {
//    private DataSource dataSource;
//
//    ContactDAOImpl() throws Exception {
//        try {
//            InitialContext ctx = new InitialContext();
//            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/ContactDB");
//        } catch (NamingException e) {
//            throw new Exception("Database connection error", e);
//        }
//    }

    @Override
    public List<Contact> queryForAllContacts() throws Exception {
        List<Contact> contactList = new ArrayList<>();
        try (Connection conn = DBUtil.getDataSource().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM contacts")) {
            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("id_number"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("organization"),
                        rs.getString("masked_name"),
                        rs.getString("masked_phone"),
                        rs.getString("hashed_phone")
                );
                contactList.add(contact);
            }
        } catch (SQLException e) {
            throw new Exception("Exception in repository " + e.getMessage());
        }
        return contactList;
    }

    @Override
    public Contact queryForContactById(int id) throws Exception {
        List<Contact> contactList = new ArrayList<>();
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM contacts WHERE id = ?");
            ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("id_number"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("organization"),
                        rs.getString("masked_name"),
                        rs.getString("masked_phone"),
                        rs.getString("hashed_phone")
                );
                contactList.add(contact);
            }
        } catch (SQLException e) {
            throw new Exception("Exception in repository " + e.getMessage());
        }
        return contactList.get(0);

    }

    @Override
    public Contact createContact(Contact contact) throws Exception {
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO contacts (full_name, phone_number, email, id_number, dob, gender, organization, masked_name, masked_phone, hashed_phone) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id")) {
            stmt.setString(1, contact.getFullName());
            stmt.setString(2, contact.getPhoneNumber());
            stmt.setString(3, contact.getEmail());
            stmt.setString(4, contact.getIdNumber());
            stmt.setDate(5, Date.valueOf(contact.getDob()));
            stmt.setString(6, contact.getGender());
            stmt.setString(7, contact.getOrganization());
            stmt.setString(8, contact.getMaskedName());
            stmt.setString(9, contact.getMaskedPhone());
            stmt.setString(10, contact.getHashedPhone());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                contact.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw  new Exception("Error catch in repository " + e.getMessage());
        }
        return contact;
    }

    @Override
    public boolean updateContact(int id, Contact contact) throws Exception {
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE contacts SET full_name=?, phone_number=?, email=?, id_number=?, dob=?, gender=?, organization=?, masked_name=?, masked_phone=?, hashed_phone=? " +
                             "WHERE id=?")) {
            stmt.setString(1, contact.getFullName());
            stmt.setString(2, contact.getPhoneNumber());
            stmt.setString(3, contact.getEmail());
            stmt.setString(4, contact.getIdNumber());
            stmt.setString(5, contact.getDob());
            stmt.setString(6, contact.getGender());
            stmt.setString(7, contact.getOrganization());
            stmt.setString(8, contact.getMaskedName());
            stmt.setString(9, contact.getMaskedPhone());
            stmt.setString(10, contact.getHashedPhone());
            stmt.setInt(11, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new Exception("Contact not found");
            }
        } catch (SQLException e) {
            throw new Exception("Database error " + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean deleteContact(int id) throws Exception {
        boolean result = false;
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM contacts WHERE id=?")) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new Exception("Contact not found");
            }
            result = true;
        } catch (SQLException e) {
            throw new Exception("Database error");
        }
        return result;
    }

    @Override
    public List<Contact> queryAllByOrganizationName(String organizationName) throws Exception {
        List<Contact> contactList = new ArrayList<>();
        String query = "SELECT * FROM contacts WHERE organization = ?";

        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, organizationName);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("id_number"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("organization"),
                        rs.getString("masked_name"),
                        rs.getString("masked_phone"),
                        rs.getString("hashed_phone")
                );
                contactList.add(contact);
            }
        } catch (SQLException e) {
            throw  new SQLException("Database Error Contact Admin");
        }
        return contactList;
    }

    @Override
    public List<Contact> searchContact(String hashedPhone, String maskedName, String maskedPhone) throws Exception {
        List<Contact> contactList = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM contacts WHERE 1=1");
        List<String> params = new ArrayList<>();

        if (hashedPhone != null && !hashedPhone.isEmpty()) {
            query.append(" AND hashed_phone = ?");
            params.add(hashedPhone);
        }
        if (maskedName != null && !maskedName.isEmpty()) {
            query.append(" AND masked_name = ?");
            params.add(maskedName);
        }
        if (maskedPhone != null && !maskedPhone.isEmpty()) {
            query.append(" AND masked_phone = ?");
            params.add(maskedPhone);
        }

        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i + 1, params.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Contact contact = new Contact(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("id_number"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("organization"),
                        rs.getString("masked_name"),
                        rs.getString("masked_phone"),
                        rs.getString("hashed_phone")
                );
                contactList.add(contact);
            }
        } catch (SQLException e) {
            throw new Exception("Database error");
        }
        return contactList;
    }

    @Override
    public Contact queryByPhone(String phone) throws Exception {
        String query = "SELECT * FROM contacts WHERE phone_number = ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Contact(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("id_number"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("organization"),
                        rs.getString("masked_name"),
                        rs.getString("masked_phone"),
                        rs.getString("hashed_phone")
                );
            }
        } catch (SQLException e) {
            throw new Exception("Database Error Contact Admin");
        }
        return null;
    }

    @Override
    public Contact queryByEmail(String email) throws Exception {
        String query = "SELECT * FROM contacts WHERE email = ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Contact(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("id_number"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("organization"),
                        rs.getString("masked_name"),
                        rs.getString("masked_phone"),
                        rs.getString("hashed_phone")
                );
            }
        } catch (SQLException e) {
            throw new Exception("Database Error Contact Admin");
        }
        return null;
    }

    @Override
    public Contact queryByIdNumber(String idNumber) throws Exception {
        String query = "SELECT * FROM contacts WHERE id_number = ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, idNumber);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                return new Contact(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("id_number"),
                        rs.getString("dob"),
                        rs.getString("gender"),
                        rs.getString("organization"),
                        rs.getString("masked_name"),
                        rs.getString("masked_phone"),
                        rs.getString("hashed_phone")
                );
            }
        } catch (SQLException e) {
            throw new Exception("Database Error Contact Admin");
        }
        return null;
    }
}
