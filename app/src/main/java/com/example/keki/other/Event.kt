package com.example.keki.other

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
    private set // Allow external read but not write
    /**
     * Returns the content and prevents its use again.
     */
    fun getContentifNothandled():T?{
        return if (hasBeenHandled) {
            null
        }  else{
            hasBeenHandled = true
        content
    }}
    fun peekContent(): T = content

}