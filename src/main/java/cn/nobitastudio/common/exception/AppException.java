package cn.nobitastudio.common.exception;

/**
 * 系统业务异常基础类
 */
public class AppException extends RuntimeException {

    private String errorCode;

    /**
     *
     */
    private static final long serialVersionUID = 2649271829485681945L;

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new runtime exception with the specified detail
     * message, cause, suppression enabled or disabled, and writable
     * stack trace enabled or disabled.
     *
     * @param message the detail message.
     * @param cause   the cause.  (A {@code null} value is permitted,
     *                and indicates that the cause is nonexistent or unknown.)
     * @since 1.7
     */
    public AppException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Object... args) {
        super(format(message, args));
    }

    public static void main(String[] args) {
        new AppException("adaffa");
    }

    /**
     * 从guava Preconditions 搬过来的，保持和guava方式的一致
     *
     * @param template
     * @param args
     * @return
     */
    public static String format(String template, Object... args) {
        template = String.valueOf(template); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }


}
