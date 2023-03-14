package ru.lyrian.kotlinmultiplatformsandbox.android.core.ui.navigation.destinations

object Destinations {
   object RootGraph {
      const val GRAPH_ROUTE = "rootGraph"

      object HomeGraph {
         const val GRAPH_ROUTE = "homeGraph"

         // Launches Bottom Nav Tab
         const val LAUNCHES = "launchesHome"
         object LaunchesDetailsGraph {
            const val GRAPH_ROUTE = "launchesDetailsGraph"

            const val DETAILS = "launchDetails"
            object DetailsArgs {
               const val LAUNCH_ID = "launchId"
            }
         }

         // Videos Bottom Nav Tab
         const val VIDEOS = "videosHome"
         object VideoDetailsGraph {
            const val GRAPH_ROUTE = "videoDetailsGraph"

            const val DETAILS = "videoDetails"
            object DetailsArgs {
               const val VIDEO_ID = "videoId"
            }
         }

         // Profile Bottom Nav Tab
         const val PROFILE = "profileHome"
      }
   }
}
