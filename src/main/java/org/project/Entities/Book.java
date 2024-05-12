package org.project.Entities;

public class Book
{
    private String name;
    private String author;
    private String isbn;
    private String Price;
    private String Quantity;

    public Book(String name, String author, String isbn, String price, String quantity)
    {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        Price = price;
        Quantity = quantity;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getIsbn()
    {
        return isbn;
    }

    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }

    public String getPrice()
    {
        return Price;
    }

    public void setPrice(String price)
    {
        Price = price;
    }

    public String getQuantity()
    {
        return Quantity;
    }

    public void setQuantity(String quantity)
    {
        Quantity = quantity;
    }
}
