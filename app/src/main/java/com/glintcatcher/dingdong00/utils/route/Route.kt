package com.glintcatcher.dingdong00.utils.route

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination

object Route {
    fun navigate(navCtrl: NavController, current: NavDestination?, destination: String) {
        if (current?.route != destination) {
            navCtrl.navigate(destination) {
                popUpTo(navCtrl.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    // 返回当前页
    fun navigate2(navCtrl: NavController, current: NavDestination?, destination: String) {
        current?.let {
            if (it.route != null && it.route != destination) {
                navCtrl.navigate(destination) {
                    popUpTo(it.route!!) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
}