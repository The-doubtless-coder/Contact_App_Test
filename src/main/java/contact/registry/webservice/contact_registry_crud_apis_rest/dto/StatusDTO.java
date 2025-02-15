package contact.registry.webservice.contact_registry_crud_apis_rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@XmlRootElement
public class StatusDTO {
    private int statusCode;
    private String statusMessage;
    private String message;
}
