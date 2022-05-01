package runstatic.minecraft.listener

import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.event.EventListener.Result
import net.minestom.server.event.server.ServerListPingEvent
import net.minestom.server.network.ConnectionManager
import runstatic.minecraft.game.MinecraftEventListener
import runstatic.minecraft.game.MinecraftListener
import runstatic.minecraft.game.plus

/**
 *
 * @author chenmoand
 */
@MinecraftListener
class ServerMotdListener(
    private val connectionManager: ConnectionManager
) : MinecraftEventListener<ServerListPingEvent>() {

//    private val log = useSlf4jLogger()

    override fun run(event: ServerListPingEvent): Result {
        event.responseData.apply {
            description = (
                    (NamedTextColor.RED + "Join <BadFriend>") +
                            (NamedTextColor.GRAY + " | ") +
                            (NamedTextColor.LIGHT_PURPLE + "wish you happy everyday!\n") +
                            (NamedTextColor.BLUE + "Server IP: mc.static.run")
                    )

            addEntries(connectionManager.onlinePlayers)
        }

        return Result.SUCCESS
    }


}