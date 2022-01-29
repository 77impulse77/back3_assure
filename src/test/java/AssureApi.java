import static io.restassured.RestAssured.*;

import builderMehod.AltBuilder;
import builderMehod.BookFactory;
import builderMehod.StandartBuilder;
import clients.BookApi;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Book;
import models.BookingDates;
import models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class AssureApi {

    private static BookFactory bookFactory;

    static BookApi api;
    static User user = new User("admin", "password123");

    @BeforeAll
    static void setUp() {
        api = new BookApi();
        api.setSession(user);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeEach
    void prepare() {
        bookFactory = new BookFactory(new StandartBuilder());
    }


    @Test
    void Auth() {

        User user = new User("admin", " password123");

        given()

                .when()
                .body(user)
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .statusCode(200);

    }

    @Test
    void GetBookingIds() {
        RestAssured.get("https://restful-booker.herokuapp.com/booking/")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .statusLine("HTTP/1.1 200 OK");
    }

    @Test
    void CreateBooking() {


        Book book = bookFactory.buildBook();

        Response response = api.createBooking(book);
        response
                .then()
                .statusCode(200);
        api.saveId(response);

    }

    @Test
    void GetBooking() {
        Book book = bookFactory.buildBook();

        Response response = api.createBooking(book);
        api.saveId(response);
        RestAssured.get("https://restful-booker.herokuapp.com/booking/" + BookApi.getId())
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK");
    }


    @Test
    void UpdateBooking() {

        Book book = bookFactory.buildBook();
        given()
                .log().all();
        int bookingid = api.createBooking(book)
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .extract()
                .response()
                .jsonPath()
                .getInt("bookingid");
        bookFactory = new BookFactory(new AltBuilder());
        book = bookFactory.buildBook();

        String firstname = api.updateBooking(bookingid, book)

                .then()
                .statusCode(200)

                .statusLine("HTTP/1.1 200 OK")
                .extract()
                .response()
                .jsonPath()
                .getString("firstname");
        assert firstname.equals(bookFactory.getFirstname());


    }


    @Test
    void deleteBooking() {
        Book book = bookFactory.buildBook();
        int bookingid = api.createBooking(book)
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .extract()
                .response()
                .jsonPath()
                .getInt("bookingid");
        api.deleteBooking(bookingid)
                .then()
                .statusCode(201)
                .statusLine("HTTP/1.1 201 Created");

    }

    @Test
    void HealthCheckPing() {
        RestAssured.get("https://restful-booker.herokuapp.com/ping")
                .then()
                .statusCode(201)
                .statusLine("HTTP/1.1 201 Created");
    }


}
