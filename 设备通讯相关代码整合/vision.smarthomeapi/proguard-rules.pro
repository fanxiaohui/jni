# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\App\Development\android-sdks/tools/proguard/proguard-android.txt
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

#指定代码的压缩级别
-optimizationpasses 5
#包明不混合大小写
-dontusemixedcaseclassnames
#不去忽略非公共的库类
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers   #指定不去忽略包可见的库类的成员。
#优化  不优化输入的类文件
-dontoptimize
#混淆时是否记录日志
-verbose
# 混淆时所采用的算法
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#保护注解
-keepattributes *Annotation*

# 保持系统组件类不被混淆
-keep public class * extends android.app.Fragment{
*;
}

-keep public class * extends android.support.v4.app.Fragment{
*;
}

-keep public class * extends android.app.Service{
*;
}

-keep public class * extends android.content.BroadcastReceiver{
*;
}

-keep public class * extends android.app.backup.BackupAgentHelper{
*;
}

-keep public class * extends android.preference.Preference{
*;
}

-keep public class com.android.vending.licensing.ILicensingService{
*;
}
#忽略警告
-ignorewarnings
# 避免混淆泛型 ，混淆报错建议关掉
-keepattributes Signature

#保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

#保持 枚举 不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# Bean不被混淆，反射类
-keep class com.vision.smarthomeapi.bean.**{
*;
}

# 保持继承DataSupport类不混淆
-keep class com.smarthome.head.**{
*;
}

# 保持继承DataSupport类不混淆
-keep class com.vision.smarthomeapi.bll.**{
*;
}

# 保持继承DataSupport类不混淆
-keep class com.vision.smarthomeapi.dal.impl.**{
*;
}

# 保持继承DataSupport类不混淆
-keep class com.vision.smarthomeapi.util.**{
*;
}

# 保持继承DataSupport类不混淆
-keep class com.vision.smarthomeapi.dal.user.**{
*;
}

-keep public class com.vision.smarthomeapi.dal.data.**{
*;
}
-keep public class com.vision.smarthomeapi.dal.function.**{
*;
}
-keep public class com.vision.smarthomeapi.net.**{
*;
}

-keep class com.vision.smarthomeapi.sqlutil.**{
*;
}
-keep class com.vision.smarthomeapi.sqlutil.model.**{
*;
}

-keep class com.vision.smarthomeapi.dal.sqlutil.model.**{
*;
}

-keep class com.vision.smarthomeapi.dal.sql.**{
*;
}

-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod

#保持okhttp不被混淆
-dontwarn okhttp3.**