package badcode;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class RegisterBusiness {
    private final String firstNameRequiredMessage = "First name is required.";
    private final String lastNameRequiredMessage = "Last name is required.";
    private final String emailIsrequiredMessage = "Email is required.";
    private final String incorrectStandardMessage = "Speaker doesn't meet our standard rules.";
    private final String saveErrorMessage = "Can't save a speaker.";

    public Integer register(SpeakerRepository repository, Speaker speaker) {
        Integer speakerId;
        String[] domains = {"gmail.com", "live.com"};
        if(StringUtils.isEmpty(speaker.getFirstName())){
            throw new ArgumentNullException(firstNameRequiredMessage);
        }
        if(StringUtils.isEmpty(speaker.getLastName())){
            throw new ArgumentNullException(lastNameRequiredMessage);
        }

        if(StringUtils.isEmpty(speaker.getEmail())){
            throw new ArgumentNullException(emailIsrequiredMessage);
        }

        String emailDomain = getEmailDomain(speaker.getEmail()); // ArrayIndexOutOfBound
        if (Arrays.stream(domains).filter(it -> it.equals(emailDomain)).count() == 1) {
            int exp = speaker.getExp();
            speaker.setRegistrationFee(getFee(exp));

            try {
                speakerId = repository.saveSpeaker(speaker);
            }catch (Exception exception) {
                throw new SaveSpeakerException(saveErrorMessage);
            }
        } else {
            throw new SpeakerDoesntMeetRequirementsException(incorrectStandardMessage);
        }

        return speakerId;
    }

    int getFee(int exp) {
        int fee = 0;
        if (exp <= 1) {
            fee = 500;
        } else if (exp <= 3) {
            fee = 250;
        } else if (exp <= 5) {
            fee = 100;
        } else if (exp <= 9) {
            fee = 50;
        }
        return fee;
    }

    public String getEmailDomain(String email) {
        String[] inputs = email.trim().split("@");
        if(inputs.length == 2) return inputs[1];
        throw new DomainEmailInvalidException();
    }

}
