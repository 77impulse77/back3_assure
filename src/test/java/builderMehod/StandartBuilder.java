package builderMehod;

import models.Book;
import models.BookingDates;

public class StandartBuilder implements AbstractBuilder {
    String firstname = "";
    String lastname = "";
    int totalprice = 0;
    boolean depositpaid = false;
    BookingDates bookingdates = new BookingDates();
    String additionalneeds = "";


    @Override
    public AbstractBuilder setFirstname() {
        this.firstname = "Alex";
        return this;
    }

    @Override
    public String getFirstname() {

        return firstname;
    }

    @Override
    public AbstractBuilder setLastname() {
        this.lastname = "thirteenth";
        return this;
    }

    @Override
    public AbstractBuilder setTotalprice() {
        this.totalprice = 777;
        return this;
    }

    @Override
    public AbstractBuilder setDepositpaid() {
        this.depositpaid = true;
        return this;
    }

    @Override
    public AbstractBuilder setBookingdates() {
        this.bookingdates = new BookingDates("2021-09-15", "2021-10-10");
        return this;
    }

    @Override
    public AbstractBuilder setAdditionalneeds() {
        this.additionalneeds = "Breakfast";
        return this;
    }

    @Override
    public Book build() {
        Book book = new Book();
        book.depositpaid = this.depositpaid;
        book.firstname = this.firstname;
        book.totalprice = this.totalprice;
        book.bookingdates = this.bookingdates;
        book.lastname = this.lastname;
        book.additionalneeds = this.additionalneeds;
        return book;
    }
}
