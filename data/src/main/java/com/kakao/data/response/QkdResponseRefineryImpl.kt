package com.kakao.data.response

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.kakao.domain.exception.ErrorDto
import com.kakao.domain.response.QkdErrorResponse
import com.kakao.domain.response.QkdSuccessResponse
import com.kakao.domain.response.ResponseListDto
import retrofit2.Response
import javax.inject.Inject

class QkdResponseRefineryImpl @Inject constructor(
) : QkdResponseRefinery {

    override fun <T> response(response: Response<T>) = responseByCode(response)

    /**
     * Response Return
     * @desc Define from Response Code
     */
    private fun <T> responseByCode(response: Response<T>): QkdResult<Any> {
        return when (response.code()) {
            in 200..299 -> response2xCode(response)
            in 300..399 -> response3xCode(response)
            in 400..499 -> response4xCode(response)
            in 500..599 -> response5xCode(response)
            else -> responseUnexpectedException(response = response, exception = "")
        }
    }

    /**
     * Success
     * @desc 2x
     */
    private fun <T> response2xCode(
        response: Response<T>
    ): QkdResult<QkdSuccessResponse<T>> {

//        val (nextOffset, limit) = if (response.body() is ResponseListDto<*>) {
//            val body = response.body() as ResponseListDto<*>
//            if (body.value.isNotEmpty()) {
//                if (body.count == body.limit) { body.offset + 1 } else { null }
//            } else { null } to body.limit
//        } else {
//            null to null
//        }

        return QkdResult.Success(
            QkdSuccessResponse(
                code = response.code(),
                body = response.body(),
//                nextOffset = nextOffset,
//                limit = limit
            )
        )
    }

    /**
     * Redirect
     * @desc 3x
     */
    private fun <T> response3xCode(
        response: Response<T>
    ): QkdResult<QkdErrorResponse> {
        return responseUnexpectedException(response = response, exception = "")
    }

    /**
     * Failed
     * @desc 4x
     */
    private fun <T> response4xCode(
        response: Response<T>
    ): QkdResult<QkdErrorResponse> {
        return try {
            val gson = Gson()
            val type = object : TypeToken<QkdErrorResponse>() {}.type
            val errorResponse: QkdErrorResponse = gson.fromJson(response.errorBody()?.charStream(), type)

            QkdResult.Error(QkdErrorResponse(error = errorResponse.error))
        } catch (e: NullPointerException) {
            responseUnexpectedException(response = response, exception = e.message.toString())
        } catch (e: JsonSyntaxException) {
            responseUnexpectedException(response = response, exception = e.message.toString())
        } catch (e: Exception) {
            responseUnexpectedException(response = response, exception = e.message.toString())
        }
    }

    /**
     * Failed
     * @desc 5x
     */
    private fun <T> response5xCode(
        response: Response<T>
    ): QkdResult<QkdErrorResponse> {
        return responseUnexpectedException(response = response, exception = "")
    }

    /**
     * UnexpectedException
     * @desc ELSE
     */
    private fun <T> responseUnexpectedException(
        response: Response<T>,
        exception: String
    ): QkdResult<QkdErrorResponse> {
        return QkdResult.Error(
            QkdErrorResponse(error = ErrorDto(code = response.code(), message = exception))
        )
    }
}
