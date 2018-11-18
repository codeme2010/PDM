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
import android.widget.AdapterView
import android.widget.SimpleCursorAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment1.*
import kotlinx.android.synthetic.main.item1.view.*

class Fragment1 : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var mMainView: View? = null
    private var adapter: SimpleCursorAdapter? = null
    internal var selection: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainView = activity!!.layoutInflater.inflate(R.layout.fragment1, activity!!.container, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        /*val p = mMainView.parent as ViewGroup
        p?.removeAllViewsInLayout()*/
        return mMainView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uiBindFrom0 = arrayOf("suppnum", "suppname")
        val uiBindTo0 = intArrayOf(R.id.suppnum1, R.id.suppname1)
        loaderManager.initLoader(1, null, this)
        adapter = SimpleCursorAdapter(
                context, R.layout.item1, null, uiBindFrom0, uiBindTo0,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER)
        lv1.adapter = adapter
        et1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = s.toString()
                selection = "((suppname like '%$str%') or (suppnum like '%$str%'))"
                loaderManager.restartLoader(1, null, this@Fragment1)
            }

            override fun afterTextChanged(s: Editable) {}
        })
        lv1.onItemClickListener = AdapterView.OnItemClickListener { _, view, _, _ ->
            App.spa.sch(2, view.suppnum1.text.toString() + " ")
            (activity as MainActivity).container.currentItem = 1
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val projection = arrayOf("_id", "suppnum", "suppname")
        val uri = Uri.withAppendedPath(App.CONTENT_URI, App.Table_supp)
        return CursorLoader(context!!, uri, projection, selection, null, "ROWID limit 200")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapter?.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter?.swapCursor(null)
    }
}

