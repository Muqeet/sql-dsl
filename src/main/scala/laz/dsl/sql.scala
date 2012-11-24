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

        def ~(chunk: Any) = append(" ", chunk, null)
        def ~?(chunk: Any) = {
            b.append(" ")
            argument(chunk)
            this
        }
        def ~&(chunk: Any) = append(", ", chunk, null)
        def ~#(chunk: Any) = append(" (", chunk, null)
        def ~&#(chunk: Any) = append(", ", chunk, ")")
        
        private def append(before: String, chunk: Any, after: String): Query = {
            if (before != null) b.append(before)
            if (chunk != null) b.append(chunk.toString)
            if (after != null) b.append(after)
            this
        }

        def argument(chunk: Any) {
            chunk match {
                case i: java.lang.Integer => b.append(chunk.toString)
                case i: Int               => b.append(chunk.toString)
                case f: Float             => b.append(chunk.toString)
                case d: Double            => b.append(chunk.toString)
                case _ =>
                    b.append("'")
                    b.append(chunk.toString)
                    b.append("'")
            }
        }

        def group(chunks: Array[Any])(f: (Query, Any, Int) => Unit): Query = {
            var i = 0
            for (chunk <- chunks) {
                f(this, chunk, i)
                i += 1
            }

            this
        }

        private var first = true
        def value(chunks: Any*) = this.group(chunks.toArray) { (query: Query, chunk: Any, i: Int) =>
            if (first) first = false else b.append(", ") 

            if (i == 0) {
                b.append(" (")
                argument(chunk)
            } else if (i == chunks.length - 1) {
                // b.append(", ")
                argument(chunk)
                b.append(")")
            } else {
                // b.append(", ")
                argument(chunk)
            }
        }

        override def toString = {
            b.append(";")
            b.toString
        }

        def sql = toString
    }

    implicit def str2Query(s: String) = new Query(s)

    object SELECT {
        def apply(chunks: String*) = new Query(select).group(chunks.toArray) { (query: Query, column: Any, i: Int) =>
            if (i == 0) query ~ column else query ~& column

        }
    }

    object INSERTINTO {
        def apply(table: Table)(columns: String*) = new Query("INSERT INTO").~(table).group(columns.toArray) { (query: Query, column: Any, i: Int) =>
            if (i == 0) query ~# column else if (i == columns.length - 1) query ~&# column else query ~& column
        } ~ "VALUES"
    }

    trait Table {
        protected val column = this
        def name(s: String) = toString + "." + s
        def link(s: String) = column name s.replace('.', '_')
    }
}