package com.paymorecase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import com.paymorecase.main.MainRoute

/**
 * Main navigation host for the PaymoreCase application.
 *
 * This composable serves as the central navigation hub for the entire application,
 * implementing a modular navigation structure that coordinates between different feature modules.
 *
 * Each feature module has its own navigation setup which is connected here to create
 * a cohesive navigation experience. The navigation structure uses type-safe route objects
 * instead of string routes for improved maintainability.
 *
 * The Routes classes are deliberately placed within each feature module along with the
 * Navigation Compose dependency implementation in each feature module. This architecture
 * allows each feature to access navigation arguments directly using the
 * [SavedStateHandle.toRoute] function, maintaining proper encapsulation and
 * feature independence while still enabling type-safe navigation argument access.
 *
 * ### Example Usages
 *
 * #### 1. Navigating to Home with Arguments
 * ```
 * // In home feature module:
 * @Serializable
 * data class HomeRoute(
 *     val userId: String,
 *     val userRole: String,
 *     val isFirstLogin: Boolean
 * ) : HomeRoutes
 *
 * // In navigation extension:
 * internal fun NavController.navigateToHome(
 *     userId: String,
 *     userRole: String,
 *     isFirstLogin: Boolean,
 * ) {
 *     navigate(HomeRoute(userId, userRole, isFirstLogin))
 * }
 *
 * // In LoginContainer:
 * LoginContainer(
 *     navigateToHome = { userId, role, isFirst ->
 *         navigateToHome(userId, role, isFirst)
 *     }
 * )
 *```
 *
 * #### 2. Accessing route parameters in ViewModel
 * ```
 * // The ViewModel:
 * val argumentId: Int = savedStateHandle
 *     .toRoute<AddTeacherRoutes.CompetentInformationRoute>()
 *     .id
 * ```
 *
 * @param modifier Modifier to be applied to the NavHost composable
 */
@Composable
fun PaymoreCaseNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MainRoute
    ) {
        setupMainUsageFlow(navController)
        setupQrPaymentUsageFlow(navController)
    }
}