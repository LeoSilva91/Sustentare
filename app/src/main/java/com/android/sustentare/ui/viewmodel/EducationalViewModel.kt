package com.android.sustentare.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

open class EducationalViewModel : ViewModel() {

    val _educationalContent = MutableLiveData<List<EducationalContent>>()
    val educationalContent: LiveData<List<EducationalContent>> get() = _educationalContent

    private val _educationalDetailContent = MutableLiveData<EducationalContent?>()
    val educationalDetailContent: LiveData<EducationalContent?> get() = _educationalDetailContent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val firestore = FirebaseFirestore.getInstance()

    // Função otimizada para carregar o conteúdo educacional
    fun loadEducationalContent() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val result = FirebaseFirestore.getInstance().collection("educationalContent").get().await()
                val contentList = result.map { document ->
                    document.toObject(EducationalContent::class.java)
                }
                Log.d("EducationalContent", "Content loaded: $contentList")
                _educationalContent.value = contentList
            } catch (exception: Exception) {
                Log.e("EducationalContent", "Error loading content: ${exception.message}")
                _error.value = exception.message
            } finally {
                _isLoading.value = false
            }
        }
    }



    // Função para carregar os detalhes educacionais
    fun loadEducationalDetailContent(link: String) {
        viewModelScope.launch {
            try {
                val result = firestore.collection("educationalContent")
                    .whereEqualTo("link", link)
                    .get().await()

                _educationalDetailContent.value = if (result.isEmpty) null else result.first().toObject(EducationalContent::class.java)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}

// Modelo de dados
data class EducationalContent(
    val title: String = "",
    val description: String = "",
    val link: String = "",
    val date: String = ""
)
