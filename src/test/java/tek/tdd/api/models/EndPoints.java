package tek.tdd.api.models;

public enum EndPoints {
    TOKEN("/api/token"),
    GET_ACCOUNT("/api/accounts/get-account");

    private String value;


    EndPoints(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
