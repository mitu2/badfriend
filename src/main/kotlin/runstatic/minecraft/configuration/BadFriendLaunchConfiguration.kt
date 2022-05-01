package runstatic.minecraft.configuration

import net.minestom.server.MinecraftServer
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.instance.InstanceManager
import net.minestom.server.instance.block.Block
import net.minestom.server.terminal.MinestomTerminal
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import javax.annotation.PreDestroy

/**
 *
 * @author chenmoand
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
class BadFriendLaunchConfiguration(
    instanceManager: InstanceManager,
) {

    private val mainInstanceContainer = instanceManager.createInstanceContainer()

    @Bean
    @Primary
    fun mainInstanceContainer(): InstanceContainer {
        mainInstanceContainer.setGenerator { unit ->
            unit.modifier().fillHeight(0, 40, Block.GRASS_BLOCK)
        }
        return mainInstanceContainer
    }


    @PreDestroy
    fun destroy() {
        MinestomTerminal.stop()
        MinecraftServer.stopCleanly()
    }

}