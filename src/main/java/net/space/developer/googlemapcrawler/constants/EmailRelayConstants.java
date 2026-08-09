package net.space.developer.googlemapcrawler.constants;

public final class EmailRelayConstants {

    public static final String SMTP_AUTH = "mail.smtp.auth";
    public static final String SMTP_STARTTLS = "mail.smtp.starttls.enable";
    public static final String SMTP_SSL = "mail.smtp.ssl.enable";
    public static final String SMTP_HOST = "mail.smtp.host";
    public static final String SMTP_PORT = "mail.smtp.port";

    public static final String TRUE_VALUE = "true";
    public static final String SSL = "ssl";

    public static final String MAIL_EXCEPTION = "Error trying to send email: %s";
    public static final String EMPTY_SMTP_CONFIGURATIONS_EXCEPTION = "There are not smtp configurations for this user";
    public static final String ENCRYPT_EXCEPTION = "Error trying to encrypt smtp password: %s";
    public static final String SMTP_CONFIGURATION_NOT_FOUND_EXCEPTION = "Smtp configuration with id: %s was not found";

    private EmailRelayConstants(){}
}
