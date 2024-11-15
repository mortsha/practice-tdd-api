package tek.tdd.api.models.EndPoint;

public enum EndPoints {
    TOKEN("/api/token"),
    GET_ACCOUNT("/api/accounts/get-account"),
    GET_PRIMARY_ACCOUNT("/api/accounts/get-primary-account");

    private String value;


    EndPoints(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
