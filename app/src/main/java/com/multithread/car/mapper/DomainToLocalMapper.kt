package com.multithread.car.mapper

import com.multithread.car.base.TwoWayMapper
import com.multithread.car.data.local.data.CarItemLocalEntity
import com.multithread.car.data.local.data.LocalCarContent
import com.multithread.car.domain.entity.CarContentEntity
import com.multithread.car.domain.entity.CarEntity
import javax.inject.Inject

class DomainToLocalMapper @Inject constructor() :
    TwoWayMapper<List<CarEntity>, List<CarItemLocalEntity>> {
    override fun mapLeftToRight(left: List<CarEntity>): List<CarItemLocalEntity> =
        mutableListOf<CarItemLocalEntity>().apply {
            left.forEach {
                add(
                    CarItemLocalEntity(
                        changed = it.changed,
                        content = createContent(it.content),
                        created = it.created,
                        dateTime = it.dateTime,
                        id = it.id,
                        image = it.image,
                        ingress = it.ingress,
                        title = it.title
                    )
                )
            }
        }

    private fun createContent(content: List<CarContentEntity>): List<LocalCarContent> =
        mutableListOf<LocalCarContent>().apply {
            content.forEach {
                add(
                    LocalCarContent(
                        description = it.description,
                        subject = it.subject,
                        type = it.type
                    )
                )
            }
        }

    override fun mapRightToLeft(right: List<CarItemLocalEntity>): List<CarEntity> =
        mutableListOf<CarEntity>().apply {
            right.forEach {
                add(
                    CarEntity(
                        changed = it.changed,
                        content = createEntityContent(it.content),
                        created = it.created,
                        dateTime = it.dateTime,
                        id = it.id,
                        image = it.image,
                        ingress = it.ingress,
                        title = it.title
                    )
                )
            }
        }

    private fun createEntityContent(content: List<LocalCarContent>): List<CarContentEntity> =
        mutableListOf<CarContentEntity>().apply {
            content.forEach {
                add(
                    CarContentEntity(
                        description = it.description,
                        subject = it.subject,
                        type = it.type
                    )
                )
            }
        }

}