package account.configuration.messages;

public class AdminMessages {
    public final static String REMOVE_ADMIN_ERRORMSG = "Can't remove ADMINISTRATOR role!";
    public final static String ROLE_DOESNT_EXIST_ERRORMSG = "The user does not have a role!";
    public final static String ROLE_NOT_FOUND_ERRORMSG = "Role not found!";
    public final static String NO_ROLE_ERRORMSG = "The user must have at least one role!";
    public final static String SUCCESSFUL_REMOVAL_MSG = "Deleted successfully!";
    public final static String DIFFERENT_ROLE_TYPES_CONFLICT_ERRORMSG = "The user cannot combine administrative and business roles!";
    public final static String REMOVE_ROLE_RESPONSE_MSG = "Remove role %s from %s";
    public final static String GRANT_ROLE_RESPONSE_MSG = "Grant role %s to %s";
}
