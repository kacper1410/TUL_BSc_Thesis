package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.Mapper;
import tul.swiercz.thesis.bookmind.domain.AccessLevel;
import tul.swiercz.thesis.bookmind.dto.user.AccessLevelInfo;

@Mapper(componentModel = "spring")
public interface AccessLevelMapper {

    AccessLevel dtoToAccessLevel(AccessLevelInfo accessLevelInfo);
}
