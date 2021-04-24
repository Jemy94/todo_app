package com.jemy.todoapp.utils.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.jemy.todoapp.R


fun Fragment.toast(msg: String?) {
    msg?.let {
        Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
    }
}

fun Fragment.toastLong(msg: String?) {
    msg?.let {
        Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
    }
}

fun Fragment.hideKeypad() {
    activity?.let { activity ->
        activity.currentFocus?.let { view ->
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

fun Fragment.showKeypad() {
    activity?.let { activity ->
        val manager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        )
    }
}

fun Fragment.messageDialog(@StringRes res: Int? = null, message: String? = null): MaterialDialog? {
    return if (isAdded) {
        return MaterialDialog(activity!!)
            .title(R.string.dialog_title)
            .message(res, message)
            .cancelable(true)
            .cancelOnTouchOutside(true)
            .positiveButton(android.R.string.ok) { it.dismiss() }
    } else {
        null
    }
}


