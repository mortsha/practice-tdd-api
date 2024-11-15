package tek.tdd.api.models.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class PlanCodeList {
    private List<PlanCodeResponse> planCodeResponseList;


}
