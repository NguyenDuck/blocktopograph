package com.mithrilmania.blocktopograph.flat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.andreabaccega.formedittextvalidator.Validator;
import com.andreabaccega.widget.FormEditText;
import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.block.ListingBlock;
import com.mithrilmania.blocktopograph.databinding.DialogEditLayerBinding;
import com.mithrilmania.blocktopograph.util.UiUtil;

import java.io.Serializable;

import static com.mithrilmania.blocktopograph.flat.EditFlatFragment.EXTRA_KEY_LIST_EXISTING_SUM;
import static com.mithrilmania.blocktopograph.flat.EditFlatFragment.EXTRA_KEY_LIST_INDEX;
import static com.mithrilmania.blocktopograph.flat.EditFlatFragment.EXTRA_KEY_LIST_IS_ADD;
import static com.mithrilmania.blocktopograph.flat.EditFlatFragment.EXTRA_KEY_LIST_LAYER;

public final class EditLayerDialog extends AppCompatActivity {

    public static final int REQUEST_CODE_PICK_BLOCK = 2014;
    private boolean mIsAdd;
    private int mExistingSum;
    private int mPositon;
    private DialogEditLayerBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.dialog_edit_layer);
        setResult(RESULT_CANCELED);

        Intent intent = getIntent();
        if (savedInstanceState != null || intent == null) {
            //The activity does not do anything worth restore.
            finish();
            return;
        }
        Serializable ser = intent.getSerializableExtra(EXTRA_KEY_LIST_LAYER);
        if (!(ser instanceof Layer)) {
            Log.d(this, "wtf?");
            finish();
            return;
        }
        Layer layer = (Layer) ser;

        mPositon = intent.getIntExtra(EXTRA_KEY_LIST_INDEX, -1);
        mBinding.setLayer(layer);
        mIsAdd = intent.getBooleanExtra(EXTRA_KEY_LIST_IS_ADD, true);
        mExistingSum = intent.getIntExtra(EXTRA_KEY_LIST_EXISTING_SUM, 0);

        if (mIsAdd) setTitle(R.string.edit_flat_add_layer_title);
        else setTitle(R.string.edit_flat_edit_layer_title);

        FormEditText amountBar = mBinding.amount;
        amountBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                mBinding.amount.testValidity();
            }
        });
        amountBar.addValidator(new AmountValidator(getString(R.string.edit_layer_amount_constrait)));

        mBinding.icon.setImageBitmap(layer.block.getIcon(getAssets()));
        UiUtil.blendBlockColor(mBinding.frame, layer.block);
    }

    public void onClickChangeBlock(View view) {
        startActivityForResult(
                new Intent(this, PickBlockActivity.class), REQUEST_CODE_PICK_BLOCK
        );
    }

    public void onClickPositiveButton(View view) {
        Layer layer = mBinding.getLayer();
        String am = mBinding.amount.getText().toString();
        int ami = 1;
        if (!am.isEmpty()) try {
            ami = Integer.parseInt(am);
        } catch (NumberFormatException e) {
            //
        }
        layer.amount = ami;
        setResult(RESULT_OK, new Intent()
                .putExtra(EXTRA_KEY_LIST_INDEX, mPositon)
                .putExtra(EXTRA_KEY_LIST_IS_ADD, mIsAdd)
                .putExtra(EXTRA_KEY_LIST_LAYER, layer));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK_BLOCK: {
                if (resultCode != RESULT_OK) return;
                assert data != null;
                ListingBlock block = (ListingBlock) data.getSerializableExtra(PickBlockActivity.EXTRA_KEY_BLOCK);
                Layer layer = mBinding.getLayer();
                layer.block = block;
                mBinding.setLayer(layer);
                mBinding.icon.setImageBitmap(layer.block.getIcon(getAssets()));
                UiUtil.blendBlockColor(mBinding.frame, block);
                //mBinding.notifyChange();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class AmountValidator extends Validator {

        AmountValidator(String customErrorMessage) {
            super(customErrorMessage);
        }

        @Override
        public boolean isValid(EditText et) {
            String text = et.getText().toString();
            if (text.isEmpty()) return true;
            int val;
            try {
                val = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return false;
            }
            return val >= 0 && val < 128 - mExistingSum;
        }
    }
}
