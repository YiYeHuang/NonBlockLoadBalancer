package org.lb.core;

import org.lb.pojo.Endpoint;

/**
 * The main interface
 */
public interface LoadBalancer {
	/**
	 * @param manager
	 */
	 void updateManager(final EndpointsManager manager);

	/**
	 *
	 * @return the next endpoint
	 */
	 Endpoint nextEndpoint();
}
