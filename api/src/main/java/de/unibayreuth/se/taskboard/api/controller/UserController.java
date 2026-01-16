package de.unibayreuth.se.taskboard.api.controller;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.api.mapper.UserDtoMapper;
import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@OpenAPIDefinition(info = @Info(title = "TaskBoard", version = "0.0.1"))
@Tag(name = "Users")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAll()
                .stream()
                .map(userDtoMapper::fromBusiness)
                .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable UUID id) {
        User user = userService.getById(id);
        return userDtoMapper.fromBusiness(user);
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto dto) {
        User created = userService.create(userDtoMapper.toBusiness(dto));
        return userDtoMapper.fromBusiness(created);
    }
}
