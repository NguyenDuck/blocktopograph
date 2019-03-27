#!/usr/bin/env bash

# I've found that the "Migrate to AndroidX" converter in Android Studio doesn't work very
# well, so I wrote my own script to do the simple job of converting package names.
#
# You can download a CSV of package names here: https://developer.android.com/topic/libraries/support-library/downloads/androidx-class-mapping.csv
#
# It'll run faster on a clean build because then there are fewer files to scan over.
#
# Uses `gsed` because I'm on a Mac. Can easily replace with `sed` if you don't have `gsed`.
# 
# This isn't perfect; it won't find every conversion issue. You break it you buy it. Viewer discretion is advised.

MAPPING_FILE=./androidx-class-mapping.csv
PROJECT_DIR=.

replace=""
while IFS=, read -r from to
do
	replace+="; s/$from/$to/g"
done <<< "$(cat $MAPPING_FILE)"

find $PROJECT_DIR \( -name "*.kt" -o -name "*.java" -o -name "*.xml" \) -type f -not -path '*/\.git*' -print0 | xargs -0 gsed -i "$replace"