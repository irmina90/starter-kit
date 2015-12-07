package pl.spring.demo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookTo;

@Component
public class BookMapper {

	public BookEntity map(BookTo bookTo) {
		if (bookTo == null)
			return null;
		return new BookEntity(bookTo.getId(), bookTo.getTitle(), convertToList(bookTo.getAuthors()));
	}

	public BookTo map(BookEntity bookEntity) {
		if (bookEntity == null)
			return null;
		return new BookTo(bookEntity.getId(), bookEntity.getTitle(), convertToString(bookEntity.getAuthors()));
	}

	public List<BookEntity> map2Entity(List<BookTo> bookTos) {
		List<BookEntity> bookEntities = new ArrayList<BookEntity>();
		for (BookTo book : bookTos) {
			bookEntities.add(map(book));
		}
		return bookEntities;
	}

	public List<BookTo> map2To(List<BookEntity> bookEntities) {
		List<BookTo> bookTos = new ArrayList<BookTo>();
		for (BookEntity book : bookEntities) {
			bookTos.add(map(book));
		}
		return bookTos;
	}

	private List<AuthorTo> convertToList(String authors) {
		List<AuthorTo> authorsList = new ArrayList<AuthorTo>();
		Long id = 1L;
		if (!authors.isEmpty()) {
			String[] authorsArray = authors.split(",");
			for (String author : authorsArray) {
				String[] items = author.trim().split(" ");
				AuthorTo authorObj = new AuthorTo(id, items[0], items[1]);
				authorsList.add(authorObj);
				id++;
			}
		}
		return authorsList;
	}

	private String convertToString(List<AuthorTo> authorsArray) {
		StringBuffer authors = new StringBuffer();
		if (authorsArray != null) {
			for (AuthorTo author : authorsArray) {
				authors.append(author.getId()).append(" ").append(author.getFirstName()).append(" ")
						.append(author.getLastName()).append(",");
			}
		}
		return authors.toString();
	}

}
