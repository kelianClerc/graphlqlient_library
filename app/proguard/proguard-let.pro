# Let 0.1.11

-keep class com.canelmas.let.** { *; }
-keepnames class * implements com.canelmas.let.RuntimePermissionListener

-keepclassmembers class * implements com.canelmas.let.RuntimePermissionListener {
    public void onRequestPermissionsResult(***);
}

-keepclasseswithmembernames class * {
    @com.canelmas.let.* <methods>;
}
