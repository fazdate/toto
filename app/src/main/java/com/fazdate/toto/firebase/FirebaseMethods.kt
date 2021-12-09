package com.fazdate.toto.firebase

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await

class FirebaseMethods {

    private  val db = FirebaseFirestore.getInstance()

    suspend fun addDocumentToCollection(collectionName: String, hashMap: HashMap<String, Int>) : Boolean {
        val documentName = "game_" + generateGameID(collectionName)
        return return try{
            val data = db.collection(collectionName).document(documentName).set(hashMap).await()
            true
        }catch (e : Exception){
            false
        }
    }

    suspend fun getEveryDocumentFromCollection(collectionName: String) : QuerySnapshot? {
        return try {
            db.collection(collectionName).orderBy("gameNumber", Direction.ASCENDING).get().await()
        }catch (e : Exception) {
            null
        }
    }

    suspend fun generateGameID(collectionName: String) : Int {
        return return try {
            val documents = getEveryDocumentFromCollection(collectionName)?.documents
            var maxID = 0
            if (documents != null) {
                for (doc in documents) {
                    val temp = doc.get("gameNumber").toString().toInt()
                    if (maxID < temp) {
                        maxID = temp
                    }
                }
            }
            (maxID + 1)
        }catch (e : Exception) {
            0
        }
    }

    private suspend fun getTotal(collectionName: String, fieldName: String) : Int {
        return try {
            val documents = getEveryDocumentFromCollection(collectionName)?.documents
            var totalPrize = 0
            if (documents != null) {
                for (doc in documents) {
                    totalPrize += doc.get(fieldName).toString().toInt()
                }
            }
            return totalPrize
        } catch (e : Exception) {
            Log.w(TAG, e)
        }
    }

    suspend fun getTotalPrize(collectionName: String) : Int {
        return getTotal(collectionName, "prizeMoney")
    }

    suspend fun getTotalCorrectGuesses(collectionName: String) : Int {
       return getTotal(collectionName, "numberOfCorrectGuesses")
    }
}
