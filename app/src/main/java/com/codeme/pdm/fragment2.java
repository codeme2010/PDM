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

public class fragment2 extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
	View mMainView;
	EditText et;
	ListView lv;
	SimpleCursorAdapter adapter;
	String selection = null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		mMainView = inflater.inflate(R.layout.fragment2, (ViewGroup)getActivity().findViewById(R.id.container), false);
		et = (EditText)mMainView.findViewById(R.id.et2);
		lv = (ListView)mMainView.findViewById(R.id.lv2);

		String[] uiBindFrom = {"pronum","proname","model","suppname","suppnum","designer","time"};
		int[] uiBindTo = {R.id.pronum,R.id.proname,R.id.model,R.id.suppname,R.id.suppnum,R.id.designer,R.id.time};
		getLoaderManager().initLoader(2, null, this);
		adapter = new SimpleCursorAdapter(
				getContext(), R.layout.item2,
				null, uiBindFrom, uiBindTo,
				CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		lv.setAdapter(adapter);

		et.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				final String str0 = s.toString();
				if (!String.valueOf(str0).endsWith(" ")) {
					String[] str = str0.split(" ");
					selection = " (pronum like '%" + str[0]
							+ "%' or proname like '%" + str[0]
							+ "%' or suppnum like '%" + str[0]
							+ "%' or suppname like '%" + str[0]
							+ "%' or model like '%" + str[0] + "%')";
					for (int i = 1; i < str.length; i++) {
						selection = selection + " and ( pronum like '%" + str[i]
								+ "%' or proname like '%" + str[i]
								+ "%' or suppnum like '%" + str[i]
								+ "%' or suppname like '%" + str[i]
								+ "%' or model like '%" + str[i]
								+ "%')";
					}
					getLoaderManager().restartLoader(2, null, fragment2.this);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
		String[] projection = {"_id","pronum","proname","model","suppname","suppnum","designer","time"};
		Uri uri = Uri.withAppendedPath(App.CONTENT_URI,App.Table_product);
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

