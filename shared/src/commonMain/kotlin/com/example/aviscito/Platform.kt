package com.example.aviscito

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform