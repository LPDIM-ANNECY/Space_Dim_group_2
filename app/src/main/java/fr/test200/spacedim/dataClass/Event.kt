package fr.test200.spacedim.dataClass

import com.squareup.moshi.Json

enum class EventType() {
    GAME_STARTED(), GAME_OVER(), ERROR(), READY(), NEXT_ACTION(),
    NEXT_LEVEL(), WAITING_FOR_PLAYER(), PLAYER_ACTION()
}

sealed class Event(@Json(name = "type") val eventType: EventType) {
    data class NextAction(val action: Action): Event(EventType.NEXT_ACTION)
    data class GameStarted(val uiElementList: List<UIElement>): Event(EventType.GAME_STARTED)
    data class GameOver(val score: Int, val win: Boolean, val level: Int): Event(EventType.GAME_OVER)
    data class NextLevel(val uiElementList: List<UIElement>, val level: Int): Event(EventType.NEXT_LEVEL)
    data class WaitingForPlayer(val userList: List<User>): Event(EventType.WAITING_FOR_PLAYER)
    data class Error(val message: String): Event(EventType.ERROR)
    data class Ready(val value: Boolean): Event(EventType.READY)
    data class PlayerAction(val uiElement: UIElement): Event(EventType.PLAYER_ACTION)
}