package pl.spring.demo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "CommonServiceTest-context.xml")
public class BookServiceImplTest {

    @Autowired
    private BookService bookService;

    @Test
    public void testShouldFindAllBooks() {
        // when
        List<BookTo> allBooks = bookService.findAllBooks();
        // then
        assertNotNull(allBooks);
        assertFalse(allBooks.isEmpty());
        assertEquals(6, allBooks.size());
    }

    @Test
    public void testShouldFindAllBooksByTitle() {
        // given
        final String title = "Opium w rosole";
        // when
        List<BookTo> booksByTitle = bookService.findBooksByTitle(title);
        // then
        assertNotNull(booksByTitle);
        assertFalse(booksByTitle.isEmpty());
    }
    
    @Test
    public void testShouldFindAllBooksByPartOfTitle() {
        // given
        final String title = "Opium";
        // when
        List<BookTo> booksByTitle = bookService.findBooksByTitle(title);
        // then
        assertNotNull(booksByTitle);
        assertFalse(booksByTitle.isEmpty());
        assertEquals("Opium w rosole", booksByTitle.get(0).getTitle());
    }
    
    @Test
    public void testShouldFindAllBooksByAuthor() {
        // given
        final String author = "Zbigniew Nienacki";
        // when
        List<BookTo> booksByAuthor = bookService.findBooksByAuthor(author);
        // then
        assertNotNull(booksByAuthor);
        assertFalse(booksByAuthor.isEmpty());
        assertEquals("Pan Samochodzik i Fantomas", booksByAuthor.get(0).getTitle());
    }
    
    @Test
    public void testShouldFindAllBooksByAuthorLowCase() {
        // given
        final String author = "zbigniew nienacki";
        // when
        List<BookTo> booksByAuthor = bookService.findBooksByAuthor(author);
        // then
        assertNotNull(booksByAuthor);
        assertFalse(booksByAuthor.isEmpty());
        
    }

    @Test(expected = BookNotNullIdException.class)
    public void testShouldThrowBookNotNullIdException() {
        // given
        final BookTo bookToSave = new BookTo();
        bookToSave.setId(22L);
        // when
        bookService.saveBook(bookToSave);
        // then
        fail("test should throw BookNotNullIdException");
    }
}
