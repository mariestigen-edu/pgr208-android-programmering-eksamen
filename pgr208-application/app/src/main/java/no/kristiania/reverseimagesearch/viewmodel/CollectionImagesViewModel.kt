package no.kristiania.reverseimagesearch.viewmodel

import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import no.kristiania.reverseimagesearch.model.db.ResultImageDao
import no.kristiania.reverseimagesearch.model.entity.ResultImage

class CollectionImagesViewModel(
    private val resultImageDao: ResultImageDao,
    private val requestImgId: Long
) : ViewModel() {


    lateinit var resultImages: LiveData<List<ResultImage>>
    private var _infoMessage = MutableLiveData<String>("")
    val infoMessage get() = _infoMessage


    private var _collectionName = MutableLiveData<String>("")
    val collectionName get() = _collectionName

    init {
        // Sub req. 8
        try {
            resultImages = resultImageDao.getByParentId(requestImgId)
        } catch (e: SQLiteException) {
            infoMessage.value = e.message.toString()
        }
    }

    fun setCollectionName(collectionName: String) {
        _collectionName.value = collectionName
        Log.d("Collection name", collectionName)
    }
}