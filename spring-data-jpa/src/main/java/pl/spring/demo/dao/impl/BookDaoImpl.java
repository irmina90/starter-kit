package pl.spring.demo.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.to.AuthorTo;

@Component
public class BookDaoImpl implements BookDao {

	private final Set<BookEntity> ALL_BOOKS = new HashSet<>();

	public BookDaoImpl() {
		addTestBooks();
	}

	@Override
	public List<BookEntity> findAll() {
		return new ArrayList<>(ALL_BOOKS);
	}

	@Override
	public List<BookEntity> findBookByTitle(String title) {
		return ALL_BOOKS.stream().filter(book -> book.getTitle().toLowerCase().startsWith(title.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public List<BookEntity> findBooksByAuthor(String author) {
		List<BookEntity> searchResults = new ArrayList<BookEntity>();
		for (BookEntity book : ALL_BOOKS) {
			for (AuthorTo authorTo : book.getAuthors()) {
				if (authorTo.getFirstName().toLowerCase().startsWith(author.split(" ")[0].toLowerCase())
						&& authorTo.getLastName().toLowerCase().startsWith(author.split(" ")[1].toLowerCase())) {
					searchResults.add(book);
				}
			}
		}
		return searchResults;
	}

	@Override
	@NullableId
	public BookEntity save(BookEntity book) {
		ALL_BOOKS.add(book);
		return book;
	}

	public Set<BookEntity> getAllBooks() {
		return ALL_BOOKS;
	}

	private void addTestBooks() {
		ALL_BOOKS.add(new BookEntity(1L, "Romeo i Julia", Arrays.asList(new AuthorTo(1L, "Wiliam", "Szekspir"))));
		ALL_BOOKS.add(new BookEntity(2L, "Opium w rosole", Arrays.asList(new AuthorTo(2L, "Hanna", "Ożogowska"))));
		ALL_BOOKS.add(new BookEntity(3L, "Przygody Odyseusza", Arrays.asList(new AuthorTo(3L, "Jan", "Parandowski"))));
		ALL_BOOKS
				.add(new BookEntity(4L, "Awantura w Niekłaju", Arrays.asList(new AuthorTo(4L, "Edmund", "Niziurski"))));
		ALL_BOOKS.add(new BookEntity(5L, "Pan Samochodzik i Fantomas",
				Arrays.asList(new AuthorTo(5L, "Zbigniew", "Nienacki"))));
		ALL_BOOKS.add(new BookEntity(6L, "Zemsta", Arrays.asList(new AuthorTo(6L, "Aleksander", "Fredro"))));
	}
}
