# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\manojK\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
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

-optimizationpasses 5
-optimizations !code/simplification/arithmetic

#When not preverifing in a case-insensitive filing system, such as Windows. Because this tool unpacks your processed jars, you should then use:
-dontusemixedcaseclassnames

#Specifies not to ignore non-public library classes. As of version 4.5, this is the default setting
-dontskipnonpubliclibraryclasses

#Preverification is irrelevant for the dex compiler and the Dalvik VM, so we can switch it off with the -dontpreverify option.
-dontpreverify


#To keep parcelable classes (to serialize - deserialize objects to sent through Intents)
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#To remove debug logs:
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

#Uncomment if using Serializable
-keep class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

#http://proguard.sourceforge.net/manual/examples.html#androidapplication
#Deprecated
-keepattributes Exceptions,InnerClasses,SourceFile,Signature,LineNumberTable,*Annotation*


#-keep class in.gov.hartrans.etickets.*{ *; }
#-dontwarn in.gov.hartrans.etickets.**

#-keep public class * extends android.app.AppCompatActivity
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service


-verbose

#-keep public class com.google.android.gms.**
#-dontwarn com.google.android.gms.**

#-dontwarn android.support.design.**
#-keep class android.support.design.** { *; }
#-dontnote com.google.android.gms.**
#-keep class com.google.android.gms.** { *; }
#-dontwarn org.apache.commons.**
#-dontwarn org.apache.http.**
#-keep class org.apache.http.** { *; }
#-dontwarn jcifs.http.NetworkExplorer
#-keep class android.support.v4.** { *; }
#-dontnote android.support.v4.**
#-keep class android.support.v7.** { *; }
#-dontnote android.support.v7.**

#-dontnote

-keepattributes SourceFile,LineNumberTable,EnclosingMethod
-dontwarn org.apache.**

#-keep class org.apache.http.**
#-keep interface org.apache.http.**
#-keep class         org.apache.commons.beanutils.** { *; }
#-keep interface     org.apache.commons.beanutils.** { *; }
#-keep enum          org.apache.commons.beanutils.** { *; }
#-keep, includedescriptorclasses


# 10-may-2018
# added to ingnore warnings to generate signed apk
-ignorewarnings
