package com.example.grifon

import androidx.lifecycle.ViewModel

class GrifonTopBarViewModel : ViewModel() {
    val destinations = listOf(
        TopBarDestination(
            label = "Σουηδικό κατάστημα χονδρικής",
            activityClass = WholesaleSwedishStoreActivity::class.java,
        ),
        TopBarDestination(
            label = "Καταστήματα λιανικής",
            activityClass = RetailStoresActivity::class.java,
        ),
        TopBarDestination(
            label = "Παραγγελίες",
            activityClass = OrdersActivity::class.java,
        ),
    )
}
