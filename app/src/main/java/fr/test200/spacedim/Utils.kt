package fr.test200.spacedim

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.inputmethod.InputMethodManager
import kotlin.system.exitProcess


class Utils {

    companion object {
        fun createDialog(
            context: Context,
            title: String,
            message: String,
            cancelable: Boolean = true,
            onQuit : () -> Unit = fun (){}
        ): AlertDialog? {
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)

            if (!cancelable) alertDialog.setCancelable(false)

            return alertDialog.setTitle(title)
                .setMessage(message) // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { _, _ ->
                    onQuit()
                    exitProcess(0)
                }) // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        }

        fun hideKeyboard(activity: Activity) {
            val imm: InputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (null != activity.currentFocus) imm.hideSoftInputFromWindow(
                activity.currentFocus!!.applicationWindowToken,
                0
            )
        }

        fun setTimeout(runnable: Runnable, delay: Int) {
            Thread {
                try {
                    Thread.sleep(delay.toLong())
                    runnable.run()
                } catch (e: Exception) {
                    System.err.println(e)
                }
            }.start()
        }
    }

}