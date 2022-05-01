package runstatic.minecraft.game

import net.minestom.server.event.Event
import net.minestom.server.event.EventListener
import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Indexed
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 *
 * @author chenmoand
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Indexed
@Component
annotation class MinecraftListener(
    @get:AliasFor(annotation = Component::class)
    val value: String = ""
)

@Suppress("UNCHECKED_CAST")
abstract class MinecraftEventListener<T : Event> : EventListener<T> {

    private val innerEventHandler: EventListener<T> by lazy {
        EventListener.builder(eventType()).handler {
            runUnit(it)
        }.build()
    }

    private val type: Type = let {
        val superClass = javaClass.genericSuperclass
        require(superClass !is Class<*>) {  // sanity check, should never happen
            "Internal error: MinecraftEventListener constructed without actual type information"
        }
        return@let (superClass as ParameterizedType).actualTypeArguments[0]
    }

    override fun eventType(): Class<T> = Class.forName(type.typeName) as Class<T>

    override fun run(event: T): EventListener.Result = innerEventHandler.run(event)


    protected open fun runUnit(event: T): Unit {
        throw NotImplementedError()
    }

}