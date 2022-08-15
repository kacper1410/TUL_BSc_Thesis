package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import tul.swiercz.thesis.bookmind.domain.ShelfBook;
import tul.swiercz.thesis.bookmind.dto.book.BookOnShelfListInfo;

@Mapper(componentModel = "spring")
public interface ShelfBookMapper {

    @Mappings({
            @Mapping(source = "version", target = "connectionVersion"),
            @Mapping(source = "book.title", target = "title"),
            @Mapping(source = "book.author", target = "author"),
            @Mapping(source = "book.id", target = "id"),
            @Mapping(source = "book.version", target = "version")
    })
    BookOnShelfListInfo mapToBookOnShelf(ShelfBook shelfBook);
}
