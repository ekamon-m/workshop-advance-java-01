package badcode;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Speaker {
    private String firstName;
    private String lastName;
    private String email;
    private int exp;
    private int registrationFee;
    private boolean hasBlog;
    private String blogUrl;
    private List<String> certifications;
}
