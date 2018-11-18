package com.codeme.pdm

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (openDatabase())
            App.db = SQLiteDatabase.openOrCreateDatabase(App.databaseFilename, null)

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(Fragment1())
        fragmentList.add(Fragment2())
        fragmentList.add(Fragment3())

        val titleList = ArrayList<String>()
        titleList.add("供应商")
        titleList.add("认可报告")
        titleList.add("产品清单")

        App.spa = SectionsPagerAdapter(supportFragmentManager, fragmentList, titleList)
        container.adapter = App.spa
        tabs.setupWithViewPager(container)
    }

    private fun openDatabase(): Boolean {
        App.DATABASE_PATH = "${this.filesDir}"
        App.databaseFilename = App.DATABASE_PATH + "/pdm.db"
        try {
            val dir = File(App.DATABASE_PATH)
            if (!dir.exists())
                dir.mkdir()
            if (!File(App.databaseFilename).exists()) {
                // 获得封装dictionary.db文件的InputStream对象
                val `is` = resources.openRawResource(R.raw.pdm)
                val fos = FileOutputStream(App.databaseFilename)
                val buffer = ByteArray(7168)
                var count: Int = `is`.read(buffer)
                // 开始复制dictionary.db文件
                while (count > 0) {
                    fos.write(buffer, 0, count)
                    count = `is`.read(buffer)
                }
                Toast.makeText(this, "数据库创建成功", Toast.LENGTH_LONG).show()
                fos.close()
                `is`.close()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "数据库创建错误：" + e.message,
                    Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
