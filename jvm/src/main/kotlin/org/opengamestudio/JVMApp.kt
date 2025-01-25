package org.opengamestudio

class JVMApp { }

fun main(args: Array<String>) {
    val ctrl = CLDController(Context())
    consoleRegisterCallbacks(ctrl)

    // Register behaviour.
    arrayOf(
        ::shouldListInputFiles,
        ::shouldParseCfg,
        ::shouldPrintToConsole,
        ::shouldReadCfg,
        ::shouldReadMarkdown,
        ::shouldResetCfgPath,
        ::shouldResetDbg,
        ::shouldResetInputDirs,
        ::shouldStartConversion,
    ).forEach { f ->
        ctrl.registerFunction { c -> f(c as Context) }
    }

    // Pass CLI parameters.
    ctrl.set("arguments", args)
    // Launch.
    ctrl.set("didLaunch", true)


    // TODO: REMOVE
    println("ИГР: ${markdownToHTML("Sample **text**")}")
}
