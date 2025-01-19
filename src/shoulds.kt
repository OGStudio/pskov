/**
 * This file is part of PSKOV:
 *     https://github.com/OGStudio/pskov
 * License: MIT
 * Version: 2.0.0
 */

package org.opengamestudio

// Print to console
//
// Conditions:
// 1. At app launch no command line arguments were provided
fun shouldPrintToConsole(c: Context): Context {
    if (
        c.recentField == "didLaunch" &&
        c.arguments.isEmpty()
    ) {
        c.consoleOutput = "Usage: {bin} --file=/path/to/pskov.cfg"
        c.recentField = "consoleOutput"
        return c
    }

    c.recentField = "none"
    return c
}
