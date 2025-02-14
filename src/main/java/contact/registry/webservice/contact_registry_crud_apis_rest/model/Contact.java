package contact.registry.webservice.contact_registry_crud_apis_rest.model;

import lombok.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {
    private int id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private String dob;
    private String gender;
    private String organization;
    private String maskedName;
    private String maskedPhone;
    private String hashedPhone;

    public String maskName(String name) {
        if (name == null || name.isEmpty()) return "***";
        return name.split(" ")[0] + " ***";
    }

    public String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return "***";
        return phone.substring(0, 6) + "***" + phone.substring(phone.length() - 3);
    }

    public String hashPhone(String phone) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(phone.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing phone number", e);
        }
    }
}
