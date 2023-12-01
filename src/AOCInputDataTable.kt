class AOCInputDataTable(val lines: List<String>) {
    val columns =
        (0..<lines.first().length).map { index ->
            lines.map { it[index].toString() }
                .joinToString(separator = "")
        }
}