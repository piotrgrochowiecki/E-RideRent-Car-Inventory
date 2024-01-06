package com.piotrgrochowiecki.eriderentcarinventory.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import spock.lang.Specification
import spock.lang.Unroll

@DataJpaTest
class CarJpaRepositorySpec extends Specification {

    @Autowired
    private TestEntityManager testEntityManager

    @Autowired
    private CarJpaRepository carJpaRepository

    @Unroll
    def "whenFindAll_thenReturnListOfTwoCarEntities"() {
        given:
        CarEntity carEntity1 = new CarEntity(uuid: "carUuid1",
                brand: "Tesla",
                model: "Model X",
                plateNumber: "WB 21533")

        CarEntity carEntity2 = new CarEntity(uuid: "carUuid2",
                brand: "VW",
                model: "ID.4",
                plateNumber: "WK 32353")

        def expectedListOfCarEntites = [carEntity1, carEntity2] as List

        testEntityManager.persist(carEntity1)
        testEntityManager.persist(carEntity2)
        testEntityManager.flush()

        when:
        def resultList = carJpaRepository.findAll()

        then:
        resultList == expectedListOfCarEntites
    }
}
