/**
 * This file is part of PSKOV:
 *     https://github.com/OGStudio/pskov
 * License: MIT
 * Version: 2.0.0
 */

package org.opengamestudio

// Application state
data class Context(
    // Command line arguments
    var arguments: Array<String> = arrayOf(),
    // Config contents as key-value dictionary
    var cfg: Map<String, String> = mapOf(),
    // Config file contents as lines
    var cfgLines: Array<String> = arrayOf(),
    // Path to config
    var cfgPath: String = "",
    // String to print to console
    var consoleOutput: String = "",
    // The application did finish launching
    var didLaunch: Boolean = false,
    // Finished writing to output file
    var didWriteOutputFile: Boolean = false,
    // List of directories from cfg's `input`
    var inputDirs: Array<String> = arrayOf(),
    override var recentField: String = "",
): CLDContext {
    override fun <T> field(name: String): T {
        if (name == "arguments") {
            return arguments as T
        } else if (name == "cfg") {
            return cfg as T
        } else if (name == "cfgLines") {
            return cfgLines as T
        } else if (name == "cfgPath") {
            return cfgPath as T
        } else if (name == "consoleOutput") {
            return consoleOutput as T
        } else if (name == "didLaunch") {
            return didLaunch as T
        } else if (name == "didWriteOutputFile") {
            return didWriteOutputFile as T
        } else if (name == "inputDirs") {
            return inputDirs as T
        }
        return "unknown-field-name" as T
    }

    override fun selfCopy(): CLDContext {
        return this.copy()
    }

    override fun setField(
        name: String,
        value: Any?
    ) {
        if (name == "arguments") {
            arguments = value as Array<String>
        } else if (name == "cfg") {
            cfg = value as Map<String, String>
        } else if (name == "cfgLines") {
            cfgLines = value as Array<String>
        } else if (name == "cfgPath") {
            cfgPath = value as String
        } else if (name == "consoleOutput") {
            consoleOutput = value as String
        } else if (name == "didLaunch") {
            didLaunch = value as Boolean
        } else if (name == "didWriteOutputFile") {
            didWriteOutputFile = value as Boolean
        } else if (name == "inputDirs") {
            inputDirs = value as Array<String>
        }
    }
}
