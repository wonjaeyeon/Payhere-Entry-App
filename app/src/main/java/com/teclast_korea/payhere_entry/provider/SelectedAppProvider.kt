package com.teclast_korea.payhere_entry.provider


import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.teclast_korea.payhere_entry.data.data_source.local.db.AppDatabase
import com.teclast_korea.payhere_entry.data.data_source.local.db.di.AppDatabaseEntryPoint
import com.teclast_korea.payhere_entry.data.data_source.local.db.selected_app.SelectedAppEntity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.runBlocking

class SelectedAppProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.teclast_korea.payhere_entry.provider"
        private const val PATH = "selected_app"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH")

        private const val CODE_SELECTED_APP = 1

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, PATH, CODE_SELECTED_APP)
        }
    }

    private lateinit var db: AppDatabase

    override fun onCreate(): Boolean {
        // Hilt EntryPoint를 통해 AppDatabase 인스턴스를 가져옴
        val appContext = context?.applicationContext ?: return false
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            AppDatabaseEntryPoint::class.java
        )
        db = hiltEntryPoint.provideAppDatabase()

        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when (uriMatcher.match(uri)) {
            CODE_SELECTED_APP -> {
                if (values == null) return null
                val packageName = values.getAsString("packageName") ?: return null

                // Room DB에 저장
                runBlocking {
                    val entity = SelectedAppEntity(packageName = packageName)
                    db.selectedAppDao().setSelectedApp(entity)
                }

                // 성공적으로 삽입했으면 생성된 Uri를 반환 (id=1 가정)
                Uri.withAppendedPath(CONTENT_URI, "1")
            }
            else -> null
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        // 필요하면 구현. 예: "현재 선택된 앱이 뭔지 조회"
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?,
        selection: String?, selectionArgs: Array<out String>?
    ): Int {
        // 필요하다면 구현
        return 0
    }

    override fun delete(
        uri: Uri, selection: String?, selectionArgs: Array<out String>?
    ): Int {
        // 필요하면 구현
        return 0
    }

    override fun getType(uri: Uri): String? = null
}