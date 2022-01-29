package builderMehod;

import models.Book;

public class BookFactory {

    private AbstractBuilder builder;

    public BookFactory(AbstractBuilder build){
        super();
        this.builder = build;
        if (this.builder == null) {
            this.builder = new StandartBuilder();
        }
    }

    public Book buildBook (){
        return builder
                .setFirstname()
                .setLastname()
                .setDepositpaid()
                .setTotalprice()
                .setBookingdates()
                .setAdditionalneeds()
                .build();
    }
    public String getFirstname(){
        return builder.setFirstname().getFirstname();
    }
}
