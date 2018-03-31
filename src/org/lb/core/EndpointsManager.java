package org.lb.core;

import org.lb.pojo.Endpoint;

import java.util.Iterator;
import java.util.Set;

public final class EndpointsManager implements Iterable<Endpoint> {
    // list of endpoints
    private final Set<Endpoint> endpoints;

    // weight of the endpoints
    public final int totalWeight;

    public EndpointsManager(Set<Endpoint> endpoints) {
        int sum = 0;
        for (Endpoint endpoint : endpoints) {
            sum += endpoint.weight;
        }
        this.totalWeight = sum;
        this.endpoints = endpoints;
    }

    public int getEndpointCount() {
        return endpoints.size();
    }

    @Override
    public final Iterator<Endpoint> iterator() {
        return endpoints.iterator();
    }

    @Override
    public String toString() {
        return "[endpoints=" + endpoints + ", totalWeight=" + totalWeight +"]";
    }
}