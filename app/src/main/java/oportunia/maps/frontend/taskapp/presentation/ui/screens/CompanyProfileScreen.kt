package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.LanguageSelector
import oportunia.maps.frontend.taskapp.presentation.ui.components.ProfileInfoCard
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyViewModel


@Composable
fun CompanyProfileScreen(
    companyViewModel: CompanyViewModel,
    navController: NavController,
    onLogOut: () -> Unit
) {
    LaunchedEffect(Unit) {
        companyViewModel.getLoggedCompany()
    }

    val companyState by companyViewModel.companyState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            LanguageSelector()
        }

        when (val state = companyState) {
            is CompanyState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CompanyState.Empty -> {
                Text(
                    text = stringResource(R.string.no_company_info_available),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is CompanyState.Success -> {
                val company = state.company

                Spacer(modifier = Modifier.height(24.dp))

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(company.imageProfile)
                        .crossfade(true)
                        .error(R.drawable.default_profile_icon)
                        .fallback(R.drawable.default_profile_icon)
                        .build(),
                    contentDescription = stringResource(R.string.profile_picture_content_description),
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = company.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.rating_format, company.rating),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFFA500),
                    modifier = Modifier.clickable {
                        navController.navigate(NavRoutes.CompanyRatings.createRoute(company.id!!))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileInfoCard(
                    title = stringResource(R.string.mission),
                    value = company.mision
                )
                ProfileInfoCard(
                    title = stringResource(R.string.vision),
                    value = company.vision
                )
                ProfileInfoCard(
                    title = stringResource(R.string.corporate_culture),
                    value = company.corporateCultur
                )
                ProfileInfoCard(
                    title = stringResource(R.string.contact),
                    value = company.contact.toString()
                )

                Spacer(modifier = Modifier.height(32.dp))

                CustomButton(
                    onClick = { onLogOut() },
                    text = stringResource(R.string.logout_button),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
                Spacer(modifier = Modifier.height(80.dp))
            }

            is CompanyState.Error -> {
                Text(
                    text = stringResource(R.string.error_message, state.message),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            CompanyState.Failure -> {
                Text(text = "")
            }
        }
    }
}