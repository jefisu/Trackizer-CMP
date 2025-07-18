@file:OptIn(InternalSerializationApi::class)

package com.jefisu.trackizer.core.data.remote

import dev.gitlive.firebase.firestore.ChangeType
import dev.gitlive.firebase.firestore.DocumentChange
import kotlin.reflect.KClass
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.serializer

sealed class RemoteDataChange<T : RemoteData>(val document: T) {
    class Added<T : RemoteData>(doc: T) : RemoteDataChange<T>(doc)
    class Modified<T : RemoteData>(doc: T) : RemoteDataChange<T>(doc)
    class Removed<T : RemoteData>(doc: T) : RemoteDataChange<T>(doc)
}

fun <T : RemoteData> RemoteDataChange<T>.onAdded(action: (T) -> Unit): RemoteDataChange<T> {
    if (this is RemoteDataChange.Added) action(document)
    return this
}

fun <T : RemoteData> RemoteDataChange<T>.onModified(
    action: (RemoteData) -> Unit,
): RemoteDataChange<T> {
    if (this is RemoteDataChange.Modified) action(document)
    return this
}

fun <T : RemoteData> RemoteDataChange<T>.onRemoved(
    action: (RemoteData) -> Unit,
): RemoteDataChange<T> {
    if (this is RemoteDataChange.Removed) action(document)
    return this
}

@Suppress("REDUNDANT_ELSE_IN_WHEN")
fun <T : RemoteData> DocumentChange.toRemoteDataChange(clazz: KClass<T>): RemoteDataChange<T> {
    val document = this.document.data(clazz.serializer())
    return when (this.type) {
        ChangeType.ADDED -> RemoteDataChange.Added(document)
        ChangeType.MODIFIED -> RemoteDataChange.Modified(document)
        ChangeType.REMOVED -> RemoteDataChange.Removed(document)
        else -> error("Unknown ChangeType: ${this.type}")
    }
}
