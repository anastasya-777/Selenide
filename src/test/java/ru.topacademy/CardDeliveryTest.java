package ru.topacademy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;

import static com.codeborne.selenide.Selenide.open;


public class CardDeliveryTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    public String dateGenerator(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
        // Метод генерации Даты от текущей
    }

    @Test
    public void sendForm() {
        String date = dateGenerator(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Александр Александров-Борисов");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=notification] .notification__title").shouldHave(exactText("Успешно!"), Duration.ofSeconds(20));
        $("[data-test-id=notification] .notification__content").shouldHave(exactText("Встреча успешно забронирована на " + date));
    }

    @Test
    public void validateCity() {
        String date = dateGenerator(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Берёзовский");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Александр Александров-Борисов");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=city] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    public void noCity() {
        String date = dateGenerator(3, "dd.MM.yyyy");

        $(".calendar-input__custom-control input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Александр Александров-Борисов");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=city] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    public void validateName() {
        String date = dateGenerator(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Alexandrov Alexander");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    public void noName() {
        String date = dateGenerator(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(date);
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=name] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    public void validateDate() {
        String date = dateGenerator(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Александр");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=date] .input__sub").shouldHave(exactText("Неверно введена дата"));

    }

    @Test
    public void validatePhone() {
        String date = dateGenerator(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Александр");
        $("[data-test-id=phone] input").setValue("+7 (999) 999 99 99");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    public void noPhone() {
        String date = dateGenerator(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Александр");
        $(".checkbox__box").click();
        $(".button").click();
        $("[data-test-id=phone] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    public void noCheckBox() {
        String date = dateGenerator(3, "dd.MM.yyyy");

        $("[data-test-id=city] input").setValue("Екатеринбург");
        $(".calendar-input__custom-control input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Александр");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $(".button").click();
        $(".input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }
}
