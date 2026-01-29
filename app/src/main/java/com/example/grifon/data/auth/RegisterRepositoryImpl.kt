package com.example.grifon.data.auth

import android.util.Log
import com.example.grifon.domain.auth.RegisterOutcome
import com.example.grifon.domain.auth.RegisterParams
import com.example.grifon.domain.auth.RegisterRepository
import com.example.grifon.domain.auth.RegisterResult
import retrofit2.HttpException
import java.io.IOException
import org.json.JSONObject

class RegisterRepositoryImpl(
    private val api: AuthApi,
) : RegisterRepository {
    override suspend fun register(params: RegisterParams): RegisterOutcome {
        return try {
            val cleanEmail = params.email.trim()
            val cleanPasswd = params.passwd.trim()
            Log.d(
                TAG,
                "Register request: email=$cleanEmail, socialTitle=${params.socialTitle}, " +
                    "firstName=${params.firstName}, lastName=${params.lastName}, countryIso=${params.countryIso}, " +
                    "street=${params.street}, city=${params.city}, postalCode=${params.postalCode}, " +
                    "phone=${params.phone}, company=${params.company}, vatNumber=${params.vatNumber}, " +
                    "iban=${params.iban}, passwdProvided=${cleanPasswd.isNotBlank()}, " +
                    "customerDataPrivacyAccepted=${params.customerDataPrivacyAccepted}, " +
                    "newsletter=${params.newsletter}, termsAndPrivacyAccepted=${params.termsAndPrivacyAccepted}, " +
                    "partnerOffers=${params.partnerOffers}",
            )
            if (cleanEmail.isBlank() || cleanPasswd.isBlank()) {
                Log.w(TAG, "Register request missing email or passwd.")
                return RegisterOutcome.Error("Ελέγξτε το email και τον κωδικό πρόσβασης.")
            }
            val request = buildMap<String, Any> {
                put("email", cleanEmail)
                put("passwd", cleanPasswd)
                params.socialTitle?.let { put("socialTitle", it) }
                put("firstName", params.firstName)
                put("lastName", params.lastName)
                put("countryIso", params.countryIso)
                put("street", params.street)
                put("city", params.city)
                put("postalCode", params.postalCode)
                params.phone?.let { put("phone", it) }
                params.company?.let { put("company", it) }
                params.vatNumber?.let { put("vatNumber", it) }
                params.iban?.let { put("iban", it) }
                put("customerDataPrivacyAccepted", params.customerDataPrivacyAccepted)
                put("newsletter", params.newsletter)
                put("termsAndPrivacyAccepted", params.termsAndPrivacyAccepted)
                params.partnerOffers?.let { put("partnerOffers", it) }
            }
            Log.d(
                TAG,
                "Register payload: passwdKey=passwd, passwdProvided=${cleanPasswd.isNotBlank()}",
            )
            val response = api.register(request)
            Log.d(
                TAG,
                "Register response: customerId=${response.customerId}, status=${response.status}, " +
                    "message=${response.message}",
            )
            RegisterOutcome.Success(
                RegisterResult(
                    customerId = response.customerId,
                    status = response.status,
                    message = response.message,
                ),
            )
        } catch (exception: HttpException) {
            Log.w(TAG, "Register request failed with HTTP ${exception.code()}.", exception)
            val apiMessage = extractApiErrorMessage(exception)
            RegisterOutcome.Error(apiMessage ?: mapHttpError(exception.code()))
        } catch (exception: IOException) {
            Log.w(TAG, "Register request failed due to network error.", exception)
            RegisterOutcome.Error("Δεν ήταν δυνατή η σύνδεση με τον server. Δοκιμάστε ξανά.")
        } catch (exception: Exception) {
            Log.e(TAG, "Register request failed with unexpected error.", exception)
            RegisterOutcome.Error("Η εγγραφή απέτυχε. Δοκιμάστε ξανά.")
        }
    }

    private fun extractApiErrorMessage(exception: HttpException): String? {
        val errorBody = exception.response()?.errorBody()?.string() ?: return null
        Log.w(TAG, "Register request error body: $errorBody")
        return try {
            val message = JSONObject(errorBody)
                .optJSONObject("error")
                ?.optString("message")
                ?.trim()
            message?.takeIf { it.isNotEmpty() }
        } catch (exception: Exception) {
            Log.w(TAG, "Register request error body was not valid JSON.", exception)
            null
        }
    }

    private fun mapHttpError(code: Int): String {
        return when (code) {
            400 -> "Ελέγξτε τα στοιχεία που συμπληρώσατε."
            401 -> "Δεν επιτρέπεται η εγγραφή με αυτά τα στοιχεία."
            409 -> "Το email χρησιμοποιείται ήδη."
            else -> "Η εγγραφή απέτυχε. Δοκιμάστε ξανά."
        }
    }

    private companion object {
        private const val TAG = "RegisterRepository"
    }
}
