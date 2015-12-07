package pl.spring.demo.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookTo;

public class BookMapperTest {

	private BookMapper bookMapper = new BookMapper();
	
    @Test
    public void testShouldConvertBookTo() {
    	//given
    	BookTo bookTo = new BookTo(1L, "Metro 2035", "Dmitry Glukhovsky");
        //when
    	BookEntity bookEntityExpected = new BookEntity(1L, "Metro 2035", Arrays.asList(new AuthorTo(1L,"Dmitry","Glukhovsky")));
    	BookEntity result = bookMapper.map(bookTo);
    	//then
        assertEquals(Long.valueOf(1), result.getId());
        assertEquals(bookEntityExpected.getTitle(), result.getTitle());
        assertEquals(bookEntityExpected.getAuthors().get(0).getId(), result.getAuthors().get(0).getId());
    }
    
    @Test
    public void testShouldConvertBookToWithNullId() {
    	//given
    	BookTo bookTo = new BookTo(null, "Metro 2035", "Dmitry Glukhovsky");
        //when
    	BookEntity bookEntityExpected = new BookEntity(1L, "Metro 2035", Arrays.asList(new AuthorTo(1L,"Dmitry","Glukhovsky")));
    	BookEntity result = bookMapper.map(bookTo);
    	//then
        assertEquals(null, result.getId());
        assertEquals(bookEntityExpected.getTitle(), result.getTitle());
        assertEquals(bookEntityExpected.getAuthors().get(0).getId(), result.getAuthors().get(0).getId());
    }
    
    @Test
    public void testShouldConvertBookEntity() {
    	//given
    	BookEntity bookEntity = new BookEntity(1L, "Koniec swiata", Arrays.asList(new AuthorTo(1L,"Anna", "Mikolajewicz"),new AuthorTo(2L,"Wieslaw", "Lubelski")));
        //when
    	BookTo bookToExpected = new BookTo(1L, "Koniec swiata", "1 Anna Mikolajewicz,2 Wieslaw Lubelski,");
    	BookTo result = bookMapper.map(bookEntity);
    	//then
        assertEquals(bookToExpected.getId(), result.getId());
        assertEquals(bookToExpected.getTitle(), result.getTitle());
        assertEquals(bookToExpected.getAuthors(), result.getAuthors());
    }

    @Test
    public void testShouldConvertBookEntityList() {
    	//given
    	BookEntity book1 = new BookEntity(1L, "Koniec swiata", Arrays.asList(new AuthorTo(1L,"Anna", "Mikolajewicz"),new AuthorTo(2L,"Wieslaw", "Lubelski")));
    	BookEntity book2 = new BookEntity(2L, "Poczatek swiata", Arrays.asList(new AuthorTo(3L,"Anna", "Waszkiewicz"),new AuthorTo(4L,"Jerzy", "Zdrojewski"),new AuthorTo(5L,"Maciej", "Krychowiak")));
        List<BookEntity> books = Arrays.asList(book1,book2);
    	//when
    	List<BookTo> booksToExpected = Arrays.asList(new BookTo(1L, "Koniec swiata", "1 Anna Mikolajewicz,2 Wieslaw Lubelski,"),
    												new BookTo(2L,"Poczatek swiata", "3 Anna Waszkiewicz,4 Jerzy Zdrojewski,5 Maciej Krychowiak,"));
    	List<BookTo> result = bookMapper.map2To(books);
    	//then
        assertEquals(booksToExpected.get(0).getId(), result.get(0).getId());
        assertEquals(booksToExpected.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(booksToExpected.get(0).getAuthors(), result.get(0).getAuthors());
    }
    
    @Test
    public void testShouldConvertBookToList() {
    	//given
    	BookTo book1 = new BookTo(1L, "Metro 2035", "Dawid Glukhovsky");
    	BookTo book2 = new BookTo(2L, "Popatrz w gore", "Katarzyna Mielcarek");
    	List<BookTo> books = Arrays.asList(book1,book2);
        //when
    	List<BookEntity> bookEntityExpected = Arrays.asList(new BookEntity(1L, "Metro 2035", Arrays.asList(new AuthorTo(1L,"Dawid","Glukhovsky"))),
    														new BookEntity(2L, "Popatrz w gore", Arrays.asList(new AuthorTo(2L,"Katarzyna","Mielcarek"))));
    	List<BookEntity> result = bookMapper.map2Entity(books);
    	//then
        assertEquals(bookEntityExpected.get(0).getTitle(), result.get(0).getTitle());
        assertEquals(bookEntityExpected.get(0).getAuthors().get(0).getFirstName(), result.get(0).getAuthors().get(0).getFirstName());
    }

}


