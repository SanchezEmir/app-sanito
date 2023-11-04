package com.sanchezemir.sanito.authentication.data.matcher

import android.util.Patterns
import com.sanchezemir.sanito.authentication.domain.matcher.EmailMatcher

class EmailMatcherImpl : EmailMatcher {
    override fun isValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}