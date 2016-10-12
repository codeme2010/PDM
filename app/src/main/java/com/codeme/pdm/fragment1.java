package com.codeme.pdm;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class fragment1 extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
	View mMainView;
	ListView lv;
	SimpleCursorAdapter adapter;
	String selection = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment1, (ViewGroup) getActivity().findViewById(R.id.container), false);
		lv = (ListView) mMainView.findViewById(R.id.lv1);
		String[] uiBindFrom0 = {"suppnum", "suppname"};
		int[] uiBindTo0 = {R.id.suppnum, R.id.suppname};
		getLoaderManager().initLoader(1, null, this);
		adapter = new SimpleCursorAdapter(
				getContext(), R.layout.item1,
				null, uiBindFrom0, uiBindTo0,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		lv.setAdapter(adapter);
		EditText et = (EditText)mMainView.findViewById(R.id.et1);
		et.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				final String str = s.toString();
				selection = "((suppname like '%" + str + "%') or (suppnum like '%" + str + "%'))";
				getLoaderManager().restartLoader(1, null, fragment1.this);
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		ViewGroup p = (ViewGroup) mMainView.getParent();
		if (p != null) {
			p.removeAllViewsInLayout();
		}
		return mMainView;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {"_id","suppnum","suppname"};
		Uri uri = Uri.withAppendedPath(App.CONTENT_URI,App.Table_supp);
		return new CursorLoader(getContext(), uri, projection, selection, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
}

