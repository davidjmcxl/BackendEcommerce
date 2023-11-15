package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.model.Dto.ListUserDTO;
import com.ecommerce.ecommerce.model.Dto.RegisterUserDTO;
import com.ecommerce.ecommerce.model.Dto.UpdateUserDTO;
import com.ecommerce.ecommerce.model.Mensaje;
import com.ecommerce.ecommerce.model.entities.Product;
import com.ecommerce.ecommerce.model.entities.User;
import com.ecommerce.ecommerce.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<ListUserDTO> getUsers(Pageable paginacion) {
        return userRepository.findAll(paginacion).map(ListUserDTO::new);
    }

    public ListUserDTO registerUser(RegisterUserDTO registerUserDTO) {
        if (userRepository.existsByEmail(registerUserDTO.email())) {

            throw new ValidationException("El Email ya existe con otro usuario ");
        }
        if (userRepository.existsByIdentification(registerUserDTO.identification())) {
            throw new ValidationException("El documento  ya existe on otro usuario ");
        }
        User newUser = userRepository.save(new User(registerUserDTO));
        return new ListUserDTO(newUser);
    }


    public ListUserDTO updateUser(UpdateUserDTO datesUpdate, Long id) {
        if (userRepository.existsByIdNotAndEmail(id, datesUpdate.email())) {

            throw new ValidationException("El email ya esta registrado con  otro usuario ");
        }
        if (userRepository.existsByIdNotAndIdentification(id, datesUpdate.identification())) {

            throw new ValidationException("El Documento ya esta registrado con  otro usuario");
        }

        if (userRepository.existsById(id)) {
            var userOld = userRepository.findById(id).get();
            userOld.setIdentification(datesUpdate.identification());
            userOld.setEmail(datesUpdate.email());
            userOld.setName(datesUpdate.name());
            userOld.setAddress(datesUpdate.address());
            if (datesUpdate.password() != null) {
                userOld.setPassword(new BCryptPasswordEncoder().encode(datesUpdate.password()));
            }
            User updatedUser = userRepository.save(userOld);

            return new ListUserDTO(updatedUser);
        } else {
            throw new EntityNotFoundException("No se encontró un tema con el ID proporcionado");
        }
    }

    public Mensaje deleteUser(Long id) {
        try {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("No se encontró un usuario con el ID proporcionado"));

            // Elimina el usuario de la base de datos

                userRepository.deleteById(id);
                return new Mensaje("Usuario eliminado correctamente");

        } catch (Exception e) {
            // Maneja cualquier excepción que pueda ocurrir durante el proceso de eliminación
            return new Mensaje("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}
