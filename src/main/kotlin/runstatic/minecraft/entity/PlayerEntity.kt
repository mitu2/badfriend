package runstatic.minecraft.entity

import net.minestom.server.entity.GameMode
import org.springframework.data.jpa.domain.AbstractPersistable
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table

/**
 *
 * @author chenmoand
 */
@Entity
@Table(
    name = "minecraft_player", indexes = [
        Index(name = "uuid_index", columnList = "uuid"),
        Index(name = "username_index", columnList = "username")
    ]
)
data class PlayerEntity(
    @field:Column(name = "uuid", unique = true, length = 128)
    var uuid: String,
    @field:Column(name = "username", unique = true, length = 50)
    var username: String,
    @field:Column(name = "is_genuine")
    var isGenuine: Boolean,
    @field:Column(name = "game_mode")
    var gameMode: GameMode? = GameMode.SURVIVAL,
    var exp: Float = 0f,
    var level: Int = 0,
    var food: Int = 20,
    var flying: Boolean = false,
    @field:Column(name = "last_login_time")
    var lastLoginTime: LocalDateTime? = null,
    @field:Column(name = "last_logout_time")
    var lastLogoutTime: LocalDateTime? = null,
) : AbstractPersistable<Long>()