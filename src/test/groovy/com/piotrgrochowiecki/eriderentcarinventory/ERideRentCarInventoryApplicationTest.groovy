package com.piotrgrochowiecki.eriderentcarinventory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import spock.lang.Specification

@SpringBootTest
class ERideRentCarInventoryApplicationTest extends Specification {

    @Autowired
    private ApplicationContext applicationContext

    def "expect context"() {
        expect: "the context is loaded"
        applicationContext
    }

}
