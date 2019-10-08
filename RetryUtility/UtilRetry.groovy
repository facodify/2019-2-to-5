// cerner_2^5_2019
// Groovy utility method to encapsulate retry logic
// Based on https://stackoverflow.com/questions/7087185/retry-after-exception-in-groovy
// NOTE: depends on MultipleFailureException implemented separately
// + Modified to handle optional list of error codes/strings that can trigger retry logic
// + Modified to handle optional sleep timer to wait between retry attempts
//
// Example1: retry some operation for any exception 
//    UtilRetry.retry {   
//       errorProneOperation()
//    } 
// Example2: retry 3 times with 300ms sleep timer, only when exception contains specified keywrods
//    UtilRetry.retry(3, 300, ['deadlock', 'lock']) {
//       someSqlOperation()
//    } 
//
public final class UtilRetry {
    public static def retry(int maxTries = 5, int sleepFor = 0, def errorList=[], Closure errorHandler = { e -> }, Closure body) {
        int retries = 0
        def exceptions = []
        while(retries++ < maxTries) {
            try {
                //attempt body of closure specified
                return body.call()
            } catch(e) {
                //catch error and check if its in list specified
                if (!errorList.size() || errorList.any { m -> e.toString().toUpperCase().indexOf(m.toUpperCase()) > -1}) {
                    exceptions << e
                    errorHandler.call(e)
                    //sleep for that long if specified
                    if (sleepFor>0 && retries < maxTries) {
                        sleep(sleepFor)
                    }
                } else {
                    //some other error not in specified list; end retry loop and throw error
                    retries = maxTries
                    throw e
                }
            }        
        }
        if (exceptions.size() > 0) {
            throw new MultipleFailureException("RETRY failed after ${maxTries} attempts.", exceptions)            
        }
    }
}