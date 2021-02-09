package badcode;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class RegisterBusiness {
    private final String firstNameRequiredMessage = "First name is required.";
    private final String lastNameRequiredMessage = "Last name is required.";
    private final String emailIsrequiredMessage = "Email is required.";
    private final String incorrectStandardMessage ="Speaker doesn't meet our standard rules.";

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

        String emailDomain = speaker.getEmail().split("@")[1];
        if (Arrays.stream(domains).filter(it -> it.equals(emailDomain)).count() == 1) {
            int exp = speaker.getExp();
            if (exp <= 1) {
                speaker.setRegistrationFee(500);
            } else if (exp >= 2 && exp <= 3) {
                speaker.setRegistrationFee(250);
            } else if (exp >= 4 && exp <= 5) {
                speaker.setRegistrationFee(100);
            } else if (exp >= 6 && exp <= 9) {
                speaker.setRegistrationFee(50);
            } else {
                speaker.setRegistrationFee(0);
            }
            try {
                speakerId = repository.saveSpeaker(speaker);
            }catch (Exception exception) {
                throw new SaveSpeakerException("Can't save a speaker.");
            }
        } else {
            throw new SpeakerDoesntMeetRequirementsException(incorrectStandardMessage);
        }

        return speakerId;
    }

}
