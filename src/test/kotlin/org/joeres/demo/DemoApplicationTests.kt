package org.joeres.demo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jdbc.core.JdbcAggregateTemplate

@SpringBootTest
class DemoApplicationTests(
    @Autowired private val jdbcAggregateTemplate: JdbcAggregateTemplate,
    @Autowired private val repo: Repo
) {

    @Test
    fun `should read lower case enum`() {
        val writeEntity = TestEntity(id = 1, theEnum = MyEnum.FIRST)
        jdbcAggregateTemplate.insert(writeEntity)
        val readEntity = repo.findById(1).get()
        assertEquals(writeEntity, readEntity)
    }

}
