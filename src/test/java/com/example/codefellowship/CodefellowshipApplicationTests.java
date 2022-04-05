package com.example.codefellowship;

import com.example.codefellowship.web.WebController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CodefellowshipApplicationTests {


	@LocalServerPort
	private int port;
	@Autowired
	WebController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Autowired
	private TestRestTemplate restTemplate;


	@Test
	public void testSplashPage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class)).contains("Welcome to our website");
	}

	@Test
	public void testSignUpPage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/signup", String.class)).contains("First Name");
	}

}
