package io.vn.nguyenduck.blocktopograph.world;

import static io.vn.nguyenduck.blocktopograph.Constants.BOGGER;
import static io.vn.nguyenduck.blocktopograph.Constants.WORLD_LEVELNAME_FILE;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.badlogic.gdx.utils.LittleEndianInputStream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.logging.Level;

import de.piegames.nbt.Tag;
import io.vn.nguyenduck.blocktopograph.nbt.NBTInputStream;

public class WorldPreLoader {

    public final File path;
    public File icon;
    private File data;
    private NBTInputStream dataStream;
    private Tag<?> tag;

    public WorldPreLoader(String path) {
        this.path = new File(path);
        update();
    }

    public void update() {
        icon = fetchIcon();
        data = fetchWorldData();
    }

    @Nullable
    private File fetchIcon() {
        if (icon != null) return icon;
        var i = Arrays.stream(path.listFiles((v, n) -> n.startsWith("world_icon"))).findFirst();
        return i.orElse(null);
    }

    @Nullable
    private File fetchWorldData() {
        if (data != null) return data;
        var i = Arrays.stream(path.listFiles((v, n) -> n.equals("level.dat"))).findFirst();
        return i.orElse(null);
    }

    @Nullable
    public Drawable getIconDrawable() {
        File iconFile = fetchIcon();
        if (iconFile == null) return null;
        Bitmap icon = BitmapFactory.decodeFile(iconFile.getPath());
        return new BitmapDrawable(Resources.getSystem(), icon);
    }

    public String getName() {
        File levelNameFile = new File(path, WORLD_LEVELNAME_FILE);
        if (!levelNameFile.exists()) return path.getName();
        try {
            var reader = new BufferedReader(new InputStreamReader(new FileInputStream(levelNameFile)));
            return reader.readLine();
        } catch (Exception e) {
            BOGGER.log(Level.SEVERE, "", e);
        }
        return "";
    }

    public Tag<?> getData() {
        if (tag == null) {
            try {
                FileInputStream is = new FileInputStream(data);
                var reader = new LittleEndianInputStream(is);
                reader.readByte(); // header version
                is.skip(3);
                reader.readInt();

                dataStream = new NBTInputStream(is, 0, ByteOrder.LITTLE_ENDIAN);
                tag = dataStream.readTag();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tag;
    }
}