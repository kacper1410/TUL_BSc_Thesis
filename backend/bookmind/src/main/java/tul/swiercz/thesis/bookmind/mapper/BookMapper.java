package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.Mapper;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.dto.book.BookInfo;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookInfo bookToDto(Book book);
    Iterable<BookInfo> booksToDtos(Iterable<Book> books);

}
