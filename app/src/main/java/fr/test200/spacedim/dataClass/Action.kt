package fr.test200.spacedim.dataClass

data class Action(
        val sentence: String,
        val uiElement: UIElement,
        val time: Long = 8000
)
