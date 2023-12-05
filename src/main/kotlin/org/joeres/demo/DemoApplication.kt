package org.joeres.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.annotation.Id
import org.springframework.data.convert.ReadingConverter
import org.springframework.data.convert.WritingConverter
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}

@Configuration
class JdbcConfig : AbstractJdbcConfiguration() {
    override fun userConverters(): List<*> = listOf(
        MyEnum.MyEnumReadingConverter(),
        MyEnum.MyEnumWritingConverter()
    )
}

enum class MyEnum {
    FIRST,
    SECOND;

    @ReadingConverter
    class MyEnumReadingConverter : Converter<String, MyEnum> {
        override fun convert(source: String): MyEnum {
            return MyEnum.valueOf(source.uppercase())
        }
    }

    @WritingConverter
    class MyEnumWritingConverter : Converter<MyEnum, String> {
        override fun convert(source: MyEnum): String {
            return source.toString().lowercase()
        }
    }
}

data class TestEntity(
    @Id
    val id: Int,
    val theEnum: MyEnum
)

@Repository
interface Repo : CrudRepository<TestEntity, Int>

