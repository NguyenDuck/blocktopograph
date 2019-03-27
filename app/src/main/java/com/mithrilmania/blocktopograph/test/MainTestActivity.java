package com.mithrilmania.blocktopograph.test;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.litl.leveldb.DB;
import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.databinding.ActivityMainTestBinding;
import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;
import com.mithrilmania.blocktopograph.util.ConvertUtil;
import com.mithrilmania.blocktopograph.util.IoUtil;
import com.mithrilmania.blocktopograph.util.McUtil;
import com.mithrilmania.blocktopograph.util.UiUtil;

import java.io.File;
import java.io.Serializable;
import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public final class MainTestActivity extends AppCompatActivity {

    private ActivityMainTestBinding mBinding;
    private World mWorld;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_test);

        if (savedInstanceState != null) {
            Serializable ser = savedInstanceState.getSerializable(World.ARG_WORLD_SERIALIZED);
            if (ser instanceof World) mWorld = (World) ser;
        }
        if (mWorld == null) {
            Intent intent = getIntent();
            if (intent != null) {
                Serializable ser = intent.getSerializableExtra(World.ARG_WORLD_SERIALIZED);
                if (ser instanceof World) mWorld = (World) ser;
            }
            if (mWorld == null) {
                finish();
                return;
            }
        }

        try {
            mWorld.getWorldData().load();
        } catch (WorldData.WorldDataLoadException e) {
            Log.d(this, e);
            finish();
            return;
        }
        File file = Environment.getExternalStorageDirectory();
        file = McUtil.getBtgTestDir(file);
        mBinding.fabMenuFixLdb.setOnClickListener(this::onClickFixLdb);
        mBinding.setPath(file.getPath());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(World.ARG_WORLD_SERIALIZED, mWorld);
    }

    private byte[] getDbKey() {
        byte[] key;
        String text = mBinding.searchBar.getText().toString();
        switch (mBinding.rgForm.getCheckedRadioButtonId()) {
            case R.id.rb_form_text:
                key = text.getBytes(NBTConstants.CHARSET);
                break;
            case R.id.rb_form_hex: {
                key = ConvertUtil.hexStringToBytes(text);
                break;
            }
            default:
                key = null;
        }
        return key;
    }

    private byte[] readItem() {
        byte[] key = getDbKey();
        byte[] ret;
        WorldData wdata = mWorld.getWorldData();
        try {
            wdata.openDB();
            ret = wdata.db.get(key);
            wdata.closeDB();
        } catch (Exception e) {
            Log.d(this, e);
            ret = null;
        }
        try {
            wdata.closeDB();
        } catch (WorldData.WorldDBException e) {
            Log.d(this, e);
        }
        return ret;
    }

    public void onClickSearch(View view) {
    }

    public void onClickOpen(View view) {
    }

    public void onClickExport(View view) {
        IoUtil.Errno errno;
        flow:
        {

            byte[] item = readItem();

            if (!IoUtil.hasWritePermission(this)) {
                errno = IoUtil.Errno.PERMISSION_DENIED;
                break flow;
            }

            File dir = new File(mBinding.textPath.getText().toString());

            errno = IoUtil.makeSureDirIsDir(dir);
            if (IoUtil.Errno.OK != errno) {
                break flow;
            }

            String name = ConvertUtil.getLegalFileName(mBinding.searchBar.getText().toString());
            File out = IoUtil.getFileWithFirstAvailableName(dir, name, ".dat", "(", ")");
            if (out == null) {
                errno = IoUtil.Errno.UNKNOWN;
                break flow;
            }

            if (!IoUtil.writeBinaryFile(out, item)) errno = IoUtil.Errno.UNKNOWN;
        }
        if (errno == IoUtil.Errno.OK) Snackbar.make(getWindow().getDecorView(),
                R.string.general_done, Snackbar.LENGTH_SHORT).show();
        else Toast.makeText(this, errno.toString(), Toast.LENGTH_SHORT).show();
    }

    public void onClickFixLdb(View view) {
        new FixLdbTask(this, mWorld.getWorldData()).execute();
    }

    private static class FixLdbTask extends AsyncTask<Void, Void, Void> {

        private WeakReference<Context> mContext;
        private WeakReference<Dialog> mDialog;
        private WorldData mWorldData;
        private String result;

        FixLdbTask(Context context, WorldData worldData) {
            mContext = new WeakReference<>(context);
            mWorldData = worldData;
        }

        @Override
        protected void onPreExecute() {
            Context context = mContext.get();
            if (context == null) return;
            AlertDialog dialog = UiUtil.buildProgressWaitDialog(context, R.string.general_please_wait, null);
            dialog.show();
            mDialog = new WeakReference<>(dialog);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                mWorldData.closeDB();
            } catch (WorldData.WorldDBException e) {
                Log.d(this, e);
            }
            result = DB.fixLdb(mWorldData.db.getPath().getAbsolutePath());
            mWorldData = null;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Dialog dialog;
            if (mDialog != null && (dialog = mDialog.get()) != null) {
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e(this, e);
                }
            }
            Context context;
            if (result != null && (context = mContext.get()) != null) {
                new AlertDialog.Builder(context)
                        .setMessage(result)
                        .create().show();
            }
        }
    }
}