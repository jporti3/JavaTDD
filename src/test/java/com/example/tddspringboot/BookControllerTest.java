package com.example.tddspringboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @Captor
    private ArgumentCaptor <BookRequest> argumentCaptor;

    @Test
    public void postingANewBookShouldCreateANewBookInTheDataBase() throws Exception {

        //Given
        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor("Juan Portilla");
        bookRequest.setTitle("100 años de código");
        bookRequest.setIsbn("1998239392");

        //When
        when(bookService.createNewBook(argumentCaptor.capture())).thenReturn(1L);

        this.mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/api/books/1"));

        //Then
        assertThat(argumentCaptor.getValue().getAuthor(), is("Juan Portilla"));
        assertThat(argumentCaptor.getValue().getTitle(), is("100 años de código"));
        assertThat(argumentCaptor.getValue().getIsbn(), is("1998239392"));


    }
    //Revisar porque no me devuelve el id como string
    @Test
    public void allBooksEndpointShouldReturnTwoBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(createBook(1L, "Libro Blanco", "Author Colombiano", "123456789")));
        this.mockMvc
                .perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Libro Blanco")))
                .andExpect(jsonPath("$[0].author", is("Author Colombiano")))
                .andExpect(jsonPath("$[0].isbn", is("123456789")));

    }
// Revisar porque no me devuelve correctamente el libro por id


    @Test
    public void updateBookWithIdShouldUpdateTheBook() throws  Exception {

        BookRequest bookRequest = new BookRequest();
        bookRequest.setAuthor("Juan Portilla");
        bookRequest.setTitle("100 años de código");
        bookRequest.setIsbn("1998239392");

        when(bookService.updateBook(eq(1L), argumentCaptor.capture()))
                .thenReturn(1L, "Juan Portilla Montealegre", "100 años de soledad" , "123");

        this.mockMvc
                .perform(put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }
    private <E> Book createBook(long id, String title, String author, String isbn) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setId(id);
        book.setIsbn(isbn);
        return book;
    }
}
