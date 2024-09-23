package com.android.sustentare.ui.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authStates = MutableLiveData<AuthStates>()
    val authStates: LiveData<AuthStates> = _authStates

    private var handler = Handler(Looper.getMainLooper())
    private var timeoutRunnable: Runnable? = null
    private var isTimeoutActive  = false

    init {
        checkAuthStatus()
    }

    // Auth methods here
    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authStates.value = AuthStates.Unauthenticated
        } else
            _authStates.value = AuthStates.Authenticated
    }

    fun checkLogin(email: String, password: String) {

        if (email.isBlank() || password.isBlank()) {
            _authStates.value = AuthStates.Error("Não pode deixar campos vazios")
            return
        }
        _authStates.value = AuthStates.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authStates.value = AuthStates.Authenticated
                } else {
                    _authStates.value =
                        AuthStates.Error(task.exception?.message ?: "Algo deu errado no seu login")
                }
            }
        }

    fun checkSignup(email: String, password: String, confirmPassword: String) {
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            _authStates.value = AuthStates.Error("Não pode deixar campos vazios")
            return
        }

        if (password != confirmPassword) {
            _authStates.value = AuthStates.Error("As senhas não coincidem")
            return
        }

        if (password.length < 8) {
            _authStates.value = AuthStates.Error("A senha deve ter no mínimo 8 caracteres")
            return
        }

        _authStates.value = AuthStates.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authStates.value = AuthStates.SignupSuccess // Emitindo o novo estado
                } else {
                    _authStates.value = AuthStates.Error(task.exception?.localizedMessage ?: "Erro no cadastro")
                }
            }
    }

    fun checkLogout() {
        Log.d("AuthViewModel", "checkLogout called")
        auth.signOut()
        _authStates.value = AuthStates.Unauthenticated
    }


    // Methods for logout in second plane
    fun startTimeout() {
        if (isTimeoutActive) return
        cancelTimeout()
        timeoutRunnable = Runnable {
            checkLogout()
        }
        handler.postDelayed(timeoutRunnable!!, 10000)
        isTimeoutActive = true
    }

    fun cancelTimeout() {
        timeoutRunnable?.let {
            handler.removeCallbacks(it)
            timeoutRunnable = null
            isTimeoutActive = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelTimeout()
    }
}

    sealed class AuthStates {
        data object Loading : AuthStates()
        data object Authenticated : AuthStates()
        data object Unauthenticated : AuthStates()
        data object SignupSuccess : AuthStates()
        data class Error(val message: String) : AuthStates()

    }