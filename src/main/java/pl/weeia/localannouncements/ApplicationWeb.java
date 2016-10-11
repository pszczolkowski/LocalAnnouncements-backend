package pl.weeia.localannouncements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import pl.weeia.localannouncements.sharedkernel.constant.Profiles;

public class ApplicationWeb extends SpringBootServletInitializer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationWeb.class);

	   @Override
	   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	      return application.profiles(addDefaultProfile())
	         .sources(Application.class);
	   }

	   private String[] addDefaultProfile() {
	      String profile = System.getProperty("spring.profiles.active");
	      if (profile != null) {
	         LOGGER.info("Running with Spring profile(s) : {}", profile);
	         return new String[]{profile};
	      }

	      LOGGER.warn("No Spring profile configured, running with default configuration");
	      return new String[]{Profiles.PRODUCTION};
	   }
	   
}