/**
 * This file is part of PSKOV:
 *     https://github.com/OGStudio/pskov
 * License: MIT
 * Version: 2.0.0
 */

package org.opengamestudio

import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor

// Convert Markdown to HTML
fun markdownToHTML(input: String): String {
    val flavour = GFMFlavourDescriptor()
    val tree = MarkdownParser(flavour).buildMarkdownTreeFromString(input)
    val html = HtmlGenerator(input, tree, flavour).generateHtml()
    return html
}
