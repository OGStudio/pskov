/**
 * This file is part of PSKOV:
 *     https://github.com/OGStudio/pskov
 * License: MIT
 * Version: 2.0.0
 */

package org.opengamestudio

// List files to process
//
// Conditions:
// 1. Input dirs are available
fun shouldListInputFiles(c: Context): Context {
    if (c.recentField == "inputDirs") {
        c.inputFiles = listInputFiles(c.inputDirs)
        c.recentField = "inputFiles"
        return c
    }

    c.recentField = "none"
    return c
}
// Parse cfg
//
// Conditions:
// 1. Cfg file contents are available
fun shouldParseCfg(c: Context): Context {
    if (c.recentField == "cfgLines") {
        c.cfg = cfgParse(c.cfgLines)
        c.recentField = "cfg"
        return c
    }

    c.recentField = "none"
    return c
}

// Print to console
//
// Conditions:
// 1. At app launch no command line arguments were provided
fun shouldPrintToConsole(c: Context): Context {
    if (
        c.recentField == "didLaunch" &&
        c.arguments.isEmpty()
    ) {
        c.consoleOutput = "Usage: {bin} --cfg=/path/to/pskov.cfg"
        c.recentField = "consoleOutput"
        return c
    }

    c.recentField = "none"
    return c
}

// Read cfg file
//
// Conditions:
// 1. Cfg file path is now available
fun shouldReadCfg(c: Context): Context {
    if (c.recentField == "cfgPath") {
        c.cfgLines = fsReadFile(c.cfgPath)
        c.recentField = "cfgLines"
        return c
    }

    c.recentField = "none"
    return c
}

// Read markdown file
//
// Conditions:
// 1. Started conversion of markdown file
fun shouldReadMarkdown(c: Context): Context {
    if (c.recentField == "startConversion") {
        val path = c.inputFiles[c.startConversion]
        c.markdownLines = fsReadFile(path)
        c.recentField = "markdownLines"
        return c
    }

    c.recentField = "none"
    return c
}

// Reset path to cfg file
//
// Conditions:
// 1. At app launch cfg file was specified with command line argument
fun shouldResetCfgPath(c: Context): Context {
    if (
        c.recentField == "didLaunch" &&
        cliCfg(c.arguments).length > 0
    ) {
        c.cfgPath = cliCfg(c.arguments)
        c.recentField = "cfgPath"
        return c
    }

    c.recentField = "none"
    return c
}

// Reset debug output state
//
// Conditions:
// 1. Arguments are available
fun shouldResetDbg(c: Context): Context {
    if (
        c.recentField == "arguments" &&
        cliDbg(c.arguments)
    ) {
        c.isDbg = cliDbg(c.arguments)
        c.recentField = "isDbg"
        return c
    }

    c.recentField = "none"
    return c
}

// Reset input dirs from cfg's `input`
//
// Conditions:
// 1. Cfg has been parsed
fun shouldResetInputDirs(c: Context): Context {
    if (
        c.recentField == "cfg" &&
        c.cfg.containsKey("input")
    ) {
        val dir = cfgDir(c.cfgPath)
        c.inputDirs = cfgInputDirs(c.cfg, dir)
        c.recentField = "inputDirs"
        return c
    }

    c.recentField = "none"
    return c
}

// Start conversion of the file specified by id
//
// Conditions:
// 1. Input files are available
fun shouldStartConversion(c: Context): Context {
    if (c.recentField == "inputFiles") {
        c.startConversion = 0
        c.recentField = "startConversion"
        return c
    }

    c.recentField = "none"
    return c
}
