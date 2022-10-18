package br.com.lucasleli.carcontrol.controller;

import br.com.lucasleli.carcontrol.model.User;
import br.com.lucasleli.carcontrol.model.dto.UserDto;
import br.com.lucasleli.carcontrol.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class UserController {

    public static final String USER_V1_URL = "/api/user/v1.0";

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(USER_V1_URL)
    public ResponseEntity<User> save(@RequestBody @Valid UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user = userService.save(user);
        return ResponseEntity.created(URI.create(USER_V1_URL + "/" + user.getId())).build();
    }

}
