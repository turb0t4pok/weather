package org.example.weather.data.cache

@JsFun("() => Date.now()")
external fun jsDateNow(): Double

actual fun currentTimeMillis(): Long = jsDateNow().toLong()