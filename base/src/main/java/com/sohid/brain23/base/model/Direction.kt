package com.sohid.brain23.base.model

sealed class Direction {

  class Repo(val owner: String, val repo: String) : Direction()

  object Pop : Direction()
}
