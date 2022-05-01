package runstatic.minecraft.game

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.command.CommandSender

/**
 *
 * @author chenmoand
 */

fun CommandSender.sendMessage(vararg components: TextComponent) = components.forEach {
    this.sendMessage(it)
}

operator fun TextComponent.plus(text: TextComponent) = this.append(text)

operator fun NamedTextColor.plus(text: String) = asTextComponent(text)

fun NamedTextColor.asTextComponent(text: String) = Component.text(text, this)

