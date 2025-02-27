package com.subhranil.bluemoon.lite.utils.result

enum class RemoteError: RootError {
    UNAUTHORIZED,
    NOT_FOUND,
    SERVER_ERROR,
    NETWORK_ERROR,
    UNKNOWN_ERROR,
    TIMEOUT
}