package de.unibayreuth.se.taskboard.api.mapper;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.business.domain.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-16T22:52:02+0100",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserDtoMapperImpl extends UserDtoMapper {

    @Override
    public UserDto fromBusiness(User source) {
        if ( source == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        return userDto;
    }

    @Override
    public User toBusiness(UserDto source) {
        if ( source == null ) {
            return null;
        }

        String name = null;

        User user = new User( name );

        return user;
    }
}
