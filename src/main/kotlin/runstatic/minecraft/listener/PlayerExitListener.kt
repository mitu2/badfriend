package runstatic.minecraft.listener

import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.network.ConnectionManager
import org.springframework.util.Assert
import runstatic.minecraft.game.MinecraftEventListener
import runstatic.minecraft.game.MinecraftListener
import runstatic.minecraft.game.plus
import runstatic.minecraft.repository.PlayerRepository
import runstatic.stools.logging.useSlf4jLogger
import java.time.LocalDateTime

/**
 *
 * @author chenmoand
 */
@MinecraftListener
class PlayerExitListener(
    private val connectionManager: ConnectionManager,
    private val playerRepository: PlayerRepository,
) : MinecraftEventListener<PlayerDisconnectEvent>() {

    private val log = useSlf4jLogger()

    override fun runUnit(event: PlayerDisconnectEvent) {
        val player = event.player
        val playerEntity = playerRepository.findByUsername(player.username)!!
        playerEntity.lastLogoutTime = LocalDateTime.now()
        playerRepository.save(playerEntity)
        connectionManager.onlinePlayers.forEach {
            it.sendMessage {
                (NamedTextColor.RED + "[-] Player ") + player.name as TextComponent + (NamedTextColor.RED + " Exit Game!")
            }
        }
    }
}