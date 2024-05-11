package org.project.Services;

import org.project.Entities.Book;

public class BookService
{
    public static boolean isBookValid(Book book)
    {
        char[] arr = book.getName().toCharArray();
        int count = 0;
        for (int i = 0; i < arr.length; i++)
        {
            int test = (int)arr[i];
            if ((test >= 65 && test <= 90) || (test >= 97 && test <= 122))
                count++;
        }
        if (count == arr.length)
            return true;
        else
            return false;
    }

    public static void main(String[] args)
    {
        Book book = new Book("aaa", "cccc", 57656765, 22, 100);
        System.out.println(BookService.isBookValid(book));
    }
}
