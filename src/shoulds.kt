/**
 * This file is part of PSKOV:
 *     https://github.com/OGStudio/pskov
 * License: CC0
 * Version: 2.0.1
 */

package org.opengamestudio

// Convert Markdown page to HTML one
//
// Conditions:
// 1. Markdown is available
fun shouldConvert(c: Context): Context {
    if (c.recentField == "markdownLines") {
        val path = c.inputFiles[c.convertFileId]
        var tmpl = pageTemplate(path, c.cfg, c.templates)
        val dt = pageDate(c.markdownLines)
        val fileName = pageFileName(c.markdownLines)
        val contents = pageContents(c.markdownLines)
        val title = pageTitle(c.markdownLines)
        val htmlContents = markdownToHTML(contents)
        c.html = tmpl
            .replace("PSKOV_ITEM_CONTENTS", htmlContents)
            .replace("PSKOV_ITEM_DATE", dt)
            .replace("PSKOV_ITEM_TITLE", title)
            .replace("PSKOV_ITEM_URL", fileName)
        c.recentField = "html"
        return c
    }

    c.recentField = "none"
    return c
}

// List all files of input directories
//
// Conditions:
// 1. Input directories are available
fun shouldListInputDirFiles(c: Context): Context {
    if (c.recentField == "inputDirs") {
        c.inputDirFiles = listInputDirFiles(c.inputDirs)
        c.recentField = "inputDirFiles"
        return c
    }

    c.recentField = "none"
    return c
}

// List files to process
//
// Conditions:
// 1. Templates are available
fun shouldListInputFiles(c: Context): Context {
    if (c.recentField == "templates") {
        c.inputFiles = listInputFiles(c.inputDirFiles)
        c.recentField = "inputFiles"
        return c
    }

    c.recentField = "none"
    return c
}

// List template files
//
// Conditions:
// 1. Files of input directories are available
fun shouldListTemplateFiles(c: Context): Context {
    if (c.recentField == "inputDirFiles") {
        val files = expectedTemplateFiles(c.cfg)
        c.templateFiles = listTemplateFiles(c.inputDirFiles, files)
        c.recentField = "templateFiles"
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
// 1. Time to convert another Markdown file
fun shouldReadMarkdown(c: Context): Context {
    if (c.recentField == "convertFileId") {
        val path = c.inputFiles[c.convertFileId]
        c.markdownLines = fsReadFile(path)
        c.recentField = "markdownLines"
        return c
    }

    c.recentField = "none"
    return c
}

// Read template files
//
// Conditions:
// 1. Template files are available
fun shouldReadTemplates(c: Context): Context {
    if (c.recentField == "templateFiles") {
        c.templates = readTemplates(c.templateFiles)
        c.recentField = "templates"
        return c
    }

    c.recentField = "none"
    return c
}

// Iterate over input files to convert them
//
// Conditions:
// 1. Input files are available
// 2. HTML file was saved
fun shouldRepeatConversion(c: Context): Context {
    if (c.recentField == "inputFiles") {
        c.convertFileId = 0
        c.recentField = "convertFileId"
        return c
    }

    if (
        c.recentField == "didSaveHTML" &&
        c.convertFileId + 1 < c.inputFiles.size
    ) {
        c.convertFileId += 1
        c.recentField = "convertFileId"
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

// Save generated HTML to disk
//
// Conditions:
// 1. Generated HTML is available
fun shouldSaveHTML(c: Context): Context {
    if (c.recentField == "html") {
        val inputFile = c.inputFiles[c.convertFileId]
        val fileName = pageFileName(c.markdownLines)
        val path = outputFile(inputFile, fileName)
        fsWriteFile(path, c.html)
        c.didSaveHTML = true
        c.recentField = "didSaveHTML"
        return c
    }

    c.recentField = "none"
    return c
}
