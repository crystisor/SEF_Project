package org.project.Entities;

public class Book
{
    private String name;
    private String author;
    private String isbn;
    private String price;
    private String quantity;
    private String image_url;
    private String libraryID;

    public Book(String name, String author, String isbn, String price, String quantity, String image_url)
    {
        this.name = name;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.quantity = quantity;
        this.image_url = image_url;
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
        return price;
    }

    public void setPrice(String price)
    {
        price = price;
    }

    public String getQuantity()
    {
        return quantity;
    }

    public void setQuantity(String quantity)
    {
        quantity = quantity;
    }

    public String getImage_url()
    {
        return image_url;
    }

    public void setImage_url(String image_url)
    {
        this.image_url = image_url;
    }


    @Override
    public String toString()
    {
        String content = "Name: " + getName() + "   Author: " + getAuthor() + "  Price: " + getPrice() + "$   Quantity: " + getQuantity();
        return content;
    }
}
