package communication.backend.utilities;

/**
 * Logging service.
 * <p>
 * Code
 * <code>SLoggerService.setLogger(new SLoggerService.SLoggerPrint);</code> sets
 * default output to be sent to standard output.
 *
 * @author Václav Blažej
 */
public class SLoggerService {

    private static SLogger implementation = new SLoggerDefault();

    public static void setLogger(SLogger logger) {
        implementation = logger;
    }

    public static void print(String message) {
        implementation.print(message);
    }

    public static interface SLogger {

        public abstract void print(String message);
    }

    public static class SLoggerDefault implements SLogger {

        @Override
        public void print(String message) {
        }
    }

    public static class SLoggerPrint implements SLogger {

        @Override
        public void print(String message) {
            System.out.println("<<CONNECTION>> " + message);
        }
    }
}
