package com.wooz.countries.data.framework.remote.common

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.annotations.Expose

/**
 * @author wooz
 * @since 10/10/2020
 */
class SerializationExclusionStrategy : ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        val annotation = clazz!!.getAnnotation(Expose::class.java)
        return if (annotation != null) !annotation.serialize else false
    }

    override fun shouldSkipField(f: FieldAttributes?): Boolean {
        val annotation = f!!.getAnnotation(Expose::class.java)
        return if (annotation != null) !annotation.serialize else false
    }
}

class DeserializationExclusionStrategy : ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?): Boolean {
        val annotation = clazz!!.getAnnotation(Expose::class.java)
        return if (annotation != null) !annotation.deserialize else false
    }

    override fun shouldSkipField(f: FieldAttributes?): Boolean {
        val annotation = f!!.getAnnotation(Expose::class.java)
        return if (annotation != null) !annotation.deserialize else false
    }
}