package runstatic.minecraft.listener

import com.fasterxml.jackson.databind.ObjectMapper
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.GameMode
import net.minestom.server.event.player.PlayerLoginEvent
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.network.ConnectionManager
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.beans.factory.annotation.Qualifier
import runstatic.minecraft.entity.PlayerEntity
import runstatic.minecraft.game.MinecraftEventListener
import runstatic.minecraft.game.MinecraftListener
import runstatic.minecraft.game.plus
import runstatic.minecraft.repository.PlayerRepository
import runstatic.stools.logging.debug
import runstatic.stools.logging.useSlf4jLogger
import java.time.LocalDateTime
import java.util.*

/**
 *
 * @author chenmoand
 */
@MinecraftListener
class PlayJoinListener(
    @field:Qualifier("mainInstanceContainer")
    private val mainInstanceContainer: InstanceContainer,
    private val connectionManager: ConnectionManager,
    private val playerRepository: PlayerRepository,
    private val okHttpClient: OkHttpClient
) : MinecraftEventListener<PlayerLoginEvent>() {

    private val mapper = ObjectMapper()
    private val log = useSlf4jLogger()

    override fun runUnit(event: PlayerLoginEvent) {
        val player = event.player
        player.gameMode = GameMode.CREATIVE
        var isNew = false
        var playerEntity: PlayerEntity? = playerRepository.findByUsername(player.username)
        if (playerEntity == null) {
            playerEntity = getPlayerEntityByUsername(player.username)
            isNew = true
        }

        event.setSpawningInstance(mainInstanceContainer)
        player.respawnPoint = Pos(.0, 42.0, .0)
        connectionManager.onlinePlayers.forEach {
            it.sendMessage {
                (NamedTextColor.RED + "[+] Player ") + player.name as TextComponent + (NamedTextColor.RED + " Join Game!")
            }
        }

        if (isNew) {
            event.player.sendMessage {
                NamedTextColor.RED + "[BadFriend] Welcome to join for the first time"
            }
        }
    }


    fun getPlayerEntityByUsername(username: String): PlayerEntity {
        var uuid = getUuidByUsername(username)
        var isGenuine = true
        if (uuid == null) {
            uuid = UUID.randomUUID().toString()
            isGenuine = false
        } else {
            log.debug { "player $username 's uuid $uuid" }
        }

        return playerRepository.save(
            PlayerEntity(
                uuid = uuid,
                isGenuine = isGenuine,
                username = username,
                lastLoginTime = LocalDateTime.now()
            )
        )
    }

    fun getUuidByUsername(username: String): String? {
        try {
            okHttpClient.newCall(
                Request.Builder()
                    .get()
                    .url(API_GET_MOJANG_UUID + username)
                    .build()
            ).execute().use {
                if (it.code == 200) {
                    return mapper.readTree(it.body.byteStream()).get("id").toString()
                }
            }
        } catch (e: Exception) {
            log.error(e.message, e)
        }
        return null
    }

    companion object {
        const val API_GET_MOJANG_UUID = "https://api.mojang.com/users/profiles/minecraft/"
    }
}