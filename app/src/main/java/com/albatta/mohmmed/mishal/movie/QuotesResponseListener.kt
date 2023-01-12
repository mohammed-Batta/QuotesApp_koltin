package com.albatta.mohmmed.mishal.movie

interface QuotesResponseListener {
    fun didFetch(response: ArrayList<QuotesModel>, manager: String)
    fun didError(manager: String)
}