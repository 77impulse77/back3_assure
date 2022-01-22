package clients;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.cucumber.messages.JSON;
import io.qameta.allure.internal.shadowed.jackson.databind.util.JSONPObject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.Book;
import models.User;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class BookApi {

    static int bookingid = 0;

    RequestSpecification session;

    public void setSession (User user){
        session = new RequestSpecBuilder().build().header("Cookie", "token=" + getToken(user));

    }

    public Response deleteBooking (int bookId){

        return given(session)
                .pathParam("bookingId", bookId)
                .delete("https://restful-booker.herokuapp.com/booking/{bookingId}");

    }


    public Response updateBooking (int bookId, Book book){

        return given(session)
                .contentType(ContentType.JSON)
                .body(book)

                .pathParam("bookingId", bookId)
                .put("https://restful-booker.herokuapp.com/booking/{bookingId}");


    }




    public Response createBooking (Book book){
        return given()
                .contentType(ContentType.JSON)
                .body(book)
                .post("https://restful-booker.herokuapp.com/booking");

    }

    public String getToken (User user){

        String token =  given()
                .contentType(ContentType.JSON)
                .body(user)
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .statusCode(200)
                .contentType("application/json; charset=utf-8")
                .extract()
                .response()
                .jsonPath()
                .getString("token");
        System.out.println(token);
        return token;
    }


    public void saveId(Response response){


        JSONObject obj = new JSONObject(response.body().asString());

        bookingid = obj.getInt("bookingid");
        System.out.println(obj);



    }

    public static int getId(){

        return bookingid;
    }

}
