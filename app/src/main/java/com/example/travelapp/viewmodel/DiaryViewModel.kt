package com.example.travelapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.travelapp.User1.Use
import com.example.travelapp.entity.DiaryEntry
import com.example.travelapp.repository.DiaryRepository
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

class DiaryViewModel(application: Application) : AndroidViewModel(application) {
    private val firedb = FirebaseFirestore.getInstance()
    private val dRepository = DiaryRepository(application)
    var allDiary: LiveData<List<DiaryEntry>> = dRepository.allDiary

    fun insert(diaryEntry: DiaryEntry) {
        val id = dRepository.insert(diaryEntry)
        diaryEntry.id = id
        firedb.collection(Use.getUserEmail() + "DiaryDatabase").document(diaryEntry.id.toString()).set(diaryEntry)
            .addOnSuccessListener { dRepository.setUpdate(diaryEntry.id!!, true) }
    }

    val allId: LiveData<List<Int>>
        get() = dRepository.allId

    fun findDiarybyId(diaryId: Int): DiaryEntry? {
        return dRepository.findDiarybyId(diaryId)
    }

    val rating: List<Int>
        get() = dRepository.rating

    val fee: List<Int>
        get() = dRepository.fee

    val location: List<String>
        get() = dRepository.location

    val date: List<String>
        get() = dRepository.date

    fun getUpdated(diaryId: Int): Boolean {
        return dRepository.getUpdated(diaryId)
    }

    fun SynchronizeGetData() {
        val localId = allId.value
        val localDocId: MutableList<String> = ArrayList()
        val diaryRef = firedb.collection(Use.getUserEmail() + "DiaryDatabase")
        if (localId != null) {
            for (id in localId) {
                localDocId.add(id.toString())
            }

            diaryRef.whereNotIn(FieldPath.documentId(), localDocId)
                .get().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (doc in task.result!!) {
                            val diary = doc.toObject(DiaryEntry::class.java)
                            diary.updated = true
                            diary.id = doc.id.toInt()
                            dRepository.insert(diary)
                        }
                    }
                }
        } else {
            diaryRef.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (doc in task.result!!) {
                        val diary = doc.toObject(DiaryEntry::class.java)
                        diary.updated = true
                        diary.id = doc.id.toInt()
                        dRepository.insert(diary)
                    }
                }
            }
        }
    }
}
