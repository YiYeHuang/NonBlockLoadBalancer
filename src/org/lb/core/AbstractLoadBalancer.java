package org.lb.core;

import org.lb.pojo.Endpoint;

import java.util.Random;
import java.util.logging.Logger;

/**
 *
 */
public abstract class AbstractLoadBalancer implements LoadBalancer {
    private final static Logger LOGGER = Logger.getAnonymousLogger();

    // volatile is used here to control the visibility of the
    protected volatile EndpointsManager m_endPointManager;

    protected final Random random;

    // Back ground thread to change the endpoint health
    private Thread heartbeatThread;

    public AbstractLoadBalancer(EndpointsManager manager) {

        if (null == manager || 0 == manager.getEndpointCount()) {
            throw new IllegalArgumentException("Invalid candidate " + manager);
        }

        this.m_endPointManager = manager;
        random = new Random();
    }

    /**
     * initial the service health check
     * @throws Exception
     */
    public synchronized void init() throws Exception {
        if (null == heartbeatThread) {
            heartbeatThread = new Thread(new HeartbeatTask(), "Endpoints_Heartbeat");
            heartbeatThread.setDaemon(true);
            heartbeatThread.start();
        }
    }

    /**
     * Set the endpoints manager
     * @param manager
     */
    @Override
    public void updateManager(final EndpointsManager manager) {
        if (null == manager || 0 == manager.getEndpointCount()) {
            throw new IllegalArgumentException("Invalid candidate " + manager);
        }
        //the assign for the manager is a atomic action
        this.m_endPointManager = manager;
    }

    /**
     * Algorithm to detect the next endpoint leave to child class to implement
     * @return
     */
    @Override
    public abstract Endpoint nextEndpoint();

    protected void monitorEndpoints() {
        // read volatile instance, atomic action as well
        final EndpointsManager currCandidate = m_endPointManager;
        boolean isTheEndpointOnline;

        // Simulate and Check the status of the endpoints
        for (Endpoint endpoint : currCandidate) {
            isTheEndpointOnline = endpoint.isOnline();
            if (doDetect(endpoint) != isTheEndpointOnline) {
                endpoint.setOnline(!isTheEndpointOnline);
                if (isTheEndpointOnline) {
                    LOGGER.log(java.util.logging.Level.SEVERE,
                            endpoint + " offline");
                } else {
                    LOGGER.log(java.util.logging.Level.INFO,
                            endpoint + " is online");
                }
            }
        }
    }

    private boolean doDetect(Endpoint endpoint) {
        boolean online = true;
        // FAKE status here
        int rand = random.nextInt(1000);
        // 30% chance failure
        if (rand <= 300) {
            online = false;
        }
        return online;
    }

    private class HeartbeatTask implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    monitorEndpoints();
                    Thread.sleep(2000);
                }
            }
            catch (InterruptedException e) {
                LOGGER.log(java.util.logging.Level.SEVERE,"Health check service down");
            }
        }
    }
}