package com.barapp.repository;

import com.barapp.model.Size;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SizeRepositoryTest {

    @Autowired
    private SizeRepository repository;

    @Test
    void saveAndFind() {
        Size s = new Size();
        s.setLabel("M");
        Size saved = repository.save(s);

        Optional<Size> found = repository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getLabel()).isEqualTo("M");
    }
}
