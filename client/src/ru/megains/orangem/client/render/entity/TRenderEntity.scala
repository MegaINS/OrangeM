package ru.megains.orangem.client.render.entity

import ru.megains.mge.render.shader.Shader
import ru.megains.orangem.common.entity.Entity
import ru.megains.orangem.common.world.World

trait TRenderEntity {

    def init():Unit

    def render(entity: Entity, world: World,shader: Shader): Boolean
}
