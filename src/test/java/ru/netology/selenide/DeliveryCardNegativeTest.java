package ru.netology.selenide;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardNegativeTest {
    public static String setLocalDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy",
                new Locale("ru")));
    }


    @Test
    void shouldBeFailedIncorrectCityTest() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Донецк");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Доставка в выбранный город недоступна")).shouldBe(visible);
    }

    @Test
    void shouldBeFailedNameTest() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Paganini Niccolo");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);
    }

    @Test
    void shouldBeFailedMaxPhoneTest() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+7999999999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void shouldBeFailedMinPhoneTest() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+7999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void shouldBeFailedUncheckedCheckboxTest() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).shouldBe(visible);

    }

    @Test
    void shouldBeFailedEmptyCityTest() {
        String date = setLocalDate(3);
        open("http://localhost:9999");
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Поле обязательно для заполнения")).shouldBe(visible);
    }
}
