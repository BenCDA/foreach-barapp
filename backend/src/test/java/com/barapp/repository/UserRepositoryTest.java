package com.barapp.repository;

import com.barapp.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void shouldFindByEmail() {
        User user = new User();
        user.setName("Ben");
        user.setEmail("ben@test.com");
        user.setPassword("pwd");
        repository.save(user);

        Optional<User> found = repository.findByEmail("ben@test.com");
        assertThat(found).isPresent();
    }
}
