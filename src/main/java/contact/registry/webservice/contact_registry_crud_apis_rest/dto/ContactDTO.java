package contact.registry.webservice.contact_registry_crud_apis_rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {
//    private int id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String idNumber;
    private String dob;
    private String gender;
    private String organization;
//    private String maskedName;
//    private String maskedPhone;
//    private String hashedPhone;

}
