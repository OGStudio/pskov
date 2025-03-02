/**
 * This file is part of PSKOV:
 *     https://github.com/OGStudio/pskov
 * License: CC0
 * Version: 2.0.1
 */

package org.opengamestudio

// Debug controller/context changes to console
fun consoleDebug(c: Context) {
    val key = c.recentField
    val value = c.fieldAny(c.recentField)
    var strval = "${value}"
    // Preview array of strings
    (value as? Array<String>)?.also { items ->
        strval = dbgStringArray(items)
    }
    // Limit the length of printed value
    strval = strval.take(DBG_LEN)

    if (c.isDbg) {
        println("PSK-DBG '$key': '$strval'")
    }
}

// Print to console
fun consolePrint(c: Context) {
    if (c.recentField == "consoleOutput") {
        println(c.consoleOutput)
    }
}

// Register callbacks to print to console
fun consoleRegisterCallbacks(ctrl: CLDController) {
    ctrl.registerCallback({ cc ->
        val c = cc as Context
        consoleDebug(c)
        consolePrint(c)
    })
}
