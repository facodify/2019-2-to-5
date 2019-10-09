// cerner_2^5_2019
// Java utility class to capture multiple exceptions
// Based on: https://code.google.com/archive/p/gparallelizer/source/default/source
// + Modified to handle SQL exception with error code
//
import java.sql.SQLException;
public final class MultipleFailureException extends RuntimeException {
    private final List<Throwable> multipleExceptions;

    @SuppressWarnings(["AssignmentToCollectionOrArrayFieldFromParameter"])
    public MultipleFailureException(final String message, final List<Throwable> multipleExceptions) {
        super(message);
        this.multipleExceptions = multipleExceptions;
    }

    public List<Throwable> getMultipleExceptions() { return multipleExceptions; }
    
    @Override
    public String toString() { return super.getMessage() + ' ' + buildMessage(); }

    @Override
    public String getMessage() { return buildMessage(); }

    @SuppressWarnings(["StringBufferWithoutInitialCapacity"])
    private String buildMessage() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MultipleFailureException {multipleExceptions= [\n");
        for (final Throwable singleException : multipleExceptions) {
            if (singleException instanceof SQLException)
                sb.append(singleException.toString().replaceFirst(": ", ": ["+singleException.getErrorCode()+"] ")).append('\n');    
            else
                sb.append(singleException.toString()).append('\n');    
        }
        sb.append("]}");
        return sb.toString();
    }
}