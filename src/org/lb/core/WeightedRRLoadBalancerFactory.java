package org.lb.core;

import org.lb.pojo.Endpoint;

/**
 * Weighted Round Robin Load Balancer
 */
public class WeightedRRLoadBalancerFactory extends AbstractLoadBalancer {

    private WeightedRRLoadBalancerFactory(EndpointsManager manager) {
        super(manager);
    }

    public static LoadBalancer newInstance(EndpointsManager manager) throws Exception {
        WeightedRRLoadBalancerFactory lb = new WeightedRRLoadBalancerFactory(manager);
        lb.init();
        return lb;
    }

    @Override
    public Endpoint nextEndpoint() {
        Endpoint selectedEndpoint = null;
        int subWeight = 0;
        int dynamicTotoalWeight;
        final double rawRnd = super.random.nextDouble();
        int rand;

        final EndpointsManager manager = super.m_endPointManager;
        dynamicTotoalWeight = manager.totalWeight;
        for (Endpoint endpoint : manager) {
            // skip the offline endpoint
            if (!endpoint.isOnline()) {
                dynamicTotoalWeight -= endpoint.weight;
                continue;
            }

            rand = (int) (rawRnd * dynamicTotoalWeight);
            subWeight += endpoint.weight;
            if (rand <= subWeight) {
                selectedEndpoint = endpoint;
                break;
            }
        }
        return selectedEndpoint;
    }
}
