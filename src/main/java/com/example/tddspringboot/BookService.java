package com.example.tddspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private IBookRepository iBookRepository;
    public Long createNewBook(BookRequest bookRequest) {

        Book book = new Book();
        book.setAuthor(bookRequest.getAuthor());
        book.setTitle(bookRequest.getTitle());
        book.setIsbn(bookRequest.getIsbn());
        book = iBookRepository.save(book);

        return book.getId();
    }

    public List<Book> getAllBooks() {

        return iBookRepository.findAll();
    }

    public Book getBookById(long id) {

        Optional<Book> requestedBook = iBookRepository.findById(id);

        return requestedBook.get();

    }

    @Transactional
    public Object updateBook(long id, BookRequest bookToUpdateRequest) {

        Optional<Book> bookFromDatabase = iBookRepository.findById(id);

        Book bookToUpdate = bookFromDatabase.get();

        bookToUpdate.setTitle(bookToUpdateRequest.getTitle());
        bookToUpdate.setAuthor(bookToUpdateRequest.getAuthor());
        bookToUpdate.setIsbn(bookToUpdateRequest.getIsbn());
        return bookToUpdate;
    }

    public Object deleteBookById(Long id) {
        Optional<Book> requestedBook = iBookRepository.findById(id);
        return null;

    }
}
