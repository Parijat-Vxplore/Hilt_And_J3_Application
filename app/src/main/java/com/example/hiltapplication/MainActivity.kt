package com.example.hiltapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.debduttapanda.j3lib.InterCom
import com.debduttapanda.j3lib.MyScreen
import com.debduttapanda.j3lib.WirelessViewModel
import com.debduttapanda.j3lib.arguments
import com.debduttapanda.j3lib.models.EventBusDescription
import com.debduttapanda.j3lib.models.Route
import com.debduttapanda.j3lib.wvm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                val navController = rememberNavController()


                NavHost(
                    navController = navController,
                    startDestination = Routes.splash.full
                ){
                    MyScreen(
                        navController,
                        Routes.splash,
                        wirelessViewModel = { hiltViewModel<SplashViewModel>() }
                    ) {
                        SplashPage()
                    }
                    MyScreen(
                        navController,
                        Routes.login,
                        wirelessViewModel = { hiltViewModel<LoginViewModel>() }
                    ) {
                        val userInfo=navController.arguments()?.getString("userDetails")
                        LoginPage(userInfo)
                    }
                }
            }
        }
    }
}




@Composable
fun SplashPage(){
    var isColor by remember { mutableStateOf(false) }
    var userName by remember { mutableStateOf(TextFieldValue("")) }

    val myViewModel: WirelessViewModel = viewModel()

    Column(
        Modifier
            .background(colorResource(R.color.white))
            .fillMaxSize()
            .padding(20.dp, 0.dp),
        horizontalAlignment  =  Alignment.CenterHorizontally) {
        Spacer(Modifier.height(100.dp))

        Text(
            "You are in First Activity",
            fontWeight = FontWeight.W700,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            color = colorResource(R.color.black),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        TextField(  modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.white)),
            value = userName,
            textStyle = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold ,
            ),
            placeholder = {
                Text("User ID",
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                )
            },
            onValueChange = {
                userName = it
            },
        )
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                if(isColor) {
                    myViewModel.setStatusBarColor(color = Color.Red, darkIcon = false)
                    isColor=false
                }
                else {
                    myViewModel.setStatusBarColor(color = Color.Green, darkIcon = false)
                    isColor=true
                }
            }){
            Text("Change Status Bar color")
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                myViewModel.navigation {
                    navigate(Routes.login.arguments {  set("userDetails", userName.text)})
                }

            }){
            Text("Navigate to next page")
        }
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                myViewModel.interCom(InterCom("PermissionCheck",1))
            }){
            Text("Request Camera Permission")
        }
    }

}


class SplashViewModel: WirelessViewModel(){

    override fun eventBusDescription(): EventBusDescription? {
        return null
    }

    override fun interCom(message: InterCom) {
        viewModelScope.launch {
            val r = android.Manifest.permission.CAMERA.checkPermission()
            val granted = r?.allPermissionsGranted==true
            if(!granted){
                val r1 = android.Manifest.permission.CAMERA.requestPermission()
                val granted1 = r1.multiPermissionState?.allPermissionsGranted==true
                if (granted1)
                    toast("Permission Granted")
                else
                    toast("Permission Denied")
            }
            toast("Permission Already Granted")
        }
    }

    override fun onBack() {

    }

    override fun onNotification(id: Any?, arg: Any?) {

    }

    override fun onStartUp(route: Route?, arguments: Bundle?) {
        setStatusBarColor(color = Color.Red, darkIcon = false)

    }

    fun onStart() {

    }

}

object Routes{

    val splash = Route("splash" )
    val login = Route("login", listOf(
        navArgument("userDetails"){
            nullable = true
            defaultValue = null
            type = NavType.StringType

        }
    ))


}

