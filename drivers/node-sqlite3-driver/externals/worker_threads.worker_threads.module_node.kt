@file:JsModule("worker_threads")
@file:JsNonModule
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")
package worker_threads

import kotlin.js.*
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
import perf_hooks.EventLoopUtilityFunction
import events.EventEmitter
import url.URL
import stream.internal.Writable
import stream.internal.Readable
import NodeJS.RefCounted
import vm.Context

external var isMainThread: Boolean

external var parentPort: MessagePort?

external var resourceLimits: ResourceLimits

external var SHARE_ENV: Any

external var threadId: Number

external var workerData: Any

external open class MessageChannel {
    open var port1: MessagePort
    open var port2: MessagePort
}

external interface WorkerPerformance {
    var eventLoopUtilization: EventLoopUtilityFunction
}

external open class MessagePort : EventEmitter {
    open fun close()
    open fun postMessage(value: Any, transferList: Array<Any /* ArrayBuffer | MessagePort | FileHandle | X509Certificate | Blob */> = definedExternally)
    open fun ref()
    open fun unref()
    open fun start()
    open fun addListener(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun addListener(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    open fun addListener(event: String /* "messageerror" */, listener: (error: Error) -> Unit): MessagePort /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun emit(event: String /* "close" */): Boolean
    open fun emit(event: String /* "message" */, value: Any): Boolean
    override fun emit(eventName: String, vararg args: Any): Boolean
    override fun emit(eventName: Any, vararg args: Any): Boolean
    override fun emit(eventName: String, vararg args: Any): Boolean
    override fun emit(eventName: Any, vararg args: Any): Boolean
    open fun emit(event: String /* "messageerror" */, error: Error): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    open fun on(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun on(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    open fun on(event: String /* "messageerror" */, listener: (error: Error) -> Unit): MessagePort /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun once(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun once(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    open fun once(event: String /* "messageerror" */, listener: (error: Error) -> Unit): MessagePort /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun prependListener(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun prependListener(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    open fun prependListener(event: String /* "messageerror" */, listener: (error: Error) -> Unit): MessagePort /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun prependOnceListener(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun prependOnceListener(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    open fun prependOnceListener(event: String /* "messageerror" */, listener: (error: Error) -> Unit): MessagePort /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun removeListener(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun removeListener(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    open fun removeListener(event: String /* "messageerror" */, listener: (error: Error) -> Unit): MessagePort /* this */
    override fun removeListener(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
    open fun off(event: String /* "close" */, listener: () -> Unit): MessagePort /* this */
    override fun off(event: String /* "message" */, listener: (value: Any) -> Unit): MessagePort /* this */
    open fun off(event: String /* "messageerror" */, listener: (error: Error) -> Unit): MessagePort /* this */
    override fun off(event: Any, listener: (args: Any) -> Unit): MessagePort /* this */
}

external interface WorkerOptions {
    var argv: Array<Any>?
        get() = definedExternally
        set(value) = definedExternally
    var env: dynamic /* NodeJS.Dict<String>? | Any? */
        get() = definedExternally
        set(value) = definedExternally
    var eval: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var workerData: Any?
        get() = definedExternally
        set(value) = definedExternally
    var stdin: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var stdout: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var stderr: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var execArgv: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var resourceLimits: ResourceLimits?
        get() = definedExternally
        set(value) = definedExternally
    var transferList: Array<dynamic /* ArrayBuffer | MessagePort | FileHandle | X509Certificate | Blob */>?
        get() = definedExternally
        set(value) = definedExternally
    var trackUnmanagedFds: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ResourceLimits {
    var maxYoungGenerationSizeMb: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maxOldGenerationSizeMb: Number?
        get() = definedExternally
        set(value) = definedExternally
    var codeRangeSizeMb: Number?
        get() = definedExternally
        set(value) = definedExternally
    var stackSizeMb: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external open class Worker : EventEmitter {
    constructor(filename: String, options: WorkerOptions = definedExternally)
    constructor(filename: String)
    constructor(filename: URL, options: WorkerOptions = definedExternally)
    constructor(filename: URL)
    open var stdin: Writable?
    open var stdout: Readable
    open var stderr: Readable
    open var threadId: Number
    open var resourceLimits: ResourceLimits?
    open var performance: WorkerPerformance
    open fun postMessage(value: Any, transferList: Array<Any /* ArrayBuffer | MessagePort | FileHandle | X509Certificate | Blob */> = definedExternally)
    open fun ref()
    open fun unref()
    open fun terminate(): Promise<Number>
    open fun getHeapSnapshot(): Promise<Readable>
    open fun addListener(event: String /* "error" | "messageerror" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun addListener(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun addListener(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun addListener(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun addListener(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun emit(event: String /* "error" | "messageerror" */, err: Error): Boolean
    override fun emit(eventName: String, vararg args: Any): Boolean
    override fun emit(eventName: Any, vararg args: Any): Boolean
    override fun emit(eventName: String, vararg args: Any): Boolean
    override fun emit(eventName: Any, vararg args: Any): Boolean
    open fun emit(event: String /* "exit" */, exitCode: Number): Boolean
    open fun emit(event: String /* "message" */, value: Any): Boolean
    open fun emit(event: String /* "online" */): Boolean
    override fun emit(event: Any, vararg args: Any): Boolean
    open fun on(event: String /* "error" | "messageerror" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun on(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun on(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun on(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun on(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun once(event: String /* "error" | "messageerror" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun once(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun once(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun once(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun once(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun prependListener(event: String /* "error" | "messageerror" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun prependListener(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun prependListener(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun prependListener(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun prependListener(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun prependOnceListener(event: String /* "error" | "messageerror" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun prependOnceListener(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun prependOnceListener(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun prependOnceListener(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun prependOnceListener(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun removeListener(event: String /* "error" | "messageerror" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun removeListener(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun removeListener(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun removeListener(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun removeListener(event: Any, listener: (args: Any) -> Unit): Worker /* this */
    open fun off(event: String /* "error" | "messageerror" */, listener: (err: Error) -> Unit): Worker /* this */
    open fun off(event: String /* "exit" */, listener: (exitCode: Number) -> Unit): Worker /* this */
    override fun off(event: String /* "message" */, listener: (value: Any) -> Unit): Worker /* this */
    open fun off(event: String /* "online" */, listener: () -> Unit): Worker /* this */
    override fun off(event: Any, listener: (args: Any) -> Unit): Worker /* this */
}

external open class BroadcastChannel(name: String) : RefCounted {
    override fun ref(): BroadcastChannel /* this */
    override fun unref(): BroadcastChannel /* this */
    open var name: String
    open var onmessage: (message: Any) -> Unit
    open var onmessageerror: (message: Any) -> Unit
    open fun close()
    open fun postMessage(message: Any)
}

external fun markAsUntransferable(obj: Any?)

external fun moveMessagePortToContext(port: MessagePort, contextifiedSandbox: Context): MessagePort

external interface `T$22` {
    var message: Any
}

external fun receiveMessageOnPort(port: MessagePort): `T$22`?

external fun getEnvironmentData(key: String?): dynamic /* String? | Any? | Number? | Boolean? | Any? */

external fun getEnvironmentData(key: Any?): dynamic /* String? | Any? | Number? | Boolean? | Any? */

external fun getEnvironmentData(key: Number?): dynamic /* String? | Any? | Number? | Boolean? | Any? */

external fun getEnvironmentData(key: Boolean?): dynamic /* String? | Any? | Number? | Boolean? | Any? */

external fun setEnvironmentData(key: String?, value: Any? /* String? | Any? | Number? | Boolean? | Any? */)

external fun setEnvironmentData(key: Any?, value: Any? /* String? | Any? | Number? | Boolean? | Any? */)

external fun setEnvironmentData(key: Number?, value: Any? /* String? | Any? | Number? | Boolean? | Any? */)

external fun setEnvironmentData(key: Boolean?, value: Any? /* String? | Any? | Number? | Boolean? | Any? */)