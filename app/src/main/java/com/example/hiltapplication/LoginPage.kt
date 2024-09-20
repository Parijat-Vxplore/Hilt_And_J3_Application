package com.example.hiltapplication

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route


class LoginViewModel: WirelessViewModel(){
    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
    }

    override fun onBack() {
        popBackStack()
    }

    override fun onNotification(id: Any?, arg: Any?) {

    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
        setStatusBarColor(color = Color.Blue,darkIcon = false)

    }

    fun onStart() {

    }

}

@Composable
fun LoginPage(userInfo: String?) {

    Column(
        Modifier
            .background(colorResource(R.color.white))
            .fillMaxSize()
            .padding(20.dp, 0.dp),
        horizontalAlignment  =  Alignment.CenterHorizontally) {
        Spacer(Modifier.height(100.dp))

        Text(
            "Welcome, $userInfo",
            fontWeight = FontWeight.W700,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = colorResource(R.color.black),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))

    }

}

