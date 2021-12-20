package ru.megains.orangem.client.register

import ru.megains.orangem.common.block.{Block, BlockAir, BlockGlass, BlockLeaves, BlockTest}
import ru.megains.orangem.client.OrangeM
import ru.megains.orangem.client.render.block.{RenderBlockGlass, RenderBlockGrass, RenderBlockLeaves, RenderBlockLog}
import ru.megains.orangem.client.render.entity.RenderEntityCube
import ru.megains.orangem.client.utils.Logger
import ru.megains.orangem.common.entity.item.EntityItem
import ru.megains.orangem.common.entity.mob.EntityCube
import ru.megains.orangem.common.item.{ItemMass, ItemPack, ItemSingle}
import ru.megains.orangem.common.register.TGameRegister
import ru.megains.orangem.common.register.{Bootstrap => BootstrapCommon}
import ru.megains.orangem.client.render.block.RenderBlockGrass

object Bootstrap extends Logger[OrangeM] {

    var isNotInit = true

    def init(): Unit = {
        if (isNotInit) {
            isNotInit = false
            log.info("Blocks init...")
            initBlocks()
            log.info("Items init...")
            initItems()
            log.info("TileEntity init...")
            initTileEntity()
            log.info("Entity init...")
            initEntity()
        }

    }

    def initBlocks(): Unit = {
        BootstrapCommon.initBlocks(GameRegisterRender)

        GameRegisterRender.registerBlockRender("grass", new RenderBlockGrass("grass"))
        GameRegisterRender.registerBlockRender("glass", new RenderBlockGlass("glass"))

        GameRegisterRender.registerBlockRender("leaves_oak", new RenderBlockLeaves("leaves_oak"))
        GameRegisterRender.registerBlockRender("leaves_birch", new RenderBlockLeaves("leaves_birch"))

        GameRegisterRender.registerBlockRender("log_birch", new RenderBlockLog("log_birch"))
        GameRegisterRender.registerBlockRender("log_oak", new RenderBlockLog("log_oak"))


    }

    def initItems(): Unit = {
        BootstrapCommon.initItems(GameRegisterRender)
    }

    def initTileEntity(): Unit = {
        BootstrapCommon.initTileEntity(GameRegisterRender)
        // GameRegister.registerTileEntityRender(classOf[TileEntityChest],RenderChest)
    }

    def initEntity(): Unit = {
        BootstrapCommon.initEntity(GameRegisterRender)

        GameRegisterRender.registerEntityRender(classOf[EntityCube], RenderEntityCube)
    }
}
