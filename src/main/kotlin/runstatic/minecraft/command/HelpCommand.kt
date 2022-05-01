package runstatic.minecraft.command

import net.kyori.adventure.text.format.NamedTextColor.RED
import net.minestom.server.command.builder.Command
import org.springframework.boot.info.BuildProperties
import runstatic.minecraft.game.MinecraftCommand
import runstatic.minecraft.game.addSyntax
import runstatic.minecraft.game.asTextComponent

/**
 *
 * @author chenmoand
 */
@MinecraftCommand
class HelpCommand(
    private val buildProperties: BuildProperties
) : Command("help", "h") {

    init {

        addSyntax { sender, _ ->
            sender.sendMessage(
                RED.asTextComponent("Server ${buildProperties.name} version ${buildProperties.version}"),
            )
        }
    }

}