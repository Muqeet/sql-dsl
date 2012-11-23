package laz.dsl

object sql {
    val select = "SELECT"
    val all = "*"
    val from = "FROM"
    val join = "JOIN"
    val on = "ON"
    val where = "WHERE"
    val and = "AND"
    val or = "OR"
    val groupBy = "GROUP BY"
    val having = "HAVING"
    val orderBy = "ORDER BY"
    val asc = "ASC"
    val desc = "DESC"
    val limit = "LIMIT"
    val offset = "OFFSET"
    val of = "OF"
    val equal = "="
    val greaterThan = ">"
    val smallerThan = "<"
    val X = "?"

    class Query(begin: String) {
        private val b = new StringBuilder // buffer if synchronization is needed otherwise builder

        b.append(begin)

        private def append(before: String, chunk: Any, after: String): Query = {
            if (before != null) b.append(before)
            b.append(chunk.toString)
            if (after != null) b.append(after)
            this
        }

        def ~(chunk: Any) = append(" ", chunk, null)
        def ~&(chunk: Any) = append(", ", chunk, null)
        def ~%(chunk: Any) = append(" '", chunk, "'")
        def ~%%(chunk: Any) = append(" \"", chunk, "\"")
        def ~?(chunk: Any) = append(" '", chunk, "'")
        def ~??(chunk: Any) = append(" \"", chunk, "\"")

        override def toString = b.toString

        def sql = toString
    }

    implicit def str2Query(s: String) = new Query(s)

    trait Table {
        protected val column = this
        def name(s: String) = toString + "." + s
        def link(s: String) = column name s.replace('.', '_')
    }
}