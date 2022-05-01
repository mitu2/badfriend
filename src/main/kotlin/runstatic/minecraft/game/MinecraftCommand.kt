package runstatic.minecraft.game

import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.CommandContext
import net.minestom.server.command.builder.CommandSyntax
import net.minestom.server.command.builder.arguments.Argument
import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Indexed

/**
 *
 * @author chenmoand
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Indexed
@Component
annotation class MinecraftCommand(
    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)

fun command(name: String, vararg aliases: String, block: (Command) -> Unit): Command =
    Command(name, *aliases).apply(block)


fun Command.addSyntax(vararg args: Argument<*>, block: (CommandSender, CommandContext) -> Unit): MutableCollection<CommandSyntax> = addSyntax(block, *args)

fun Command.addSyntax(format: String, block: (CommandSender, CommandContext) -> Unit) = addSyntax(block, format)