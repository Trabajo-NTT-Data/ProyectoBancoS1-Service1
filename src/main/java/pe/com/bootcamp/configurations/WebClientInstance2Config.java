package pe.com.bootcamp.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import java.util.List;

import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.EurekaClient;

import lombok.Data;

@Configuration
@LoadBalancerClient(name = WebClientInstance2Config.InstanceName, configuration = ServiceInstance2ListSupplier.class)
public class WebClientInstance2Config {
	
	public static final String InstanceName = "MICRO-SERVICE2";
	
	@Autowired
	LoadBalancerInstance2Config config;
	
	@LoadBalanced
	@Bean	
	@Primary
	@Qualifier("Loan")
  	WebClient.Builder LoanClientBuilder() {		
		return WebClient.builder()
				.baseUrl(config.getBaseUrl(InstanceName,"Loan"));
	}	
}
class ServiceInstance2ListSupplier{
	@Autowired
	LoadBalancerInstance2Config config;
	
	@Autowired
    @Lazy
    private EurekaClient eurekaClient;
	
	@Bean
	@Primary	
	ServiceInstanceListSupplier serviceInstanceListSupplier() throws Exception {
		List<InstanceInfo> instanceInfoList = eurekaClient.getApplications()
				.getRegisteredApplications(WebClientInstance2Config.InstanceName)
				.getInstances();
		
		if(instanceInfoList == null || instanceInfoList.size() == 0)
			throw new Exception("instances not found.");
		
		Boolean isSecure = config.getEnabledSSL();
		@SuppressWarnings("unchecked")
		List<Triplet<String, Integer, Boolean>> instances = (List<Triplet<String, Integer, Boolean>>) eurekaClient.getApplications()
				.getRegisteredApplications(WebClientInstance2Config.InstanceName)
				.getInstances().stream()
				.filter(e -> e.getStatus().equals(InstanceStatus.UP))
				.map(e -> new Triplet<String, Integer, Boolean>(e.getIPAddr(), e.getPort(), isSecure));
		
		if(instances.size() == 0)
			throw new Exception("no available instances found.");
		
		return new MicroServiceInstanceListSupplier(WebClientInstance2Config.InstanceName, instances);
	}
}
@Configuration
@ConfigurationProperties(prefix = "vaetech.load-balancer.instance2")
@Data
class LoadBalancerInstance2Config {	
	private Boolean enabledSSL = false;	
	
	public final String getBaseUrl(String instanceName, String controllerName) {
		final String protocol = enabledSSL ? "https": "http";
		return  protocol + "://" + instanceName + "/" + controllerName;
	}
}