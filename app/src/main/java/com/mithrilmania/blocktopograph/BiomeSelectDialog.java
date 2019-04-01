package com.mithrilmania.blocktopograph;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.mithrilmania.blocktopograph.databinding.DialogPickBiomeBinding;
import com.mithrilmania.blocktopograph.databinding.ItemPickBiomeBinding;
import com.mithrilmania.blocktopograph.map.Biome;
import com.mithrilmania.blocktopograph.util.UiUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public final class BiomeSelectDialog extends AppCompatActivity {

    public static final String KEY_BIOME = "biome";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setResult(RESULT_CANCELED);

        DialogPickBiomeBinding binding = DataBindingUtil.setContentView(this, R.layout.dialog_pick_biome);
        binding.list.setLayoutManager(new LinearLayoutManager(this));
        binding.list.setAdapter(new MeowAdapter());
    }

    private class MeowAdapter extends RecyclerView.Adapter<MeowAdapter.MeowHolder> {

        @NonNull
        @Override
        public MeowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemPickBiomeBinding binding = DataBindingUtil.inflate(
                    getLayoutInflater(), R.layout.item_pick_biome, parent, false
            );
            MeowHolder meowHolder = new MeowHolder(binding.getRoot());
            meowHolder.binding = binding;
            binding.biomeView.setOnClickListener(v -> {
                Biome biome = meowHolder.binding.getBiome();
                setResult(RESULT_OK, new Intent().putExtra(KEY_BIOME, biome));
                finish();
            });
            return meowHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MeowHolder holder, int position) {
            Biome biome = Biome.values()[position];
            UiUtil.blendBlockColor(holder.binding.biomeView, biome);
            holder.binding.setBiome(biome);
        }

        @Override
        public int getItemCount() {
            return Biome.values().length;
        }

        private class MeowHolder extends RecyclerView.ViewHolder {

            private ItemPickBiomeBinding binding;

            MeowHolder(@NonNull View itemView) {
                super(itemView);
            }
        }

    }
}
