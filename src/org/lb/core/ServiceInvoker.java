package org.lb.core;

import org.lb.pojo.Endpoint;
import org.lb.pojo.Request;

public class ServiceInvoker {
    private static ServiceInvoker INSTANCE = null;

    // LB singleton instance
    private volatile LoadBalancer loadBalancer;

    private ServiceInvoker() {
    }

    public static synchronized ServiceInvoker getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new ServiceInvoker();
        }
        return INSTANCE;
    }

    /**
     * @param request
     */
    public void dispatchRequest(Request request) {
        // get the next endpoint
        Endpoint endpoint = getLoadBalancer().nextEndpoint();

        if (null == endpoint) {
            return;
        }

        dispatchToDownstream(request, endpoint);
    }

    //
    private void dispatchToDownstream(Request request, Endpoint endpoint) {
        // na... dont really want to do anything here
        endpoint.executeRequest(request);
    }

    /**
     * Getter
     * @return
     */
    public LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }

    /**
     * Setter
     * @param loadBalancer
     */
    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }
}
