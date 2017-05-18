#!/bin/zsh
# $> bash icon-gen.sh <version label> <project dir> <script sub-dir>
#
#
# process_icon version_num res_sub_dir current_work_dir  target_dir
process_icon(){
    e="$(tr '[:lower:]' '[:upper:]' <<< ${5:0:1})${5:1}"
    image_width=`identify -format %[fx:w] $3/app/src/main/res/mipmap-$2/ic_launcher.png` && let "image_width-=4"
    image_height=`identify -format %[fx:h] $3/app/src/main/res/mipmap-$2/ic_launcher.png` && let "image_height-=4"
    convert -size 512x512 canvas:none -pointsize 160 -gravity south -draw "text 0,0 '$1'" -channel RGBA -blur 0x6 -fill white -stroke black -strokewidth 3 -weight 900 -draw "text 0,0 '$1'" $3$4/v.png
    convert -composite $3$4/$5.png $3$4/v.png $3$4/$5-v.png
    convert -composite $3$4/$6.png $3$4/$5-v.png $3$4/$5-$6-v.png
    convert -composite -gravity center $3/app/src/main/res/mipmap-$2/ic_launcher.png $3$4/$5-$6-v.png -resize $image_widthx$image_height $3$4/ic_launcher.png
    mkdir -p $3/app/src/$6$e/res/mipmap-$2/
    cp $3$4/ic_launcher.png $3/app/src/$6$e/res/mipmap-$2/
    rm $3$4/ic_launcher.png
    rm $3$4/v.png
    rm $3$4/$5-v.png
    rm $3$4/$5-$6-v.png
}

type convert &> /dev/null 2>&1 || { echo "no update icon (convert not found)" && exit 0; }
type identify &> /dev/null 2>&1 || { echo "no update icon (identify not found)" && exit 0; }

echo "perform update icon"

PATH=$PATH:/usr/local/bin

for x in mdpi hdpi xhdpi xxhdpi xxxhdpi
{
    for y in debug distrib
    {
        for z in stubs preprod prod
        {
            process_icon $1 $x $2 $3 $y $z
        }
    }
}
