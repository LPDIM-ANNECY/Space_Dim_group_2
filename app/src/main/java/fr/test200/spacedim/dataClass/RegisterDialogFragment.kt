package fr.test200.spacedim.dataClass

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import fr.test200.spacedim.R

class RegisterDialogFragment(private val title: String, private val message : String, private val textPositive : String, private val textNegative : String? = null) : DialogFragment() {
    var onPositiveClick: ((userName: String) -> Unit)? = null

    var onNegativeClick: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val editText = EditText(context)

        editText.inputType = InputType.TYPE_CLASS_TEXT
        dialog?.setContentView(R.layout.login_fragment)
        return with(AlertDialog.Builder(context)) {
            setView(editText)
            setTitle(title)
            setMessage(message)
            setPositiveButton(textPositive) { _, _ -> onPositiveClick?.invoke(editText.text.toString()) }
            if(textNegative != null) {
                setNegativeButton(textNegative) { dialog, _ ->
                    dialog.cancel()
                    onNegativeClick?.invoke() }
            }
            create()
        }
    }
}