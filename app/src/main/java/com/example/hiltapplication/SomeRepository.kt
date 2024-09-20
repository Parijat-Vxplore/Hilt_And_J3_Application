package com.example.hiltapplication

import javax.inject.Inject

class SomeRepository @Inject constructor() {
    fun fetchData(): String {
        return "Hello from the Repository!"
    }
}
