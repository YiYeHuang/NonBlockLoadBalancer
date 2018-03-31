package org.lb.pojo;

import java.util.logging.Logger;

/**
 * Endpoint pojo
 */
public class Endpoint {

    // TODO: share this later
    private final static Logger LOGGER = Logger.getAnonymousLogger();
	public final String host;
	public final int port;
	public final int weight;
	// same use volatile here, keep the status visible
	private volatile boolean online = true;

	public Endpoint(String host, int port, int weight) {
		this.host = host;
		this.port = port;
		this.weight = weight;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean executeRequest(Request request) {
	    // not really doing anyThing;
        try {
            LOGGER.log(java.util.logging.Level.INFO,
                    this + " *********** EXE request " + request.getTransactionId());
            Thread.sleep(2000);
        }
        catch (InterruptedException ex) {
            // dont do anything
        }
	    return true;
    }

	/**
	 * Hash code, always override hashCode when override equals
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + port;
		result = prime * result + weight;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endpoint other = (Endpoint) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Endpoint [" +
				"host=" + host + ", " +
				"port=" + port + ", " +
				"weight=" + weight + ", " +
				"online=" + online + "]\n";
	}
}
