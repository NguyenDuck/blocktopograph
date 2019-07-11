package com.mithrilmania.blocktopograph;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.litl.leveldb.DB;
import com.mithrilmania.blocktopograph.databinding.ActivityCreateWorldBinding;
import com.mithrilmania.blocktopograph.flat.EditFlatFragment;
import com.mithrilmania.blocktopograph.flat.FlatLayers;
import com.mithrilmania.blocktopograph.flat.Layer;
import com.mithrilmania.blocktopograph.map.Biome;
import com.mithrilmania.blocktopograph.map.KnownBlock;
import com.mithrilmania.blocktopograph.nbt.InventoryHolder;
import com.mithrilmania.blocktopograph.nbt.ItemTag;
import com.mithrilmania.blocktopograph.nbt.Keys;
import com.mithrilmania.blocktopograph.nbt.convert.LevelDataConverter;
import com.mithrilmania.blocktopograph.nbt.convert.NBTConstants;
import com.mithrilmania.blocktopograph.nbt.convert.NBTInputStream;
import com.mithrilmania.blocktopograph.nbt.convert.NBTOutputStream;
import com.mithrilmania.blocktopograph.nbt.tags.CompoundTag;
import com.mithrilmania.blocktopograph.nbt.tags.LongTag;
import com.mithrilmania.blocktopograph.nbt.tags.StringTag;
import com.mithrilmania.blocktopograph.nbt.tags.Tag;
import com.mithrilmania.blocktopograph.util.ConvertUtil;
import com.mithrilmania.blocktopograph.util.IoUtil;
import com.mithrilmania.blocktopograph.util.McUtil;
import com.mithrilmania.blocktopograph.util.UiUtil;
import com.tomergoldst.tooltips.ToolTip;
import com.tomergoldst.tooltips.ToolTipsManager;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.content.res.AssetManager.ACCESS_STREAMING;


public final class CreateWorldActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_PICK_BIOME = 2012;
    private ActivityCreateWorldBinding mBinding;
    private ToolTipsManager mToolTipsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_world);
        //mBinding.scroll.post(()->mBinding.scroll.doOverScroll());
        mToolTipsManager = new ToolTipsManager();
        Log.logFirebaseEvent(this, Log.CustomFirebaseEvent.CREATE_WORLD_OPEN);

        setBiomeToView(Biome.JUNGLE);
    }

    public void onClickPositiveButton(View view) {
        new CreateWorldTask(this).execute();
    }

    public void onClickHelpLayers(View view) {
        View anchorView = (View) view.getParent();
        if (mToolTipsManager.findAndDismiss(anchorView)) return;
        ToolTip.Builder builder = new ToolTip.Builder(
                this, anchorView, mBinding.helpFrameLayers,
                getString(R.string.edit_flat_help), ToolTip.POSITION_ABOVE)
                .setAlign(ToolTip.ALIGN_LEFT);
        mToolTipsManager.show(builder.build());
    }

    public void onClickChangeBiome(View view) {
        startActivityForResult(new Intent(this, BiomeSelectDialog.class), REQUEST_CODE_PICK_BIOME);
    }

    private void setBiomeToView(@NotNull Biome biome) {
        UiUtil.blendBlockColor(mBinding.biomeView.root, biome);
        mBinding.biomeView.setBiome(biome);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_PICK_BIOME) {
            if (resultCode == RESULT_OK && data != null) {
                Serializable ser = data.getSerializableExtra(BiomeSelectDialog.KEY_BIOME);
                if (ser instanceof Biome) setBiomeToView((Biome) ser);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static class CreateWorldTask extends AsyncTask<Void, Void, Boolean> {

        private final WeakReference<CreateWorldActivity> thiz;
        private boolean canProceed;
        private boolean mIsVanillaFlat;
        private String mName;
        private int mBiome;
        private int mVersion;
        private List<Layer> layers;

        private CreateWorldTask(CreateWorldActivity thiz) {
            this.thiz = new WeakReference<>(thiz);
        }

        @Override
        protected void onPreExecute() {
            CreateWorldActivity activity = thiz.get();
            if (activity == null) {
                canProceed = false;
                return;
            }
            int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(activity, R.string.no_read_write_access, Toast.LENGTH_SHORT).show();
                canProceed = false;
                return;
            }
            canProceed = true;
            mName = activity.mBinding.worldName.getText().toString();
            mVersion = activity.mBinding.version.getCheckedRadioButtonId();
            Biome biome = activity.mBinding.biomeView.getBiome();
            mBiome = biome == null ? 21 : biome.id;
            Fragment fr = activity.getSupportFragmentManager().findFragmentById(R.id.frag_layers);
            EditFlatFragment frag = (EditFlatFragment) fr;
            assert frag != null;
            layers = frag.getResultLayers();
        }

        @Override
        @NonNull
        protected Boolean doInBackground(Void... voids) {

            // Check existing failure.
            if (!canProceed) return false;

            // Make sure activity available.
            CreateWorldActivity activity = thiz.get();
            if (activity == null) return false;

            // Make sure worlds dir exists.
            File worldsDir = McUtil.getMinecraftWorldsDir(Environment.getExternalStorageDirectory());
            if (!worldsDir.isDirectory()) {
                if (!worldsDir.mkdirs()) return false;
            }

            // Get name.
            String name = mName;
            if (name.isEmpty() || name.trim().isEmpty()) {
                name = activity.getString(R.string.create_world_default_name);
            }

            // Get biome.
            //

            // Get version.
            String verStr;
            if (mVersion == R.id.version_aqua) {
                verStr = "1_2_13";
            } else {
                verStr = "unknown";
            }

            // Get dir.
            String dirName = ConvertUtil.getLegalFileName(name) + "[" + verStr + "]";
            File wDir = new File(worldsDir, dirName);
            if (wDir.exists()) {
                int cnt = 0;
                do {
                    wDir = new File(worldsDir, dirName + '_' + cnt);
                } while (wDir.exists());
            }
            if (!wDir.mkdir()) return false;

            // Get dat.
            AssetManager ass = activity.getAssets();
            CompoundTag rootTag = null;
            try {
                InputStream is = ass.open("dats/" + verStr + ".dat", ACCESS_STREAMING);
                rootTag = LevelDataConverter.read(is);
            } catch (Exception e) {
                Log.d(this, e);
            }
            if (rootTag == null) return false;

            // Modify dat.
            {
                // Name.
                Tag tag = rootTag.getChildTagByKey(Keys.LEVEL_NAME);
                StringTag stag = (StringTag) tag;
                stag.setValue(name);

                // Layers.
                tag = rootTag.getChildTagByKey(Keys.FLAT_WORLD_LAYERS);
                int lsize = layers.size();
                if (lsize != 4) mIsVanillaFlat = false;
                else {
                    Layer ltest = layers.get(0);
                    mIsVanillaFlat = ltest.block == KnownBlock.B_31_2_TALLGRASS_GRASS && ltest.amount == 1
                            && (ltest = layers.get(1)).block == KnownBlock.B_2_0_GRASS && ltest.amount == 1
                            && (ltest = layers.get(2)).block == KnownBlock.B_3_0_DIRT && ltest.amount == 29
                            && (ltest = layers.get(3)).block == KnownBlock.B_7_0_BEDROCK && ltest.amount == 1;
                }
                Layer[] alayers = new Layer[lsize < 3 ? 3 : lsize];
                for (int i = 0; i < lsize; i++) {
                    alayers[i] = layers.get(lsize - i - 1);
                }
                // Actually there have to be at least 3 layers, but we don't need to inform users.
                for (int i = lsize; i < 3; i++) {
                    // Fill with air of no amount.
                    Layer air = new Layer();
                    air.amount = 0;
                    alayers[i] = air;
                }
                FlatLayers flatLayers = FlatLayers.createNew(mBiome, alayers);
                stag = (StringTag) tag;
                stag.setValue(flatLayers.write());

                // Time.
                tag = rootTag.getChildTagByKey(Keys.LAST_PLAYED);
                LongTag ltag = (LongTag) tag;
                ltag.setValue(System.currentTimeMillis() / 1000);
            }

            // Write dat.
            try {
                LevelDataConverter.write(rootTag, McUtil.getLevelDatFile(wDir));
            } catch (IOException e) {
                Log.d(this, e);
                return false;
            }

            // Write levelname.
            if (!IoUtil.writeTextFile(McUtil.getLevelNameFile(wDir), name)) return false;

            // Write ad.
            IoUtil.writeTextFile(
                    new File(wDir, activity.getString(R.string.create_world_extra_file_name)),
                    activity.getString(R.string.create_world_extra_file_text));

            // Write player.
            try {
                // Load template from assets.
                NBTInputStream nis = new NBTInputStream(
                        ass.open("dats/" + verStr + "-player.dat", ACCESS_STREAMING),
                        false, true);
                CompoundTag playerTag = (CompoundTag) nis.readTag();

                // Modify inventory.
                InventoryHolder inv = InventoryHolder.readFromPlayer(playerTag);
                assert inv != null;

                // Create a book in the first slot.
                InventoryHolder.Item item = inv.getItemOfSlot((short) 0);
                assert item != null;
                //item.setName("minecraft:written_book");
                item.setId((short) 387);
                item.setCount((byte) 1);
                item.setDamage((short) 0);

                // Its nbt data.
                ItemTag itag = item.getItemTag();
                assert itag != null;
                itag.setId(-19260817L);
                itag.setGeneration(0);
                itag.setAuthor("Blocktopograph");
                itag.setTitle(activity.getString(R.string.create_world_book_title));
                //itag.setXuid("2535445286243008");
                CompoundTag page = itag.getPage(0);
                assert page != null;
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ItemTag.setPageText(page, activity.getString(
                        R.string.create_world_book_page_0, dateFormat.format(new Date())));
                ItemTag.setPageUrl(page, "https://play.google.com/store/apps/details?id=rbq2012.blocktopograph");
                {
                    page = itag.getPage(1);
                    assert page != null;
                    StringBuilder pager = new StringBuilder();
                    pager.append(activity.getString(R.string.create_world_book_recipe_header));
                    int lines = layers.size();
                    boolean tooManyLines;
                    if (lines > 8) {
                        lines = 7;
                        tooManyLines = true;
                    } else {
                        tooManyLines = false;
                        if (lines < 5) pager.append('\n');
                    }
                    for (int i = 0; i < lines; i++) {
                        Layer layer = layers.get(i);
                        int maxlen = 11 - Integer.toString(layer.amount).length();
                        String nam;
                        if (maxlen <= 0) nam = "";
                        else {
                            String namo = layer.block.str;
                            if (namo.length() >= maxlen) nam = namo.substring(0, maxlen);
                            else nam = namo;
                        }
                        pager.append(activity.getString(
                                R.string.create_world_book_recipe_format, layer.amount, nam));
                    }
                    if (tooManyLines)
                        pager.append(activity.getString(R.string.create_world_book_recipe_many));
                    ItemTag.setPageText(page, pager.toString());
                }
                // Write to DB.
                DB db = new DB(new File(wDir, "db"));
                db.open();
                // Size enough for the 5KB data.
                ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
                NBTOutputStream nos = new NBTOutputStream(baos, false, true);
                nos.writeTag(playerTag);
                nos.close();
                db.put("~local_player".getBytes(NBTConstants.CHARSET), baos.toByteArray());
                db.close();
            } catch (Exception e) {
                Log.d(this, e);
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            assert aBoolean != null;
            Activity activity = thiz.get();
            if (activity == null) return;
            Bundle params = new Bundle();
            int type;
            if (aBoolean) {
                if (mIsVanillaFlat) type = 1;
                else type = 3 + layers.size();
            } else type = 0;
            params.putInt(Log.ANA_PARAM_CREATE_WORLD_TYPE, type);
            Log.logFirebaseEvent(activity, Log.CustomFirebaseEvent.CREATE_WORLD_SAVE, params);
            if (aBoolean) {
                Toast.makeText(activity,
                        activity.getString(R.string.general_done),
                        Toast.LENGTH_SHORT).show();
                activity.finish();
            } else {
                Toast.makeText(activity,
                        activity.getString(R.string.general_failed)
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }
}
