package runstatic.minecraft.configuration

import net.minestom.server.event.GlobalEventHandler
import org.springframework.context.annotation.Configuration
import runstatic.minecraft.game.MinecraftEventListener
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 *
 * @author chenmoand
 */
@Configuration
class ListenerConfiguration(
    private val globalEventHandler: GlobalEventHandler,
    private val listeners: List<MinecraftEventListener<*>>
) {

    @PostConstruct
    fun init() {
        listeners.forEach {
            globalEventHandler.addListener(it)
        }
    }

    @PreDestroy
    fun destroy() {
        listeners.forEach {
            globalEventHandler.removeListener(it)
        }
    }

}