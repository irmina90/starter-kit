package pl.spring.demo.mock;
/**
 * @COPYRIGHT (C) 2015 Schenker AG
 *
 * All rights reserved
 */

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.spring.demo.dao.BookDao;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.mapper.BookMapper;
import pl.spring.demo.service.impl.BookServiceImpl;
import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookTo;

/**
 * TODO The class BookServiceImplTest is supposed to be documented...
 *
 * @author AOTRZONS
 */
public class BookServiceImplTest {

    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookDao bookDao;
    private BookMapper bookMapper = new BookMapper();

    @Before
    public void setUpt() {
        MockitoAnnotations.initMocks(this);
    }

    @Ignore
    @Test
    public void testShouldSaveBook() {
        // given
        BookEntity book = new BookEntity(null, "title", Arrays.asList(new AuthorTo(1L,"name","lastname")));
        Mockito.when(bookDao.save(book)).thenReturn(new BookEntity(null, "title", Arrays.asList(new AuthorTo(1L,"name","lastname"))));
        // when
        BookTo result = bookService.saveBook(bookMapper.map(book));
        // then
        Mockito.verify(bookDao).save(book);
        assertEquals(1L, result.getId().longValue());
    }
    
}
