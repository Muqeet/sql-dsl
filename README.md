Scala DSL for SQL generation

<pre>
scala> println {
     | select ~ all ~ from ~ User ~ where ~ User.id ~ equal ~ X ~ and ~ User.pass ~ equal ~ X
     | }
SELECT * FROM USER WHERE USER.NAME = ? AND USER.PASSWORD = ?
</pre>

check tests for more examples ;)

