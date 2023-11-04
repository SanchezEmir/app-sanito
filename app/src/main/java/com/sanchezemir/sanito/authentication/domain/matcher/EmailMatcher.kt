package com.sanchezemir.sanito.authentication.domain.matcher

interface EmailMatcher {
    fun isValid(email: String): Boolean
}