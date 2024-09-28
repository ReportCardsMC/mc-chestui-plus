package me.tech.mcchestui.listeners

import me.tech.mcchestui.GUI
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent

internal class GUISlotClickListener(gui: GUI) : GUIEventListener(gui) {
    @EventHandler
    internal fun InventoryClickEvent.slotClick() {
        // ensure clicked inventory is ui inventory.
        if(!gui.isBukkitInventory(clickedInventory)) {
            return
        }

        val guiSlot = gui.slots.getOrNull(slot)
            ?: return // handle cancellation of task in onPlace.

        if(click in PICKUP_CLICK_ACTIONS) {
            if(!gui.allowItemPickup) {
                isCancelled = true
                // return // This is commented out for my own use, literally why is it returning here when I need the drop and swap hand events on item clicks for additional actions, tech, you are silly for this one, actually wait I should check the git blame... One second... Okay yeah it was you, I'm not stupid, okay what the actual frick.
            }
        }

        guiSlot.onClick?.let { uiEvent ->
            uiEvent(this, whoClicked as Player)
        }
    }

    companion object {
        private val PICKUP_CLICK_ACTIONS = setOf(
            ClickType.DROP,
            ClickType.CONTROL_DROP,
            ClickType.SWAP_OFFHAND
        )
    }
}