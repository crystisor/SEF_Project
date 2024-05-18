package org.project.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order {

    public String userEmail;
    public List<Book> books = new ArrayList<Book>();
    public LocalDateTime orderDate;
    //public String status;
}
