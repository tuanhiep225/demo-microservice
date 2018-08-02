package pl.piomin.microservices.account.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.discovery.EurekaClient;

import pl.piomin.microservices.account.model.Account;

@RestController
public class Api {

	private List<Account> accounts;
	
	@Autowired
    private EurekaClient discoveryClient;
    
    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String portNumber;
	
	protected Logger logger = Logger.getLogger(Api.class.getName());
	
	public Api() {
		accounts = new ArrayList<>();
		accounts.add(new Account(1, 1, "1111113"));
		accounts.add(new Account(2, 2, "22222452"));
		accounts.add(new Account(3, 3, "333333"));
		accounts.add(new Account(4, 4, "44452444"));
		accounts.add(new Account(5, 1, "555555"));
		accounts.add(new Account(6, 2, "66665566"));
		accounts.add(new Account(7, 2, "777777"));
	}
	
	@RequestMapping("/accounts/{number}")
	public Account findByNumber(@PathVariable("number") String number) {
		logger.info(String.format("Account.findByNumber(%s), port : %s", number, this.portNumber));
		return accounts.stream().filter(it -> it.getNumber().equals(number)).findFirst().get();
	}
	
	@RequestMapping("/accounts/customer/{customer}")
	public Map<String,Object> findByCustomer(@PathVariable("customer") Integer customerId) {
		String info = String.format("Hello from '%s with Port Number %s'!", discoveryClient.getApplication(appName)
                .getName(), portNumber);
		logger.info(String.format("Account.findByCustomer(%s)", customerId));
		Map<String, Object> result= new HashMap<>();
		result.put("list",accounts.stream().filter(it -> it.getCustomerId().intValue()==customerId.intValue()).collect(Collectors.toList()));
		result.put("info", info);
		return result;
	}
	
	@RequestMapping("/accounts")
	public List<Account> findAll() {
		logger.info("Account.findAll()");
		return accounts;
	}
	
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test()
    {
    	return String.format("Hello from '%s with Port Number %s'!", discoveryClient.getApplication(appName)
                .getName(), portNumber);
    	
    }
	
}
