package com.github.ekohlwey

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.mapper.annotations.*

@Entity
@PropertyStrategy(mutable = true)
data class FooEntity(@PartitionKey var id: String? = null)

@Dao
interface FooDao {
    @Select
    fun findById(id: String): FooEntity
}

@Mapper
interface FooMapper {
    @DaoFactory
    fun fooDao(): FooDao
}

fun main() {
    val mapper = FooMapperBuilder(CqlSession.builder().build()).build()
    val dao = mapper.fooDao()
    dao.findById("foo")
}
