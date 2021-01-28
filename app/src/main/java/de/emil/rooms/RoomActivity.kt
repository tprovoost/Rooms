package de.emil.rooms

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class RoomActivity : AppCompatActivity() {

    protected fun leaveActivityWithAnimation() {
        finish()
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                leaveActivityWithAnimation()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        leaveActivityWithAnimation()
    }


}