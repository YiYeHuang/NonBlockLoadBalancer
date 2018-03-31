package org.lb.launch;

import org.lb.core.*;
import org.lb.pojo.Endpoint;

import java.util.HashSet;
import java.util.Set;

public class LaunchService {

  public static void main(String[] args) throws Exception {
    LaunchService ls = new LaunchService();
    ServiceInvoker rd = ServiceInvoker.getInstance();

    LoadBalancer lb = ls.createLoadBalancer();
    rd.setLoadBalancer(lb);
  }

  // 根据系统配置创建负载均衡器实例
  private LoadBalancer createLoadBalancer() throws Exception {
    LoadBalancer lb;
    EndpointsManager manager = new EndpointsManager(loadEndpoints());
    lb = WeightedRRLoadBalancerFactory.newInstance(manager);
    return lb;
  }

  private Set<Endpoint> loadEndpoints() {
    Set<Endpoint> endpoints = new HashSet<Endpoint>();

    endpoints.add(new Endpoint("10.17.7.1", 8080, 3));
    endpoints.add(new Endpoint("10.17.7.2", 8080, 2));
    endpoints.add(new Endpoint("10.17.7.3", 8080, 5));
    endpoints.add(new Endpoint("10.17.7.4", 8080, 7));
    return endpoints;
  }
}
