package com.pipaysimplificado.repositories;

import com.pipaysimplificado.domain.user.User;
import com.pipaysimplificado.domain.user.UserType;
import com.pipaysimplificado.dtos.UserDTO;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("When find user by document then return the user with success from database")
    void whenFindUserByDocumentThenReturnTheUserSucessfully() {
        String document = "12345678900";
        UserDTO data = new UserDTO("mATHEUS", "Teste",
                document, new BigDecimal(10), "teste@gmail.com", "1234", UserType.COMMON);
        createUser(data);

        Optional<User> result = userRepository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("When find user by document then not return if user do not exists in database")
    void whenFindUserByDocumentThenNotReturnIfUserDoNotExists() {
        String document = "12345678900";
        Optional<User> result = userRepository.findUserByDocument(document);

        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserDTO data){
        User user = new User(data);
        entityManager.persist(user);
        return user;
    }
}