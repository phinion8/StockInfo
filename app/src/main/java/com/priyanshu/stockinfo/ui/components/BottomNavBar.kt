package com.priyanshu.stockinfo.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.priyanshu.stockinfo.ui.components.models.BottomNavItem
import com.priyanshu.stockinfo.ui.theme.poppins_semiBold
import com.priyanshu.stockinfo.ui.theme.primaryColor
import com.priyanshu.stockinfo.ui.theme.secondaryColor


@Composable
fun BottomNavBar(
    modifier: Modifier,
    bottomNavItems: List<BottomNavItem>,
    navController: NavController,
    onItemClick: (BottomNavItem) -> Unit
) {

    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(
        modifier = modifier,
        containerColor = secondaryColor
    ) {

        bottomNavItems.forEach { item ->

            val selected = currentBackStackEntry.value?.destination?.route == item.route
            NavigationBarItem(
                interactionSource = MutableInteractionSource(),
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = primaryColor,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = primaryColor,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = secondaryColor
                ),
                onClick = {
                    onItemClick(item)
                },
                icon = {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (selected)
                            AnimatedVisibility(visible = true) {
                                Box(contentAlignment = Alignment.Center) {
                                    Surface(
                                        modifier = Modifier
                                            .width(62.dp)
                                            .height(34.dp)
                                            .clip(shape = RoundedCornerShape(35.dp))
                                    ) {

                                    }
                                    Icon(
                                        modifier = Modifier.size(26.dp),
                                        painter = painterResource(id = item.icon),
                                        contentDescription = item.title
                                    )
                                }
                            }
                        else
                            Icon(
                                modifier = Modifier.size(26.dp),
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title
                            )
                        Spacer(modifier = Modifier.height(3.dp))

                        AnimatedVisibility(visible = selected) {
                            Text(text = item.title, fontFamily = poppins_semiBold, fontSize = 13.sp)
                        }
                    }


                }

            )
        }

    }

}