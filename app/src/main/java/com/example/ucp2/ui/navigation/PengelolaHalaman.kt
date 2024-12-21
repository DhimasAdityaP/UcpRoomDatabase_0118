package com.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.home.HomeDosenView
import com.ucp2.ui.view.dosen.DestinasiDosenInsert
import com.ucp2.ui.view.dosen.DetailDosenView
import com.ucp2.ui.view.dosen.InsertDosenView
import com.ucp2.ui.view.home.HomeView
import com.ucp2.ui.view.matakuliah.DestinasiMatakuliahInsert
import com.ucp2.ui.view.matakuliah.DetailMatakuliahView
import com.ucp2.ui.view.matakuliah.HomeMatakuliahView
import com.ucp2.ui.view.matakuliah.InsertMatakuliahView
import com.ucp2.ui.view.matakuliah.UpdateMatakuliahView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route // Starting screen is HomeView
    ) {
        // Home Screen
        composable(route = DestinasiHome.route) {
            HomeView(
                onDosenClick = {
                    navController.navigate(DestinasiDosen.route)
                },
                onMataKuliahClick = {
                    navController.navigate(DestinasiMatakuliah.route)
                },
                modifier = modifier
            )
        }

        // Dosen List Screen
        composable(route = DestinasiDosen.route) {
            HomeDosenView(
                onDetailClick = { nidn ->
                    navController.navigate("${DestinasiDosenDetail.route}/$nidn")
                    println("PengelolaHalaman = $nidn")
                },
                onBack = { navController.popBackStack() },
                onAddDosen = {
                    navController.navigate(DestinasiDosenInsert.route)
                },
                modifier = modifier
            )
        }

        // Insert Dosen Screen
        composable(
            route = DestinasiDosenInsert.route
        ) {
            InsertDosenView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        // Detail Dosen Screen
        composable(
            DestinasiDosenDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDosenDetail.NIDN) {
                    type = NavType.StringType
                }
            )
        ) {
            val nidn = it.arguments?.getString(DestinasiDosenDetail.NIDN)
            nidn?.let { nidn ->
                DetailDosenView(
                    onBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier
                )
            }
        }

        // Matakuliah List Screen
        composable(
            route = DestinasiMatakuliah.route
        ) {
            HomeMatakuliahView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiMatakuliahDetail.route}/$kode")
                    println("PengelolaHalaman = $kode")
                },
                onBack = { navController.popBackStack() },
                onAddMatakuliah = {
                    navController.navigate(DestinasiMatakuliahInsert.route)
                },
                modifier = modifier
            )
        }

        // Insert Matakuliah Screen
        composable(route = DestinasiMatakuliahInsert.route) {
            InsertMatakuliahView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }

        // Detail Matakuliah Screen
        composable(
            DestinasiMatakuliahDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiMatakuliahDetail.KODE) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val kode = backStackEntry.arguments?.getString(DestinasiMatakuliahDetail.KODE)
            kode?.let { kode ->
                DetailMatakuliahView(
                    onBack = { navController.popBackStack() },
                    onEditClick = {
                        navController.navigate("${DestinasiMatakuliahUpdate.route}/$it")
                    },
                    onDeleteClick = { navController.popBackStack() },
                    modifier = modifier
                )
            }
        }

        // Update Matakuliah Screen
        composable(
            DestinasiMatakuliahUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiMatakuliahUpdate.KODE) { type = NavType.StringType }
            )
        ) {
            UpdateMatakuliahView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() },
                modifier = modifier
            )
        }
    }
}