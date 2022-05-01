package runstatic.minecraft

import net.minestom.server.entity.PlayerSkin
import net.minestom.server.utils.mojang.MojangUtils
import org.junit.jupiter.api.Test

/**
 *
 * @author chenmoand
 */
class UuidTest {

    @Test
    fun testGetUuid() {
        println(MojangUtils.fromUsername("mitu2"))
        println(PlayerSkin.fromUsername("mitu2"))
    }

}