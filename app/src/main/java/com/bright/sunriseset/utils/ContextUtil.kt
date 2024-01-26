package com.bright.sunriseset.utils

import android.content.Context
import android.content.ContextWrapper
import android.os.LocaleList
import java.util.Locale


class ContextUtils(base: Context) : ContextWrapper(base) {
    companion object {
        /**
         * The updateLocale method facilitates the dynamic switching of the application locale.
         * It takes the current application context and the target locale to switch to.
         *
         * @param context The current application context.
         * @param localeToSwitchTo The target Locale to switch to.
         * @return An instance of [ContextUtils] with the updated context.
         */
        fun updateLocale(context: Context, localeToSwitchTo: Locale): ContextUtils {
            val resources = context.resources
            // 1. Create a new configuration object using the resource configuration in the given Context.
            val configuration = resources.configuration
            //2. Create a locale list containing the locale to which we plan to switch.
            val localeList = LocaleList(localeToSwitchTo)
            //3. Assign the default locale for this instance of the JVM to the locale
            // at the first index on this list.
            // Since there’s only one locale present in our example,
            // this will inadvertently set the locale on the localeToSwitchTo variable as the default locale.
            LocaleList.setDefault(localeList)
            //4. Set the locale list we created in step 2 to the configuration object we created in step 1 using a setter.
            configuration.setLocales(localeList)
            //5. Set the current context variable to a new Context object whose resources are adjusted to match the given configuration.
            val updatedContext = context.createConfigurationContext(configuration)
            //6. Return an instance of ContextUtils with the updated context.
            return ContextUtils(updatedContext)
        }
    }
}