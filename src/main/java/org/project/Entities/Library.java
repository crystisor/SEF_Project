package org.project.Entities;

public class Library
{
    private static int bookCount;
    private String ID;
    private String name;
    private String address;
    private String phone;
    private String email;


    public Library(String ID, String name, String address, String phone, String email)
    {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public static int getBookCount()
    {
        return bookCount;
    }

    public static void setBookCount(int bookCount)
    {
        Library.bookCount = bookCount;
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public static void main(String[] args)
    {
        System.out.println("Hello World!");
    }
}
