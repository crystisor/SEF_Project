package org.project.Services;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.project.Entities.Book;
import org.project.Services.BookListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BookListCellRendererTest {

    private FrameFixture window;

    @BeforeEach
    void setUp() {
        JFrame frame = GuiActionRunner.execute(() -> {
            JFrame frame1 = new JFrame();
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.setSize(new Dimension(300, 200));

            JList<Book> bookJList = new JList<>(new Book[]{
                    new Book("Book A", "Author A", "ISBN A", "Price A", "Quantity A", "path/to/imageA.jpg"),
                    new Book("Book B", "Author B", "ISBN B", "Price B", "Quantity B", null)
            });

            bookJList.setCellRenderer(new BookListCellRenderer());
            frame1.add(new JScrollPane(bookJList));

            return frame1;
        });
        window = new FrameFixture(frame);
        window.show();
    }

    @AfterEach
    void tearDown() {
        window.cleanUp();
    }

    @Test
    void testBookListCellRendererWithImage() {
        GuiActionRunner.execute(() -> {
            JList<Book> list = window.robot().finder().findByType(JList.class);
            ListCellRenderer<? super Book> renderer = list.getCellRenderer();

            Book book = new Book("Book A", "Author A", "ISBN A", "Price A", "Quantity A", "path/to/imageA.jpg");
            Component component = renderer.getListCellRendererComponent(list, book, 0, true, true);

            assertNotNull(component);
            assertTrue(component instanceof JPanel);

            JPanel panel = (JPanel) component;
            JLabel lblImage = (JLabel) panel.getComponent(0);
            JLabel lblText = (JLabel) panel.getComponent(1);

            assertEquals("Name: Book A Author: Author A", lblText.getText());
            assertNotNull(lblImage.getIcon());
        });
    }

    @Test
    void testBookListCellRenderer_NoImage() {
        GuiActionRunner.execute(() -> {
            JList<Book> list = window.robot().finder().findByType(JList.class);
            ListCellRenderer<? super Book> renderer = list.getCellRenderer();

            Book book = new Book("Book B", "Author B", "ISBN B", "Price B", "Quantity B", null);
            Component component = renderer.getListCellRendererComponent(list, book, 1, true, true);

            assertNotNull(component);
            assertTrue(component instanceof JPanel);

            JPanel panel = (JPanel) component;
            JLabel lblImage = (JLabel) panel.getComponent(0);
            JLabel lblText = (JLabel) panel.getComponent(1);

            assertEquals("Name: Book B Author: Author B", lblText.getText());
            assertNull(lblImage.getIcon());
        });
    }
}