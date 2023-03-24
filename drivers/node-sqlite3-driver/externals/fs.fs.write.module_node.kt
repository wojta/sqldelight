@file:JsQualifier("fs.write")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")
package fs.write

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
import fs.promises.`T$43`
import fs.promises.`T$44`

external fun <TBuffer> __promisify__(fd: Number, buffer: TBuffer = definedExternally, offset: Number = definedExternally, length: Number = definedExternally, position: Number? = definedExternally): Promise<`T$43`<TBuffer>>

external fun <TBuffer> __promisify__(fd: Number): Promise<`T$43`<TBuffer>>

external fun <TBuffer> __promisify__(fd: Number, buffer: TBuffer = definedExternally): Promise<`T$43`<TBuffer>>

external fun <TBuffer> __promisify__(fd: Number, buffer: TBuffer = definedExternally, offset: Number = definedExternally): Promise<`T$43`<TBuffer>>

external fun <TBuffer> __promisify__(fd: Number, buffer: TBuffer = definedExternally, offset: Number = definedExternally, length: Number = definedExternally): Promise<`T$43`<TBuffer>>

external fun __promisify__(fd: Number, string: String, position: Number? = definedExternally, encoding: String /* "ascii" | "utf8" | "utf-8" | "utf16le" | "ucs2" | "ucs-2" | "base64" | "base64url" | "latin1" | "binary" | "hex" */ = definedExternally): Promise<`T$44`>

external fun __promisify__(fd: Number, string: String): Promise<`T$44`>

external fun __promisify__(fd: Number, string: String, position: Number? = definedExternally): Promise<`T$44`>