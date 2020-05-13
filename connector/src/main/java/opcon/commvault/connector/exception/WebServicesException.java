package opcon.commvault.connector.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebServicesException extends Exception {

	private static final long serialVersionUID = 1L;
	private final static Logger LOG = LoggerFactory.getLogger(WebServicesException.class);

	public WebServicesException() {
		super();
		logError("Empty Exception thrown ...");
	}

	public WebServicesException(String msg, Throwable t) {
		super(msg, t);
		logError(msg + " : " + t.getLocalizedMessage());
	}

	public WebServicesException(String msg) {
		super(msg);
		logError(msg);
	}

	public WebServicesException(String msg, int exitCode) {
		super(msg);
		logError(msg);
		System.exit(exitCode);
	}
	
	public WebServicesException(Throwable t, int error) {
		super(t);
		logError(t.getLocalizedMessage());
		System.exit(error);
	}

	public WebServicesException(Throwable t) {
		super(t);
		logError(t.getLocalizedMessage());
	}

	private void logError(String msg) {
		LOG.error(msg);
	}

}
