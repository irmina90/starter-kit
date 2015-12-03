package pl.spring.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.spring.demo.dao.BookDao;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.mapper.BookMapper;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

@Service("bookService")
public class BookServiceImpl implements BookService {

    private BookDao bookDao;
    private BookMapper bookMapper;

    @Override
    public List<BookTo> findAllBooks() {
        return bookMapper.map2To(bookDao.findAll());
    }

    @Override
    public List<BookTo> findBooksByTitle(String title) {
        return bookMapper.map2To(bookDao.findBookByTitle(title));
    }

    @Override
    public List<BookTo> findBooksByAuthor(String author) {
        return bookMapper.map2To(bookDao.findBooksByAuthor(author));
    }

    @Override
    public BookTo saveBook(BookTo book) {
    	 if(book.getTitle() == null || book.getAuthors() == null) throw new BookNotNullIdException();
    	 return bookMapper.map(bookDao.save(bookMapper.map(book)));	
    }

    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Autowired
	public void setBookMapper(BookMapper bookMapper) {
		this.bookMapper = bookMapper;
		
	}
	
}
