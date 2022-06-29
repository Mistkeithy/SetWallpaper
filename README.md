# Set Wallpaper
Quickly set the wallpaper by call from adb or gallery without confirmations and no fuss.

This applicaton may to set the wallpaper without any confirmations or cropping, just get permission to read the external storage!
It useful for making a systemized clusters from android devices or work phone/tablets for mass setting of wallpapers with inventory number, device characteristics and other data.

In the other android galleries (such as com.android.gallery3d (AOSP Gallery) or Simple Gallery) you can see the annoying the crop settings, confirmations and other rubbish. This application may to bypass these problems and automate process of set the wallpaper without user intervening on N-pieces of devices like as BGInfo on Microsoft(R) Windows(R) by Sysinternals.

This application can't to add the splashes or draw texts on the images by self, you should have the advance prepared the wallpaper and move it to any paths on the device memory!

## How to setup wallpapers by ADB Shell?
You can use ADB Shell and also other Terminal emulators to use it.

### Learn the adb commands:

By first, you should put the image on internal memory of your device:
```BASH
# Your PC's terminal shell (also it works in Microsoft Windows):
~> adb push <LOCAL PATH> <DESTINATION DEVICE PATH>
```

To get device serial:
```BASH
~> adb devices

# Output:
0123456789ABCDEF device
FEDCBA9876543210 device
0000000000000111 unautorized
```

Use this prefix to connect to device:
```BASH
~> adb -s <SERIAL like as xxxxxxxxxxx> <ANY ADB CMDs>
```

Use for a one-time command call:
```BASH
~> adb shell <DEVICE SHELL COMMAND>
```

Use for the install the apk:
```BASH
~> adb install <LOCAL PATH TO APK>
```

To test it, open the device shell:
```BASH
~> adb shell

# Device shell output:
~$ 
```

### Installation and set the wallpaper:

```BASH
# Device shell:
# IMPORTANT! Get SetWallpaper permissions to read files (Or you can get the request to give permission)
~$ pm grant org.mistkeith.setwallpaper android.permission.READ_EXTERNAL_STORAGE

# Send the intent to application and set the wallpaper
~$ export DEST=<LOCAL PATH>
~$ am start -a android.intent.action.SET_WALLPAPER -c android.intent.category.DEFAULT -d file://$DEST -t 'image/*' -e mimeType 'image/*' -f 0x10000000 org.mistkeith.setwallpaper/.MainActivity
```

### Example using in the BASH script:
~~~BASH
#!/bin/bash
export DEVICE_SERIAL=0123456789ABCDEF
export LOCAL_IMAGE_FILE=/home/user/wallpaper.png
export ONDEVICE_IMAGE_FILE=/sdcard/Documents/wallpaper.png

# Install app
adb -s $SERIAL install /home/user/SetWallpaper.apk

# Grant app to read files
adb -s $SERIAL -d shell pm grant org.mistkeith.setwallpaper android.permission.READ_EXTERNAL_STORAGE

# ~~~ HERE IS SOME MAGIC WITH WALLPAPER IMAGE ~~~
# ~ ADDING SPLASHES AND SOME TEXTS ~

# Remove old wallpaper
adb -s $SERIAL shell rm $ONDEVICE_IMAGE_FILE

# Push the wallpaper
adb -s $SERIAL push $LOCAL_IMAGE_FILE $ONDEVICE_IMAGE_FILE

# Set the wallpaper
adb -s $SERIAL am start -a android.intent.action.SET_WALLPAPER -c android.intent.category.DEFAULT -d file://$ONDEVICE_IMAGE_FILE -t 'image/*' -e mimeType 'image/*' -f 0x10000000 org.mistkeith.setwallpaper/.MainActivity
~~~

## How to install wallpaper by gallery
IT SOMING SOON!

## How to add texts and watermarks?
IT SOMING SOON!

## Contribute 
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
[MIT](https://choosealicense.com/licenses/mit/)

## Authors and acknowledgment
Show your appreciation to those who have contributed to the project.

[Mistkeith](https://github.com/Mistkeithy)

