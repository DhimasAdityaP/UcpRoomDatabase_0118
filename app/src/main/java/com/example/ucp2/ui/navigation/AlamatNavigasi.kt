package com.ucp2.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object DestinasiHome : AlamatNavigasi {
    override val route = "home"
}

object DestinasiDosen : AlamatNavigasi {
    override val route = "dosen"
}

object DestinasiDosenDetail : AlamatNavigasi {
    override val route = "dosendetail"
    const val NIDN = "nidn"
    val routesWithArg = "$route/{$NIDN}"
}

object DestinasiMatakuliah : AlamatNavigasi {
    override val route = "matakuliah"
}

object DestinasiMatakuliahDetail : AlamatNavigasi {
    override val route = "matakuliahdetail"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiMatakuliahUpdate : AlamatNavigasi {
    override val route = "matakuliahupdate"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

