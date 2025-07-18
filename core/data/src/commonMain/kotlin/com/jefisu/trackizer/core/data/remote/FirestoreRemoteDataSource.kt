@file:OptIn(ExperimentalCoroutinesApi::class, InternalSerializationApi::class)

package com.jefisu.trackizer.core.data.remote

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.firestore
import kotlin.reflect.KClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.serialization.InternalSerializationApi

class FirestoreRemoteDataSource<T : RemoteData>(
    collectionPath: String,
    private val clazz: KClass<T>,
) : RemoteDataSource<T> {

    private val firestore = Firebase.firestore
    private val auth = Firebase.auth

    private val userFlow = auth.authStateChanged
    private val collectionRef = firestore.collection(collectionPath)

    override val data = userFlow
        .filterNotNull()
        .flatMapMerge { firebaseUser ->
            collectionRef
                .where {
                    val userIsOwnerFilter = "userId" equalTo firebaseUser.uid
                    val noHasSpecificOwnerFilter = "userId" equalTo null
                    userIsOwnerFilter or noHasSpecificOwnerFilter
                }
                .snapshots
                .map { snapshot ->
                    snapshot.documentChanges.map { it.toRemoteDataChange(clazz) }
                }
        }
}
