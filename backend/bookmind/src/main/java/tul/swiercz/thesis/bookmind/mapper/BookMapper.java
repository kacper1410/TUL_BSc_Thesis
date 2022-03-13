package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tul.swiercz.thesis.bookmind.domain.Book;
import tul.swiercz.thesis.bookmind.dto.book.BookWithShelvesInfo;
import tul.swiercz.thesis.bookmind.dto.book.BookListInfo;
import tul.swiercz.thesis.bookmind.dto.book.CreateBook;
import tul.swiercz.thesis.bookmind.dto.book.ModifyBook;

@Mapper(componentModel = "spring")
public interface BookMapper extends AbstractMapper<Book> {

    BookWithShelvesInfo bookToDtoWithShelves(Book book);

    BookListInfo bookToDto(Book book);

    Iterable<BookListInfo> booksToDtos(Iterable<Book> books);

    Book createToBook(CreateBook createBook);

    Book modifyToBook(ModifyBook createBook);

}
