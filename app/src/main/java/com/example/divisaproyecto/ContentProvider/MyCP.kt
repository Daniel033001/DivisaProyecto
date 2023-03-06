package com.example.divisaproyecto.ContentProvider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.divisaproyecto.MonedaDB.DB
import com.example.divisaproyecto.repository.MonedaApp
import com.example.divisaproyecto.repository.MonedaRepository

private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {

    addURI("com.example.divisaproyecto", "monedas", 1)

    addURI("com.example.divisaproyecto", "monedas/#", 2)

    addURI("com.example.divisaproyecto", "monedas/*", 3)
}


class MyCP: ContentProvider() {
    lateinit var repository: MonedaRepository
    lateinit var db: DB
    override fun onCreate(): Boolean {
        //TODO("Not yet implemented")
        repository =  (context as MonedaApp).repositoryMoneda
        db =  (context as MonedaApp).database
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        //TODO("Not yet implemented")
        var cursor: Cursor? = null

        when( sUriMatcher.match(p0)){
            //"content://com.example.proyectodivisa/monedas"
            //query / insert
            1 -> {
                cursor = db.daoMoneda().getAllCursor()

            }
            //"content://com.example.proyectodivisa/monedas/*"
            //query
            2 -> {

            }
            //"content://com.example.proyectodivisa/monedas/#"
            //query / update  /  delete
            3 -> {

            }
            else -> {

            }
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }
}