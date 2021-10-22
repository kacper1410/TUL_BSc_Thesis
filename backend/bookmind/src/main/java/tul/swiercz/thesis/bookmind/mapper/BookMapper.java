package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.Mapper;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.dto.book.BookInfo;
import tul.swiercz.thesis.bookmind.dto.book.CreateBook;
import tul.swiercz.thesis.bookmind.dto.book.ModifyBook;

@Mapper(componentModel = "spring")
public interface BookMapper extends AbstractMapper<Book> {

    BookInfo bookToDto(Book book);

    Iterable<BookInfo> booksToDtos(Iterable<Book> books);

    Book createToBook(CreateBook createBook);

    Book modifyToBook(ModifyBook createBook);

}
