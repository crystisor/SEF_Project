package org.project.Services;

import org.project.Entities.Book;

public class BookService
{
    public static boolean isBookValid(Book book)
    {
        if (book.getName().isEmpty() || book.getAuthor().isEmpty() || book.getIsbn().isEmpty() || book.getPrice().isEmpty() || book.getQuantity().isEmpty())
            return false;
        char[] arr = book.getName().toCharArray();

        return isNameValid(book.getName()) && isNameValid(book.getAuthor()) && isValueValid(book.getIsbn()) && isValueValid(book.getPrice())
                && isValueValid(book.getQuantity());
    }

    private static boolean isNameValid(String name)
    {
        char[] arr = name.toCharArray();
        int count = 0;
        for (int i = 0; i < arr.length; i++)
        {
            int test = (int)arr[i];
            if ((test >= 65 && test <= 90) || (test >= 97 && test <= 122) || arr[i] == ' ')
                count++;
        }
        if (count == arr.length)
            return true;
        else
            return false;
    }

    private static boolean isValueValid(String value)
    {
        for (int i = 0; i < value.length(); i++)
        {
            if (!isDigit(value.charAt(i)))
                return false;
        }
        return true;
    }

    private static boolean isDigit(char c)
    {
        return (c >= '0' && c <= '9') || c == '.' || c == '-';
    }

    public static void main(String[] args)
    {
        Book book = new Book("aaa", "cccc", "57656765", "22", "100", "dferwkfreijk");
        System.out.println(BookService.isBookValid(book));
    }
}
