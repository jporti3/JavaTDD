package com.example.tddspringboot;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class BookInicializer implements CommandLineRunner {

    @Autowired
    private IBookRepository iBookRepositorybook;


    @Override
    public void run(String... args) throws Exception{

        log.info("Starting to initialize sample data");
        Faker faker = new Faker();
        for( int i=0; i < 11; i++){
            Book book = new Book();
            book.setAuthor(faker.book().author());
            book.setTitle(faker.book().title());
            book.setIsbn(UUID.randomUUID().toString());

            iBookRepositorybook.save(book);

        }
        log.info("finished with data initialization");
    }
}
