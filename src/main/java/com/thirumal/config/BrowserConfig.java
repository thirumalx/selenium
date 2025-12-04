package com.thirumal.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PostConstruct;

@Configuration
public class BrowserConfig {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Value("${browser.name}")
    private String browser;

    @Value("${base.url}")
    private String baseUrl;
    
    @PostConstruct
    public void init() {
    	logger.info("Browser = {}", browser);
        logger.info("Base URL = {}", baseUrl);
    }

    @Bean(destroyMethod = "quit")
    WebDriver webDriver() {
        WebDriverManager.chromedriver().setup();
        return switch (browser.toLowerCase()) {
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            default -> throw new IllegalStateException("Unsupported browser: " + browser);
        };
    }

    @Bean
    String baseUrl() {
        return baseUrl;
    }
}
