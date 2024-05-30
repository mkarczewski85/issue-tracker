package pl.wsb.issuetracker.helper;

public final class TestDataHelper {

    public static final String LOW_SEVERITY = "LOW";
    public static final String ASSIGNED_STATUS = "ASSIGNED";
    public static final String REPORTER = "REPORTER";
    public static final String TECHNICIAN = "TECHNICIAN";
    public static final String ADMINISTRATOR = "ADMINISTRATOR";
    public static final String IN_PROGRESS_STATUS = "IN_PROGRESS";
    public static final String MEDIUM_SEVERITY = "MEDIUM";

    public static String getUserDisplayName(String role) {
        TestAuthenticationData reporter = SecurityContextHelper.AUTHENTICATION_DATA_MAP.get(role);
        return reporter.getFirstName() + " " + reporter.getLastName();
    }

}
