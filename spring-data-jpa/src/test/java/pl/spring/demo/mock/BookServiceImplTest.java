package pl.spring.demo.mock;
/**
 * @COPYRIGHT (C) 2015 Schenker AG
 *
 * All rights reserved
 */

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

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
    @Mock
    private BookMapper bookMapper;

    @Before
    public void setUpt() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testShouldSaveBook() {
        // given
        BookTo bookTo1 = new BookTo(null, "title", "author");
        BookTo bookTo2 = new BookTo(1L, "title", "author");
        
        BookEntity bookEntity1 = new BookEntity(null, "title",  Arrays.asList(new AuthorTo(null, "author", null)));
        BookEntity bookEntity2 = new BookEntity(1L, "title",  Arrays.asList(new AuthorTo(null, "author", null)));
        
        Mockito.when(bookDao.save(bookEntity1)).thenReturn(bookEntity2);
        Mockito.when(bookMapper.map(bookEntity2)).thenReturn(bookTo2);
        Mockito.when(bookMapper.map(bookTo1)).thenReturn(bookEntity1);
        // when
        BookTo result = bookService.saveBook(bookTo1);
        // then
        Mockito.verify(bookDao).save(bookEntity1);
        Mockito.verify(bookMapper).map(bookEntity2);
        assertEquals(1L, result.getId().longValue());
    }
}

