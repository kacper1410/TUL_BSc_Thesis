package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.Mapper;
import tul.swiercz.thesis.bookmind.domain.ShelfAction;
import tul.swiercz.thesis.bookmind.dto.action.ShelfActionDto;

@Mapper(componentModel = "spring")
public interface ShelfActionMapper {

    ShelfAction dtoToShelfAction(ShelfActionDto shelfActionDto);
}
