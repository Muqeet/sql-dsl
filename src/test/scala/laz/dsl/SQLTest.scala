package laz.dsl

import laz.dsl.sql._

object Author extends Table {
    override def toString = "AUTHOR"
    val id = column name "ID"
}

object Book extends Table {
    override def toString = "BOOK"
    val title = column name "TITLE"
    val authorId = column link Author.id
    val language = column name "LANGUAGE"
}

object User extends Table {
    override def toString = "USER"
    val id = column name "NAME"
    val pass = column name "PASSWORD"
}

object SQLTest {
    def main(args: Array[String]) {
        val q1 = select ~ all ~ from ~ "users" ~ where ~ "uid" ~ equal ~ "'lazar'"
        println(q1.sql)

        val q2 = select ~ "FIRST_NAME" ~& "LAST_NAME" ~& "COUNT(*)" ~
            from ~ "AUTHOR" ~ join ~ "BOOK" ~
            on ~ "AUTHOR.ID" ~ equal ~ "BOOK.AUTHOR_ID" ~
            where ~ "LANGUAGE" ~ equal ~% "DE" ~
            having ~ "COUNT(*)" ~ greaterThan ~ 5 ~
            orderBy ~ "LAST_NAME" ~
            limit ~ 100

        println(q2.sql)

        val q3 = select ~ Author.id ~& Book.title ~
            from ~ Author ~ join ~ Book ~
            on ~ Author.id ~ equal ~ Book.authorId ~
            where ~ Book.language ~ equal ~? "DE" ~
            and ~ Author.id ~ greaterThan ~?? "kolio" ~
            limit ~ 101

        println(q3.sql)

        val q4 = select ~ all ~ from ~ User ~ where ~ User.id ~ equal ~ X ~ and ~ User.pass ~ equal ~ X
        println(q4.sql)
    }
}