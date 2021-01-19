package fr.test200.spacedim.dataClass

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import fr.test200.spacedim.BR

class EditTextName: BaseObservable() {
    @Bindable
    var name: String = String()
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }
        get() = field
}