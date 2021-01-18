package fr.test200.spacedim.dataClass

enum class UIType {
    BUTTON, SWITCH, SHAKE
}

interface IElement {
    var id: Int
    val content: String
}

sealed class UIElement(val type: UIType) : IElement {
    data class Button(override var id: Int, override val content: String) : UIElement(UIType.BUTTON)
    data class Switch(override var id: Int, override val content: String) : UIElement(UIType.SWITCH)
    data class Shake(override var id: Int, override val content: String) : UIElement(UIType.SHAKE)
}
