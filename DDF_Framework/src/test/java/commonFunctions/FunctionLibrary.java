package commonFunctions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.testng.Reporter;

import config.AppUtil;

public class FunctionLibrary extends AppUtil{
public static boolean adminLogin(String username,String password) throws Throwable
{
	driver.get(conpro.getProperty("Url"));
	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	driver.findElement(By.xpath(conpro.getProperty("ObjReset"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Objuser"))).sendKeys(username);
	driver.findElement(By.xpath(conpro.getProperty("Objpass"))).sendKeys(password);
	driver.findElement(By.xpath(conpro.getProperty("Objlogin"))).click();;	
	String Expected="dashboard";
	String Actual=driver.getCurrentUrl();
	if(Actual.contains(Expected))
	{
		Reporter.log("Username and Password are Valid::"+Expected+" "+Actual+" ",true);
		Thread.sleep(3000);
		//click logout link
		driver.findElement(By.xpath(conpro.getProperty("ObjLogoutLink"))).click();
		return true;
	}else
	{
		String Errormessage=driver.findElement(By.xpath(conpro.getProperty("ObjError_Message"))).getText();
		driver.findElement(By.xpath(conpro.getProperty("Objokbutton"))).click();
		Reporter.log(Errormessage+" "+Expected+" "+Actual+" ",true);
	   return false;
}
	
}}
