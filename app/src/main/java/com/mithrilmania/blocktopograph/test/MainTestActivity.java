package com.mithrilmania.blocktopograph.test;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.databinding.ActivityMainTestBinding;
import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;
import com.mithrilmania.blocktopograph.util.ConvertUtil;
import com.mithrilmania.blocktopograph.util.IoUtil;
import com.mithrilmania.blocktopograph.util.McUtil;

import java.io.File;
import java.io.Serializable;

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
}