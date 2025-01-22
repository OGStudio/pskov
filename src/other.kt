/**
 * This file is part of PSKOV:
 *     https://github.com/OGStudio/pskov
 * License: MIT
 * Version: 2.0.0
 */

package org.opengamestudio


// Return cfg directory
fun cfgDir(path: String): String {
    if (path.endsWith(CFG_FILE)) {
        val len = path.length - CFG_FILE.length
        return path.substring(len)
    }

    return ""
}

// Return cfg[input] values prefixed by directory
fun cfgInputDirs(
    dic: Map<String, String>,
    prefix: String
): Array<String> {
    val items = dic["input"]!!.split(";")
    var dirs = arrayOf<String>()
    for (item in items) {
        dirs += "$prefix$FS_DELIMITER$item"
    }
    return dirs
}

// Parse cfg file contents
fun cfgParse(lines: Array<String>): Map<String, String> {
    var dic = mutableMapOf<String, String>()
    for (ln in lines) {
        cfgParseLine(dic, ln)
    }
    return dic
}

// Parse single cfg line
fun cfgParseLine(
    dic: MutableMap<String, String>,
    ln: String
) {
    val parts = ln.split(" = ")

    // Skip invalid line
    if (parts.size != 2) {
        return
    }

    // Store key-value pair into dictionary
    val key = parts[0]
    val value = parts[1]
    dic[key] = value
}

// Extract cfg file path from command line arguments
fun cliCfg(args: Array<String>): String {
    for (arg in args) {
        if (arg.startsWith(ARGUMENT_CFG)) {
            val prefix = ARGUMENT_CFG + "="
            val path = arg.substring(prefix.length)
            return path
        }
    }
    return ""
}
