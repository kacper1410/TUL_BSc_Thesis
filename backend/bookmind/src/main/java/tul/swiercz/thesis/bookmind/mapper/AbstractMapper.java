package tul.swiercz.thesis.bookmind.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

public interface AbstractMapper<DOMAIN> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(DOMAIN newDomain, @MappingTarget DOMAIN DomainToUpdate);

}
