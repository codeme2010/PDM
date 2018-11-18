package com.codeme.pdm

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v4.widget.CursorAdapter
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment2.*

class Fragment2 : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var mMainView: View? = null
    private var adapter: SimpleCursorAdapter? = null
    internal var selection: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainView = activity!!.layoutInflater.inflate(R.layout.fragment2, activity!!.container, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mMainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uiBindFrom = arrayOf("pronum", "proname", "model", "suppname", "suppnum", "designer", "time")
        val uiBindTo = intArrayOf(R.id.pronum, R.id.proname, R.id.model, R.id.suppname, R.id.suppnum, R.id.designer, R.id.time)
        loaderManager.initLoader(2, null, this)
        adapter = SimpleCursorAdapter(
                context, R.layout.item2, null, uiBindFrom, uiBindTo,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        lv2.adapter = adapter

        et2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str0 = s.toString()
                if (str0.isEmpty()) {
                    selection = null
                } else if (!str0.endsWith(" ")) {
                    val str = str0.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    selection = " (pronum like '%" + str[0] +
                            "%' or proname like '%" + str[0] +
                            "%' or suppnum like '%" + str[0] +
                            "%' or suppname like '%" + str[0] +
                            "%' or model like '%" + str[0] + "%')"
                    for (i in 1..str.size - 1) {
                        selection = selection + " and ( pronum like '%" + str[i] +
                                "%' or proname like '%" + str[i] +
                                "%' or suppnum like '%" + str[i] +
                                "%' or suppname like '%" + str[i] +
                                "%' or model like '%" + str[i] +
                                "%')"
                    }
                }
                loaderManager.restartLoader(2, null, this@Fragment2)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    fun sch(id: String) {
        et2.setText(id)
        selection = "suppnum = '${id.trim()}'"
        loaderManager.restartLoader(2, null, this@Fragment2)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf("_id", "pronum", "proname", "model", "suppname", "suppnum", "designer", "time")
        val uri = Uri.withAppendedPath(App.CONTENT_URI, App.Table_product)
        return CursorLoader(context!!, uri, projection, selection, null, "ROWID limit 200")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapter?.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter?.swapCursor(null)
    }
}

