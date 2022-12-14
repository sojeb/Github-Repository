package com.sohid.brain23.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ListResponse<T : Any> {

  @SerialName("total_count")
  var totalCount: Int? = null

  @SerialName("incomplete_results")
  var incompleteResults: Boolean? = null

  @SerialName("items")
  var items: List<T>? = null
}
