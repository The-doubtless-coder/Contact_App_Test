package contact.registry.webservice.HelloRestExample.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestResponseDTO {
   // Needed if you are not using Jackson
        private String message;
        private int status;

        public TestResponseDTO() {}

        public TestResponseDTO(String message, int status) {
            this.message = message;
            this.status = status;
        }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public int getStatus() { return status; }
        public void setStatus(int status) { this.status = status; }
}
