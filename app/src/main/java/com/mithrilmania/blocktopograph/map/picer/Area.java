package com.mithrilmania.blocktopograph.map.picer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Area {

    static int maxDist;

    int mMinX;
    int mMinZ;
    int mMaxX;
    int mMaxZ;
    private final List<Member> mList;

    Area(int firstX, int firstZ) {
        mMaxX = mMinX = firstX;
        mMaxZ = mMinZ = firstZ;
        mList = new ArrayList<>(16);
    }

    boolean add(int x, int z) {
        if (x >= mMinX - maxDist && x <= mMaxX + maxDist
                && z >= mMinZ - maxDist && z <= mMaxZ + maxDist) {
            if (x < mMinX) mMinX = x;
            else if (x > mMaxX) mMaxX = x;
            if (z < mMinZ) mMinZ = z;
            else if (z > mMaxZ) mMaxZ = z;
            mList.add(new Member(x, z));
            return true;
        }
        return false;
    }

    private boolean absMerge(@NonNull Area another) {
        if (another.mMaxX >= mMinX - maxDist && another.mMinX <= mMaxX + maxDist
                && another.mMaxZ >= mMinZ - maxDist && another.mMinZ <= mMaxZ + maxDist) {
            if (another.mMinX < mMinX) mMinX = another.mMinX;
            else if (another.mMaxX > mMaxX) mMaxX = another.mMaxX;
            if (another.mMinZ < mMinZ) mMinZ = another.mMinZ;
            else if (another.mMaxZ > mMaxZ) mMaxZ = another.mMaxZ;
            mList.addAll(another.mList);
            return true;
        }
        return false;
    }

    static void absMergeList(@NonNull List<Area> list) {
        boolean hasOp;
        do {
            hasOp = false;
            for (int i = list.size() - 1; i > 0; i--) {
                Area toBeMerged = list.get(i);
                assert toBeMerged != null;
                for (int j = 0; j < i; j++) {
                    if (list.get(j).absMerge(toBeMerged)) {
                        list.remove(toBeMerged);
                        hasOp = true;
                        break;
                    }
                }
            }
        } while (hasOp);
    }

    @NonNull
    List<Member> getList() {
        return mList;
    }

    int calculateArea() {
        return (mMaxX - mMinX) * (mMaxZ - mMinZ);
    }

    public int width() {
        return mMaxX - mMinX;
    }

    public int height() {
        return mMaxZ - mMinZ;
    }

    class Member {

        public int x;
        public int z;

        Member(int x, int z) {
            this.x = x;
            this.z = z;
        }
    }

}