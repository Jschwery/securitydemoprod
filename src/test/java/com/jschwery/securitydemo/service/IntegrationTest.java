//package com.jschwery.securitydemo.service;
//
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//
//import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
//
//@RunWith(SpringRunner.class)
//public class IntegrationTest {
//    @SpringBootTest(webEnvironment = RANDOM_PORT)
//    public class BasicConfigurationIntegrationTest {
//
//        URL base;
//        @LocalServerPort int port;
//
//        @Before
//        public void setUp() throws MalformedURLException {
//            base = new URL("http://localhost:" + port);
//        }
//
//    }
