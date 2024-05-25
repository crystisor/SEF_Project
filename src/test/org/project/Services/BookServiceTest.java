package org.project.Services;

import org.junit.jupiter.api.Test;
import org.project.Entities.Book;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest
{

    @Test
    void nonASCIIinName()
    {
        Book book = new Book("[[]=[", "lllk", "78#45-111","99.12","3", "dfkmewfh");
        assertEquals(false ,BookService.isBookValid(book));   // also assert false is ok
    }

    @Test
    void numbersInAuthor()
    {
        Book book = new Book("aaaa", "a.aaaa", "454545","12.2","3", "dfkmewfh");
        assertEquals(true ,BookService.isBookValid(book));   // also assert false is ok
    }
}