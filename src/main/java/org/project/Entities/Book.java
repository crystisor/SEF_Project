package org.project.Entities;

public class Book
{
    private String name;
    private String author;
    private long isbn;
    private int Price;
    private int Quantity;

    public Book(String name, String author, long isbn, int quantity,int price)
    {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.Price = price;
        this.Quantity = quantity;
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

    public long getIsbn()
    {
        return isbn;
    }

    public void setIsbn(long isbn)
    {
        this.isbn = isbn;
    }
}
