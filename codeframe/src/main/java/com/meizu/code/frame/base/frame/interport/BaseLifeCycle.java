package com.meizu.code.frame.base.frame.interport;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 生命周期接口
 * <p>
 * Created by maxueming on 17-11-10.
 */

public abstract class BaseLifeCycle {

    protected abstract void onCreate(Bundle savedInstanceState);

    protected abstract void onActivityCreate();

    protected abstract boolean onCreateOptionsMenu(Menu menu);

    protected abstract boolean onPrepareOptionsMenu(Menu menu);

    protected abstract boolean onOptionsItemSelected(MenuItem item);

    protected abstract void onOptionsMenuClosed(Menu menu);

    protected abstract void onStart();

    protected abstract void onRestart();

    protected abstract void onResume();

    protected abstract void onPause();

    protected abstract void onStop();

    protected abstract void onDestroy();
}
