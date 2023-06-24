package com.kampus.merdeka.firebaserealtimedb

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseDatabaseHelper {
    private val databaseRef = FirebaseDatabase.getInstance().reference

    fun createTire(child: String, tireModel: TireModel, callback: (Boolean) -> Unit) {
        val childRef = databaseRef.child(child).push()
        val tireId = childRef.key
        val newTire = TireModel(tireId, tireModel.name, tireModel.description)
        childRef.setValue(newTire)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }

    fun readTires(child: String, callback: (List<TireModel>) -> Unit) {
        val usersRef = databaseRef.child(child)
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tireList = mutableListOf<TireModel>()
                if (snapshot.exists()) {
                    for (childSnapshot in snapshot.children) {
                        val tire = childSnapshot.getValue(TireModel::class.java)
                        tire?.let { tireList.add(it) }
                    }
                }
                callback(tireList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })
    }

    fun updateTire(child: String, tireModel: TireModel, callback: (Boolean) -> Unit) {
        val userRef = databaseRef.child(child).child(tireModel.id!!)
        userRef.setValue(tireModel)
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun deleteTire(child: String, tireId: String, callback: (Boolean) -> Unit) {
        val userRef = databaseRef.child(child).child(tireId)
        userRef.removeValue()
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }

    fun deleteAll(child: String, callback: (Boolean) -> Unit) {
        val userRef = databaseRef.child(child)
        userRef.removeValue()
            .addOnCompleteListener { task ->
                callback(task.isSuccessful)
            }
    }
}