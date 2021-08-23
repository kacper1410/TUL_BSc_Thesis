package tul.swiercz.thesis.bookmind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.repositories.BookRepository;


@SpringBootApplication
public class BookmindApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookmindApplication.class, args);
	}

	private static final Logger log = LoggerFactory.getLogger(BookmindApplication.class);

	@Autowired
	private BookRepository repository;

	@Override
	public void run(String... args) {

		log.info("StartApplication...");

		repository.save(new Book("Java"));
		repository.save(new Book("Node"));
		repository.save(new Book("Python"));

		System.out.println("\nfindAll()");
		repository.findAll().forEach(System.out::println);

		System.out.println("\nfindById(1L)");
		repository.findById(1L).ifPresent(System.out::println);

		System.out.println("\nfindByName('Node')");
		repository.findByTitle("Node").forEach(System.out::println);

	}

}