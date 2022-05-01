package runstatic.minecraft

import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandManager
import net.minestom.server.event.GlobalEventHandler
import net.minestom.server.instance.InstanceManager
import net.minestom.server.network.ConnectionManager
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import runstatic.minecraft.repository.RepositoryScanPosition
import javax.annotation.PostConstruct


/**
 *
 * @author chenmoand
 */
@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = [RepositoryScanPosition::class])
class BadFriendApplication {

    private val minecraftServer: MinecraftServer = MinecraftServer.init()

    @Bean
    fun minecraftServer(): MinecraftServer = minecraftServer

    @Bean
    fun instanceManager(): InstanceManager = MinecraftServer.getInstanceManager()

    @Bean
    fun globalEventHandler(): GlobalEventHandler = MinecraftServer.getGlobalEventHandler()

    @Bean
    fun commandManager(): CommandManager = MinecraftServer.getCommandManager()

    @Bean
    fun connectionManager(): ConnectionManager = MinecraftServer.getConnectionManager()

    @PostConstruct
    fun init() {
        minecraftServer.start("0.0.0.0", 25565)
    }

}


fun main(args: Array<String>) {
    runApplication<BadFriendApplication>(*args)
}