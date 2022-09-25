package account.model.constant;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum Role {
    ADMINISTRATOR,
    USER,
    ACCOUNTANT,
    AUDITOR;

    private final static String PREFIX = "ROLE_";

    public static Collection<String> getAdministrativeRoles() {
        return List.of(PREFIX + Role.ADMINISTRATOR.name());
    }

    public static Collection<String> getBusinessRoles() {
        return Arrays.stream(Role.values())
                .filter(r -> !r.equals(Role.ADMINISTRATOR))
                .map(Role::toString).toList();
    }

    @Override
    public String toString() {
        return PREFIX + name();
    }
}
