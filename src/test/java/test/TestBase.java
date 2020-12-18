package test;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;

public class TestBase {
    @BeforeAll
    static void setUp() {
        String selenoidLogin = System.getProperty("selenoid.username");
        String selenoidPwd = System.getProperty("selenoid.pwd");
        String remoteBrowser = System.getProperty("remote.browser.url");
        String url = "http://"+ selenoidLogin + ":" + selenoidPwd  + "@" + remoteBrowser + ":4444/wd/hub/";

        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVnc",true);
        capabilities.setCapability("enableVideo",true);

        Configuration.browserCapabilities = capabilities;
        Configuration.remote = url;
        Configuration.startMaximized = true;
    }

    @AfterEach
    @Step("Attachments")
    public void afterEach(){
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        attachVideo();
        closeWebDriver();
    }
}
