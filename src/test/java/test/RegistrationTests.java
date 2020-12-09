package test;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class RegistrationTests extends TestBase {

    @Test
    @DisplayName("Successful registration form test")
    void successfulRegistrationTest() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String userEmail = fakeValuesService.bothify("????##@gmail.com");
        String userNumber = fakeValuesService.regexify("[0-9]{10}");
        String address = faker.address().fullAddress();
        String subject = "Biology";
        String gender = "Male";
        String hobbies = "Sports";
        String date = "1 December,2020";
        String state = "NCR";
        String city = "Delhi";
        File file = new File("src/test/resources/test.jpg");

        step("Open registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill registration form", () -> {
            $("#firstName").val(firstName);
            $("#lastName").val(lastName);
            $("#userEmail").val(userEmail);
            $x("//*[@for='gender-radio-1']").click();
            $("#userNumber").val(userNumber);
            $("#dateOfBirthInput").click();
            $(".react-datepicker__day--001").click();
            $("#subjectsInput").click();
            $("#subjectsInput").val(subject);
            $$("div[id^='react-select-2-option']").find(text(subject)).click();
            $x("//*[@for='hobbies-checkbox-1']").click();
            $("#uploadPicture").uploadFile(file);
            $("#currentAddress").val(address);
            $("#state").scrollTo().click();
            $(byText(state)).click();
            $("#city").click();
            $(byText(city)).click();
            $("#submit").scrollTo().click();
        });

        step("Assertions after filling", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
            $x("*//tr[1]/td[2]").shouldHave(text(firstName + " " + lastName));
            $x("*//tr[2]/td[2]").shouldHave(text(userEmail));
            $x("*//tr[3]/td[2]").shouldHave(text(gender));
            $x("*//tr[4]/td[2]").shouldHave(text(userNumber));
            $x("*//tr[5]/td[2]").shouldHave(text(date));
            $x("*//tr[6]/td[2]").shouldHave(text(subject));
            $x("*//tr[7]/td[2]").shouldHave(text(hobbies));
            $x("*//tr[8]/td[2]").shouldHave(text("test.jpg"));
            $x("*//tr[9]/td[2]").shouldHave(text(address));
            $x("*//tr[10]/td[2]").shouldHave(text(state + " " + city));
        });
    }
}