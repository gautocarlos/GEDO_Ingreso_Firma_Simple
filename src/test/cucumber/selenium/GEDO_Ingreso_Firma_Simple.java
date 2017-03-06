package test.cucumber.selenium;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GEDO_Ingreso_Firma_Simple {
	private WebDriver driver;
	// private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	WebDriverWait wait;

	@Before
	public void setUp() throws Exception {
		// driver = new FirefoxDriver();
		// baseUrl = "https://cas.nac.everis.int/";
		// driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.setProperty("webdriver.gecko.driver", "C:\\WebDrivers\\geckodriver.exe");
		// Fix problema certificado
		ProfilesIni allProfiles = new ProfilesIni();
		FirefoxProfile profile = allProfiles.getProfile("default");
		profile.setAcceptUntrustedCertificates(true);
		profile.setAssumeUntrustedCertificateIssuer(false);
		// Fix problema certificado
		driver = new FirefoxDriver(profile);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Given("^El usuario cuenta cuanto mínimo una tarea de firma en su bandeja$")
	public void el_usuario_cuenta_cuanto_minimo_una_tarea_de_firma_en_su_bandeja() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		// throw new Exception();
	}

	@When("^Ingresa a la URL \"(.*)\"$")
	public void ingresoCAS(String urlCAS) {
		System.out.println("URL: " + urlCAS);
		driver.navigate().to(urlCAS);
		wait = new WebDriverWait(driver, 10);
	}

	@When("^El usuario ingresa al sistema con sus credenciales: usuario \"(.*)\" y password \"(.*)\"$")
	public void datosLogin(String usuario, String password) {
		// System.out.println("Usuario: " + usuario);
		// System.out.println("Password: " + password);
		driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div/div/div/div[2]/div/input")).clear();
		driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div/div/div/div[2]/div/input")).sendKeys(usuario);
		driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div/div/div/div[3]/div/input")).clear();
		driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div/div/div/div[3]/div/input"))
				.sendKeys(password);
		// WebElement element = wait.until(ExpectedConditions
		// .elementToBeClickable(By.xpath("/html/body/div/div[2]/div[2]/div/div/div/div/div[4]/button")));
		driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div/div/div/div[4]/button")).click();
	}

	// @Test
	@When("^El usuario firma una tarea de firma simple con certificado$")
	public void testGEDOIngresoFirmaSimple() throws Exception {
		driver.findElement(By.xpath("//td/img")).click();
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (isElementPresent(By.xpath("//td[5]/div/div/div/img")))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.xpath("//td[5]/div/div/div/img")).click();
		/*
		 * Cerrar cartel de firma de documento y volver a la bandeja de inicio
		 */
		for (int second = 0;; second++) {
			if (second >= 60)
				fail("timeout");
			try {
				if (isElementPresent(By.xpath("//td[2]/div/div/div/div/img")))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		driver.findElement(By.xpath("//td[2]/div/div/div/div/img")).click();

	}

	@Then("^Se genera un número de documento GDE$")
	public void se_genera_un_numero_de_documento_GDE() throws Exception {
		// driver.close();
		String mensajeGeneracionDocumento = driver
				.findElement(By
						.xpath("/html/body/div[3]/div[2]/div/div/div/table/tbody/tr/td/table/tbody//tr[1]/td/table/tbody/tr/td/table/tbody/tr/td/span"))
				.getText();
		System.out.println("Mensaje de confirmación: " + mensajeGeneracionDocumento);
		/**
		 * TODO: Generar validación de mensaje de documento generado
		 * */
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
