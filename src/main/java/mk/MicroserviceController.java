package mk;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.boot.actuate.metrics.CounterService;
//import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.LongTaskTimer.Sample;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import mk.dao.MongoUserRepository;
import mk.dao.MysqlUserRepository;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

//2.0.1
//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.Metrics;

//1.5.10
//import com.codahale.metrics.Counter;
//import com.codahale.metrics.Gauge;
//import com.codahale.metrics.MetricRegistry;
//import com.codahale.metrics.Timer;


import mk.metrics.SampleMetricBean;
import mk.model.MongoUser;
import mk.model.User;
import mk.service.MysqlUserService;
import mk.service.UserNumService;
import mk.service.UserNumServiceImpl;

//@EnableDiscoveryClient
@RestController
@RequestMapping("/api")
public class MicroserviceController {

	public MicroserviceController() {
		super();

		/*
		Counter counter = Counter
				  .builder("instance")
				  .description("indicates instance count of the object")
				  .tags("dev", "performance")
				  .register(registry);
				  
				  	*/

				  	
				  	}

	@Autowired
	private UserNumService userNumService;
	
	@Autowired
	private SampleMetricBean sampleBean;

	@Autowired
	MeterRegistry registry;
	
//	private final Counter successesCounter = (Counter) Metrics.counter("MKsuccessesCounter6.index", "result", "success");

	private static Logger log = LoggerFactory.getLogger(MicroserviceController.class);

	// Private fields

//	@Autowired
//	private MysqlUserRepository mysqlUserRepository;

	@Autowired
	private MysqlUserService mysqlUserService;

	@Autowired
	private MongoUserRepository mongoUserRepository;
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

	@RequestMapping("/createmysqluser")
	// @ResponseBody
	public User createMysqlUser(String email, String name) {
		String userId = "";
		User user = null;
		try {
			user = new User(email, name);
			//mysqlUserRepository.save(user);
			mysqlUserService.addUser(user);
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

//		Timer t = registry.timer("MK_execution time");
//		Timer.Context c = t.time();
		log.info("Microservice findAllUsersByEmail executed");

		List<User> users;

		String userId = "";
		try {
			//users = mysqlUserRepository.findAllUsersByEmail(email);
			users = mysqlUserService.findAllUsersByEmail(email);

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
	
	// http://localhost:9191/api/getMongoUserByEmail?email=marcin2@marcin.com
	@RequestMapping("/getMongoUserByEmail")	
	public MongoUser getMongoUserByEmail(@RequestParam(value = "email", defaultValue = ".") String email,
			@RequestHeader HttpHeaders headers) {
		
		MongoUser user = mongoUserRepository.findByEmail(email);
		List<MongoUser> users = mongoUserRepository.findAll();

		System.out.println("email "+email);		
		System.out.println("red users");
		
		int size=users.size();
		
		for (int i=0;i<size;i++)
		{
			System.out.println(users.get(i));
			
		}//for
		
		System.out.println(user);
		
		return user;
	}

	//http://localhost:9191/api/adduser
	
	
	/*
{
	"_id" :3,
	"name" : "Marcin2",
	"email" : "marcin2@marcin.com",
}
	 */
	
	//@RequestMapping(value="/adduser", method = RequestMethod.POST)

	public MongoUser addUser_fallbackMethod(MongoUser user,@RequestHeader HttpHeaders headers)
	{
		MongoUser user2= new MongoUser();
		user2.setId(new Long(12345));
		user2.setName("fallback");
		user2.setEmail("fallback@fallback");
		return user2;
		
	}
	
    //@HystrixCommand(fallbackMethod = "getDataFallBack",ignoreExceptions = { HttpServerErrorException.class } ,commandProperties = {
    //	     @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") ,
    // 	     @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000") 
    //	     })	
	
	@HystrixCommand(commandKey = "addUser", fallbackMethod = "addUser_fallbackMethod")
	@RequestMapping(method = RequestMethod.POST, value = "/adduser",produces = MediaType.APPLICATION_JSON_VALUE)
	//@PostMapping("/adduser")
	public MongoUser addUser(@RequestBody MongoUser user,@RequestHeader HttpHeaders headers)
	{

		log.info("addUser START: version :"+System.getenv("VERSION_NAME"));
		
		Set<String> keys = headers.keySet();
		System.out.println("Headers start");

		for (String key : keys) {

			List<String> value = headers.get(key);

			int size = value.size();
			
			for (int i=0;i<size;i++) 			System.out.println(key + " " +value.get(i));



		}
		
		
		System.out.println("Headers end");


		log.info("executing addUser "+user);

		log.info(user.toString());
		
		
//		MongoUser user2= new MongoUser();
//		user2.setId(new Long(12345));
//		user2.setName("Marcin2");
//		user2.setEmail("marcin2@marcin.com");

		log.info("Getting id");
		if (userNumService==null )
			log.info("userNumService is null");
		else log.info("userNumService is not null");

		long id=userNumService.getNext();
		log.info("id:"+id);

		user.setId(id);
		
		mongoUserRepository.save(user);
		log.info("after save "+user);
		log.info("addUser END");
		return user;
	}

	@RequestMapping("/get-by-email2")
	public Mono<User> getByEmail2(@RequestParam(value = "email", defaultValue = ".") String email,@RequestHeader HttpHeaders headers) {

		
		  return Mono.fromCallable(() -> {

				log.info("getByEmail2 START");

				String threadName=Thread.currentThread().getName() ;
		    	System.out.println("threadName "+threadName);

		        return getByEmailNoCache(email,headers);
		    }).subscribeOn(Schedulers.elastic());
		
		
//		return user;		
	}
	
	// http://localhost:8081/api/get-by-email?email=x@x.com
	// http://springbootmicroservice-cs:9191/api/get-by-email?email=x@x.com
	@RequestMapping("/get-by-email")
	// @ResponseBody
	@Cacheable(value= "getByEmail",  key = "#email")
	public User getByEmail(@RequestParam(value = "email", defaultValue = ".") String email,
			@RequestHeader HttpHeaders headers) {
		log.info("getByEmail START");

		processRequest();

		long start1 = System.currentTimeMillis();
		long start2 = System.nanoTime();
		
		sampleBean.handleMessage("XXX");
		
	
		// ------------------------ custom counter ------------------------//
//		Counter mkCustomCounter = metricRegistry.counter("mkCustomCounter");
//		mkCustomCounter.inc();
		// ------------------------ custom counter ------------------------//

		Timer timer = registry.timer("endpoint.MK22get-by-email.MK33_execution_time");
//		Timer timer = registry.timer("endpoint.MKget-by-email.MK_execution_time");
		
		
//		LongTaskTimer longTaskTimer = LongTaskTimer .builder("endpoint.MKget-by-email.MK_execution_time") .register(registry);

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
			//user = mysqlUserRepository.findByEmail(email);
			user = mysqlUserService.findByEmail(email);

			
			Long longobj = new Long(user.getId());
			
			Gauge gauge = Gauge  .builder("endpoint.MKget-by-email.MK_gauge", longobj,Long::longValue)
					.tags("userid",user.getId().toString())
					.tags("tag1", "1111111")
					.tags("tag2", "22222222")
					.tags("tagx", "xxxxxxxx")
					
					  .register(registry);
			
			
			
			
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
	//		long timeElapsed = longTaskTimer.stop(currentTaskId.stop());
			


			long stop1 = System.currentTimeMillis();
		    long diff1 = stop1 - start1;

			long stop2 = System.nanoTime();
			long diff2 = stop2 - start2;

			System.out.println("\nstart1="+start1+"\nstop1 ="+stop1+"\nDiff1="+diff1);
			System.out.println("\nstart2="+start2+"\nstop2 ="+stop2+"\nDiff2="+diff2);
			
			System.out.println("A");
			if (timer==null) 			System.out.println("Timer is NULL");

			if (timer!=null) 		timer.record(diff1,  TimeUnit.MILLISECONDS);
			System.out.println("B");

		}
		
		System.out.println("Microservice get-by-email end with user "+user);

		
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


	// http://localhost:8081/api/get-by-email?email=x@x.com
		// http://springbootmicroservice-cs:9191/api/get-by-email?email=x@x.com
		@RequestMapping("/get-by-email-nocache")
		public User getByEmailNoCache(@RequestParam(value = "email", defaultValue = ".") String email,
				@RequestHeader HttpHeaders headers) {
			log.info("getByEmailNoCache START");
	
			processRequest();
	
			long start1 = System.currentTimeMillis();
			long start2 = System.nanoTime();
			
			sampleBean.handleMessage("XXX");
			
		
			// ------------------------ custom counter ------------------------//
	//		Counter mkCustomCounter = metricRegistry.counter("mkCustomCounter");
	//		mkCustomCounter.inc();
			// ------------------------ custom counter ------------------------//
	
			Timer timer = registry.timer("endpoint.MK22get-by-email.MK33_execution_time");
	//		Timer timer = registry.timer("endpoint.MKget-by-email.MK_execution_time");
			
			
	//		LongTaskTimer longTaskTimer = LongTaskTimer .builder("endpoint.MKget-by-email.MK_execution_time") .register(registry);
	
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
				//user = mysqlUserRepository.findByEmail(email);
				user = mysqlUserService.findByEmail(email);
	
				
				Long longobj = new Long(user.getId());
				
				Gauge gauge = Gauge  .builder("endpoint.MKget-by-email.MK_gauge", longobj,Long::longValue)
						.tags("userid",user.getId().toString())
						.tags("tag1", "1111111")
						.tags("tag2", "22222222")
						.tags("tagx", "xxxxxxxx")
						
						  .register(registry);
				
				
				
				
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
		//		long timeElapsed = longTaskTimer.stop(currentTaskId.stop());
				
	
	
				long stop1 = System.currentTimeMillis();
			    long diff1 = stop1 - start1;
	
				long stop2 = System.nanoTime();
				long diff2 = stop2 - start2;
	
				System.out.println("\nstart1="+start1+"\nstop1 ="+stop1+"\nDiff1="+diff1);
				System.out.println("\nstart2="+start2+"\nstop2 ="+stop2+"\nDiff2="+diff2);
				
				System.out.println("A");
				if (timer==null) 			System.out.println("Timer is NULL");
	
				if (timer!=null) 		timer.record(diff1,  TimeUnit.MILLISECONDS);
				System.out.println("B");
	
			}
			
			System.out.println("Microservice get-by-email end with user "+user);
	
			
			// return "The user id is: " + userId;
			return user;
		}

}
