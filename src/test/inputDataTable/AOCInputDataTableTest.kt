package test.inputDataTable

import println
import readAOCInputDataTable

fun main() {
    val data = readAOCInputDataTable("test/inputDataTable/AOCInputDataTableTest")
    data.lines.println()
    data.columns.println()
}