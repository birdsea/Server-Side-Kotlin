package io.github.birdsea.ServerSideKotlin.component

/**
 * DbManagerクラス
 */

import io.github.birdsea.ServerSideKotlin.bean.Film
import org.springframework.stereotype.Component
import java.sql.*
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.starProjectedType

@Component
object DbManager {
    //接続文字列
    val url = "jdbc:postgresql://localhost:5432/dvdrental"
    val user = "postgres"
    val password = "postgres"

    private var conn: Connection? = null

    /**
     * トランザクション開始
     */
    fun startTransaction() {
        conn = DriverManager.getConnection(url, user, password)
        //自動コミットOFF
        conn!!.autoCommit = false
    }

    /**
     * トランザクション終了
     */
    fun endTransaction() {
        conn!!.close()
    }

    fun selectFilm(): MutableList<Film> {
        var stmt: Statement? = null
        var rset: ResultSet? = null

        val result = mutableListOf<Film>()

        try {
            //SELECT文の実行
            stmt = conn!!.createStatement()
            var sql = "SELECT * FROM FILM ORDER BY FILM_ID"
            rset = stmt!!.executeQuery(sql)

            //SELECT結果の受け取り
            while (rset!!.next()) {
                val meta = rset.metaData
                val film = Film()
                for (i in 1..meta.columnCount) {
                    Film::class.memberProperties.filter { it.visibility == KVisibility.PUBLIC }
                            .filter { it.returnType.isSubtypeOf(String::class.starProjectedType) }
                            .filterIsInstance<KMutableProperty<*>>()
                            .forEach {
                                if (it.name.equals(meta.getColumnName(i))) {
                                    when (meta.getColumnType(i)) {
                                        java.sql.Types.DATE, java.sql.Types.TIME -> it.setter.call(film, rset.getDate(meta.getColumnName(i)))
                                        java.sql.Types.TIMESTAMP -> it.setter.call(film, rset.getTimestamp(meta.getColumnName(i)))
                                        else -> it.setter.call(film, rset.getString(meta.getColumnName(i)))
                                    }
                                }
                            }
                }
                result += film
            }

        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            try {
                rset!!.close()
                stmt!!.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }
        return result
    }
}
