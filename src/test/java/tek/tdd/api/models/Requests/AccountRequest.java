package tek.tdd.api.models.Requests;

import lombok.*;

@AllArgsConstructor
@NonNull
@Getter
@Setter
@Builder
public class AccountRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String title;
    private String gender;
    private String maritalStatus;
    private String employmentStatus;
    private String dateOfBirth; // yyyy-MM-dd
}
