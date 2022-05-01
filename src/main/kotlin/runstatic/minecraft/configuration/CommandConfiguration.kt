package runstatic.minecraft.configuration

import net.minestom.server.command.CommandManager
import net.minestom.server.command.builder.Command
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 *
 * @author chenmoand
 */
@Configuration
class CommandConfiguration(
    val commandManager: CommandManager,
    val commands: List<Command>
) {

    @PostConstruct
    fun init() {
        commands.forEach {
            commandManager.register(it)
        }

        commandManager.setUnknownCommandCallback { sender, command ->
            sender.sendMessage("Command: /$command does not exist")
        }
    }

    @PreDestroy
    fun destroy() {
        commands.forEach {
            commandManager.unregister(it)
        }
    }


}