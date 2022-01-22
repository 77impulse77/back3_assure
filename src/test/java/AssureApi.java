import static io.restassured.RestAssured.*;

import clients.BookApi;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import models.Book;
import models.BookingDates;
import models.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class AssureApi {

    static BookApi api;
    static User user = new User("admin", "password123");

    @BeforeAll
    static void setUp() {
        api = new BookApi();
        api.setSession(user);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void Auth(){

        User user = new User("admin", " password123");

        String token = given()

                .when()
                .body(user)
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .extract()
                .response()
                .jsonPath()
                .getString("token");
    }
    @Test
    void GetBookingIds(){
        RestAssured.get("https://restful-booker.herokuapp.com/booking/")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .statusLine("HTTP/1.1 200 OK");
    }

    @Test
    void CreateBooking(){


        Book book = new Book();
        book.depositpaid = true;
        book.firstname = "Alex";
        book.totalprice = 777;
        book.bookingdates = new BookingDates("2021-09-15", "2021-10-10");
        book.lastname = "thirteenth";
        book.additionalneeds = "Breakfast";
        Response response = api.createBooking(book);
        response
                .then()
                .statusCode(200);
        api.saveId(response);

    }

    @Test
    void GetBooking(){
        Book book = new Book();
        book.depositpaid = true;
        book.firstname = "Alex";
        book.totalprice = 777;
        book.bookingdates = new BookingDates("2021-09-15", "2021-10-10");
        book.lastname = "thirteenth";
        book.additionalneeds = "Breakfast";
        Response response = api.createBooking(book);
        api.saveId(response);
        RestAssured.get("https://restful-booker.herokuapp.com/booking/" + BookApi.getId())
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK");
    }


    @Test
    void UpdateBooking(){

        Book book = new Book();
        book.depositpaid = true;
        book.firstname = "Alex";
        book.totalprice = 777;
        book.bookingdates = new BookingDates("2021-09-15", "2021-10-10");
        book.lastname = "thirteenth";
        book.additionalneeds = "Breakfast";
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
        book = new Book();
        book.depositpaid = true;
        book.firstname = "Alex13";
        book.totalprice = 777;
        book.bookingdates = new BookingDates("2021-09-15", "2021-10-10");
        book.lastname = "thirteenth-13";
        book.additionalneeds = "Breakfast";

        api.updateBooking(bookingid, book)
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK");
    }



    @Test
    void deleteBooking(){
        Book book = new Book();
        book.depositpaid = true;
        book.firstname = "Alex";
        book.totalprice = 777;
        book.bookingdates = new BookingDates("2021-09-15", "2021-10-10");
        book.lastname = "thirteenth";
        book.additionalneeds = "Breakfast";
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
    void HealthCheckPing(){
        RestAssured.get("https://restful-booker.herokuapp.com/ping")
                .then()
                .statusCode(201)
                .statusLine("HTTP/1.1 201 Created");
    }



}
