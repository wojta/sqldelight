@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")
package node.events

import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.dom.events.*
import org.w3c.dom.parsing.*
import org.w3c.dom.svg.*
import org.w3c.dom.url.*
import org.w3c.fetch.*
import org.w3c.files.*
import org.w3c.notifications.*
import org.w3c.performance.*
import org.w3c.workers.*
import org.w3c.xhr.*
import kotlin.js.*

external interface EventEmitterOptions {
    var captureRejections: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface NodeEventTarget {
    fun once(eventName: String, listener: (args: Any) -> Unit): NodeEventTarget /* this */
    fun once(eventName: Any, listener: (args: Any) -> Unit): NodeEventTarget /* this */
}

external interface `T$15` {
    var once: Boolean
}

external interface DOMEventTarget {
    fun addEventListener(eventName: String, listener: (args: Any) -> Unit, opts: `T$15` = definedExternally): Any
}

@JsModule("node:events")
external open class EventEmitter(options: EventEmitterOptions = definedExternally)  {
    open fun addListener(eventName: String, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun addListener(eventName: Any, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun on(eventName: String, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun on(eventName: Any, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun once(eventName: String, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun once(eventName: Any, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun removeListener(eventName: String, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun removeListener(eventName: Any, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun off(eventName: String, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun off(eventName: Any, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun removeAllListeners(event: String): dynamic /* EventEmitter */
    open fun removeAllListeners(): dynamic /* EventEmitter */
    open fun removeAllListeners(event: Any): dynamic /* EventEmitter */
    open fun setMaxListeners(n: Number): EventEmitter /* this */
    open fun getMaxListeners(): Number
    open fun listeners(eventName: String): Array<Function<*>>
    open fun listeners(eventName: Any): Array<Function<*>>
    open fun rawListeners(eventName: String): Array<Function<*>>
    open fun rawListeners(eventName: Any): Array<Function<*>>
    open fun emit(eventName: String, vararg args: Any): Boolean
    open fun emit(eventName: Any, vararg args: Any): Boolean
    open fun listenerCount(eventName: String): Number
    open fun listenerCount(eventName: Any): Number
    open fun prependListener(eventName: String, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun prependListener(eventName: Any, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun prependOnceListener(eventName: String, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun prependOnceListener(eventName: Any, listener: (args: Any) -> Unit): dynamic /* EventEmitter */
    open fun eventNames(): Array<dynamic /* String | Any */>
}
