package org.project.Entities;

public class Feedback
{
    private String userID;
    private String date;
    private String content;
    private String orderID;

    public Feedback(String userID, String ID, String date, String content)
    {
        this.userID = userID;
        this.orderID = ID;
        this.date = date;
        this.content = content;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getOrderID()
    {
        return orderID;
    }

    public void setOrderID(String orderID)
    {
        this.orderID = orderID;
    }
}
