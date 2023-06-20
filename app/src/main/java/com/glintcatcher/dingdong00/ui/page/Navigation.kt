import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.glintcatcher.dingdong00.local.RemindEntity
import com.glintcatcher.dingdong00.ui.page.*
import com.glintcatcher.dingdong00.utils.route.RouteName
import com.glintcatcher.dingdong00.viewmodel.LoginAction
import com.glintcatcher.dingdong00.viewmodel.LoginViewModel
import com.glintcatcher.dingdong00.viewmodel.RemindViewModel
import com.glintcatcher.dingdong00.viewmodel.TabViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@Composable
fun NavigationCtrl(
    navCtrl: NavHostController,
    title: MutableState<String>,
    homePagerState: PagerState,
    remindViewModel: RemindViewModel,
    tabViewModel: TabViewModel,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    onRemindUpdate: (RemindEntity) -> Unit,
) {
    val loginViewModel: LoginViewModel = viewModel()

    AnimatedNavHost(
        navController = navCtrl,
        startDestination = RouteName.HOMEPAGE
    ) {
        composable(
            RouteName.HOMEPAGE
        ) {
            title.value = RouteName.HOMEPAGE
            HomePage(homePagerState, remindViewModel, tabViewModel, onRemindUpdate) {
                scope.launch {
                    scaffoldState.bottomSheetState.apply {
                        if (isExpanded) {
                            collapse()
                        } else {
                            expand()
                        }
                    }
                }
            }
        }
        composable(RouteName.LOGIN) {
            title.value = RouteName.LOGIN
            Login(loginViewModel)
        }
        composable(RouteName.TIMEOUT) {
            title.value = RouteName.TIMEOUT
            TimeOut(viewModel = remindViewModel, onUpdate = onRemindUpdate)
        }
        composable(RouteName.SETTING) {
            title.value = RouteName.SETTING
            Setting(navCtrl) {
                loginViewModel.dispatch(LoginAction.ClearNickname)
                loginViewModel.dispatch(LoginAction.ClearUsername)
                loginViewModel.dispatch(LoginAction.ClearPassword)
            }
        }
        composable(RouteName.ABOUT_ME) {
            AboutMe()
        }
    }
}