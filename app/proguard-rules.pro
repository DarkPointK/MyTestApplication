# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Programs\Tools\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose
-dontpreverify

-dontwarn com.squareup.picasso.**
-dontwarn com.android.support.**
-dontwarn com.nineoldandroids.**
-dontwarn com.jakewharton.**
-dontwarn  com.google.dagger.**
-dontwarn  com.readystatesoftware.systembartint.**

-keepattributes InnerClasses
-keepattributes Signature
-keepattributes *Annotation*
-keep @interface *
-keep enum * { *; }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgent
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment

-keep public class com.android.vending.licensing.ILicensingService
-keep public  class com.squareup.picasso.**{*;}
-keep public  class com.nineoldandroids.**{*;}
-keep public  class com.jakewharton.**{*;}
-keep public  class com.google.dagger.**{*;}
-keep  public class com.readystatesoftware.systembartint.**{*;}
-keep public  class com.android.support.**{*;}
-keep public class com.example.alphadog.mytestapplication.ui.**{*;}

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
  <init>(android.os.Parcel);
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
-dontwarn javax.xml.**

-keep class * extends java.util.ListResourceBundle { protected Object[][] getContents(); }

#------------LIBS--------------------
-keep class android.support.** { public *; }