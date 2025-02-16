package contact.registry.webservice.contact_registry_crud_apis_rest.repository;

import contact.registry.webservice.contact_registry_crud_apis_rest.model.Contact;
import contact.registry.webservice.contact_registry_crud_apis_rest.util.DBUtil;

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
    public int createContact(Contact contact) throws Exception {
        int affectedRows = 0;
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO contacts (full_name, phone_number, email, id_number, dob, gender, organization, masked_name, masked_phone, hashed_phone) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
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
            affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return affectedRows;
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    affectedRows = generatedKeys.getInt(1);
                } else {
                    affectedRows = -1;
                }
            }
        } catch (SQLException e) {
            throw  new Exception("Error catch in repository " + e.getMessage());
        }
        return affectedRows;
    }

    @Override
    public boolean updateContact(int id, Contact contact) throws Exception {
        boolean isUpdated = false;
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
            if (rowsUpdated > 0) {
                isUpdated = true;
            }
        } catch (SQLException e) {
            throw new Exception("Database error " + e.getMessage());
        }
        return isUpdated;
    }

    @Override
    public boolean deleteContact(int id) throws Exception {
        boolean result = false;
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM contacts WHERE id=?")) {
            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                return result;
            }
            result = true;
        } catch (SQLException e) {
            throw new Exception("Database error at repository " + e.getMessage());
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

        if(params.isEmpty()){
            return contactList;//never return the whole table contents//contents must be filtered
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

    @Override
    public Contact queryById(int id) throws Exception {
        String query = "SELECT * FROM contacts WHERE id = ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
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

    //    OR id_number = ?, phone_number = ?)
    @Override
    public boolean existsByEmail(String email, int id) throws Exception {
        String sql = "SELECT COUNT(*) FROM contacts WHERE (email = ? )  AND id <> ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
//            ps.setString(2, IdNumber);
//            ps.setString(3, phone);
            ps.setInt(2, id); // Exclude the record being updated
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // If count > 0, duplicate exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Database Error Contact Admin");
        }
        return false;
    }

    @Override
    public boolean existsByIdNumber(String IdNumber, int id) throws Exception {
        String sql = "SELECT COUNT(*) FROM contacts WHERE (id_number = ? )  AND id <> ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, IdNumber);
//            ps.setString(2, IdNumber);
//            ps.setString(3, phone);
            ps.setInt(2, id); // Exclude the record being updated
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // If count > 0, duplicate exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Database Error Contact Admin");
        }
        return false;
    }

    @Override
    public boolean existsByPhone(String phone, int id) throws Exception {
        String sql = "SELECT COUNT(*) FROM contacts WHERE (phone_number = ? )  AND id <> ?";
        try (Connection conn = DBUtil.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phone);
//            ps.setString(2, IdNumber);
//            ps.setString(3, phone);
            ps.setInt(2, id); // Exclude the record being updated
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // If count > 0, duplicate exists
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Database Error Contact Admin");
        }
        return false;
    }

}
