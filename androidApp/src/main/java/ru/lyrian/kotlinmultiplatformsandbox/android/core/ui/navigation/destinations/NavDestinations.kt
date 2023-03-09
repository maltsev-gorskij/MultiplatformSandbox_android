package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations

object NavDestinations {
   object GraphsDestinations {
      const val ROOT = "rootGraph"
      const val HOME = "homeGraph"
      const val DETAILS = "detailsGraph"
   }

   object HomeNavGraph {
      const val LAUNCHES = "launchesList"
      const val MEDIA = "media"
      const val PROFILE = "profile"
   }

   object DetailsNavGraph {
      const val DETAILS = "launchDetails"
   }
}
