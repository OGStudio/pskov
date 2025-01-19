/**
 * This file is part of PSKOV:
 *     https://github.com/OGStudio/pskov
 * License: MIT
 * Version: 2.0.0
 */

package org.opengamestudio

// Extract input file path from command line arguments
fun cliInputFile(args: Array<String>): String {
    for (arg in args) {
        if (arg.startsWith(ARGUMENT_FILE)) {
            val prefix = ARGUMENT_FILE + "="
            val path = arg.substring(prefix.length)
            return path
        }
    }
    return ""
}

// Extract output file path from command line arguments
fun cliOutputFile(args: Array<String>): String {
    for (arg in args) {
        if (arg.startsWith(ARGUMENT_OUT)) {
            val prefix = ARGUMENT_OUT + "="
            val path = arg.substring(prefix.length)
            return path
        }
    }
    return ""
}
