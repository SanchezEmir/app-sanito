package com.sanchezemir.sanito.authentication.data.repository

import com.sanchezemir.sanito.authentication.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl : AuthenticationRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
//            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signup(email: String, password: String): Result<Unit> {
        return try {
//            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUserId(): String? {
//        return Firebase.auth.currentUser?.uid
        return null
    }

    override suspend fun logout() {
//        Firebase.auth.signOut()
    }
}
