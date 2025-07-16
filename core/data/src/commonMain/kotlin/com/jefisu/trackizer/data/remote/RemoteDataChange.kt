@file:OptIn(InternalSerializationApi::class)

package com.jefisu.trackizer.data.remote

import com.jefisu.trackizer.data.remote.RemoteDataChange.Added
import com.jefisu.trackizer.data.remote.RemoteDataChange.Modified
import com.jefisu.trackizer.data.remote.RemoteDataChange.Removed
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
    if (this is Added) action(document)
    return this
}

fun <T : RemoteData> RemoteDataChange<T>.onModified(
    action: (RemoteData) -> Unit,
): RemoteDataChange<T> {
    if (this is Modified) action(document)
    return this
}

fun <T : RemoteData> RemoteDataChange<T>.onRemoved(
    action: (RemoteData) -> Unit,
): RemoteDataChange<T> {
    if (this is Removed) action(document)
    return this
}

fun <T : RemoteData> DocumentChange.toRemoteDataChange(clazz: KClass<T>): RemoteDataChange<T> {
    val document = this.document.data(clazz.serializer())
    return when (this.type) {
        ChangeType.ADDED -> Added(document)
        ChangeType.MODIFIED -> Modified(document)
        ChangeType.REMOVED -> Removed(document)
        else -> error("Unknown ChangeType: ${this.type}")
    }
}
