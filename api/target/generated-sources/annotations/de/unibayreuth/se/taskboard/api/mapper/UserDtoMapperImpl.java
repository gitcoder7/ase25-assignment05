package de.unibayreuth.se.taskboard.api.mapper;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.business.domain.User;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-01-17T00:22:29+0100",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserDtoMapperImpl extends UserDtoMapper {

    @Override
    public UserDto fromBusiness(User source) {
        if ( source == null ) {
            return null;
        }

        UUID id = null;
        String name = null;
        LocalDateTime createdAt = null;

        id = source.getId();
        name = source.getName();
        createdAt = mapTimestamp( source.getCreatedAt() );

        UserDto userDto = new UserDto( id, name, createdAt );

        return userDto;
    }

    @Override
    public User toBusiness(UserDto source) {
        if ( source == null ) {
            return null;
        }

        String name = null;

        name = source.name();

        User user = new User( name );

        user.setCreatedAt( mapTimestamp(source.createdAt()) );

        return user;
    }
}
