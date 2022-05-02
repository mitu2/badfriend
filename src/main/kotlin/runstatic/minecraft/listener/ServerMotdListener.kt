package runstatic.minecraft.listener

import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.event.EventListener.Result
import net.minestom.server.event.server.ServerListPingEvent
import net.minestom.server.network.ConnectionManager
import org.springframework.util.ResourceUtils
import runstatic.minecraft.game.MinecraftEventListener
import runstatic.minecraft.game.MinecraftListener
import runstatic.minecraft.game.plus
import runstatic.stools.logging.useSlf4jLogger
import java.io.FileNotFoundException
import java.util.*

/**
 *
 * @author chenmoand
 */
@MinecraftListener
class ServerMotdListener(
    private val connectionManager: ConnectionManager
) : MinecraftEventListener<ServerListPingEvent>() {

    private val log = useSlf4jLogger()

    private val faviconBase64: String? by lazy {
        try {
            return@lazy "data:image/png;base64," + ResourceUtils.getFile("classpath:static/favicon.png").inputStream()
                .use {
                    Base64.getEncoder().encodeToString(it.readAllBytes())
                }
        } catch (e: FileNotFoundException) {
            log.debug(e.message, e)
            return@lazy null
        } catch (e: Exception) {
            log.error(e.message, e)
            return@lazy null
        }
    }

    override fun run(event: ServerListPingEvent): Result {
        event.responseData.apply {
            description = (
                    (NamedTextColor.RED + "Join <BadFriend>") +
                            (NamedTextColor.GRAY + " | ") +
                            (NamedTextColor.LIGHT_PURPLE + "wish you happy everyday!\n") +
                            (NamedTextColor.BLUE + "Server IP: mc.static.run")
                    )
            favicon = faviconBase64
            addEntries(connectionManager.onlinePlayers)
        }

        return Result.SUCCESS
    }


}