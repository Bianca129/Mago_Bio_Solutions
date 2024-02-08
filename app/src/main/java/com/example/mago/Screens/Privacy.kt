package com.example.mago.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mago.R

@Composable
fun Privacy(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = stringResource(id = R.string.privacyStatement_1), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_2))
        Text(text = stringResource(id = R.string.privacyStatement_3))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_4))
        Text(text = stringResource(id = R.string.privacyStatement_5))
        Text(text = stringResource(id = R.string.privacyStatement_6))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_7))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_8))
        Text(text = stringResource(id = R.string.privacyStatement_9))
        Text(text = stringResource(id = R.string.privacyStatement_10))
        Text(text = stringResource(id = R.string.privacyStatement_11))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_12))
        Text(text = stringResource(id = R.string.privacyStatement_13))
        Text(text = stringResource(id = R.string.privacyStatement_14))
        Text(text = stringResource(id = R.string.privacyStatement_15))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_16))
        Text(text = stringResource(id = R.string.privacyStatement_17))
        Text(text = stringResource(id = R.string.privacyStatement_18))
        Text(text = stringResource(id = R.string.privacyStatement_19))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_20))
        Text(text = stringResource(id = R.string.privacyStatement_21))
        Text(text = stringResource(id = R.string.privacyStatement_22))
        Text(text = stringResource(id = R.string.privacyStatement_23))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_24))
        Text(text = stringResource(id = R.string.privacyStatement_25))
        Text(text = stringResource(id = R.string.privacyStatement_26))
        Text(text = stringResource(id = R.string.privacyStatement_27))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_28))
        Text(text = stringResource(id = R.string.privacyStatement_29))
        Text(text = stringResource(id = R.string.privacyStatement_30))
        Text(text = stringResource(id = R.string.privacyStatement_31))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_32))
        Text(text = stringResource(id = R.string.privacyStatement_33))
        Text(text = stringResource(id = R.string.privacyStatement_34))
        Text(text = stringResource(id = R.string.privacyStatement_35))
        Text(text = stringResource(id = R.string.privacyStatement_36))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_37))
        Text(text = stringResource(id = R.string.privacyStatement_38))
        Text(text = stringResource(id = R.string.privacyStatement_39))
        Text(text = stringResource(id = R.string.privacyStatement_40))
        Text(text = stringResource(id = R.string.privacyStatement_41))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_42))
        Text(text = stringResource(id = R.string.privacyStatement_43))
        Text(text = stringResource(id = R.string.privacyStatement_44))
        Text(text = stringResource(id = R.string.privacyStatement_45))
        Text(text = stringResource(id = R.string.privacyStatement_46))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_47))
        Text(text = stringResource(id = R.string.privacyStatement_48))
        Text(text = stringResource(id = R.string.privacyStatement_49))
        Text(text = stringResource(id = R.string.privacyStatement_50))
        Text(text = stringResource(id = R.string.privacyStatement_51))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_52))
        Text(text = stringResource(id = R.string.privacyStatement_53))
        Text(text = stringResource(id = R.string.privacyStatement_54))
        Text(text = stringResource(id = R.string.privacyStatement_55))
        Text(text = stringResource(id = R.string.privacyStatement_56))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_57))
        Text(text = stringResource(id = R.string.privacyStatement_58))
        Text(text = stringResource(id = R.string.privacyStatement_59))
        Text(text = stringResource(id = R.string.privacyStatement_60))
        Text(text = stringResource(id = R.string.privacyStatement_61))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_62))
        Text(text = stringResource(id = R.string.privacyStatement_63))
        Text(text = stringResource(id = R.string.privacyStatement_64))
        Text(text = stringResource(id = R.string.privacyStatement_65))
        Text(text = stringResource(id = R.string.privacyStatement_66))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_67))
        Text(text = stringResource(id = R.string.privacyStatement_68))
        Text(text = stringResource(id = R.string.privacyStatement_69))
        Text(text = stringResource(id = R.string.privacyStatement_70))
        Text(text = stringResource(id = R.string.privacyStatement_71))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_72))
        Text(text = stringResource(id = R.string.privacyStatement_73))
        Text(text = stringResource(id = R.string.privacyStatement_74))
        Text(text = stringResource(id = R.string.privacyStatement_75))
        Text(text = stringResource(id = R.string.privacyStatement_76))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_77))
        Text(text = stringResource(id = R.string.privacyStatement_78))
        Text(text = stringResource(id = R.string.privacyStatement_79))
        Text(text = stringResource(id = R.string.privacyStatement_80))
        Text(text = stringResource(id = R.string.privacyStatement_81))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_82))
        Text(text = stringResource(id = R.string.privacyStatement_83))
        Text(text = stringResource(id = R.string.privacyStatement_84))
        Text(text = stringResource(id = R.string.privacyStatement_85))
        Text(text = stringResource(id = R.string.privacyStatement_86))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_87))
        Text(text = stringResource(id = R.string.privacyStatement_88))
        Text(text = stringResource(id = R.string.privacyStatement_89))
        Text(text = stringResource(id = R.string.privacyStatement_90))
        Text(text = stringResource(id = R.string.privacyStatement_91))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_92))
        Text(text = stringResource(id = R.string.privacyStatement_93))
        Text(text = stringResource(id = R.string.privacyStatement_94))
        Text(text = stringResource(id = R.string.privacyStatement_95))
        Text(text = stringResource(id = R.string.privacyStatement_96))
        Text(text = stringResource(id = R.string.privacyStatement_97))
        Text(text = stringResource(id = R.string.privacyStatement_98))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_99))
        Text(text = stringResource(id = R.string.privacyStatement_100))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_101))
        Text(text = stringResource(id = R.string.privacyStatement_102))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_103))
        Text(text = stringResource(id = R.string.privacyStatement_104))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_105))
        Text(text = stringResource(id = R.string.privacyStatement_106))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_107))
        Text(text = stringResource(id = R.string.privacyStatement_108))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_109))
        Text(text = stringResource(id = R.string.privacyStatement_110))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_111))
        Text(text = stringResource(id = R.string.privacyStatement_112))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_113))
        Text(text = stringResource(id = R.string.privacyStatement_114))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_115))
        Text(text = stringResource(id = R.string.privacyStatement_116))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_117))
        Text(text = stringResource(id = R.string.privacyStatement_118))
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(id = R.string.privacyStatement_119))
        Text(text = stringResource(id = R.string.privacyStatement_120))
        Spacer(modifier = Modifier.height(16.dp))
    }
}