package tek.tdd.api.models.EndPoint;

import lombok.Getter;

@Getter
public enum EndPoints {
    TOKEN("/api/token"),
    GET_ACCOUNT("/api/accounts/get-account"),
    GET_ALL_PLAN_CODE("/api/plans/get/all-plan-code"),
    ADD_PRIMARY_ACCOUNT("/api/accounts/add-primary-account"),
    GET_PRIMARY_ACCOUNT("/api/accounts/get-primary-account");

    private final String value;


    EndPoints(String value) {
        this.value = value;
    }

}
