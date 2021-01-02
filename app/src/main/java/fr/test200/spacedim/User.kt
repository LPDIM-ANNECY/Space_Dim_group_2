package fr.test200.spacedim

data class User(val id: Int, val name: String, val avatar: String,
                val score: Int, var state: State = State.OVER) {
}