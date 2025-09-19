# ============================================
# Общие атрибуты, полезные для Kotlin / reflection
# ============================================
-keepattributes Signature, *Annotation*, InnerClasses, EnclosingMethod, KotlinMetadata

# Сохраняем имена пакета приложения (удобно для логов/рефлексии) — можно сузить
-keepnames class com.nani.movies.** { *; }

# ============================================
# Parcelable classes - keep all fields and CREATOR
# ============================================
-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
    <fields>;
    <init>(...);
}

# Keep domain models (they might be used with Parcelable)
-keep class com.nani.movies.domain.model.** {
    <fields>;
    <init>(...);
}

# ============================================
# Moshi (runtime reflection) — сохраняем модели и поля
# ============================================
# Keep data models for Moshi serialization
-keep class com.nani.movies.data.remote.dto.** {
    <fields>;
    <init>(...);
}

# Legacy model package support
-keep class com.nani.movies.model.** {
    <fields>;
    <init>(...);
}

# Если ты используешь Moshi codegen и генерируешь JsonAdapter'ы,
# то стоит сохранить все классы с суффиксом JsonAdapter:
-keep class **JsonAdapter {
    *;
}

# Также сохранение аннотаций @Json и @JsonClass (на всякий случай)
-keep @interface com.squareup.moshi.Json
-keep @interface com.squareup.moshi.JsonClass

# Keep Moshi's built-in adapters
-keep class com.squareup.moshi.** { *; }
-dontwarn com.squareup.moshi.**

# ============================================
# Retrofit — оставляем интерфейсы API (методы с аннотациями)
# ============================================
# Сохраняем интерфейсы, помеченные retrofit аннотациями (GET, POST и т.д.)
-keep interface * {
    @retrofit2.http.* <methods>;
}

# Keep Retrofit API interfaces
-keep interface com.nani.movies.data.remote.api.** { *; }

# Если Retrofit используется с Rx/Coroutines — можно сохранить возвращаемые типы,
# но обычно это не требуется.

# ============================================
# OkHttp / Logging Interceptor
# ============================================
# Обычно не требуются специальные правила, но подавляем варнинги:
-dontwarn okhttp3.**
-dontwarn okio.**

# ============================================
# Koin (DI) — сохраняем необходимые метаданные/аннотации
# ============================================
# Сохраняем Kotlin метаданные (уже включено выше), и типичные классы Koin
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# ============================================
# Jetpack Compose
# ============================================
# Compose Gradle plugin обычно генерирует свои keep'ы автоматически.

# ============================================
# Kotlin Parcelize
# ============================================
-keepnames class kotlinx.parcelize.Parcelize
-keepnames class kotlinx.parcelize.Parceler


# ============================================
# Kotlin stdlib / reflection — общие предупреждения
# ============================================
-dontwarn kotlin.**
-dontwarn kotlin.jvm.**
-dontwarn kotlin.reflect.**

# ============================================
# Стандартные подсказки для библиотек (если появляются warn'ы)
# ============================================
-dontwarn javax.annotation.**
-dontwarn com.fasterxml.jackson.**

# ============================================
# Логирование и безопасность: не удаляем классы, которые ты хочешь дебажить
# ============================================
# (если хочешь полностью убрать логирование в релизе, используй productFlavors / buildTypes
#  и переключай уровень логов)
