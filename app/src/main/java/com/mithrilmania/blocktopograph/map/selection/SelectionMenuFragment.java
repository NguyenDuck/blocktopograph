package com.mithrilmania.blocktopograph.map.selection;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.mithrilmania.blocktopograph.Log;
import com.mithrilmania.blocktopograph.R;
import com.mithrilmania.blocktopograph.databinding.FragSelMenuBinding;
import com.mithrilmania.blocktopograph.map.FloatPaneFragment;
import com.mithrilmania.blocktopograph.map.edit.EditFunction;
import com.mithrilmania.blocktopograph.map.edit.SearchAndReplaceFragment;
import com.mithrilmania.blocktopograph.util.UiUtil;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class SelectionMenuFragment extends FloatPaneFragment {

    public static final String TAG_SNR = "Snr";
    @NotNull
    private final Rect mSelection = new Rect();
    private FragSelMenuBinding mBinding;
    @Nullable
    private SelectionChangedListener mSelectionChangedListener;

    private EditFunctionEntry mEditFunctionEntry;

    public static SelectionMenuFragment newInstance(
            @NotNull Rect initial, @NotNull EditFunctionEntry editFunctionEntry) {
        SelectionMenuFragment fragment = new SelectionMenuFragment();
        fragment.mSelection.set(initial);
        fragment.mEditFunctionEntry = editFunctionEntry;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_sel_menu, container, false);
        mBinding.content.setSelection(mSelection);
        mBinding.content.fromXText.addTextChangedListener(new MeowWatcher(mBinding.content.fromXText));
        mBinding.content.fromYText.addTextChangedListener(new MeowWatcher(mBinding.content.fromYText));
        mBinding.content.rangeWText.addTextChangedListener(new MeowWatcher(mBinding.content.rangeWText));
        mBinding.content.rangeHText.addTextChangedListener(new MeowWatcher(mBinding.content.rangeHText));
        mBinding.content.applyButton.setOnClickListener(this::onApply);
        mBinding.content.funcLampshade.setOnClickListener(this::onChooseLampshade);
        mBinding.content.funcSnr.setOnClickListener(this::onChooseSnr);
        mBinding.expansionLayout.post(() -> mBinding.expansionLayout.scrollTo(0, 0));
        if (mBinding.scroll != null)
            mBinding.expansionLayout.addListener(this::onExpansionChanged);
        return mBinding.getRoot();
    }

    private void onExpansionChanged(ExpansionLayout layout, boolean expanded) {
        mBinding.scroll.setScrollY(mBinding.scroll.getChildAt(0).getMeasuredHeight());
        mBinding.scroll.post(() -> mBinding.scroll.smoothScrollTo(0, 0));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setSelectionChangedListener(@Nullable SelectionChangedListener selectionChangedListener) {
        mSelectionChangedListener = selectionChangedListener;
    }

    public void onSelectionChangedOutsides(Rect rect) {
        mSelection.set(rect);
        mBinding.content.setSelection(rect);
    }

    private void onApply(View button) {

        // It should not live alone without a selection board.
        if (mSelectionChangedListener == null) {
            if (mOnCloseButtonClickListener != null)
                mOnCloseButtonClickListener.onCloseButtonClick();
            return;
        }

        int errno = 0;
        flow:
        {
            mSelection.left = UiUtil.readIntFromView(mBinding.content.fromXText);
            mSelection.top = UiUtil.readIntFromView(mBinding.content.fromYText);
            int tmp = UiUtil.readIntFromView(mBinding.content.rangeWText);
            if (tmp < 1) {
                errno = -1;
                break flow;
            }
            mSelection.right = mSelection.left + tmp;
            tmp = UiUtil.readIntFromView(mBinding.content.rangeHText);
            if (tmp < 1) {
                errno = -1;
                break flow;
            }
            mSelection.bottom = mSelection.top + tmp;
            mSelectionChangedListener.onSelectionChanged(mSelection);
        }
        switch (errno) {
            case -1: {
                Activity activity = getActivity();
                if (activity != null)
                    Toast.makeText(activity,
                            R.string.map_sel_error_size_one, Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    private void onChooseLampshade(@NotNull View view) {
        AlertDialog dialog = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AppTheme))
                .setView(R.layout.dialog_lampshade)
                .setTitle(R.string.map_edit_func_lampshade)
                .setPositiveButton(android.R.string.ok,
                        (dialogInterface, i) -> mEditFunctionEntry.invokeEditFunction(EditFunction.LAMPSHADE, null))
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.show();
        Log.logFirebaseEvent(view.getContext(), Log.CustomFirebaseEvent.SNR_OPEN);
    }

    private void onChooseSnr(View view) {
        SearchAndReplaceFragment fragment = SearchAndReplaceFragment.newInstance(mEditFunctionEntry);
        FragmentActivity activity = getActivity();
        FragmentManager fragmentManager = null;
        if (activity != null) fragmentManager = activity.getSupportFragmentManager();
        if (fragmentManager == null) fragmentManager = getChildFragmentManager();
        fragment.show(fragmentManager, TAG_SNR);
        Log.logFirebaseEvent(view.getContext(), Log.CustomFirebaseEvent.SNR_OPEN);
    }

    public interface EditFunctionEntry {
        void invokeEditFunction(@NotNull EditFunction func, @Nullable Bundle args);
    }

    private class MeowWatcher implements TextWatcher {

        private final WeakReference<EditText> which;

        MeowWatcher(EditText which) {
            this.which = new WeakReference<>(which);
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            EditText which = this.which.get();
            if (which == null) return;
            String text = editable.toString();
            int val;
            try {
                val = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                return;
            }
            switch (which.getId()) {
                case R.id.from_x_text:
                    if (mSelection.left == val) return;
                    which.removeTextChangedListener(this);
                    mSelection.right += val - mSelection.left;
                    mSelection.left = val;
                    break;
                case R.id.from_y_text:
                    if (mSelection.top == val) return;
                    which.removeTextChangedListener(this);
                    mSelection.bottom += val - mSelection.top;
                    mSelection.top = val;
                    break;
                case R.id.range_w_text:
                    if (mSelection.right - mSelection.left == val) return;
                    which.removeTextChangedListener(this);
                    mSelection.right = mSelection.left + val;
                    break;
                case R.id.range_h_text:
                    if (mSelection.bottom - mSelection.top == val) return;
                    which.removeTextChangedListener(this);
                    mSelection.bottom = mSelection.top + val;
                    break;
            }
            mBinding.content.setSelection(mSelection);
            which.addTextChangedListener(this);
        }
    }
}
