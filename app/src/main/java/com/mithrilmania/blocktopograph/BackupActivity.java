package com.mithrilmania.blocktopograph;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mithrilmania.blocktopograph.backup.Backup;
import com.mithrilmania.blocktopograph.backup.WorldBackups;
import com.mithrilmania.blocktopograph.databinding.ActivityBackupBinding;
import com.mithrilmania.blocktopograph.databinding.ItemBackupBinding;
import com.mithrilmania.blocktopograph.util.Consumer;
import com.mithrilmania.blocktopograph.util.UiUtil;

import java.util.Date;

public class BackupActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private ActivityBackupBinding mBinding;

    private WorldBackups mBackups;

    private MeowAdapter mBackupsListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_backup);
        World world = (World) (savedInstanceState == null
                ? getIntent().getSerializableExtra(World.ARG_WORLD_SERIALIZED)
                : savedInstanceState.getSerializable(World.ARG_WORLD_SERIALIZED));
        if (world == null) {
            finish();
            return;
        }
        mBackups = new WorldBackups(world);
        mBackups.loadConfig();
        mBinding.setBackups(mBackups);
        mBinding.cbAutoBackup.setChecked(mBackups.autoBackup);
        mBinding.cbAutoDelete.setChecked(mBackups.autoDelete);
        mBinding.cbAutoBackup.setOnCheckedChangeListener(this);
        mBinding.cbAutoDelete.setOnCheckedChangeListener(this);
        mBinding.list.setLayoutManager(new LinearLayoutManager(this));
        mBackupsListAdapter = new MeowAdapter(this::onClickRestore, this::onClickDelete);
        mBinding.list.setAdapter(mBackupsListAdapter);
        resetList();
    }

    @SuppressLint("StaticFieldLeak")
    public void onClickBackup(View view) {
        // The progress won't take long then it won't leak.
        String name = mBinding.editName.getText().toString().trim();
        String bakName = name.equals("") ? "Backup" : name;
        AlertDialog dia = UiUtil.buildProgressWaitDialog(this, R.string.general_please_wait, null);
        dia.show();
        new AsyncTask<WorldBackups, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(WorldBackups... worldBackups) {
                return worldBackups[0].createNewBackup(bakName, new Date());
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if (aBoolean)
                    UiUtil.toast(BackupActivity.this, R.string.general_done);
                else
                    UiUtil.toast(BackupActivity.this, R.string.general_failed);
                synchronized (dia) {
                    if (dia.isShowing()) dia.dismiss();
                }
                resetList();
            }
        }.execute(mBackups);
    }

    @SuppressLint("StaticFieldLeak")
    private void onClickRestore(@NonNull Backup backup) {
        // The progress won't take long then it won't leak.
        new AlertDialog.Builder(this).setTitle(R.string.restore_caption)
                .setMessage(R.string.restore_warn)
                .setPositiveButton(android.R.string.ok, (use, less) -> {
                    AlertDialog dia = UiUtil.buildProgressWaitDialog(this, R.string.general_please_wait, null);
                    dia.show();
                    new AsyncTask<Backup, Void, Boolean>() {
                        @Override
                        protected Boolean doInBackground(Backup... backups) {
                            return mBackups.restoreBackup(backups[0]);
                        }

                        @Override
                        protected void onPostExecute(Boolean aBoolean) {
                            if (aBoolean)
                                UiUtil.toast(BackupActivity.this, R.string.general_done);
                            else
                                UiUtil.toast(BackupActivity.this, R.string.general_failed);
                            synchronized (dia) {
                                if (dia.isShowing()) dia.dismiss();
                            }
                        }
                    }.execute(backup);
                }).setNegativeButton(android.R.string.cancel, null).create().show();
    }

    private void onClickDelete(@NonNull Backup backup) {
        new AlertDialog.Builder(this).setTitle(R.string.backup_delete_caption)
                .setMessage(R.string.backup_delete_warn)
                .setPositiveButton(android.R.string.ok, (use, less) -> {
                    mBackups.deleteBackup(backup);
                    resetList();
                })
                .setNegativeButton(android.R.string.cancel, null).create().show();
    }

    private void resetList() {
        mBackups.cleanOldBackups(new Date());
        mBackupsListAdapter.setData(mBackups.getBackups());
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.cb_auto_backup:
                if (b) {
                    new AlertDialog.Builder(this)
                            .setMessage(R.string.enable_auto_backup_note)
                            .setTitle(R.string.enable_auto_backup_title)
                            .setPositiveButton(android.R.string.ok, (use, less) -> mBackups.setAutoBackup(true, getString(R.string.backup_readme)))
                            .create().show();
                } else
                    mBackups.setAutoBackup(false, getString(R.string.backup_readme));
                break;
            case R.id.cb_auto_delete:
                mBackups.setAutoDelete(b, getString(R.string.backup_readme));
                break;
        }
    }

    private static class MeowAdapter extends RecyclerView.Adapter<MeowAdapter.MeowHolder> {

        @NonNull
        private Consumer<Backup> mRestorer;

        @NonNull
        private Consumer<Backup> mDeleter;

        @Nullable
        private Backup[] data;

        MeowAdapter(@NonNull Consumer<Backup> restorer,
                    @NonNull Consumer<Backup> deleter) {
            mRestorer = restorer;
            mDeleter = deleter;
        }

        public void setData(@Nullable Backup[] data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MeowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MeowHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(parent.getContext()), R.layout.item_backup, parent, false
            ));
        }

        @Override
        public void onBindViewHolder(@NonNull MeowHolder holder, int position) {
            if (data != null) holder.setData(data[position]);
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.length;
        }

        private class MeowHolder extends RecyclerView.ViewHolder {

            @NonNull
            private ItemBackupBinding mBinding;

            MeowHolder(@NonNull ItemBackupBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
                mBinding.buttonRestore.setOnClickListener(v -> mRestorer.accept(mBinding.getBackup()));
                mBinding.buttonDelete.setOnClickListener(v -> mDeleter.accept(mBinding.getBackup()));
            }

            public void setData(Backup backup) {
                mBinding.setBackup(backup);
            }

        }

    }
}
