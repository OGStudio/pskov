package org.opengamestudio


data class Context(
    // Command line arguments
    var arguments: Array<String> = arrayOf(),
    // Config file contents as key-value dictionary
    var cfgLines: Array<String> = arrayOf(),
    var cfgPath: String = "",
    var consoleOutput: String = "",
    // Conversion iterator
    var convertFileId: Int = 0,
    var didLaunch: Boolean = false,
    var didSaveHTML: Boolean = false,
    var html: String = "",
    // List of all files in input directories
    var inputDirFiles: Array<String> = arrayOf(),
    var inputDirs: Array<String> = arrayOf(),
    // List of files to process
    var inputFiles: Array<String> = arrayOf(),
    var isDbg: Boolean = false,
    var markdownLines: Array<String> = arrayOf(),
    var templateFiles: Array<String> = arrayOf(),
    override var recentField: String = "",
): CLDContext {
    override fun <T> field(name: String): T {
        if (name == "arguments") {
            return arguments as T
        } else if (name == "cfgLines") {
            return cfgLines as T
        } else if (name == "cfgPath") {
            return cfgPath as T
        } else if (name == "consoleOutput") {
            return consoleOutput as T
        } else if (name == "convertFileId") {
            return convertFileId as T
        } else if (name == "didLaunch") {
            return didLaunch as T
        } else if (name == "didSaveHTML") {
            return didSaveHTML as T
        } else if (name == "html") {
            return html as T
        } else if (name == "inputDirFiles") {
            return inputDirFiles as T
        } else if (name == "inputDirs") {
            return inputDirs as T
        } else if (name == "inputFiles") {
            return inputFiles as T
        } else if (name == "isDbg") {
            return isDbg as T
        } else if (name == "markdownLines") {
            return markdownLines as T
        } else if (name == "templateFiles") {
            return templateFiles as T
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
        } else if (name == "cfgLines") {
            cfgLines = value as Array<String>
        } else if (name == "cfgPath") {
            cfgPath = value as String
        } else if (name == "consoleOutput") {
            consoleOutput = value as String
        } else if (name == "convertFileId") {
            convertFileId = value as Int
        } else if (name == "didLaunch") {
            didLaunch = value as Boolean
        } else if (name == "didSaveHTML") {
            didSaveHTML = value as Boolean
        } else if (name == "html") {
            html = value as String
        } else if (name == "inputDirFiles") {
            inputDirFiles = value as Array<String>
        } else if (name == "inputDirs") {
            inputDirs = value as Array<String>
        } else if (name == "inputFiles") {
            inputFiles = value as Array<String>
        } else if (name == "isDbg") {
            isDbg = value as Boolean
        } else if (name == "markdownLines") {
            markdownLines = value as Array<String>
        } else if (name == "templateFiles") {
            templateFiles = value as Array<String>
        }
    }
}

// File system item representation
data class FSFile(
    // Templates for input directories
    var isDirectory: Boolean = false,
    var isFile: Boolean = false,
    var name: String = "",
) {}
