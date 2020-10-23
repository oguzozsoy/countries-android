package com.wooz.countries.domain.common

/**
 * @author wooz
 * @since 09/10/2020
 */
abstract class Mapper<Entity, Dto> {
    abstract fun mapToDto(from: Entity): Dto
    abstract fun mapToEntity(from: Dto): Entity
    fun mapToDtoList(from: List<Entity>): List<Dto> = from.map { mapToDto(it) }
    fun mapToEntityList(from: List<Dto>): List<Entity> = from.map { mapToEntity(it) }
}