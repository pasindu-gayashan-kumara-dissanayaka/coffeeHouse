package com.example.coffeehouse.repository

import com.example.coffeehouse.domain.Category
import com.example.coffeehouse.domain.CoffeeItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseCoffeeRepository(
    private val rootRef: DatabaseReference = FirebaseDatabase.getInstance().reference
) : CoffeeRepository {

    override fun observeCategories(): Flow<List<Category>> {
        return rootRef.child("categories").asListFlow(Category::class.java)
    }

    override fun observePopularItems(): Flow<List<CoffeeItem>> {
        return rootRef.child("popularItems").asListFlow(CoffeeItem::class.java)
    }

    override fun observeSpecialOffers(): Flow<List<CoffeeItem>> {
        return rootRef.child("specialOffers").asListFlow(CoffeeItem::class.java)
    }
}

private fun <T : Any> DatabaseReference.asListFlow(type: Class<T>): Flow<List<T>> = callbackFlow {
    val listener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val items = snapshot.children.mapNotNull { child ->
                child.getValue(type)
            }
            trySend(items).isSuccess
        }

        override fun onCancelled(error: DatabaseError) {
            close(error.toException())
        }
    }

    addValueEventListener(listener)
    awaitClose { removeEventListener(listener) }
}
