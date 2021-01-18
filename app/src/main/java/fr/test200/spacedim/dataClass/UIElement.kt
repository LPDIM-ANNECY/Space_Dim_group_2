package fr.test200.spacedim.dataClass

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

enum class UIType {
    BUTTON, SWITCH, SHAKE
}

interface IElement {
    var id: Int
    val content: String
}

@JsonClass(generateAdapter = true)
sealed class UIElement(val type: UIType) : IElement {
    data class Button(override var id: Int, override val content: String) : UIElement(UIType.BUTTON)
    data class Switch(override var id: Int, override val content: String) : UIElement(UIType.SWITCH)
    data class Shake(override var id: Int, override val content: String) : UIElement(UIType.SHAKE)
}
