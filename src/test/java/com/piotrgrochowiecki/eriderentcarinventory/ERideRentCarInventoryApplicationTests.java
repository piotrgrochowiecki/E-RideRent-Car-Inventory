package com.piotrgrochowiecki.eriderentcarinventory;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ERideRentCarInventoryApplicationTests {

    @Test
    void contextLoads() {
    }

}
