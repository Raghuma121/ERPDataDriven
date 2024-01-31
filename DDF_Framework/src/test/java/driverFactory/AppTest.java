package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import config.AppUtil;
import utilities.ExcelFileUtil;

public class AppTest extends AppUtil {

	String inputpath="./FileInput/LoginData.xlsx";
	String outputpath="./FileOutput/DataDrivenResults.xlsx";
	ExtentReports report;
	ExtentTest logger;
	@Test
	public void startTest() throws Throwable
	{
		//path of html
		report=new ExtentReports("./target/Reports/DataDriven.html");
		
	//create object for excel file util class
		ExcelFileUtil xl=new ExcelFileUtil(inputpath);
		//count no of rows in login sheet
		int rc= xl.rowCount("Login");
		Reporter.log("No of rows are::"+rc,true);
		for(int i=1;i<=rc;i++)
		{
			logger=report.startTest("Validate Login");
			String user=xl.getCellData("Login", i, 0);
			String pass=xl.getCellData("Login", i, 1);
			//call adminlogin method from functionlibrary class
			boolean res=FunctionLibrary.adminLogin(user, pass);
			if(res)
			{
				//Write login success in result cell
				xl.setCellData("Login", i, 2, "Login Success", outputpath);
				xl.setCellData("Login", i, 3, "Pass",outputpath);
				logger.log(LogStatus.PASS, "Valid Username and Password");
			}else
			{
				//take screenshot and store in file
				File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				//copy screen shot into local system
				FileUtils.copyFile(screen, new File("./Screenshot/Iteration/"+i+"Loginpage.png"));
				xl.setCellData("Login", i, 2, "Login Fail",outputpath);
				xl.setCellData("Login", i, 3, "fail",outputpath);
				logger.log(LogStatus.FAIL, "Invalid Username and Password");
			}
			report.endTest(logger);
			report.flush();
		}
}
	
}
