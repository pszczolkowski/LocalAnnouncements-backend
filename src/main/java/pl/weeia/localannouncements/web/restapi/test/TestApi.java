package pl.weeia.localannouncements.web.restapi.test;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApi {

	@RequestMapping(method = GET)
	public Test get() {
		return new Test(123);
	}
	
}
