package fr.test200.spacedim

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import fr.test200.spacedim.dataClass.*

object GameEventTools {

    private val moshiSpaceCommunicationSerializer: Moshi = with(Moshi.Builder()) {
        add(
            PolymorphicJsonAdapterFactory.of(Event::class.java, "type")
                .withSubtype(Event.GameStarted::class.java, EventType.GAME_STARTED.name)
                .withSubtype(Event.WaitingForPlayer::class.java, EventType.WAITING_FOR_PLAYER.name)
                .withSubtype(Event.GameOver::class.java, EventType.GAME_OVER.name)
                .withSubtype(Event.NextAction::class.java, EventType.NEXT_ACTION.name)
                .withSubtype(Event.NextLevel::class.java, EventType.NEXT_LEVEL.name)
                .withSubtype(Event.Error::class.java, EventType.ERROR.name)
                .withSubtype(Event.Ready::class.java, EventType.READY.name)
                .withSubtype(Event.PlayerAction::class.java, EventType.PLAYER_ACTION.name)
        )
        add(
            PolymorphicJsonAdapterFactory.of(UIElement::class.java, "type")
                .withSubtype(UIElement.Button::class.java, UIType.BUTTON.name)
                .withSubtype(UIElement.Switch::class.java, UIType.SWITCH.name)
                .withSubtype(UIElement.Shake::class.java, UIType.SHAKE.name)
        )
        add(KotlinJsonAdapterFactory())
        build()
    }

    val spaceEventParser: JsonAdapter<Event> = moshiSpaceCommunicationSerializer.adapter(
        Event::class.java
    )
}