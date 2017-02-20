package com.malpo.potluck.firebase.exceptions

/**
 * This exception occured when the firebase returns a permission
 * denied error
 */
class FirebasePermissionDeniedException(detailMessage: String) : Exception(detailMessage)
