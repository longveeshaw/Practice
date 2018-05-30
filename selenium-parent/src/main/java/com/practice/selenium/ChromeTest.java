package com.practice.selenium;


import junit.framework.TestCase;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;


public class ChromeTest  extends TestCase{

    private static ChromeDriverService service;
    private WebDriver driver;

    public static void createAndStartService() {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("D:\\PortableSoft\\chromedriver_2.34.exe"))
                .usingAnyFreePort()
                .build();
        try {
            service.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void StopService() {
        service.stop();
    }

    public void createDriver() {
        driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
    }

    public void quitDriver() {
        driver.quit();
    }

    @Test
    public void testGoogleSearch() {
        createAndStartService();
        createDriver();

        driver.get("http://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("webdriver");

        assertEquals("Google", driver.getTitle());

        quitDriver();
        StopService();
    }
}
 