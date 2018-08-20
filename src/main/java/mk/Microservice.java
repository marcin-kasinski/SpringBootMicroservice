package mk;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.metrics.CounterService;
//import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//2.0.1
//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.Metrics;

//1.5.10
//import com.codahale.metrics.Counter;
//import com.codahale.metrics.Gauge;
//import com.codahale.metrics.MetricRegistry;
//import com.codahale.metrics.Timer;


import mk.metrics.SampleMetricBean;

//@EnableDiscoveryClient
@RestController
@RequestMapping("/api")
public class Microservice {

	@Autowired
	private SampleMetricBean sampleBean;
	
//	private final Counter successesCounter = (Counter) Metrics.counter("MKsuccessesCounter6.index", "result", "success");

	private static Logger log = LoggerFactory.getLogger(Microservice.class);

	// Private fields

	@Autowired
	private UserRepository userRepository;
/*
	@Autowired
	CounterService counterService;
	@Autowired
	GaugeService gaugeService;
*/
	int index = 0;

//	private MetricRegistry metricRegistry;
	private static final String PATTERN = "^-?+\\d+\\.?+\\d*$";

	long mkrrequestsperminute=0;
	byte lastminutecheck =-1;
	
	/*
	@Autowired
	public Microservice(MetricRegistry inmetricRegistry) {
		this.metricRegistry = inmetricRegistry;
		// ------------------------ custom gauge ------------------------//

		metricRegistry.register("MKCustomGaugeIndex", new Gauge<Integer>() {
			@Override
			public Integer getValue() {

				index++;
				// int randomNum = ThreadLocalRandom.current().nextInt(0, 300 + 1);
				// System.out.println(new Date ()+" MKCustomGaugeXYZ generated "+index );
				return (int) new Integer(index);
			}
		});

		metricRegistry.register("MKRequestsPerMinute", new Gauge<Integer>() {
			@Override
			public Integer getValue() {

				
				long ret = mkrrequestsperminute;
				
				log.info("ret mkrrequestsperminute: "+ret);

				
				mkrrequestsperminute =0;				
				return (int) new Integer((int)ret);
			}
		});

		// ------------------------ custom gauge ------------------------//
	}

	
	*/
	
	// Counter pendingJobs = metricRegistry.counter(name(this, "pending-jobs"));

	@RequestMapping("/create")
	// @ResponseBody
	public User create(String email, String name) {
		String userId = "";
		User user = null;
		try {
			user = new User(email, name);
			userRepository.save(user);
			userId = String.valueOf(user.getId());
		} catch (Exception ex) {
			// return "Error creating the user: " + ex.toString();
		}
		// return "User succesfully created with id = " + userId;
		return user;
	}
	

	@RequestMapping("/gethostname")
	// @ResponseBody
	public String getHostname() 
		{
				String ret = null;
				try {
					ret = InetAddress.getLocalHost().getHostName();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ret;
		}

	// http://localhost:9191/api/findallusersbyemail?email=x@x.com
	@RequestMapping("/findallusersbyemail")
	// @ResponseBody
	public List<User> findAllUsersByEmail(@RequestParam(value = "email", defaultValue = ".") String email) {

		System.out.println("/findallusersbyemail " + email);

//		successesCounter.increment();
//		sampleBean.handleMessage("XXX");

		System.out.println("successesCounter.increment()");

//		Timer t = metricRegistry.timer("endpoint.test");
//		Timer.Context c = t.time();
		log.info("Microservice findAllUsersByEmail executed");

		List<User> users;

		String userId = "";
		try {
			users = userRepository.findAllUsersByEmail(email);

			System.out.println("users size " + users.size());
//			this.counterService.increment("greetMK.txnCount");
//			this.gaugeService.submit("greetMK.customgauge", 1.0);

		} catch (Exception ex) {

			System.out.println("error " + ex.getMessage());
			System.out.println("error " + ex.getLocalizedMessage());

			return null;
		} finally {
//			c.stop();

		}
		// return "The user id is: " + userId;
		return users;
	}

	
	
	public void processRequest()
	{
		Calendar rightNow = Calendar.getInstance();
		int minute = rightNow.get(Calendar.MINUTE);
		
		mkrrequestsperminute++;
		log.info("mkrrequestsperminute : "+mkrrequestsperminute);
	
		
	}
	
	@RequestMapping("/get-by-emailM")	
	public String getByEmailM()
	{
		return 	"# TYPE qps gauge\nqps "+String.valueOf(mkrrequestsperminute);
	}

	
	
	// http://localhost:8081/api/get-by-email?email=x@x.com

	
	@RequestMapping("/get-by-email")
	// @ResponseBody
	public User getByEmail(@RequestParam(value = "email", defaultValue = ".") String email,
			@RequestHeader HttpHeaders headers) {
		processRequest();
		
		sampleBean.handleMessage("XXX");

		// ------------------------ custom counter ------------------------//
//		Counter mkCustomCounter = metricRegistry.counter("mkCustomCounter");
//		mkCustomCounter.inc();
		// ------------------------ custom counter ------------------------//

//		Timer t = metricRegistry.timer("endpoint.MKget-by-email");
//		Timer.Context c = t.time();

		User user;

		log.info("Microservice get-by-email executed ["+email+"]");

		Set<String> keys = headers.keySet();
		System.out.println("Headers start");

		for (String key : keys) {

			List<String> value = headers.get(key);

			int size = value.size();
			
			for (int i=0;i<size;i++) 			System.out.println(key + " " +value.get(i));



		}
		
		
		System.out.println("Headers end");


//		System.out.println("/get-by-email " + email);

		String userId = "";
		try {
			user = userRepository.findByEmail(email);

			log.info("getId " + user.getId());
			log.info("getName " + user.getName());
//			System.out.println("getEmail " + user.getEmail());

			userId = String.valueOf(user.getId());

//			this.counterService.increment("get-by-email.MKtxnCount");
//			this.counterService.increment("get-by-email.MKtxnCount2");

			int randomNum = ThreadLocalRandom.current().nextInt(0, 300 + 1);
//			System.out.println("get-by-email.MKcustomgauge generated " + randomNum);
//			System.out.println("get-by-email.MKcustomgauge2 generated " + randomNum * 2);

//			this.gaugeService.submit("get-by-email.MKcustomgauge", randomNum);
//			this.gaugeService.submit("get-by-email.MKcustomgauge2", randomNum * 2);

		} catch (Exception ex) {

			System.out.println("error " + ex.getMessage());
			System.out.println("error " + ex.getLocalizedMessage());

			return null;
		} finally {
//			c.stop();

		}
		// return "The user id is: " + userId;
		return user;
	}

	@RequestMapping("/test")
	public int pow() {
		log.info("Handling /api/test");

//		Timer t = metricRegistry.timer("endpoint.test");
		// System.out.println("2");

//		Timer.Context c = t.time();
		try {
			// System.out.println("Handling /api/test");

//			this.counterService.increment("greetMK.txnCount");
//			this.gaugeService.submit("greetMK.customgauge", 1.0);

			return 12345;
		} finally {
//			c.stop();

		}

	}

	@RequestMapping("/power")
	public Calculation pow(@RequestParam(value = "base") String b, @RequestParam(value = "exponent") String e) {

		List<String> input = new ArrayList();
		input.add(b);
		input.add(e);
		List<String> output = new ArrayList();
		String powValue = "";
		if (b != null && e != null && b.matches(PATTERN) && e.matches(PATTERN)) {
			powValue = String.valueOf(Math.pow(Double.valueOf(b), Double.valueOf(e)));
		} else {
			powValue = "Base or/and Exponent is/are not set to numeric value.";
		}
		output.add(powValue);
		return new Calculation(input, output, "power");
	}

	@RequestMapping(value = "/sqrt/{value:.+}", method = GET)
	public Calculation sqrt(@PathVariable(value = "value") String aValue) {

		System.out.println("1");
//		Timer t = metricRegistry.timer("endpoint.sqrt");
		System.out.println("2");

//		Timer.Context c = t.time();
		System.out.println("3");

		List<String> input = new ArrayList();
		input.add(aValue);
		List<String> output = new ArrayList();
		String sqrtValue = "";
		if (aValue != null && aValue.matches(PATTERN)) {
			sqrtValue = String.valueOf(Math.sqrt(Double.valueOf(aValue)));
		} else {
			sqrtValue = "Input value is not set to numeric value.";
		}

		output.add(sqrtValue);

		try {

			return new Calculation(input, output, "sqrt");

		} // try

		finally {
//			c.stop();

		}

	}

}
