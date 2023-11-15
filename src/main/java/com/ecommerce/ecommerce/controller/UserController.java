package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.model.Dto.ListUserDTO;
import com.ecommerce.ecommerce.model.Dto.RegisterProductDto;
import com.ecommerce.ecommerce.model.Dto.RegisterUserDTO;
import com.ecommerce.ecommerce.model.Dto.UpdateUserDTO;
import com.ecommerce.ecommerce.model.Mensaje;
import com.ecommerce.ecommerce.model.entities.Product;
import com.ecommerce.ecommerce.model.entities.User;
import com.ecommerce.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity <Page<ListUserDTO>> registerUser(Pageable paginacion) {// cuando se crea un producto

        Page<ListUserDTO> users =  userService.getUsers(paginacion);
        return ResponseEntity.ok().body(users);

    }
    @PostMapping
    public ResponseEntity<ListUserDTO> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {// cuando se crea un producto

        ListUserDTO newUser =  userService.registerUser(registerUserDTO);
        return ResponseEntity.ok().body(newUser);

    }
    @PutMapping("/{id}")
    public ResponseEntity<ListUserDTO> registerUser(@RequestBody UpdateUserDTO updateUserDTO ,@PathVariable Long id) {// cuando se crea un producto

        ListUserDTO updateUser =  userService.updateUser(updateUserDTO,id);
        return ResponseEntity.ok().body(updateUser);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> deleteUser(@PathVariable Long id) {

        Mensaje respMensaje =  this.userService.deleteUser(id);

        return ResponseEntity.ok().body(respMensaje);

    }
}
