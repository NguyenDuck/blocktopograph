package com.mithrilmania.blocktopograph.test;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.litl.leveldb.DB;
import com.litl.leveldb.Iterator;
import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.World;
import com.mithrilmania.blocktopograph.WorldData;
import com.mithrilmania.blocktopograph.block.Block;
import com.mithrilmania.blocktopograph.block.BlockRegistry;
import com.mithrilmania.blocktopograph.block.KnownBlockRepr;
import com.mithrilmania.blocktopograph.chunk.Chunk;
import com.mithrilmania.blocktopograph.chunk.ChunkTag;
import com.mithrilmania.blocktopograph.chunk.Version;
import com.mithrilmania.blocktopograph.databinding.ActivityMainTestBinding;
import com.mithrilmania.blocktopograph.map.Dimension;
import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;
import com.mithrilmania.blocktopograph.util.ConvertUtil;
import com.mithrilmania.blocktopograph.util.IoUtil;
import com.mithrilmania.blocktopograph.util.McUtil;
import com.mithrilmania.blocktopograph.util.UiUtil;

import java.io.File;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public final class MainTestActivity extends AppCompatActivity {

    private ActivityMainTestBinding mBinding;
    private World mWorld;

    /**
     * https://stackoverflow.com/a/25659067/9399618
     *
     * @param data    123
     * @param pattern 123
     * @return 123
     */
    public static boolean arrContains(byte[] data, byte[] pattern, int[] failure) {

        int j = 0;

        for (byte datum : data) {
            while (j > 0 && pattern[j] != datum) {
                j = failure[j - 1];
            }
            if (pattern[j] == datum) {
                j++;
            }
            if (j == pattern.length) {
                return true;//i - pattern.length + 1;
            }
        }
        return false;//-1;
    }

    /**
     * Computes the failure function using a boot-strapping process,
     * where the pattern is matched against itself.
     */
    private static int[] computeFailure(byte[] pattern) {
        int[] failure = new int[pattern.length];

        int j = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (j > 0 && pattern[j] != pattern[i]) {
                j = failure[j - 1];
            }
            if (pattern[j] == pattern[i]) {
                j++;
            }
            failure[i] = j;
        }

        return failure;
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
        //mBinding.fabMenuGenerateAllBlocks.setOnClickListener(this::onClickGenAllBlocks);
        //mBinding.fabMenuAnalyzeAllBlocks.setOnClickListener(this::onClickAnaAllBlocks);
        //mBinding.fabMenuGenCodeAllBlocksState.setOnClickListener(this::onClickGenCodeAllBlocksState);
        mBinding.setPath(file.getPath());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(World.ARG_WORLD_SERIALIZED, mWorld);
    }

    private byte[] readItem() {
        return readItem(getDbKey());
    }

    private byte[] readItem(byte[] key) {
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
        byte[] pattern = getDbKey();
        WorldData wdata = mWorld.getWorldData();
        try {
            wdata.openDB();
            List<String> keys = new ArrayList<>();
            List<Boolean> keyTypeText = new ArrayList<>();
            List<byte[]> originalKeys = new ArrayList<>();
            Iterator iter = wdata.db.iterator();
            int[] failure = computeFailure(pattern);
            for (iter.seekToFirst(); iter.isValid(); iter.next()) {
                byte[] key = iter.getKey();
                if (arrContains(key, pattern, failure)) {
                    String str = null;
                    boolean isText = false;
                    if (mBinding.rgForm.getCheckedRadioButtonId() == R.id.rb_form_text) {
                        try {
                            str = new String(key, NBTConstants.CHARSET);
                            isText = true;
                        } catch (Exception ignored) {
                        }
                    }
                    if (str == null)
                        str = ConvertUtil.bytesToHexStr(key);
                    keys.add(str);
                    originalKeys.add(key);
                    keyTypeText.add(isText);
                }
            }
            iter.close();
            wdata.closeDB();
            String[] keyArr = new String[keys.size()];
            keys.toArray(keyArr);
            AlertDialog dia = new AlertDialog.Builder(this).setItems(keyArr, (di, i) -> {
                mBinding.searchBar.setText(keyArr[i]);
                if (keyTypeText.get(i))
                    mBinding.rbFormText.setChecked(true);
                else
                    mBinding.rbFormHex.setChecked(true);
                showData(readItem(originalKeys.get(i)));
            }).create();
            dia.show();
        } catch (Exception e) {
            Log.d(this, e);
            printStackTraceInDialog(e);
        }
        try {
            wdata.closeDB();
        } catch (WorldData.WorldDBException e) {
            Log.d(this, e);
        }
    }

    private void showData(byte[] data) {
        new AlertDialog.Builder(this)
                .setMessage(ConvertUtil.bytesToHexStr(data))
                .show();
    }

    private void printStackTraceInDialog(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        new AlertDialog.Builder(this).setMessage(sw.toString()).create().show();
    }

    public void onClickOpen(View view) {
        showData(readItem());
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

    @SuppressWarnings("unchecked")
    private void onClickFixLdb(View view) {
        new ForegroundTask(this).execute(() -> {
            WorldData worldData = mWorld.getWorldData();
            try {
                worldData.closeDB();
            } catch (WorldData.WorldDBException e) {
                Log.d(this, e);
            }
            return DB.fixLdb(worldData.db.getPath().getAbsolutePath());
        });
    }

    @SuppressWarnings("unchecked")
    private void onClickGenAllBlocks(View view) {
        new ForegroundTask(this).execute(() -> {
            WorldData worldData = mWorld.getWorldData();
            BlockRegistry registry = worldData.mBlockRegistry;
            int pos = 0;
            for (KnownBlockRepr block : KnownBlockRepr.values()) {
                if (pos % 16 == 0)
                    worldData.removeChunkData(pos / 16, 0, ChunkTag.TERRAIN, Dimension.OVERWORLD, (byte) 0, true);
                String pot = "------------X------------" +
                        "------XXX--XOX--XXX------" +
                        "XXXXXX---XX-?-XX---XXXXXX";
                Chunk chunk = worldData.getChunk(pos / 16, 0, Dimension.OVERWORLD, true, Version.V1_2_PLUS);
                int i = 0;
                for (int y = 0; y < 3; y++)
                    for (int x = 0; x < 5; x++)
                        for (int z = 0; z < 5; z++) {
                            Block blk;
                            switch (pot.charAt(i)) {
                                case 'X':
                                    blk = registry.createBlock(KnownBlockRepr.B_42_0_IRON_BLOCK);
                                    break;
                                case 'O':
                                    blk = registry.createBlock(KnownBlockRepr.B_3_0_DIRT);
                                    break;
                                case '?':
                                    blk = registry.createBlock(block);
                                    break;
                                default:
                                    blk = null;
                            }
                            if (blk != null)
                                chunk.setBlock(pos % 16 + 1 + x, 12 + y, 3 + z, 0, blk);
                            i++;
                        }
                for (int j = 0; j < 8; j++) {
                    chunk.setBlock(pos % 16 + j, 17, 9, 0,
                            registry.createBlock(KnownBlockRepr.B_12_0_SAND_DEFAULT));
                    chunk.setBlock(pos % 16 + j, 0, 9, 0,
                            registry.createBlock(KnownBlockRepr.B_42_0_IRON_BLOCK));
                }
                pos += 8;
            }
            worldData.resetCache();
            worldData.closeDB();
            return "meow";
        });
    }

    @SuppressWarnings("unchecked")
    private void onClickAnaAllBlocks(View view) {
//        new ForegroundTask(this).execute(() -> {
//            WorldData worldData = mWorld.getWorldData();
//            int pos = 0, valids = 0, invalids = 0, offs = 0;
//            StringBuilder sb = new StringBuilder();
//            CompoundTag[] tags = null;
//            for (KnownBlockRepr block : KnownBlockRepr.values()) {
//                Chunk chunk = worldData.getChunk(pos / 16, 0, Dimension.OVERWORLD, true, Version.V1_2_PLUS);
//                int ind;
//                if (pos % 16 == 0) {
//                    tags = ((BedrockChunk) chunk).tempGetSubChunk().tempGetPalettes(3, 14, 5);
//                    ind = 0;
//                } else ind = 1;
//                String name = ((StringTag) tags[ind].getChildTagByKey("name")).getValue().substring(10);
//                sb.append(block.identifier).append(":").append(block.subId).append("->").append(name).append(":");
//                CompoundTag states = ((CompoundTag) tags[ind].getChildTagByKey("states"));
//                sb.append("[");
//                ArrayList<Tag> value = states.getValue();
//                for (int i = 0; i < value.size(); i++) {
//                    Tag tag = value.get(i);
//                    sb.append(tag.getType()).append(":").append(tag.getIdentifier()).append("=");
//                    switch (tag.getType()) {
//                        case INT:
//                            sb.append(((IntTag) tag).getValue());
//                            break;
//                        case BYTE:
//                            sb.append(((ByteTag) tag).getValue());
//                            break;
//                        case SHORT:
//                            sb.append(((ShortTag) tag).getValue());
//                            break;
//                        case STRING:
//                            sb.append(((StringTag) tag).getValue());
//                            break;
//                    }
//                    if (i != value.size() - 1) sb.append(",");
//                }
//                sb.append("]\n");
//                pos += 8;
//                offs++;
//            }
//            worldData.resetCache();
//            worldData.closeDB();
//            IoUtil.writeTextFile(new File(McUtil.getBtgTestDir(Environment.getExternalStorageDirectory()), "blks.txt"), sb.toString());
//            return "meow";
//        });
    }

    @SuppressWarnings("unchecked")
    private void onClickGenCodeAllBlocksState(View view) {
//        new ForegroundTask(this).execute(() -> {
//            WorldData worldData = mWorld.getWorldData();
//            int pos = 0, valids = 0, invalids = 0;
//            StringBuilder sb = new StringBuilder();
//            CompoundTag[] tags = null;
//            for (KnownBlockRepr block : KnownBlockRepr.values()) {
//                Chunk chunk = worldData.getChunk(pos / 16, 0, Dimension.OVERWORLD, true, Version.V1_2_PLUS);
//                int ind;
//                if (pos % 16 == 0) {
//                    tags = ((BedrockChunk) chunk).tempGetSubChunk().tempGetPalettes(3, 14, 5);
//                    ind = 0;
//                } else ind = 1;
//                String name = ((StringTag) tags[ind].getChildTagByKey("name")).getValue().substring(10);
//                if (name.equals(block.identifier)) {
//                    CompoundTag states = ((CompoundTag) tags[ind].getChildTagByKey("states"));
//                    sb.append("new BlockStateBuilder()");
//                    Object[] value = states.getValue().toArray();
//                    Arrays.sort(value, (o1, o2) -> {
//                        String n1 = ((Tag) o1).getIdentifier();
//                        String n2 = ((Tag) o2).getIdentifier();
//                        if (n1.contains("color"))
//                            return -1;
//                        if (n2.contains("color"))
//                            return 1;
//                        if (n1.contains("type"))
//                            return -1;
//                        if (n2.contains("type"))
//                            return 1;
//                        if (n1.contains("direction"))
//                            return -1;
//                        if (n2.contains("direction"))
//                            return 1;
//                        return 0;
//                    });
//                    for (int i = 0; i < value.length; i++) {
//                        Tag tag = (Tag) value[i];
//                        String tagName = tag.getIdentifier();
//                        switch (tag.getType()) {
//                            case INT:
//                                sb.append(".addInt(\"").append(tagName).append("\", ");
//                                sb.append(((IntTag) tag).getValue());
//                                break;
//                            case BYTE:
//                                sb.append(".addByte(\"").append(tagName).append("\", (byte)");
//                                sb.append(((ByteTag) tag).getValue());
//                                break;
//                            case SHORT:
//                                sb.append(".addShort(\"").append(tagName).append("\", (short)");
//                                sb.append(((ShortTag) tag).getValue());
//                                break;
//                            case STRING:
//                                sb.append(".addProperty(\"").append(tagName).append("\", \"");
//                                sb.append(((StringTag) tag).getValue()).append("\"");
//                                break;
//                        }
//                        sb.append(")");
//                    }
//                    sb.append(".build()\n");
//                    valids++;
//                } else {
//                    sb.append("new CompoundTag(\"\", new ArrayList())\n");
//                    invalids++;
//                }
//                pos += 8;
//            }
//            worldData.resetCache();
//            worldData.closeDB();
//            IoUtil.writeTextFile(new File(McUtil.getBtgTestDir(Environment.getExternalStorageDirectory()), "blkStateCodes.txt"), sb.toString());
//            return "meow valids: " + valids + ", invalids: " + invalids;
//        });
    }

    private static class ForegroundTask extends AsyncTask<Callable<String>, Void, Void> {

        private WeakReference<Context> mContext;
        private WeakReference<Dialog> mDialog;
        private StringBuilder result;

        ForegroundTask(Context context) {
            mContext = new WeakReference<>(context);
            result = new StringBuilder();
        }

        @Override
        protected void onPreExecute() {
            Context context = mContext.get();
            if (context == null) return;
            AlertDialog dialog = UiUtil.buildProgressWaitDialog(context, R.string.general_please_wait, null);
            dialog.show();
            mDialog = new WeakReference<>(dialog);
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(Callable<String>... params) {
            for (Callable<String> callable : params) {
                try {
                    result.append(callable.call());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
            Context context = mContext.get();
            if (context != null) {
                new AlertDialog.Builder(context)
                        .setMessage(result.toString())
                        .create().show();
            }
        }
    }
}