package account.model.constant;

public enum AccessOperation {
    LOCK,
    UNLOCK;

    public String getLoggingDefinition() {
        return this.name().toLowerCase() + "ed";
    }
}
