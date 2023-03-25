package com.hackerrank.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DataValidator {

  // TODO
  public static boolean verifyPercentage(String indexPage, String site, WebDriver driver) {
    String appValue = null;
    String appValueAtDetailPage;
    boolean result = false;
    String siteName = null;
    driver.get(indexPage);
    driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    WebDriverWait wait = new WebDriverWait(driver, 30);
    By Element = By.cssSelector(".bar");
    wait.until(ExpectedConditions.presenceOfElementLocated(Element));
    List<WebElement> sites = driver.findElements(Element);
    for (WebElement app : sites) {
      try {
        wait.until(ExpectedConditions.presenceOfElementLocated(Element));
        siteName = app.getAttribute("data-name");
        appValue = app.getAttribute("title").replaceAll("//D", "").replace("%", "");
      } catch (Exception e) {
        e.printStackTrace();
      }

      if (siteName.contains(site)) {
        try {
          app.click();
        } catch (StaleElementReferenceException staleElementReferenceException) {
          driver.findElement(By.xpath("//*[contains(text(),'" + site + "')]")).click();
        }
        System.out.println(driver.getCurrentUrl() + "details page url");
        By ElementSiteName = By.xpath("//*[contains(text(),'" + site + "')]/../td[2]");
        appValueAtDetailPage = driver.findElement(ElementSiteName).getText();
        if (appValue.contains(appValueAtDetailPage)) {
          System.out.println(
              "SiteName "
                  + site
                  + " Pecentage of index page is +"
                  + appValue
                  + " and matched with details page "
                  + appValueAtDetailPage);
          result = true;
          break;
        }
      }
    }
    return result;
  }
}
