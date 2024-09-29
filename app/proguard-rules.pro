# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


# ProGuard rules for Firebase
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# ProGuard rules for Coil
-keep class coil.** { *; }
-dontwarn coil.**

# Manter as classes do ViewModel
-keep class androidx.lifecycle.ViewModel { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Manter a classe AuthViewModel
-keep class com.android.sustentare.ui.viewmodel.AuthViewModel { *; }

# Manter todos os métodos e atributos das classes que você pode usar via reflexão
-keepclassmembers class * {
    <fields>;
    <methods>;
}

# Manter todos os métodos e atributos de classes com a anotação @Composable
-keep @androidx.compose.runtime.Composable class * { *; }

# Manter a classe Uri
-keep class android.net.Uri { *; }

# Preservar atributos de informações de linha
-keepattributes SourceFile,LineNumberTable