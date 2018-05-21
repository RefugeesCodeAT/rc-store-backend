package at.refugeescode.rcstore.configuration;

import at.refugeescode.rcstore.models.Item;
import at.refugeescode.rcstore.persistence.ItemRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import java.util.List;

@Configuration
public class Components {

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }

    @Bean
    ApplicationRunner applicationRunner(ItemRepository itemRepository) {
        return args -> {
            List<Item> itemList = itemRepository.findAll();
            if (itemList.size() > 0) {
                return;
            }
            Item item = Item.builder()
                    .name("name")
                    .description("description")
                    .borrowed(false)
                    .borrowingLimit(15)
                    .build();
            itemRepository.save(item);
        };
    }

}
