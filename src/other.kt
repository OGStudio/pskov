/**
 * This file is part of PSKOV:
 *     https://github.com/OGStudio/pskov
 * License: CC0
 * Version: 2.0.1
 */

package org.opengamestudio

// Return directory where config is located
fun cfgDir(path: String): String {
    if (path.endsWith(CFG_FILE)) {
        val len = path.length - CFG_FILE.length
        return path.substring(0, len)
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
        val dir = prefix + FS_DELIMITER + item
        // Remove potentially duplicated FS_DELIMITER
        val cleanDir = dir.replace(FS_DELIMITER + FS_DELIMITER, FS_DELIMITER)
        dirs += cleanDir
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

// Detect the presence of debug command line argument
fun cliDbg(args: Array<String>): Boolean {
    for (arg in args) {
        if (arg == ARGUMENT_DBG) {
            return true
        }
    }
    return false
}

// Convert string array to debug string
fun dbgStringArray(items: Array<String>): String {
    var output = "(${items.size})["
    var i = 0
    // Construct the preview.
    for (str in items) {
        output += str + ","
        i += 1
        // Interrupt the preview.
        if (i > 2) {
            output += "..."
            break
        }
    }
    output += "]"
    return output
}

// Extract directory from path by dropping the ending
fun dirname(path: String): String {
    val parts = path.split(FS_DELIMITER)
    val dropped = parts.dropLast(1)
    return dropped.joinToString(FS_DELIMITER)
}

// Extract file names from config
fun expectedTemplateFiles(cfg: Map<String, String>): Array<String> {
    var files = arrayOf<String>()
    arrayOf(
        TEMPLATE_INDEX,
        TEMPLATE_ITEM,
        TEMPLATE_PAGINATION_NEXT,
        TEMPLATE_PAGINATION_PREV,
        TEMPLATE_PAGINATION_PREV_NEXT,
        TEMPLATE_PREVIEW,
    ).forEach { f ->
        if (
            cfg.containsKey(f) &&
            cfg[f]!!.length > 0
        ) {
            files += cfg[f]!!
        }
    }
    return files
}

// Collect list of all files in input directories
fun listInputDirFiles(dirs: Array<String>): Array<String> {
    var files = arrayOf<String>()
    for (dir in dirs) {
        val fs = fsListFiles(dir)
        for (f in fs) {
            if (f.isFile) {
                files += dir + FS_DELIMITER + f.name
            }
        }
    }
    files.sort()
    return files
}

// Extract Markdown files from list of all files
fun listInputFiles(allFiles: Array<String>): Array<String> {
    var files = arrayOf<String>()
    for (f in allFiles) {
        if (f.endsWith(".md")) {
            files += f
        }
    }
    return files
}

// List template files of input directories
fun listTemplateFiles(
    dirFiles: Array<String>,
    templateFiles: Array<String>
): Array<String> {
    var files = arrayOf<String>()
    for (df in dirFiles) {
        for (tf in templateFiles) {
            if (df.endsWith(tf)) {
                files += df
            }
        }
    }
    return files
}

// Convert input Markdown filename to output HTML filename
fun outputFile(
    inputFile: String,
    fileName: String
): String {
    val dir = dirname(inputFile)
    return dir + FS_DELIMITER + fileName
}

// Generated HTML contents
fun pageContents(mdLines: Array<String>): String {
    var contents = ""
    var isHeaderOver = false
    for (ln in mdLines) {
        if (isHeaderOver) {
            contents += ln + "\n"
        }
        else if (
            !isHeaderOver &&
            ln == ""
        ) {
            // Header ends at the first blank line
            isHeaderOver = true
        }
    }

    return contents
}

// Article date of the generated HTML page
fun pageDate(mdLines: Array<String>): String {
    for (ln in mdLines) {
        if (ln.startsWith(PAGE_DATE)) {
            return ln.replace(PAGE_DATE, "").trim()
        }
    }

    return "unknown-page-date"
}

// File name of the generated HTML page
fun pageFileName(mdLines: Array<String>): String {
    for (ln in mdLines) {
        if (ln.startsWith(PAGE_SLUG)) {
            val slug = ln.replace(PAGE_SLUG, "").trim()
            return slug + ".html"
        }
    }

    return "unknown-page-filename.html"
}

// Item template for the page
fun pageTemplate(
    path: String,
    cfg: Map<String, String>,
    templates: Map<String, String>
): String {
    val dir = dirname(path)
    val templateFile = dir + FS_DELIMITER + cfg[TEMPLATE_ITEM]!!
    return templates[templateFile]!!
}

// Article title of the generated HTML page
fun pageTitle(mdLines: Array<String>): String {
    for (ln in mdLines) {
        if (ln.startsWith(PAGE_TITLE)) {
            return ln.replace(PAGE_TITLE, "").trim()
        }
    }

    return "unknown-page-slug"
}

// Read template files
fun readTemplates(files: Array<String>): Map<String, String> {
    var d = mutableMapOf<String, String>()
    for (file in files) {
        val lines = fsReadFile(file)
        val contents = lines.joinToString("\n")
        d[file] = contents
    }
    return d
}
