package builderMehod;
import models.Book;

public interface AbstractBuilder {



    public AbstractBuilder setFirstname() ;

    public AbstractBuilder setLastname( ) ;

    public AbstractBuilder setTotalprice( ) ;

    public AbstractBuilder setDepositpaid( ) ;

    public AbstractBuilder setBookingdates( ) ;

    public AbstractBuilder setAdditionalneeds( ) ;

    public Book build ();

    public String getFirstname();


}
