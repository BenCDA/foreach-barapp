package com.barapp.repository;

import com.barapp.model.Cocktail;
import com.barapp.model.CocktailSizePrice;
import com.barapp.model.Size;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CocktailSizePriceRepositoryTest {

    @Autowired
    private CocktailSizePriceRepository repository;
    @Autowired
    private CocktailRepository cocktailRepository;
    @Autowired
    private SizeRepository sizeRepository;

    @Test
    void shouldFindByCocktailId() {
        Cocktail c = new Cocktail();
        c.setName("Mojito");
        cocktailRepository.save(c);

        Size s = new Size();
        s.setLabel("M");
        sizeRepository.save(s);

        CocktailSizePrice csp = new CocktailSizePrice();
        csp.setCocktail(c);
        csp.setSize(s);
        csp.setPrice(5);
        repository.save(csp);

        List<CocktailSizePrice> list = repository.findByCocktailId(c.getId());
        assertThat(list).hasSize(1);
        assertThat(list.get(0).getPrice()).isEqualTo(5);
    }
}
