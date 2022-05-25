package me.dannly.mirainikki.domain.repository

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher
}